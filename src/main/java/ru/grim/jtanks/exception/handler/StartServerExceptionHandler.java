package ru.grim.jtanks.exception.handler;

import ru.grim.jtanks.controller.MainSceneController;
import ru.grim.jtanks.exception.StartServerException;

public class StartServerExceptionHandler implements MainSceneExceptionHandler {

	@Override
	public void handleException(Throwable e, MainSceneController controller) {
		if (e instanceof StartServerException) {
			controller.networkMenuController.setSceneControlsIfServerError();;
		}
	}

}
