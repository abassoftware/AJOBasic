package de.abas.training.basic.expertselection;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.selection.ExpertSelection;
import de.abas.erp.db.selection.Selection;
import de.abas.erp.db.settings.DisplayMode;
import de.abas.training.basic.common.AbstractAjoAccess;

/**
 * Select products and its vendors using an expert selection.
 *
 * @author abas Software AG
 *
 */
public class SelectProductsWithVendor extends AbstractAjoAccess {

	/**
	 * Runs program in client mode.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SelectProductsWithVendor selectProductsWithVendor = new SelectProductsWithVendor();
		selectProductsWithVendor.runClientProgram(args);
	}

	@Override
	public void run(String[] args) {
		DbContext ctx = getDbContext();
		ctx.getSettings().setDisplayMode(DisplayMode.DISPLAY);

		String criteria = "idno=;vendor<>`;@language=en";
		Selection<Product> selection = ExpertSelection.create(Product.class, criteria);
		Query<Product> query = ctx.createQuery(selection);

		for (Product product : query) {
			ctx.out().println(product.getIdno() + " - " + product.getSwd() + " - " + product.getString("vendor^idno") + " - " + product.getString("vendor^swd"));
		}
	}

}
