package ru.grim.jtanks.client;

public interface Client {
	
	void connect(String server, String port);
	
	void disconnect();

}
