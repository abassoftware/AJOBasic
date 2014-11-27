package de.abas.training.basic.firststeps;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.util.ContextHelper;

/**
 * Example for program running on client.
 *
 * @author abas Software AG
 *
 */
public class ClientProgram {

	/**
	 * Main method used to execute class in client mode.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ClientProgram clientProgram = new ClientProgram();
		// creates client context - configure parameters according to your client
		DbContext dbContext = ContextHelper.createClientContext("schulung", 6550, "i7erp0", "sy", clientProgram.getClass().getSimpleName());
		// prints to console
		dbContext.out().println("Client-Context running");
		// closes database context
		dbContext.close();
	}

}
