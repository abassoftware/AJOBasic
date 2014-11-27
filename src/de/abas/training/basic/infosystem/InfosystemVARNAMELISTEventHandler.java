package de.abas.training.basic.infosystem;

import de.abas.erp.axi.event.EventException;
import de.abas.erp.axi.screen.ScreenControl;
import de.abas.erp.axi2.EventHandlerRunner;
import de.abas.erp.axi2.annotation.ButtonEventHandler;
import de.abas.erp.axi2.annotation.EventHandler;
import de.abas.erp.axi2.annotation.FieldEventHandler;
import de.abas.erp.axi2.event.ButtonEvent;
import de.abas.erp.axi2.event.FieldEvent;
import de.abas.erp.axi2.type.ButtonEventType;
import de.abas.erp.axi2.type.FieldEventType;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.infosystem.custom.ow1.InfosystemVARNAMELIST;
import de.abas.erp.db.infosystem.custom.ow1.InfosystemVARNAMELIST.Row;
import de.abas.erp.db.schema.company.Vartab;
import de.abas.erp.jfop.rt.api.annotation.RunFopWith;

/**
 * Logic of infosystem VARNAMELIST. Shows all variables of a variable table in German and English.
 *
 * @author abas Software AG
 *
 */
@EventHandler(head = InfosystemVARNAMELIST.class, row = InfosystemVARNAMELIST.Row.class)
@RunFopWith(EventHandlerRunner.class)
public class InfosystemVARNAMELISTEventHandler {

	/**
	 * Loads the table.
	 *
	 * @param event The event that occurred.
	 * @param screenControl The ScreenControl instance.
	 * @param ctx The database context.
	 * @param head The infosystem's instance.
	 * @throws EventException Thrown if an error occurs.
	 */
	@ButtonEventHandler(field = "start", type = ButtonEventType.AFTER)
	public void startAfter(ButtonEvent event, ScreenControl screenControl, DbContext ctx, InfosystemVARNAMELIST head) throws EventException {
		if (head.getYvartab() != null) {
			Iterable<Vartab.Row> rows = head.getYvartab().table().getRows();
			for (Vartab.Row row : rows) {
				Row infosystemRow = head.table().appendRow();
				infosystemRow.setTygername(row.getVarName());
				infosystemRow.setTyengname(row.getVarNameEnglish());
			}
		}
	}

	/**
	 * Reset the table
	 *
	 * @param event The event that occurred.
	 * @param screenControl The ScreenControl instance.
	 * @param ctx The database context.
	 * @param head The infosystem's instance.
	 * @throws EventException Thrown if an error occurs.
	 */
	@FieldEventHandler(field = "yvartab", type = FieldEventType.EXIT)
	public void yvartabExit(FieldEvent event, ScreenControl screenControl, DbContext ctx, InfosystemVARNAMELIST head) throws EventException {
		head.table().clear();
	}

}
