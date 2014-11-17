package de.abas.training;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.Query;
import de.abas.erp.db.schema.customer.Customer;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.Order;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.selection.SelectionBuilder.Conjunction;
import de.abas.training.common.AbstractAjoAccess;

public class ClientServerTestProgram extends AbstractAjoAccess {

	public static void main(String[] args) {
		new ClientServerTestProgram().runClientProgram(args);	
	}
	
	
	@Override
	public void run(String[] args) {
		DbContext dbContext = getDbContext();
		
		SelectionBuilder<Customer> selectionBuilder = SelectionBuilder.create(Customer.class);
		
		// Criteria
		selectionBuilder.add(Conditions.between(Customer.META.idno, "70010", "70020"));
		selectionBuilder.add(Conditions.notEmpty(Customer.META.phoneNo));
		
		selectionBuilder.addOrder(Order.desc(Customer.META.idno));
		
		selectionBuilder.setTermConjunction(Conjunction.OR);
		
		Query<Customer> queryCustomer = dbContext.createQuery(selectionBuilder.build());
		
		for (Customer customer : queryCustomer) {
			dbContext.out().println(customer.getIdno() + " - " + 
					customer.getSwd() + " - " + 
					customer.getTown() + " - " + 
					customer.getPhoneNo());
		}
	}
}
