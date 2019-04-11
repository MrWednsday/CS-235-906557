package screen;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import event.Event;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import library.Library;
import library.LibraryEvents;
import library.LibraryResources;
import resources.Book;
import resources.CopyData;
import resources.DVD;
import resources.Laptop;
import resources.Resource;
import resources.VideoGame;
import user.User;

/**
 * <h1>Event Screen.</h1>
 * <p>
 * This class represents the event screen. This is the screen seen after
 * pressing the events button.
 * </p>
 * 
 * @author Deyan Naydenov, Peter Daish, Dominik R Wojtasiewicz.
 * @version 1.0
 */
@SuppressWarnings("Duplicates")
public class EventScreen extends Screen implements Initializable {
	// TOP TOOL BAR - COMMON BETWEEN SCREENS - COPY FROM HERE - MAKE SURE THE IDs IN
	// SCENEBUILDER ARE OF THE SAME NAME AS THE VARIBLES HERE!!!!!
	@FXML
	private Text fineText;

	@FXML
	private ListView events;
	
	@FXML
	private ListView pastEvents;
	
	@FXML
	private TableView<EventTableData> eventTable;

	@FXML
	private TableView<EventAttendedTableData> eventTable1;
	
	@FXML
	private TableColumn<EventTableData, String> eventID;
	
	@FXML
	private TableColumn<EventTableData, String> title;
	
	@FXML
	private TableColumn<EventTableData, String> date;

	@FXML
	private TableColumn<EventTableData, String> time;

	@FXML
	private TableColumn<EventTableData, String> description;

	@FXML
	private TableColumn<EventTableData, String> eventAttendees;

	@FXML
	private TableColumn<EventTableData, String> attending;

	@FXML
	private TableColumn<EventAttendedTableData, String> eventID1;

	@FXML
	private TableColumn<EventAttendedTableData, String> name1;

	@FXML
	private TableColumn<EventAttendedTableData, String> date1;


	@Override
	/**
	 * This method changes the screen manager to the HomeScreen.
	 */
	public void start() {
		Pane root;
		try {
			root = FXMLLoader.load(getClass().getResource("fxml/EventScreen.fxml"));
			ScreenManager.setCurrentScene(new Scene(root, 1280, 720));
			// setupEvents();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Initialises the scene (populates tables etc).
	 */
	public void initialize(URL arg0, ResourceBundle arg1) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(Library.getCurrentLoggedInUser().getProfImage()));
		} catch (IOException e) {
			System.out.println("IOException"+e.getStackTrace());
		}

		User loggedInUser = Library.getCurrentLoggedInUser();
		try {
			updateEventTableData();
			updateEventTablePastData();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//populate tables
		eventID.setCellValueFactory(new PropertyValueFactory<EventTableData, String>("eventID"));
		title.setCellValueFactory(new PropertyValueFactory<EventTableData, String>("title"));
		date.setCellValueFactory(new PropertyValueFactory<EventTableData, String>("date"));
		time.setCellValueFactory(new PropertyValueFactory<EventTableData, String>("time"));
		eventAttendees.setCellValueFactory(new PropertyValueFactory<EventTableData, String>("eventAttendees"));
		description.setCellValueFactory(new PropertyValueFactory<EventTableData, String>("description"));
		attending.setCellValueFactory(new PropertyValueFactory<EventTableData, String>("attending"));

		eventID1.setCellValueFactory(new PropertyValueFactory<EventAttendedTableData, String>("eventID1"));
		name1.setCellValueFactory(new PropertyValueFactory<EventAttendedTableData, String>("title1"));
		date1.setCellValueFactory(new PropertyValueFactory<EventAttendedTableData, String>("date1"));

		if (Library.currentUserIsLibrarian()) {
			issueDeskBtn.setVisible(true);
		}
		userIcon.setImage(SwingFXUtils.toFXImage(img, null));
		usernameText.setText(loggedInUser.getUserName());
		
		eventTable.setOnMouseClicked((MouseEvent event) -> {
			//if user double clicked
			if (event.getClickCount() == 1 && eventTable.getSelectionModel().getSelectedItems().get(0) != null) {
				
				//check if event is full
				String eventID = eventTable.getSelectionModel().getSelectedItems().get(0).getEventID();
				Event currentEvent = LibraryEvents.getEvent(eventID);
				
				//if there are less people than the maximum attending, you can attend.
				if (currentEvent.getCurrentNumberOfAttending() < currentEvent.getMaxNumberOfAttending()) {
					System.out.println(eventID);
					
					//add user to event...
					loggedInUser.addNewEvent(eventID);
					//update table
					try {
						updateEventTableData();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			} else if (event.getClickCount() == 2 && eventTable.getSelectionModel().getSelectedItems().get(0) != null) {
				//if double clicked, delete event from user
				//check if event is full
				String eventID = eventTable.getSelectionModel().getSelectedItems().get(0).getEventID();
				Event currentEvent = LibraryEvents.getEvent(eventID);
				loggedInUser.removeEvent(eventID);
				
				try {
					updateEventTableData();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Updates the event table with information regarding all events.
	 * @throws ParseException for whether the date format of event can be parsed.
	 */
	private void updateEventTableData() throws ParseException {
		ArrayList<Event> listOfEvents = LibraryEvents.getAllEvents();

		ArrayList<String> userEvents = Library.getCurrentLoggedInUser().getAllEventsAttended();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		eventTable.getItems().clear();
		Date currentDate = sdf.parse(Library.getCurrentDate());
		Date eventDate;
		for (Event event : listOfEvents) {

			eventDate = sdf.parse(event.getDate());

			if(eventDate.after(currentDate)){
				String id = event.getEventID();
				String title = event.getTitle();
				String date = event.getDate();
				String time = event.getTime();
				String eventAttendees = Integer.toString(event.getCurrentNumberOfAttending());
				String description = event.getDescription();
				String attending = "No";


				if(event.getCurrentNumberOfAttending() == event.getMaxNumberOfAttending()){
					attending = "Full";
				}else{
					for(String s : userEvents){
						if(s.equals(event.getEventID())){
							attending = "Yes";
						}
					}
				}

				EventTableData etd = new EventTableData(id, title, date, time, eventAttendees, description, attending);

				eventTable.getItems().add(etd);
			}
		}
	}

	/**
	 * Updates past data table
	 * @throws ParseException if date is json is not in correct format dd/MM/yyyy
	 */
	private void updateEventTablePastData() throws ParseException {
		eventTable1.getItems().clear();

		ArrayList<String> userEvents = Library.getCurrentLoggedInUser().getAllEventsAttended();
		System.out.println("Current user is: " + Library.getCurrentLoggedInUser().getUserName());

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Date currentDate = sdf.parse(Library.getCurrentDate());
		Date eventDate;

		for(String s : userEvents){
			Event event = LibraryEvents.getEvent(s);
			eventDate = sdf.parse(event.getDate());
			if(currentDate.after(eventDate)){
				String id = event.getEventID();
				String title = event.getTitle();
				String date = event.getDate();
				System.out.println(id + " " + title + " " + date);
				EventAttendedTableData eatd = new EventAttendedTableData(id, title, date);
				eventTable1.getItems().add(eatd);
			}
		}

	}

	/**
	 * On enter search
	 * @param actionEvent event enter
	 */
	public void onEnter(ActionEvent actionEvent) {
		Library.setSearchStringText(searchBar.getText());
		ScreenManager.changeScreen(new SearchResultScreen());
	}
}
