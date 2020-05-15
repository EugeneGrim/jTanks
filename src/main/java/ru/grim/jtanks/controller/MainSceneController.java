package ru.grim.jtanks.controller;

import java.util.Set;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import ru.grim.jtanks.exception.handler.ClientConnectExceptionHandler;
import ru.grim.jtanks.exception.handler.ExceptionHandler;
import ru.grim.jtanks.exception.handler.StartServerExceptionHandler;

public class MainSceneController {
	
	public final MainMenuController mainMenuController;
	public final NetworkServerController serverController;
	public final NetworkClientController clientController;
	
	private final Set<ExceptionHandler> exceptionHandlers;
	
	@FXML private Label statusLabel;
	@FXML private MenuItem startServerMenuItem;
	@FXML private MenuItem stopServerMenuItem;
	@FXML private MenuItem joinServerMenuItem;
	@FXML private MenuItem disconnectServerMenuItem;
	
	public MainSceneController() {
		mainMenuController = new MainMenuController();
		serverController = new NetworkServerController();
		clientController = new NetworkClientController();
		
		exceptionHandlers = Set.of(
				new StartServerExceptionHandler(),
				new ClientConnectExceptionHandler());
		
		Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> exceptionHandlers
				.forEach(handler -> handler.handleException(exception, MainSceneController.this)));
	}
	
	public void startServerMenuItemClicked() {
		mainMenuController.setMenuItemsIfServerStarted(true);
		statusLabel.setText("Status: server is running");
		serverController.startServer();
	}
	
	public void stopServerMenuItemClicked() {
		mainMenuController.setMenuItemsIfServerStarted(false);
		statusLabel.setText("Status: server was stopped; disconnected");
		serverController.stopServer();
	}
	
	public void joinServerMenuItemClicked() {
		mainMenuController.setMenuItemsIfClientConnected(true);
		statusLabel.setText("Status: connected to remote server");
		clientController.joinServer();
	}
	
	public void disconnectFromServerMenuItemClicked() {
		mainMenuController.setMenuItemsIfClientConnected(false);
		statusLabel.setText("Status: disconnected");
		clientController.disconnect();
	}
	
	public void quitMenuItemClicked() {
		Platform.exit();
	}
	
	public void setStatus(String statusMessage) {
//		Platform.runLater(() -> {
			statusLabel.setText(statusMessage);
//		});
	}
	
	public class MainMenuController {
		
		public void setMenuItemsIfServerStarted(boolean serverStarted) {
			if (serverStarted) {
//				Platform.runLater(() -> {
					startServerMenuItem.setDisable(true);
					stopServerMenuItem.setDisable(false);
					joinServerMenuItem.setDisable(true);
					disconnectServerMenuItem.setDisable(true);
//				});
			} else {
//				Platform.runLater(() -> {
					startServerMenuItem.setDisable(false);
					stopServerMenuItem.setDisable(true);
					joinServerMenuItem.setDisable(false);
					disconnectServerMenuItem.setDisable(true);
//				});
			}
		}
		
		public void setMenuItemsIfClientConnected(boolean clientConnected) {
			if (clientConnected) {
//				Platform.runLater(() -> {
					startServerMenuItem.setDisable(true);
					stopServerMenuItem.setDisable(true);
					joinServerMenuItem.setDisable(true);
					disconnectServerMenuItem.setDisable(false);
//				});
			} else {
//				Platform.runLater(() -> {
					startServerMenuItem.setDisable(false);
					stopServerMenuItem.setDisable(true);
					joinServerMenuItem.setDisable(false);
					disconnectServerMenuItem.setDisable(true);
//				});
			}
		}
	}
}
