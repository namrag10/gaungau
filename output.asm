&LDA 0
STO 0
LDA 0
&ADD 8
&SUB 2
STO 1
LDA 0
STO 255
LDA 1
SUB 255
BRP 12
BRA 15
LDA 1
OUT
BRA 17
LDA 0
OUT
