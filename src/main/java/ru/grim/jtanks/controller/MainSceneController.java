package ru.grim.jtanks.controller;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Set;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import ru.grim.jtanks.client.Client;
import ru.grim.jtanks.exception.handler.MainSceneExceptionHandler;
import ru.grim.jtanks.exception.handler.StartServerExceptionHandler;
import ru.grim.jtanks.exception.handler.StopServerExceptionHandler;
import ru.grim.jtanks.server.Server;
import ru.grim.jtanks.server.impl.NetworkServer;

public class MainSceneController {
	
	public NetworkServerController serverController;
	public NetworkClientController clientController;
	
	private Set<MainSceneExceptionHandler> exceptionHandlers;
	
	@FXML private Label serverStatusLabel;
	@FXML private MenuItem startServerMenuItem;
	@FXML private MenuItem stopServerMenuItem;
	
	public MainSceneController() {
		serverController = new NetworkServerController();
		clientController = new NetworkClientController();
		exceptionHandlers = Set.of(
				new StartServerExceptionHandler(), 
				new StopServerExceptionHandler());
		Thread.setDefaultUncaughtExceptionHandler(getMainSceneExceptionHandler());
	}
	
	public void startServerMenuItemClicked() {
		serverController.startServer();
	}
	
	public void stopServerMenuItemClicked() {
		serverController.stopServer();
	}
	
	public void joinServerMenuItemClicked() {
		clientController.joinServer();
	}
	
	public void quitMenuItemClicked() {
		Platform.exit();
	}

	private UncaughtExceptionHandler getMainSceneExceptionHandler() {
		return (thread, exception) 
				-> exceptionHandlers.forEach(handler -> handler.handleException(exception, MainSceneController.this));
	}
	
	public class NetworkServerController {
		
		private Server server;
		
		public void startServer() {
			Thread serverThread = createNewServerAsDaemon();
			serverThread.start();
			setSceneControlsIfServerStarted();
		}
		
		public void stopServer() {
			server.stop();
		}
		
		public void setSceneControlsIfServerStarted() {
			Platform.runLater(() -> {
				serverStatusLabel.setText("Server status: running");
				startServerMenuItem.setDisable(true);
				stopServerMenuItem.setDisable(false);
			});
		}

		public void setSceneControlsIfServerStoped() {
			Platform.runLater(() -> {
				serverStatusLabel.setText("Server status: is not running");
				startServerMenuItem.setDisable(false);
				stopServerMenuItem.setDisable(true);
			});
		}
		
		public void setSceneControlsIfServerError() {
			Platform.runLater(() -> {
				serverStatusLabel.setText("Server status: ERROR starting server");
				startServerMenuItem.setDisable(false);
				stopServerMenuItem.setDisable(true);
			});
		}
		
		private Thread createNewServerAsDaemon() {
			server = new NetworkServer();
			Thread serverThread = new Thread(server);
			serverThread.setDaemon(true);
			return serverThread;
		}
	}
	
	public class NetworkClientController {
		
		private Client client;
		
		public void joinServer() {
			client.connect("localhost", 5555);
		}
		
	}
}
