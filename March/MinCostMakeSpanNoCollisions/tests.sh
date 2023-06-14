#!/bin/sh

# Compile main.cpp file to executable named $1

alias g++='g++-12'
g++ main.cpp -o "$1"

num_tests=$2
failed_tests=0


# Run small tests
echo "Small sparse tests: \n"
for (( i=0; i<$num_tests; i++ ))
do
	echo "Test $i:"
	obtained=`cat "mincost/small-sparse-$i.in" | ./$1`
	expected=$(cat "mincost/small-sparse-$i.ans")

	if [[ $obtained -eq $expected ]]
	then echo "PASSED: answer is: $obtained"
	else 
		echo "FAILED: expected $expected, but got $obtained" 
		((failed_tests++))
	fi 

done


echo "Small dense tests: \n"
for (( i=0; i<$num_tests; i++ ))
do
	echo "Test $i:"
	obtained=`cat "mincost/small-dense-$i.in" | ./$1`
	expected=$(cat "mincost/small-dense-$i.ans")

	if [[ $obtained -eq $expected ]]
	then echo "PASSED: answer is: $obtained"
	else 
		echo "FAILED: expected $expected, but got $obtained" 
		((failed_tests++))
	fi 

done


echo "\n"

# Run medium tests
echo "Medium sparse tests: \n"
for (( i=0; i<$num_tests; i++ ))
do
	echo "Test $i:"
	obtained=`cat "mincost/medium-sparse-$i.in" | ./$1`
	expected=$(cat "mincost/medium-sparse-$i.ans")

	if [[ $obtained -eq $expected ]]
	then echo "PASSED: answer is: $obtained"
	else 
		echo "FAILED: expected $expected, but got $obtained" 
		((failed_tests++))
	fi 

done


echo "Medium dense tests: \n"
for (( i=0; i<$num_tests; i++ ))
do
	echo "Test $i:"
	obtained=`cat "mincost/medium-dense-$i.in" | ./$1`
	expected=$(cat "mincost/medium-dense-$i.ans")

	if [[ $obtained -eq $expected ]]
	then echo "PASSED: answer is: $obtained"
	else 
		echo "FAILED: expected $expected, but got $obtained" 
		((failed_tests++))
	fi 

done

# Run large tests
#
echo "Large sparse tests: \n"
for (( i=0; i<$num_tests; i++ ))
do
	echo "Test $i:"
	obtained=`cat "mincost/large-sparse-$i.in" | ./$1`
	expected=$(cat "mincost/large-sparse-$i.ans")

	if [[ $obtained -eq $expected ]]
	then echo "PASSED: answer is: $obtained"
	else 
		echo "FAILED: expected $expected, but got $obtained" 
		((failed_tests++))
	fi 

done


echo "Large dense tests: \n"
for (( i=0; i<$num_tests; i++ ))
do
	echo "Test $i:"
	obtained=`cat "mincost/large-dense-$i.in" | ./$1`
	expected=$(cat "mincost/large-dense-$i.ans")

	if [[ $obtained -eq $expected ]]
	then echo "PASSED: answer is: $obtained"
	else 
		echo "FAILED: expected $expected, but got $obtained" 
		((failed_tests++))
	fi 

done


exit 0
