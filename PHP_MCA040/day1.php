<?php
/*
	echo "Hello World\n";
	$a = 5;
	$b = 6;
	$sum = $a+$b;
	echo "Sum is :",$sum;
	$age = 17;
	if($age>=18){
		echo "\nAdult..!!";
	}else{
		echo "\nTeenager..!!";
	}
	
	$ternary_op = $age>18?"\nAdult..!!":"\nTeenager..!!";
	echo $ternary_op;
	echo "\n";
	$number = 0;

	$result = $number > 0 ? "Positive" : ($number < 0 ? "Negative" : "Zero");

	echo $result; 

	$multNumber = readline("Enter a number: ");
	for($i = 1; $i<=10; $i++){
		$result = $multNumber * $i;
		echo ($result) ;
	}
	echo "\n\n";
	$j = 1;
	while($j <= 10){
		$result = $multNumber * $j;
		echo ($result) ;
		$j++;
	}
*/
	//Odd Even
	$num = 2;
	echo $num % 2 == 0 ? "Even":"Odd";
	
	echo "\n\n";

	//Factorial
	$fact = 1;
	if($num == 0 || $num == 1){
		echo "Fact is: ",$num;
	}else{
		for($i = 2; $i <= $num; $i++){
			$fact = $fact * $i;
			echo $fact ,"\n";
		}
	}
	
	//prim number check
	$prime = 1;
	if($num <= 1){
		$prime = 0;
	}
	for($i = 2; $i<= sqrt($num); $i++){
		if($num % $i == 0){
			$prime = 0;
			break;
		}
	}
	echo $prime == 1 ? "Prime":"Not Prime";
	

?>