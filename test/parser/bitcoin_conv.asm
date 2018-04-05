.data

dollars : .word
yen : .word
bitcoins : .word

.text

main:

#Syscall
addi	$v0,	$zero,	1
add	$a0,	0,	$zero
syscall
li	$v0,	4
la	$a0,__newline__
syscall

#Syscall
addi	$v0,	$zero,	1
add	$a0,	0,	$zero
syscall
li	$v0,	4
la	$a0,__newline__
syscall

#Syscall
addi	$v0,	$zero,	1
add	$a0,	0,	$zero
syscall
li	$v0,	4
la	$a0,__newline__
syscall

#Syscall
addi	$v0,	$zero,	1
add	$a0,	0,	$zero
syscall
li	$v0,	4
la	$a0,__newline__
syscall

