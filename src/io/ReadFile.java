package io;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import event.Event;
import library.LibraryEvents;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import library.Library;
import resources.*;
import user.Librarian;
import user.User;

@SuppressWarnings("Duplicates")
/**
 * <h1>ReadFile.</h1>
 * <p>This class reads data from files saved on disk.</p>
 * @author Samuel Jankinson, James Carter, Etienne Badoche
 */
public class ReadFile extends IO {
	private static FileReader file = null;
	private static BufferedReader reader = null;
	private static String currentLine = null;

	/**
	 * Returns users read from file.
	 * @return userList
	 * A list of users stores on disk.
	 */
	public static ArrayList<User> readUsers() {
		JSONParser parser = new JSONParser();
		JSONArray resourceArray = new JSONArray();
		JSONArray transactionArray = new JSONArray();
		JSONArray borrowHistoryArray = new JSONArray();
		JSONArray requestedArray = new JSONArray();
		JSONArray reservedArray = new JSONArray();
        JSONArray eventsArray = new JSONArray();
		ArrayList<User> userList = new ArrayList<>();
		try {
			file = new FileReader(IO.getUsersFilePath());
			reader = new BufferedReader(file);
			while ((currentLine = reader.readLine()) != null) {
				JSONObject object = (JSONObject) parser.parse(currentLine);
				User user = new User((String) object.get("username"), (String) object.get("firstName"),
						(String) object.get("lastName"), (String) object.get("mobileNumber"),
						(String) object.get("firstLineAddress"), (String) object.get("secondLineAddress"),
						(String) object.get("postCode"), (String) object.get("townName"),
						Double.parseDouble((String) object.get("accountBalance")), (String) object.get("imageAddress"),
					(String) object.get("email"));
				Date login = null;
				//Translate the string date format to a Date object
				try {
					login = new SimpleDateFormat("dd/MM/yyyy").parse((String) object.get("lastLogin"));
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				user.setLastLogin(login);

				resourceArray = (JSONArray) object.get("resourceBorrow");
				ArrayList<String> borrowedResources = new ArrayList<String>();
				if (resourceArray != null) {
					for (Object resource : resourceArray) {
						String stringResource = (String) resource;
						borrowedResources.add(stringResource);
					}
					user.setResourceCurrentlyBorrowed(borrowedResources);
				}

				transactionArray = (JSONArray) object.get("transactionHistory");
				if (transactionArray != null) {
					for (Object transactionInformation : transactionArray) {
						JSONObject transactionInformationArray = (JSONObject) transactionInformation;
						String[] data = new String[3];
						data[0] = (String) transactionInformationArray.get("System");
						data[1] = (String) transactionInformationArray.get("Date");
						data[2] = (String) transactionInformationArray.get("Amount");
						user.addToTransactionHistory(data);
					}
				}

				borrowHistoryArray = (JSONArray) object.get("borrowHistory");
				if (borrowHistoryArray != null) {
					for (Object borrowInformation : borrowHistoryArray) {
						JSONObject borrowInformationArray = (JSONObject) borrowInformation;
						String[] data = new String[2];
						data[0] = (String) borrowInformationArray.get("ID");
						data[1] = (String) borrowInformationArray.get("Date");
						user.addToBorrowHistory(data);
					}
				}

				requestedArray = (JSONArray) object.get("requested");
				if (requestedArray != null) {
					for (Object requestedResource : requestedArray) {
						String requestedResourceID = (String) requestedResource;
						user.requestResource(requestedResourceID);
					}
				}

				reservedArray = (JSONArray) object.get("reserved");
				if (reservedArray != null) {
					for (Object reservedResource : requestedArray) {
						String reservedResourceID = (String) reservedResource;
						user.addToReserved(reservedResourceID);
					}
				}
                eventsArray = (JSONArray) object.get("events");
				if(eventsArray != null){
				    for(Object event : eventsArray){
				        user.addEvent((String) event);
				        System.out.println("Event added to "+user.getUserName()+ ":" + (String) event);
                    }
                }
				System.out.println(user.getUserName() + " is added");
				userList.add(user);
			}

			reader.close();
			file.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find " + IO.getUsersFilePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR reading file " + IO.getUsersFilePath());
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("ERROR parsing users JSON");
			e.printStackTrace();
		}
		return userList;
	}

	/**
	 * Returns a list of librarians currently employed by the library.
	 * @return librarianList
	 * The list of librarians employed by this library.
	 */
	public static ArrayList<Librarian> readLibrarians() {
		JSONParser parser = new JSONParser();
		JSONArray resourceArray = new JSONArray();
		JSONArray transactionArray = new JSONArray();
		JSONArray borrowHistoryArray = new JSONArray();
		JSONArray requestedArray = new JSONArray();
		JSONArray reservedArray = new JSONArray();
		JSONArray eventsArray = new JSONArray();
		ArrayList<Librarian> librarianList = new ArrayList<>();
		try {
			file = new FileReader(IO.getLibrarianFilePath());
			reader = new BufferedReader(file);
			while ((currentLine = reader.readLine()) != null) {
				JSONObject object = (JSONObject) parser.parse(currentLine);
				Librarian librarian = new Librarian((String) object.get("username"), (String) object.get("firstName"),
						(String) object.get("lastName"),
						(String) object.get("mobileNumber"),
						(String) object.get("firstLineAddress"),
						(String) object.get("secondLineAddress"),
						(String) object.get("postCode"),
						(String) object.get("townName"),
						Double.parseDouble((String) object.get("accountBalance")),
						(String) object.get("imageAddress"),
						Integer.parseInt((String) object.get("empDay")),
						Integer.parseInt((String) object.get("empMonth")),
						Integer.parseInt((String) object.get("empYear")), (String) object.get("staffNumber"),
						Integer.parseInt((String) object.get("noOfEmploys")),
						(String) object.get("email"));
				Date login = null;
				//Translate the string date format to a Date object
				try {
					login = new SimpleDateFormat("dd/MM/yyyy").parse((String) object.get("lastLogin"));
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}
				librarian.setLastLogin(login);
				
				resourceArray = (JSONArray) object.get("resourceBorrow");
				ArrayList<String> borrowedResources = new ArrayList<String>();
				if (resourceArray != null) {
					for (Object resource : resourceArray) {
						String stringResource = (String) resource;
						borrowedResources.add(stringResource);
					}
					librarian.setResourceCurrentlyBorrowed(borrowedResources);
				}

				transactionArray = (JSONArray) object.get("transactionHistory");
				if (transactionArray != null) {
					for (Object transactionInformation : transactionArray) {
						JSONArray transactionInformationArray = (JSONArray) transactionInformation;
						String[] data = new String[3];
						data[0] = (String) transactionInformationArray.get(0);
						data[1] = (String) transactionInformationArray.get(1);
						data[2] = (String) transactionInformationArray.get(2);
						librarian.addToTransactionHistory(data);
					}
				}

				borrowHistoryArray = (JSONArray) object.get("borrowHistory");
				if (borrowHistoryArray != null) {
					for (Object borrowInformation : borrowHistoryArray) {
						JSONArray borrowInformationArray = (JSONArray) borrowInformation;
						String[] data = new String[2];
						data[1] = (String) borrowInformationArray.get(0);
						data[0] = (String) borrowInformationArray.get(1);
						librarian.addToBorrowHistory(data);
					}
				}

				requestedArray = (JSONArray) object.get("requested");
				if (requestedArray != null) {
					for (Object requestedResource : requestedArray) {
						String requestedResourceID = (String) requestedResource;
						librarian.requestResource(requestedResourceID);
					}
				}

				reservedArray = (JSONArray) object.get("reserved");
				if (reservedArray != null) {
					for (Object reservedResource : requestedArray) {
						String reservedResourceID = (String) reservedResource;
						librarian.addToReserved(reservedResourceID);
					}
				}
				eventsArray = (JSONArray) object.get("events");
				if(eventsArray != null){
					for(Object event : eventsArray){
						librarian.addEvent((String) event);
						System.out.println("Event added to "+librarian.getUserName()+ ":" + (String) event);
					}
				}
				librarianList.add(librarian);
			}
			reader.close();
			file.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find " + IO.getLibrarianFilePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR reading file " + IO.getLibrarianFilePath());
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("ERROR parsing users JSON");
			e.printStackTrace();
		}
		return librarianList;
	}

	/**
	 * Reads resources from Json file
	 * @param resourceJson object
	 * @return Resource object
	 */
	public static Resource readResourceFromJSON(JSONObject resourceJson){
	    Resource resource;
	    
	    String dateAdded = resourceJson.get("DateAdded").toString();
	    String title = resourceJson.get("Title").toString();
	    String year = resourceJson.get("Year").toString();
        String thumbnailImg = resourceJson.get("ThumbnailImage").toString();
        String uniqueID = resourceJson.get("ID").toString();

        int noOfCopies = Integer.parseInt(resourceJson.get("CopyAmount").toString());
        List<List<BorrowHistoryData>> borrowHistory = new ArrayList<>();
        List<BorrowHistoryData> currentData = new ArrayList<>();
        ArrayList<String> loanDurs = new ArrayList<>();

        JSONArray listOfLoanDur;
        listOfLoanDur = (JSONArray) resourceJson.get("LoanDurations");
        if (listOfLoanDur != null) {
            for (Object loanDur : listOfLoanDur) {
                String loanDurString = (String) loanDur;
                loanDurs.add(loanDurString);
            }
        }

        JSONArray bookBorrowHistoryArray;
        bookBorrowHistoryArray = (JSONArray) resourceJson.get("BorrowHistory");
        if (bookBorrowHistoryArray != null) {
            int i = 0;
            for (Object copyBorrowHistoryObject : bookBorrowHistoryArray) {
                JSONArray copyBorrowHistoryArray = (JSONArray) copyBorrowHistoryObject;
                List<BorrowHistoryData> copyBorrowHistoryData = new ArrayList<>();
                System.out.println("Loading Copy History for: " + uniqueID + ":" + i++);
                for (Object copyBorrowHistory : copyBorrowHistoryArray) {
                    JSONObject borrowHistoryObject = (JSONObject) copyBorrowHistory;
                    BorrowHistoryData borrowHistoryData = new BorrowHistoryData();
                    borrowHistoryData.setUserID(borrowHistoryObject.get("UserID").toString());
                    borrowHistoryData.setDateBorrowed(borrowHistoryObject.get("BorrowDate").toString());
                    borrowHistoryData.setDateReturned(borrowHistoryObject.get("ReturnDate").toString());
                    borrowHistoryData.setDateRequestedReturn(borrowHistoryObject.get("RequestedDate").toString());
                    copyBorrowHistoryData.add(borrowHistoryData);
                }
                borrowHistory.add(copyBorrowHistoryData);
            }
        }

        JSONArray currentBorrowData;
        currentBorrowData = (JSONArray) resourceJson.get("CurrentBorrowData");
        if (currentBorrowData != null) {
            for (Object copyCurrentBorrowDataObject : currentBorrowData) {
                JSONObject copyCurrentBorrowDataArray = (JSONObject) copyCurrentBorrowDataObject;

                BorrowHistoryData borrowHistoryData = new BorrowHistoryData();
                borrowHistoryData.setUserID(copyCurrentBorrowDataArray.get("UserID").toString());
                borrowHistoryData.setDateBorrowed(copyCurrentBorrowDataArray.get("DateBorrowed").toString());
                borrowHistoryData.setDateReturned(copyCurrentBorrowDataArray.get("DateReturned").toString());
                borrowHistoryData.setDateRequestedReturn(copyCurrentBorrowDataArray.get("DateRequestedReturn").toString());

                currentData.add(borrowHistoryData);
            }
        }

        resource = new Resource(year, title, thumbnailImg, uniqueID, dateAdded, noOfCopies, loanDurs, borrowHistory, currentData);

        JSONArray queueArray;
        queueArray = (JSONArray) resourceJson.get("ReservedQueue");
        if (queueArray != null) {
            for (Object user : queueArray) {
                String username = (String) user;
                resource.addUserToRequestQueue(username);
            }
        }

	    return resource;
    }

    /**
     * Returns a list of books owned by this library.
     * @return bookList
     * The list of books currently owned by this library.
     */
	public static ArrayList<Book> readBooksJSON(){
	    ArrayList<Book> bookList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(IO.getResourceFilePath());
            JSONObject resourcesObject = (JSONObject) parser.parse(fileReader);
            JSONArray books;
            books = (JSONArray) resourcesObject.get("Books");

            for (Object book : books){
                JSONObject bookJson = (JSONObject) book;

                Resource resource = readResourceFromJSON(bookJson);

                String author = bookJson.get("Author").toString();
                String genre = bookJson.get("Genre").toString();
                String isbn = bookJson.get("ISBN").toString();
                String publisher = bookJson.get("Publisher").toString();

                ArrayList<String> languages = new ArrayList<>();
                JSONArray languageArray = (JSONArray) bookJson.get("Languages");
                if (languageArray != null) {
                    for (Object language : languageArray) {
                        String stringLanguage = (String) language;
                        languages.add(stringLanguage);
                    }
                }

                Book b = new Book(resource, author, genre, isbn, publisher, languages);
                bookList.add(b);
            }


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }


        return bookList;
    }

    /**
     * Returns a list of DVDs owned by this library.
     * @return dvds
     * The list of dvds owned by this library.
     */
    public static ArrayList<DVD> readDvdsJSON(){
        ArrayList<DVD> dvdList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(IO.getResourceFilePath());
            JSONObject resourcesObject = (JSONObject) parser.parse(fileReader);
            JSONArray dvds;
            dvds = (JSONArray) resourcesObject.get("DvDs");

            for (Object dvd : dvds){
                JSONObject dvdJson = (JSONObject) dvd;

                Resource resource = readResourceFromJSON(dvdJson);

                String director = dvdJson.get("Director").toString();
                String runtime = dvdJson.get("Runtime").toString();
                String language = dvdJson.get("Language").toString();

                ArrayList<String> languages = new ArrayList<>();
                JSONArray languageArray = (JSONArray) dvdJson.get("Sub-languages");
                if (languageArray != null) {
                    for (Object lang : languageArray) {
                        String stringLanguage = (String) lang;
                        languages.add(stringLanguage);
                    }
                }

                DVD d = new DVD(resource, director, runtime, language, languages);
                dvdList.add(d);
            }


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }


        return dvdList;
    }

    /**
     * Returns a list of laptops owned by the library.
     * @return laptops
     * The list of laptops currently owned by the library.
     */
    public static  ArrayList<Laptop> readLaptopsJSON(){
        ArrayList<Laptop> laptopList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(IO.getResourceFilePath());
            JSONObject resourcesObject = (JSONObject) parser.parse(fileReader);
            JSONArray laptops;
            laptops = (JSONArray) resourcesObject.get("Laptops");

            for (Object laptop : laptops){
                JSONObject laptopJson = (JSONObject) laptop;

                Resource resource = readResourceFromJSON(laptopJson);

                String manufacturer = laptopJson.get("Manufacturer").toString();
                String model = laptopJson.get("Model").toString();
                String operatingSys = laptopJson.get("OperatingSys").toString();

                Laptop l = new Laptop(resource, manufacturer, model, operatingSys);
                laptopList.add(l);
            }


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }


        return laptopList;
    }

    /**
     * Returns a list of DVDs owned by this library.
     * @return dvds
     * The list of dvds owned by this library.
     */
    public static ArrayList<VideoGame> readVideoGamesJSON(){
        ArrayList<VideoGame> videoGamesList = new ArrayList<>();

        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(IO.getResourceFilePath());
            JSONObject resourcesObject = (JSONObject) parser.parse(fileReader);
            JSONArray videoGames;
            videoGames = (JSONArray) resourcesObject.get("VideoGames");

            for (Object videoGame : videoGames){
                JSONObject videoGameJson = (JSONObject) videoGame;

                Resource resource = readResourceFromJSON(videoGameJson);

                String publisher = videoGameJson.get("Publisher").toString();
                String genre = videoGameJson.get("Genre").toString();
                String multiplayerSupport = videoGameJson.get("MultiplayerSupport").toString();
                String certificateRating = videoGameJson.get("CertificateRating").toString();

                ArrayList<String> languages = new ArrayList<>();
                JSONArray languageArray = (JSONArray) videoGameJson.get("Languages");
                if (languageArray != null) {
                    for (Object language : languageArray) {
                        String stringLanguage = (String) language;
                        languages.add(stringLanguage);
                    }
                }

                VideoGame b = new VideoGame(resource, genre, certificateRating, publisher, multiplayerSupport, languages);
                videoGamesList.add(b);
            }


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }


        return videoGamesList;
    }

	/**
	 * Reads ratings from a file
	 * @return An array list of ratings
	 */
	public static ArrayList<String[]> readRatings() {
		JSONParser parser = new JSONParser();
		ArrayList<String[]> ratingList = new ArrayList<>();
		
		try {
			file = new FileReader(IO.getRatingsFilePath());
			reader = new BufferedReader(file);

			while ((currentLine = reader.readLine()) != null) {
				JSONObject object = (JSONObject) parser.parse(currentLine);
				String[] newRating = new String[4];
				String id = ((String) object.get("id"));
				String message = ((String) object.get("message"));
				String rating = ((String) object.get("rating"));
				String username = ((String) object.get("username"));
				newRating[0] = id;
				newRating[1] = message;
				newRating[2] = rating;
				newRating[3] = username;
				ratingList.add(newRating);
			}
			
			reader.close();
			file.close();
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find " + IO.getRatingsFilePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR reading file " + IO.getRatingsFilePath());
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("ERROR parsing ratings JSON");
			e.printStackTrace();
		}
		
		return ratingList;
	}

	/**
	 * Returns a list of events which have been read from a file.
	 * @return eventsList the list of events read from file.
	 */
	public static ArrayList<Event> readEvents() {
		JSONParser parser = new JSONParser();
		ArrayList<Event> eventsList = new ArrayList<>();

		try {
			file = new FileReader(IO.getEventFilepath());
			reader = new BufferedReader(file);

			while((currentLine = reader.readLine()) != null) {
				JSONObject object = (JSONObject) parser.parse(currentLine);
				String eventID = (String) object.get("eventID");
				String title = (String) object.get("title");
				String date = (String) object.get("date");
				String time = (String) object.get("time");
				int maxNumber = Integer.valueOf((String) object.get("maxNumberOfAttending"));
				int current = Integer.valueOf((String) object.get("currentNumberOfAttending"));
				String description = (String) object.get("description");

				eventsList.add(new Event(eventID, title, date, time, 
						maxNumber, current, description));
			}
			reader.close();
			file.close();
		} catch (FileNotFoundException e) { 
			System.out.println("Cannot find " + IO.getRatingsFilePath());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("ERROR reading file " + IO.getRatingsFilePath());
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("ERROR parsing ratings JSON");
			e.printStackTrace();
		}
		return eventsList;
	}
}
