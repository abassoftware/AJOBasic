package de.abas.training.firststeps;

import de.abas.eks.jfop.FOPException;
import de.abas.eks.jfop.remote.ContextRunnable;
import de.abas.eks.jfop.remote.FOPSessionContext;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.util.ContextHelper;

public class ServerClientProgram implements ContextRunnable {

	@Override
	public int runFop(FOPSessionContext ctx, String[] args) throws FOPException {
		DbContext dbContext = ctx.getDbContext();
		run(dbContext, "Application is running in server mode.");
		return 0;
	}

	/**
	 * Main method used to execute class in client mode.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ServerClientProgram serverClientProgram = new ServerClientProgram();
		DbContext dbContext = ContextHelper.createClientContext("oxford", 6550, "schulvajo", "sy", "Client-Server program");
		serverClientProgram.run(dbContext, "Application is running in client mode.");
		dbContext.close();

	}
	
	/**
	 * Prints message to console.
	 * 
	 * @param ctx The database context.
	 * @param message The message to print.
	 */
	private void run(DbContext ctx, String message) {
		ctx.out().println(message);
	}

}
