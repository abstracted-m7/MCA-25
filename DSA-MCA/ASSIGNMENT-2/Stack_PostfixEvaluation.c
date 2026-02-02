/*
	4. Write a C program to evaluate the postfix expression using stack.
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
	int i,a,b;
	char postfix[MAX];
	
	printf("Enter the postfix exp: ");
	scanf("%s",postfix);
	
	for( i = 0; postfix[i] != '\0'; i++){
		if(isdigit(postfix[i])) push(postfix[i] - '0');
		
		else{
			b = pop();
			a = pop();
			
			switch(postfix[i]){
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

