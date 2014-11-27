package de.abas.training.basic.standardselection;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.selection.SelectionBuilder.Conjunction;
import de.abas.training.basic.common.AbstractAjoAccess;

/**
 * Selecting products using Matchcode.
 *
 * @author abas Software AG
 *
 */
public class SelectProducts extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		DbContext ctx = getDbContext();

		SelectionBuilder<Product> selectionBuilder = SelectionBuilder.create(Product.class);

		selectionBuilder.add(Conditions.match(Product.META.swd, "^B*0^"));
		selectionBuilder.add(Conditions.match(Product.META.swd, "*AN*"));
		selectionBuilder.setTermConjunction(Conjunction.OR);

		Query<Product> query = ctx.createQuery(selectionBuilder.build());

		for (Product product : query) {
			ctx.out().println(product.getIdno() + " - " + product.getSwd() + " - " + product.getDescr());
		}
	}

}
