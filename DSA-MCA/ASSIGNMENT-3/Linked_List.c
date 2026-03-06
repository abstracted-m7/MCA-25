#include<stdio.h>
#include<stdlib.h>

struct node{ //node create
	int data;
	struct node *next;
};

//Initialize NULL in head node
struct node *head = NULL;

//-----------------------------------------------------Insert types-------------------------------------------------------------------//	
//insert at beginning
void insert_beg(){
	struct node *newnode = (struct node*)malloc(sizeof(struct node));
	printf("Enter the value: ");
	scanf("%d",&newnode -> data);
	printf("The %d Inserted Successfully.",newnode->data);
	newnode -> next = head;
	head = newnode;
}
//Insert at end
void insert_end(){
	struct node *newnode, *temp;
	newnode = (struct node*)malloc(sizeof(struct node));
	
	printf("Enter the value: ");
	scanf("%d",&newnode->data);
	newnode->next = NULL;
	printf("The %d Inserted Successfully.",newnode->data);
	
	if(head == NULL){
		head = newnode;
		return;
	}
	temp = head;
	while(temp->next!=NULL){
		temp = temp->next;
	}
	temp->next = newnode;
}

//-----------------------------------------------------delete types-------------------------------------------------------------------//
//delete first node
void delete_beg(){
	struct node *temp;
	if(head == NULL){
		printf("\nEmpty LL.");
		return;
	}
	temp = head;
	printf("Deleted Element: %d",temp->data);
	head = head->next;
	free(temp);
}
//delete end node
void delete_end(){
	struct node *temp,*prev;
	
	if(head == NULL){
		printf("\nEmpty LL.");
		return;
	}
	if(head->next == NULL){
		printf("Deleted element: %d",head->data);
		free(head);
		head = NULL;
		return;
	}
	temp = head;
	while(temp->next != NULL){
		prev = temp;
		temp = temp->next;
	}
	printf("Deleted Element: %d",temp->data);
	prev->next = NULL;
	free(temp);
}

//-----------------------------------------------------display types-------------------------------------------------------------------//
//display
void display(){
	struct node *temp = head;
	if(temp == NULL){
		printf("List is empty.!!");
		return;
	}
	printf("\nThe data in Linked List: ");
	while(temp!=NULL){
		printf("%d->",temp->data);
		temp = temp->next;
	}
	printf("NULL\n");
}

//-----------------------------------------------------main function-------------------------------------------------------------------//

int main(){
	int ch;
	
	while(1){
		printf("\n=======================================================");
		printf("\n\t\tSingly LL Menu");
		printf("\n-------------------------------------------------------");
		printf("\n\t1. Insert at the beginning.");  			//complete
		printf("\n\t2. Insert at the end.");					//complete
		printf("\n\t3. Insert after a particular element.");
		printf("\n\t4. Insert before a particular element.");
		printf("\n\t5. Delete First Node.");					//complete
		printf("\n\t6. Delete End Node.");						//complete
		printf("\n\t7. Delete the middle element.");
		printf("\n\t8. Delete any node.");
		printf("\n\t9. Display All Nodes.");					//complete
		printf("\n\t10. Reverse display off all nodes.");
		printf("\n\t11. Physically reverse the list.");
		printf("\n\t12. Exit.");								//complete
		printf("\n=======================================================\n");
		
		printf("\nEnter choice: ");
		scanf("%d",&ch);
		
		switch(ch){
			case 1:insert_beg();break;
			case 2:insert_end();break;
			case 5:delete_beg();break;
			case 6:delete_end();break;
			case 9:display();break;
			case 12:printf("Visit again..!!");return 0;
		}
	}
	return 0;
}

//---------------------------------------------------------------Code End------------------------------------------------------//
