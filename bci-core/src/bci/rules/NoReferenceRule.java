package bci.rules;
import bci.exceptions.RuleNotFollowedException;
import bci.user.User;
import bci.work.Work;



public class NoReferenceRule implements Rule {
    @java.io.Serial
    private static final long serialVersionUID = 202510152235L;

    private static final int RULE_ID = 5;

    /**
     * Validates the rule that prevents users from requesting reference works.
     * @param work The work being requested.
     * @param user The user making the request.
     * @throws RuleNotFollowedException if the work is a reference work.
     */
    @Override
    public void validate(Work work, User user) throws RuleNotFollowedException {
        if (work.isReference()) {
            throw new RuleNotFollowedException(user.getUserID(), work.getWorkID(), RULE_ID);
        }
    }
}
