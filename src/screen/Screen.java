package screen;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import library.Library;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <h1>Screen.</h1>
 * <p>Screen is an abstract outline of how a Screen functions so that the ScreenManager can use them.</p>
 * @author James Carter, Etienne Badoche, Deyan Naydenov 
 * @version 1.0
 * @since 11/11/2018
 */
public abstract class Screen {
    protected List<Node> components;
    @FXML
	protected TextField searchBar;
	@FXML
	protected Button searchBtn;
	@FXML
	protected ImageView userIcon;
	@FXML
	protected Text usernameText;
	@FXML
	protected Button logoutBtn;
	@FXML
	protected Button homeBtn;
	@FXML
	protected Button accountBtn;
	@FXML
	protected Button issueDeskBtn;
	@FXML
	protected Button drawAppBtn;
	@FXML
	private Button statsBtn;
	@FXML
	private Button eventsButton;
	
    public abstract void start();

    /**
     * Changes the screen from the current to the specified one.
     * @param screen
     * Screen being switched to
     */
    public void switchScreen(Screen screen) {
        ScreenManager.changeScreen(screen);
    }

    /**
     * Sets the display back to the previous Screen
     */
    public void prevScreen() {
        ScreenManager.previousScreen();
    }

    /**
     * Gets the list of components on the Screen (Everything that needs to be displayed/have functionality)
     * @return List of the components on the Screen
     */
    public List<Node> getComponents() {
        return components;
    }

    /**
     * Logs the current user out of the system and returns to login screen.
     */
    protected void logout() {
    	Library.setLoggedInUser(null);
    	ScreenManager.changeScreen(new LoginScreen());
    }
    
    @FXML
    /**
     * Opens the DrawApp
     * @param event
     * The event of pressing the "DrawApp" button.
     */
	protected void drawAppButton(Event event) {
		ScreenManager.changeScreen(new DrawApp());
	}
	
	@FXML
	/**
	 * Searches the library's database based off full or partial information provided in search bar.
	 * @param event
	 * The event of pressing the "search" button.
	 */
	protected void searchButton(Event event) {
    	Library.setSearchStringText(searchBar.getText());
		ScreenManager.changeScreen(new SearchResultScreen());
	}
	
	@FXML
	/**
	 * Allows for the user to logout when the logout button is pressed.
	 * @param event
	 * The event of pressing the logout button.
	 */
	protected void logoutButton(Event event) {
		
		logout();
	}

	@FXML
	/**
	 * Changes current screen to IssueDeskScreen when button pressed.
	 * @param event
	 * The event of pressing the IssueDesk button.
	 */
	protected void issueDeskButton(Event event) {
		ScreenManager.changeScreen(new IssueDeskScreen());
	}
	
	@FXML
	/**
	 * Changes current screen to AccountScreen when button pressed.
	 * @param event
	 * The event of pressing the AccountScreen button.
	 */
	protected void accountDeskButton(Event event) {
		ScreenManager.changeScreen(new AccountScreen());
	}
	
	@FXML
	/**
	 * Changes current screen to HomeScreen when button pressed.
	 * @param event
	 * The event of pressing the Home button.
	 */
    protected void homeButton(Event event) {
        ScreenManager.changeScreen(new HomeScreen());
    }

    @FXML
	/**
	 * Changes current screen to stats screen
	 */
	protected void statsButton(Event actionEvent) {
		ScreenManager.changeScreen(new StatsScreen());
	}

	/**
	 * Changes current screen to events screen
	 * @param event the event of pressing event button
	 */
	@FXML
	protected void goToEventsButton(Event event){
    	ScreenManager.changeScreen(new EventScreen());
	}
}
