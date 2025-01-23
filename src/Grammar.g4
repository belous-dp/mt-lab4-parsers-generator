grammar Grammar;

lang: rule_+ token+;
rule_: RULE_NAME inhAttrs? synthAttrs? ':' branch ('|' branch)*; // nonterminals
inhAttrs: ATTRS;
synthAttrs: '->' ATTRS;
branch: 'ε' #epsBranch | symb+ SYNTH_CODE? #nonEpsBranch;
symb: RULE_NAME inhAttrs? #symbr | TOKEN_NAME #symbt;
token: TOKEN_NAME ':' '"' tokenValue '"'; // terminals
tokenValue: (ESCQ | ESQS | ATTRS | SYNTH_CODE | RULE_NAME | TOKEN_NAME | RESTQ)+;

ESCQ: '\\"';
ESQS: '\\ ';
ATTRS: '[' .*? ']';
SYNTH_CODE: '{' .*? '}';
RULE_NAME: [a-z][_a-z]*;
TOKEN_NAME: [A-Z][_A-Z]*;
WHITESPACE: [ \t\n\r] -> skip;
RESTQ: ~["];
