package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.DoesNotExistWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

    /**
     * Constructor for DoDisplayWork.
     * 
     * @param receiver library manager
     */
    DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
        addIntegerField("workId", Prompt.workId());
    }

    
    /**
     * Executes the command.
     */
    @Override
    protected final void execute() throws CommandException {
        int workId = integerField("workId");

        try {
            _display.popup(_receiver.displayWork(workId));
        } catch (DoesNotExistWorkException e) {
            throw new NoSuchWorkException(e.getWorkId());
        }
    }
}
