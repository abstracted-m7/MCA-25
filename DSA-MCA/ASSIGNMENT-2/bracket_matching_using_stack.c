/*
	Write a program to check whether the parenthesises are properly arranged or not e.g. [{(a+b}]) is not properly arranged.
*/

#include <stdio.h>
#include <string.h>
#define MAX 100

char stack[MAX];
int top = -1;

//push function
void push(char c){
	stack[++top] = c;
}
//pop function
char pop(){
	return stack[top--];
}
//Match brackets
int isMatching(char open, char close){
	if(open == '(' && close == ')') return 1;
	if(open == '{' && close == '}') return 1;
	if(open == '[' && close == ']') return 1;
	return 0;
}
//Main function
int main()
{
	char expr[MAX];
	int i, valid = 1;
	
	printf("Enter expression:  ");
	scanf("%s",expr);
	
	for(i = 0; expr[i] != '\0'; i++){
		if(expr[i]=='(' || expr[i]== '{' || expr[i] == '[')
		{
			push(expr[i]);
		}
		else if(expr[i]==')' || expr[i]== '}' || expr[i] == ']')
		{
			if(top == -1 || !isMatching(pop(), expr[i]))
			{
				valid = 0;
				break;
			}
		}
	}
	if(top != -1) valid = 0;
	if(valid) printf("\nprentheses correct.");
	else printf("\nParentheses not correct.");
	return 0;
}
