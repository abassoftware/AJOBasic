package de.abas.training.basic.firststeps;

import de.abas.eks.jfop.FOPException;
import de.abas.eks.jfop.remote.ContextRunnable;
import de.abas.eks.jfop.remote.FOPSessionContext;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.util.ContextHelper;

/**
 * Example for program running on server and client.
 *
 * @author abas Software AG
 *
 */
public class ServerClientProgram implements ContextRunnable {

	/**
	 * Main method used to execute class in client mode.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		ServerClientProgram clientProgram = new ServerClientProgram();
		DbContext dbContext = ContextHelper.createClientContext("10.0.3.12", 6550, "i7erp0", "sy", clientProgram.getClass().getSimpleName());
		clientProgram.run(dbContext, "Application is running in client mode.");
		dbContext.close();
	}

	@Override
	public int runFop(FOPSessionContext ctx, String[] args) throws FOPException {
		DbContext dbContext = ctx.getDbContext();
		run(dbContext, "Application is running in server mode.");
		return 0;
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
