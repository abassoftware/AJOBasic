package de.abas.training.basic.recursion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.Product.Row;
import de.abas.erp.db.selection.Conditions;
import de.abas.erp.db.selection.SelectionBuilder;
import de.abas.erp.db.util.QueryUtil;
import de.abas.training.basic.common.AbstractAjoAccess;

public class ProductionList extends AbstractAjoAccess {

	/**
	 * Starts program in client mode.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		new ProductionList().runClientProgram(args);
	}

	@Override
	public void run(String[] args) {
		DbContext ctx = getDbContext();

		BufferedReader bufferedReader = null;
		try {
			// reads product idno from console (e.g. 30001)
			ctx.out().println("Enter product idno: ");
			bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String idno = bufferedReader.readLine();

			Product product = getSelectedProduct(idno);
			if (product != null) {
				ctx.out().println("Product: " + product.getIdno() + " - " + product.getSwd());
				printAllLevelsOfProductionList(product, 1);
			}
			else {
				ctx.out().println("Product with idno " + idno + " does not exist.");
			}
		}
		catch (IOException e) {
			ctx.out().println("Could not read from console: " + e.getMessage());
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

	/**
	 * Indents according to indentationLevel.
	 *
	 * @param indentationLevel Level of indentation.
	 * @return The indentation as String.
	 */
	private String makeIndentation(int indentationLevel) {
		String indentation = "";
		for (int i = 0; i < indentationLevel; i++) {
			indentation = indentation + "  ";
		}
		return indentation;
	}

	/**
	 * Recursive method to print all levels of a product's production list.
	 *
	 * @param product The current product.
	 * @param indentationLevel The level of indentation.
	 */
	private void printAllLevelsOfProductionList(Product product, int indentationLevel) {
		int lowerLevelIndentation = ++indentationLevel;
		Iterable<Row> rows = product.table().getRows();
		for (Row row : rows) {
			getDbContext().out().println(makeIndentation(indentationLevel) + row.getProductListElem().getIdno() + " - " + row.getProdListElem().getSwd());
			if (row.getProductListElem() instanceof Product) {
				printAllLevelsOfProductionList((Product) row.getProductListElem(), lowerLevelIndentation);
			}
		}
	}

}
