## int fib (int n){		#   n através do $a0
##  if (n<=1)
##	return n;
##  else
##      return fib (n-1)+fib(n-2);
##  }

.text
fib:
	bgt 	$a0, 1, fib_recurse
	move	$v0, $a0
	jr	$ra
fib_recurse:
	sub     $sp,$sp, 12
	sw	$ra,0($sp)		# salva   $ra
	
	sw	$a0, 4($sp)		#salva n
	add     $a0, $a0, -1		# n-1
	jal     fib
	lw      $a0,4($sp)		# guarda n
	sw	$v0, 8($sp) 	  	# salva retorno de valor do fib(n-1)
	
	add	$a0, $a0, -2         # n-2
	jal	fib

	lw	$t0, 8($sp)	     #  guarda valoor  fib(n-1)
	add	$v0, $t0, $v0	     

	lw	$ra, 0($sp)	     # guarda $ra
	add	$sp, $sp, 12	     
	jr	$ra  											
