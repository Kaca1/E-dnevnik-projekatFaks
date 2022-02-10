package application;

import beauty.LoginW;
import engine.ConnectDB;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {

			(new LoginW()).start(primaryStage);

		} catch (Exception e) {
			e.printStackTrace();
		}
		;
	}

	public static void main(String[] args) {
		ConnectDB.connect();
		launch(args);
	}
}
