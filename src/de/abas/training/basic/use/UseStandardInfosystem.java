package de.abas.training.basic.use;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.infosystem.standard.la.StockLevelInformation;
import de.abas.erp.db.infosystem.standard.la.StockLevelInformation.Row;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.settings.DisplayMode;
import de.abas.erp.db.util.QueryUtil;
import de.abas.training.basic.common.AbstractAjoAccess;

/**
 * Shows how to execute the standard infosystem StockLevelInformation and how to use the content of the resulting table.
 *
 * @author abas Software AG
 *
 */
public class UseStandardInfosystem extends AbstractAjoAccess {

	/**
	 * Starts program in client mode.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new UseStandardInfosystem().runClientProgram(args);
	}

	@Override
	public void run(String[] args) {
		DbContext ctx = getDbContext();

		ctx.getSettings().setDisplayMode(DisplayMode.DISPLAY);

		StockLevelInformation stockLevelInformation = ctx.openInfosystem(StockLevelInformation.class);
		stockLevelInformation.setArtikel(getSelectedProduct("10003"));
		stockLevelInformation.invokeStart();

		Iterable<Row> rows = stockLevelInformation.table().getRows();
		for (Row row : rows) {
			ctx.out().println(
					row.getLgruppe().getSwd() + " - " + row.getLager().getSwd() + " - " + row.getLplatz().getSwd() + " - " + row.getLemge() + " - " + row.getString("leinheit"));
		}
	}

	/**
	 * Selects product with idno.
	 *
	 * @param idno The product's idno.
	 * @return The product as an instance of Product.
	 */
	private Product getSelectedProduct(String idno) {
		SelectionBuilder<Product> selectionBuilder = SelectionBuilder.create(Product.class);
		selectionBuilder.add(Conditions.eq(Product.META.idno, idno));
		return QueryUtil.getFirst(getDbContext(), selectionBuilder.build());
	}
}
