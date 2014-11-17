package de.abas.training.common;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.field.StringField;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;

public class SelectionUtility {
	
	/**
	 * Selects products in defined idno range.
	 * 
	 * @param from Start idno.
	 * @param to End idno.
	 * @return Iterable Query object containing the selected products.
	 */
	public Query<Product> getProductsBetween(DbContext ctx, StringField<Product> field, String from, String to) {
		SelectionBuilder<Product> selectionBuilder = SelectionBuilder.create(Product.class);
		selectionBuilder.add(Conditions.between(field, from, to));
		return ctx.createQuery(selectionBuilder.build());
	}
	
	public Query<Product> getProductsStartsWith(DbContext ctx, StringField<Product> field, String from) {
		SelectionBuilder<Product> selectionBuilder = SelectionBuilder.create(Product.class);
		selectionBuilder.add(Conditions.starts(field, from));
		return ctx.createQuery(selectionBuilder.build());
	}

}
