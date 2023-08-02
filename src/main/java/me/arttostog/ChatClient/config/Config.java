package me.arttostog.ChatClient.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.arttostog.ChatClient.request.SendAndGetMessages;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Config {
	private static final File config = new File("config.json");

	public static void CopyConfig() throws IOException, URISyntaxException {
		if (!config.exists()) {
			URI uri = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("config.json")).toURI();
			Files.copy(Paths.get(uri), config.toPath());
		}
	}

	public static void SetValues() throws FileNotFoundException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(config));
		JsonObject obj = new Gson().fromJson(bufferedReader, JsonObject.class);

		SendAndGetMessages.Address = "http://" + obj.get("ip").toString().replaceAll("\"", "") +
				":" + obj.get("port").toString().replaceAll("\"", "") +
				"/message/";
	}
}
