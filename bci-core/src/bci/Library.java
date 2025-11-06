package bci;

import bci.exceptions.*;
import bci.notification.*;
import bci.request.*;
import bci.rules.*;
import bci.user.*;
import bci.work.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/** Class that represents the library as a whole. */
public class Library implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;


    /** Current day. */
    private int _currentDay = 1;

    /** Library has been changed. */
    private boolean _changed = false;

    private int _nextUserId =1;
    private int _nextWorkId = 1;
    
    private String _filename = null;
    //FIXME maybe define attributes
    //FIXME maybe implement constructor
    //FIXME maybe implement methods

    /** Users. */
    private final  Map <Integer, User> _users = new TreeMap<>();

    /** Works. */
    private final Map<Integer, Work> _works = new TreeMap<>();

    private final Map<String, Creator> _creators = new TreeMap<>();

     /** Rules. */

    private List<Rule> _rules = new ArrayList<>();


    public Library() {
        addRules();
    }

    public void addRules(){
        _rules.add(new DoubleRequestRule());
        _rules.add(new LimitRequestRule());
        _rules.add(new MaxPriceRule());
        _rules.add(new NoSuspendedRule());
        _rules.add(new NoReferenceRule());
        _rules.add(new NoCopiesRule());
    }
    

    /**
     * Marks the library as changed.
     */
    public void setChanged(boolean changed) {
        _changed = changed;
    }


    /**
     * Checks if the library has been changed.
     *
     * @return true if the library has been changed, false otherwise
     */
    public boolean changed() {
        return _changed;
    }

    public Map<Integer, User> getUsers() {
        return _users;
    }

    /**
     * Increments and returns the next work ID.
     * 
     * @return next work ID
     */
    public int getAndIncNextWorkId() {
        int currNextWorkId = _nextWorkId;
        _nextWorkId++;
        return currNextWorkId;
    }


    /** 
     * @return filename
     */
    public String getFilename() {
        return _filename;
    }


    /** 
     * Sets the filename.
     */
    public void setFilename(String filename) {
        _filename = filename;
        _changed=true;
    }

    
    /**
     * @return the current date
     */
    public int getCurrentDate() {
        return _currentDay;
    } 
    

    /**
     * Advances the current date by required days.
     * 
     * @param number of days to advance
     * @return the new current day
     */
    public int advanceDate(int days) {
        if (days <= 0) {
            return _currentDay;
        }
        _currentDay += days;
        setChanged(true);

        return _currentDay;
    }


    /**
     * Provides a list of all works in the library, ordered by their
     * identifiers as stored in the internal map.
     *
     * @return a list of all works in the library
     */
    public List<Work> displayWorks() {
        return new ArrayList<>(_works.values());
    }


    /**
     * Provides the work in the library that matches the given identifier.
     *
     * @param workID (the identifier of the work to retrieve)
     * @return the work corresponding to the given ID
     */
    public Work displayWork(int workID) {
        return _works.get(workID);
    }


    /**
     * Provides a list of all works in the library created by the specified creator.
     * The resulting list is sorted lexicographically by the works' titles,
     * ignoring differences between uppercase and lowercase letters.
     *
     * @param creatorName the name of the creator whose works are to be retrieved
     * @return a list of works associated to the specified creator
     */
    public List<Work> displayWorksByCreator(String creatorName) {
        return _works.values().stream()
            .filter(w -> w.hasCreator(creatorName))  
            .sorted(Comparator.comparing(Work::getTitle, String.CASE_INSENSITIVE_ORDER))
            .collect(Collectors.toList());
    }


    /**
     * Read the text input file at the beginning of the program and populates the
     * instances of the various possible types (books, DVDs, users).
     *     
     * @param filename name of the file to load
     * @throws UnrecognizedEntryException
     * @throws IOException
     */
    void importFile(String filename) throws IOException, UnrecognizedEntryException {
         var reader = new BufferedReader(new FileReader(filename));
         String line;
         while ((line = reader.readLine()) != null) {
             var fields = line.split(":");
             process(fields);
         }
         reader.close();
         setChanged(true);
    }


    /**
     * Registers a new user in the library.
     * 
     * @param name  User's name
     * @param email User's email
     * @throws DuplicateUserException
     * @return the registered user
     */
    public User registerUser(String name, String email) throws FailedUserRegistrationException{

        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new FailedUserRegistrationException(name, email);
        }

        User newUser = new User(_nextUserId++, name, email);
        _users.put(newUser.getUserID(), newUser);
        setChanged(true);
        return newUser;
    }


    /**
     * Increments to return the current next user ID.
     * 
     * @return next user ID
     */
    public int getAndIncNextUserId() {
        int currNextUserId = _nextUserId;
        _nextUserId++;
        return currNextUserId; 
    }


    /**
     * Displays the user with the given ID.
     * 
     * @param userID
     * @return the user with the given ID, or null if not found
     */
    public User showUser(int userID) throws DoesNotExistUserException {
        return findUser(userID);
    }

    
    /**
     * Provides a list of all users in the library, ordered by their
     * usernames and, in case of ties, by their user IDs.
     * 
     * @return a list of all users in the library
     */
    public List<User> showAllUsers() {
        return _users.values().stream().sorted(Comparator.comparing(User::getUsername)
                    .thenComparing(User::getUserID))
                    .collect(Collectors.toList());
    }

   
    /**
     * Processes a line from the input file based on its type (USER, BOOK, DVD).
     * 
     * @param fields
     * @throws UnrecognizedEntryException
     */
    public void process(String[] fields) throws UnrecognizedEntryException {
        switch (fields[0]) {
           case "USER" -> processUser(fields);
           case "DVD" , "BOOK" -> processWork(fields);
           default -> throw new UnrecognizedEntryException(fields[0]);
        }
    }


    /**
     * Processes a user entry from the input file and adds it to the library.
     * 
     * @param fields
     * @return the created user
     */
    public User processUser(String... fields) {
        String name = fields[1];
        String email = fields[2];

        int userId = getAndIncNextUserId();
        User user = new User(userId, name, email);
        _users.put(userId, user);

        setChanged(true); 

        return user;
    }


    /**
     * Processes a work entry (BOOK or DVD) from the input file and adds it to the library.
     * 
     * @param fields
     * @throws UnrecognizedEntryException
     * @return the created work
     */
    public Work processWork(String[] fields) throws UnrecognizedEntryException {
        String type = fields[0];
        String title = fields[1];
        String creatorsField = fields[2];
        int price = Integer.parseInt(fields[3]);
        String categoryStr = fields[4];
        String isbnORigac = fields[5];
        int copies = Integer.parseInt(fields[6]);
        int workId = getAndIncNextWorkId();

        Category categoryObj;
        switch (categoryStr) {
            case "FICTION" -> categoryObj = new Fiction();
            case "SCITECH" -> categoryObj = new Scitech();
            case "REFERENCE" -> categoryObj = new Reference();
            default -> throw new UnrecognizedEntryException(categoryStr);
        }

        Work work;
        switch (type) {
            case "BOOK": {
                List<Creator> creators = Arrays.stream(creatorsField.split(","))
                                            .map(name -> name.trim())
                                            .map(name -> new Creator(name))
                                            .toList();

                work = new Book(workId, price, title, categoryObj, copies, isbnORigac, creators);

                for (Creator c : creators) {
                    c.addWork(work);  // addWork deve inserir no Map<Integer, Work> do creator
                }
                break;
            }

            case "DVD": {
                Creator director = new Creator(creatorsField.trim());
                work = new DVD(workId, price, title, categoryObj, copies, isbnORigac, director);
                director.addWork(work);
                break;
            }

            default:
                throw new UnrecognizedEntryException(type);
        }
        _works.put(workId, work);
        setChanged(true);
        return work;
    }

    public User findUser(int userId) throws DoesNotExistUserException {
        User user = _users.get(userId);
        if (user == null) {
            throw new DoesNotExistUserException(userId);
        }
        return user;
    }


    public Work findWork(int workId) throws DoesNotExistWorkException {
        Work work = _works.get(workId);
        if (work == null) {
            throw new DoesNotExistWorkException(workId);
        }
        return work;
    }

    public int requestWork(int userId, int workId) throws RuleNotFollowedException,
    DoesNotExistUserException, DoesNotExistWorkException {
        User user = findUser(userId);
        Work work = findWork(workId);

        for (Rule r : _rules) {
            r.validate(work, user);
        }

        work.decrementAvailableCopies();
        int returnDay = _currentDay + user.getBehaviour().getMaxReturnDays(work);
        Request request = new Request(workId, returnDay);
        user.addRequest(request);
        setChanged(true);

        return returnDay;
    }

    public int returnWork(int userId, int workId) throws DoesNotExistUserException,
    DoesNotExistWorkException, NotRequestedException {
        User user = findUser(userId);
        Work work = findWork(workId);
        boolean notify = needToNotify(work, 1);

        Request activeRequest = user.getRequests().get(workId);

        if (activeRequest == null || activeRequest.isReturned()) {
            throw new NotRequestedException(workId, userId);
        }

       
        int requestFine = activeRequest.calculateFine(_currentDay);

        int userFine = user.updateFine(requestFine);

        activeRequest.markAsReturned(_currentDay);

        work.incrementAvailableCopies();

        if (notify) {
            work.notifyAvailabilityObservers(new AvailabilityNotification(work));
        }
        
        user.updateBehaviourAfterReturn(activeRequest);
        setChanged(true);

        return userFine;
    }

    
    public void payFine(int userId) throws ActiveUserException, DoesNotExistUserException {
        User user = findUser(userId);
        if (user.isActive())
            throw new ActiveUserException(userId);
            
        user.payFine(_currentDay);
        setChanged(true);
    }
    

    public List<Notification> showUserNotifications(int userId) throws DoesNotExistUserException {
        User user = findUser(userId);
        return user.getUserNotifications();
    }


    public List<Work> searchWorksByTerm(String term) {
        List <Work> correspondedWorks = new ArrayList<>();

        for (Work work : _works.values()) {
            if (work.correspondsToTerm(term)) {
                correspondedWorks.add(work);
            }
        }
        correspondedWorks.sort(Comparator.comparingInt(Work ::getWorkID));
        return correspondedWorks;
    }


    public void removeWorkFromCreators(int workID) throws DoesNotExistWorkException {
        for (Creator creator : _creators.values()) {
            creator.removeWork(workID);
        }
    }


    public void removeCreatorsWithoutWorks() {
        _creators.values().removeIf(creator -> creator.getNWorks() == 0);
    
    }

    public void removeUser(int UserId) throws DoesNotExistUserException {
        User user = findUser(UserId);
        _users.remove(user.getUserID());
    }


    public boolean needToNotify(Work work, int amount) {
        return (work.getAvailableCopies() == 0 && amount > 0);
    }


    public boolean updateWorkInventory(int workId, int amount)
    throws DoesNotExistWorkException, FailedInventoryException, DoesNotExistWorkException {
        
        Work work = findWork(workId);
        boolean notify = needToNotify(work, amount);

        work.updateInventory(amount);

        if (notify) {
            work.notifyAvailabilityObservers(new AvailabilityNotification(work));
        }

        if (work.getNCopies() == 0) {
             removeWorkFromCreators(workId);
             
            _works.remove(workId);
           
            removeCreatorsWithoutWorks();
        }
        setChanged(true);
        return true;
    }
    

    public void updateUserSuspension (int userID) {
        User user = _users.get(userID);

        boolean suspended = false;

        if (user.hasOverdueRequests(_currentDay) || user.getFine() != 0)
            suspended = true;
        
        if (suspended) { user.setSuspended();}
        else { user.setActive();}
    }


    public void updateAllUsersSuspension() {
        for (User user : _users.values()) {
            updateUserSuspension(user.getUserID());
        }
    }


    public void registerAvailabilityInterest(int userID, int workId) throws DoesNotExistUserException,
    DoesNotExistWorkException {
        Subscriber sub = findUser(userID);
        Work work = findWork(workId);
        work.addAvailabilityObserver(sub);
    }


    public void registerBorrowInterest(int userID, int workId) throws DoesNotExistUserException,
    DoesNotExistWorkException {
        Subscriber sub = findUser(userID);
        Work work = findWork(workId);
        work.addBorrowObserver(sub);
    }


    public void cleanUserNotifications(int userId) throws DoesNotExistUserException {
        User user = findUser(userId);
        user.clearNotifications();
        setChanged(true);
    }
}

