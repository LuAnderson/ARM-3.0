#   Data
.data
prompt: .asciiz  "\n\nEnter up to 10 characters without semicolon: "  # Prompt asking for user input
newLine: .asciiz "\n"                               # Newline character
theString: .asciiz "           "                    # A ten character string initially filled with whitespace

#   Text
.text 


#   Procedure: ARM 3.0
#   Info:      Asks user for input, gets input, and then call 
#              procedures to manipulate the input and output.

main:

    #  Print Prompt

    la $a0, prompt   # Load address of prompt from memory into $a0
    li $v0, 4        # Load Opcode: 4 (print string) 
    syscall          # Init syscall

    #  Read User Input into address of theString

    la $a0,theString  # Load address of theString into syscall argument a0
    li $a1,11         # Load sizeOfInput+1 into syscall argument a1
    li $v0,8          # Load Opcode: 8 (Read String)
    syscall

    #  Define total num of chars
    
    li $s7,10           # s7 upper index

    #  Call procedures 

    jal uppercase  
    jal sort
    jal print
    j exit

# main END

# Info:      Loops through the ten elements of chars gathered from 
#              user input and if ascii is in range between 97  
#              and 122, it will subtract 32 and store back

uppercase:

    la $s0, theString    # Load base address to theString into $t0
    add $t6,$zero,$zero  # Set index i = 0 ($t6)



    lupper:
 
        #  Check for sentinal val and if true
        #  branch to done to jump back to ra
 
        beq $t6,$s7,done 

 
        #  Load Array[i]
 
        add $s2,$s0,$t6 #
        lb  $t1,0($s2)

        #  if char is within lowercase 
        #  range.

        sgt  $t2,$t1,96
        slti $t3,$t1,123
        and $t3,$t2,$t3

        #  else, don't store byte

        beq $t3,$zero,isUpper
        addi $t1,$t1,-32
        sb   $t1, 0($s2)

        isUpper:

        #  increment and Jump back 

        addi $t6,$t6,1
        j lupper

# uppercase END 

#   Info:      Bubble sorts whatever is contained within 
#              theString based on ascii values

sort:   
    #  Initialize incrementer for outer
    #  loop
    add $t0,$zero,$zero 

    #  Outer Loop

    loop:

        #  Check for sentinal val and if true
        #  branch to done
        beq $t0,$s7,done

        #  Initialize upper bound of inner
        #  loop ( 10 - i - 1 ) 

        sub $t7,$s7,$t0
        addi $t7,$t7,-1

        #  Initialize incrementer for inner
        #  loop

        add $t1,$zero,$zero
        
        #  Inner Loop

        jLoop:

            #  Check for sentinal val and if true
            #  branch to continue

            beq $t1,$t7,continue

            #  Load Array[i] and Array[i+1]

            add $t6,$s0,$t1
            lb  $s1,0($t6)
            lb  $s2,1($t6)

            #  If ascii(Array[i]) > ascii(Array[i+1])
            #  then swap and store
        
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

# sort END 

#
# Info:      Prints whatever is stored inside theString
             

print:

    # Print a new line

    la $a0,newLine
    li $v0,4
    syscall 

    #  Initialize incrementer for loop

    add $t6,$zero,$zero # Set index i = 0 $t6

    lprint:

        #  Check for sentinal val and if true
        #  branch to done

        beq $t6,$s7,done  


        #  Load Array[i] into t1 and print

        add $t1,$s0,$t6 
        lb $a0, 0($t1)  # Load argument
        li $v0, 11      # Load opcode
        syscall         # Call syscall

        #  increment and Jump back 

        addi $t6,$t6,1  
        j lprint

# print END  




#  Procedure: ARM 3.0
# Info: Jumps to $ra. Only one procedure is needed to jump back to ra


done:
    jr $ra
exit: