.text
main:
	#Asking user to provide n followed by n lines of text
	li $v0, 4
	la $a0, prompt1
	syscall	#Executes command
	
	#numLines stored in $v0
	addi $v0, $zero, 5	#Read in integer and store in $v0
	syscall
	addi $t0, $v0, 0
	
	#put input into number of elements in array
	addi $t7, $t0, 0
	li $t4, 4
	
	#Index for array
	li $t6, 0
	
	la $s3, text	#Load space for user input
	
	#Read in words
	j loop
	
	loop:
		#Check that numLines == 0
		beq $t7, 0, next
		
		move $a0, $s3
		li $a1,20
		#user input
		li $v0, 8	#take in string
		syscall
		
		#store in array
		sw $v0, myArray($t6)
		
		#Increment index by 4
		addi $t6, $t6, 4
		
		addi $t7, $t7, -1
		
		#run loop again
		j loop
		
	next:
		#print second prompt
		li $v0, 4
		la $a0, prompt2
		syscall	#Executes command
		
		#j printReverse
	
	#printReverse:
	
		#beq $t6,0,exit
		
		add $t0, $zero, $t6
		
		lw $t2, myArray($t0)
		
		li $v0,4
    		move $a0, $t2
    		syscall
		
		
		#Retriving first value in array
		#lw $t5, myArray($zero)
	
		#Displaying value
		#li $v0, 1
		#addi $a0, $t5, 0
		#syscall
	
	#Displaying numLines
	#add $a0, $zero, $v0
	#addi $v0, $zero, 1
	#syscall
.data
	myArray:	.space	1024
	text:		.space	1024
	prompt1:	.asciiz		"Enter n, followed by n lines of text:\n"
	prompt2:	.asciiz		"The values are:"	