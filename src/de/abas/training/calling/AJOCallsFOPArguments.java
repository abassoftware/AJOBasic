package de.abas.training.calling;

import de.abas.eks.jfop.remote.FOe;
import de.abas.jfop.base.buffer.BufferFactory;
import de.abas.jfop.base.buffer.UserTextBuffer;
import de.abas.training.common.AbstractAjoAccess;

public class AJOCallsFOPArguments extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		UserTextBuffer userTextBuffer = BufferFactory.newInstance().getUserTextBuffer();
		initalizeNewVariable(userTextBuffer, "xiNumber1");
		initalizeNewVariable(userTextBuffer, "xiNumber2");
		initalizeNewVariable(userTextBuffer, "xiResult");
		
		userTextBuffer.assign("xiNumber1", 7);
		userTextBuffer.assign("xiNumber2", 7);
		userTextBuffer.assign("xiResult", 9999);
		
		FOe.input("./ow1/FOP.CALLED.BY.AJO");
		
		int result = userTextBuffer.getIntegerValue("xiResult");
		
		getDbContext().out().println("Result: " + result);
	}

	private void initalizeNewVariable(UserTextBuffer userTextBuffer,
			String number) {
		if (!userTextBuffer.isVarDefined(number)) {
			userTextBuffer.defineVar("int", number);
		}
	}

}
