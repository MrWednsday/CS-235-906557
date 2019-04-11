package screen;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;

import event.Event;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import library.Library;
import library.LibraryEvents;
import resources.CopyData;
import resources.Resource;

/**
 * <h1>IssueDeskScreen.</h1>
 * <p>This class represents the Issue Desk, a screen only available to Librarians to authorise payments and loans, as well
 * as create new users and resources.</p>
 * @author Etienne Badoche, Ammar Alamri, Deyan Naydenov
 * @version 1.0
 */
public class IssueDeskScreen extends Screen implements Initializable {
    @FXML
    private TextField loanUsername;
    @FXML
    private TextField loanCopyID;
    @FXML
    private Label loanUserError;
    @FXML
    private Label loanCopyError;
    @FXML
    private Label loanSuccess;
    @FXML
    private Label outstandingFineMsg;
    @FXML
    private Label overdueCopyMsg;
    @FXML
    private Label unavailableCopyMsg;
    @FXML
    private TextField returnUsername;
    @FXML
    private ListView userBorrowList;
    @FXML
    private Label returnSearchError;
    @FXML
    private Label returnSuccess;
    @FXML
    private TextField paymentUsername;
    @FXML
    private TextField paymentAmount;
    @FXML
    private Label paymentUserError;
    @FXML
    private Label paymentAmountError;
    @FXML
    private Label paymentSuccess;
    @FXML
    private Text paymentSearchBalance;
    @FXML
    private TextField userUsername;
    @FXML
    private TextField userFirstName;
    @FXML
    private TextField userLastName;
    @FXML
    private TextField userMobile;
    @FXML
    private TextField userAddr1;
    @FXML
    private TextField userAddr2;
    @FXML
    private TextField userPstCd;
    @FXML
    private TextField userTown;
    @FXML
    private Label userUsernameError;
    @FXML
    private Label userError;
    @FXML
    private Label userSuccess;
    @FXML
    private ImageView userAvatar;
    @FXML
    private Text userAvatarName;
    @FXML
    private TextField bookTitle;
    @FXML
    private TextField bookAuthor;
    @FXML
    private TextField bookYear;
    @FXML
    private TextField bookPublisher;
    @FXML
    private TextField bookGenre;
    @FXML
    private TextField bookISBN;
    @FXML
    private TextField bookLanguage;
    @FXML
    private TextField bookNumCopies;
    @FXML
    private TextField emailTextField;
    @FXML
    private Label bookSuccess;
    @FXML
    private Label bookError;
    @FXML
    private Label bookCopiesError;
    @FXML
    private Label toManyResourcesError;
    @FXML
    private Text bookImgName;
    @FXML
    private ImageView bookImg;
    @FXML
    private TextField book1Day;
    @FXML
    private TextField book1Week;
    @FXML
    private TextField book2Weeks;
    @FXML
    private TextField book4Weeks;
    @FXML
    private Label bookDurationError;
    @FXML
    private ListView bookOverdueList;
    @FXML
    private TextField dvdTitle;
    @FXML
    private TextField dvdDirector;
    @FXML
    private TextField dvdYear;
    @FXML
    private TextField dvdRuntime;
    @FXML
    private TextField dvdLanguage;
    @FXML
    private TextField dvdSubs;
    @FXML
    private TextField dvdNumCopies;
    @FXML
    private TextField dvd1Day;
    @FXML
    private TextField dvd1Week;
    @FXML
    private TextField dvd2Weeks;
    @FXML
    private TextField dvd4Weeks;
    @FXML
    private Label dvdCopiesError;
    @FXML
    private Label dvdError;
    @FXML
    private Label dvdSuccess;
    @FXML
    private Label dvdDurationError;
    @FXML
    private Text dvdImgName;
    @FXML
    private ImageView dvdImg;
    @FXML
    private ListView dvdOverdueList;
    @FXML
    private TextField laptopTitle;
    @FXML
    private TextField laptopYear;
    @FXML
    private TextField laptopManuf;
    @FXML
    private TextField laptopModel;
    @FXML
    private TextField laptopOS;
    @FXML
    private TextField laptopNumCopies;
    @FXML
    private TextField laptop1Day;
    @FXML
    private TextField laptop1Week;
    @FXML
    private TextField laptop2Weeks;
    @FXML
    private TextField laptop4Weeks;
    @FXML
    private Label laptopError;
    @FXML
    private Label laptopSuccess;
    @FXML
    private Label laptopCopiesError;
    @FXML
    private Label laptopDurationError;
    @FXML
    private Text laptopImgName;
    @FXML
    private ImageView laptopImg;
    @FXML
    private ListView laptopOverdueList;
    
    
    //videogames
    @FXML
    private TextField videoGameTitle;
    @FXML
    private TextField videoGameCertificateRating;
    @FXML
    private TextField videoGameYear;
    @FXML
    private TextField videoGameDateAdded;
    @FXML
    private TextField videoGamePublisher;
    @FXML
    private TextField videoGameGenre;
    @FXML
    private TextField videoGameMultiplayerSupport;
    @FXML
    private TextField videoGameLanguage;
    @FXML
    private TextField videoGameNumCopies;
    @FXML
    private Label videoGameSuccess;
    @FXML
    private Label videoGameError;
    @FXML
    private Label videoGameCopiesError;
    @FXML
    private Text videoGameImgName;
    @FXML
    private ImageView videoGameImg;
    @FXML
    private TextField videoGame1Day;
    @FXML
    private TextField videoGame1Week;
    @FXML
    private TextField videoGame2Weeks;
    @FXML
    private TextField videoGame4Weeks;
    @FXML
    private Label videoGameDurationError;
    @FXML
    private ListView videoGameOverdueList;


    //Events
    @FXML
    private TextField eventName;
    @FXML
    private DatePicker date;
    @FXML
    private TextField time;
    @FXML
    private TextField description;
    @FXML
    private TextField maxNumberOfSpaces;
    @FXML
    private Label eventError;
    @FXML
    private Label eventSuccess;

    @Override
    /**
     * Sets IssueDesk as the current scene.
     */
    public void start() {
        Pane root;
        try {
            root = FXMLLoader.load(getClass().getResource("fxml/IssueDesk.fxml"));
            ScreenManager.setCurrentScene(new Scene(root, 1280, 720));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    /**
     * Called to initialize a controller after its root element has been completely processed.
     * @param arg0 The location used to resolve relative paths for the root object,
     *            or null if the location is not known.
     * @param arg1 The resources used to localize the root object, or null if the root object was not localized.
     */
    public void initialize(URL arg0, ResourceBundle arg1) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(Library.getCurrentLoggedInUser().getProfImage()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Library.currentUserIsLibrarian()) {
            issueDeskBtn.setVisible(true);
        }

        userIcon.setImage(SwingFXUtils.toFXImage(img, null));
        usernameText.setText(Library.getCurrentLoggedInUser().getUserName());

        //Add overdue copies to respective resource lists
        ArrayList<String> allOverdueIDs = Library.findAllOverdue();
        for (String copy : allOverdueIDs) {
            String type = copy.split(":")[0];
            String rID = copy.split("-")[0];
            String cID = copy.split("-")[1];
            String user = Library.getResource(rID).getCopyInfo(Integer.parseInt(cID)).getCurrentInfo().getUserID();
            switch (type) {
                case "Book":
                    bookOverdueList.getItems().add(copy + " - " + user);
                    break;
                case "DVD":
                    dvdOverdueList.getItems().add(copy + " - " + user);
                    break;
                case "VideoGame":
                    videoGameOverdueList.getItems().add(copy + " - " + user);
                    break;
                case "Laptop":
                    laptopOverdueList.getItems().add(copy + " - " + user);
                    break;
                default:
                    break;
            }
        }
    }


    @FXML
    /**
     * Event handling to process payments.
     */
    private void paymentButton() {
        String user = paymentUsername.getText();

        //Reset all error/success labels
        paymentAmountError.setVisible(false);
        paymentUserError.setVisible(false);
        paymentSuccess.setVisible(false);

        //Check Library if user exists
        if (Library.checkForUser(user)) {
            //User must not be current user
            if (!Library.getUser(user).equals(Library.getCurrentLoggedInUser())) {
                try {
                    int balance = Integer.parseInt(paymentAmount.getText());
                    Library.subtractBalance(balance, paymentUsername.getText());
                    paymentSuccess.setVisible(true);
                    //Exceptions thrown if negative amount or amount more than account balance
                } catch (IllegalArgumentException ex) {
                    paymentAmountError.setVisible(true);
                }
            } else {
                paymentUserError.setVisible(true);
            }
        } else {
            paymentUserError.setVisible(true);
        }
    }


    @FXML
    /**
     * Event handling to let a Librarian get a user's current balance.
     */
    private void paymentSearchButton() {
        String user = paymentUsername.getText();

        //Reset error label
        paymentUserError.setVisible(false);

        //Check if user exists
        if (Library.checkForUser(user)) {
            String balance = Library.getUser(user).getAccountBalanceString();
            paymentSearchBalance.setText("Current balance: " + balance);
            paymentSearchBalance.setVisible(true);
        } else {
            paymentUserError.setVisible(true);
        }
    }


    @FXML
    @SuppressWarnings("Duplicates")
    /**
     * Event handling to process loans.
     */
    private void loanButton() {
        String user = loanUsername.getText();
        String id = loanCopyID.getText();

        //Reset all error/success labels
        loanUserError.setVisible(false);
        loanCopyError.setVisible(false);
        loanSuccess.setVisible(false);
        outstandingFineMsg.setVisible(false);
        overdueCopyMsg.setVisible(false);
        toManyResourcesError.setVisible(false);


        //Check Library if user exists
        if (Library.checkForUser(user)) {
            //User must not be current user
            if (!Library.getUser(user).equals(Library.getCurrentLoggedInUser())) {
                //Check if user has no outstanding balance
                if (Library.getUser(user).getAccountBalanceDouble() == 0) {
                    //Check user has no overdue copies
                    if (Library.checkForOverDue(user).isEmpty()) {
                        //Check if Resource ID is valid
                        if (Library.getResource(id.split("-")[0]) != null) {
                            Resource r = Library.getResource(id.split("-")[0]);
                            CopyData copy = r.getArrayListOfCopies().get(Integer.parseInt(id.split("-")[1]));
                            if (Library.getUser(user).canBorrow(id)){
                                if (copy.isAvailable()) {
                                    Library.loanResource(user, id);
                                    loanSuccess.setVisible(true);
                                } else if (copy.isReserved()) {
                                    if (copy.getReservedUser().equals(user)) {
                                        Library.loanResource(user, id);
                                        loanSuccess.setVisible(true);
                                    } else {
                                    loanUserError.setVisible(true);
                                }
                            } else {
                                    unavailableCopyMsg.setVisible(true);
                                }
                            } else {
                                toManyResourcesError.setVisible(true);
                            }
                            }else {
                            loanCopyError.setVisible(true);
                        }
                    } else {
                        overdueCopyMsg.setVisible(true);
                    }
                } else {
                    outstandingFineMsg.setVisible(true);
                }
            } else {
                loanUserError.setVisible(true);
            }
        } else {
            loanUserError.setVisible(true);
        }
    }


    @FXML
    /**
     * Event handling to return a searched user's currently borrowed items.
     */
    private void returnSearchButton() {
        String user = returnUsername.getText();

        //Reset all error/success labels
        returnSearchError.setVisible(false);
        returnSuccess.setVisible(false);

        //Empty list view
        userBorrowList.getItems().clear();
        //Check Library if user exists
        if (Library.checkForUser(user)) {
            //User must not be current user
            if (!Library.getUser(user).equals(Library.getCurrentLoggedInUser())) {
                //Get list of borrowed copies
                ArrayList<String> borrowList = Library.getUser(user).getCurrentlyBorrowedResources();
                //Check for overdue copies
                for (String item : borrowList) {
                    for (String copy : Library.checkForOverDue(user)) {
                        if (item.equals(copy)) {
                            item += " (OVERDUE)";
                        }
                    }
                    userBorrowList.getItems().add(item);
                }
            } else {
                returnSearchError.setVisible(true);
            }
        } else {
            returnSearchError.setVisible(true);
        }
    }
    
    @FXML
    /**
     * Event handling to process returns.
     */
    private void returnButton() {
        String user = returnUsername.getText();
        int selectedIdx = userBorrowList.getSelectionModel().getSelectedIndex();

        //Reset all error/success labels
        returnSuccess.setVisible(false);
        outstandingFineMsg.setVisible(false);

        if (selectedIdx != -1) {
            String copy = userBorrowList.getSelectionModel().getSelectedItem().toString();
            String id = copy.split(" ")[0];
            Library.returnResource(user, id);
            userBorrowList.getItems().remove(selectedIdx);
            returnSuccess.setVisible(true);
        }
    }

    @FXML
    @SuppressWarnings("Duplicates")
    /**
     * Event handling to create a new User.
     */
    private void createUserButton() {
        String username = userUsername.getText();
        String firstName = userFirstName.getText();
        String lastName = userLastName.getText();
        String mobileNum = userMobile.getText();
        String address1 = userAddr1.getText();
        String address2 = userAddr2.getText();
        String postCode = userPstCd.getText();
        String town = userTown.getText();
        String avatar = userAvatarName.getText();
        String email = emailTextField.getText();

        //Reset all error/success labels
        userUsernameError.setVisible(false);
        userError.setVisible(false);
        userSuccess.setVisible(false);

        //Set avatar to default if not selected
        if (avatar.equals("")) {
            avatar = "default_image_1.png";
        }

        //Check if username not already used
        if (!Library.checkForUser(username)) {
            //Check all required fields have inputs
            if (username.equals("") || firstName.equals("") || lastName.equals("") || mobileNum.equals("")
                || address1.equals("") || postCode.equals("") || town.equals("")) {
                userError.setVisible(true);
            } else {
                Library.addUser(username, firstName, lastName, mobileNum, address1, address2, postCode, town,
                        0, "./data/images/default/" + avatar, email);
                userSuccess.setVisible(true);
            }
        } else {
            userUsernameError.setVisible(true);
        }
    }

    @FXML
    /**
     * Event handling to let a user choose their avatar.
     */
    private void userAvatarButton() {
        try {
            File selectedFile = getImageFile("default");
            userAvatarName.setText(selectedFile.getName());
            setImage(userAvatar, selectedFile);
        } catch (NullPointerException ex) {
            System.out.println("No book image file selected");
        }
    }
    
    @FXML
    @SuppressWarnings("Duplicates")
    /**
     * Event handling to create a new Book.
     */
    private void createBookButton() {
    	Date date = Calendar.getInstance().getTime();
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	String dateAdded = dateFormat.format(date);
        String title = bookTitle.getText();
        String author = bookAuthor.getText();
        String year = bookYear.getText();
        String publisher = bookPublisher.getText();
        String genre = bookGenre.getText();
        String isbn = bookISBN.getText();
        String languageString = bookLanguage.getText();
        ArrayList<String> languages;
        String imageName = bookImgName.getText();

        //Reset error/success labels
        bookSuccess.setVisible(false);
        bookError.setVisible(false);
        bookCopiesError.setVisible(false);
        bookDurationError.setVisible(false);

        //Check if required fields have input
        if (dateAdded.equals("") || title.equals("") || author.equals("") || year.equals("") || publisher.equals("") || imageName.equals("")) {
            bookError.setVisible(true);
        } else {
            //Set optional fields to null if empty
            if (genre.equals("")) {
                genre = null;
            }
            if (isbn.equals("")) {
                isbn = null;
            }
            if (languageString.equals("")) {
                languages = null;
            } else {
                //Split language input into ArrayList
                String[] languageArray = languageString.split(", ");
                languages = new ArrayList<>(Arrays.asList(languageArray));
            }
            int numCopies;
            int num1Day;
            int num1Week;
            int num2Weeks;
            int num4Weeks;
            try {
                //Add the book to the Library
                String image = "./data/images/book/" + imageName;
                if (bookNumCopies.getText().equals("")) {
                    numCopies = 0;
                } else {
                    numCopies = Integer.parseInt(bookNumCopies.getText());
                }
                if (book1Day.getText().equals("")) {
                    num1Day = 0;
                } else {
                    num1Day = Integer.parseInt(book1Day.getText());
                }
                if (book1Week.getText().equals("")) {
                    num1Week = 0;
                } else {
                    num1Week = Integer.parseInt(book1Week.getText());
                }
                if (book2Weeks.getText().equals("")) {
                    num2Weeks = 0;
                } else {
                    num2Weeks = Integer.parseInt(book2Weeks.getText());
                }
                if (book4Weeks.getText().equals("")) {
                    num4Weeks = 0;
                } else {
                    num4Weeks = Integer.parseInt(book4Weeks.getText());
                }
                if (numCopies >= 0 && num1Day >= 0 && num1Week >= 0 && num2Weeks >= 0 && num4Weeks >= 0) {
                    if (num1Day + num1Week + num2Weeks + num4Weeks == numCopies) {
                        ArrayList<String> loanDuration = new ArrayList<>();
                        for (int i = 0; i < num1Day; i++) {
                            loanDuration.add("1");
                        }
                        for (int i = 0; i < num1Week; i++) {
                            loanDuration.add("7");
                        }
                        for (int i = 0; i < num2Weeks; i++) {
                            loanDuration.add("14");
                        }
                        for (int i = 0; i < num4Weeks; i++) {
                            loanDuration.add("28");
                        }
                        Library.addBook(dateAdded, year, title, image, null, author, genre, isbn, publisher, languages,
                                numCopies, loanDuration, new ArrayList<>(), new ArrayList<>());
                        bookSuccess.setVisible(true);
                        bookImgName.setText("");
                    } else {
                        bookDurationError.setVisible(true);
                    }
                } else {
                    bookCopiesError.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                bookCopiesError.setVisible(true);
            }
        }
    }

    @FXML
    @SuppressWarnings("Duplicates")
    /**
     * Event handling to create a new VideoGame.
     */
    private void createVideoGameButton() {
    	Date date = Calendar.getInstance().getTime();
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	String dateAdded = dateFormat.format(date);
        String title = videoGameTitle.getText();
        String certificateRating = videoGameCertificateRating.getText();
        String year = videoGameYear.getText();
        String publisher = videoGamePublisher.getText();
        String genre = videoGameGenre.getText();
        String multiplayerSupport = videoGameMultiplayerSupport.getText();
        //String languageString = videoGameLanguage.getText();
        ArrayList<String> languages = null;
        String imageName = videoGameImgName.getText();

        //Reset error/success labels
        videoGameSuccess.setVisible(false);
        videoGameError.setVisible(false);
        videoGameCopiesError.setVisible(false);
        videoGameDurationError.setVisible(false);

        //Check if required fields have input
        if (dateAdded.equals("") || title.equals("") || certificateRating.equals("") || year.equals("") || publisher.equals("") 
        		|| imageName.equals("") || genre.equals("") || multiplayerSupport.equals("")) {
            videoGameError.setVisible(true);
        } else {
            int numCopies;
            int num1Day;
            int num1Week;
            int num2Weeks;
            int num4Weeks;
            try {
                //Add the book to the Library
                String image = "./data/images/videogame/" + imageName;
                if (videoGameNumCopies.getText().equals("")) {
                    numCopies = 0;
                } else {
                    numCopies = Integer.parseInt(videoGameNumCopies.getText());
                }
                if (videoGame1Day.getText().equals("")) {
                    num1Day = 0;
                } else {
                    num1Day = Integer.parseInt(videoGame1Day.getText());
                }
                if (videoGame1Week.getText().equals("")) {
                    num1Week = 0;
                } else {
                    num1Week = Integer.parseInt(videoGame1Week.getText());
                }
                if (videoGame2Weeks.getText().equals("")) {
                    num2Weeks = 0;
                } else {
                    num2Weeks = Integer.parseInt(videoGame2Weeks.getText());
                }
                if (videoGame4Weeks.getText().equals("")) {
                    num4Weeks = 0;
                } else {
                    num4Weeks = Integer.parseInt(videoGame4Weeks.getText());
                }
                if (numCopies >= 0 && num1Day >= 0 && num1Week >= 0 && num2Weeks >= 0 && num4Weeks >= 0) {
                    if (num1Day + num1Week + num2Weeks + num4Weeks == numCopies) {
                        ArrayList<String> loanDuration = new ArrayList<>();
                        for (int i = 0; i < num1Day; i++) {
                            loanDuration.add("1");
                        }
                        for (int i = 0; i < num1Week; i++) {
                            loanDuration.add("7");
                        }
                        for (int i = 0; i < num2Weeks; i++) {
                            loanDuration.add("14");
                        }
                        for (int i = 0; i < num4Weeks; i++) {
                            loanDuration.add("28");
                        }
                        Library.addVideoGame(dateAdded, year, title, image, null, publisher, genre, multiplayerSupport, certificateRating, languages,
                                numCopies, loanDuration, new ArrayList<>(), new ArrayList<>());
                        videoGameSuccess.setVisible(true);
                        videoGameImgName.setText("");
                    } else {
                        videoGameDurationError.setVisible(true);
                    }
                } else {
                    videoGameCopiesError.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                videoGameCopiesError.setVisible(true);
            }
        }
    }
    
    @FXML
    @SuppressWarnings("Duplicates")
    /**
     * Event handling to create a new DVD.
     */
    private void createDVDButton() {
    	Date date = Calendar.getInstance().getTime();
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	String dateAdded = dateFormat.format(date);
        String title = dvdTitle.getText();
        String director = dvdDirector.getText();
        String year = dvdYear.getText();
        String runtime = dvdRuntime.getText();
        String language = dvdLanguage.getText();
        String subsString = dvdSubs.getText();
        ArrayList<String> subs;
        String imageName = dvdImgName.getText();

        //Reset error/success labels
        dvdError.setVisible(false);
        dvdSuccess.setVisible(false);
        dvdCopiesError.setVisible(false);
        dvdDurationError.setVisible(false);

        //Check if required fields have input
        if (dateAdded.equals("") || title.equals("") || director.equals("") || year.equals("") || runtime.equals("") || imageName.equals("")) {
            dvdError.setVisible(true);
        } else {
            //Set optional fields to null if empty
            if (language.equals("")) {
                language = null;
            }
            if (subsString.equals("")) {
                subs = null;
            } else {
                //Split subtitles input into ArrayList
                String[] subsArray = subsString.split(", ");
                subs = new ArrayList<>(Arrays.asList(subsArray));
            }
            int numCopies;
            int num1Day;
            int num1Week;
            int num2Weeks;
            int num4Weeks;
            try {
                //Add the DVD to the Library
                String image = "./data/images/dvd/" + imageName;
                if (dvdNumCopies.getText().equals("")) {
                    numCopies = 0;
                } else {
                    numCopies = Integer.parseInt(dvdNumCopies.getText());
                }
                if (dvd1Day.getText().equals("")) {
                    num1Day = 0;
                } else {
                    num1Day = Integer.parseInt(dvd1Day.getText());
                }
                if (dvd1Week.getText().equals("")) {
                    num1Week = 0;
                } else {
                    num1Week = Integer.parseInt(dvd1Week.getText());
                }
                if (dvd2Weeks.getText().equals("")) {
                    num2Weeks = 0;
                } else {
                    num2Weeks = Integer.parseInt(dvd2Weeks.getText());
                }
                if (dvd4Weeks.getText().equals("")) {
                    num4Weeks = 0;
                } else {
                    num4Weeks = Integer.parseInt(dvd4Weeks.getText());
                }
                if (numCopies >= 0 && num1Day >= 0 && num1Week >= 0 && num2Weeks >= 0 && num4Weeks >= 0) {
                    if (num1Day + num1Week + num2Weeks + num4Weeks == numCopies) {
                        ArrayList<String> loanDuration = new ArrayList<>();
                        for (int i = 0; i < num1Day; i++) {
                            loanDuration.add("1");
                        }
                        for (int i = 0; i < num1Week; i++){
                            loanDuration.add("7");
                        }
                        for (int i = 0; i < num2Weeks; i++) {
                            loanDuration.add("14");
                        }
                        for (int i = 0; i < num4Weeks; i++) {
                            loanDuration.add("28");
                        }
                        Library.addDVD(dateAdded, year, title, image, null, director, runtime, language, subs, numCopies,
                                loanDuration, new ArrayList<>(), new ArrayList<>());
                        dvdSuccess.setVisible(true);
                        dvdImgName.setText("");
                    } else {
                        dvdDurationError.setVisible(true);
                    }
                } else {
                    dvdCopiesError.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                dvdCopiesError.setVisible(true);
            }
        }
    }

    @FXML
    @SuppressWarnings("Duplicates")
    /**
     * Event handling to create a new Laptop.
     */
    private void createLaptopButton() {
    	Date date = Calendar.getInstance().getTime();
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	String dateAdded = dateFormat.format(date);
        String title = laptopTitle.getText();
        String year = laptopYear.getText();
        String manufacturer = laptopManuf.getText();
        String model = laptopModel.getText();
        String os = laptopOS.getText();
        String imageName = laptopImgName.getText();

        //Reset error/success labels
        laptopError.setVisible(false);
        laptopSuccess.setVisible(false);
        laptopCopiesError.setVisible(false);
        laptopDurationError.setVisible(false);

        //Check if require fields have input
        if (dateAdded.equals("") || title.equals("") || year.equals("") || manufacturer.equals("") || model.equals("") || os.equals("")
                || imageName.equals("")) {
            laptopError.setVisible(true);
        } else {
            int numCopies;
            int num1Day;
            int num1Week;
            int num2Weeks;
            int num4Weeks;
            try {
                //Add the Laptop to the Library
                String image = "./data/images/laptop/" + imageName;
                if (laptopNumCopies.getText().equals("")) {
                    numCopies = 0;
                } else {
                    numCopies = Integer.parseInt(laptopNumCopies.getText());
                }
                if (laptop1Day.getText().equals("")) {
                    num1Day = 0;
                } else {
                    num1Day = Integer.parseInt(laptop1Day.getText());
                }
                if (laptop1Week.getText().equals("")) {
                    num1Week = 0;
                } else {
                    num1Week = Integer.parseInt(laptop1Week.getText());
                }
                if (laptop2Weeks.getText().equals("")) {
                    num2Weeks = 0;
                } else {
                    num2Weeks = Integer.parseInt(laptop2Weeks.getText());
                }
                if (laptop4Weeks.getText().equals("")) {
                    num4Weeks = 0;
                } else {
                    num4Weeks = Integer.parseInt(laptop4Weeks.getText());
                }
                if (numCopies >= 0 && num1Day >= 0 && num1Week >= 0 && num2Weeks >= 0 && num4Weeks >= 0) {
                    if (num1Day + num1Week + num2Weeks + num4Weeks == numCopies) {
                        ArrayList<String> loanDuration = new ArrayList<>();
                        for (int i = 0; i < num1Day; i++) {
                            loanDuration.add("1");
                        }
                        for (int i = 0; i < num1Week; i++) {
                            loanDuration.add("7");
                        }
                        for (int i = 0; i < num2Weeks; i++) {
                            loanDuration.add("14");
                        }
                        for (int i = 0; i < num4Weeks; i++) {
                            loanDuration.add("28");
                        }
                        Library.addLaptop(dateAdded, year, title, image, null, manufacturer, model, os, numCopies,
                                loanDuration, new ArrayList<>(), new ArrayList<>());
                        laptopSuccess.setVisible(true);
                        laptopImgName.setText("");
                    } else {
                        laptopDurationError.setVisible(true);
                    }
                } else {
                    laptopCopiesError.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                laptopCopiesError.setVisible(true);
            }
        }
    }

    @FXML
    /**
     * Event handling to choose a book thumbnail image.
     */
    private void bookImageButton() {
        try {
            File selectedFile = getImageFile("book");
            bookImgName.setText(selectedFile.getName());
            setImage(bookImg, selectedFile);
        } catch (NullPointerException ex) {
            System.out.println("No book image file selected");
        }
    }

    @FXML
    /**
     * Event handling to choose a dvd thumbnail image.
     */
    private void dvdImageButton() {
         try {
             File selectedFile = getImageFile("dvd");
             dvdImgName.setText(selectedFile.getName());
             setImage(dvdImg, selectedFile);
         } catch (NullPointerException ex) {
             System.out.println("No dvd image file selected");
         }
    }
    
    @FXML
    /**
     * Event handling to choose a video game thumbnail image.
     */
    private void videoGameImageButton() {
         try {
             File selectedFile = getImageFile("videogame");
             videoGameImgName.setText(selectedFile.getName());
             setImage(videoGameImg, selectedFile);
         } catch (NullPointerException ex) {
             System.out.println("No video game image file selected");
         }
    }

    @FXML
    /**
     * Event handling to choose a laptop thumbnail image.
     */
    private void laptopImageButton() {
        try {
            File selectedFile = getImageFile("laptop");
            laptopImgName.setText(selectedFile.getName());
            setImage(laptopImg, selectedFile);
        } catch (NullPointerException ex) {
            System.out.println("No laptop image file selected");
        }
    }

    /**
     * Opens a FileChooser in the image directory of the selected type.
     * @param type 
     * The type of resource for which to choose an image (i.e. book/dvd/laptop).
     * @return a File object correspondng to the selected image (null if cancelled).
     */
    private File getImageFile(String type) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.setInitialDirectory(new File("./data/images/" + type));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images files", "*png", "*jpg")
        );
        return fileChooser.showOpenDialog(ScreenManager.getStage());
    }

    /**
     * Sets an image to an ImageView object
     * @param imv 
     * The ImageView object.
     * @param imgFile 
     * The image file object.
     */
    private void setImage(ImageView imv, File imgFile) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imv.setImage(SwingFXUtils.toFXImage(img, null));
    }
    
    /**
     * Creates an event which users can attend.
     */
    @FXML
    private void createEventButton() {
        eventError.setVisible(false);
        eventSuccess.setVisible(false);

        String event = eventName.getText();
        String[] d = date.getValue().toString().split("-");
        String da = d[2]+"/"+d[1]+"/"+d[0];
        String timeOfEvent = time.getText();
        String desc = description.getText();
        int maxNum = Integer.valueOf(maxNumberOfSpaces.getText());


        if(!event.equals("") && !d.equals("") && !timeOfEvent.equals("")
        		&& !desc.equals("") && maxNum != 0) {
        	
            Library.addNewEvent(event, da, timeOfEvent, maxNum, desc);
            eventSuccess.setVisible(true);
            
        } else {
            eventError.setVisible(true);
        }

    }

    @FXML
    private void checkDate(){

    }
}
