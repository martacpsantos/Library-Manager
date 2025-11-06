package bci.app.main;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;


/**
 * 4.1.2. Display the current date.
 */
public class DoDisplayDate extends Command<LibraryManager> {

    /**
     * Constructor for DoDisplayDate
     * @param receiver library manager
     */
    public DoDisplayDate(LibraryManager receiver) {
        super(Label.DISPLAY_DATE, receiver);
    }


    /**
     * Executes the command.
     */
    @Override
    public final void execute() throws CommandException {
        _display.popup(Message.currentDate(_receiver.getCurrentDate()));
    }
}
