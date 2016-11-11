package de.abas.training.basic.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
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

public class CreateNewProductTest {

	@Rule
	public TestName testName = new TestName();
	private CreateNewProduct instance;
	private DbContext ctx;
	private Utility utility = new Utility();
	private static Logger logger = TestLogger.getLogger();

	@After
	public void cleanup() {
		logger.info(TestLogger.CLEANUP_MESSAGE);
		utility.deleteObjects(ctx, Product.class, "RUNIT");
	}

	@Test
	public void createNewProductRunit() {
		logger.info(String.format(TestLogger.TEST_INIT_MESSAGE,
				testName.getMethodName()));
		List<Product> productsMYCPU = selectProducts("MYCPU");
		assertNotEquals("count of products with swd MYCPU", 0, productsMYCPU.size());
		logger.info(String.format("Found %d products with swd MYCPU: %s",
				productsMYCPU.size(), productsMYCPU.get(0).getId().toString()));
		instance.run(null);
		List<Product> productsRUNIT = selectProducts("RUNIT");
		assertEquals("count of products with swd RUNIT", 1, productsRUNIT.size());
		logger.info(String.format("Found %d products with swd RUNIT: %s",
				productsRUNIT.size(), productsRUNIT.get(0).getId().toString()));
		Product product = productsRUNIT.get(0);
		assertEquals("descr of product", "Tech Runit", product.getDescr());
		assertEquals("packDimWidth of product", new BigDecimal(5).setScale(2,
				RoundingMode.HALF_UP),
				product.getPackDimWidth().setScale(2, RoundingMode.HALF_UP));
		assertEquals("packDimLength of product", new BigDecimal(5).setScale(2,
				RoundingMode.HALF_UP),
				product.getPackDimLength().setScale(2, RoundingMode.HALF_UP));
		assertEquals("packDimHeight of product", new BigDecimal(2).setScale(2,
				RoundingMode.HALF_UP),
				product.getPackDimHeight().setScale(2, RoundingMode.HALF_UP));
		assertEquals("salesPrice of product", new BigDecimal(326.95).setScale(2,
				RoundingMode.HALF_UP),
				product.getSalesPrice().setScale(2, RoundingMode.HALF_UP));
	}

	@Before
	public void setup() {
		logger.info(TestLogger.SETUP_MESSAGE);
		instance = new CreateNewProduct();
		ctx = utility.createClientContext();
		logger.info(String.format(TestLogger.CONNECTION_MESSAGE,
				utility.getClient(), utility.getHostname()));
		cleanup();
	}

	private List<Product> selectProducts(String swd) {
		SelectionBuilder<Product> selectionBuilder =
				SelectionBuilder.create(Product.class);
		selectionBuilder.add(Conditions.starts(Product.META.swd, swd));
		List<Product> products = ctx.createQuery(selectionBuilder.build()).execute();
		return products;
	}
}
