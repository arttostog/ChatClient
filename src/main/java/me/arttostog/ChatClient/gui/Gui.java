package me.arttostog.ChatClient.gui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.arttostog.ChatClient.request.SendAndGetMessages;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.IOException;

public class Gui {
	private static JTextArea messages;
	private static JTextField username;
	private static JTextField message;

	public static void Open() {
		JFrame frame = new JFrame("Chat");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);

		JPanel start = new JPanel();

		username = new JTextField("Username", 10);

		start.add(username);

		JPanel center = new JPanel();

		messages = new JTextArea(17, 34);
		messages.setEditable(false);
		DefaultCaret caret = (DefaultCaret)messages.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scroll = new JScrollPane(messages);

		center.add(scroll, BorderLayout.PAGE_START);

		JPanel end = new JPanel();

		message = new JTextField("Message", 16);

		JButton submit = new JButton("Submit!");
		submit.addActionListener(e -> {
			try {
				SendMessage();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		});

		end.add(message);
		end.add(submit);

		frame.add(BorderLayout.PAGE_START, start);
		frame.add(BorderLayout.CENTER, center);
		frame.add(BorderLayout.PAGE_END, end);

		new Thread(Gui::UpdateMessages).start();

		frame.setVisible(true);
	}

	private static void UpdateMessages() {
		try {
			while (true) {
				String[] msgs = SendAndGetMessages.GetMessages();

				StringBuilder area = new StringBuilder();

				for (String message: msgs) {
					if (message.isEmpty()) continue;

					JsonObject obj = JsonParser.parseString(message).getAsJsonObject();

					String Date = obj.get("Date").getAsString().split("\\.")[0].replace("T", " ");

					String sb = "[" + Date + "] " +
							obj.get("User").getAsString() + ": " +
							obj.get("Message").getAsString() + "\n";

					area.append(sb);
				}

				messages.setText(area.toString());

				Thread.sleep(1000);
			}
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static void SendMessage() throws IOException {
		if (username.getText().isEmpty() || message.getText().isEmpty()) {
			return;
		}

		SendAndGetMessages.Send(username.getText(), message.getText());
	}
}
