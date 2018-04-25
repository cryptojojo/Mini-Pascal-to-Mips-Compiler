.data

dollars : .word 0
yen : .word 0
bitcoins : .word 0
arr : .word  0,  0,  0,  0,  0, 0
dolla : .word 0
arr2 : .word  0,  0, 0
input:  .asciiz  "Input: " 
newLine: .asciiz "\n"

.text

main:

#Assignment Statement (array)

#Expression statement
li   $s0,   1

#Expression statement
li   $s0,   0
li   $t0   4
mult   $t0,   $s0
mflo   $s0
la   $s1,   arr
add   $s1,   $s0,   $s1
sw   $s0,   0($s1)

#Assignment Statement (array)

#Expression statement
li   $s0,   2

#Expression statement
li   $s0,   1
li   $t0   4
mult   $t0,   $s0
mflo   $s0
la   $s1,   arr
add   $s1,   $s0,   $s1
sw   $s0,   0($s1)

#Assignment Statement (array)

#Expression statement
li   $s0,   3

#Expression statement
li   $s0,   2
li   $t0   4
mult   $t0,   $s0
mflo   $s0
la   $s1,   arr
add   $s1,   $s0,   $s1
sw   $s0,   0($s1)

#Assignment Statement (array)

#Expression statement
li   $s0,   7

#Expression statement
li   $s0,   3
li   $t0   4
mult   $t0,   $s0
mflo   $s0
la   $s1,   arr
add   $s1,   $s0,   $s1
sw   $s0,   0($s1)

#Write Statement

#Expression statement

# Array Stuff

#Expression statement
li   $s1,   0
li   $t0,   4
mult   $t0,   $s1
mflo   $s1
la   $s2,   arr
add   $s2,   $s1,   $s2
lw   $s0,   0($s2) 
addi   $v0,   $zero,   1
add   $a0,   $s0,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall

#Write Statement

#Expression statement

# Array Stuff

#Expression statement
li   $s2,   1
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

#Write Statement

#Expression statement

# Array Stuff

#Expression statement
li   $s3,   2
li   $t0,   4
mult   $t0,   $s3
mflo   $s3
la   $s4,   arr
add   $s4,   $s3,   $s4
lw   $s2,   0($s4) 
addi   $v0,   $zero,   1
add   $a0,   $s2,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall

#Write Statement

#Expression statement

# Array Stuff

#Expression statement
li   $s4,   3
li   $t0,   4
mult   $t0,   $s4
mflo   $s4
la   $s5,   arr
add   $s5,   $s4,   $s5
lw   $s3,   0($s5) 
addi   $v0,   $zero,   1
add   $a0,   $s3,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall

#Write Statement

#Expression statement

# Array Stuff

#Expression statement
li   $s5,   2
li   $t0,   4
mult   $t0,   $s5
mflo   $s5
la   $s6,   arr
add   $s6,   $s5,   $s6
lw   $s4,   0($s6) 
addi   $v0,   $zero,   1
add   $a0,   $s4,   $zero
syscall
li   $v0,   4
la   $a0, newLine
syscall


#Exit Program 
li  $v0, 10 

