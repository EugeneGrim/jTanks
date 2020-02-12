package ru.grim.jtanks.controller;

import ru.grim.jtanks.client.Client;
import ru.grim.jtanks.client.impl.NetworkClient;

public class NetworkClientController {
	
	private Client client;
	
	public void joinServer() {
		client = new NetworkClient();
		client.connect("localhost", 5555);
	}
	
	public void disconnect() {
		client = null;
	}
}
