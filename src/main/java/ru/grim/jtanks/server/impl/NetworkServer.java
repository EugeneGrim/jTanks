package ru.grim.jtanks.server.impl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.grim.jtanks.exception.ClientAcceptServerException;
import ru.grim.jtanks.exception.StartServerException;
import ru.grim.jtanks.exception.StopServerException;
import ru.grim.jtanks.server.Server;

public class NetworkServer implements Server {
	
	static ExecutorService executeIt = Executors.newFixedThreadPool(2);
	
	private ServerSocket serverSocket;

	@Override
	public void run() {
		openServerSocket(5555);
		
        while (!serverSocket.isClosed()) {
            acceptClientConnection();
        }
        
        closeServerSocket();
	}
	
	@Override
	public void stop() {
		closeServerSocket();
	}
	
	private void openServerSocket(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			//TODO write to log
			throw new StartServerException(e.getMessage());
		}
	}

	private void acceptClientConnection() {
		try {
			Socket client = serverSocket.accept();
			executeIt.execute(new MonoThreadClientHandler(client));
			System.out.print("Connection accepted.");
		} catch (SocketException e) {
			//TODO write to log
			System.out.println(e.getMessage());
		} catch (IOException e) {
			//TODO write to log
			throw new ClientAcceptServerException(e.getMessage());
		}
	}

	private void closeServerSocket() {
		executeIt.shutdownNow();
		try {
			if (serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch (IOException e) {
			// TODO write to log
			throw new StopServerException(e.getMessage());
		} finally {
			serverSocket = null;
		}
	}
}
