/**
 * @author      Joseph Miller <miller12 @ augsburg.edu>
 * @version     JDK/JRE 1.8.0_141  
 */

/* Declarations */
package scanner;

%%

%class  Scanner   /* Names the produced java file */
%function nextToken /* Renames the yylex() function */
%type   Token      /* Defines the return type of the scanning function */
%eofval{
  return null;
%eofval}
/* Patterns */

other         = .
letter        = [A-Za-z]
word          = {letter}+ | {letter}+[0-9]+{letter}*

lineterminator = |\n|\n
whitespace     = {lineterminator} | [    \f]

symbols       = [ ";" | "," | "." | ":" | "[" | "]" | "(" | ")" | "+" | "-" | "=" | "<>" | "<" | "<=" | ">" | ">=" | "*" | "/" | ":=" ]



integer 	=   [0-9] | [1-9][0-9]*
float 	=   [0-9]+ \. [0-9]*
posexp 	=   [0-9]+ "E" [0-9]* | [0-9]+ "E+" [0-9]*
negexp 	=   [0-9]+ "E-" [0-9]*

comment =   [{] [^*] ~ [}]




%%
/* Lexical Rules */

{word}     {
             //System.out.println("Found a word: " + yytext());
             return( new Token( yytext(), 1));
            }
            
{whitespace}  {  /* Ignore Whitespace */ 
                 
              }
			  
{symbols}    { 
			 //System.out.println("Found a symbol: " + yytext());
             return( new Token( yytext(), 2));
           }
			  
		   
{integer}    { 
			 //System.out.println("Found a number: " + yytext());
             return( new Token( yytext(), 4));
           }
		   
{float}	{
			 return( new Token( yytext(), 5));
			}

{posexp}	{
			 return( new Token( yytext(), 6));
			}
	
{negexp}	{
			 return( new Token( yytext(), 7));
			}	

{comment}	{
			 /* Ignore Comments */ 
}
	
	
{other}    { 
			 //System.out.println("Found an other: " + yytext());
             return( new Token( yytext(), 3));
           }
		   
		   
		   
		   
		   
		   
		   
		   
		   
           