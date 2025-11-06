package bci.app.user;

import bci.LibraryManager;
import bci.exceptions.DoesNotExistUserException;
import bci.app.exceptions.NoSuchUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
        addIntegerField("userId", Prompt.userId());
        //FIXME maybe add fields
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = integerField("userId");
        try {
            _display.popup(_receiver.showUserNotifications(userId));
            _receiver.cleanUserNotifications(userId);
        }
        catch (DoesNotExistUserException e) {
            throw new NoSuchUserException(e.getUserId());
        }
        //FIXME implement command
    }

}
