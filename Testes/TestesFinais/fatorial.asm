
.text

main:         
      li $a0,5   
      jal fact      # chama a label fatc
     move $a0,$v0   
     
      J exit
     

fact:
      sub $sp,$sp,8   # Ajusta a pilha para 2 objetos
      sw $ra, 4($sp)   # Guarda endereço de retorno
      sw $a0, 0($sp)   # Guarda argumento n

      slti $t0,$a0,1   # testa se n < 1
      beq $t0,$zero,L1   # Se n >= 1, vai fazer outra chamada

      li $v0,1      
      add $sp,$sp,8   
      jr $ra      

L1:   sub $a0,$a0,1   
      jal fact     

       
      lw $a0, 0($sp)   
      lw $ra, 4($sp)   
      add $sp,$sp,8   

      mul $v0,$a0,$v0   # Calcula n * fact (n - 1)
      jr $ra            
exit:
