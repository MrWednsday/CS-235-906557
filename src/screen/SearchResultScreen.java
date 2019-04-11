package screen;

import javafx.collections.ObservableMap;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import library.Library;
import resources.*;

import javax.imageio.ImageIO;

import io.ReadFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * <h1>SearchResultScreen.</h1>
 * <p>This class represents the search results screen, a list of Resources which
 * are shown as a result of searching for resources.</p>
 *
 * @author James Carter, Ammar Almari, Sam Jankinson.
 * @version 1.0
 */
public class SearchResultScreen extends Screen implements Initializable {
	@FXML
	private VBox ratingViewBox;
	private static ArrayList<String[]> rRatings = new ArrayList<>();
    @FXML
    private ComboBox<String> resourceTypeCB;
    @FXML
    private VBox resourcesVBox;
    @FXML
    private ImageView resourceThumbnailImage;
    @FXML
    private Label titleLbl;
    @FXML
    private Label uIDLbl;
    @FXML
    private Label yearLbl;
    @FXML
    private Label rs1Lbl;
    @FXML
    private Label rs2Lbl;
    @FXML
    private Label rs3Lbl;
    @FXML
    private Label rs4Lbl;
    @FXML
    private Label rs5Lbl;
    @FXML
    private TextField titleTf;
    @FXML
    private TextField uIDTf;
    @FXML
    private TextField yearTf;
    @FXML
    private TextField rs1Tf;
    @FXML
    private TextField rs2Tf;
    @FXML
    private TextField rs3Tf;
    @FXML
    private TextField rs4Tf;
    @FXML
    private TextField rs5Tf;
    @FXML
    private Button editResourceButton;
    @FXML
    private Button requestButton;
    @FXML
    private Button ratingsButton;
    @FXML
    private Button trailerButton;
    // private TextField[] textFields = {titleTf, uIDTf, yearTf, rs1Tf, rs2Tf,
    // rs3Tf, rs4Tf, rs5Tf};
    @FXML
    private ListView<String> copiesList;
    @FXML
    private ListView<String> copyHistoryList;
    private Resource selectedResource;

    @Override
    /**
     * This method handles the startup procedure for SearchResultScreen such as displaying the scene.
     */
    public void start() {
        Pane root;
        try {
            root = FXMLLoader.load(getClass().getResource("fxml/SearchResultScreen.fxml"));
            ScreenManager.setCurrentScene(new Scene(root, 1280, 720));
            // setupEvents();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @FXML
    /**
     * Handles the event when the search button is pressed to search the library.
     */
    protected void searchButton(Event event) {
        updateSearchResults();
    }

    @Override
    /**
     * Initialises the scene.
     * @param arg0
     * The location of the root object.
     * @param arg1
     * The resource used to localise the root object.
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(Library.getCurrentLoggedInUser().getProfImage()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Library.currentUserIsLibrarian()) {
            copyHistoryList.setVisible(true);
        } else {
            copyHistoryList.setVisible(false);
        }

        resourceTypeCB.getItems().setAll("Book", "DVD", "Laptop","Video Game");
        resourceTypeCB.setValue("Book");

        userIcon.setImage(SwingFXUtils.toFXImage(img, null));
        usernameText.setText(Library.getCurrentLoggedInUser().getUserName());
        updateSearchResults();

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            updateSearchResults();
        });

        copiesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateCopyHistoryList(newValue);
        });
        searchBar.setText(Library.getSearchScreenText());

        if (Library.currentUserIsLibrarian()) {
            issueDeskBtn.setVisible(true);
        }
        
        rRatings = ReadFile.readRatings();

    }

    /**
     * Show more information for a particular Resource.
     */
    public void showMoreDetails() {

    }

    /**
     * Event handling for requesting a Copy when none are available.
     */
    public void requestCopy() {

    }

    @FXML
    /**
     * Sets the current user to request a copy of the resource highlighted.
     */
    public void requestResource() {
        Library.requestResource(uIDTf.getText());
    }

    @FXML
    /**
     * Opens rating screen for the resource highlighted on the search results screen.
     */
    public void viewRatings() {
    	RatingScreen.setResource(uIDTf.getText(), titleTf.getText());
        ScreenManager.changeScreen(new RatingScreen());
        System.out.println(uIDTf.getText());
    }

    /**
     * Updates the borrow history associated with a copy of a resource.
     *
     * @param newValue The full id of the the copy including resource id.
     */
    private void updateCopyHistoryList(String newValue) {
        if (newValue != null && newValue != "") {
            String[] ids = newValue.split(":")[2].split("-");

            CopyData c = selectedResource.getArrayListOfCopies().get(Integer.parseInt(ids[1]));

            copyHistoryList.getItems().clear();

            for (BorrowHistoryData borrowHistory : c.getBorrowHistory()) {
                copyHistoryList.getItems().add(borrowHistory.toString());
            }
        }
    }

    @FXML
    /**
     * Updates the search results when queried.
     */
    private void updateSearchResults() {
        // Empty the current search results & ratings
        resourcesVBox.getChildren().clear();

        // Check the search bar
        String searchString = searchBar.getText();
        String resourceType = resourceTypeCB.getValue();
        List resources = null;

        switch (resourceType) {
            case "Book":
                resources = Library.getAllBooks();
                trailerButton.setVisible(false);
                break;
            case "DVD":
                resources = Library.getAllDVD();
                trailerButton.setVisible(true);
                break;
            case "Laptop":
                resources = Library.getAllLaptops();
                trailerButton.setVisible(false);
                break;
            case "Video Game":
            	resources = Library.getAllVideoGames();
            	trailerButton.setVisible(true);
            default:
                break;
        }

        for (Object r : resources) {
            Resource rs = (Resource) r;
            if (rs.toSingleString().toLowerCase().contains(searchString.toLowerCase())) {
                resourcesVBox.getChildren().add(createResourceContainer(rs));
            }
        }

        // Fetch JavaFX Vbox container to get Resource id of first resource in the list
        HBox firstResource = (HBox) resourcesVBox.getChildren().get(0);
        VBox resourceDetails = (VBox) firstResource.getChildren().get(1);
        Text textId = (Text) resourceDetails.getChildren().get(1);
        String id = textId.getText();
        id = id.replace("Unique ID: ", "");
        // Set resource details to first resource in the list
        Resource r = Library.getResource(id);
        updateResourceDetails(r);
    }

    @FXML
    /**
     * Event handling for a Librarian to edit a Resource.
     */
    private void editResource() {
        String resourceType = resourceTypeCB.getSelectionModel().getSelectedItem();

        switch (resourceType) {
            case "Book":
                ArrayList<String> languages;
                String languageString = rs5Tf.getText();
                if (languageString.equals("")) {
                    languages = null;
                } else {
                    //Split languages input into ArratList
                    String[] languageArray = languageString.split(", ");
                    languages = new ArrayList<>(Arrays.asList(languageArray));
                }
                Library.editBook(uIDTf.getText(), titleTf.getText(), yearTf.getText(), rs1Tf.getText(),
                        rs2Tf.getText(), rs3Tf.getText(), rs4Tf.getText(), languages);
                break;
            case "DVD":
                ArrayList<String> subs;
                String subsString = rs4Tf.getText();
                if (subsString.equals("")) {
                    subs = null;
                } else {
                    //Split subtitles input into ArrayList
                    String[] subsArray = subsString.split(", ");
                    subs = new ArrayList<>(Arrays.asList(subsArray));
                }
                Library.editDVD(uIDTf.getText(), titleTf.getText(), yearTf.getText(), rs1Tf.getText(), rs2Tf.getText(),
                        rs3Tf.getText(), subs);
                break;
            case "Laptop":
                Library.editLaptop(uIDTf.getText(), titleTf.getText(), yearTf.getText(), rs1Tf.getText(),
                        rs2Tf.getText(), rs3Tf.getText());
                break;
            case "Video Game":
                ArrayList<String> languages1;
                String languageString1 = rs5Tf.getText();
                if (languageString1.equals("")) {
                    languages1 = null;
                } else {
                    //Split languages input into ArratList
                    String[] languageArray = languageString1.split(", ");
                    languages1 = new ArrayList<>(Arrays.asList(languageArray));
                }
                Library.editVideoGame(uIDTf.getText(), titleTf.getText(), yearTf.getText(), rs1Tf.getText(),
                        rs2Tf.getText(), rs4Tf.getText(), rs3Tf.getText(), languages1);
            default:
                break;
        }
    }

    /**
     * Creates the JavaFX container for a resource.
     *
     * @param r The resource to be contained.
     * @return container
     * The container for the resource on screen.
     */
    private HBox createResourceContainer(Resource r) {
        ImageView imgV = createImageViewForResource(r);

        Text title = new Text("Title:" + r.getTitle());
        Text uniqueID = new Text("Unique ID: " + r.getUniqueID());
        Text year = new Text("Year: " + r.getYear());
        VBox details = new VBox(title, uniqueID, year);

        HBox container = new HBox(imgV, details);
        container.setOnMouseEntered(mouse -> {
            updateResourceDetails(r);
        });

        title.setWrappingWidth(container.getWidth() - imgV.getFitWidth());
        uniqueID.setWrappingWidth(container.getWidth() - imgV.getFitWidth());
        return container;
    }

    /**
     * Updates the resources details.
     *
     * @param r The current resource which needs updating selected by the user.
     */
    private void updateResourceDetails(Resource r) {
        selectedResource = r;
        resourceThumbnailImage.setImage(getResourceImage(r));
        titleTf.setText(r.getTitle());
        uIDTf.setText(r.getUniqueID());
        yearTf.setText(r.getYear());

        String resourceType = resourceTypeCB.getValue();

        rs4Lbl.setVisible(true);
        rs4Tf.setVisible(true);
        rs5Lbl.setVisible(true);
        rs5Tf.setVisible(true);
        ratingsButton.setVisible(true);

        TextField[] textFields = {titleTf, yearTf, rs1Tf, rs2Tf, rs3Tf, rs4Tf, rs5Tf};

        if (Library.currentUserIsLibrarian()) {
            for (TextField tf : textFields) {
                tf.setEditable(true);
                editResourceButton.setVisible(true);
            }
        } else {
            for (TextField tf : textFields) {
                tf.setEditable(false);
            }
        }

        copiesList.getItems().clear();
        requestButton.setDisable(false);

        for (CopyData copy : r.getArrayListOfCopies()) {
            copiesList.getItems().add("Copy: " + r.getUniqueID() + "-" + copy.getId() + "- Available: "
                    + String.valueOf(copy.isAvailable()));
        }

//		if (!isAvailable) {
//			requestButton.setDisable(false);
//		}

        switch (resourceType) {
            case "Book":
                Book b = (Book) r;
                rs1Lbl.setText("Author: ");
                rs1Tf.setText(b.getAuthor());
                rs2Lbl.setText("Publisher: ");
                rs2Tf.setText(b.getPublisher());
                rs3Lbl.setText("Genre: ");
                rs3Tf.setText(b.getGenre());
                rs4Lbl.setText("ISBN: ");
                rs4Tf.setText(b.getIsbn());

                rs5Lbl.setText("Languages: ");

                rs5Tf.setText(b.getLanguages().get(0));
                ArrayList<String> languages = b.getLanguages();
                // languages.remove(0);
                for (int i = 1; i < languages.size(); i++) {
                    rs5Tf.setText(rs5Tf.getText() + ", " + languages.get(i));
                }

                break;
            case "DVD":
                DVD d = (DVD) r;
                rs1Lbl.setText("Director: ");
                rs1Tf.setText(d.getDirector());
                rs2Lbl.setText("Runtime: ");
                rs2Tf.setText(d.getRuntime());
                rs3Lbl.setText("Language: ");
                rs3Tf.setText(d.getLanguage());

                rs4Lbl.setText("Sub-Languages: ");

                if (d.getSubLang().isEmpty()) {
                    rs4Tf.setText("N/A");
                } else {
                    rs4Tf.setText(d.getSubLang().get(0));
                    languages = d.getSubLang();
                    languages.remove(0);
                    for (String language : languages) {
                        rs4Tf.setText(rs4Tf.getText() + ", " + language);
                    }
                }

                rs5Tf.setVisible(false);
                rs5Lbl.setVisible(false);

                break;
            case "Laptop":
                Laptop l = (Laptop) r;
                rs1Lbl.setText("Manufacturer: ");
                rs1Tf.setText(l.getManufacturer());
                rs2Lbl.setText("Model: ");
                rs2Tf.setText(l.getModel());
                rs3Lbl.setText("Operating System: ");
                rs3Tf.setText(l.getOperatingSys());

                rs4Lbl.setVisible(false);
                rs4Tf.setVisible(false);
                rs5Lbl.setVisible(false);
                rs5Tf.setVisible(false);
                break;
            case "Video Game":
                VideoGame g = (VideoGame) r;
                rs1Lbl.setText("Publisher: ");
                rs1Tf.setText(g.getPublisher());
                rs2Lbl.setText("Genre: ");
                rs2Tf.setText(g.getGenre());
                rs3Lbl.setText("Certificate Rating: ");
                rs3Tf.setText(g.getCertificateRating());
                rs4Lbl.setText("Multiplayer Support: ");
                rs4Tf.setText(g.getMultiplayerSupport());

                rs5Lbl.setVisible(false);
                rs5Tf.setVisible(false);

                if (g.getLanguages().isEmpty()) {
                    rs5Tf.setText("N/A");
                } else {
                    rs5Tf.setText(g.getLanguages().get(0));
                    languages = g.getLanguages();
                    languages.remove(0);
                    for (String language : languages) {
                        rs5Tf.setText(rs5Tf.getText() + ", " + language);
                    }
                }

                break;
            default:
                break;
        }
        //Update ratings when new resource selected.
        ratingViewBox.getChildren().clear();
        for(String[] rating : rRatings) {
			if(rating[0].equals(uIDTf.getText())) {
				ratingViewBox.getChildren().add(createRatingContainer(rating));
			}
		}
    }

    /**
     * Creates a JavaFX Image view for the resource based off of its referenced filepath.
     *
     * @param r The resource to create an image view of.
     * @return imgV
     * The created image view of the avatar of a resource.
     */
    private ImageView createImageViewForResource(Resource r) {
        System.out.println(r.getThumbnailImageRef());
        ImageView imgV = new ImageView();
        imgV.setFitWidth(101);
        imgV.setFitHeight(150);

        imgV.setImage(getResourceImage(r));

        return imgV;
    }

    /**
     * Creates a Writable Image of the avatar read in from filepath on disk.
     *
     * @param r The resource to create an image of.
     * @return The writable image of the resources avatar.
     */
    private WritableImage getResourceImage(Resource r) {
        try {
            return SwingFXUtils.toFXImage(ImageIO.read(new File(r.getThumbnailImageRef())), null);
        } catch (IOException e) {
            System.out.println("Could not load Image for Resource: " + r.getUniqueID());
        }
        return null;
    }

    /**
     * Event handler to watch a DVD/Video Game trailer.
     */
    @FXML
    private void watchTrailer() {
        Resource r = Library.getResource(uIDTf.getText());
        TrailerScreen ts = new TrailerScreen();
        ts.getTrailer(r);
    }

    /**
     * Crates rating container
     * @param rating String[] of ratings
     * @return HBox
     */
    private HBox createRatingContainer(String[] rating) {
        Text username = new Text("Username: " + rating[3]);
        Text ratingText = new Text("Rating: " + rating[2]);
        TextArea message = new TextArea("Review: " + rating[1]);
        message.autosize();
        message.setEditable(false);
        VBox details = new VBox(username, ratingText, message);

        HBox container = new HBox(details);

        username.setWrappingWidth(container.getWidth());
        ratingText.setWrappingWidth(container.getWidth());
        message.setMaxHeight(40);
        return container;
    }
}
