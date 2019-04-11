package screen;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import library.Library;

/**
 * <h1>LoginScreen.</h1>
 * <p>This class models a screen used for a user to login to their library account.<p>
 * @author James Carter, Sam Jankinson, Ammar Almari
 * @version 1.0
 */
public class LoginScreen extends Screen {
	@FXML
	private Label statusLabel;
	
	@FXML
	private TextField usernameTextField;
	
	@FXML
	private Button loginBtn;

	@FXML
	/**
	 * This method handles login attempts.
	 * @param event
	 * The event which triggers a login - the pressing of the login button.
	 */
	private void login(Event event) {
		if (Library.checkForUser(usernameTextField.getText())) {
			Library.onLogin(usernameTextField.getText());
			ScreenManager.changeScreen(new HomeScreen());
		} else {
			statusLabel.setText("Username is invalid!");
			statusLabel.setTextFill(Color.RED);
		}
	}
	
	@FXML
	/**
	 * This methods allows user to attempt to login by pressing the "Enter" key.
	 * @param actionEvent
	 * The event to trigger a login - the pressing of the enter key.
	 */
	private void onEnter(ActionEvent actionEvent) {
		login(actionEvent);
	}

	@Override
	/**
	 * This method displays the scene LoginScreen to user.
	 */
	public void start() {
		Pane root;
		try {
			root = FXMLLoader.load(getClass().getResource("fxml/LoginScreen.fxml"));
			ScreenManager.setCurrentScene(new Scene(root, 1280, 720));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
