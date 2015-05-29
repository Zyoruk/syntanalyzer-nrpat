package tablebuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import Datastructs.SimpleList.SimpleList;

public class FilesManager {

    public FilesManager() {

    }

    @SuppressWarnings("unchecked")
    public void describe(SimpleList < Object[] > matrix) {

	System.out.println( "LONGITUD:" );
	System.out.println( matrix.length() );
	System.out.println();

	while ( matrix.length() != 0 ) {
	    System.out.println( "PRODUCCION:" );
	    System.out.println( matrix.getData()[0] );
	    // System.out.println();

	    System.out.println( "TERMINALES:" );
	    System.out
		    .println( ( ( SimpleList < String > ) matrix.getData()[1] )
			    .describe() );

	    matrix.delete();
	}

    }

    public void FilesMan(SimpleList < Object[] > matrix) throws IOException {

	final File tmp = new File( "Gramatica.txt" );
	final BufferedReader arch = new BufferedReader( new FileReader( tmp ) );
	String line;

	while ( ( line = arch.readLine() ) != null ) {
	    if ( !line.isEmpty() ) {
		final SimpleList < String > terms = new SimpleList < String >();
		final Object[] twopl = new Object[2];

		final StringBuilder sb = new StringBuilder( line );
		while ( sb.indexOf( " " ) != -1 ) {
		    sb.deleteCharAt( sb.indexOf( " " ) );
		}
		final String line_nospc = sb.toString();

		final String[] elements_imp = line_nospc.split( "->" );
		twopl[0] = elements_imp[0];
		String Miau = elements_imp[1];

		final StringBuilder sp = new StringBuilder( Miau );
		if ( sp.indexOf( "|" ) != -1 ) {
		    while ( sp.indexOf( "|" ) != -1 ) {
			sp.replace( sp.indexOf( "|" ) , sp.indexOf( "|" ) + 1 ,
				" " );
		    }
		    Miau = sp.toString();
		    final String[] elem_cpy = Miau.split( " " );
		    for ( final String elem : elem_cpy ) {
			if ( elem.equals( "ID" ) ) {
			    terms.append( "id" );
			} else {
			    terms.append( elem );
			}
		    }
		} else {
		    if ( Miau.equals( "ID" ) ) {
			terms.append( "id" );
		    } else {
			terms.append( Miau );
		    }
		}
		twopl[1] = terms;
		matrix.append( twopl );
	    }
	}
	arch.close();
    }
}
