.data

fee : .word 0
iter : .word 0
arr : .word  0,  0,  0,  0,  0, 0
input:  .asciiz  "Input: " 
newLine: .asciiz "\n"

.text

main:

#----------Assignment Statement (non-array)----------#

#----------Expression statement----------#

#----------Set Value----------#
li   $s0,   1
sw  $s0,   fee

#----------Assignment Statement (non-array)----------#

#----------Expression statement----------#

#----------Set Value----------#
li   $s0,   1
sw  $s0,   iter

#----------While-Do Loop----------#
whileDoNum0:

#----------Expression statement----------#

#----------Operation----------#

#----------Expression statement----------#
lw   $s0,  iter

#----------Expression statement----------#

#----------Set Value----------#
li   $s1,   5
bge   $s0,   $s1,   endWhile0

#----------Assignment Statement (array)----------#

#----------Expression statement----------#

#----------Operation----------#

#----------Expression statement----------#
lw   $s1,  iter

#----------Expression statement----------#

#----------Set Value----------#
li   $s2,   5
mult   $s1,   $s2
mflo   $s1

#----------Expression statement----------#
lw   $s2,  iter
li   $t0,   4
mult   $t0,   $s2
mflo   $s2
la   $s3,   arr
add   $s3,   $s2,   $s3
sw   $s1,   0($s3)

#----------Assignment Statement (non-array)----------#

#----------Expression statement----------#

#----------Operation----------#

#----------Expression statement----------#
lw   $s1,  iter

#----------Expression statement----------#

#----------Set Value----------#
li   $s2,   1
add   $s1,   $s1,   $s2
sw  $s1,   iter
j whileDoNum0
endWhile0:

#----------Assignment Statement (non-array)----------#

#----------Expression statement----------#

#----------Set Value----------#
li   $s0,   1
sw  $s0,   iter

#----------While-Do Loop----------#
whileDoNum1:

#----------Expression statement----------#

#----------Operation----------#

#----------Expression statement----------#
lw   $s0,  iter

#----------Expression statement----------#

#----------Set Value----------#
li   $s1,   5
bge   $s0,   $s1,   endWhile1

#----------Write Statement----------#

#----------Expression statement----------#

#----------Array Stuff----------#

#----------Expression statement----------#
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

#----------Assignment Statement (non-array)----------#

#----------Expression statement----------#

#----------Operation----------#

#----------Expression statement----------#
lw   $s1,  iter

#----------Expression statement----------#

#----------Set Value----------#
li   $s2,   1
add   $s1,   $s1,   $s2
sw  $s1,   iter
j whileDoNum1
endWhile1:


#----------Exit Program----------# 
li  $v0, 10 

