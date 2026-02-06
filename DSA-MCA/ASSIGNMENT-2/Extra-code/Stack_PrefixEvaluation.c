/*
	4. Write a C program to evaluate the postfix expression using stack. 
	task: remove edge case like: +-27*8/412 as input but the compiler assume single char, so i have to remove this error such way: user give input like: + - 2 7 * 8 / 4 12, space between elements. in create this space will be elemenate during operations.
*/

#include<stdio.h>
#include<ctype.h>

#define MAX 50

int stack[MAX];
int top = -1;

void push(int n){
	stack[++top] = n;
}

char pop(){
	return stack[top--];
}

int main(){
	int i,a,b, len;
	char prefix[MAX];
	
	printf("Enter the prefix exp: ");
	scanf("%s",prefix);
	
	len = strlen(prefix);
	
	for( i = len - 1; i >= 0; i --){
		if(isdigit(prefix[i])) push(prefix[i] - '0');
		
		else{
			a = pop();
			b = pop();
			
			switch(prefix[i]){
				case '+': push(a+b); break;
				case '-': push(a-b); break;
				case '*': push(a*b); break;
				case '/': push(a/b); break;
			}
		}
	}
	
	printf("Result = %d\n",pop());
	return 0;
}

