&LDA 10
STO 0
&LDA 5
STO 1
&LDA 5
STO 255
LDA 0
SUB 255
BRC 10
BRA 13
LDA 1
OUT
BRA 16
LDA 0
&SUB 1
STO 0