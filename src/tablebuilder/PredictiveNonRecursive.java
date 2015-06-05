/**
 *
 */
package tablebuilder;

import java.util.Stack;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Datastructs.SimpleList.SimpleList;

/** @author zyoruk , jeukel */
public class PredictiveNonRecursive {

	String[][]	      steps;
	Object[][]	      firstsArr;
	Object[][]	      data;
	String[]		terminals;
	String[]		rows;

	SimpleList<String> stepsList; 
    SimpleList < Object[] > grammar;
    SimpleList < Object[] > followsMatrix;
    TableModel	      tb;
    String[] produc;
    SimpleList<String> newLines;
    SimpleList<String> errors;

    public void init(){
    	newLines = new SimpleList<>();
    	errors = new SimpleList<>();
    }
    
	private void calcFirsts() {
		final SimpleList < Object[] > temp = new SimpleList <>( grammar );
		final SimpleList < Object[] > firsts = new SimpleList < Object[] >();
		for ( int i = 0; i < grammar.length(); i++ ) {
			String prod =temp.getData()[0].toString();
			String[] f = getFirstOf( prod );
			firsts.append( f );
			StringBuilder step =new StringBuilder();
			step.append("Calcular los primeros de ");
			step.append(prod + " :{");
			for (int j = 0 ; j < f.length;j++){
				step.append(f[j]);
				if(j+1 < f.length){
					step.append(", ");	
				}
			}
			step.append("}");
			stepsList.append(step.toString());
			temp.delete();
		}
//		System.out.println(this.stepsList.describe());
		firstsArr = new Object[firsts.length()][1];
		int i = 0;
		while ( firsts.getData() != null ) {
			firstsArr[i] = firsts.getData();
			firsts.delete();
			i++;
		}
	}

	public TableModel createTable(SimpleList < Object[] > pGrammar) {
		stepsList= new SimpleList<String>();
		grammar = pGrammar;
		calcFirsts();
		getFollows();
		getRows();
		getTerminals();
//		describe(this.followsMatrix);
		buildData();
		final TableModel table = new DefaultTableModel( data , terminals );
		return table;

	}
	public TableModel getStepTable(){
		String[] sc = new String[1];
		sc[0] = "PASOS";
		final TableModel table = new DefaultTableModel( getSteps() , sc);
		return table;
	}
	private String[][] getSteps(){
		steps = new String[this.stepsList.length()][1];
		for (int i = 0; i < this.stepsList.length();i++){
			steps[i][0] = this.stepsList.getElementAt(i);
		}
		return steps;
	}

	@SuppressWarnings("unchecked")
	private String[] getTerminals() {

		final SimpleList < String > columnsT = new SimpleList < String >();
		//Recorrer arreglo de primeros.
		for ( int i = 0; i < firstsArr.length; i++ ) {
			for ( int j = 0; j < firstsArr[i].length; j++ ) {

				if ( !columnsT.exists( ( String ) firstsArr[i][j] ) ) {
					if((( String ) firstsArr[i][j]).equals("ñ")){
						firstsArr[i][j]="ñ";
						columnsT.append( "ñ" );	
					}else{
						columnsT.append( ( String ) firstsArr[i][j] );	
					}
				}
			}
		}

		//Recorrer la matriz de siguientes.TODO
		SimpleList < Object[] > temp = new SimpleList<> (followsMatrix);
		SimpleList<String> temp2 ;
		while(temp.getData()!=null){
			temp2= new SimpleList<>((SimpleList<String>) temp.getData()[1]);
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

		SimpleList < Object[] > tempGrammar = new SimpleList <>( grammar );
		SimpleList < String > rightSideOfProd = null;
		for ( int i = 0; i < grammar.length(); i++ ) {
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
						.equals( "�" ) ) {
					stB.append( 'ñ' );
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

		final SimpleList < Object[] > temp = new SimpleList<>(grammar);
		rows = new String[grammar.length()];
		for ( int i = 0; i < grammar.length(); i++ ) {
			rows[i] = temp.getElementAt( i )[0].toString();
		}
		return rows;
	}

	@SuppressWarnings("unchecked")
	public void getFollows(){
		followsMatrix = new SimpleList<Object[]>();
		SimpleList<Object[]> matrixC = new SimpleList<>(grammar);
		SimpleList<String> follows;
		SimpleList<String> implies;
		produc = new String[grammar.length()];


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
			
			//Add to steps
			StringBuilder step= new StringBuilder();
			step.append("Siguientes de ");
			step.append(elem);
			step.append(" :{");
			for(int h = 0;h < follows.length();h++){
				step.append(follows.getElementAt(h));
				if(h+1 < follows.length()){
					step.append(", ");	
				}
			}
			step.append("}");
			stepsList.append(step.toString());
//			System.out.println(stepsList.describe());
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
//					System.out.println("Element found: " + elem);
					
					//Next is null
					if((sb.indexOf(elem)+1) >= sb.length()){ 
//						System.out.println("Next element of: " + elem + " is null.");
						//Do not repeat follows
						if(!follows.exists(Character.toString('$'))){
							follows.append(Character.toString('$'));
						}
					}else{
//						System.out.println("Next element of: " + elem + " is not null.");
						//if next is a "string"							
						if(sb.charAt(sb.indexOf(elem)+1) == '('){	
//							System.out.println("Next element of: " + elem + " is a string.");
							int u = (sb.indexOf(")")); String t;
							
							if((sb.indexOf(")")+1) == ('+' | '?' | '*')){
								t = sb.substring( 0 , u+1 );
							}else{
								t = sb.substring( 0 , u );
							}
							follows.append(t);
						}else{
//							System.out.print("Next element of: " + elem + " is an element.");
							
							//If found, gets follower 
							char v = sb.charAt(sb.indexOf(elem) + 1);
//							System.out.println(" Which is: " + v + ".");
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

	@SuppressWarnings("unchecked")
	private void buildData() {
		this.data = new Object[this.firstsArr.length][this.terminals.length];
		SimpleList < Object[] > grammarT = new SimpleList<> (this.grammar);
		// recorrer la lista de primeros
		for (int i = 0; i < this.firstsArr.length; i ++){
			Object[] firsts = this.firstsArr[i];
			SimpleList<String> tempProd = (SimpleList<String>) grammarT.getData()[1];
			for (int j= 0; j < this.firstsArr[i].length; j++){
				int K= getTerminalIndex((String)firsts[j]);
				String prod =tempProd.getData();
				if(K!=-1){
					//TODO hay que ir agregando las implicaciones.
					if(prod !=null && Character.isUpperCase(prod.charAt(0))){
						String[] tempProds = this.getFirstOf(Character.toString(prod.charAt(0)));
						for(int t = 0;t<tempProds.length;t++){
							this.data[i][t+1] = tempProds[t];
						}
					}else if(prod!=null){
						this.data[i][K] = prod;	
					}
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
//		this.describe(temp);
		int i = 0;
		while(temp.getData()!=null){
			SimpleList<String> temp2 = (SimpleList<String>) temp.getData()[1];
			while(temp2.getData()!=null){
				int K= getTerminalIndex(temp2.getData());
				if(K!=-1){
					//TODO hay que ir agregando las implicaciones.
					this.data[i][K] = temp2.getData();
				}
				temp2.delete();
			}
			i++;
			temp.delete();
		}
		
		//solo para una K
		int t=0;
		for (t= 0;t< terminals.length;t++){
			if(this.terminals[t].equals("�")){
				break;
			}
		}
		//Eliminar terminal
		for (int a = t;a < terminals.length;a++){
			if(a+1 < terminals.length){
				terminals[a]=terminals[a+1];
			}else{
				terminals[a] = " ";
			}				
		}	
		//Eliminar dentro de data
		for(int b = 0;b< data.length;b++){
			for (int a = t;a < data[0].length;a++){
				if(a+1 < data[0].length){
					data[b][a]=data[b][a+1];
				}else{
					data[b][a] = " ";
				}				
			}			
		}
		
		for (int k= 0; k < this.produc.length;k++){
			this.data[k][0] = this.produc[k];
		}
		
	}
	
	private int getTerminalIndex(String pTerminal){
		for(int i = 0; i < this.terminals.length;i++){
			if(pTerminal.equals(this.terminals[i])){
				return i;
			}
//			else if(this.terminals[i].charAt(0) != '('){
//				if(pTerminal.equals(Character.toString(this.terminals[i].charAt(0)))){
//					return i;			
//				}
//			}
//				else{
//				String temp = this.terminals[i].substring(1, this.terminals[i].indexOf(')'));
//				if(pTerminal.equals(temp)){
//					return i;			
//				}
//			}
		}
//		System.out.println(pTerminal);
		return -1;
	}
	
	public boolean evalString(String toEval){
		Stack<String> theStack= new Stack<String>();
		theStack.push("$");
	
		//tenemos entonces una pila con $ y un string que termina en $
		//El string que entra hay que "tokenizarlo"
		String tokenizedString = tokenize(toEval,"");
		
		StringBuilder temp1 = new StringBuilder();
		temp1.append(tokenizedString);
		temp1.append("$");
		String eval = temp1.toString();
<<<<<<< HEAD
		newLines.append(tokenizedString);
		//System.out.println(tokenizedString);
=======
		System.out.println(temp1);
		
>>>>>>> 979ca03b0efc40ede36b906378b310c7d4314ba1
		return true;
	}
	
	private StringBuilder tokenized;
	private String tokenize(String rawString,String s2){
//		if ( rawString == ""){
//			return "id";
//		}else if(rawString.length() == 1){
//			return "id";
//		}
		tokenized = new StringBuilder();
		
		String s = rawString;
		if(this.getTerminalIndex(s)== -1 && this.getTerminalIndex(s2)==-1){
			if(s.length()> 1 && !s2.equals(s.substring(0, 1))){
//				System.out.println("s " + s);
//				System.out.println("s2 " + s2);
				s2 = s2 + s.substring(0, 1);
				s = s.substring(1);
				tokenize(s2, "");
				tokenize(s,s2);
			}else if (s.length()>1){
//				System.out.println("s " + s);
//				System.out.println("s2 " + s2);
				s2 = s.substring(0, 1)+"+";
				s = s.substring(1);
				tokenize(s2, "");
				tokenize(s,s2);
			}else if(s.length()==0){
				tokenized.append("id");
			}
		}else if(this.getTerminalIndex(s)== -1 && this.getTerminalIndex(s2)!=-1){
			tokenize(s2,"");
//			tokenized.append("id");
		}else if(this.getTerminalIndex(s)!= -1 && this.getTerminalIndex(s2)==-1){
			tokenized.append(s);
			tokenized.append("id");
		}
		//Recorrer el string en busca de terminales.
		return tokenized.toString();
	}
	
	public boolean isTextCorrect(){
		SimpleList<String> newLinesC = new SimpleList<>(newLines);
		String Line;
		boolean is = true;
		int line = 1;
		//Check line by line its validity. Or logs the line of error.
		while(newLinesC.getData() != null){ 
			Line = newLinesC.getData();
			is = isLineCorrect(Line);
			if(!is){
				this.errors.append("Se encontró error en la Linea " + line 
						+ ": " + "[" + Line + "]");
			}
			newLinesC.delete();
			line++;
		}
		errors.describe();
		if (errors.length() > 0){
			return false;
		}
		return true; 
	}
	
	public boolean isLineCorrect(String Line){
		for(String terminal : terminals){
			Line.replace(terminal,"");
		}
		if(Line != ""){
			return false;
		}
		return true;		
	}
}