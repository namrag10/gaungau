; === $Yeet = 4 ===
&LDA 4
STO 0x00

; === $test = 2 ===
&LDA 2
STO 0x01

; === if($test < 3) ===
&LDA 3          ; To compare against
SUB 0x01        ; Subtracts the vale of $tst from 3

					; If the carry flag is true, then value is greater
BRC [Skip Block]   	; The result (3 - 2 = 1) is positive
BRZ [Skip Block]	; If its 0, then var = 3, so is not less than

; === inside if success block ===
	; === $num = 5 ===
	&LDA 5
	STO 0x02

	; === while($num > 0) === 
	LDA 0x02
	&LDA 0
	BRC [<skip after BRP>]
	BRP [<Skip out of while>]

	; === inside while loop ===
		; === $num = $num - 1;
		LDA 0x02
		&SUB 1
		STO 0x02

		; === NOP ==
		// NO OPERATION

		BRA [TOP OF WHILE BLOCK]






; > : less than 0
; < : Greater than 0
; if(2 > 3)

; 2 < 3
; 3 - 2 = 1 : 