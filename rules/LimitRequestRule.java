package bci.rules;
import bci.exceptions.RuleNotFollowedException;
import bci.user.User;
import bci.work.Work;

public class LimitRequestRule implements Rule {
    @java.io.Serial
    private static final long serialVersionUID = 202510152232L;

    private static final int RULE_ID = 4;

    
    /**
     * Validates the rule that limits the number of active requests a user can have based on their behavior.
     * @param work The work being requested.
     * @param user The user making the request.
     * @throws RuleNotFollowedException if the user exceeds their maximum allowed active requests.
     */
    @Override
    public void validate(Work work, User user)throws RuleNotFollowedException{
        if(user.getActiveRequests() +1 > user.getBehaviour().getMaxRequests()){
            throw new RuleNotFollowedException(user.getUserID(), work.getWorkID(), RULE_ID);
        }


    }

}
