package de.abas.training.basic.use;

import de.abas.erp.common.type.AbasDate;
import de.abas.erp.common.type.enums.EnumEntryTypeStockAdjustment;
import de.abas.erp.common.type.enums.EnumTypeCommands;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.EditorCommand;
import de.abas.erp.db.EditorCommandFactory;
import de.abas.erp.db.EditorObject;
import de.abas.erp.db.exception.CommandException;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.storagequantity.StockAdjustmentEditor;
import de.abas.erp.db.schema.storagequantity.StockAdjustmentEditor.Row;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.util.QueryUtil;
import de.abas.training.basic.common.AbstractAjoAccess;

/**
 * Shows how to execute the Typed Command Stockadjustment.
 *
 * @author abas Software AG
 *
 */
public class UseTypedCommand extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		DbContext dbContext = getDbContext();

		EditorCommand editorCommand =
				EditorCommandFactory.typedCmd(EnumTypeCommands.Stockadjustment);

		try {
			EditorObject editorObject = dbContext.openEditor(editorCommand);
			if (editorObject instanceof StockAdjustmentEditor) {
				StockAdjustmentEditor stockAdjustmentEditor =
						(StockAdjustmentEditor) editorObject;

				// fills head fields
				String idno = "10003";
				stockAdjustmentEditor.setProduct(getSelectedProduct(idno));
				stockAdjustmentEditor.setDocNo("mybeleg_bf02");
				stockAdjustmentEditor.setDateDoc(new AbasDate());
				stockAdjustmentEditor
						.setEntType(EnumEntryTypeStockAdjustment.Receipt);

				// clears table
				stockAdjustmentEditor.table().clear();

				// add row to the table and fill the fields
				Row appendRow = stockAdjustmentEditor.table().appendRow();
				int number = 1;
				appendRow.setUnitQty(number);
				appendRow.setString(StockAdjustmentEditor.Row.META.location2,
						"L01.002");

				// execute stock adjustment
				stockAdjustmentEditor.commit();
				getDbContext().out().println(
						"Stock adjustment: " + idno + " - Number: " + number);
			}
		}
		catch (CommandException e) {
			dbContext.out().println(e.getMessage());
		}
	}

	/**
	 * Selects product with idno.
	 *
	 * @param idno The product's idno.
	 * @return The product as an instance of Product.
	 */
	private Product getSelectedProduct(String idno) {
		SelectionBuilder<Product> selectionBuilder =
				SelectionBuilder.create(Product.class);
		selectionBuilder.add(Conditions.eq(Product.META.idno, idno));
		return QueryUtil.getFirst(getDbContext(), selectionBuilder.build());
	}

}
