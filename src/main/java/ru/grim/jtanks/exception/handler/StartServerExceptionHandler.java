package ru.grim.jtanks.exception.handler;

import javafx.application.Platform;
import ru.grim.jtanks.controller.MainSceneController;
import ru.grim.jtanks.exception.StartServerException;

public class StartServerExceptionHandler implements ExceptionHandler {

	@Override
	public void handleException(Throwable e, MainSceneController controller) {
		if (e instanceof StartServerException) {
			Platform.runLater(() -> {
				controller.mainMenuController.setMenuItemsIfServerStarted(false);
				controller.setStatus("Status: ERROR starting server");
			});
		}
	}

}
