package ru.grim.jtanks.controller;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashSet;
import java.util.Set;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import ru.grim.jtanks.exception.handler.MainSceneExceptionHandler;
import ru.grim.jtanks.exception.handler.StartServerExceptionHandler;
import ru.grim.jtanks.exception.handler.StopServerExceptionHandler;
import ru.grim.jtanks.server.impl.NetworkServer;

public class MainSceneController {
	
	@FXML private Label serverStatusLabel;
	@FXML private MenuItem startServerMenuItem;
	@FXML private MenuItem stopServerMenuItem;
	
	private NetworkServer server;
	private Set<MainSceneExceptionHandler> exceptionHandlers;
	
	public MainSceneController() {
		exceptionHandlers = getExceptionHandlers();
		Thread.setDefaultUncaughtExceptionHandler(getMainSceneExceptionHandler());
	}
	
	public void startServerMenuItemClicked() {
		Thread serverThread = createNewServerAsDaemon();
		serverThread.start();
		setSceneControlsIfServerStarted();
	}
	
	public void stopServerMenuItemClicked() {
		server.stop();
	}

	private Thread createNewServerAsDaemon() {
		server = new NetworkServer();
		Thread serverThread = new Thread(server);
		serverThread.setDaemon(true);
		return serverThread;
	}

	private Set<MainSceneExceptionHandler> getExceptionHandlers() {
		Set<MainSceneExceptionHandler> handlers = new HashSet<>();
		handlers.add(new StartServerExceptionHandler());
		handlers.add(new StopServerExceptionHandler());
		return handlers;
	}

	private UncaughtExceptionHandler getMainSceneExceptionHandler() {
		return (thread, exception) 
				-> exceptionHandlers.forEach(handler -> handler.handleException(exception, MainSceneController.this));
	}
	
	private void setSceneControlsIfServerStarted() {
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
}
