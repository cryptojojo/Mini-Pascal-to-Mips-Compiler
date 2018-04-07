.data

dollars : .word 0
yen : .word 0
bitcoins : .word 0
__newline__: .asciiz "\n"

.text

main:

#Write Statement

#Expression statement

#Expression statement
lw   $s0,  MemAddressNULL

#Expression statement
li   $s1,   5
div   $s0,   $s1
mflo   $s0
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, __newline__
syscall

#Write Statement

#Expression statement
lw   $s0,  MemAddressNULL
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, __newline__
syscall

#Write Statement

#Expression statement
lw   $s0,  MemAddressNULL
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, __newline__
syscall

#Write Statement

#Expression statement
lw   $s0,  null
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, __newline__
syscall

