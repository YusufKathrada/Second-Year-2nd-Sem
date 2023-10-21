.data
    .align 2
    string:     .space  20000       #Store user input
    prompt1:	.asciiz "Enter n and formulae:\n"
    prompt2:	.asciiz "The values are:\n"
    newline:	.asciiz "\n"
    myArray:	.space	1024

.text
    .globl main
main:

# prompt1
    li      $v0,4
    la      $a0,prompt1
    syscall
    
    # get number of items in $v0
    li      $v0,5
    syscall
    add    $t2,$v0,0
    
    add $t3,$zero,$t2
    
    #Array index
    add     $t0,$zero,$zero
    	#Counter
    li    $t6,0
    
    la      $s2,string
    
    input:
    #bgt     $t1,$s0,next          # if index == number of lines, break
    
    #li      $v0,5
    #syscall
    #move    $a0,$s2
    #li      $a1,20
    #li      $v0,8
    #syscall
    
    #Break if index equals 0, going to decrement the counter
    beq $t3,0,next
    
    #Take in user input
    li $v0, 5
    syscall
    
    sw      $v0,myArray($t6)
    
    #increment array by 4 bytes
    addi    $t6,$t6,4           
    	#Decrement counter
    addi    $t3,$t3,-1
    
    j       input
 
next:   
    # prompt2
    la      $a0,prompt2
    li      $v0,4
    syscall
    
    move $t2, $t6
    
    j while
    
li    $t7,0
#add	$t0, $t0, 4   
    while:
	
    beq      $t2,$zero,exit        #if index == 0, exit
    lw      $t4,myArray($t7)      # get string at index

    # output string
    li      $v0,1
    move    $a0,$t4
    syscall

    #addi    $t0,$t0,-4           # decrement index by 4 (Reversing)
    addi    $t2,$t2,-1           # decrement count
    addi    $t7,$t7,4
    
    # print new line
    la      $a0,newline
    li      $v0,4
    syscall
    
    j       while

# finished
exit:
    li      $v0,10
    syscall
    

    
    