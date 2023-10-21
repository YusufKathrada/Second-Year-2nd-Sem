## Moloko M. Mokubedi -- 24/10/21

.data ## Start of data declarations:
	newline: .asciiz "\n"
	string_space: .space 1024 		# reserve 1024 bytes for the string.
	prompt1:.asciiz	"Enter a sum:\n"

.text
main:
	la $a0, prompt1				#Load the typing prompt string into the $v0  
	li $v0, 4				#Set the system to print the propt
	syscall					#print the prompt
		
	la $a0, string_space 			# read the input string string_space:
	li $a1, 1024				# set the buffer to 1024 bytes
	li $v0, 8 				# load "string_space" code into $v0.
	syscall

	la $t0, string_space 			# Initialize S.
	li $t2, 0 				# Initialize sum = 0.

get_sign:
	li $t3, 1 				# assume the sign is positive.
	lb $t1, 0($t0) 				# grab the "sign"
	bne $t1, '-', positive 			# if not "-", do nothing.
	li $t3, -1 				# otherwise, set t3 = -1, and
	addu $t0, $t0, 1 			# skip over the sign.

positive:
	bne $t1, '+', sum_loop
	addu $t0, $t0, 1 			# skip over the sign.

sum_loop:
	lb $t1, ($t0) 				# load the byte at addr S into $t1,
	addu $t0, $t0, 1 			# and increment S,

## use 10 instead of 'n' due to SPIM bug!
	beq $t1, 10, end_sum_loop 		# if $t1 == \n, branch out of loop.

	blt $t1, '0', end_sum_loop 		# make sure 0 <= t1
	bgt $t1, '9', end_sum_loop 		# make sure 9 >= t1

	mult $t2, $t4
	sub $t1, $t1, '0' 			# t1 -= ’0’.
	mul $t1, $t1, $t3 			# set the sign properly.
	add $t2, $t2, $t1 			# t2 += t1.


	b get_sign				# and repeat the loop.

end_sum_loop:
	move $a0, $t2 				# print out the answer (t2).
	li $v0, 1
	syscall

	la $a0, newline 			# and then print out a newline.
	li $v0, 4
	syscall

	b exit

exit: 						# exit the program:
	li $v0, 10 				# load "exit" into $v0.
	syscall 				# make the system call.

 ## end of atoi-4.asm
