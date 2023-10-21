.data
    .align 2

#size:       .word   20
string:     .space  20000       #Store user input
prompt1:         .asciiz "Enter n, followed by n lines of text:\n"
prompt2:       .asciiz "The values are:\n"
myArray:      .space  80


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

    # place in array at index
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
sub	$t0, $t0, 4
while:
	
    blt      $t0,0,exit        #if index == 0, exit
    lw      $t2,myArray($t0)      # get string at index

    # output string
    li      $v0,4
    move    $a0,$t2
    syscall

    addi    $t0,$t0,-4           # decrement index by 4 (Reversing)
    addi    $t1,$t1,1           # increment count
    j       while

# finished
exit:
    li      $v0,10
    syscall
