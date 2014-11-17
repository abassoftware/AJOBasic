package de.abas.training.selection;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.vendor.Vendor;
import de.abas.erp.db.selection.ExpertSelection;
import de.abas.erp.db.selection.Selection;
import de.abas.erp.db.settings.DisplayMode;
import de.abas.training.common.AbstractAjoAccess;

public class SelectProductsWithVendor extends AbstractAjoAccess {

	public static void main(String[] args) {
		new SelectProductsWithVendor().runClientProgram(args);
	}
	
	@Override
	public void run(String[] args) {
		DbContext ctx = getDbContext();
		ctx.getSettings().setDisplayMode(DisplayMode.DISPLAY);
		String criteria = "idno=;vendor<>`;@language=en";
		Selection<Product> selection = ExpertSelection.create(Product.class, criteria);
		Query<Product> query = ctx.createQuery(selection);
		for (Product product : query) {
			if (product.getVendor() instanceof Vendor) {
				Vendor vendor = (Vendor) product.getVendor();
				ctx.out().println(product.getIdno() + " - " + product.getSwd() + " - " + vendor.getIdno() + " - " + vendor.getSwd() + " - " + vendor.getTurnoverFY());				
			}
			Iterable<Product.Row> rows = product.table().getRows();
			for (Product.Row row : rows) {
				ctx.out().println(" - " + row.getProductListElem() + " - " + row.getElemQty() + " - " + row.getString(Product.Row.META.countUnit));
			}
		}
	}

}
