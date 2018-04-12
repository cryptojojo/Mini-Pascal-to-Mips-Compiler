.data

dollars : .word 0
yen : .word 0
bitcoins : .word 0
__newline__: .asciiz "\n"

.text

main:

#Assignment Statement

#Expression statement
li   $s0,   20000
sw  $s0,   dollars

#Assignment Statement

#Expression statement
li   $s0,   1
sw  $s0,   yen

# while-do loop
whileDoNum0:

#Expression statement

#Expression statement
lw   $s0,  yen

#Expression statement
li   $s1,   600
bge   $s0,   $s1,   endWhile0

#Write Statement

#Expression statement
lw   $s1,  yen
addi   $v0,   $zero,   1
add   $a0,   $s1,   $zero
syscall
li   $v0,   4
la   $a0, __newline__
syscall

#Assignment Statement

#Expression statement

#Expression statement
lw   $s1,  yen

#Expression statement
li   $s2,   100
add   $s1,   $s1,   $s2
sw  $s1,   yen
j whileDoNum0
endWhile0:

#Write Statement

#Expression statement
lw   $s0,  dollars
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, __newline__
syscall

#Write Statement

#Expression statement
lw   $s0,  yen
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, __newline__
syscall


#Exit Program 
li  $v0, 10 

