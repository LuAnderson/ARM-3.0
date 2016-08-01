 


 	

	.text

		main:                   ;labelnova
    		li $t0,0
   		    li $t1,2
    		li $t3,0
				loop: ;looping
    				bgt $t0,4,exit
    				addi $t0,$t0,1 
    				j loop
   					 mul $t1,$t1,$t1
    				
exit: 
