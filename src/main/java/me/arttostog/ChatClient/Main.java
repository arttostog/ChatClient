package me.arttostog.ChatClient;

import me.arttostog.ChatClient.config.Config;
import me.arttostog.ChatClient.gui.Gui;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
	public static void main(String[] args) throws IOException, URISyntaxException {
		Config.CopyConfig();
		Config.SetValues();
		Gui.Open();
	}
}