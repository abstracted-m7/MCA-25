// Write a program to read and display n numbers using array
#include <stdio.h>
#include <math.h>
int main()
{
	int arr[5] = {4,2,1,6,7};
	int n = sizeof(arr)/sizeof(arr[0]);
	int i;
	for(i = 0; i<n; i++){
		printf("%d \n",arr[i]);
	}
}
