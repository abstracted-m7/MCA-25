/*
	5. Write a program to convert the given infix expression into prefix form using stack.
*/

#include <stdio.h>
#include <ctype.h>
#include <string.h>
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

void reverse(char exp[]){
	int i,j;
	char temp;
	for( i = 0, j = strlen(exp) - 1; i < j; i++,j--){
		temp = exp[i];
		exp[i] = exp[j];
		exp[j] = temp;
	}
}

void InfixToPostfix(char infix[], char postfix[]){
	int i,k = 0;
	char ch;
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
}

int main()
{
	char infix[MAX], postfix[MAX], prefix[MAX];
	int i;
	
	printf("Enter infix expression: ");
	scanf("%s",infix);
	
	reverse(infix);
	
	for(i=0; infix[i] != '\0'; i++){
		if (infix[i] == '(') infix[i] = ')';
		else if(infix[i] == ')') infix[i] = '(';
	}
	
	InfixToPostfix(infix, postfix);
	reverse(postfix);
	strcpy(prefix, postfix);
	
	printf("The prefix exp is: %s",prefix);
	return 0;
}
