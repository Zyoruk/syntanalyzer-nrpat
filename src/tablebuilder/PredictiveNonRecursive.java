/**
 *
 */
package tablebuilder;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Datastructs.SimpleList.SimpleList;

/** @author zyoruk , jeukel */
public class PredictiveNonRecursive {

    Object[][]	      steps;
    Object[][]	      firstsArr;
    Object[][]	      data;
    String[]		columns;
    String[]		rows;

    SimpleList < Object[] > grammar;
    SimpleList < Object[] > followsMatrix;
    TableModel	      tb;

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
<<<<<<< HEAD
    grammar = pGrammar;
    followsMatrix = new SimpleList<Object[]>();
	//getRows();
	//calcFirsts();
	getFollows();
	describe(this.followsMatrix);

	//columns = new String[pGrammar.length()];
	final TableModel table = new DefaultTableModel( data , columns );
	//
=======

	tb = new DefaultTableModel();
	grammar = pGrammar;
	calcFirsts();
	getRows();
	getColumns();
	final TableModel table = new DefaultTableModel( firstsArr , columns );
	grammar = pGrammar;
	followsMatrix = new SimpleList < Object[] >();
>>>>>>> 33a39627b67a5b63d5fe3b1167460604603b4542
	return table;

    }

    private String[] getColumns() {

	final SimpleList < String > columnsT = new SimpleList < String >();

	for ( int i = 0; i < firstsArr.length; i++ ) {
	    for ( int j = 0; j < firstsArr[i].length; j++ ) {

		if ( !columnsT.exists( ( String ) firstsArr[i][j] ) ) {
		    columnsT.append( ( String ) firstsArr[i][j] );
		}
	    }
	}
	columns = new String[columnsT.length() + 1];
	int k = 1;
	while ( columnsT.getData() != null ) {
	    columns[k] = columnsT.getData();
	    columnsT.delete();
	    k++;
	}
	columns[0] = "Table";
	// columns[columns.length - 1] = "$";
	return columns;
    }

    @SuppressWarnings("unchecked")
    private String[] getFirstOf(String pProd) {

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

    private String[] getFirstsAux(SimpleList < String > pIn) {

	final String[] toreturn = new String[pIn.length()];
	final SimpleList < String > temp = pIn;
	int i = 0;
	while ( temp.getData() != null ) {
	    toreturn[i] = temp.getData();
	    temp.delete();
	    i++;
	}
	return toreturn;
    }

    @SuppressWarnings("unchecked")
    public void getFollows() {

	final SimpleList < Object[] > matrixC = new SimpleList <>( grammar );
	final SimpleList < Object[] > maTMP = new SimpleList <>( grammar );
	SimpleList < String > follows = new SimpleList < String >();
	final String[] produc = new String[grammar.length()];

	// Make list of Productions
	int i = 0;
	while ( maTMP.getData() != null ) {
	    produc[i] = ( String ) maTMP.getData()[1];
	    maTMP.delete();
	    i++;
	}

	// find follow of each Production
	for ( final String element : produc ) {

	    // find follows by implies of one production
	    while ( matrixC.getData() != null ) {

		final SimpleList < String > implies = ( SimpleList < String > ) matrixC
			.getData()[1];
		final String imp = implies.getData();

		// find follows by production
		while ( ( ( SimpleList < Object[] > ) matrixC.getData()[1] )
			.getData() != null ) {
		    final StringBuilder sb = new StringBuilder( imp );

		    // If production not found
		    if ( sb.indexOf( element ) == -1 ) {
			( ( SimpleList < Object[] > ) matrixC.getData()[1] )
				.delete(); // deletes a node

		    } else {
			// Next is null
			if ( ( sb.indexOf( ( String ) matrixC.getData()[1] ) + 1 ) >= sb
				.length() ) {
			    // Do not repeat follows
			    if ( !follows.exists( Character.toString( '$' ) ) ) {
				follows.append( Character.toString( '$' ) );
			    }
			} else {
			    // if next is string
			    if ( sb.indexOf( ( String ) matrixC.getData()[1] ) == '(' ) {
				final int u = ( sb.indexOf( ")" ) );
				String t;

				if ( ( sb.indexOf( ")" ) + 1 ) == ( '+' | '?' | '*' ) ) {
				    t = sb.substring( 0 , u + 1 );
				} else {
				    t = sb.substring( 0 , u );
				}
				follows.append( t );
			    } else {
				// If found, gets follower
				final char v = sb
					.charAt( sb.indexOf( ( String ) matrixC
						.getData()[0] ) + 1 );
				follows.append( Character.toString( v ) );
			    }

			}
		    }
		}
		matrixC.delete();
	    }
	    // Sets new follows matrix.
	    final Object[] arr = new Object[2];
	    arr[0] = element;
	    follows = replaceFirstOf( follows );
	    arr[1] = follows;
	    follows.clear();
	    followsMatrix.append( arr );
	}
    }

    private String[] getRows() {

	final SimpleList < Object[] > temp = grammar;
	rows = new String[grammar.length()];
	for ( int i = 0; i < grammar.length(); i++ ) {
	    rows[i] = temp.getElementAt( i )[0].toString();
	}
	return rows;
    }
<<<<<<< HEAD
    
    @SuppressWarnings("unchecked")
	public void getFollows(){
		SimpleList<Object[]> matrixC = new SimpleList<>(grammar);;
		SimpleList<String> follows;
		SimpleList<String> implies;
		String[] produc = new String[grammar.length()];
		
		int i = 0;
		while(matrixC.getData() != null){
			produc[i] = (String) matrixC.getData()[0];
			matrixC.delete();
			i++;
		}
		
		//find follow of each Production
		for(String elem : produc){
			matrixC = new SimpleList<>(grammar);
			follows = new SimpleList<String>();
			System.out.println(elem);
			
			//find follows by implies of one production			
			while(matrixC.length() != 0){
				implies = new SimpleList<String>((SimpleList<String>) matrixC.getData()[1]);
				//System.out.println(implies.length());
				
				//find follows by production
				while(implies.length() != 0){
					String imp = implies.getData();
					StringBuilder sb = new StringBuilder(imp);
					
					//If production not found
					if(sb.indexOf(elem) != -1){
						System.out.println(sb.indexOf(elem));
						
						//Next is null
						if((sb.indexOf(elem)+1) >= sb.length()){ 
							//Do not repeat follows
							if(!follows.exists(Character.toString('$'))){
								follows.append(Character.toString('$'));
							}
						}else{
							
							//if next is a "string"							
							if(sb.charAt(sb.indexOf(elem)+1) == '('){								
								int u = (sb.indexOf(")")); String t;
								
								if((sb.indexOf(")")+1) == ('+' | '?' | '*')){
									t = sb.substring( 0 , u+1 );
								}else{
									t = sb.substring( 0 , u );
								}
								follows.append(t);
							}else{
								
								//If found, gets follower 
								char v = sb.charAt(sb.indexOf(elem) + 1);
								//System.out.println(v);
								follows.append(Character.toString(v)); 
							}
						}
					}
					
					implies.delete();
				}//end inner while
				
				matrixC.delete();
			}//end outer while

			//Sets new follows matrix.
			Object[] arr = new Object[2];
			arr[0] = elem;
			if(follows.length() == 0){
				follows.append(Character.toString('$'));
			}else{
				follows = replaceFirstOf(follows);
			}
			arr[1] = follows;
			followsMatrix.append(arr);
		}
		System.out.print("DONE");
	}
	
	private SimpleList<String> replaceFirstOf(SimpleList<String> follows){
		//Doing follows list
		SimpleList<String> tmp = new SimpleList<String>();
		while(follows.getData() != null){
			
			String first = follows.getData();			
			if(!Character.isUpperCase(first.charAt(0))){
				tmp.append(first);
			}else{
				/*String[] str = getFirstOf(first);
				for(int u = 0; u < str.length; u++){
					tmp.append(str[u]);
				}*/
			}			
		}
		return tmp;
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
	
		    System.out.println( "FOLLOWS:" );
		    System.out
			    .println( ( ( SimpleList < String > ) matrix.getData()[1] )
				    .describe() );
	
		    matrix.delete();
		}
	}
=======

    private SimpleList < String > replaceFirstOf(SimpleList < String > follows) {

	// Doing follows list
	final SimpleList < String > tmp = new SimpleList < String >();
	while ( follows.getData() != null ) {

	    final String first = follows.getData();
	    if ( !Character.isUpperCase( first.charAt( 0 ) ) ) {
		tmp.append( first );
	    } else {
		final String[] str = getFirstOf( first );
		for ( final String element : str ) {
		    tmp.append( element );
		}
	    }
	}
	follows = tmp; // Replace Upper with firstOf.
	return follows;
    }

    private buildTable() {
>>>>>>> 33a39627b67a5b63d5fe3b1167460604603b4542

	// recorrer la lista de primeros
	// recorrer la lista de siguiente

    }
}