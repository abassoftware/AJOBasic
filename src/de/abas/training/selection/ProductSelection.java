package de.abas.training.selection;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.training.common.AbstractAjoAccess;

public class ProductSelection extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		DbContext dbContext = getDbContext();
		
		SelectionBuilder<Product> selectionBuilder = SelectionBuilder.create(Product.class);
		
		selectionBuilder.add(Conditions.match(Product.META.swd, "^AN*"));
		selectionBuilder.add(Conditions.match(Product.META.swd, "*V^"));
		
		Query<Product> queryProduct = dbContext.createQuery(selectionBuilder.build());
		
		for (Product product : queryProduct) {
			dbContext.out().println(product.getIdno() 
					+ " - " + product.getSwd()
					+ " - " + product.getDescrOperLang() );
		}
		

	}

}
