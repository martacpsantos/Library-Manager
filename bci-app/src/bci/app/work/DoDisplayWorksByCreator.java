package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import bci.exceptions.DoesNotExistCreatorException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

    /**
     * Constructor for DoDisplayWorksByCreator.
     * 
     * @param receiver library manager
     */
    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
        addStringField("creatorName", Prompt.creatorId());
    }

    
    /**
     * Executes the command.
     */
    @Override
    protected final void execute() throws CommandException {
        String creatorId = stringField("creatorName");

        try {
            _display.popup(_receiver.displayWorksByCreator(creatorId));
        } catch (DoesNotExistCreatorException e) {
            throw new NoSuchCreatorException(e.getCreatorName());
        }
    }
}
