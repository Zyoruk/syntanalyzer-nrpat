/**
 *
 */
package tablebuilder;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Datastructs.SimpleList.SimpleList;

/** @author zyoruk */
public class PredictiveNonRecursive {

    Object[][]	      steps;
    String[]		columns;
    String[]		rows;
    Object[][]	      data;
    SimpleList < Object[] > grammar;
    TableModel	      tb = new DefaultTableModel();

    private void calcFirsts() {

	final SimpleList < Object[] > temp = grammar;
	final SimpleList < Object[] > firsts = new SimpleList < Object[] >();
	for ( int i = 0; i < grammar.length(); i++ ) {
	    firsts.append( getFirstOf( temp.getData()[0].toString() ) );
	    temp.delete();
	}
    }

    public TableModel createTable(SimpleList < Object[] > pGrammar) {

	getRows();
	calcFirsts();

	columns = new String[pGrammar.length()];
	final TableModel table = new DefaultTableModel( data , columns );
	grammar = pGrammar;
	return table;

    }

    @SuppressWarnings("unchecked")
    private Object[] getFirstOf(String pProd) {

	final SimpleList < Object[] > temp = grammar;
	SimpleList < String > temp2 = null;
	for ( int i = 0; i < grammar.length(); i++ ) {
	    if ( temp.getData()[0].toString() == pProd ) {
		temp2 = ( SimpleList < String > ) temp.getData()[1];
		break;
	    }
	}
	if ( temp2 == null ) { return null; }
	final SimpleList < String > temp3 = new SimpleList < String >();
	for ( int i = 0; i < temp2.length(); i++ ) {
	    final StringBuilder stB = new StringBuilder();
	    if ( Character.isUpperCase( temp2.getData().charAt( 0 ) ) ) {
		final Object[] prodStr = getFirstOf( Character.toString( temp2
			.getData().charAt( 0 ) ) );
		for ( final Object element : prodStr ) {
		    temp3.append( element.toString() );
		}
	    }

	    if ( temp2.getData().charAt( 1 ) == ( '+' | '?' | '*' ) ) {
		stB.append( temp2.getData().charAt( 0 ) );
		stB.append( temp2.getData().charAt( 1 ) );
		temp3.append( stB.toString() );

	    } else if ( temp2.getData().charAt( 0 ) == '(' ) {
		final int RBRACK = temp2.getData().indexOf( ')' );
		stB.append( temp2.getData().substring( 0 , RBRACK ) );
		if ( temp2.getData().charAt( RBRACK + 1 ) == ( '+' | '?' | '*' ) ) {
		    stB.append( temp2.getData().charAt( RBRACK + 1 ) );
		    temp3.append( stB.toString() );

		}
		temp3.append( stB.toString() );
	    } else if ( Character.isDigit( temp2.getData().charAt( 0 ) ) ) {
		stB.append( temp2.getData().charAt( 0 ) );
		for ( int intcount = 1; intcount < temp2.getData().length(); intcount++ ) {
		    if ( Character.isDigit( temp2.getData().charAt( intcount ) ) ) {
			stB.append( temp2.getData().charAt( intcount ) );
		    } else {
			break;
		    }
		}
		temp3.append( stB.toString() );
	    } else if ( temp2.getData().substring( 0 , 1 ) == "id" ) {
		stB.append( temp2.getData().substring( 0 , 1 ) );
		temp3.append( stB.toString() );

	    } else {
		stB.append( temp2.getData().charAt( 0 ) );
		temp3.append( stB.toString() );
	    }
	}
	return getFirstsAux( temp3 );
    }

    private Object[] getFirstsAux(SimpleList < String > pIn) {

	final Object[] toreturn = new Object[pIn.length()];
	final SimpleList < String > temp = pIn;
	for ( int i = 0; i < pIn.length(); i++ ) {
	    toreturn[i] = temp.getData();
	    temp.delete();
	}
	return toreturn;
    }

    private String[] getRows() {

	for ( int i = 0; i < grammar.length(); i++ ) {
	    rows[i] = ( String ) grammar.getElementAt( i )[1];
	}
	return rows;
    }

}
