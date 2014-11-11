package de.abas.training.objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.ProductEditor;
import de.abas.training.common.AbstractAjoAccess;

/**
 * Creating new products from a txt file with logging.
 *
 * @author abas Software AG
 *
 */
public class CreateProductsFromTXT extends AbstractAjoAccess {

	/**
	 * Stores the products and logs the process.
	 *
	 * @param ctx The database context.
	 * @param bufferedWriter The BufferedWriter instance.
	 * @param productEditor The ProductEditor instance.
	 * @throws IOException Exception thrown if an error occurred while writing to log file.
	 */
	private void commitProducts(DbContext ctx, BufferedWriter bufferedWriter, ProductEditor productEditor) throws IOException {
		// For testing purposes
		// productEditor.abort();

		productEditor.commit();
		Product objectId = productEditor.objectId();
		String write = "created: " + objectId.getIdno() + " - " + objectId.getSwd();
		ctx.out().println(write);
		bufferedWriter.write(write);
		bufferedWriter.newLine();
	}

	/**
	 * Returns the field names as String array.
	 *
	 * @param read The field names as String.
	 * @return The field names as String array.
	 */
	private String[] initFieldNames(String read) {
		String[] value = read.split(";");
		return value;
	}

	/**
	 * Returns the field values as String array.
	 *
	 * @param read The field values as String.
	 * @return The field values as String array.
	 */
	private String[] initFieldValue(String read) {
		return read.split(";");
	}

	@Override
	public void run(String[] args) {
		DbContext dbContext = getDbContext();

		String readFile = "java/projects/AJOBasicEnglishFinally/files/CreateProductsFromTXT.txt";
		String logFile = "java/projects/AJOBasicEnglishFinally/files/CreateProductsFromTXT.log";

		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;

		try {
			if (getMode().equals(ContextMode.SERVER_MODE.toString())) {
				bufferedReader = new BufferedReader(new FileReader(readFile));
				bufferedWriter = new BufferedWriter(new FileWriter(logFile));
			}
			else {
				dbContext.out().println("Server-Mode only");
				return;
			}

			String read = "";
			String[] field = null;
			String[] value = null;

			read = bufferedReader.readLine();

			if (read != null) {
				dbContext.out().println(read);
				field = initFieldNames(read);
			}
			else {
				dbContext.out().println("File: is empty");
				bufferedReader.close();
				bufferedWriter.close();
				return;
			}

			while ((read = bufferedReader.readLine()) != null) {
				value = initFieldValue(read);
				ProductEditor productEditor = dbContext.newObject(ProductEditor.class);
				for (int i = 0; i < value.length; i++) {
					productEditor.setString(field[i], value[i]);
					dbContext.out().println(field[i] + " - > " + value[i]);
				}
				commitProducts(dbContext, bufferedWriter, productEditor);
			}
			dbContext.out().println("end");
			bufferedReader.close();
			bufferedWriter.close();
		}
		catch (FileNotFoundException e) {
			getDbContext().out().println(e.getMessage());
			return;
		}
		catch (IOException e) {
			getDbContext().out().println(e.getMessage());
			return;
		}
	}
}
