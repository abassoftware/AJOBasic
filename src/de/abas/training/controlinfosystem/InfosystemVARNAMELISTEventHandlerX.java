package de.abas.training.controlinfosystem;

import de.abas.erp.axi2.EventHandlerRunner;
import de.abas.erp.db.infosystem.custom.ow1.InfosystemVARNAMELIST;
import de.abas.erp.db.schema.company.Vartab;
import de.abas.erp.db.schema.company.Vartab.Row;
import de.abas.erp.axi2.type.ButtonEventType;
import de.abas.erp.db.DbContext;
import de.abas.erp.axi.event.EventException;
import de.abas.erp.axi2.annotation.EventHandler;
import de.abas.erp.axi2.annotation.ButtonEventHandler;
import de.abas.erp.axi.screen.ScreenControl;
import de.abas.erp.jfop.rt.api.annotation.RunFopWith;
import de.abas.erp.axi2.event.ButtonEvent;

@EventHandler(head = InfosystemVARNAMELIST.class, row = InfosystemVARNAMELIST.Row.class)
@RunFopWith(EventHandlerRunner.class)
public class InfosystemVARNAMELISTEventHandlerX {

	@ButtonEventHandler(field="start", type = ButtonEventType.AFTER)
	public void startAfter(ButtonEvent event, ScreenControl screenControl, DbContext ctx, InfosystemVARNAMELIST head) throws EventException {
		Vartab yvartab = head.getYvartab();
		if (yvartab != null) {
			Iterable<Row> rows = yvartab.table().getRows();
			head.table().clear();
			for (Row row : rows) {
				de.abas.erp.db.infosystem.custom.ow1.InfosystemVARNAMELIST.Row appendRow = head.table().appendRow();
				appendRow.setTyengname(row.getVarNameEnglish());
				appendRow.setTygername(row.getVarName());
			}
		}
	}
}
