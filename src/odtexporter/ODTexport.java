package odtexporter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.table.TableModel;

import org.jopendocument.dom.spreadsheet.Sheet;
// import org.jopendocument.dom.OOUtils;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import constants.Constants;

public class ODTexport {

    Constants K = new Constants();

    public void createFiles(TableModel model, int tableOp) {

	try {
	    if ( tableOp == 1 ) {
		final File file = new File( K._TABLE1 );
		SpreadSheet.createEmpty( model ).saveAs( file );

		final Sheet sheet = SpreadSheet.createFromFile( file )
			.getSheet( 0 );
		final Sheet sheet2 = SpreadSheet.createFromFile( file )
			.addSheet( "Hoja2" );
		final File outputFile = new File( K._TABLE1 );
		sheet.getSpreadSheet().saveAs( outputFile );
		sheet2.getSpreadSheet().saveAs( outputFile );

	    } else if ( tableOp == 2 ) {
		final File file = new File( K._TABLE2 );
		SpreadSheet.createEmpty( model ).saveAs( file );

		final Sheet sheet = SpreadSheet.createFromFile( file )
			.getSheet( 0 );
		final Sheet sheet2 = SpreadSheet.createFromFile( file )
			.addSheet( "Hoja2" );
		final File outputFile = new File( K._TABLE2 );
		sheet.getSpreadSheet().saveAs( outputFile );
		sheet2.getSpreadSheet().saveAs( outputFile );

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
