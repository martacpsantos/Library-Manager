package bci.work;

import bci.ObservableWork;
import bci.Subscriber;
import bci.exceptions.FailedInventoryException;
import bci.notification.Notification;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Work implements Serializable, ObservableWork {

    @java.io.Serial
    private static final long serialVersionUID = 202510031145L;
    
    /** Lists of borrowNotification subscribers. */
    private List<Subscriber> _borrowObservers = new ArrayList<>();

    /** Lists of availabilityNotification subscribers. */
    private List<Subscriber> _availabilityObservers = new ArrayList<>();
    
    private final Category _category;
    private int _workID;
    private final int _workPrice;
    private int _nCopies;
    private final String _title;
    private int _availableCopies;


    public Work (int workID, int workPrice, String title, Category category, int nCopies) {
        _workID = workID;
        _workPrice = workPrice;
        _title = title;
        _category = category;
        _nCopies = nCopies;
        _availableCopies = _nCopies;
    }


    public Category getCategory() {
        return _category;
    }


    public int getWorkID() {
        return _workID;
    }


    public int getWorkPrice() {
        return _workPrice;
    }


    public String getTitle() {
        return _title;
    }


    public int getNCopies() {
        return _nCopies;
    }


    public void setNCopies(int nCopies) {
        _nCopies = nCopies;
    }


    public int getAvailableCopies() {
        return _availableCopies;
    }


    public void setAvailableCopies(int availableCopies) {
        _availableCopies = availableCopies;
    }

    public boolean isReference() {
        return _category.isReference();
    }

    public boolean isScitech() {
        return _category.isScitech();
    }

    public boolean isFiction() {
        return _category.isFiction();
    }


    public void decrementAvailableCopies() {
            _availableCopies--;
    }

    public void incrementAvailableCopies() {
            _availableCopies++;
    }


    /**
     * Updates the inventory of the work.
     * @param amount
     * @throws FailedInventoryException if total or available copies would become negative.
     */
    public void updateInventory(int amount) throws FailedInventoryException {
        int newAvailable = getAvailableCopies() + amount;
        int newTotal = getNCopies() + amount;

        if (newAvailable < 0 || newTotal < 0)
            throw new FailedInventoryException();

        if (newAvailable > newTotal)
            newAvailable = newTotal;

        setAvailableCopies(newAvailable);
        setNCopies(newTotal);
    }

    /**
     * Checks if the work has a creator with the given name.
     * @param creatorName
     * @return true if the work has a creator with the given name.
     */
    public abstract boolean hasCreator(String creatorName);


    /**
     * Checks if the work corresponds to the given search term.
     * @param term String to search for.
     * @return true if the work corresponds to the given search term.
     */
    public boolean correspondsToTerm(String term) {
        term = term.toLowerCase();
        return getTitle().toLowerCase().contains(term);
    }


    @Override
    public void addBorrowObserver(Subscriber sub) {
        _borrowObservers.add(sub);
    }

    @Override
    public void addAvailabilityObserver(Subscriber sub) {
        _availabilityObservers.add(sub);
    }

    @Override
    public void removeBorrowObserver(Subscriber sub) {
        _borrowObservers.remove(sub);
    }

    @Override
    public void removeAvailabilityObserver(Subscriber sub) {
        _availabilityObservers.remove(sub);
    }

    @Override
    public void notifyBorrowObservers(Notification n) {
        for (Subscriber sub : _borrowObservers)
            sub.updateNotification(n);
    }

    @Override
    public void notifyAvailabilityObservers(Notification n) {
        for (Subscriber sub : _availabilityObservers)
            sub.updateNotification(n);
    }

    @Override
    public abstract String toString();


}
