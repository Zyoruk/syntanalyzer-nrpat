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
    String[]		columns;
    String[]		rows;
    Object[][]	      data;
    SimpleList < Object[] > grammar;
    SimpleList<Object[]> followsMatrix;
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
    grammar = pGrammar;
    followsMatrix = new SimpleList<Object[]>();
	//getRows();
	//calcFirsts();
	getFollows();
	describe(this.followsMatrix);

	//columns = new String[pGrammar.length()];
	final TableModel table = new DefaultTableModel( data , columns );
	//
	return table;

    }

    @SuppressWarnings("unchecked")
    private String[] getFirstOf(String pProd) {

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

    private String[] getFirstsAux(SimpleList < String > pIn) {

	final String[] toreturn = new String[pIn.length()];
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

}
