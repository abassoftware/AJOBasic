package de.abas.training.basic.use;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.abas.training.basic.testutility.TestLogger;

public class UseStandardInfosystemTest {

	@Rule
	public TestName testName = new TestName();
	private UseStandardInfosystem instance;
	private static Logger logger = TestLogger.getLogger();

	@Before
	public void setup() {
		logger.info(TestLogger.SETUP_MESSAGE);
		instance = new UseStandardInfosystem();
	}

	@Test
	public void thereShouldBeRows() throws Exception {
		logger.info(String.format(TestLogger.TEST_INIT_MESSAGE,
				testName.getMethodName()));
		int status = instance.run(null);
		assertEquals("row count", 0, status);
		logger.info("row count was " + (status == 0 ? "greater than zero" : "zero"));
	}
}
