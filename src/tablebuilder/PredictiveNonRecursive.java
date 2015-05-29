/**
 * 
 */
package tablebuilder;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import Datastructs.SimpleList.SimpleList;

/**
 * @author zyoruk
 *
 */
public class PredictiveNonRecursive {

	Object[][] steps;
	
	String[] columns;
	String[] rows;
	Object[][] data;
	
	SimpleList<Object[]> grammar;
	
	public TableModel createTable(SimpleList<Object[]> pGrammar){
		getRows();
		calcFirsts();
		this.columns =new String[pGrammar.length()];
		TableModel table = new DefaultTableModel(data,columns);
		this.grammar = pGrammar;
		return table;
		
	}
	
	private String[] getRows(){
		for (int i = 0 ; i < grammar.length();i++){
			this.rows[i] = (String) grammar.getElementAt(i)[1];
		}
		return this.rows;	
	}
	
	private String[] getColumns(){
		return this.columns;
	}
	TableModel tb = new DefaultTableModel();
	
	private void calcFirsts(){
		SimpleList<Object[]> temp =  this.grammar;
		SimpleList<Object[]> firsts = new SimpleList<Object[]>();
		for(int i = 0 ; i < this.grammar.length();i++){
			firsts.append(getFirstOf(temp.getData()[0].toString()));
			temp.delete();
		}
	}
	
	private Object[] getFirstsAux(SimpleList<String> pIn){
		Object[] toreturn = new Object[pIn.length()];
		SimpleList<String> temp = pIn;
		for (int i = 0 ; i <pIn.length();i++){
			toreturn[i] =temp.getData();
			temp.delete();
		}
		return toreturn;
	}
	@SuppressWarnings("unchecked")
	private Object[] getFirstOf(String pProd){
		SimpleList<Object[]> temp =  this.grammar;
		SimpleList<String> temp2 = null;
		for ( int i = 0; i < this.grammar.length();i++){
			if( temp.getData()[0].toString() == pProd){
				temp2 = (SimpleList<String>) temp.getData()[1];
				break;
			}			
		}
		if(temp2 == null){
			return null;
		}
		SimpleList<String> temp3 = new SimpleList<String>();
		for(int i = 0 ; i < temp2.length();i++){
			StringBuilder stB = new StringBuilder();
			if ( Character.isUpperCase(temp2.getData().charAt(0))){
				Object[] prodStr = getFirstOf(Character.toString(temp2.getData().charAt(0)));
				for(int j = 0 ; j < prodStr.length; j++){
					temp3.append(prodStr[i].toString());
				}
			}
			stB.append(temp2.getData().charAt(0));
			temp3.append(stB.toString());
		}		
		return getFirstsAux(temp3);
		
	}
	
	private void calcFollows(){
		
	}

}
