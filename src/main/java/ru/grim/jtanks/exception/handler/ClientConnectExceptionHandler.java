package ru.grim.jtanks.exception.handler;

import javafx.application.Platform;
import ru.grim.jtanks.controller.MainSceneController;
import ru.grim.jtanks.exception.ConnectToServerException;

public class ClientConnectExceptionHandler implements ExceptionHandler {

	@Override
	public void handleException(Throwable e, MainSceneController controller) {
		if (e instanceof ConnectToServerException) {
			System.out.println(e.getMessage());
			Platform.runLater(() -> {
				controller.mainMenuController.setMenuItemsIfClientConnected(false);
				controller.setStatus("Status: ERROR connecting to server");
			});
		}
	}

}
