package me.arttostog.ChatClient.request;

import java.io.IOException;
import java.time.Instant;

public class SendAndGetMessages {
	public static String Address = "";

	public static void Send(String Username, String Message) throws IOException {
		RequestCreator.Create(Address + "send?user=" + Username + "&date=" + Instant.now() + "&message=" + Message);
	}

	public static String[] GetMessages() throws IOException {
		String[] output = RequestCreator.Create(Address + "get")
				.replace("[", "")
				.replace("]", "")
				.split("},");

		for (int i = 0; i < output.length - 1; i++) output[i] += "}";

		return output;
	}
}
