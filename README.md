# Pascal to MIPs Compiler
### Author: Joseph Miller
## Description
> This project is written in java and will contain a .jar file that will be able to take in pascal code and compile it into MIPs assembly code. The scanner used is [JFlex](http://jflex.de/).

## Parts
> This project will be made from 4 major sections:
- Scanner
- Parser
- Semantic Analysis
- Code Generation

## Ouput
> The compiller will output several files derived from the inputed pascal code
- *InputFileName.asm*: the MIPs assembly code
- *InputFileName.table*: the symbol table, a list of all the variables declared and their types
- *InputFileName.tree*: an indented list of the syntax tree produced
