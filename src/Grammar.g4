grammar Grammar;

lang: rule+ token+;
rule: RULE_NAME;
token: TOKEN_NAME;

RULE_NAME: [a-z][a-zA-Z]*;
TOKEN_NAME: [A-Z][_A-Z]*;
