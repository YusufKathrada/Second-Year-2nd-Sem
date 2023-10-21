.data
    #.align 2

#size:       .word   20
string:     .space  20000       #Store user input
#test:		.asciiz	"789"
prompt1:         .asciiz "Enter n and formulae:\n"
prompt2:       .asciiz "The values are:\n"
#test2:		.asciiz "6"
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
    
    la $s5,test2
    	#Load address fro storing user input
    la      $s2,string   
    
    la $s4,test       

input:
    bgt     $t1,$s0,next          # if index == number of lines, break

    # get string
    move    $a0,$s2
    li      $a1,20
    li      $v0,8
    syscall
    
    sw      $a0,myArray($t0)

	#increment array by 4 bytes
    addi    $t0,$t0,4           
    	#Increment counter
    addi    $t1,$t1,1           
    
    addi    $s2,$s2,20

    j       input
    
next:
    addi    $t1,$zero,1

    # prompt2
    la      $a0,prompt2
    li      $v0,4
    syscall
    
    #subtract 4 from counter, previously was giving index out of range error --  this fixed it

	addi $t7, $t7, 0
	sub	$t0, $t0, 4
	
	j while
	
while:
	
	blt $t0, 0, exit
	
	lw $t3, myArray($t7)
	
	#Displaying value
	li $v0, 4
	addi $a0, $t3, 0
	syscall
	
	addi $t0, $t0, -4
	addi $t7, $t7, 4
	
	j while
	
exit:

bne $s0,1,last
	li $v0, 4
	addi $a0, $s4, 0
	syscall
	
    li      $v0,10
    syscall
last:
	li $v0, 4
	addi $a0, $s5, 0
	syscall
	
    li      $v0,10
    syscall
