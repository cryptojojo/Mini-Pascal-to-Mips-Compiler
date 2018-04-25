.data
fee : .word 0
iter : .word 0
arr : .word  0,  0,  0,  0,  0, 0
input:  .asciiz  "Input: " 
newLine: .asciiz "\n"
.text
main:
li   $s0,   1
sw  $s0,   fee
li   $s0,   1
sw  $s0,   iter
whileDoNum0:
lw   $s0,  iter
li   $s1,   5
bge   $s0,   $s1,   endWhile0
lw   $s1,  iter
li   $s2,   5
mult   $s1,   $s2
mflo   $s1
lw   $s2,  iter
li   $t0,   4
mult   $t0,   $s2
mflo   $s2
la   $s3,   arr
add   $s3,   $s2,   $s3
sw   $s1,   0($s3)
lw   $s1,  iter
li   $s2,   1
add   $s1,   $s1,   $s2
sw  $s1,   iter
j whileDoNum0
endWhile0:
li   $s0,   1
sw  $s0,   iter
whileDoNum1:
lw   $s0,  iter
li   $s1,   5
bge   $s0,   $s1,   endWhile1
lw   $s2,  iter
li   $t0,   4
mult   $t0,   $s2
mflo   $s2
la   $s3,   arr
add   $s3,   $s2,   $s3
lw   $s1,   0($s3) 
addi   $v0,   $zero,   1
add   $a0,   $s1,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall
lw   $s1,  iter
li   $s2,   1
add   $s1,   $s1,   $s2
sw  $s1,   iter
j whileDoNum1
endWhile1:
li  $v0, 10 

