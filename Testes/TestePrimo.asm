 
.text 


main:   
	li $t0, 19
	li $t1, 0
	li $t2, 2
	li $t3, 3
	
	       loop: 
	       	beq $t1, $t2, tratamento1
	       	beq $t1, $t3, tratamento2	       		       	
	       	beq $t1, $t0, exit
	       	addi $t1, $t1, 1
	       	div  $t1, $t2
	        mfhi $t6                     
            	beqz $t6, loop
                div $t1, $t3
                mfhi $t6
                beqz $t6, loop
      		  move $a0,$t1
                  li $v0,1       # syscall 1 (Printa o resultado da potencia)
   
    syscall

      		J loop
      		
 tratamento1:
move $a0,$t1
li $v0,1   
syscall
 J loop
 
 tratamento2:
move $a0,$t1
li $v0,1   
syscall
 J loop
        
exit:         