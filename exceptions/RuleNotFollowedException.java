package bci.exceptions;

public class RuleNotFollowedException extends Exception {


    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;


    private int _userID;
    private int _workID;
    private int _ruleID; 

    public RuleNotFollowedException(int userID, int workID, int ruleID) {
        _userID = userID;
        _workID = workID;
        _ruleID = ruleID;
    }
    public int getUserID() {
        return _userID;
    }
    public int getWorkID() {
        return _workID;
    }
    
    public int getRuleID() {
        return _ruleID;
    }
}
