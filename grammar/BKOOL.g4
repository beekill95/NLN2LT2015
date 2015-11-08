/**
 * Student name: Nguyễn Mai Bảo Quân
 * Student ID: 51303225
 */
grammar BKOOL;

@lexer::header{
	package bkool.parser;
}

@parser::header{
	package bkool.parser;
}

options{
	language=Java;
}

// Syntax
program  : classes+ EOF;         // match keyword hello followed by an identifier

classes: CLASS ID (EXTENDS ID)? LP members* RP;
members
	: STATIC? attributes
	| methods
	;
attributes
	: mutable_atrs
	| immutable_atrs
	;
mutable_atrs : para_list SEMICOLON; // variables
immutable_atrs : FINAL type ID '=' expression SEMICOLON; // constants
id_list: ID (COMMA ID)*; // list of comma-separate list
type // list of types
	: primitive_types		#primitiveType
	| array_types			#arrayType
	| class_types			#classType
	;
primitive_types // primitive types include boolean, integer, float, string and void
	: INTEGER
	| BOOL
	| FLOAT
	| STRING
	;
array_types : (primitive_types|class_types) LSB INTLIT RSB;
class_types : ID; // class type is simply an identifier
	
methods // method comprises of functions which are normal methods and constructors
	: functions
	| constructor
	;
functions : (type|VOID) (STATIC)? ID parameter_list block_statement; // is function return-type type?
constructor : (STATIC)? ID parameter_list block_statement; // different from functions, constructor doesn't have return type
parameter_list
	: LB para_list (SEMICOLON para_list)* RB
	| LB RB
	;
para_list : id_list COLON type;

// define block statement && different types of statement
block_statement : LP (STATIC? attributes)* statements* RP;
statements
	: assign_statement SEMICOLON			#assignStm
	| if_statement							#ifStm
	| while_statement						#whileStm
	| break_statement SEMICOLON				#breakStm
	| continue_statement SEMICOLON			#continueStm
	| return_statement SEMICOLON			#returnStm
	| method_invo_statement SEMICOLON		#methodInvoStm
	| block_statement						#blockStm
	;
assign_statement : expression ':=' expression;
if_statement : IF expression THEN statements (ELSE statements)?;
while_statement : WHILE expression DO statements;
break_statement : BREAK;
continue_statement : CONTINUE;
return_statement : RETURN expression;
method_invo_statement
	: expression DOT ID list_expr			
	;
	
// define expression
expression
	: operands														#operandLit
	| LB expression RB												#braceExpr
	| <assoc=right> NEWOP ID list_expr								#createObjExpr		
	| expression DOT ID list_expr? 									#accessExpr
	| expression LSB expression RSB									#indexExpr
	| <assoc=right> (ADDOP|SUBOP) expression						#unaExpr
	| <assoc=right> NOTOP expression								#unaLogExpr
	| expression CONOP expression									#binStrExpr
	| expression (MULOP|FDIVOP|IDIVOP|MODOP) expression				#mulMathExpr
	| expression (ADDOP|SUBOP) expression							#addMathExpr
	| expression (ANDOP|LOGOROP) expression							#logExpr
	| expression (EQOP|NEQOP) expression							#equalExpr
	| expression (LESSOP|MOREOP|LESSEQOP|MOREEQOP) expression		#relExpr
	;
operands
	: INTLIT			#intLit
	| FLOATLIT			#floatLit
	| BOOLEANLIT		#boolLit
	| STRINGLIT			#stringLit
	| SELF				#selfLit
	| NULL				#nullLit
	| ID				#idLit
	;


// list of expressions
list_expr 
	: LB expr_list? RB
	;
expr_list: expression (COMMA expression)*;

// Lexical

// Separators
LP: '{';
RP: '}';
LSB: '[';
RSB: ']';
LB: '(';
RB: ')';
SEMICOLON : ';';
COLON: ':';
COMMA: ',';
DOT: '.';

WS : [ \t\r\n]+ -> skip ; // skip spaces, tabs, newlines

LINE_COMMENT : '#' ~[\r\n]* '\r'? '\n' -> skip;
BLOCK_COMMENT : '(*' (.)*? '*)' -> skip;

fragment ALPHA : [a-z]|[A-Z];
fragment DIGIT : [0-9];

// Literals
INTLIT : DIGIT+ ;
FLOATLIT : DIGIT+ '.'? DIGIT* EXPONENT? ;
fragment EXPONENT : ('e'|'E') ('+'|'-')? DIGIT+;
BOOLEANLIT : 'true'|'false' ;
STRINGLIT : '"' (ALPHA|DIGIT|ESCSEQ|SPECIAL)* '"';
fragment ESCSEQ : '\\' ('b'|'r'|'f'|'n'|'t'|'"'|'\\');
fragment SPECIAL : ~[\r\n\b\f\t\"\\a-zA-Z0-9];

// Operators
ADDOP : '+' ;
SUBOP : '-' ;
FDIVOP : '/' ;
MULOP : '*' ;
MODOP : '%' ;
EQOP : '==' ;
MOREOP : '>' ;
MOREEQOP : '>=' ;
IDIVOP : '\\' ;
NEQOP : '<>' ;
LESSOP : '<' ;
LESSEQOP : '<=' ;
LOGOROP : '||' ;
ANDOP : '&&' ;
NOTOP : '!' ;
CONOP : '^' ;
NEWOP : 'new' ;
ASSIGNOP : '='|':=' ;

// Keyword
BOOL: 'bool';
INTEGER: 'integer';
FLOAT: 'float';
STRING: 'string';
VOID: 'void' ;
BREAK : 'break' ;
CLASS : 'class' ;
CONTINUE : 'continue' ;
DO : 'do' ;
ELSE : 'else' ;
EXTENDS : 'extends' ;
IF : 'if' ;
THEN : 'then' ;
WHILE : 'while' ;
RETURN : 'return' ;
NULL : 'null' ;
SELF : 'self' ;
FINAL : 'final' ;
STATIC : 'static' ;

// Identifier
ID : ('_'|ALPHA)('_'|ALPHA|DIGIT)* ;             // match lower-case identifiers

// ERRORS
WRONG_STRINGLIT : '"' (ALPHA|DIGIT|ESCSEQ|SPECIAL)* ILLESCSEQ {if (true) throw new bkool.parser.IllegalEscape(getText());};
fragment ILLESCSEQ: '\\' ~('b'|'r'|'f'|'n'|'t'|'"'|'\\');
UNCLOSED_STRINGLIT : '"' (ALPHA|DIGIT|ESCSEQ|SPECIAL)* {if(true) throw new bkool.parser.UncloseString(getText());};
ERRORTOKEN: . {if(true) throw new bkool.parser.ErrorToken(getText());};
