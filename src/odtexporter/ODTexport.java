package odtexporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.jopendocument.model.OpenDocument;

import constants.Constants;

public class ODTexport {

    Constants K = new Constants();

    public void createFiles(TableModel model, int tableOp, TableModel model2) {

    	try {
    		if ( tableOp == 1 ) {
    			final File file = new File( K._TABLE1 );
    			SpreadSheet ss1 = SpreadSheet.createEmpty(model);
    			ss1.getFirstSheet().setName("Tabla");
    			ss1.addSheet(1, "Pasos");
    			ss1.getSheet(1).merge(model2, 1, 0);
    			ss1.getSheet(1).setValueAt("Pasos", 0, 0);
    			ss1.saveAs(file);

    		} else if ( tableOp == 2 ) {

    			final File file = new File( K._TABLE2 );
    			SpreadSheet ss1 = SpreadSheet.createEmpty(model);
    			ss1.getFirstSheet().setName("Tabla");
    			ss1.addSheet(1, "Pasos");
    			ss1.getSheet(1).merge(model2, 1, 0);
    			ss1.getSheet(1).setValueAt("Pasos", 0, 0);
    			ss1.saveAs(file);

    		} else {
    			System.out.println( "err" );
    		}

	    // OOUtils.open(file); abre automaticamente el archivo recien
	    // creado.

	} catch ( final FileNotFoundException e ) {
	    System.out.println( K._FILE_NOT_FOUND );
	    e.printStackTrace();
	} catch ( final IOException e ) {
	    e.printStackTrace();
	}
    }
}
