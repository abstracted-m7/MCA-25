/*
	3. Write a program to convert the given infix expression into postfix form using stack.
*/

#include<stdio.h>
#include<ctype.h>
#define MAX 50


char stack[MAX];
int top =- 1;

void push(char op){
	stack[++top] = op;
}

char pop(){
	if(top == -1) return -1;
	return stack[top--];
}

int precedence(char op){
	if(op == '+' || op == '-') return 1;
	if(op == '*' || op == '/') return 2;
	if(op == '^') return 3;
	return 0;
}

int main()
{
	char infix[MAX], postfix[MAX];
	int i,k = 0;
	char ch;
	
	printf("Enter infix expression: ");
	scanf("%s",infix);
	
	for ( i = 0; infix[i] != '\0'; i++){
		ch = infix[i];
		
		if(isalnum(ch)) postfix[k++] = ch;
		else if(ch == '(') push(ch);
		else if(ch == ')'){
			while(stack[top] != '(') postfix[k++] = pop();
			pop();
		}else{
			while(top != -1 && precedence(stack[top]) >= precedence(ch))
				postfix[k++] = pop();
			push(ch);
		}
	}
	while(top != -1) postfix[k++] = pop();
	
	postfix[k] = '\0';
	printf("Postfix expression: %s\n",postfix);
	return 0;
}
