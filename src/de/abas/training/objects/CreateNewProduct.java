package de.abas.training.objects;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.ProductEditor;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.util.QueryUtil;
import de.abas.training.common.AbstractAjoAccess;

public class CreateNewProduct extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		DbContext ctx = getDbContext();
		
		// create new product
		ProductEditor productEditor = ctx.newObject(ProductEditor.class);
		// set the fields
		productEditor.setSwd("RUNIT");
		productEditor.setDescr("Tech Runit");
		productEditor.setPackDimWidth(5);
		productEditor.setPackDimLength(5);
		productEditor.setPackDimHeight(2);
		productEditor.setSalesPrice(326.95);
		
		// select product for production list
		SelectionBuilder<Product> selectionBuilder = SelectionBuilder.create(Product.class);
		selectionBuilder.add(Conditions.starts(Product.META.swd, "MYCPU"));
		Product product = QueryUtil.getFirst(ctx, selectionBuilder.build());
		
		// set row fields
		ProductEditor.Row row = productEditor.table().appendRow();
		row.setProductListElem(product);
		row.setElemQty(2);
		
		// store new product
		productEditor.commit();
		
		// print information about new product to console
		ctx.out().println("Product create: " + productEditor.objectId().getIdno() + " " + productEditor.objectId().getSwd());
	}

	public static void main(String[] args) {
		new CreateNewProduct().runClientProgram(args);
	}

}
