/**
 * 
 */
package grammarCheck;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import constants.Constants;

/**
 * @author zyoruk
 *
 */
public class GrammarChecker {
	Constants K;
	File file = null;
	FileReader reader = null;
	BufferedReader bufferReader = null;
	String basicRules = (K._GRAMMAR_RULES);
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
					System.out.println(K._GRAMMAR_ERROR);
				}
				else if(match.group(1) != null){
					System.out.println(fileLine);
					String[] split = fileLine.split(" ");
					if(split.length != 1){
						
					}
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


