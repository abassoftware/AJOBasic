package de.abas.training.basic.standardselection;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.schema.customer.Customer;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.Order;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.selection.SelectionBuilder.Conjunction;
import de.abas.training.basic.common.AbstractAjoAccess;

/**
 * Selecting customers with criteria.
 *
 * @author abas Software AG
 *
 */
public class CustomerAdvancedSelection extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		DbContext ctx = getDbContext();

		SelectionBuilder<Customer> selectionBuilder = SelectionBuilder.create(Customer.class);
		// Defines an idno range
		selectionBuilder.add(Conditions.between(Customer.META.idno, "70020", "70030"));
		// Only selects customers with a phone number
		selectionBuilder.add(Conditions.notEmpty(Customer.META.phoneNo));
		// Logical interconnection set to OR
		selectionBuilder.setTermConjunction(Conjunction.OR);
		// Sorting direction
		selectionBuilder.addOrder(Order.asc(Customer.META.phoneNo));

		Query<Customer> query = ctx.createQuery(selectionBuilder.build());

		for (Customer customer : query) {
			ctx.out().println(customer.getIdno() + " - " + customer.getSwd() + " - " + customer.getDescrOperLang() + " - " + customer.getPhoneNo());
		}
	}

}
