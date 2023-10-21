.data
    .align 2

string:     .space  5       #Store user input
prompt1:         .asciiz "Enter n and formulae:\n"
prompt2:       .asciiz "The values are:\n"
newline:	.asciiz "\n"

.align 2
myArray:      .space  16


.text
    .globl main
main:

    # prompt user
    li      $v0,4
    la      $a0,prompt1
    syscall

    # get number of items in $v0
    li      $v0,5
    syscall
    addi    $s0,$v0,0

	#Array index
    add     $t0,$zero,$zero
    	#Counter
    addi    $t1,$zero,1
    
    	#Load address fro storing user input
    la      $s2,string          

input:
    bgt     $t1,$s0,next          # if index == number of lines, break

    # get string
    move    $a0,$s2
    li      $a1,20
    li      $v0,8
    syscall
    
    #NEW
	lb $t2,0($s2)	#get first char of element in array, looking for "="
	bne $t2, '=', normal	#if not "=" go to normal function
	lb $s4, 1($s2)	#skip over first char "="


	#Convert string to int
	addu $s4,$s4,-48
	
	#multiply by 4, to be used as index
	mul $s4, $s4,4
	
	
	#get element at index 
	lw $t5, myArray($s4)
	
	addi $t4, $t5, 0
	
	#DELETE
	add $t6,$t6,4
	sw $s3, myArray($t6)
	
	#loop updates
	
	
	#increment array by 4 bytes
    	addi    $t0,$t0,4           
    	#Increment counter
    	addi    $t1,$t1,1           
    
    	addi    $s2,$s2,20

    	j       input

	#NEW
	
normal:
    # place in array at index
    sw      $a0,myArray($t0)

	#increment array by 4 bytes
    addi    $t0,$t0,4
    add    $s1,$s1,$t0        
    	#Increment counter
    addi    $t1,$t1,1           
    
    addi    $s2,$s2,20

    j       input


next:
    #addi    $t1,$zero,1

    # prompt2
    la      $a0,prompt2
    li      $v0,4
    syscall
    
    #subtract 4 from counter, previously was giving index out of range error --  this fixed it

	addi $t7, $t7, 0
	sub	$t0, $t0, 4
	sub 	$s1,$s1,4
	
	j while
while:
	
	blt $t0, 0, exit
	
	lw $t3, myArray($t7)
	
	#Displaying value
	li $v0, 4
	addi $a0, $t3, 0
	syscall
	
	addi $t0, $t0, -4
	addi $s1,$s1,-4
	addi $t7, $t7, 4
	
	j while
	
#normal:	
	#li $v0, 4
	#move $a0, $t3
	#syscall
	
	#addi $t7, $t7, 4
	#addi $t0, $t0, -4
	
	#j while

exit:
    li      $v0,10
    syscall
	

    

