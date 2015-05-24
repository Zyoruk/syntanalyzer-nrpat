/**
 * 
 */
package grammarCheck;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zyoruk
 *
 */
public class GrammarChecker {
	File file = null;
	FileReader reader = null;
	BufferedReader bufferReader = null;
	String basicRules = ("([+|?||]+)|([a-z]+)|([A-Z]+)|([(|)]+)");
	String fileLine;

	public boolean checkGrammar(String thePath){
		try{
			this.file = new File(thePath);
			this.reader = new FileReader(this.file);
			this.bufferReader = new BufferedReader(this.reader);
			Pattern patt = 	Pattern.compile(basicRules);

			while ((fileLine = this.bufferReader.readLine()) != null){
				Matcher match  = patt.matcher(fileLine);
				if (match.find()!= true){
					System.out.print("Grammar input error.");
				}
			}

		}catch (Exception error){
			error.printStackTrace();
			return false;
		}finally{
			try{
				if(null != reader){
					reader.close();
				}
			}catch(Exception othererror){
				othererror.printStackTrace();
				return false;
			}
		}
		return true;
	}
}


