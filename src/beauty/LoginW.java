package beauty;

import abstractEntities.User;
import engine.CurrentUser;
import engine.Login;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginW extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			VBox root = new VBox(10);
			// ---------------------------------------------------------

			root.setPadding(new Insets(10, 10, 10, 10));
			HBox hbTop = new HBox(10);
			VBox vbLevi = new VBox(10);
			VBox vbDesni = new VBox(10);
			vbLevi.setPadding(new Insets(10, 0, 0, 10));
			vbDesni.setPadding(new Insets(10, 10, 0, 0));

			// KREIRANJE

			// dugmad
			Button btnLogIn = new Button("Log in");

			// label
			Label lblkorime = new Label("username: ");
			Label lblpassword = new Label("password: ");
			Label lblTekst = new Label("");
			lblTekst.setTextFill(Color.RED);

			// inputfield
			TextField inputField1 = new TextField();
			PasswordField inputField2 = new PasswordField();

			// DODAVANJE
			vbLevi.getChildren().addAll(lblkorime, lblpassword);
			vbDesni.getChildren().addAll(inputField1, inputField2);

			hbTop.getChildren().addAll(vbLevi, vbDesni);

			root.getChildren().addAll(hbTop, btnLogIn, lblTekst);

			btnLogIn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String username = inputField1.getText();
					String password = inputField2.getText();
					User user = Login.checkLog(username, password);

					if (user != null) {
						primaryStage.close();
						CurrentUser.setUser(user);
						try {
							if (CurrentUser.role.equals("profesor"))
								(new FirstW_Prof()).start(primaryStage);
							else if (CurrentUser.role.equals("ucenik"))
								(new FirstW_Ucenik()).start(primaryStage);
							else if (CurrentUser.role.equals("nepotpun_ucenik")) {
								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Information Dialog");
								alert.setHeaderText("Morate da imate ocjenu ili izostanak da bi pristupili sistemu.");

								alert.showAndWait();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else
						lblTekst.setText("Neuspesno logovanje!");

				}
			});

			// ------------------------------

			Scene scene = new Scene(root, 400, 200);

			primaryStage.setScene(scene);
			primaryStage.setTitle("Log in");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
