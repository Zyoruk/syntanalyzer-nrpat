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

	getRows();
	calcFirsts();

	columns = new String[pGrammar.length()];
	final TableModel table = new DefaultTableModel( data , columns );
	grammar = pGrammar;
	followsMatrix = new SimpleList<Object[]>();
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
		SimpleList<Object[]> matrixC = new SimpleList<>(grammar);
		SimpleList<Object[]> maTMP = new SimpleList<>(grammar);
		SimpleList<String> follows = new SimpleList<String>();
		String[] produc = new String[grammar.length()];
		
		//Make list of Productions
		int i = 0;
		while(maTMP.getData() != null){
			produc[i] = (String) maTMP.getData()[1];
			maTMP.delete();
			i++;
		}
		
		//find follow of each Production
		for(int o = 0; o < produc.length; o++){
			
			//find follows by implies of one production
			while(matrixC.getData() != null){
				
				SimpleList<String> implies = (SimpleList<String>) matrixC.getData()[1];
				String imp = implies.getData();
				
				//find follows by production
				while(((SimpleList<Object[]>) matrixC.getData()[1]).getData() != null){
					StringBuilder sb = new StringBuilder(imp);
					
					//If production not found
					if(sb.indexOf(produc[o]) == -1){ 
						((SimpleList<Object[]>) matrixC.getData()[1]).delete(); //deletes a node
					
					}else{
						//Next is null
						if((sb.indexOf((String) matrixC.getData()[1])+1) >= sb.length()){ 
							//Do not repeat follows
							if(!follows.exists(Character.toString('$'))){
								follows.append(Character.toString('$'));
							}
						}else{
							//if next is string
							if(sb.indexOf((String) matrixC.getData()[1]) == '('){
								int u = (sb.indexOf(")")); String t;
								
								if((sb.indexOf(")")+1) == ('+' | '?' | '*')){
									t = sb.substring( 0 , u+1 );
								}else{
									t = sb.substring( 0 , u );
								}
								follows.append(t);
							}else{
								//If found, gets follower 
								char v = sb.charAt(sb.indexOf((String) matrixC.getData()[0]) + 1);
								follows.append(Character.toString(v)); 
							}
							
						}
					}
				}
				matrixC.delete();
			}
			//Sets new follows matrix.
			Object[] arr = new Object[2];
			arr[0] = produc[o];
			follows = replaceFirstOf(follows);
			arr[1] = follows;
			follows.clear();
			followsMatrix.append(arr);
		}
	}
	
	private SimpleList<String> replaceFirstOf(SimpleList<String> follows){
		//Doing follows list
				SimpleList<String> tmp = new SimpleList<String>();
				while(follows.getData() != null){
					
					String first = follows.getData();			
					if(!Character.isUpperCase(first.charAt(0))){
						tmp.append(first);
					}else{
						String[] str = getFirstOf(first);
						for(int u = 0; u < str.length; u++){
							tmp.append(str[u]);
						}
					}			
				}
				follows = tmp; //Replace Upper with firstOf.
				return follows;
	}

}
