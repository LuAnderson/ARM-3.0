.text 


	main: 

	li $t0, 13 #até onde vão ser gerados os numeros primos
	li $t1, 1 # 
	li $t2, 0 #conta quantas vezes o resto da divisão é 0
	li $t3, 0
	
	addi $t0, $t0, 1
	
	loop: 
	
	move $t2, $zero
	move $t3, $zero
	addi $t1, $t1, 1
	addi $t2, $t2, 1
	

	beq $t1, $t0, exit
	J loop2


	loop2: 
	
	addi $t3, $t3, 1
	beq $t3, $t1, verificar
	div $t1, $t3
	mfhi $t6
	beqz $t6, incrementar
	
	J loop2








	verificar: 

	beq $t2, 2, printar
	J loop





 	printar:
  
      	move $a0,$t1
      	# li $v0,1 Impressão para teste
      	# syscall
      	J loop
      	
      	
      	incrementar:
      	
      	addi $t2, $t2, 1
      	J loop2








exit:
