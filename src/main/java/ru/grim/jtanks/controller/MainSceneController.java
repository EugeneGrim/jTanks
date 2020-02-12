package ru.grim.jtanks.controller;

import java.util.Set;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import ru.grim.jtanks.exception.handler.ExceptionHandler;
import ru.grim.jtanks.exception.handler.StartServerExceptionHandler;
import ru.grim.jtanks.exception.handler.StopServerExceptionHandler;

public class MainSceneController {
	
	public final MainMenuController mainMenuController;
	public final NetworkServerController serverController;
	public final NetworkClientController clientController;
	
	private final Set<ExceptionHandler> exceptionHandlers;
	
	@FXML private Label serverStatusLabel;
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
				new StopServerExceptionHandler());
		
		Thread.setDefaultUncaughtExceptionHandler((thread, exception) -> exceptionHandlers
				.forEach(handler -> handler.handleException(exception, MainSceneController.this)));
	}
	
	public void startServerMenuItemClicked() {
		mainMenuController.setMenuItemsIfServerStarted(true);
		serverController.startServer();
	}
	
	public void stopServerMenuItemClicked() {
		mainMenuController.setMenuItemsIfServerStarted(false);
		serverController.stopServer();
	}
	
	public void joinServerMenuItemClicked() {
		mainMenuController.setMenuItemsIfClientConnected(true);
		clientController.joinServer();
	}
	
	public void disconnectFromServerMenuItemClicked() {
		mainMenuController.setMenuItemsIfClientConnected(false);
		clientController.disconnect();
	}
	
	public void quitMenuItemClicked() {
		Platform.exit();
	}
	
	public class MainMenuController {
		
		public void setMenuItemsIfServerStarted(boolean serverStarted) {
			if (serverStarted) {
				Platform.runLater(() -> {
					startServerMenuItem.setDisable(true);
					stopServerMenuItem.setDisable(false);
					joinServerMenuItem.setDisable(true);
					disconnectServerMenuItem.setDisable(true);
					serverStatusLabel.setText("Status: server is running");
				});
			} else {
				Platform.runLater(() -> {
					startServerMenuItem.setDisable(false);
					stopServerMenuItem.setDisable(true);
					joinServerMenuItem.setDisable(false);
					disconnectServerMenuItem.setDisable(true);
					serverStatusLabel.setText("Status: server is not running");
				});
			}
		}
		
		public void setMenuItemsIfError(String errorMessage) {
			Platform.runLater(() -> {
				startServerMenuItem.setDisable(false);
				stopServerMenuItem.setDisable(true);
				joinServerMenuItem.setDisable(false);
				disconnectServerMenuItem.setDisable(true);
				serverStatusLabel.setText(errorMessage);
			});
		}
		
		public void setMenuItemsIfClientConnected(boolean clientConnected) {
			if (clientConnected) {
				Platform.runLater(() -> {
					startServerMenuItem.setDisable(true);
					stopServerMenuItem.setDisable(true);
					joinServerMenuItem.setDisable(true);
					disconnectServerMenuItem.setDisable(false);
				});
			} else {
				Platform.runLater(() -> {
					startServerMenuItem.setDisable(false);
					stopServerMenuItem.setDisable(true);
					joinServerMenuItem.setDisable(false);
					disconnectServerMenuItem.setDisable(true);
				});
			}
		}
	}
}
