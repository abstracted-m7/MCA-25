#include <stdio.h>
#define MAX 3

int stk[MAX];
int top= -1;

void push(int stk[], int val);
int pop(int stk[]);
int peek(int stk[]);
void display(int stk[]);

/* push oper. */
void push(int stk[], int val)
{
	if (top == MAX-1)
	{
		printf("Stack Overflow.");
	}else
	{
		top++;
		stk[top] = val;
		printf("Value: %d inserted successfully..",val);
	}
}

/* pop function */
int pop(int stk[])
{
	int val;
	if (top == -1)
	{
		printf("Stack Undeflow");
		return -1;	
	}else
	{
		val = stk[top];
		top--;
		return val;
	}
}
/* peek function */
int peek(int stk[])
{
	if (top == -1)
	{
		printf("Stack Empty.");
		return -1;	
	}else
	{
		return stk[top];
	}
}
/* display function */
void display(int stk[])
{
	int i;
	if(top == -1)
	{
		printf("Stack is empty.");
	}
	else{
		printf("Elements in stack are : ");
		for(i = top; i >= 0; i--)
		{
			printf("\n%d",stk[i]);
		}	
	}
}
/* main function */
int main()
{
	int val, choice;
	do{
		printf("\n\n=== Main Menu ===");
		printf("\n1. Push");
		printf("\n2. Pop");
		printf("\n3. Peek");
		printf("\n4. Display");
		printf("\n5. Exit");
		printf("\n=================");
		printf("\n\nEnter your choice: ");
		scanf("%d", &choice);
		
		switch(choice){
			case 1:
				printf("Enter the number to be store on stack: ");
				scanf("%d", &val);
				push(stk, val);
				break;
			case 2:
				val = pop(stk);
				if(val != -1){
					printf("The value %d from the stack.", val);
				}
				break;
			case 3:
				val = peek(stk);
				if(val != -1){
					printf("The peek element is: %d",val);
				}
				break;
			case 4:
				display(stk);
				break;
			case 5:
				printf("Program Ended..!!");
				break;
			default:
				printf("Invalid Choice...");
		}
	}while(choice != 5);
	return 0;
}
