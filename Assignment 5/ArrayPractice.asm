.data
	myArray:	.space		1024
.text
	addi $s0, $zero, 4
	addi $s1, $zero, 10
	addi $s2, $zero, 12

	#Index = $t0
	addi $t0, $zero, 0
	
	#Setting each element in array
	sw $s0, myArray($t0)
		addi $t0, $t0, 4	#Updating index by 4
	sw $s1, myArray($t0)
		addi $t0, $t0, 4
	sw $s2, myArray($t0)
	
	#Retriving first value in array
	lw $t6, myArray($zero)
	
	#Displaying value
	li $v0, 1
	addi $a0, $t6, 0
	syscall
	
	