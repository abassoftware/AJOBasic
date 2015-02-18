package de.abas.training.basic.objects;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.EditorAction;
import de.abas.erp.db.exception.CommandException;
import de.abas.erp.db.exception.DBRuntimeException;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.ProductEditor;
import de.abas.training.basic.testutility.TestLogger;
import de.abas.training.basic.testutility.Utility;

public class EditExistingProductsTest {

	@Rule
	public TestName testName = new TestName();
	private EditExistingProducts instance;
	private DbContext ctx;
	private Utility utility = new Utility();
	private static Logger logger = TestLogger.getLogger();
	private ArrayList<BigDecimal> salesPrices = new ArrayList<BigDecimal>();

	@Test
	public void changeSalesPrice() throws Exception {
		instance.run(null);
		List<Product> products =
				utility.getObjects(ctx, Product.class, EditExistingProducts.FROM,
						EditExistingProducts.TO);
		for (int i = 0; i < products.size(); i++) {
			Product product = products.get(i);
			assertEquals(
					String.format(
							"salesPrice of product %s is increased by 10 percent",
							product.getId()),
					salesPrices.get(i).multiply(new BigDecimal(1.10))
							.setScale(2, RoundingMode.HALF_UP),
					product.getSalesPrice());
			logger.info(String.format("salesPrice of product %s: %s",
					product.getId(), product.getSalesPrice().toString()));
		}
	}

	@After
	public void cleanup() {
		logger.info(TestLogger.CLEANUP_MESSAGE);
		resetProductSalesPrice();
	}

	@Before
	public void setup() {
		logger.info(TestLogger.SETUP_MESSAGE);
		instance = new EditExistingProducts();
		ctx = utility.createClientContext();
		logger.info(String.format(TestLogger.CONNECTION_MESSAGE,
				utility.getClient(), utility.getHostname()));
		cleanup();
	}

	/**
	 * Sets the sales price of the products to random price value. Saves random price
	 * value in ArrayList.
	 */
	private void resetProductSalesPrice() {
		List<Product> products =
				utility.getObjects(ctx, Product.class, EditExistingProducts.FROM,
						EditExistingProducts.TO);
		for (Product product : products) {
			ProductEditor productEditor = product.createEditor();
			try {
				productEditor.open(EditorAction.UPDATE);
				BigDecimal salesPrice =
						new BigDecimal(1 + (Math.random() * 1000)).setScale(2,
								RoundingMode.HALF_UP);
				salesPrices.add(salesPrice);
				productEditor.setSalesPrice(salesPrice);
				productEditor.commit();
			}
			catch (CommandException e) {
				logger.fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}
			catch (DBRuntimeException e) {
				logger.fatal(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
	}
}
