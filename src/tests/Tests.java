package tests;

import grammarCheck.GrammarChecker;
import grammarCheck.GrammarChecker.lexerror;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
<<<<<<< HEAD

=======
import odtexporter.ODTexport;
import grammarCheck.GrammarChecker;
import grammarCheck.GrammarChecker.lexerror;
import tablebuilder.FilesManager;
>>>>>>> 9b82987be5821b7d42ac2b9de2aa27151a735d2b
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import odtexporter.ODTexport;

import org.junit.Test;

import tablebuilder.FilesManager;
import tablebuilder.PredictiveNonRecursive;
import Datastructs.SimpleList.SimpleList;

public class Tests {
<<<<<<< HEAD
=======
	
	@Test
	public void test3() throws IOException{
		FilesManager file = new FilesManager();
		SimpleList<Object[]> matrix = new SimpleList<Object[]>();
		file.FilesMan(matrix);
		file.describe(matrix);
	}
	
	@Test
	public void test5() throws IOException{
		//FilesManager file = new FilesManager();
		//SimpleList<Object[]> matrix = new SimpleList<Object[]>();
		//file.FilesMan(matrix);
		//file.describe(matrix);
		
	}
>>>>>>> 9b82987be5821b7d42ac2b9de2aa27151a735d2b

    // @Test
    public void test() {

	// fail("Not yet implemented");
	final String thePath = "./grammar/Gramatica.txt";
	Reader r;
	try {
	    // r = new FileReader(thePath);
	    r = new InputStreamReader( new FileInputStream( thePath ) ,
		    "Cp1252" );
	    new GrammarChecker( new BufferedReader( r ) );
	} catch ( IOException | lexerror e ) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    // @Test
    public void test2() {

	final ODTexport exporter = new ODTexport();
	final Object[][] data = new Object[6][2];
	data[0] = new Object[] { "January", 1 };
	data[1] = new Object[] { "February", 3 };
	data[2] = new Object[] { "March", 8 };
	data[3] = new Object[] { "April", 10 };
	data[4] = new Object[] { "May", 15 };
	data[5] = new Object[] { "June", 18 };

	final String[] columns = new String[] { "Month", "Temp" };
	final TableModel model = new DefaultTableModel( data , columns );
	exporter.createFiles( model , 1 );
    }

    // @Test
    public void test3() throws IOException {

	final FilesManager file = new FilesManager();
	final SimpleList < Object[] > matrix = new SimpleList < Object[] >();
	file.FilesMan( matrix );
	file.describe( matrix );
    }

    // @Test
    public void test4() {

	final String t = "abs | abc";
	final String[] t2 = t.split( " " );
	final StringBuilder t4 = new StringBuilder();
	for ( final String t3 : t2 ) {
	    System.out.println( t3 );
	    t4.append( t3 );
	    System.out.println( t4 );
	}
	t4.replace( t4.indexOf( "|" ) , t4.indexOf( "|" ) + 1 , " " );
	System.out.println( t4 );
    }

    @Test
    public void test6() throws IOException {

	final FilesManager file = new FilesManager();
	final SimpleList < Object[] > matrix = new SimpleList < Object[] >();
	file.FilesMan( matrix );
	// file.describe( matrix );
	final TableModel model = new PredictiveNonRecursive()
		.createTable( matrix );
	final ODTexport export = new ODTexport();
	export.createFiles( model , 1 );
    }
}
