package de.abas.training.basic.objects;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.customer.CustomerEditor;
import de.abas.training.basic.common.AbstractAjoAccess;

public class CreateNewCustomers extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		DbContext ctx = getDbContext();
		
		// create a new customer
		CustomerEditor customerEditor = ctx.newObject(CustomerEditor.class);
		// fill fields
		customerEditor.setSwd("GRIFFS");
		customerEditor.setDescr("Griff's Hamburgers, Bergewörden");
		customerEditor.setAddr("Griff's Hamburgers");
		customerEditor.setStreet("An der Urania 45");
		customerEditor.setZipCode("25779");
		customerEditor.setTown("Bergewörden");
		// store new customer
		customerEditor.commit();
		
		ctx.out().println("New customer successfully created: " + customerEditor.objectId().getIdno() + " " + customerEditor.objectId().getSwd());
	}

	public static void main(String[] args) {
		new CreateNewCustomers().runClientProgram(args);
	}

}
