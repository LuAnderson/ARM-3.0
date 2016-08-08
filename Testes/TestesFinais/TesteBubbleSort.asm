#   Data
.data
prompt: .asciiz  "\n\nEntre com 10 numeros (sem espaçamento): " 
newLine: .asciiz "\n"                               # Newline 
theString: .asciiz "           "        

#   Text
.text 

main:

    #  Print Prompt

    la $a0, prompt   # Carrega o endereço da memoria em $a0
    li $v0, 4       
    syscall          # Init syscall

    la $a0,theString  
    li $a1,11         
    li $v0,8         
    syscall

    li $s7,10           

    jal uppercase  
    jal sort
    jal print
    j exit

uppercase:

    la $s0, theString    
    add $t6,$zero,$zero  # Set index i = 0 ($t6)



    lupper:

        beq $t6,$s7,done 

 
        #  Load Array[i]
 
        add $s2,$s0,$t6 #
        lb  $t1,0($s2)

        sgt  $t2,$t1,96
        slti $t3,$t1,123
        and $t3,$t2,$t3

        beq $t3,$zero,isUpper
        addi $t1,$t1,-32
        sb   $t1, 0($s2)

        isUpper:

        addi $t6,$t6,1
        j lupper

sort:   
    #  loop
    add $t0,$zero,$zero 

    loop:

        beq $t0,$s7,done


        #  loop ( 10 - i - 1 ) 

        sub $t7,$s7,$t0
        addi $t7,$t7,-1

        add $t1,$zero,$zero

        jLoop:


            beq $t1,$t7,continue

            #  Load Array[i] and Array[i+1]

            add $t6,$s0,$t1
            lb  $s1,0($t6)
            lb  $s2,1($t6)

            #  If ascii(Array[i]) > ascii(Array[i+1])
       
            sgt $t2, $s1,$s2
        
            #  Else,  don't swap and store
        
            beq $t2, $zero, good
            sb  $s2,0($t6)
            sb  $s1,1($t6)

            good:
        
            #  increment and Jump back 
        
            addi $t1,$t1,1
            j jLoop

        continue:
        
        #  increment and Jump back 
        
        addi $t0,$t0,1
        j loop
           

print:

    la $a0,newLine
    li $v0,4
    syscall 

    add $t6,$zero,$zero # Set index i = 0 $t6

    lprint:

        beq $t6,$s7,done  

        add $t1,$s0,$t6 
        lb $a0, 0($t1)  
        li $v0, 11      # CArrega opcode
        syscall         # Call syscall

        addi $t6,$t6,1  
        j lprint

done:
    jr $ra
exit:
