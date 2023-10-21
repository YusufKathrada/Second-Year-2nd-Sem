.data
 .align 2
 
 	text1:	.asciiz "Enter a sum:\n"
 	text2:	.asciiz "The value is:\n"
 	usrSum:		.space	1024
 	newline: .asciiz "\n"
.text
.globl main

main:
	
	#print text
	la $a0, text1				  
	li $v0, 4		
	syscall	
	
	#Read in user sum
	la $a0, usrSum
	li $a1, 1024
	li $v0, 8 				
	syscall

	la $t0, usrSum 
	
	#sum initialise
	li $t3, 0 
	
start:				
	lb $t1, 0($t0) 	
				 
	bne $t1, '+', sum
	#skip sign
	addu $t0, $t0, 1 		

sum:
	lb $t1, ($t0) 	
	addu $t0, $t0, 1 			

#use 10 for newline check
	beq $t1, 10, final

	blt $t1, '0', final		
	bgt $t1, '9', final 

	mult $t3, $t4
	sub $t1, $t1, '0' 
				
	add $t3, $t3, $t1 			


	b start				

final:

	#print text
	la $a0, text2				  
	li $v0, 4		
	syscall

	#print sum
	move $a0, $t3 				
	li $v0, 1
	syscall

	la $a0, newline 			
	li $v0, 4
	syscall

	b exit

#exit
exit: 	
	li $v0, 10 
	syscall 	
