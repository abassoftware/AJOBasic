package de.abas.training.eventhandling;

import de.abas.erp.axi.event.EventException;
import de.abas.erp.axi.screen.ScreenControl;
import de.abas.erp.axi2.EventHandlerRunner;
import de.abas.erp.axi2.annotation.EventHandler;
import de.abas.erp.axi2.annotation.FieldEventHandler;
import de.abas.erp.axi2.event.FieldEvent;
import de.abas.erp.axi2.type.FieldEventType;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.customer.CustomerEditor;
import de.abas.erp.jfop.rt.api.annotation.RunFopWith;

/**
 * Checks whether zip code is valid for certain defined country codes.
 *
 * @author abas Software AG
 *
 */
@EventHandler(head = CustomerEditor.class)
@RunFopWith(EventHandlerRunner.class)
public class ZipCodeEventHandler {

	@FieldEventHandler(field = "zipCode", type = FieldEventType.VALIDATION)
	public void zipCodeValidation(FieldEvent event, ScreenControl screenControl, DbContext ctx, CustomerEditor head) throws EventException {
		if (head.getCtryCode().equals("D")) {
			if (!((head.getZipCode().matches("[1-9][0-9]{4}")) || (head.getZipCode().matches("[0][1-9][0-9]{3}")))) {
				throw new EventException("Zip code invalid! Please enter a valid german zip code.", 1);
			}
		}
		else if (head.getCtryCode().equals("CH")) {
			if (!(head.getZipCode().matches("[1-9][0-9]{3}"))) {
				throw new EventException("Zip code invalid! Please enter a valid swiss zip code.", 1);
			}
		}
		else if (head.getCtryCode().equals("S")) {
			if (!((head.getZipCode().matches("[1-4][0-9]{4}") || (head.getCtryCode().matches("[0][1-9][0-9]{3}") || (head.getCtryCode().matches("5[0-2][0-9]{3}")))))) {
				throw new EventException("Zip code invalid! Please enter a valid spanish zip code.", 1);
			}
		}
	}

}
