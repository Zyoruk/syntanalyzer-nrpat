package grammarCheck;
import java_cup.runtime.Symbol;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
%%
/*macros*/
%class GrammarChecker
%public
%full
%unicode
%line
%column
%cup
%cupsym sym
%char
%eof{
try{
this.LexicalOutput();
}catch(IOException ioe){
ioe.printStackTrace();
}
%eof}
%{

StringBuilder output = new StringBuilder();
StringBuilder errors = new StringBuilder();
private int counter;
private int e_counter;

/* output*/
private void LexicalOutput() throws IOException {
String outFile = "./output/lexicalAnalisis.txt";
BufferedWriter out = new BufferedWriter(
new FileWriter(outFile));
out.write(output.toString());
out.close();
}

%}
LineTerminator = \r|\n|\r\n|\n\r
//LineTerminator = [\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]
Num = [0-9][0-9]*
ID = ([a-zA-Z"_"]) ([a-zA-Z0-9]){0,31}
%%

/* Tokens */
"+" {
String str = "Operator: " + "+" + "\n";
output.append(str);
System.out.println(str);
}
"*" {
String str = "Operator: " + "*" + "\n";
output.append(str);
System.out.println(str);
}
"?" {
String str = "Operator: " + "?" + "\n";
output.append(str);
System.out.println(str);
}
"(" {
String str = "Symbol: " + "(" + "\n";
output.append(str);
System.out.println(str);
}
")" {
String str = "Operator: " + ")" + "\n";
output.append(str);
System.out.println(str);
}
"ñ" {
String str = "Empty: " + "ñ" + "\n";
output.append(str);
System.out.println(str);
}
"|" {
String str = "Operator: " + "|" + "\n";
output.append(str);
System.out.println(str);
}
"->" {
String str = "Separator: " + "->" + "\n";
output.append(str);
System.out.println(str);
}
{ID} {
String str = "Identifier: " + yytext() + "\n";
output.append(str);
System.out.println(str);
}
{Num} {
String str = "number: " + yytext() + "\n";
output.append(str);
System.out.println(str);
}
{LineTerminator} {
String str = "New Line" + "\n";
output.append(str);
System.out.println(str);
}
{WhiteSpace} {/* Nothing */}
[^] {
e_counter++;
String errorString = "Lexical Error #" + e_counter + ". "+"Invalid Character: "+"<"+yytext()+">"+ " Row: " +yyline+ ", " +"Column: "+ yycolumn + "\n";
errors.append(errorString);
System.out.println("LexicalError #" + e_counter + ". "+"Invalid Character "+"<"+yytext()+">"+ " Row: " +yyline+ ", " +"Column: "+ yycolumn);
}	
