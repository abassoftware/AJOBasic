package de.abas.training.basic.infosystem;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.infosystem.custom.ow1.ControlVarnameList;
import de.abas.erp.db.schema.company.Vartab;
import de.abas.training.basic.testutility.TestLogger;
import de.abas.training.basic.testutility.Utility;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class InfosystemVARNAMELISTEventHandlerTest {

    @Rule
    public TestName testName = new TestName();
    private ControlVarnameList infosys;
    private DbContext ctx;
    private Utility utility = new Utility();
    private static Logger logger = TestLogger.getLogger();

    @After
    public void cleanup() {
        logger.info(TestLogger.CLEANUP_MESSAGE);
        infosys.abort();
    }

    @Test
    public void runInfoysTest() {
        logger.info(String.format(TestLogger.TEST_INIT_MESSAGE,
                testName.getMethodName()));
        infosys.setYvartab(new Utility().getObjects(ctx, Vartab.class, "V-00-01").get(0));
        infosys.invokeStart();
        assertThat(infosys.getRowCount(), is(not(0)));
    }

    @Before
    public void setup() {
        logger.info(TestLogger.SETUP_MESSAGE);
        ctx = utility.createClientContext();
        infosys = ctx.openInfosystem(ControlVarnameList.class);
        logger.info(String.format(TestLogger.CONNECTION_MESSAGE,
                utility.getClient(), utility.getHostname()));
    }

}
