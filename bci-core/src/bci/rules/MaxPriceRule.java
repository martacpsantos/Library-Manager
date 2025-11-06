package bci.rules;
import bci.exceptions.RuleNotFollowedException;
import bci.user.User;
import bci.work.Work;

public class MaxPriceRule implements Rule {
    @java.io.Serial
    private static final long serialVersionUID = 202510152235L;
    
    private static final int RULE_ID = 6;


    /**
     * Validates the rule that restricts users with certain behavior from requesting works above a specified price.
     * @param work The work being requested.
     * @param user The user making the request.
     * @throws RuleNotFollowedException if the work's price exceeds the allowed limit for the user's behavior.
     */
    @Override
    public void validate(Work work, User user) throws RuleNotFollowedException {
            if(work.getWorkPrice() > 25 && !(user.isBehaved())){
                throw new RuleNotFollowedException(user.getUserID(), work.getWorkID(), RULE_ID);
            }
    }

}
