.data

dollars : .word 0
yen : .word 0
bitcoins : .word 0
arr : .word 0
input:  .asciiz  "Input: " 
newLine: .asciiz "\n"

.text

main:

#Assignment Statement

#Expression statement
li   $s0,   10000
sw  $s0,   dollars

#Assignment Statement

#Expression statement

#Expression statement
lw   $s0,  dollars

#Expression statement
li   $s1,   107
mult   $s0,   $s1
mflo   $s0
sw  $s0,   yen

#Assignment Statement

#Expression statement

#Expression statement
lw   $s0,  dollars

#Expression statement
li   $s1,   8000
div   $s0,   $s1
mflo   $s0
sw  $s0,   bitcoins

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

#Write Statement

#Expression statement
lw   $s0,  bitcoins
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall

#Assignment Statement

#Expression statement
li   $s0,   1
sw  $s0,   arr

#Write Statement

#Expression statement
lw   $s0,  ary
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall

#Assignment Statement

#Expression statement
li   $s0,   2
sw  $s0,   arr

#Write Statement

#Expression statement
lw   $s0,  ary
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall

#Assignment Statement

#Expression statement
li   $s0,   3
sw  $s0,   arr


#Exit Program 
li  $v0, 10 

