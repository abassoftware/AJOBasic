package de.abas.training.useinfosystem;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.infosystem.standard.la.StockLevelInformation;
import de.abas.erp.db.infosystem.standard.la.StockLevelInformation.Row;
import de.abas.erp.db.schema.location.LocationHeader;
import de.abas.erp.db.schema.location.SelectableLocation;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.Selection;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.settings.DisplayMode;
import de.abas.erp.db.util.QueryUtil;
import de.abas.training.common.AbstractAjoAccess;

public class UseStockLevelInformation extends AbstractAjoAccess {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(String[] args) {
		DbContext dbContext = getDbContext();
		dbContext.getSettings().setDisplayMode(DisplayMode.DISPLAY);
		
		// open the infosystem
		StockLevelInformation stockLevelInformation = dbContext.openInfosystem(StockLevelInformation.class);
		
		// Set Artikel field
		//stockLevelInformation.setArtikel(getSelectedProduct(dbContext, "10002"));
		
		stockLevelInformation.setKlplatz(getSelectedLocation("L01.002"));
		
		// Press the start button
		stockLevelInformation.invokeStart();
		
		Iterable<Row> rows = stockLevelInformation.table().getRows();
		
		for (Row row : rows) {
			dbContext.out().println(row.getLgruppe() + " - " + 
					row.getLgruppe().getSwd() + " + " + 
					row.getLager().getSwd() + "  - " + 
					row.getLemge() + "  - " +
					row.getString("leinheit")+ "  - " +
					row.getString(StockLevelInformation.Row.META.leinheit));
		}
		

	}

	private LocationHeader getSelectedLocation(String swd) {
		SelectionBuilder<LocationHeader> selectionBuilder = SelectionBuilder.create(LocationHeader.class);
		selectionBuilder.add(Conditions.eq(LocationHeader.META.swd, swd));
		
		return QueryUtil.getFirst(getDbContext(), selectionBuilder.build());
	}

	private Product getSelectedProduct(DbContext dbContext, String idno) {
		
		SelectionBuilder<Product> selectionBuilder = SelectionBuilder.create(Product.class);
		selectionBuilder.add(Conditions.eq(Product.META.idno, idno));
		Product product = QueryUtil.getFirst(dbContext, selectionBuilder.build());
		return product;
	}


}
