.data
.align 2
string:     	.space  20000 
	prompt1:	.asciiz "Enter n and formulae:\n"
    	prompt2:	.asciiz "The values are:\n"
    	newline:	.asciiz "\n"
    	myArray:	.space	10000
    	
.text
.globl main

#$t0 = number of integers to be entered
#$t6= index of array at insertion
#$
#$


main:

# prompt1
    li      $v0,4
    la      $a0,prompt1
    syscall
    
    # get number of items in $v0
    li      $v0,5
    syscall
    addi    $s0,$v0,0
    
    addi $t7,$t0,0
    li $t4,4
    
    li $t6, 1
    
    la      $s2,string    
read:

	#break $t7 == 0, then go next
	bgt $t6, $s0, next

	#Take in user integer
	#li $v0, 5
	#syscall

	# get string
    	move    $a0,$s2
    	li      $a1,20
    	li      $v0,8
    	syscall
    	
	sw $a0, myArray($t6)

	addi $t6, $t6, 4	#increment index of array by 4 bytes
	addi $t7,$t7, -1	#decrement counter by 1

	j read

next:
	
	move $t0, $t6
	
	# prompt2
    	la      $a0,prompt2
    	li      $v0,4
    	syscall
    	
	j while

while:
	
	li $v0, 4
	la $a0, newline
	syscall
	
	beq $t6, $zero, exit
	
	lw $t1, myArray($t3)
	
	li $v0, 1
	move $a0, $t1
	syscall
	
	addi $t3, $t3, 4
	addi $t6, $t6, -4
	
	j while

exit:
    li      $v0,10
    syscall
	

    
