package ru.grim.jtanks.controller;

import ru.grim.jtanks.client.Client;
import ru.grim.jtanks.client.impl.NetworkClient;
import ru.grim.jtanks.server.impl.NetworkServer;

public class NetworkClientController {
	
	private Client client;
	
	public void joinServer() {
//		client = new NetworkClient();
//		client.connect("localhost", 5555);
//		createNewClientAsDaemon().start();
		client = new NetworkClient("localhost", 5555);
		client.connect();
	}
	
	public void disconnect() {
		client.disconnect();
		client = null;
	}
}
