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
    Object[][]	      firstsArr;
    Object[][]	      data;
    SimpleList < Object[] > grammar;
    TableModel	      tb = new DefaultTableModel();

    private void calcFirsts() {

	final SimpleList < Object[] > temp = new SimpleList <>( grammar );
	final SimpleList < Object[] > firsts = new SimpleList < Object[] >();
	for ( int i = 0; i < grammar.length(); i++ ) {
	    firsts.append( getFirstOf( temp.getData()[0].toString() ) );
	    temp.delete();
	}
	firstsArr = new Object[firsts.length()][1];
	int i = 0;
	while ( firsts.getData() != null ) {
	    firstsArr[i] = firsts.getData();
	    firsts.delete();
	    i++;
	}

    }

    public TableModel createTable(SimpleList < Object[] > pGrammar) {

	grammar = pGrammar;
	getRows();
	calcFirsts();

	columns = new String[pGrammar.length()];
	final TableModel table = new DefaultTableModel( firstsArr , rows );
	return table;

    }

    @SuppressWarnings("unchecked")
    private Object[] getFirstOf(String pProd) {

	final SimpleList < Object[] > tempGrammar = new SimpleList <>( grammar );
	SimpleList < String > rightSideOfProd = null;
	for ( int i = 0; i <= grammar.length(); i++ ) {
	    if ( tempGrammar.getData()[0].toString().equals( pProd ) ) {
		rightSideOfProd = ( SimpleList < String > ) tempGrammar
			.getData()[1];
		break;
	    }
	    tempGrammar.delete();
	}
	final SimpleList < String > temp3 = new SimpleList < String >();
	final SimpleList < String > rightSideOfProdTemp = new SimpleList <>(
		rightSideOfProd );
	while ( rightSideOfProdTemp.getData() != null ) {
	    final StringBuilder stB = new StringBuilder();
	    if ( Character.isUpperCase( rightSideOfProd.getData().charAt( 0 ) ) ) {
		final Object[] prodStr = getFirstOf( Character
			.toString( rightSideOfProd.getData().charAt( 0 ) ) );
		for ( final Object element : prodStr ) {
		    System.out.println( element );
		    if ( element != null ) {
			temp3.append( element.toString() );
		    }
		}
	    } else if ( rightSideOfProdTemp.getData().length() != 1 ) {
		if ( Character.toString(
			rightSideOfProdTemp.getData().charAt( 1 ) )
			.equals( "+" )
			| Character.toString(
				rightSideOfProdTemp.getData().charAt( 1 ) )
				.equals( "?" )
			| Character.toString(
				rightSideOfProdTemp.getData().charAt( 1 ) )
				.equals( "*" ) ) {
		    stB.append( rightSideOfProdTemp.getData().charAt( 0 ) );
		    stB.append( rightSideOfProdTemp.getData().charAt( 1 ) );
		    temp3.append( stB.toString() );

		} else if ( Character.toString(
			rightSideOfProdTemp.getData().charAt( 0 ) )
			.equals( "(" ) ) {
		    final int RBRACK = rightSideOfProdTemp.getData().indexOf(
			    ')' );
		    stB.append( rightSideOfProdTemp.getData().substring( 0 ,
			    RBRACK + 1 ) );
		    if ( Character.toString(
			    rightSideOfProdTemp.getData().charAt( RBRACK + 1 ) )
			    .equals( "+" )
			    | Character.toString(
				    rightSideOfProdTemp.getData().charAt(
					    RBRACK + 1 ) ).equals( "?" )
			    | Character.toString(
				    rightSideOfProdTemp.getData().charAt(
					    RBRACK + 1 ) ).equals( "*" ) ) {
			stB.append( rightSideOfProdTemp.getData().charAt(
				RBRACK + 1 ) );
			temp3.append( stB.toString() );
		    } else {
			temp3.append( stB.toString() );
		    }
		} else if ( Character.isDigit( rightSideOfProdTemp.getData()
			.charAt( 0 ) ) ) {
		    stB.append( rightSideOfProdTemp.getData().charAt( 0 ) );
		    for ( int intcount = 1; intcount < rightSideOfProdTemp
			    .getData().length(); intcount++ ) {
			if ( Character.isDigit( rightSideOfProdTemp.getData()
				.charAt( intcount ) ) ) {
			    stB.append( rightSideOfProdTemp.getData().charAt(
				    intcount ) );
			} else {
			    break;
			}
		    }
		    temp3.append( stB.toString() );
		} else if ( Character.toString(
			rightSideOfProdTemp.getData().charAt( 0 ) )
			.equals( "ñ" ) ) {
		    stB.append( rightSideOfProdTemp.getData().charAt( 0 ) );
		    temp3.append( stB.toString() );

		} else if ( rightSideOfProdTemp.getData().substring( 0 , 2 )
			.equals( "id" ) ) {
		    stB.append( rightSideOfProdTemp.getData().substring( 0 , 2 ) );
		    temp3.append( stB.toString() );

		} else {
		    stB.append( rightSideOfProdTemp.getData().charAt( 0 ) );
		    temp3.append( stB.toString() );
		}
	    } else {
		stB.append( rightSideOfProdTemp.getData().charAt( 0 ) );
		temp3.append( stB.toString() );
	    }

	    rightSideOfProdTemp.delete();
	}
	// }<>
	// System.out.println( temp3.describe() );
	return getFirstsAux( temp3 );
    }

    private Object[] getFirstsAux(SimpleList < String > pIn) {

	final Object[] toreturn = new Object[pIn.length()];
	final SimpleList < String > temp = pIn;
	int i = 0;
	while ( temp.getData() != null ) {
	    toreturn[i] = temp.getData();
	    temp.delete();
	    i++;
	}
	return toreturn;
    }

    private String[] getRows() {

	final SimpleList < Object[] > temp = grammar;
	rows = new String[grammar.length()];
	for ( int i = 0; i < grammar.length(); i++ ) {
	    rows[i] = temp.getElementAt( i )[0].toString();
	}
	return rows;
    }

}
