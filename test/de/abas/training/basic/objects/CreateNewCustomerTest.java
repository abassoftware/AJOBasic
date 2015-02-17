package de.abas.training.basic.objects;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.customer.Customer;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.training.basic.testutility.TestLogger;
import de.abas.training.basic.testutility.Utility;

public class CreateNewCustomerTest {

	@Rule
	public TestName testName = new TestName();
	private CreateNewCustomer instance;
	private DbContext ctx;
	private Utility utility = new Utility();
	private static Logger logger = TestLogger.getLogger();

	@After
	public void cleanup() {
		logger.info(TestLogger.CLEANUP_MESSAGE);
		utility.deleteObjects(ctx, Customer.class, "GRIFFS");
	}

	@Test
	public void createNewCustomerGriffs() {
		logger.info(String.format(TestLogger.TEST_INIT_MESSAGE,
				testName.getMethodName()));
		instance.run(null);
		SelectionBuilder<Customer> selectionBuilder =
				SelectionBuilder.create(Customer.class);
		selectionBuilder.add(Conditions.starts(Customer.META.swd, "GRIFFS"));
		List<Customer> customers =
				ctx.createQuery(selectionBuilder.build()).execute();
		assertEquals("count of customers with swd GRIFFS", 1, customers.size());
		logger.info(String.format("Found %d customers with swd GRIFFS: %s",
				customers.size(), customers.get(0).getId().toString()));
		Customer customer = customers.get(0);
		assertEquals("descr of customer", "Griff's Hamburgers, Bergewörden",
				customer.getDescr());
		assertEquals("addr of customer", "Griff's Hamburgers", customer.getAddr());
		assertEquals("street of customer", "An Der Urania 45", customer.getStreet());
		assertEquals("zip code of customer", "25779", customer.getZipCode());
		assertEquals("town of customer", "Bergewörden", customer.getTown());
	}

	@Before
	public void setup() {
		logger.info(TestLogger.SETUP_MESSAGE);
		instance = new CreateNewCustomer();
		ctx = utility.createClientContext();
		logger.info(String.format(TestLogger.CONNECTION_MESSAGE,
				utility.getClient(), utility.getHostname()));
		cleanup();
	}

}
