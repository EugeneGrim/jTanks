package ru.grim.jtanks.server.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.grim.jtanks.server.Server;

public class NetworkServer implements Server {
	
	static ExecutorService executeIt = Executors.newFixedThreadPool(2);
	
	private boolean isRunning;
	
	private ServerSocket server;

	@Override
	public void start(int port) {
		
		openServerSocket(port);
		
        while (!server.isClosed()) {
            if (!isRunning) {
            	System.out.println("Main Server initiate exiting...");
            	closeServerSocket();
                break;
            }
            acceptClientConnection();
        }
        closeServerSocket();
	}

	private void acceptClientConnection() {
		try {
			Socket client = server.accept();
			executeIt.execute(new MonoThreadClientHandler(client));
			System.out.print("Connection accepted.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void closeServerSocket() {
		executeIt.shutdown();
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void openServerSocket(int port) {
		try {
			server = new ServerSocket(port);
			isRunning = true;
			System.out.println("Server socket created, command console reader for listen to server commands");
		} catch (IOException e) {
			isRunning = false;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void stop() {
		isRunning = false;
	}

}
