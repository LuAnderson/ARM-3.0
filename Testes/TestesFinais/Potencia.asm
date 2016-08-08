.data



        .text

main:   
        
        li $t0,3 #numero base
        li $t1,4 # expoente    
  
 
                
      
        
          
        li $t2,0
        move $t3,$t0

        loop:
      addi $t2,$t2,1
    beq  $t2,$t1, exit
   
    mul $t0,$t0,$t3
       J loop
exit:

    
    
    move $a0,$t0
    
   
