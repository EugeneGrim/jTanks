package ru.grim.jtanks;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JTanks extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		loadMainScene(primaryStage);
		primaryStage.setTitle("jTanks");
		primaryStage.setMinWidth(640);
		primaryStage.setMinHeight(480);
		primaryStage.show();
	}

	private void loadMainScene(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		URL xmlUrl = getClass().getResource("/mainScene.fxml");
		loader.setLocation(xmlUrl);
		Parent root = loader.load();
		primaryStage.setScene(new Scene(root));
	}

	public static void main(String[] args) {
		launch(args);
	}

}
