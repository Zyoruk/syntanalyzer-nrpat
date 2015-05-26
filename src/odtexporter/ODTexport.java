package odtexporter;


import java.io.*;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import org.jopendocument.dom.spreadsheet.Sheet;
//import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import constants.Constants;


public class ODTexport {
	Constants K = new Constants();
	public void createFiles(Object[][] data, String[] columns,int tableOp){
		
		TableModel model;  
		try {
			if(tableOp == 1){
				final File file = new File (K._TABLE1);
				model = new DefaultTableModel(data, columns);
				SpreadSheet.createEmpty(model).saveAs(file);
				SpreadSheet.createFromFile(file).addSheet("HojaB");
				SpreadSheet.createFromFile(file).saveAs(file);
				
				
			}else if(tableOp == 2){
				final File file = new File (K._TABLE2);
				model = new DefaultTableModel(data, columns);
				
				SpreadSheet.createEmpty(model).saveAs(file);	
			}else{
				System.out.println("err");
			}
			
			//OOUtils.open(file); abre automaticamente el archivo recien creado.
			
		} catch (FileNotFoundException e) {
			System.out.println(K._FILE_NOT_FOUND);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
