package odtexporter;


import java.io.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

//import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import constants.Constants;


public class ODTexport {
	Constants K;
	public void createFiles(Object[][] data, String[] columns){
		
		TableModel model = new DefaultTableModel(data, columns);  
		// Save the data to an ODS file and open it.
		final File file1 = new File(K._TABLE1);
		final File file2 = new File (K._TABLE2);
		try {
			SpreadSheet.createEmpty(model).saveAs(file1);
			SpreadSheet.createEmpty(model).saveAs(file2);
			//OOUtils.open(file); abre automaticamente el archivo recien creado.
			
		} catch (FileNotFoundException e) {
			System.out.println(K._FILE_NOT_FOUND);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
