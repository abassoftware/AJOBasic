package de.abas.training.objects;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.EditorAction;
import de.abas.erp.db.Query;
import de.abas.erp.db.exception.CommandException;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.ProductEditor;
import de.abas.training.common.AbstractAjoAccess;
import de.abas.training.common.SelectionUtility;

/**
 * Editing sales price of existing product.
 * 
 * @author abas Software AG
 *
 */
public class EditExistingProducts extends AbstractAjoAccess {

	DbContext ctx = null;
	
	@Override
	public void run(String[] args) {
		ctx = getDbContext();
		SelectionUtility selectionUtility = new SelectionUtility();
		Query<Product> query = selectionUtility.getProductsBetween(ctx, Product.META.idno, "10150", "10160");
		for (Product product : query) {
			try {
				ProductEditor productEditor = product.createEditor();
				productEditor.open(EditorAction.UPDATE);
				productEditor.setSalesPrice(productEditor.getSalesPrice().doubleValue() * 1.10);
				productEditor.commit();
				ctx.out().println("Sales price of " + product.getIdno() + " " + product.getSwd() + " changed: " + product.getSalesPrice());
				
			} catch (CommandException e) {
				ctx.out().println("An exception occurred while trying to edit " + product.getId().toString() + ": " + e.getMessage());
			}
		}
	}

	/**
	 * Runs program in client mode.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new EditExistingProducts().runClientProgram(args);
	}

}
