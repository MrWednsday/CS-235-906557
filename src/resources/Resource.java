package resources;

import library.Email;
import library.Library;
import user.User;
import utils.Queue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * <h1>Resource.</h1>
 * <p>Resource is an abstract class which provides essential attributes for all resource types</p>
 *
 * @author Peter Daish, James Carter, Dominik Wojtasiewicz
 * @version 1.0
 * @since 07/12/2018
 */
public class Resource {
    protected String year; //The year this resource was published/released.
    protected String title; //The title of this resource
    protected String thumbnailImageRef; //The filepath of this resource's avatar.
    protected String uniqueID; //The unique id of this resource.
    protected final String dateAdded; // Date resources was added, final because the date is set once.
    protected Queue<String> queueOfReservations; //The queue of current reservations for this resource.
    protected List<CopyData> copiesList; //The list of copies owned by this library for this resource.
    protected ResourceStatData resourceStatData; // The amount of times this resource has been borrowed.
    private double FINE; //The daily fine for an overdue resource. CONSTANT
    private double MAX_FINE; //The maximum fine a single resource can reach. CONSTANT

    private List<List<BorrowHistoryData>> copyBorrowHistory; // All of the borrow history for this resource
    private List<BorrowHistoryData> currentBorrowData; // The current borrow data for this resource
    private List<String> loanDurations; // All of the loan durations for this resource

    /**
     * The constructor for a Resource
     *
     * @param year              The year this resource was published/released.
     * @param title             The title of this resource.
     * @param thumbnailImageRef The filepath of the avatar for this resource.
     * @param uniqueID          The unique id of this resource.
     * @param dateAdded			The date the resource was added.
     * @param noOfCopies        The number of copies of this resource currently owned by the library.
     * @param loanDuration      The loan duration of this resource.
     * @param copyBorrowHistory The borrow history of a resource.
     * @param currentBorrowData The information for borrower and dates associated with a copy of a resource.
     */
    public Resource(String year, String title, String thumbnailImageRef,
                    String uniqueID, String dateAdded, Integer noOfCopies, List<String> loanDuration,
                    List<List<BorrowHistoryData>> copyBorrowHistory,
                    List<BorrowHistoryData> currentBorrowData) {

        this.year = year;
        this.title = title;
        this.thumbnailImageRef = thumbnailImageRef;
        this.uniqueID = uniqueID;
        this.dateAdded = dateAdded;
        this.queueOfReservations = new Queue<String>();
        this.copiesList = new ArrayList<>();
        this.resourceStatData = new ResourceStatData();
        this.copyBorrowHistory = copyBorrowHistory;
        this.currentBorrowData = currentBorrowData;
        this.loanDurations = loanDuration;

        for (int i = 0; i < noOfCopies; i++) {
            CopyData newCopy = null;
            if (copyBorrowHistory.isEmpty() || copyBorrowHistory.get(i) == null || currentBorrowData.isEmpty() || currentBorrowData.get(i) == null) {
                newCopy = new CopyData(String.valueOf(i), new ArrayList<>(), null, loanDuration.get(i));
            } else {
                newCopy = new CopyData(String.valueOf(i), copyBorrowHistory.get(i), currentBorrowData.get(i), loanDuration.get(i));
            }
            copiesList.add(newCopy);
        }
    }

    public Resource(Resource r){
        this(r.getYear(), r.getTitle(), r.getThumbnailImageRef(),
                r.getUniqueID(), r.getDateAdded(), r.getNoOfCopies(), r.getLoanDurations(),
                r.getAllBorrowHistory(), r.getCurrentBorrowData());
    }

    /* #############################################################
     * ########BELOW ARE THE GETTERS AND SETTERS OF Resource########
     * #############################################################
     */
    
    /**
     * Find the six digit string date on which the 
     * resource was added and returns it.
     * @return dateAdded
     */
    public String getDateAdded() {
		return dateAdded;
	}

	/**
     * Finds the year the resource was published
     *
     * @return year
     * The year this resource was published/released.
     */
    public String getYear() {
        return this.year;
    }

    /**
     * Allocates the year the resource was published in
     *
     * @param year The year this resource was published/released.
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * Finds the title of the Resource.
     *
     * @return title
     * The title of this resource.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Allocates a title to this resource.
     *
     * @param title The title of this resource.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Finds the filepath of the avatar of this Resource.
     *
     * @return thumbnailImageRef
     * The filepath for the avatar of this resource.
     */
    public String getThumbnailImageRef() {
        return this.thumbnailImageRef;
    }

    /**
     * Allocates a filepath to the avatar of this Resource.
     *
     * @param thumbnailImageRef The filepath for the avatar of this resource.
     */
    public void setThumbnailImageRef(String thumbnailImageRef) {
        this.thumbnailImageRef = thumbnailImageRef;
    }

    /**
     * Returns information about a copy.
     *
     * @param id The id of the copy.
     * @return CopyData
     * The information about a copy
     */
    public CopyData getCopyInfo(Integer id) {
        return this.copiesList.get(id);
    }

    /**
     * Returns the unique id of this resource.
     *
     * @return uniqueID
     * The unique id of this resource.
     */
    public String getUniqueID() {
        return this.uniqueID;
    }

    /**
     * Allocates a uniqueID for this Resource.
     *
     * @param uniqueID The uniqueID of this resource.
     */
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    /**
     * Returns the list of copies.
     *
     * @return copiesList
     * The list of copies of this resource.
     */
    public List<CopyData> getArrayListOfCopies() {
        return this.copiesList;
    }

    /**
     * Returns the reservation queue of copies of this Resource
     *
     * @return queueOfReservations
     * The queue of users who have reserved a copy of this resource.
     */
    public Queue getQueueOfReservations() {
        return this.queueOfReservations;
    }

    /**
     * Returns the borrow history of this Resource.
     *
     * @param copyID of the copy you want to add to borrow history.
     * @return BorrowHistoryData
     * The borrow history of a copy of this resource.
     */
    public List<BorrowHistoryData> getBorrowHistory(String copyID) {
        return this.copiesList.get(Integer.parseInt(copyID)).getBorrowHistory();
    }

    /**
     * Returns a list of list of borrow history objects for borrow history.
     * @return list of list of borrow history objects
     */
    public List<List<BorrowHistoryData>> getAllBorrowHistory(){
        return this.copyBorrowHistory;
    }

    /**
     * returns a list of borrow history data
     * @return list of borrow data for this resource.
     */
    public List<BorrowHistoryData> getCurrentBorrowData(){
        return this.currentBorrowData;
    }

    /**
     * Sets the borrow history of a copy of this resource.
     *
     * @param copyID        The copy id.
     * @param borrowHistory The borrow history of this copy.
     */
    public void setBorrowHistory(String copyID, List<BorrowHistoryData> borrowHistory) {
        copiesList.get(Integer.parseInt(copyID)).setBorrowHistory(borrowHistory);
    }

    /**
     * Returns the total number of copies of this resource.
     *
     * @return Total number of copies of this resource.
     */
    public int getNoOfCopies() {
        return copiesList.size();
    }
    /* #############################################################
     * ########  BELOW ARE THE COMPLEX METHODS OF Resource  ########
     * #############################################################
     */

    /**
     * Returns the current loanee of a copy
     *
     * @param copyID The copy id
     * @return The username of the user currently loaning this copy.
     */
    public String getCurrentLoanee(String copyID) {
        return copiesList.get(Integer.parseInt(copyID)).getCurrentInfo().getUserID();
    }

    /**
     * Adds a new copy to this Resource.
     *
     * @param loanDuration The length of the loan for this copy
     */
    public void addCopy(String loanDuration) {
        copiesList.add(new CopyData(String.valueOf(copiesList.size() - 1),
                new ArrayList<BorrowHistoryData>(), null, loanDuration));
    }

    /**
     * Removes a copy from the Resource
     *
     * @param copyID the id of the copy to remove.
     */
    public void removeCopy(String copyID) {
        copiesList.remove(Integer.valueOf(copyID));
    }

    /**
     * Adds a User to copy request queue.
     *
     * @param userForQueue The user who requested a copy.
     */
    public void addUserToRequestQueue(String userForQueue) {
        this.queueOfReservations.enqueue(userForQueue);
        User userAtFrontOfQueue = Library.getUser(this.queueOfReservations.peek());
        if (userAtFrontOfQueue != null)
            checkReservations();
    }

    /**
     * Check if user has reserved a copy
     */
    public void checkReservations() {
        User userAtFrontOfQueue = Library.getUser(this.queueOfReservations.peek());
        if (checkIfAvailable()) {
            for (CopyData copy : copiesList) {
                if (copy.isAvailable()) {
                    copy.reserveCopy(queueOfReservations.peek());
                    userAtFrontOfQueue.moveToReserved(getUniqueID());
                    return;
                }
            }
        }
    }

    /**
     * Removes a User from request for a copy of a resource.
     *
     * @return userAtFrontOfQueue
     * The user who first requested a copy
     */
    public User removeUserFromRequestQueue() {
        User userAtFrontOfQueue = Library.getUser(this.queueOfReservations.peek());
        this.queueOfReservations.dequeue();
        return userAtFrontOfQueue;
    }

    /**
     * Gets the head of the queueOfReservations
     *
     * @return The user who is head in queueOfReservations.
     */
    public User peekQueueOfReservations() {
        return Library.getUser(this.queueOfReservations.peek());
    }

    /**
     * Checks if someone requested a book.
     *
     * @return False if no user is currently requesting a book, True otherwise.
     */
    public Boolean checkIfRequested() {
        return queueOfReservations.isEmpty();
    }

    /**
     * Returns a simple string representation of this Resource.
     *
     * @return year and title
     * The year and title of this resource.
     */
    public String toSingleString() {
        return year + title;
    }

    /**
     * Returns the loan duration of a resource.
     *
     * @param copyID The copy ID
     * @return The loan duration of a copy of a resource.
     */
    public String getLoanDuration(String copyID) {
        return copiesList.get(Integer.valueOf(copyID)).getLoanDuration();
    }

    /**
     * Sets a user to loan a copy of a resource.
     *
     * @param copyID   The copy id of the copy to be loaned.
     * @param username The username of the user who is loaning the copy.
     */
    public void loanResource(String copyID, String username) {
        copiesList.get((Integer.valueOf(copyID))).loanCopy(username);

        SimpleDateFormat dateFormatDMY = new SimpleDateFormat("dd-MM-yyyy");
        resourceStatData.addBorrowDate(dateFormatDMY.format(new Date()));
    }

    /**
     * Sets a request to return a copy of a resource.
     *
     * @param copyId The copy id of the resource to be returned.
     */
    public void requestReturn(Integer copyId) {
        copiesList.get(copyId).requestReturn(this.title);
    }

    /**
     * Sets a copy of a resource which has been returned.
     *
     * @param copyId The id of the copy of the resource.
     */
    public void returnResource(Integer copyId) {
        copiesList.get(copyId).returnCopy();

        if (!queueOfReservations.isEmpty()) {
            copiesList.get(copyId).reserveCopy(queueOfReservations.peek());
            User userAtFrontOfQueue = Library.getUser(this.queueOfReservations.peek());
            userAtFrontOfQueue.moveToReserved(getUniqueID());
        }
    }

    /**
     * Checks if a copy of a resource is available
     *
     * @return True if available, False if not.
     */
    public boolean checkIfAvailable() {
        for (CopyData copy : copiesList) {
            if (copy.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a copy of a resource is overdue.
     *
     * @param copyID The copy id.
     * @return True if copy is overdue, False otherwise.
     */
    public Boolean checkIfOverdue(Integer copyID) {
        BorrowHistoryData data = this.copiesList.get(copyID).getCurrentInfo();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        if (data.getDateRequestedReturn().equals("")) {
            return false;
        } else {
            try {
                Date date1 = dataFormat.parse(data.getDateRequestedReturn());
                Date date2 = dataFormat.parse(Library.getCurrentDateTime());
                return date2.after(date1);
            } catch (ParseException e) {
                System.out.println("Error Resource checkIfOverdue ParseException");
            }
            return false;
        }
    }

    /**
     * Checks if a specific copy of a resource is available.
     *
     * @param copyID The id of the specific copy.
     * @return True if the specific copy is available, False otherwise.
     */
    public boolean checkIfCopyAvailable(String copyID) {
        return copiesList.get(Integer.parseInt(copyID)).isAvailable();
    }

    /**
     * Returns the daily fine rate of this resource.
     *
     * @return FINE
     * The daily fine rate of this resource.
     */
    public double getFineAmount() {
        return this.FINE;
    }

    /**
     * Returns the maximum fine this resource can reach.
     *
     * @return MAX_FINE
     * The maximum fine a resource can reach.
     */
    public double getMaxFine() {
        return this.MAX_FINE;
    }

    /**
     * Returns the copy which can be returned the earliest.
     *
     * @return The id of the copy which can be returned the earliest.
     */
    public Integer getCopyWithEarlestReturn() {

        Calendar currentlyEarliestDate = copiesList.get(0).getEstimatedReturnData();
        CopyData currentEarlyCopy = copiesList.get(0);
        // Loop over every resource
        for (CopyData copy : copiesList) {
            Calendar returnDate = copy.getEstimatedReturnData();

            // If the estimated return date is earlier than the current earliest
            // change the current to this copy.
            if (returnDate.before(currentlyEarliestDate)) {
                currentlyEarliestDate = returnDate;
                currentEarlyCopy = copy;
            }
        }
        return Integer.parseInt(currentEarlyCopy.getId());
    }

    /**
     * Returns ResourceStatData data object
     * @return ResourceStatData
     */
    public ResourceStatData getResourceStatData(){
        return resourceStatData;
    }

    /**
     * Returns loan duration
     * @return List of loan durations for this resource.
     */
    public List<String> getLoanDurations(){
        return this.loanDurations;
    }
}