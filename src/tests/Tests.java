package tests;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.junit.Test;

import grammarCheck.GrammarChecker;
import odtexporter.ODTexport;

public class Tests {

	//@Test
	public void test() {
		//fail("Not yet implemented");
		String thePath = "./syntanalyzer-nrpat/grammar/Gramatica.txt";
		GrammarChecker checker = new GrammarChecker();
		checker.checkGrammar(thePath);
	}

	@Test
	public void test2(){
		ODTexport exporter = new ODTexport();
		final Object[][] data = new Object[6][2];
		data[0] = new Object[] { "CACA", 1 };
		data[1] = new Object[] { "February", 3 };
		data[2] = new Object[] { "March", 8 };
		data[3] = new Object[] { "April", 10 };
		data[4] = new Object[] { "May", 15 };
		data[5] = new Object[] { "June", 18 };
		
		String[] columns = new String[] { "Month", "Temp" };
		TableModel model = new DefaultTableModel(data, columns);
		exporter.createFiles(model, 1);
	}
}

