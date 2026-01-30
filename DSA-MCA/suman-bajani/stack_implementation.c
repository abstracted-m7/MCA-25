#include <stdio.h>
#define Max 3
int stack[Max];
int top = -1;
int num,i;

void Push(){
	if(top == Max-1){
		printf("\nStack is Overflow condition...");
	}else{
		printf("\nEnter number you want to push: ");
		scanf("%d",&num);
		top++;
		stack[top]=num;
		printf("\nPush successfull...");
	}
}
void Pop(){
		if(top == -1){
		printf("\nStack is underflow condition...");
	}else{
		top--;
		printf("\nPop successfull...");
	}
}
void Peek(){
	if(top!=-1){
		printf("\nPeek element: %d ",stack[top]);
	}
	
}
void Display(){
	if(top==-1){
		printf("\nStack is empty...");
	}
	else{
		for(i=top;i>=0;i--){
			printf("%d ",stack[i]);
		}
	}
	printf("\nDisplay successfully...");
}

int main(){
	int choice,num;
	do{	
		printf("\n-------------Menu--------------");
		printf("\n1. Push");
		printf("\n2. Pop");
		printf("\n3. Peek");
		printf("\n4. Display");
		printf("\n5. Exit");
		printf("\nEnter your Choice: ");
		scanf("%d", &choice);
		switch(choice){
			case 1:	
				Push();
				break;
			case 2:
				Pop();
				break;
			case 3:
				Peek();
				break;
			case 4:
				Display();
				break;
			case 5:
				return 0;
				break;	
		}
	}while(choice>=1 && choice <=5);
return 0;
}
