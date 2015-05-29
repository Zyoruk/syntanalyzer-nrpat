package tests;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import odtexporter.ODTexport;
import grammarCheck.GrammarChecker;
import grammarCheck.GrammarChecker.lexerror;
import tablebuilder.FilesManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import org.junit.Test;
//import org.junit.Test;
import Datastructs.SimpleList.SimpleList;

public class Tests {
	@Test
	public void test3() throws IOException{
		FilesManager file = new FilesManager();
		SimpleList<Object[]> matrix = new SimpleList<Object[]>();
		file.FilesMan(matrix);
		file.describe(matrix);
	}

	@Test
	public void test() {
		//fail("Not yet implemented");
		String thePath = "./grammar/Gramatica.txt";
		Reader r;
		try {
			//r = new FileReader(thePath);
			 r =new InputStreamReader(new FileInputStream(thePath), "Cp1252");
			 new GrammarChecker(new BufferedReader(r));
		} catch (IOException | lexerror e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//@Test
	public void test2(){
		ODTexport exporter = new ODTexport();
		final Object[][] data = new Object[6][2];
		data[0] = new Object[] { "January", 1 };
		data[1] = new Object[] { "February", 3 };
		data[2] = new Object[] { "March", 8 };
		data[3] = new Object[] { "April", 10 };
		data[4] = new Object[] { "May", 15 };
		data[5] = new Object[] { "June", 18 };
		
		String[] columns = new String[] { "Month", "Temp" };
		TableModel model = new DefaultTableModel(data, columns);
		exporter.createFiles(model, 1);
	}
	
	//@Test
	public void test4(){
		String t = "abs | abc";
		String[] t2 = t.split(" ");
		StringBuilder t4 = new StringBuilder();
		for(int i =0; i < t2.length ;i++){
			String t3 = t2[i];
			System.out.println(t3);
			t4.append(t3);
			System.out.println(t4);
		}
		t4.replace(t4.indexOf("|"), t4.indexOf("|")+1, " ");
		System.out.println(t4);
	}
}

