
.text


  main:
  
    li $t0, 7
    li $t1, 1
    li $t2, 1 #contador
    li $t3, 0
    loop:
    
    	addi $t1,$t1,1
    	addi $t2,$t2,1
    	beq $t1,$t0,exit
    	 J loop2
    
    
    loop2:
      addi $t3,$t3,1
     beq $t3,$t1,verifica
   
    div $t1,$t3
    mfhi $t6
    beqz $t6,incrementa
   
    J loop2
    
    
    
    
    
    
    incrementa:
    addi $t2,$t2,1
    J loop2
    
    verifica:
    beq $t2,2,printa
    
    J loop
    
    printa:

    move $a0,$t1
    li $v0,1
    syscall
    move $t2, $zero
    J loop
    
    exit: