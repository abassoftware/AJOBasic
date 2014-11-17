package de.abas.training.recursion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.abas.erp.common.type.IdImpl;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.Product.Row;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.util.QueryUtil;
import de.abas.training.common.AbstractAjoAccess;

public class ProductionList extends AbstractAjoAccess {

	private Product getSelectedProduct(String idno) {
		SelectionBuilder<Product> selectionBuilder = SelectionBuilder.create(Product.class);
		selectionBuilder.add(Conditions.eq(Product.META.idno, idno));
		return QueryUtil.getFirst(getDbContext(), selectionBuilder.build());
	}
	
	private Product getSelectedProductByObjectID(String objectID) {
		return getDbContext().load(Product.class, new IdImpl(objectID));
	}
	
	@Override
	public void run(String[] args) {
		
		BufferedReader bufferedReader = null;
		
		try {
			getDbContext().out().println("Enter product idno: ");
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String idno = bufferedReader.readLine();
			
			Product product = getSelectedProduct(idno);
			if (product != null) {
				 getDbContext().out().println("Product: " + product.getIdno() + " - " + product.getSwd());
				 printAllLevelsOfProductionList(product, 1);
			}
		} catch (IOException e) {
			getDbContext().out().println("Could not read from console: " + e.getMessage());
		}
		finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					getDbContext().out().println("Could not close BufferedReader instance: " + e.getMessage());
				}
			}
		}

	}

	private void printAllLevelsOfProductionList(Product product, int indentationLevel) {
		int lowerLevelIndentation = ++indentationLevel;
		Iterable<Row> rows = product.table().getRows();
		for (Row row : rows) {
			getDbContext().out().println(makeIndentation(indentationLevel) + row.getProductListElem().getIdno() + " - " + row.getProductListElem().getSwd());
			if (row.getProductListElem() instanceof Product) {
				printAllLevelsOfProductionList((Product)row.getProductListElem(), lowerLevelIndentation);
			}
		}
	}

	private String makeIndentation(int indentationLevel) {
		String indentation = "";
		for(int i = 0; i < indentationLevel; i++) {
			indentation = indentation + "  ";
		}
		return indentation;
	}

	public static void main(String[] args) {
		new ProductionList().runClientProgram(args);

	}

}
