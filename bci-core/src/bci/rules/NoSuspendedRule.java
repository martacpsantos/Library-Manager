package bci.rules;
import bci.exceptions.RuleNotFollowedException;
import bci.user.User;
import bci.work.Work;

public class NoSuspendedRule implements Rule {

    @java.io.Serial
    private static final long serialVersionUID = 202510152234L;

    private static final int RULE_ID = 2;
    
    /**
     * Validates the rule that prevents suspended users from making requests.
     * @param work The work being requested.
     * @param user The user making the request.
     * @throws RuleNotFollowedException if the user is suspended.
     */
    @Override
    public void validate(Work work, User user) throws RuleNotFollowedException {
        if(user.isSuspended()){
            throw new RuleNotFollowedException(user.getUserID(), work.getWorkID(), RULE_ID);
        }
    }
}
