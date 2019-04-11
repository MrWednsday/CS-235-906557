package resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import library.Email;
import library.Library;

/**
 * <h1>CopyData.</h1>
 * <p>This class models a copy of a resource.</p>
 * @author James Carter, Dominik Wojtasiewicz, Etienne Badoche
 */
public class CopyData {
	private String id;
	private List<BorrowHistoryData> borrowHistory; // [4] [0] - User ID [1] - date borrowed [2] - date returned [3] - requested return date
	private BorrowHistoryData currentInfo; // [4] [0] - User ID [1] - date borrowed [2] - date returned [3] - requested return date
	private String loanDuration;
	private String reservedUserID;
	
	/**
	 * Constructor for making a copy of a resource.
	 * @param copyID
	 * The copy id of this copy.
	 * @param borrowHistory
	 * The borrow history associated with this copy.
	 * @param currentInfo
	 * The information about the current loan of this copy.
	 * @param loanDuration
	 * The loan duration of this copy.
	 */
	public CopyData(String copyID, List<BorrowHistoryData> borrowHistory, BorrowHistoryData currentInfo, String loanDuration) {
		this.id = copyID;
		this.borrowHistory = borrowHistory;
		this.loanDuration = loanDuration;
		
		if (currentInfo == null) {
			this.currentInfo = new BorrowHistoryData();
		} else {
			this.currentInfo = currentInfo;
		}
		reservedUserID = "";
	}

	/**
	 * Returns the copy id of this copy.
	 * @return id
	 * The copy id of this copy.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this copy.
	 * @param id
	 * The id to set this copy's id to.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the borrow history associated with this copy.
	 * @return borrowHistory
	 * The borrowHistory of this copy.
	 */
	public List<BorrowHistoryData> getBorrowHistory() {
		return borrowHistory;
	}

	/**
	 * Sets the borrowHistory of this copy.
	 * @param borrowHistory
	 * The new borrowHistory of this copy.
	 */
	public void setBorrowHistory(List<BorrowHistoryData> borrowHistory) {
		this.borrowHistory = borrowHistory;
	}

	/**
	 * Returns the information about the current loan.
	 * @return currentInfo
	 * The information about the current loan of this copy.
	 */
	public BorrowHistoryData getCurrentInfo() {
		return currentInfo;
	}

	/**
	 * Sets the information about the current loan.
	 * @param currentInfo
	 * The new information about the current loan.
	 */
	public void setCurrentInfo(BorrowHistoryData currentInfo) {
		this.currentInfo = currentInfo;
	}

	/**
	 * Returns the loan duration of this copy.
	 * @return loanDuration
	 * The duration this copy can be loaned for.
	 */
	public String getLoanDuration() {
		return loanDuration;
	}

	/**
	 * Sets the loan duration of this copy.
	 * @param loanDuration
	 * The new duration this copy can be loaned for.
	 */
	public void setLoanDuration(String loanDuration) {
		this.loanDuration = loanDuration;
	}
	
	/**
	 * Returns whether the copy is currently available, or whether it is on loan.
	 * @return True if the copy is available, False otherwise.
	 */
	public boolean isAvailable() {
		if (currentInfo.getDateBorrowed().equals("") && !isReserved()) {
			return true;
		}

		return false;
	}
	
	/**
	 * Returns whether a copy is reserved by a user.
	 * @return True if this copy is reserved, False otherwise.
	 */
	public boolean isReserved() {
		if (reservedUserID == "" || reservedUserID == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the user who reserved this copy
	 * @return reservedUserID
	 * The id of the user who reserved this copy.
	 */
	public String getReservedUser() {
		if (reservedUserID != "" || reservedUserID != null) {
			return reservedUserID;
		}
		return null;
	}
	
	/**
	 * Loans this copy to a user.
	 * @param username
	 * The user who is loaning this copy.
	 */
	public void loanCopy(String username) {
		this.currentInfo.setUserID(username);
		this.currentInfo.setDateBorrowed(Library.getCurrentDateTime());
	}
	
	/**
	 * Sets a date this copy needs to be returned by.
	 */
	public void requestReturn(String title) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String date = dateFormat.format(getEstimatedReturnData().getTime());
		Email.returnNotification(title, Library.getUser(currentInfo.getUserID()).getFirstName(), Library.getUser(currentInfo.getUserID()).getEmail());
		this.currentInfo.setDateRequestedReturn(date);
	}
	
	/**
	 * Allows for this copy to be returned.
	 */
	public void returnCopy() {
		currentInfo.setDateReturned(Library.getCurrentDateTime());
		this.borrowHistory.add(currentInfo);
		currentInfo = new BorrowHistoryData();
		System.out.println("Copy has been returned");
	}
	
	/**
	 * Adds a reservation to this copy.
	 * @param userID
	 * The user who reserved this copy.
	 */
	public void reserveCopy(String userID) {
		this.reservedUserID = userID;
	}
	
	/**
	 * Sets a return date for this copy.
	 * @return cal
	 * The calendar holding the return by date for this copy once reserved by a user.
	 */
	public Calendar getEstimatedReturnData() {
		String date = currentInfo.getDateBorrowed().split(" ")[0];
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy" ); //Not Required
		Date selectedDate = null;
		Calendar cal = Calendar.getInstance();

		System.out.println("Date: " + date);

		try {
			selectedDate=dateFormat.parse(date);// replace it with selected date

			cal.setTime(selectedDate);
			cal.add(Calendar.DATE,Integer.valueOf(loanDuration));

			//does a thing
			if (cal.getTime().before(new Date())) {
				cal.setTime(new Date());
				cal.add(Calendar.DATE, 1);
			}
		} catch (ParseException e) {
			System.out.println("date in wrong format");
		}
		
		return cal;
	}
}
