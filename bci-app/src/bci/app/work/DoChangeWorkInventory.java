package bci.app.work;

import bci.LibraryManager;
import bci.exceptions.DoesNotExistWorkException;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.FailedInventoryException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME maybe import classes

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
        addIntegerField("workId", Prompt.workId());
        addIntegerField("amount", Prompt.amountToUpdate());
        //FIXME maybe define fields
    }

    @Override
    protected final void execute() throws CommandException {
        int workId = integerField("workId");
        int amount= integerField("amount");

       try {
            _receiver.updateWorkInventory(workId, amount);
        } catch (FailedInventoryException e) {
            _display.popup(Message.notEnoughInventory(workId, amount));
        }
        catch (DoesNotExistWorkException e) {
            throw new NoSuchWorkException(e.getWorkId());
        }
    }
 
}



       
      

       
