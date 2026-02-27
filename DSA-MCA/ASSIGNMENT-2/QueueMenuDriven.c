#include<stdio.h>
#define MAX 50

int queue[MAX];
int front = -1, rear = -1;
void insert(){
	int x;
	if(rear == MAX-1) printf("Queue Overflow..!!");
	else{
		printf("\nEnter elements: ");
		scanf("%d",&x);
		if(front == -1) front = 0;
		queue[++rear] = x;
	}
}

void deleteElement(){
	if(front == -1 || front > rear) printf("\nQueue Underflow..!!");
	else printf("\nDeleted Element: %d\n",queue[front++]);
}

void display(){
	int i;
	if(front == -1 || front > rear) printf("\nQueue Empty..!!");
	else
		for(i = front; i <= rear; i++) printf("%d",queue[i]);
		printf("\n");

}

int main(){
	int ch;
	printf("\n1. Insert\n2. Delete\n3. Display\n4. Exit\n");
	do{
		printf("\nChoosing :");
		scanf("%d",&ch);
		switch(ch){
			case 1: insert();break;
			case 2: deleteElement();break;
			case 3: display();break;
			case 4: printf("Thank you..!!");break;
		}
	}while(ch != 4);
	return 0;
}
