package de.abas.training.basic.standardselection;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.abas.training.basic.testutility.TestLogger;

public class SelectProductsTest {

	@Rule
	public TestName testName = new TestName();
	private SelectProducts instance;
	private static Logger logger = TestLogger.getLogger();

	@Test
	public void queryShouldNotBeEmpty() throws Exception {
		logger.info(String.format(TestLogger.TEST_INIT_MESSAGE,
				testName.getMethodName()));
		int status = instance.run(null);
		assertEquals("status of selection", 0, status);
		logger.info("query was " + (status == 0 ? "not empty" : "empty"));
	}

	@Before
	public void setup() {
		logger.info(TestLogger.SETUP_MESSAGE);
		instance = new SelectProducts();
	}

}
