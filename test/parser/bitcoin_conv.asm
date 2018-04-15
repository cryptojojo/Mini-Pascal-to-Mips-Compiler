.data

fee : .word 0
newLine: .asciiz "\n"

.text

main:

#Assignment Statement

#Expression statement
li   $s0,   1
sw  $s0,   fee

# while-do loop
whileDoNum0:

#Expression statement

#Expression statement
lw   $s0,  fee

#Expression statement
li   $s1,   50
bge   $s0,   $s1,   endWhile0

#Write Statement

#Expression statement
lw   $s1,  fee
addi   $v0,   $zero,   1
add   $a0,   $s1,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall

#Assignment Statement

#Expression statement

#Expression statement
lw   $s1,  fee

#Expression statement
li   $s2,   1
add   $s1,   $s1,   $s2
sw  $s1,   fee
j whileDoNum0
endWhile0:


#Exit Program 
li  $v0, 10 

