package de.abas.training;

import de.abas.eks.jfop.FOPException;
import de.abas.eks.jfop.remote.ContextRunnable;
import de.abas.eks.jfop.remote.FOPSessionContext;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.util.ContextHelper;


public class ServerClientProgram  implements ContextRunnable{

	public static void main(String[] args) {
		ServerClientProgram clientProgram  = new ServerClientProgram();
		DbContext dbContext = ContextHelper.createClientContext("10.0.3.12", 6550, "i7erp0", "sy", clientProgram.getClass().getSimpleName());
		dbContext.out().println("Client-Context ");
		clientProgram.run(dbContext);
		dbContext.close();
	}

	@Override
	public int runFop(FOPSessionContext ctx, String[] args) throws FOPException {
		DbContext dbContext = ctx.getDbContext();
		dbContext.out().println("Server-Context ");
		run(dbContext);
		return 0;
	}

	private void run(DbContext dbContext) {
		dbContext.out().println("running... ");
		
	}

}
