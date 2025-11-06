package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.BorrowingRuleFailedException;
import bci.exceptions.DoesNotExistUserException;
import bci.exceptions.DoesNotExistWorkException;
import bci.exceptions.RuleNotFollowedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
//FIXME maybe import classes

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
    
        //FIXME maybe define fields
    }

    @Override
    protected final void execute() throws CommandException {

        int userId = integerField("userId");
        int workId = integerField("workId");

        try {

			int returnDay = _receiver.requestWork(userId, workId);
			_display.popup(Message.workReturnDay(workId, returnDay));
        }
        catch (DoesNotExistUserException e) {
            throw new NoSuchUserException(e.getUserId());
        }
        catch (DoesNotExistWorkException e) {
            throw new NoSuchWorkException(e.getWorkId());
        }
        catch (RuleNotFollowedException e) {
            if (e.getRuleID() == 3) {
                String choice = Form.requestString(Prompt.returnNotificationPreference());
                if (choice.equals("s")) {
                    try {
                        _receiver.registerAvailabilityInterest(userId, workId);
                    }
                    catch (DoesNotExistUserException ex) {
                        throw new NoSuchUserException(ex.getUserId());
                    }
                    catch (DoesNotExistWorkException ex) {
                        throw new NoSuchWorkException(ex.getWorkId());
                    }
                }
            }
            else {
                throw new BorrowingRuleFailedException(e.getUserID(), e.getWorkID(), e.getRuleID());
            }
        }
    }
}
