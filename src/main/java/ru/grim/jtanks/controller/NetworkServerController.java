package ru.grim.jtanks.controller;

import ru.grim.jtanks.server.Server;
import ru.grim.jtanks.server.impl.NetworkServer;

public class NetworkServerController {
	
	private Server server;

	public void startServer() {
		server = new NetworkServer();
		server.startServer();
	}
	
	public void stopServer() {
		server.stopServer();
		server = null;
	}	
}
