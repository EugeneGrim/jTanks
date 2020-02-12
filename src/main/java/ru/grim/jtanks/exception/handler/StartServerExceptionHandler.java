package ru.grim.jtanks.exception.handler;

import ru.grim.jtanks.controller.MainSceneController;
import ru.grim.jtanks.exception.StartServerException;

public class StartServerExceptionHandler implements ExceptionHandler {

	@Override
	public void handleException(Throwable e, MainSceneController controller) {
		if (e instanceof StartServerException) {
			controller.mainMenuController.setMenuItemsIfError("Server status: ERROR starting server");
		}
	}

}
