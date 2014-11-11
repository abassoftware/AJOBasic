package de.abas.training.standardselection;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.schema.customer.Customer;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.training.common.AbstractAjoAccess;

/**
 * Selecting all customers and printing field values to the console.
 * 
 * @author abas Software AG
 *
 */
public class CustomerSimpleSelection extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		DbContext dbContext = getDbContext();

		SelectionBuilder<Customer> selectionBuilder = SelectionBuilder.create(Customer.class);
		Query<Customer> queryCustomer = dbContext.createQuery(selectionBuilder.build());

		for (Customer customer : queryCustomer) {
			dbContext.out().println(customer.getIdno() + " - " + customer.getSwd() + " - " + customer.getDescrOperLang() + " - " + customer.getPhoneNo());
		}

	}

}
