grammar Grammar;

lang: includes? rule_+ token+;
includes: INCLUDES;
rule_: RULE_NAME inhAttrs? synthAttrs? ':' branch ('|' branch)*; // nonterminals
inhAttrs: ATTRS;
synthAttrs: '->' ATTRS;
branch: ('ε' | symb+) SYNTH_CODE?;
symb: RULE_NAME inhAttrs? #symbr | TOKEN_NAME #symbt;
token: TOKEN_NAME ':' regex? '"' tokenValue '"'; // terminals
regex: 'r';
tokenValue: (ESCQ | ESQS | ':' | '|' | '->' | 'ε' | 'r' | ATTRS | SYNTH_CODE | RULE_NAME | TOKEN_NAME | INCLUDES | RESTQ)+;

ESCQ: '\\"';
ESQS: '\\ ';
ATTRS: '[' .*? ']';
SYNTH_CODE: '{' .*? '}';
RULE_NAME: [a-z][_a-z0-9]*;
TOKEN_NAME: [A-Z][_A-Z0-9]*;
WHITESPACE: [ \t\n\r] -> skip;
INCLUDES: '###' .*? '###';
RESTQ: ~["];
