package de.abas.training.basic.objects;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.ProductEditor;
import de.abas.erp.db.schema.part.ProductEditor.Row;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.util.QueryUtil;
import de.abas.training.basic.common.AbstractAjoAccess;

/**
 * Creating new Product objects with table.
 *
 * @author abas Software AG
 *
 */
public class CreateNewProduct extends AbstractAjoAccess {

	@Override
	public int run(String[] args) {
		DbContext ctx = getDbContext();

		// creates new product
		ProductEditor productEditor = ctx.newObject(ProductEditor.class);
		// sets head fields
		productEditor.setSwd("RUNIT");
		productEditor.setDescr("Tech Runit");
		productEditor.setPackDimWidth(5);
		productEditor.setPackDimLength(5);
		productEditor.setPackDimHeight(2);
		productEditor.setSalesPrice(326.95);

		// selects product for production list
		SelectionBuilder<Product> selectionBuilder =
				SelectionBuilder.create(Product.class);
		selectionBuilder.add(Conditions.starts(Product.META.swd, "MYCPU"));
		Product product = QueryUtil.getFirst(ctx, selectionBuilder.build());

		// sets row fields
		Row row = productEditor.table().appendRow();
		row.setProdListElem(product);
		row.setElemQty(2);

		// stores new product
		productEditor.commit();

		// prints information about new product to console
		ctx.out().println(
				"Product created: " + productEditor.objectId().getIdno() + " "
						+ productEditor.objectId().getSwd());

		return 0;
	}

}
