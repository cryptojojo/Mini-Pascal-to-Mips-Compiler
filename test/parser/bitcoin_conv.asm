.data

dollars : .word 0
input:  .asciiz  "Input: " 
newLine: .asciiz "\n"

.text

main:

# Read statement 
li  $v0,  4
la  $a0,  input 
syscall
li  $v0, 5
syscall
sw  $v0,  dollars

#Write Statement

#Expression statement

#Expression statement
lw   $s0,  dollars

#Expression statement
li   $s1,   1
add   $s0,   $s0,   $s1
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall


#Exit Program 
li  $v0, 10 

