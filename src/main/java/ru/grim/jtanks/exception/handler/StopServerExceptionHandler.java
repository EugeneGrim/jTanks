package ru.grim.jtanks.exception.handler;

import ru.grim.jtanks.controller.MainSceneController;
import ru.grim.jtanks.exception.StopServerException;

public class StopServerExceptionHandler implements MainSceneExceptionHandler {

	@Override
	public void handleException(Throwable e, MainSceneController controller) {
		if (e instanceof StopServerException) {
			controller.serverController.setSceneControlsIfServerStoped();
		}
	}

}
