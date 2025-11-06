package bci.rules;
import bci.exceptions.RuleNotFollowedException;
import bci.request.Request;
import bci.user.User;
import bci.work.Work;

public class DoubleRequestRule implements Rule {
    @java.io.Serial
    private static final long serialVersionUID = 202510152233L;

    private static final int RULE_ID = 1;


    /**
     * Validates the rule that prevents a user from requesting the same work twice simultaneously.
     * @param work The work being requested.
     * @param user The user making the request.
     * @throws RuleNotFollowedException if the user has already requested the work and has not returned it yet.
     */
    @Override
    public void validate(Work work, User user) throws RuleNotFollowedException {
        Request request = user.getRequests().get(work.getWorkID());
        
        if(user.alreadyRequested(work.getWorkID()) && !request.isReturned()){
            throw new RuleNotFollowedException(user.getUserID(), work.getWorkID(), RULE_ID);
        }
    
    }
}
