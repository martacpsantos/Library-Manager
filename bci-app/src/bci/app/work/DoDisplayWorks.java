package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
//FIXME maybe import classes

/**
 * 4.3.2. Display all works.
 */
class DoDisplayWorks extends Command<LibraryManager> {

    /**
     * Constructor for DoDisplayWorks.
     * 
     * @param receiver library manager
     */
    DoDisplayWorks(LibraryManager receiver) {
        super(Label.SHOW_WORKS, receiver);
	//FIXME maybe define fields
    }

    
    /**
     * Executes the command.
     */
    @Override
    protected final void execute() {
        _display.popup(_receiver.displayWorks());
    }
}
