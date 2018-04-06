.data

dollars : .word 0
yen : .word 0
bitcoins : .word 0
__newline__: .asciiz "\n"

.text

main:

#Syscall
addi	$v0,	$zero,	1
add	$a0,	$s0,	$zero
syscall
li	$v0,	4
la	$a0, __newline__
syscall

#Syscall
addi	$v0,	$zero,	1
add	$a0,	$s0,	$zero
syscall
li	$v0,	4
la	$a0, __newline__
syscall

#Syscall
addi	$v0,	$zero,	1
add	$a0,	$s0,	$zero
syscall
li	$v0,	4
la	$a0, __newline__
syscall

#Syscall
addi	$v0,	$zero,	1
add	$a0,	$s0,	$zero
syscall
li	$v0,	4
la	$a0, __newline__
syscall

