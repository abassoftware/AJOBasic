package de.abas.training;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.util.ContextHelper;


public class ClientProgram {

	public static void main(String[] args) {
		ClientProgram clientProgram  = new ClientProgram();
		DbContext dbContext = ContextHelper.createClientContext("schulung", 6550, "i7erp0", "sy", clientProgram.getClass().getSimpleName());
		dbContext.out().println("Client-Context running");
		dbContext.close();
	}

}
