package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.exceptions.DoesNotExistUserException;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

    DoShowUser(LibraryManager receiver) {
        super(Label.SHOW_USER, receiver);
         addIntegerField("userID", Prompt.userId());
        //FIXME maybe define fields
    }


    @Override
    protected final void execute() throws CommandException {
        //FIXME implement command
        int userId = integerField("userID");

        try {
            _display.popup(_receiver.showUser(userId));
        } catch (DoesNotExistUserException e) {
            throw new NoSuchUserException(e.getUserId());
        } 
    }
}

