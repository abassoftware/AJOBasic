package de.abas.training.usetypedcommand;

import de.abas.erp.common.type.enums.EnumEntryTypeStockAdjustment;
import de.abas.erp.common.type.enums.EnumTypeCommands;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.EditorCommand;
import de.abas.erp.db.EditorCommandFactory;
import de.abas.erp.db.EditorObject;
import de.abas.erp.db.exception.CommandException;
import de.abas.erp.db.schema.storagequantity.StockAdjustmentEditor;
import de.abas.erp.db.schema.storagequantity.StockAdjustmentEditor.Row;
import de.abas.training.common.AbstractAjoAccess;

public class ControlTypedCommand extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		DbContext dbContext = getDbContext();
		EditorCommand editorCommand = EditorCommandFactory.typedCmd(EnumTypeCommands.Stockadjustment);
		try {
			EditorObject editorObject = dbContext.openEditor(editorCommand);
			if (editorObject instanceof StockAdjustmentEditor) {
				StockAdjustmentEditor stockAdjustmentEditor = (StockAdjustmentEditor)editorObject;
				
				// set fields
				stockAdjustmentEditor.setString("product", "10003");
				
				// Document
				stockAdjustmentEditor.setString(StockAdjustmentEditor.META.docNo, "MyDoc001");
				
				// Date
				stockAdjustmentEditor.setString(StockAdjustmentEditor.META.dateDoc, "20141113");
				
				// EntryType
				stockAdjustmentEditor.setString(StockAdjustmentEditor.META.entType, EnumEntryTypeStockAdjustment.Receipt.toString());
				
				// fill the table fields
				stockAdjustmentEditor.table().clear();
				
				Row appendRow = stockAdjustmentEditor.table().appendRow();
				appendRow.setUnitQty(2.0);
				
				appendRow.setString(StockAdjustmentEditor.Row.META.location2, "L01.002");
				
				stockAdjustmentEditor.commit();
				
				dbContext.out().println("end");
				
			}
			
			
			
			
			
		} catch (CommandException e) {
			dbContext.out().println(e.getMessage());
		}

	}

}
