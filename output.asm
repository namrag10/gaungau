&LDA 3
STO 0
&LDA 5
ADD 0
STO 1
&LDA 3
STO 255
LDA 1
&SUB 255
BRC 11
BRA 49
LDA 0
&ADD 7
STO 2
&LDA 78
STO 0
&LDA 0
STO 255
LDA 2
&SUB 255
BRP 22
BRA 25
LDA 2
&SUB 1
STO 2
&LDA 0
STO 255
LDA 2
&SUB 255
BRP 31
BRA 40
LDA 2
&SUB 1
STO 2
&LDA 6
STO 0
BRA 40
&LDA 50
STO 3
BRA 36
&LDA 0
STO 255
LDA 2
&SUB 255
BRP 46
BRA 49
LDA 2
&ADD 1
STO 2
&LDA 7
STO 4
