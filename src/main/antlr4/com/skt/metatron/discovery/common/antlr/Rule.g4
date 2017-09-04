grammar Rule ;

ruleset : RULE_NAME args*
        ;

args : ARG_NAME ':' expr
     ;

expr : ('-'|'!') expr                                 # unaryOpExpr
     | <assoc=right> expr '^' expr                    # powOpExpr
     | expr ('*'|'/'|'%') expr                        # mulDivModuloExpr
     | expr ('+'|'-') expr                            # addSubExpr
     | expr ('<'|'<='|'>'|'>='|'=='|'!=' | '=') expr  # logicalOpExpr
     | expr ('&&'|'||') expr                          # logicalAndOrExpr
     | expr ('&'|'|') expr                            # logicalAndOrExpr2
     | expr '=' expr                                  # assignExpr
     | '(' expr ')'                                   # nestedExpr
     | fn                                             # functionExpr
     | fn (',' fn)+                                   # functionArrayExpr
     | IDENTIFIER                                     # identifierExpr
     | DOUBLE                                         # doubleExpr
     | LONG                                           # longExpr
     | STRING                                         # stringExpr
     | REGEX                                          # regularExpr
     | IDENTIFIER (',' IDENTIFIER)+                   # identifierArrayExpr
     | STRING (',' STRING)+                           # stringArrayExpr
     | LONG (',' LONG)+                               # longArrayExpr
     | DOUBLE (',' DOUBLE)+                           # doubleArrayExpr
     ;
fn : IDENTIFIER '(' fnArgs? ')'
   ;
fnArgs : expr (',' expr)*                             # functionArgs
       ;

WS : [ \t\r\n]+ -> skip ;
RULE_NAME : ('drop' | 'header' | 'settype' | 'rename' | 'keep' | 'set' | 'derive' | 'replace' | 'countpattern' | 'split' | 'delete' | 'pivot' | 'unpivot' | 'extract' | 'flatten' | 'merge' | 'nest' | 'unnest' | 'join' | 'aggregate' | 'splitrows' | 'move' | 'sort' | 'union' | 'window');
ARG_NAME : ('col' | 'row' | 'type' | 'rownum' | 'to' | 'value' | 'as' | 'on' | 'after' | 'before' | 'global' | 'with' | 'ignoreCase' | 'limit' | 'quote' | 'group' | 'groupEvery' | 'into' | 'markLineage' | 'pluck' | 'leftSelectCol' | 'rightSelectCol' | 'condition' | 'joinType' | 'idx' | 'dataset2' | 'order' | 'masterCol' | 'slaveCol' | 'totalCol' | 'partition' | 'rowsBetween');
IDENTIFIER : [_$a-zA-Z\uAC00-\uD7AF][._$a-zA-Z0-9\[\]\uAC00-\uD7AF]* [~]* [_$a-zA-Z\uAC00-\uD7AF]*[._$a-zA-Z0-9\[\]\uAC00-\uD7AF]* | '"' ~["]+ '"';
LONG : [0-9]+ ;
DOUBLE : [0-9]+ '.' [0-9]* ;
TRUE : 'true';
FALSE : 'false';
STRING : '\'' (ESC | ~ [\'\\])* '\'';
REGEX : ':' [ ]* '/' (ESC | ~ [\'/])* '/';
fragment ESC : '\\' ([\'\\/bfnrt] | UNICODE) ;
fragment UNICODE : 'u' HEX HEX HEX HEX ;
fragment HEX : [0-9a-fA-F] ;

MINUS : '-' ;
NOT : '!' ;
POW : '^' ;
MUL : '*' ;
DIV : '/' ;
MODULO : '%' ;
PLUS : '+' ;
LT : '<' ;
LEQ : '<=' ;
GT : '>' ;
GEQ : '>=' ;
EQ : '==' ;
NEQ : '!=' ;
AND : '&&' ;
OR : '||' ;
ASSIGN : '=' ;
