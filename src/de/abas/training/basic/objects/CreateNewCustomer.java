package de.abas.training.basic.objects;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.customer.CustomerEditor;
import de.abas.training.basic.common.AbstractAjoAccess;

/**
 * Creating new Customer objects.
 *
 * @author abas Software AG
 *
 */
public class CreateNewCustomer extends AbstractAjoAccess {

	public static void main(String[] args) {
		new CreateNewCustomer().runClientProgram(args);
	}

	@Override
	public void run(String[] args) {
		DbContext ctx = getDbContext();

		// creates a new customer
		CustomerEditor customerEditor = ctx.newObject(CustomerEditor.class);
		// fills fields
		customerEditor.setSwd("GRIFFS");
		customerEditor.setDescr("Griff's Hamburgers, Bergewörden");
		customerEditor.setAddr("Griff's Hamburgers");
		customerEditor.setStreet("An Der Urania 45");
		customerEditor.setZipCode("25779");
		customerEditor.setTown("Bergewörden");
		// stores new customer
		customerEditor.commit();

		// gets field values after committing
		String idno = customerEditor.objectId().getIdno();
		String swd = customerEditor.objectId().getSwd();

		// prints information about new customer to console
		ctx.out().println("New Customer created: " + idno + " " + swd);
	}

}
