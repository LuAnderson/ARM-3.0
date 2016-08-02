.text

main:

	li $t0,144
	li $t1, 1 # resultado da raiz
	li $t2,1  # somatorio
	li $t3,1

	loop:
	
	beq $t2,$t0,exit 
        addi $t3,$t3,2
        add $t2,$t2,$t3
        

	addi $t1, $t1,1
	J loop
	
	
	
	exit:
	move $v0,$t1
                  