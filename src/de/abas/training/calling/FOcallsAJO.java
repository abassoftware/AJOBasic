package de.abas.training.calling;

import de.abas.eks.jfop.FOPException;
import de.abas.eks.jfop.remote.ContextRunnable;
import de.abas.eks.jfop.remote.FOPSessionContext;
import de.abas.erp.db.DbContext;
import de.abas.jfop.base.buffer.BufferFactory;
import de.abas.jfop.base.buffer.UserTextBuffer;

public class FOcallsAJO implements ContextRunnable {

	UserTextBuffer userTextBuffer = null;
	
	@Override
	public int runFop(FOPSessionContext ctx, String[] args) throws FOPException {
		DbContext dbContext = ctx.getDbContext();
		dbContext.out().println("AJO is running!");

		int no1 = 0;
		int no2 = 0;
		int result = 0;

		no1 = getVariableValues(no1, "xiNo1");
		no2 = getVariableValues(no2, "xiNo2");

		result = no1 + no2;
		
		userTextBuffer.setValue("xiResult", result);

		if (result != 0) {
			return 0;			
		}
		else {
			return 1;
		}
		
	}

	private int getVariableValues(int number, String field) {
		userTextBuffer = BufferFactory.newInstance(false)
				.getUserTextBuffer();
		if (userTextBuffer.isVarDefined(field)) {
			number = userTextBuffer.getIntegerValue(field);
		}
		return number;
	}

}
