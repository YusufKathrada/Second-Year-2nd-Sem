.data 
	entermessage: .asciiz "Enter a series of 5 formulae:\n"
	valuemessage: .asciiz "The values are:\n"
	newline: .asciiz "\n"
	equalSign: .byte '='
	
	String1: .space 5
	String2: .space 5
	String3: .space 5
	String4: .space 5
	String5: .space 5
	
	String1byte: .byte '1'
	String2byte: .byte '2'
	String3byte: .byte '3'
	String4byte: .byte '4'
	String5byte: .byte '5'

.text
	main:
		#Display message
		li $v0, 4
		la $a0, entermessage
		syscall
		
		# getting users input1
		li $v0, 8
		la $a0, String1
		li $a1, 5
		syscall
		# getting users input2
		li $v0, 8
		la $a0, String2
		li $a1, 5
		syscall
		# getting users input3
		li $v0, 8
		la $a0, String3
		li $a1, 5
		syscall
		# getting users input4
		li $v0, 8
		la $a0, String4
		li $a1, 5
		syscall
		# getting users input5
		li $v0, 8
		la $a0, String5
		li $a1, 5
		syscall
		
		#Display message
		li $v0, 4
		la $a0, valuemessage
		syscall
	cell1:	
		#Display the user input1
	        la $a0, String1
		lb $t1, ($a0)
		lb $t2, equalSign
		beq $t1, $t2, cellpos1
		
		la $a0, ($a0)
		li $v0, 4
		syscall
		
		j cell2
		
	cellpos1:
		la $a0, String1
		lb $t3, 4($a0)
		lb $t4, String1byte
		bne, $t3, $t4,cellpos2
		la $a0, 0($a0)
		li $v0, 11
		syscall
	cellpos2:
		la $a0, String1
		lb $t3, 4($a0)
		lb $t4, String2byte
		bne, $t3, $t4,cellpos3
		la $a0, String2
		li $v0, 11
		syscall
	cellpos3:
		la $a0, String1
		lb $t3, 4($a0)
		lb $t4, String3byte
		bne, $t3, $t4,cellpos4
		la $a0, String3
		li $v0, 11
		syscall
	cellpos4:
		la $a0, String1
		lb $t3, 4($a0)
		lb $t4, String4byte
		bne, $t3, $t4,cellpos5
		la $a0, String4
		li $v0, 11
		syscall
	cellpos5:
		la $a0, String1
		lb $t3, 4($a0)
		lb $t4, String5byte
		bne, $t3, $t4,exit
		la $a0, String4
		li $v0, 11
		syscall
	################After#################################################
	################After#################################################
	################After#################################################
	################After################################################
	cell2:
		#Display the user input2
		la $a0, String2
		lb $t1, 0($a0)
		lb $t2, equalSign
		beq $t1, $t2, exit
		la $a0, String2
		li $v0, 4
		syscall
	cell3:
		#Display the user input3
		la $a0, String3
		lb $t1, 0($a0)
		lb $t2, equalSign
		beq $t1, $t2, exit
		la $a0, String3
		li $v0, 4
		syscall
		
	cell4:
		#Display the user input4
		la $a0, String4
		lb $t1, 0($a0)
		lb $t2, equalSign
		beq $t1, $t2, exit
		la $a0, String4
		li $v0, 4
		syscall
		
	cell5:
		#Display the user input5
		la $a0, String5
		lb $t1, 0($a0)
		lb $t2, equalSign
		beq $t1, $t2, exit
		la $a0, String5
		li $v0, 4
		syscall

		# The end of the main function\
	exit:		
		li $v0, 10
		syscall
