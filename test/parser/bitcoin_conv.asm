.data

dollars : .word 0
yen : .word 0
bitcoins : .word 0
newLine: .asciiz "\n"

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

# If statement

#Expression statement
lw   $s0,  yen

#Expression statement
li   $s1,   0
ble   $s0,   $s1,   else0

#Write Statement

#Expression statement
li   $s0,   10
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall
j  endIf0
else0:

#Write Statement

#Expression statement
li   $s1,   20
addi   $v0,   $zero,   1
add   $a0,   $s1,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall
endIf0:

#Write Statement

#Expression statement
lw   $s0,  dollars
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall

#Write Statement

#Expression statement
lw   $s0,  yen
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall


#Exit Program 
li  $v0, 10 

