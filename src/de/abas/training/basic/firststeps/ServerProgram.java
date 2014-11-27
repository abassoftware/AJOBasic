package de.abas.training.basic.firststeps;

import de.abas.eks.jfop.FOPException;
import de.abas.eks.jfop.remote.ContextRunnable;
import de.abas.eks.jfop.remote.FOPSessionContext;
import de.abas.erp.db.DbContext;

/**
 * Example for program running on server.
 *
 * @author abas Software AG
 *
 */
public class ServerProgram implements ContextRunnable {

	@Override
	public int runFop(FOPSessionContext ctx, String[] args) throws FOPException {
		// gets the database context
		DbContext dbContext = ctx.getDbContext();
		// prints text to console
		dbContext.out().println("Server-Mode running");
		return 0;
	}

}
