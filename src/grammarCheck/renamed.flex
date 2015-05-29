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
%char
%cupsym sym
%cup
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
String outFile = "lexicalAnalisis.txt";
String source = "./syntanalyzer-nrpat/ALex";
BufferedWriter out = new BufferedWriter(
new FileWriter(outFile));
out.write(output.toString());
out.close();
}

%}
//LineTerminator = \r|\n|\r\n|\n\r
LineTerminator = [\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]
Num = [0-9][0-9]*
ID = ([a-zA-Z"_"]) ([a-zA-Z0-9]){0,31}
%%

/* Tokens */
"+" {
String str = "Operator: " + "+" + "\n";
output.append(str);
return new Symbol(sym.ADD, yychar,yyline);
}
"*" {
String str = "Operator: " + "*" + "\n";
output.append(str);
return new Symbol(sym.TIMES, yychar,yyline);
}
"?" {
String str = "Operator: " + "?" + "\n";
output.append(str);
return new Symbol(sym.QUESTION, yychar,yyline);
}
"(" {
String str = "Symbol: " + "(" + "\n";
output.append(str);
return new Symbol(sym.OPNBR, yychar,yyline);
}
")" {
String str = "Operator: " + ")" + "\n";
output.append(str);
return new Symbol(sym.CLSBR, yychar,yyline);
}
"ñ" {
String str = "Empty: " + "ñ" + "\n";
output.append(str);
return new Symbol(sym.TERMINAL, yychar,yyline);
}
"|" {
String str = "Operator: " + "|" + "\n";
output.append(str);
return new Symbol(sym.TERMINAL, yychar,yyline);
}
"->" {
String str = "Separator: " + "->" + "\n";
output.append(str);
return new Symbol(sym.TERMINAL, yychar,yyline);
}
{ID} {
String str = "Identifier: " + yytext() + "\n";
output.append(str);
return new Symbol(sym.ID, yychar,yyline);
}
{Num} {
String str = "number: " + yytext() + "\n";
output.append(str);
return new Symbol(sym.TERMINAL, yychar,yyline);
}
{LineTerminator} {
String str = "New Line" + "\n";
output.append(str);
return new Symbol(sym.NEWLINE, yychar,yyline);
}
{WhiteSpace} {/* Nothing */}
[^] {
e_counter++;
String errorString = "Lexical Error #" + e_counter + ". "+"Invalid Character: "+"<"+yytext()+">"+ " Row: " +yyline+ ", " +"Column: "+ yycolumn + "\n";
errors.append(errorString);
System.out.println("LexicalError #" + e_counter + ". "+"Invalid Character "+"<"+yytext()+">"+ " Row: " +yyline+ ", " +"Column: "+ yycolumn);
}		
