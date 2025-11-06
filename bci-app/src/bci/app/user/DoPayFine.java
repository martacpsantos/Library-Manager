package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.UserIsActiveException;
import bci.app.exceptions.NoSuchUserException;
import bci.exceptions.ActiveUserException;
import bci.exceptions.DoesNotExistUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

//FIXME maybe import classes

/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
         addIntegerField("userId", Prompt.userId());

        //FIXME maybe define fields
    }

    @Override
    protected final void execute() throws CommandException {

         int userId = integerField("userId");
    
        try {
            _receiver.payFine(userId); 
        
        } catch (ActiveUserException e) {
            throw new UserIsActiveException(userId);
        }
        catch (DoesNotExistUserException e) {
            throw new NoSuchUserException(e.getUserId());
        }
        //FIXME implement command
    }

}
