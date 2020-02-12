package ru.grim.jtanks.controller;

import ru.grim.jtanks.server.Server;
import ru.grim.jtanks.server.impl.NetworkServer;

public class NetworkServerController {
	
	private Server server;

	public void startServer() {
		createNewServerAsDaemon().start();
	}
	
	public void stopServer() {
		server.stop();
		server = null;
	}
	
	private Thread createNewServerAsDaemon() {
		server = new NetworkServer();
		Thread serverThread = new Thread(server);
		serverThread.setDaemon(true);
		return serverThread;
	}
}
