package bci;
import bci.exceptions.*;
import bci.notification.*;
import bci.user.User;
import bci.work.*;
import java.io.*;
import java.util.*;


/**
 * The façade class.
 */
public class LibraryManager {

    /** The object doing all the actual work. */
    private Library _library = new Library();
   
    
    public Library getLibrary() {
        return _library;
    }


    public boolean changed() {
        return _library.changed();
    }


    /**
     * Saves the current state of the application to the associated file.
     *
     * @throws MissingFileAssociationException
     * @throws IOException
     */
    public void save() throws FileNotFoundException, MissingFileAssociationException, IOException {
        String filename = _library.getFilename();
        if (filename == null) throw new MissingFileAssociationException();
        try (var oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))) {
            oos.writeObject(_library);
            _library.setChanged(false);
        }
    }


    /**
     * Saves the current state of the application to a given file.
     *
     * @param filename
     * @throws MissingFileAssociationException
     * @throws IOException
     */
    public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
        _library.setFilename(filename);
        save();
    }


    /**
     * Loads the state of the application from a given file.
     *
     * @param filename
     * @throws UnavailableFileException
     */
    public void load(String filename) throws UnavailableFileException {
      try (var ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
        _library = (Library)ois.readObject();
        _library.setFilename(filename); // update filename
        _library.setChanged(false);
      } catch (IOException | ClassNotFoundException e) {
        throw new UnavailableFileException(filename);
        }
    }


    public List<Work> displayWorks(){
        return getLibrary().displayWorks();
    }


    public Work displayWork(int workID) throws DoesNotExistWorkException {
        Work work = getLibrary().displayWork(workID);

        if (work == null)
            throw new DoesNotExistWorkException(workID);

        return work;
    }


    public List<Work> displayWorksByCreator(String creatorName) throws DoesNotExistCreatorException {
        List <Work> works = getLibrary().displayWorksByCreator(creatorName);

        if (works.isEmpty())
            throw new DoesNotExistCreatorException(creatorName);

        return works;
    }

    
    public int registerUser(String name, String email) throws FailedUserRegistrationException {
        User user = _library.registerUser(name, email);
        return user.getUserID();
    }


    /**
     * Read text input file and initializes the current library (which should be empty)
     * with the domain entities representeed in the import file.
     *
     * @param filename name of the text input file
     * @throws ImportFileException if some error happens during the processing of the
     * import file.
     */
    public void importFile(String filename) throws ImportFileException {
        try {
            if (filename != null || !filename.isEmpty())
            _library.importFile(filename);
        } catch (IOException | UnrecognizedEntryException e) {
            throw new ImportFileException(filename, e);
        }
    }
  

    /**
     * Get the current date of the library.
     * @return current date
     */
    public int getCurrentDate() {
        return getLibrary().getCurrentDate();
    }


    /**
     * Advance the current date by a given number of days.
     * @param days
     */
    public void advanceDate(int days) {
        getLibrary().advanceDate(days);
    }


    /**
     * 
     * @param userID
     * @return the user with the given ID
     * @throws DoesNotExistUserException
     */
    public User showUser(int userID) throws DoesNotExistUserException {
        User user = _library.showUser(userID);
        if (user == null)
            throw new DoesNotExistUserException(userID);
        return user;
    }


    /**
     * 
     * @return list of all users
     */
    public List<User> showAllUsers() {
        return _library.showAllUsers();
    }


    public int requestWork(int userId, int workId) throws RuleNotFollowedException,
    DoesNotExistUserException, DoesNotExistWorkException {
        return _library.requestWork(userId, workId);
    }


    public int returnWork(int userId, int workId) throws NotRequestedException, DoesNotExistUserException, DoesNotExistWorkException {
        return _library.returnWork(userId, workId);
    }


    public List<Work> searchWorksByTerm(String term) {
        return _library.searchWorksByTerm(term);
    }


    public List<Notification> showUserNotifications(int userId) throws DoesNotExistUserException {
        return _library.showUserNotifications(userId);
    }


    public void payFine(int userId) throws ActiveUserException, DoesNotExistUserException {
        _library.payFine(userId);
    }


    public boolean updateWorkInventory(int workId, int amount) throws DoesNotExistWorkException, FailedInventoryException {
        return _library.updateWorkInventory(workId, amount);
    }


    public void registerAvailabilityInterest(int userID, int workId) throws DoesNotExistUserException,
    DoesNotExistWorkException {
        _library.registerAvailabilityInterest(userID, workId);
    }


    public void updateAllUsersSuspension() {
        _library.updateAllUsersSuspension();
    }


    public void registerBorrowInterest(int userID, int workId) throws DoesNotExistUserException,
    DoesNotExistWorkException {
        _library.registerBorrowInterest(userID, workId);
    }


    public void cleanUserNotifications(int userId) throws DoesNotExistUserException {
        _library.cleanUserNotifications(userId);
    }
}

