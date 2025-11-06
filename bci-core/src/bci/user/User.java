package bci.user;

import bci.Subscriber;
import bci.notification.Notification;
import bci.request.Request;
import bci.work.Work;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class User implements Serializable, Subscriber {
    /** User's class serial number. */
    @java.io.Serial
    private static final long serialVersionUID = 202510021154L;

    /** User's notifications. */
    private List<Notification> _notifications = new ArrayList<>();



    private final Map<Integer,Request> _requests= new TreeMap<>();

    /** User's behaviour. */
    private Behaviour _behaviour;

    /** User's library status. */
    private Status _status;

    /** User's email. */
    private final String _email;

    /** User's ID. */
    private final int _userID;

    /** User's fine to pay. */
    private int _fine = 0;
    
    /** User's name. */
    private final String _username;

    /** Return on time streak */
    private int _onTimeStreak;

    /** Late return streak */
    private int _lateStreak;


    /**
     * Constructs a new user.
     * 
     * @param username User's name.
     * @param email User's email.
     */
    public User(int id, String name, String email) {
        _userID = id;
        _username = name;
        _email = email;
        _fine= 0;
        _behaviour = new Normal();
        _status = new Active();
    }


    public void setActive() {
        this._status = new Active();
    }

    public boolean isActive() {
        return _status.isActive();
    }


    public void setSuspended() {
        this._status = new Suspended();
    }


    public boolean isSuspended() {
        return _status.isSuspended();
    }


    /**
     * @return User's behaviour.
     */
    public Behaviour getBehaviour() {
        return _behaviour;
    }


    /**
     * Changes the user's behaviour.
     *
     * @param behaviour New behaviour to be assigned to the user.
     */
    public void setBehaviour(Behaviour behaviour) {
        _behaviour = behaviour;
    }

    public boolean isBehaved() {
        return _behaviour.isBehaved();
    }
    public boolean isNormal() {
        return _behaviour.isNormal();
    }
    public boolean isDefaulter() {
        return _behaviour.isDefaulter();
    }


    /**
     * @return User's Status
     */
    public Status getStatus() {
        return _status;
    }

    
    /**
     * Changes the user's current status.
     *
     * @param status New status to be assigned to the user.
     */
    public void changeStatus(Status status) {
        _status = status;
    }


    /**
     * @return User's email.
     */
    public String getEmail() {
        return _email;
    }


    /**
     * @return User's ID.
     */
    public int getUserID() {
        return _userID;
    }


    /**
     * @return User's fine.
     */
    public int getFine() {
        return _fine;
    }


    /*
     * @return User's requests.
     */
    public Map<Integer, Request> getRequests() {
        return _requests;
    }


    /**
     * @return Number of active requests.
     */
    public int getActiveRequests() {
       int count= 0 ; 
       for(Request r: _requests.values()){
           if(!r.isReturned()){
               count++;
            }
        }
        return count;
    }


    /**
     * @return User's name.
     */
    public String getUsername() {
        return _username;
    }


    /**
     * @return User's notifications.
     */
    public List<Notification> getUserNotifications() {
        return _notifications;
    }


    /**
     * Adds a notification to the list of notifications.
     *
     * @param notification Notification to be added.
     */
    public void addNotification(Notification notification) {
        _notifications.add(notification);
    }


    /**
     * Removes all notifications from the list of notifications.
     * This operation clears the current notification history.
     */
    public void clearNotifications() {
        _notifications.clear();
    }


    /*
     * @return true if the user has already requested the work with the given ID.
     */
    public boolean alreadyRequested(int workID) {
        return _requests.containsKey(workID);
    }


    /*
     * Adds a request to the user's list of requests.
     */
    public void addRequest(Request request) {
        _requests.put(request.getWorkID(), request);
    }


    /*
     * Updates the user's fine by adding the specified amount.
     * @param fine Amount to be added to the user's fine.
     * @return Updated fine amount.
     */
    public int updateFine(int fine) {  
        _fine += fine;                 
        return _fine;                  
    }


    /**
     * Pays the user's fine and updates their status accordingly.
     * @param currentDay The current day used to check for overdue requests.
     */
    public void payFine(int currentDay) {
        _fine = 0;
        if (!hasOverdueRequests(currentDay))
            setActive();
        else
            setSuspended();
    }


    /*
     * @param currentDay
     * @return true if the user has overdue requests as of the given current day.
     */
    public boolean hasOverdueRequests(int currentDay) {
        for (Request r : _requests.values()) {
            if (r.isOverdue(currentDay)) {
                return true;
            }
        }
        return false;
    }

    
    @Override
    public void updateNotification(Notification notification) {
        _notifications.add(notification);
    }


    /**
     * @param w Work to which the user wants to subscribe for borrow notifications.
     */
    public void wantBorrowNotifications(Work w) { w.addBorrowObserver(this);}

    /**
     * @param w Work to which the user wants to subscribe for availability notifications.
     */
    public void wantAvailabilityNotifications(Work w) { w.addAvailabilityObserver(this);}

    /**
     * @param w Work from which the user wants to unsubscribe for availability notifications.
     */
    public void cancelAvailabilityInterest(Work w) { w.removeAvailabilityObserver(this);}
    
    /**
     * @param w Work from which the user wants to unsubscribe for borrow notifications.
     */
    public void cancelBorrowInterest(Work w) { w.removeBorrowObserver(this);}


    /**
     * Updates the user's behaviour based on their return history.
     * @param request The request that was just returned
     */
    public void updateBehaviourAfterReturn(Request request) {
        if (request.isReturnedOnTime()) {
            _onTimeStreak++;
            if (isDefaulter() && _onTimeStreak >= 3) {
                setBehaviour(new Normal());
                _lateStreak = 0;
                return;
            }
        } else {
            _lateStreak++;
            _onTimeStreak = 0;
        }

        if (_lateStreak >= 3) {
            setBehaviour(new Defaulter());
        } else if (isDefaulter() && _onTimeStreak >= 3) {
            setBehaviour(new Normal());
        } else if (_onTimeStreak >= 5) {
            setBehaviour(new Behaved());
        } else {
            if (!(isDefaulter())) {
                setBehaviour(new Normal());
            }
        }
    }

    
    /**
     * User's information in the format:
     * "userID - username - email - behaviour - status"
     * 
     * If the user is active, the status shown is "ACTIVO".
     * If the user is suspended, the status shown is "SUSPENSO - EUR fineValue".
     *
     * @return String with the user information.
     */
    @Override
    public String toString() {
        String base = getUserID() + " - " + getUsername() + " - " + getEmail() + " - " + getBehaviour();
        if (isActive()) {
            return base + " - ACTIVO";
        } else if (isSuspended()) {
            return base + " - SUSPENSO - EUR " + getFine();
        } else {
            return base + " - " + getStatus();
        }
    }
}
