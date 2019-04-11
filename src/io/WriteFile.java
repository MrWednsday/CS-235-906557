package io;

import event.Event;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import library.Library;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import resources.*;
import user.Librarian;
import user.User;
import utils.Queue;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <h1>WriteFile.</h1>
 * <p>This class handles all of the data storing for the program. It writes all of the data to JSON files, in which the
 * file path are stored in the IO class.</p>
 *
 * @author Samuel Jankinson, James Carter, Etienne Badoche
 */
//TODO add email to write class and add email to all users.
@SuppressWarnings("Duplicates")
public class WriteFile extends IO {
    /**
     * This method saves a user object to the user json file.
     *
     * @param user a user of the library.
     */
    @SuppressWarnings({"unchecked"})
    public static void writeUser(User user) {
        JSONObject object = new JSONObject();
        JSONArray resourceArray = new JSONArray();
        JSONArray transactionArray = new JSONArray();
        JSONArray borrowHistoryArray = new JSONArray();
        JSONObject borrowHistoryObject = new JSONObject();
        JSONArray jsonRequestedArray = new JSONArray();
        JSONArray jsonReservedArray = new JSONArray();
        JSONArray jsonEventsAttended = new JSONArray();
        object.put("username", user.getUserName());
        object.put("firstName", user.getFirstName());
        object.put("lastName", user.getLastName());
        object.put("mobileNumber", user.getMobileNumber());
        object.put("firstLineAddress", user.getFirstLineAddress());
        object.put("secondLineAddress", user.getSecondLineAddress());
        object.put("postCode", user.getPostCode());
        object.put("townName", user.getTownName());
        object.put("imageAddress", user.getProfImage());
        object.put("accountBalance", String.valueOf(user.getAccountBalanceDouble()));
        Date date = user.getLastLogin();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        object.put("lastLogin", strDate);

        for (String resource : user.getCurrentlyBorrowedResources()) {
            resourceArray.add(resource);
        }
        object.put("resourceBorrow", resourceArray);

        ArrayList<String[]> transactions = user.getTransactions();
        for (String[] transaction : transactions) {
            JSONObject singleTransaction = new JSONObject();
            singleTransaction.put("System", transaction[0]);
            singleTransaction.put("Date", transaction[1]);
            singleTransaction.put("Amount", transaction[2]);
            transactionArray.add(singleTransaction);
        }
        object.put("transactionHistory", transactionArray);

        ArrayList<String[]> borrowHistoryStrings = user.getBorrowHistory();
        for (String[] borrowHistory : borrowHistoryStrings) {
            JSONObject borrowHistoryObj = new JSONObject();
            borrowHistoryObj.put("ID", borrowHistory[0]);
            borrowHistoryObj.put("Date", borrowHistory[1]);
            borrowHistoryArray.add(borrowHistoryObj);
        }
        object.put("borrowHistory", borrowHistoryArray);

        ArrayList<String> requestedArray = user.getAllRequested();
        for (String requested : requestedArray) {
            System.out.println(requested);
            jsonRequestedArray.add(requested);
        }
        object.put("requested", jsonRequestedArray);

        ArrayList<String> reservedArray = user.getAllReserved();
        for (String reserved : reservedArray) {
            jsonReservedArray.add(reserved);
        }
        object.put("reserved", jsonReservedArray);

        ArrayList<String> eventsAttended = user.getAllEventsAttended();
        for (String eventTitle : eventsAttended){
            jsonEventsAttended.add(eventTitle);
        }
        object.put("events", jsonEventsAttended);

//        ArrayList<Integer> fineArray = user.getFineHistory();
//        JSONArray fineHistoryObject = new JSONArray();
//        for (int amount : fineArray){
//            JSONObject fineData = new JSONObject();
//            fineData.put("Amount", amount);
//            fineHistoryObject.add(fineData);
//        }
//        object.put("fineHistory", fineHistoryObject);

        try {
            FileWriter file = new FileWriter(IO.getUsersFilePath(), true);
            file.write(object.toJSONString() + "\n");
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.println("Error writing user to " + IO.getUsersFilePath() + " " + user.getUserName());
        }
    }

    /**
     * This method saves a librarian object to the librarian json file. It saves everything to do with the librarian.
     *
     * @param librarian a librarian of the library.
     */
    @SuppressWarnings("unchecked")
    public static void writeLibrarian(Librarian librarian) {
        JSONObject object = new JSONObject();
        JSONArray resourceArray = new JSONArray();
        JSONArray transactionArray = new JSONArray();
        JSONArray borrowHistoryArray = new JSONArray();
        JSONArray jsonRequestedArray = new JSONArray();
        JSONArray jsonReservedArray = new JSONArray();
        JSONArray jsonEventsAttended = new JSONArray();

        object.put("username", librarian.getUserName());
        object.put("firstName", librarian.getFirstName());
        object.put("lastName", librarian.getLastName());
        object.put("mobileNumber", librarian.getMobileNumber());
        object.put("firstLineAddress", librarian.getFirstLineAddress());
        object.put("secondLineAddress", librarian.getSecondLineAddress());
        object.put("postCode", librarian.getPostCode());
        object.put("townName", librarian.getTownName());
        object.put("imageAddress", librarian.getProfImage());
        object.put("accountBalance", String.valueOf(librarian.getAccountBalanceDouble()));
        object.put("empDay", String.valueOf(librarian.getEmploymentDay()));
        object.put("empMonth", String.valueOf(librarian.getEmploymentMonth()));
        object.put("empYear", String.valueOf(librarian.getEmploymentYear()));
        object.put("staffNumber", librarian.getStaffNumber());
        object.put("noOfEmploys", String.valueOf(librarian.getNumberOfEmploys()));
        Date date = librarian.getLastLogin();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        object.put("lastLogin", strDate);

        for (String resource : librarian.getCurrentlyBorrowedResources()) {
            resourceArray.add(resource);
        }
        object.put("resourceBorrow", resourceArray);

        ArrayList<String[]> transactions = librarian.getTransactions();
        for (String[] transaction : transactions) {
            JSONArray singleTransaction = new JSONArray();
            singleTransaction.add(transaction[0]);
            singleTransaction.add(transaction[1]);
            singleTransaction.add(transaction[2]);
            transactionArray.add(singleTransaction);
        }
        object.put("transactionHistory", transactionArray);

        ArrayList<String[]> borrowHistoryStrings = librarian.getBorrowHistory();
        for (String[] borrowHistory : borrowHistoryStrings) {
            JSONArray borrowArray = new JSONArray();
            borrowArray.add(borrowHistory[0]);
            borrowArray.add(borrowHistory[1]);
            borrowHistoryArray.add(borrowArray);
        }
        object.put("borrowHistory", borrowHistoryArray);

        ArrayList<String> requestedArray = librarian.getAllRequested();
        for (String requested : requestedArray) {
            System.out.println(requested);
            jsonRequestedArray.add(requested);
        }
        object.put("requested", jsonRequestedArray);

        ArrayList<String> reservedArray = librarian.getAllReserved();
        for (String reserved : reservedArray) {
            jsonReservedArray.add(reserved);
        }
        object.put("reserved", jsonReservedArray);

        ArrayList<String> eventsAttended = librarian.getAllEventsAttended();
        for (String eventTitle : eventsAttended){
            jsonEventsAttended.add(eventTitle);
        }
        object.put("events", jsonEventsAttended);

        try {
            FileWriter file = new FileWriter(IO.getLibrarianFilePath(), true);
            file.write(object.toJSONString() + "\n");
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.println("Error writing user to " + IO.getLibrarianFilePath() + " " + librarian.getUserName());
        }
    }

    /**
     * Writes all currently owned resources to a file.
     * @param books a list of all currently owned books.
     * @param dvds a list of all currently owned dvds
     * @param laptops a list of all currently owned laptops.
     * @param games a list of all currently owned videogames.
     */
    public static void writeResources(List<Book> books, List<DVD> dvds, List<Laptop> laptops, List<VideoGame> games){
        // Create the JSONObject Holding All Resources
        JSONObject resourcesObject = new JSONObject();

        // Every Book
        JSONArray bookArray = new JSONArray();
        for (Book b : books){
            JSONObject bookObject = writeBookToObject(b);
            bookArray.add(bookObject);
        }

        // Every DVD
        JSONArray dvdArray = new JSONArray();
        for (DVD d : dvds){
            JSONObject dvdObject = writeDVDToObject(d);
            dvdArray.add(dvdObject);
        }

        // Every Laptop
        JSONArray laptopArray = new JSONArray();
        for (Laptop l : laptops){
            JSONObject laptopObject = writeLaptopToObject(l);
            laptopArray.add(laptopObject);
        }

        // Every Video Game
        JSONArray gameArray = new JSONArray();
        for (VideoGame g : games){
            JSONObject gameObject = writeVideoGameToObject(g);
            gameArray.add(gameObject);
        }


        resourcesObject.put("Books", bookArray);
        resourcesObject.put("DvDs", dvdArray);
        resourcesObject.put("Laptops", laptopArray);
        resourcesObject.put("VideoGames", gameArray);

        try {
            FileWriter file = new FileWriter(IO.getResourceFilePath());
            file.write(resourcesObject.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.println("Error writing to " + IO.getResourceFilePath());
        }

        System.out.println(resourcesObject.toJSONString());
    }

    /**
     * converts a Resource object to a writeable JSON object.
     * @param resource the resource to be converted.
     * @return resourceObject the converted, writeable resource.
     */
    private static JSONObject writeResourceToObject(Resource resource){
        JSONObject resourceObject = new JSONObject();

        // Resource Properties
        resourceObject.put("DateAdded", resource.getDateAdded());
        resourceObject.put("ID", resource.getUniqueID());
        resourceObject.put("Title", resource.getTitle());
        resourceObject.put("Year", resource.getYear());
        resourceObject.put("CopyAmount", String.valueOf(resource.getNoOfCopies()));
        resourceObject.put("ThumbnailImage", resource.getThumbnailImageRef());

        Queue<User> reservedQueue = resource.getQueueOfReservations();
        JSONArray reservedArray = new JSONArray();
        while (!reservedQueue.isEmpty()) {
            reservedArray.add(reservedQueue.peek());
            reservedQueue.dequeue();
        }
        resourceObject.put("ReservedQueue", reservedArray);

        JSONArray loanDurations = new JSONArray();
        for (int i = 0; i < resource.getNoOfCopies(); i++) {
            loanDurations.add(resource.getLoanDuration(String.valueOf(i)));
        }
        resourceObject.put("LoanDurations", loanDurations);

        // Current Data
        JSONArray currentBorrowData = new JSONArray();
        List<CopyData> copies = resource.getArrayListOfCopies();
        for (CopyData copy : copies) {
            JSONObject copyBorrowData = new JSONObject();

            copyBorrowData.put("UserID", copy.getCurrentInfo().getUserID());
            copyBorrowData.put("DateBorrowed", copy.getCurrentInfo().getDateBorrowed());
            copyBorrowData.put("DateReturned", copy.getCurrentInfo().getDateReturned());
            copyBorrowData.put("DateRequestedReturn", copy.getCurrentInfo().getDateRequestedReturn());

            currentBorrowData.add(copyBorrowData);
        }
        resourceObject.put("CurrentBorrowData", currentBorrowData);

        // History Data
        JSONArray borrowHistoryArray = new JSONArray();
        for (CopyData copy : copies) {
            JSONArray copyBorrowHistoryArray = new JSONArray();
            for (BorrowHistoryData borrowHistory : copy.getBorrowHistory()) {
                JSONObject borrowHistoryObject = new JSONObject();

                borrowHistoryObject.put("UserID", borrowHistory.getUserID());
                borrowHistoryObject.put("BorrowDate", borrowHistory.getDateBorrowed());
                borrowHistoryObject.put("ReturnDate", borrowHistory.getDateReturned());
                borrowHistoryObject.put("RequestedDate", borrowHistory.getDateRequestedReturn());

                copyBorrowHistoryArray.add(borrowHistoryObject);
            }
            borrowHistoryArray.add(copyBorrowHistoryArray);
        }
        resourceObject.put("BorrowHistory", borrowHistoryArray);

        return resourceObject;
    }

    /**
     * Converts a Book resource to a writeable JSON object.
     * @param book the resource to be converted.
     * @return bookObject the JSON object of the converted Resource
     */
    private static JSONObject writeBookToObject(Book book){
        JSONObject bookObject = writeResourceToObject(book);

        bookObject.put("Author", book.getAuthor());
        bookObject.put("Genre", book.getGenre());
        bookObject.put("ISBN", book.getIsbn());
        bookObject.put("Publisher", book.getPublisher());

        JSONArray languagesArray = new JSONArray();
        for (String language : book.getLanguages()) {
            languagesArray.add(language);
        }
        bookObject.put("Languages", languagesArray);
        return bookObject;
    }

    /**
     * Converts a DVD resource to a writeable JSON object.
     * @param dvd the resource to be converted.
     * @return dvdObject the JSON object of the converted Resource
     */
    private static JSONObject writeDVDToObject(DVD dvd){
        JSONObject dvdObject = writeResourceToObject(dvd);

        dvdObject.put("Director", dvd.getDirector());
        dvdObject.put("Runtime", dvd.getRuntime());
        dvdObject.put("Language", dvd.getLanguage());

        JSONArray languagesArray = new JSONArray();
        for (String language : dvd.getSubLang()) {
            languagesArray.add(language);
        }
        dvdObject.put("Sub-languages", languagesArray);

        return dvdObject;

    }

    /**
     * Converts a laptop resource to a writeable JSON object.
     * @param laptop the resource to be converted.
     * @return laptopObject the JSON object of the converted Resource
     */
    private static JSONObject writeLaptopToObject(Laptop laptop){
        JSONObject laptopObject = writeResourceToObject(laptop);

        laptopObject.put("Manufacturer", laptop.getManufacturer());
        laptopObject.put("Model", laptop.getModel());
        laptopObject.put("OperatingSys", laptop.getOperatingSys());

        return laptopObject;
    }

    /**
     * Converts a videogame object to a writeable JSON object
     * @param videoGame the resource to be converted.
     * @return videoGameObject the converted resource.
     */
    private static JSONObject writeVideoGameToObject(VideoGame videoGame){
        JSONObject videoGameObject = writeResourceToObject(videoGame);

        videoGameObject.put("Publisher", videoGame.getPublisher());
        videoGameObject.put("Genre", videoGame.getGenre());
        videoGameObject.put("MultiplayerSupport", videoGame.getMultiplayerSupport());
        videoGameObject.put("CertificateRating", videoGame.getCertificateRating());

        JSONArray languagesArray = new JSONArray();
        for (String language : videoGame.getLanguages()) {
            languagesArray.add(language);
        }
        videoGameObject.put("Languages", languagesArray);

        return videoGameObject;
    }

    /**
     * This method overwrites the user json file with all of the users passed into the method.
     *
     * @param users an ArrayList of users from the library.
     */
    public static void overwriteUsers(ArrayList<User> users) {
        File usersFile = new File(IO.getUsersFilePath());

        if (usersFile.exists()) {
            usersFile.delete();
        }

        for (User user : users) {
            writeUser(user);
        }
    }

    /**
     * This method overwrites the librarian json file with all of the librarians passed into the method.
     *
     * @param librarians an ArrayList of librarians from the library.
     */
    public static void overwriteLibrarians(ArrayList<Librarian> librarians) {
        File librarianFile = new File(IO.getLibrarianFilePath());

        if (librarianFile.exists()) {
            librarianFile.delete();
        }

        for (Librarian librarian : librarians) {
            writeLibrarian(librarian);
        }
    }

    /**
     * This method overwrites all of the resource json files, with all of the resources passed into the method.
     *
     * @param books   an ArrayList of books from the library.
     * @param dvds    an ArrayList of dvds from the library.
     * @param laptops an ArrayList of laptops from the library.
     * @param videoGames an ArrayList of videogames from the library.
     */
    public static void overwriteResources(ArrayList<Book> books, ArrayList<DVD> dvds, ArrayList<Laptop> laptops, ArrayList<VideoGame> videoGames) {
        File[] resourceFiles = {new File(IO.getResourceFilePath())};

        for (File resourceFile : resourceFiles) {
            if (resourceFile.exists()) {
                resourceFile.delete();
            }
        }

        writeResources(books, dvds, laptops, videoGames);
    }

    /**
     * This method is used to overwrite all of data in the json files that the program is using.
     *
     * @param users      an ArrayList of users from the library.
     * @param books      an ArrayList of the books from the library.
     * @param dvds       an ArrayList of dvds from the library.
     * @param laptops    an ArrayList of laptops from the library.
     * @param videoGames an ArrayList of videogames from the library.
     * @param librarians an ArrayList of librarians from the library.
     * @param events     an ArrayList of events hosted by the library. 
     */
    public static void fullWrite(ArrayList<User> users, ArrayList<Book> books, ArrayList<DVD> dvds,
                                 ArrayList<Laptop> laptops,ArrayList<VideoGame> videoGames, ArrayList<Librarian> librarians, ArrayList<Event> events) {

        overwriteUsers(users);
        overwriteResources(books, dvds, laptops, videoGames);
        overwriteLibrarians(librarians);
        overwriteEvents(events);
    }

    /**
     * This method backs up all of the current json files and creates new ones with the new data from the library
     * (does a full write).
     */
    public static void backupCurrent() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
        String newFilePath = dateFormat.format(new Date());
        new File("./data/backup/" + newFilePath).mkdirs();
        File currentFile = null;

        currentFile = new File(IO.getUsersFilePath());
        currentFile.renameTo(new File("./data/backup/" + newFilePath + "/users.json"));

        currentFile = new File(IO.getLibrarianFilePath());
        currentFile.renameTo(new File("./data/backup/" + newFilePath + "/librarians.json"));

        currentFile = new File(IO.getResourceFilePath());
        currentFile.renameTo(new File("./data/backup/" + newFilePath + "/resources.json"));

        currentFile = new File(IO.getEventFilepath());
        currentFile.renameTo(new File("./data/backup/" + newFilePath + "/events.json"));

        fullWrite(Library.getAllUsers(), Library.getAllBooks(), Library.getAllDVD(), Library.getAllLaptops(), Library.getAllVideoGames(), Library.getAllLibrarians(), Library.getAllEvents());
    }

    /**
     * This method saves as image to a specific file path.
     *
     * @param img  WritableImage to save.
     * @param file File path to save to.
     */
    public static void saveImageToUser(WritableImage img, File file) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * This method writes a new rating to the file.
     * @param id the id of the rating
     * @param message the rating details i.e. the written rating.
     * @param rating the number rating of resources i.e. 5/5 etc
     */
    public static void writeRatingToFile(String id, String message, String rating) {
    	JSONObject object = new JSONObject();
    	object.put("id", id);
    	object.put("message", message);
    	object.put("rating", rating);
    	object.put("username", Library.getCurrentLoggedInUser().getUserName());
    	
    	try {
            FileWriter file = new FileWriter(IO.getRatingsFilePath(), true);
            file.write(object.toJSONString() + "\n");
            file.flush();
            file.close();
        } catch (IOException e) {
            System.out.println("Error writing ratings to " + IO.getRatingsFilePath());
        }
    }

    /**
     * Overwrites all currently stored events.
     * @param events the list of events to overwrite the original file with.
     */
    public static void overwriteEvents(ArrayList<Event> events) {

        File eventFile = new File(IO.getEventFilepath());
        JSONObject object;
        FileWriter file;

        if (eventFile.exists()) {
            if(eventFile.delete()) {
                System.out.println("File deleted");
            } else {  
                System.out.println("File not deleted");
            }
        }


        try {
            file = new FileWriter(IO.getEventFilepath(), true);
            for (Event event : events) {
                object = writeEventToObject(event);
                file.write(object.toJSONString() + "\n");
                file.flush();
            }
            file.close();
        } catch (IOException e) {
            System.out.println("Error writing user to " + IO.getUsersFilePath());
        }
    }

    /**
     * Returns an object of an event which has been formatted to be writeable.
     * @param event the event to convert to a writeable format.
     * @return eventObject the writeable object.
     */
    public static JSONObject writeEventToObject(Event event) {
    	JSONObject eventObject =  new JSONObject();

        eventObject.put("eventID", event.getEventID());
        eventObject.put("title", event.getTitle());
        eventObject.put("date", event.getDate());
        eventObject.put("time", event.getTime());
        eventObject.put("maxNumberOfAttending", String.valueOf(event.getMaxNumberOfAttending()));
        eventObject.put("currentNumberOfAttending", String.valueOf(event.getCurrentNumberOfAttending()));
        eventObject.put("description", event.getDescription());

        return eventObject;
    }
}
