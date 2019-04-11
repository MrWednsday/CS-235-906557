package user;

import library.Library;
import library.LibraryEvents;
import resources.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <h1>User.</h1>
 * <p>This class models a Library User.</p>
 *
 * @author Dominik R Wojtasiewicz, Sam Jankinson, Ammar Alamri.
 * @since 18/11/2018
 */
public class User {
    protected String userName; //unique userName
    protected String firstName; //First name
    protected String lastName; //Last name
    protected String mobileNumber; //mobile number
    protected String firstLineAddress; //First line of address
    protected String secondLineAddress; //Second line of address
    protected String postCode; //Post code
    protected String townName; //Town name
    protected String email;
    protected ArrayList<String> resourceCurrentlyBorrowed = new ArrayList<>(); //List of currently borrowed books
    protected ArrayList<String> resourceCurrentlyRequested = new ArrayList<>();
    protected ArrayList<String> resourceCurrentlyReserved = new ArrayList<>(); //Available for pick up
    protected ArrayList<String[]> transactionHistory = new ArrayList<>(); //Transaction History
    protected ArrayList<String[]> borrowHistory = new ArrayList<>(); // Borrow history
    protected double accountBalance; //current account balance
    protected String profImage; //profile image address
    protected Date lastLogin; //Date last log in.
    protected ArrayList<String>[] resourcesBorrowStats = new ArrayList[3]; // [0] - Day, [1] - Week, [2] - Month. Value = Resource ID, NOT COPY ID
    protected int[] resourceTypeStats = new int[4]; // [0] - Book, [1] - DVD, [2] - Laptop, [3] - Video Game
    protected ArrayList<Integer> fineHistory = new ArrayList<>(); // History on Fines this user has had
    protected ArrayList<String> eventsAttended = new ArrayList<>(); // A list of events the user has attended.

    /**
     * The constructor which initialises a user and all their parameters.
     *
     * @param userName          unique username of the user
     * @param firstName         first name of the user
     * @param lastName          last name of the user
     * @param mobileNumber      mobile number of the user
     * @param firstLineAddress  first line address of the user
     * @param secondLineAddress second line address of the user
     * @param postCode          post code of the user
     * @param townName          town name of the user
     * @param accountBalance    current account balance of the user
     * @param profImage         path to the profile image of the user
     */
    public User(String userName, String firstName, String lastName, 
    		String mobileNumber, String firstLineAddress, String secondLineAddress, 
    		String postCode, String townName, double accountBalance, String profImage, String email) {
        setUserName(userName);
        setFirstName(firstName);
        setLastName(lastName);
        setMobileNumber(mobileNumber);
        setFirstLineAddress(firstLineAddress);
        setSecondLineAddress(secondLineAddress);
        setPostCode(postCode);
        setTownName(townName);
        setAccountBalance(accountBalance);
        setProfImage(profImage);
        this.email = email;

        resourcesBorrowStats[0] = new ArrayList<>();
        resourcesBorrowStats[1] = new ArrayList<>();
        resourcesBorrowStats[2] = new ArrayList<>();
    }

	/**
	 * Returns email address of user.
	 * @return String
	 */
	public String  getEmail(){
		return this.email;
	}


	/**
	 * This sets the account balance of the user.
     *
     * @param amount the start amount in pence.
     */
    public void setAccountBalance(double amount) {
        this.accountBalance = amount;
    }

    /**
     * Returns the username.
     *
     * @return username as String.
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * This sets the user name of the user. Can only be done once.
     *
     * @param userName the username of the user.
     */
    protected void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the first name of the user.
     *
     * @return firstName the first name of the user as String.
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * This sets the first name of the user. Can only be done once.
     *
     * @param firstName name of the user.
     */
    protected void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Return the last name of the user.
     *
     * @return last name as String.
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * This sets the last name of the user can be changed later.
     *
     * @param lastName last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * This return first and last name separated by a space as one string.
     *
     * @return String of "First Last" name
     */
    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Returns the mobile number of the user.
     *
     * @return the number as a String.
     */
    public String getMobileNumber() {
        return this.mobileNumber;
    }

    /**
     * This sets mobile number of the user. Can be changed later.
     *
     * @param mobileNumber the mobile number of the user.
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * Returns the first line of address.
     *
     * @return String of first line of address.
     */
    public String getFirstLineAddress() {
        return this.firstLineAddress;
    }

    /**
     * Sets the first line address of the user. Can be changed later.
     *
     * @param firstLineAddress first line address of the user.
     */
    public void setFirstLineAddress(String firstLineAddress) {
        this.firstLineAddress = firstLineAddress;
    }

    /**
     * Returns the second line of address.
     *
     * @return string of second line of address.
     */
    public String getSecondLineAddress() {
        return this.secondLineAddress;
    }

    /**
     * Sets the second line address of the user. Can be changed later.
     *
     * @param secondLineAddress second line address of the user.
     */
    public void setSecondLineAddress(String secondLineAddress) {
        this.secondLineAddress = secondLineAddress;
    }

    /**
     * Returns the post code of the user.
     *
     * @return a string representing the post code of the user
     */
    public String getPostCode() {
        return this.postCode;
    }

    /**
     * Sets the post code of the user. Can be changed later.
     *
     * @param postCode the postcode of the user.
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Returns the name of the town of the user
     *
     * @return a string representation the name of the town of the usr.
     */
    public String getTownName() {
        return this.townName;
    }

    /**
     * This sets the town name of the user. Can be changed later.
     *
     * @param townName the name of the town the user lives in.
     */
    public void setTownName(String townName) {
        this.townName = townName;
    }

    /**
     * This returns the full address formatted for postage
     *
     * @return String representation of a user's full address.
     */
    public String getFullAddress() {
        String address = getFirstLineAddress();
        if (!getSecondLineAddress().equals("")) {
            return address + getSecondLineAddress()
                    + "\n" + getTownName() + "\n" + getPostCode();
        } else {
            return address + "\n" + getTownName() + "\n" + getPostCode();
        }
    }

    /**
     * This returns the path to the profile image of the user
     *
     * @return String representation of this user's profile image filepath.
     */
    public String getProfImage() {
        return this.profImage;
    }

    /**
     * This sets the path for the profile image of the user.
     *
     * @param profImage the path to the image of the user.
     */
    public void setProfImage(String profImage) {
        this.profImage = profImage;
    }

    /**
     * Get the resource with specific ID from the user currently borrowed
     *
     * @param resourceID The unique ID of the user
     * @return Resource object representing resource with ID, returns null if unique ID does not match
     * any item in the list.
     */
    public String getResource(String resourceID) {
        for (String id : this.resourceCurrentlyBorrowed) {
            if (id.equals(resourceID)) {
                return id;
            }
        }
        return null;
    }

    /**
     * Returns full list of resources borrowed by the user.
     *
     * @return ArrayList storing all Resource objects.
     */
    public ArrayList<String> getCurrentlyBorrowedResources() {
        return this.resourceCurrentlyBorrowed;
    }

    /**
     * Returns all transaction history of the user.
     *
     * @return a ArrayList. This arrayList holds arrays in the following way,
     * DateOfTransaction	Amount of Transaction
     */
    public ArrayList<String[]> getTransactions() {
        return this.transactionHistory;
    }

    /**
     * This returns the current account balance as a String. Following format "£X.XX".
     *
     * @return a string representation of the balance.
     */
    public String getAccountBalanceString() {
        return "£" + String.valueOf(this.accountBalance);
    }

    /**
     * Ger current account balance
     *
     * @return Double account balance
     */
    public double getAccountBalanceDouble() {
        return this.accountBalance;
    }

    /**
     * Adds a resource to users current loan.
     *
     * @param id The resource to be added to the current borrow.
     */
    public void loanResource(String id) {
        this.resourceCurrentlyBorrowed.add(id);
        addResourceToHistory(id);
        resourceCurrentlyReserved.remove(id);
    }

    /**
     * Add's resource to user history.
     *
     * @param resourceID ID of resource.
     */
    protected void addResourceToHistory(String resourceID) {
        String[] data = new String[3];
        data[1] = getCurrentDate();
        data[0] = resourceID;
        data[2] = "Currently Out ";
        borrowHistory.add(data);
    }

    /**
     * Add balance to the account.
     *
     * @param amount the amount to be added.
     */
    public void addAccountBalance(int amount) {
        this.accountBalance = this.accountBalance + amount;
        fineHistory.add(amount);
    }

    /**
     * Remove balance from account.
     *
     * @param amount the amount to be removed from account.
     */
    public void subtractAccountBalance(int amount) {
        this.accountBalance = this.accountBalance - amount;
    }

    /**
     * Add's transaction to history of the user.
     *
     * @param amount the balance of the user.
     * @param system current system data.
     */
    public void addTransaction(String system, double amount) {
        //Computes current system data
        String[] data = {system, getCurrentDate(), String.valueOf(amount)};
        transactionHistory.add(data);
    }

    /**
     * Returns resource from a user to the library.
     *
     * @param resourceID ID of resource to return
     */
    public void returnResource(String resourceID) {
        int j = 0;
        Boolean done = false;
        while (j <= this.resourceCurrentlyBorrowed.size() - 1 && !done) {
            if (this.resourceCurrentlyBorrowed.get(j).equals(resourceID)) {
                this.resourceCurrentlyBorrowed.remove(j);
                String[] resourceCopyID = resourceID.split("-");
                Library.getResource(resourceCopyID[0]).returnResource(Integer.parseInt(resourceCopyID[1]));
                done = true;
            }
            j++;
        }

        done = false;
        int i = this.borrowHistory.size() - 1;
        while (!done && i >= 0) {
            String[] data = borrowHistory.get(i);
            if (data[0].equals(resourceID)) {
                data[1] = getCurrentDate();
                borrowHistory.remove(i);
                borrowHistory.add(i, data);
                done = true;
            }
            i--;
        }
    }

    /**
     * Returns the history of the user
     *
     * @return borrowHistory
     * The User's borrow history
     */
    public ArrayList<String[]> getBorrowHistory() {
        return this.borrowHistory;
    }

    /**
     * Returns the history of the user.
     *
     * @param ID of resource to request.
     */
    public void requestResource(String ID) {
        this.resourceCurrentlyRequested.add(ID);
    }

    /**
     * Get's all of users requested resources.
     *
     * @return resourceCurrentlyRequested
     * All the resources this user has currently requested.
     */
    public ArrayList<String> getAllRequested() {
        return this.resourceCurrentlyRequested;
    }

    /**
     * Get all of users reserved items.
     *
     * @return resourceCurrentlyReserved
     * All the resources this user currently has reserved.
     */
    public ArrayList<String> getAllReserved() {
        return this.resourceCurrentlyReserved;
    }

    /**
     * Gets current system date in following format DD-MM-YYYY HH:MM:SS.
     *
     * @return The current date
     */
    private String getCurrentDate() {
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dataFormat.format(new Date());
    }

    /**
     * Gets the borrow stats object for the user
     *
     * @return The Resource Borrow Stats Object
     */
    public ArrayList<String>[] getResourcesBorrowStats() {
        return resourcesBorrowStats;
    }

    /**
     * Gets the resource type stats for this user
     *
     * @return The Resource Type stats
     */
    public int[] getResourceTypeStats() {
        return resourceTypeStats;
    }

    /**
     * Get's the date user was last logged in.
     *
     * @return lastLogIn The date the user last logged in to the library.
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets the user last login to current date.
     * @param login the date the user last logged in.
     */
    public void setLastLogin(Date login) {
        this.lastLogin = login;
    }

    /**
     * Moves requested to reserved.
     *
     * @param id String id of resource.
     */
    public void moveToReserved(String id) {
        resourceCurrentlyRequested.remove(id);
        resourceCurrentlyReserved.add(id);
    }

    /**
     * Adds book to user reserved.
     *
     * @param id String id of resource to be added.
     */
    public void addToReserved(String id) {
        resourceCurrentlyReserved.add(id);
        Resource r = Library.getResource(id);
        r.addUserToRequestQueue(this.getUserName());
    }

    /**
     * Adds to borrow history on start up.
     *
     * @param data Information about the user's borrow history
     */
    public void addToBorrowHistory(String[] data) {
        borrowHistory.add(data);

        switch (data[0].split(":")[0]) {
            case "BookID":
                resourceTypeStats[0]++;
                break;
            case "DVDID":
                resourceTypeStats[1]++;
                break;
            case "LaptopID":
                resourceTypeStats[2]++;
                break;
            case "VideoGameID":
                resourceTypeStats[3]++;
                break;
            default:
                System.err.println("Resource Type Not Valid in Borrow History");
                break;
        }

        Date borrowDate = new Date();
        SimpleDateFormat dateFormatDMY = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        if (Library.getResource(data[0].split("-")[0]) != null) {
            System.out.println(data[0]);
            Resource r = Library.getResource(data[0].split("-")[0]);

            try {
                borrowDate = dateFormat.parse(data[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            r.getResourceStatData().addBorrowDate(dateFormatDMY.format(borrowDate));
        }

        borrowDate = new Date();
        try {
            borrowDate = dateFormat.parse(data[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (isWithinDate(new Date(), 1, borrowDate)) {
            resourcesBorrowStats[0].add(data[1]);
        }

        if (isWithinDate(new Date(), 7, borrowDate)) {
            resourcesBorrowStats[1].add(data[1]);
        }

        if (isWithinDate(new Date(), 30, borrowDate)) {
            resourcesBorrowStats[2].add(data[1]);
        }

    }

    /**
     * Check if a date is within a given date and number of days
     * @param startDate The start Date
     * @param numDays How many days to check between
     * @param date The Date being checked
     * @return If the date is between startDate + numDays
     */
    private boolean isWithinDate(Date startDate, int numDays, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, -numDays);
        Date endDate = c.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String endDateS = dateFormat.format(endDate);
        try {
            endDate = dateFormat.parse(endDateS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(date.toString() + " - " + startDate.toString() 
        + " - " + endDate.toString());

        System.out.println(date.before(startDate) && date.after(endDate));

        return (date.before(startDate) && date.after(endDate));
    }

    /**
     * Add's to transaction history on start up.
     *
     * @param data Information about the user's transaction history
     */
    public void addToTransactionHistory(String[] data) {
        transactionHistory.add(data);
    }

    /**
     * Sets a user currently borrowed
     *
     * @param data is an arraylist containing the data about currently borrowed resources.
     */
    public void setResourceCurrentlyBorrowed(ArrayList<String> data) {
        this.resourceCurrentlyBorrowed = data;
    }

    /**
     * Sets a user currently reserved
     *
     * @param data The information about the user's currently reserved resources.
     */
    public void setResourceCurrentlyReserved(ArrayList<String> data) {
        this.resourceCurrentlyReserved = data;
    }

    /**
     * Sets a user currently requested
     *
     * @param data The data about the user's currently requested resources.
     */
    public void setResourceCurrentlyRequested(ArrayList<String> data) {
        this.resourceCurrentlyRequested = data;
    }

    /**
     * Method that determines if limit of resources has been reached.
     *
     * @param id Id of resource to be borrow.
     * @return true or false depending whether the user has reached borrow limit.
     */
    public boolean canBorrow(String id) {
        String subString = id.substring(0, 1).toLowerCase();
        int afterBorrow = noOfResourcesBorrowed();

        if (subString.equals("l")) {
            afterBorrow += 3;
        } else {
            afterBorrow += 1;
        }

        return afterBorrow <= Library.getMaxResources();
    }

    /**
     * Returns the number of resources borrowed with weightings - i.e. laptop has a weighting of 3
     * @return noOfResources the number of resources currently borrowed by this user.
     */
    public int noOfResourcesBorrowed() {
        int noOfResources = 0;
        System.out.println(this.getUserName() + " 554 User " + this.resourceCurrentlyBorrowed.size());
        for (String s : this.resourceCurrentlyBorrowed) {
            String sub = s.substring(0, 1).toLowerCase();
            if (sub.equals("l")) {
                noOfResources += 3;
            } else {
                noOfResources += 1;
            }
        }
        return noOfResources;
    }

    /**
     * Returns a list of fine history of this user
     * @return fineHistory a list of all the fines this user has accumulated.
     */
    public ArrayList<Integer> getFineHistory() {
        return fineHistory;
    }

    /**
     * Sets this user to attend a new event.
     * @param eventID the title of the event to attend.
     */
    public void addNewEvent(String eventID) {
    	if (!checkAttended(eventID)) {
    		this.eventsAttended.add(eventID);
    		LibraryEvents.getEvent(eventID).RSVP();
    	} else {
    		System.out.println("Already attending");
    	}
        
    }
    
    /**
     * Checks whether this user has already attended this event.
     * @param eventID the id of the event the user wishes to attend.
     * @return true or false whether the user has already attended.
     */
    private boolean checkAttended(String eventID) {
    	for (String elem : this.eventsAttended) {
    		if (elem.equals(eventID)) {
    			return true;
    		}
    	}
    	return false;
	}

	/**
     * Sets this user to attend an event.
     * @param eventID the eventID of the event to attend.
     */
    public void addEvent(String eventID) {
        this.eventsAttended.add(eventID);
    }
    
    /**
     * Remove an event from a user's currently attending list.
     * @param eventID the event to remove
     */
    public void removeEvent(String eventID) {
    	//if user is attending, remove.
    	if (checkAttended(eventID)) {
    		this.eventsAttended.remove(eventID);
    		LibraryEvents.getEvent(eventID).cancelRSVP();
    		System.out.println("Cancelled RSVP");
    	} else {
    		System.out.println("Not attending.");
    	}
    }
    
    /**
     * Returns a list of all the events this user attended.
     * @return eventsAttanded the list of events this user attended.
     */
    public ArrayList<String> getAllEventsAttended() {
        return this.eventsAttended;
    }
    
    /**
     * Sets a list of events this user has attended.
     * @param eventsAttended the list of events this user has attended.
     */
    public void setEventsAttanded(ArrayList<String> eventsAttended) {
        this.eventsAttended = eventsAttended;
    }
}
