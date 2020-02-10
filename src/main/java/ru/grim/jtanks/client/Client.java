package ru.grim.jtanks.client;

public interface Client {
	
	void connect(String server, int port);
	
	void disconnect();

}
