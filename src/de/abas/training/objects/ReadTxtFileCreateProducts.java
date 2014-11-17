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

public class ReadTxtFileCreateProducts extends AbstractAjoAccess {

	@Override
	public void run(String[] args) {
		DbContext dbContext = getDbContext();
		String readFile = "win/tmp/PC_Components.txt";
		String logFile = "win/tmp/PC_Components.log";
		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		
		try {
			bufferedReader = new BufferedReader(new FileReader(readFile));
			bufferedWriter = new BufferedWriter(new FileWriter(logFile));
			
			String read = "";
			
			String[] fieldName = null;
			String[] fieldValue = null;
			
			// read fieldNames
			read = bufferedReader.readLine();
			if(read != null){
				fieldName = read.split(";");
			}else {
				bufferedReader.close();
				bufferedWriter.close();
				return;
			}
			
			while ((read = bufferedReader.readLine()) != null) {
				fieldValue = read.split(";");
				ProductEditor productEditor = dbContext.newObject(ProductEditor.class);
				
				for (int i = 0; i < fieldName.length; i++) {
					productEditor.setString(fieldName[i], fieldValue[i]);
					//dbContext.out().println("");
				}				
				commitProcucts(dbContext, bufferedWriter, productEditor);
			}
			dbContext.out().println("end");
			bufferedReader.close();
			bufferedWriter.close();
			
		} catch (FileNotFoundException e) {
			dbContext.out().println(e.getMessage());
		} catch (IOException e) {
			dbContext.out().println(e.getMessage());
		}
	}

	private void commitProcucts(DbContext dbContext, BufferedWriter bufferedWriter, ProductEditor productEditor) throws IOException {
		productEditor.commit();
		
		Product product = productEditor.objectId();
		String write = "created: " + product.getIdno() + "-" + product.getSwd();
		bufferedWriter.write(write);
		bufferedWriter.newLine();
	}
}
