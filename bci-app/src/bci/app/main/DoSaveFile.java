package bci.app.main;

import java.io.IOException;

import bci.LibraryManager;
import pt.tecnico.uilib.forms.Form;
import bci.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.menus.Command;
//FIXME maybe import classes

/**
 * §4.1.1 Open and load files.
 */
class DoSaveFile extends Command<LibraryManager> {

	DoSaveFile(LibraryManager receiver) {
		super(Label.SAVE_FILE, receiver, r -> r.getLibrary() != null);
	}


	@Override
	protected final void execute() {
		try {
			_receiver.save();
		} catch (MissingFileAssociationException e) {
			try {
				_receiver.saveAs(Form.requestString(Prompt.newSaveAs()));
			} catch (MissingFileAssociationException | IOException ex) {
				ex.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
