package bci.rules;
import bci.exceptions.RuleNotFollowedException;
import bci.user.User;
import bci.work.Work;

public class NoCopiesRule implements Rule {
    @java.io.Serial
    private static final long serialVersionUID = 202510152231L;
    
    private static final int RULE_ID = 3;


    /**
     * Validates the rule that prevents users from requesting works that have no available copies.
     * @param work The work being requested.
     * @param user The user making the request.
     * @throws RuleNotFollowedException if there are no available copies of the work.
     */
    @Override
    public void validate(Work work, User user) throws RuleNotFollowedException {
        if(work.getAvailableCopies() == 0){
            throw new RuleNotFollowedException(user.getUserID(), work.getWorkID(), RULE_ID);
        }

    }    
}
