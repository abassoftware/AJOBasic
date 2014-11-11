package de.abas.training.firststeps;

import de.abas.eks.jfop.FOPException;
import de.abas.eks.jfop.remote.ContextRunnable;
import de.abas.eks.jfop.remote.FOPSessionContext;
import de.abas.erp.db.DbContext;

public class ServerProgram implements ContextRunnable {

	@Override
	public int runFop(FOPSessionContext arg0, String[] arg1)
			throws FOPException {
		// gets the database context
		DbContext ctx = arg0.getDbContext();
		// prints text to console
		ctx.out().println("Application is running in server mode.");
		return 0;
	}

}
