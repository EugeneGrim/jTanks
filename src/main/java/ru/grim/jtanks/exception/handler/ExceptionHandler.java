package ru.grim.jtanks.exception.handler;

import ru.grim.jtanks.controller.MainSceneController;

public interface ExceptionHandler {
	
	public void handleException(Throwable e, MainSceneController controller);

}
