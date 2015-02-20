package de.abas.training.basic.objects;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.schema.part.Product;
import de.abas.erp.db.schema.part.ProductEditor;
import de.abas.training.basic.common.AbstractAjoAccess;

/**
 * Creating new products from a txt file with logging.
 *
 * @author abas Software AG
 *
 */
public class CreateProductsFromTXT_log4j extends AbstractAjoAccess {

	public static void main(String[] args) {
		new CreateProductsFromTXT_log4j().runClientProgram(args);
	}

	private static Logger logger = Logger
			.getLogger(CreateProductsFromTXT_log4j.class);

	@Override
	public int run(String[] args) {
		DbContext dbContext = getDbContext();
		String readFile = "/files/CreateProductsFromTXT.txt";
		BufferedReader bufferedReader = null;

		try {
			if (getMode().equals(ContextMode.CLIENT_MODE.toString())) {
				bufferedReader = new BufferedReader(new FileReader(readFile));
			}
			else {
				logger.warn("Client-Mode only");
				return 1;
			}

			String read = "";
			String[] field = null;
			String[] value = null;

			read = bufferedReader.readLine();

			if (read != null) {
				dbContext.out().println(read);
				logger.info(read);
				field = initFieldNames(read);
			}
			else {
				logger.warn("File is empty");
				return 1;
			}

			while ((read = bufferedReader.readLine()) != null) {
				value = initFieldValue(read);
				ProductEditor productEditor =
						dbContext.newObject(ProductEditor.class);
				for (int i = 0; i < value.length; i++) {
					productEditor.setString(field[i], value[i]);
					logger.info(field[i] + " -> " + value[i]);
				}
				commitProducts(productEditor);
			}
			logger.info("end");
		}
		catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			return 1;
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
			return 1;
		}
		finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			}
			catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return 0;
	}

	/**
	 * Stores the products and logs the process.
	 *
	 * @param productEditor The ProductEditor instance.
	 * @throws IOException Exception thrown if an error occurred while writing to log
	 * file.
	 */
	private void commitProducts(ProductEditor productEditor) throws IOException {
		// For testing purposes
		// productEditor.abort();

		productEditor.commit();
		try {
			Product objectId = productEditor.objectId();
			logger.info("created: " + objectId.getIdno() + " - " + objectId.getSwd());
		}
		catch (NullPointerException e) {
			logger.error(e.getMessage(), e);
		}
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

}
