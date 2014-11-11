package de.abas.training.firststeps;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.util.ContextHelper;

public class ClientProgram {

	/**
	 * Main method is used to execute class in client mode.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// creates client context
		DbContext ctx = ContextHelper.createClientContext("oxford", 6550, "schulvajo", "sy", "Client application");
		// prints to console
		ctx.out().println("Application is running in client context");
		// closes database context
		ctx.close();
	}

}
