 .data
msg1:   .asciiz "Entre com o numero :"
msg2:   .asciiz " Entre com o expoente "
msg3:   .asciiz " O Resultado da potencia e :  "


        .text

main:   li $v0, 4       # syscall 4 (printa a solicitação do  numero)
        la $a0, msg1     # argument: primeira mensagem
        syscall         # print the string
        
        li $v0,5
        syscall
        
        move $t0,$v0
      
        
        li $v0, 4       # syscall 4 (printa a solicitação do expoente)
        la $a0, msg2     # argument: segunda mensagem
        syscall         # print the string
        
        li $v0,5
        syscall
        
        move $t1,$v0
            
              
                
      
        
          
        li $t2,0
        move $t3,$t0

        loop:
      addi $t2,$t2,1
    beq  $t2,$t1, exit
   
    mul $t0,$t0,$t3
       J loop
exit:

    li $v0,4    # syscall 4 (Printa a string do resultado)
    la $a0,msg3
    syscall
   
     move $a0,$t0
     li $v0,1       # syscall 1 (Printa o resultado da potencia)
   
    syscall
    
        
        
        