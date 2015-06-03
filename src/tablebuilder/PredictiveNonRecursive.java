/**
 *
 */
package tablebuilder;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Datastructs.SimpleList.SimpleList;

/** @author zyoruk , jeukel */
public class PredictiveNonRecursive {

<<<<<<< HEAD
	Object[][]	      steps;
	Object[][]	      firstsArr;
	Object[][]	      data;
	String[]		terminals;
	String[]		rows;
=======
    Object[][]	      steps;
    Object[][]	      firstsArr;
    Object[][]	      data;
    String[]		columns;
    String[]		rows;

    SimpleList < Object[] > grammar;
    SimpleList < Object[] > followsMatrix;
    TableModel	      tb;
    String[] produc;

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
>>>>>>> e6ea1233d7a1149c5280a002bc1f9cdc377d6410

	SimpleList < Object[] > grammar;
	SimpleList < Object[] > followsMatrix;
	TableModel	      tb;

<<<<<<< HEAD
	private void calcFirsts() {
=======
    public TableModel createTable(SimpleList < Object[] > pGrammar) {
    grammar = pGrammar;
    followsMatrix = new SimpleList<Object[]>();
	//getRows();
	//calcFirsts();
    produc = new String[grammar.length()];
	getFollows();
	describe(this.followsMatrix);
>>>>>>> e6ea1233d7a1149c5280a002bc1f9cdc377d6410

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
		followsMatrix = new SimpleList<Object[]>();
		calcFirsts();
		getFollows();
		getRows();
		getTerminals();
		buildData();
		final TableModel table = new DefaultTableModel( data , terminals );
		return table;

	}

	private String[] getTerminals() {

		final SimpleList < String > columnsT = new SimpleList < String >();
		//Recorrer arreglo de primeros.
		for ( int i = 0; i < firstsArr.length; i++ ) {
			for ( int j = 0; j < firstsArr[i].length; j++ ) {

				if ( !columnsT.exists( ( String ) firstsArr[i][j] ) ) {
					columnsT.append( ( String ) firstsArr[i][j] );
				}
			}
		}

		//Recorrer la matriz de siguientes.TODO
		SimpleList < Object[] > temp = new SimpleList<> (this.followsMatrix);
		while(temp.getData()!=null){
			@SuppressWarnings("unchecked")
			SimpleList<String> temp2 = (SimpleList<String>) temp.getData()[1];
			//Recorrer la lista 
			while(temp2.getData()!=null){
				if(!columnsT.exists(temp2.getData())){
					columnsT.append(temp2.getData());
				}
				temp2.delete();
			}
			temp.delete();
		}
		terminals = new String[columnsT.length() + 1];
		int k = 1;
		while ( columnsT.getData() != null ) {
			terminals[k] = columnsT.getData();
			columnsT.delete();
			k++;
		}
		terminals[0] = "Table";
		// columns[columns.length - 1] = "$";
		return terminals;
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

	private String[] getRows() {

		final SimpleList < Object[] > temp = grammar;
		rows = new String[grammar.length()];
		for ( int i = 0; i < grammar.length(); i++ ) {
			rows[i] = temp.getElementAt( i )[0].toString();
		}
		return rows;
	}

	@SuppressWarnings("unchecked")
	public void getFollows(){
		SimpleList<Object[]> matrixC = new SimpleList<>(grammar);;
		SimpleList<String> follows;
		SimpleList<String> implies;
<<<<<<< HEAD
		String[] produc = new String[grammar.length()];

=======
		
>>>>>>> e6ea1233d7a1149c5280a002bc1f9cdc377d6410
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
//			System.out.println("Starting with: " + elem);


			//find follows by implies of one production			
			while(matrixC.length() != 0){
				implies = new SimpleList<String>((SimpleList<String>) matrixC.getData()[1]);
				if(elem.equals("B")){
<<<<<<< HEAD
//					System.out.println("Eval for implies root: " + implies.getData());
=======
					System.out.println("Eval for implies's root is: " + implies.getData());
>>>>>>> e6ea1233d7a1149c5280a002bc1f9cdc377d6410
				}

				//find follows by production
				while(implies.length() != 0){
					String imp = implies.getData();
					StringBuilder sb = new StringBuilder(imp);

					//If production found
					if(sb.indexOf(elem) != -1){
//						System.out.println("Element found: " + elem);

						//Next is null
						if((sb.indexOf(elem)+1) >= sb.length()){ 
//							System.out.println("Next element of: " + elem + " is null.");
							//Do not repeat follows
							if(!follows.exists(Character.toString('$'))){
								follows.append(Character.toString('$'));
							}
						}else{
//							System.out.println("Next element of: " + elem + " is not null.");
							//if next is a "string"							
							if(sb.charAt(sb.indexOf(elem)+1) == '('){	
//								System.out.println("Next element of: " + elem + " is a string.");
								int u = (sb.indexOf(")")); String t;

								if((sb.indexOf(")")+1) == ('+' | '?' | '*')){
									t = sb.substring( 0 , u+1 );
								}else{
									t = sb.substring( 0 , u );
								}
								follows.append(t);
							}else{
//								System.out.print("Next element of: " + elem + " is an element.");

								//If found, gets follower 
								char v = sb.charAt(sb.indexOf(elem) + 1);
//								System.out.println(" Which is: " + v + ".");
								follows.append(Character.toString(v)); 
							}
						}
					}
					implies.delete();
				}//end inner while

				matrixC.delete();
			}//end outer while
<<<<<<< HEAD

=======
			
			
			
>>>>>>> e6ea1233d7a1149c5280a002bc1f9cdc377d6410
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
//			System.out.println( "Ending with: " + elem);
		}
//		System.out.println();		
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
					if(str[u].charAt(0) == '�'){
						String[] sr = getFollowOf(first);
						
						for(int f = 0; f < sr.length; f++){
							String[] stg = getFirstOf(sr[f]);
							for(int g = 0; g < stg.length; g++){
								tmp.append(stg[g]);
							}						
						}
					}else{
						tmp.append(str[u]);
					}
				}			
			}

			follows.delete();
		}
		return tmp;
	}

	@SuppressWarnings("unchecked")
<<<<<<< HEAD
	public void describe(SimpleList < Object[] > matrix) {
=======
	private String[] getFollowOf (String elem){
		SimpleList<Object[]> matrixC = new SimpleList<>(grammar);
		SimpleList<String> follows = new SimpleList<String>();
		SimpleList<String> implies;
		//System.out.println("Starting with: " + elem);
		
		
		//find follows by implies of one production			
		while(matrixC.length() != 0){
			implies = new SimpleList<String>((SimpleList<String>) matrixC.getData()[1]);
			if(elem.equals("B")){
				//System.out.println("Eval for implies's root is: " + implies.getData());
			}
			
			//find follows by production
			while(implies.length() != 0){
				String imp = implies.getData();
				StringBuilder sb = new StringBuilder(imp);
				
				//If production found
				if(sb.indexOf(elem) != -1){
					System.out.println("Element found: " + elem);
					
					//Next is null
					if((sb.indexOf(elem)+1) >= sb.length()){ 
						System.out.println("Next element of: " + elem + " is null.");
						//Do not repeat follows
						if(!follows.exists(Character.toString('$'))){
							follows.append(Character.toString('$'));
						}
					}else{
						System.out.println("Next element of: " + elem + " is not null.");
						//if next is a "string"							
						if(sb.charAt(sb.indexOf(elem)+1) == '('){	
							System.out.println("Next element of: " + elem + " is a string.");
							int u = (sb.indexOf(")")); String t;
							
							if((sb.indexOf(")")+1) == ('+' | '?' | '*')){
								t = sb.substring( 0 , u+1 );
							}else{
								t = sb.substring( 0 , u );
							}
							follows.append(t);
						}else{
							System.out.print("Next element of: " + elem + " is an element.");
							
							//If found, gets follower 
							char v = sb.charAt(sb.indexOf(elem) + 1);
							System.out.println(" Which is: " + v + ".");
							follows.append(Character.toString(v)); 
						}
					}
				}
				implies.delete();
			}//end inner while
			
			matrixC.delete();
		}//end outer while
		
		String[] ret = new String[follows.length()];
		for(int f = 0; f < follows.length(); f++){
			ret[f] = follows.getData();
			follows.delete();
		}	
		
		return ret;		
	}
	
	@SuppressWarnings("unchecked")
    public void describe(SimpleList < Object[] > matrix) {
>>>>>>> e6ea1233d7a1149c5280a002bc1f9cdc377d6410

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

	private void buildData() {
		this.data = new Object[this.terminals.length][this.firstsArr.length];
		SimpleList < Object[] > grammarT = new SimpleList<> (this.grammar);
		// recorrer la lista de primeros
		for (int i = 0; i < this.firstsArr.length; i ++){
			Object[] firsts = this.firstsArr[i];
			@SuppressWarnings("unchecked")
			SimpleList<String> tempProd = (SimpleList<String>) grammarT.getData()[1];
			for (int j= 0; j < this.firstsArr[i].length; j++){
				int K= getTerminalIndex((String)firsts[j]);
				String prod =tempProd.getData();
				if(K!=-1){
					System.out.println(K);
					//TODO hay que ir agregando las implicaciones.
					this.data[i][K] = prod;
				}
				if(tempProd.getData()==null){
					break;
				}
				tempProd.delete();
			}
			grammarT.delete();
		}
		// recorrer la lista de siguientes
		SimpleList < Object[] > temp = new SimpleList<> (this.followsMatrix);
		int i = 0;
		while(temp.getData()!=null){
			@SuppressWarnings("unchecked")
			SimpleList<String> temp2 = (SimpleList<String>) temp.getData()[1];
			while(temp2.getData()!=null){
				int K= getTerminalIndex(temp2.getData());
				if(K!=-1){
					//TODO hay que ir agregando las implicaciones.
					this.data[i][K] = "X";
				}
				temp2.delete();
			}
			i++;
			temp.delete();
		}
	}
	private int getTerminalIndex(String pTerminal){
		for(int i = 0; i < this.terminals.length;i++){
			if(pTerminal.equals(this.terminals[i])){
				return i;
			}
		}
		return -1;
	}
}