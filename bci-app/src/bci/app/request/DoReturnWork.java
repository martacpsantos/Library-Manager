package bci.app.request;

import bci.LibraryManager;
import pt.tecnico.uilib.forms.Form;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.UserIsActiveException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import bci.exceptions.ActiveUserException;
import bci.exceptions.DoesNotExistUserException;
import bci.exceptions.DoesNotExistWorkException;
import bci.exceptions.NotRequestedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {
    
    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
        addIntegerField("userId", bci.app.user.Prompt.userId());
        addIntegerField("workId", bci.app.work.Prompt.workId());
        //FIXME maybe define fields
    }

   @Override
    protected final void execute() throws CommandException {
        int userId = integerField("userId");
        int workId = integerField("workId");
        try {
            int fine = _receiver.returnWork(userId, workId);

            if (fine > 0) {
                _display.popup(Message.showFine(userId, fine));
                String choice = Form.requestString(Prompt.finePaymentChoice());

                if (choice.equals("s")) {
                    try {
                        _receiver.payFine(userId);
                    } catch (ActiveUserException e) {
                        throw new UserIsActiveException(e.getUserId());
                    }
                }
            }
        } catch (DoesNotExistUserException e) {
            throw new NoSuchUserException(e.getUserId());
        } catch (DoesNotExistWorkException e) {
            throw new NoSuchWorkException(e.getWorkId());
        } catch (NotRequestedException e) {
            throw new WorkNotBorrowedByUserException(workId, userId);
        }
    }
}

