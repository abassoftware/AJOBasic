package de.abas.training.basic.objects;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.training.basic.testutility.TestLogger;
import de.abas.training.basic.testutility.Utility;

public class CreateProductsFromTXTTest {

	@Rule
	public TestName testName = new TestName();
	private CreateProductsFromTXT instance;
	private DbContext ctx;
	private Utility utility = new Utility();
	private static Logger logger = TestLogger.getLogger();

	@After
	public void cleanup() {
		logger.info(TestLogger.CLEANUP_MESSAGE);
		List<String> swds = getSwdsFromTXT();
		for (String swd : swds) {
			utility.deleteObjects(ctx, Product.class, swd);
		}
	}

	@Test
	public void createProducts() {
		logger.info(String.format(TestLogger.TEST_INIT_MESSAGE,
				testName.getMethodName()));
		instance.run(null);
		List<String> swds = getSwdsFromTXT();
		for (String swd : swds) {
			SelectionBuilder<Product> selectionBuilder =
					SelectionBuilder.create(Product.class);
			selectionBuilder.add(Conditions.eq(Product.META.swd, swd));
			List<Product> products =
					ctx.createQuery(selectionBuilder.build()).execute();
			assertEquals("count of products with swd" + swd, 1, products.size());
			logger.info(String.format("Found %d products with swd %s: %s",
					products.size(), swd, products.get(0).getId().toString()));
		}
	}

	@Test
	public void logFileCreated() {
		logger.info(String.format(TestLogger.TEST_INIT_MESSAGE,
				testName.getMethodName()));
		instance.run(null);
		File file = new File(CreateProductsFromTXT.LOG_FILE_CLIENT);
		Assert.assertTrue("log file exists", file.exists());
		logger.info(String.format("log file %s %s",
				CreateProductsFromTXT.LOG_FILE_CLIENT, (file.exists() ? " exists"
						: " does not exist")));
	}

	@Before
	public void setup() {
		logger.info(TestLogger.SETUP_MESSAGE);
		instance = new CreateProductsFromTXT();
		ctx = utility.createClientContext();
		logger.info(String.format(TestLogger.CONNECTION_MESSAGE,
				utility.getClient(), utility.getHostname()));
		cleanup();
	}

	/**
	 * Gets list of search words in txt file.
	 *
	 * @return List of search words in txt file.
	 * @throws RuntimeException Thrown if an error occurs.
	 */
	private List<String> getSwdsFromTXT() throws RuntimeException {
		BufferedReader bufferedReader = null;
		String read = "";
		List<String> swds = new ArrayList<String>();
		try {
			bufferedReader =
					new BufferedReader(new FileReader(
							CreateProductsFromTXT.READ_FILE_CLIENT));
			read = bufferedReader.readLine();
			while ((read = bufferedReader.readLine()) != null) {
				swds.add(read.split(";")[0]);
			}
		}
		catch (FileNotFoundException e) {
			logger.fatal(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		catch (IOException e) {
			logger.fatal(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			}
			catch (IOException e) {
				logger.fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
		return swds;
	}

}
