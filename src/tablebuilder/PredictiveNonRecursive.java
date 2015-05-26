/**
 * 
 */
package tablebuilder;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author zyoruk
 *
 */
public class PredictiveNonRecursive {

	Object[][] steps;
	
	String[] columns;
	Object[][] data;
	
	String grammar;
	
	public TableModel createTable(String pGrammar){
		TableModel table = new DefaultTableModel(data,columns);
		this.grammar = pGrammar;
		return table;
		
	}
	
	private void calcFirsts(){
		
	}
	
	private void calcFollows(){
		
	}
}
