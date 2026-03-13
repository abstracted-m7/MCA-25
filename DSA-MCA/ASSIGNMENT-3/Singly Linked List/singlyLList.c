#include<stdio.h>
#include<stdlib.h>

typedef struct node{
	int data;
	struct node *next;
} node;

//initialize head with null
node *head = NULL;

//----------------------- Insert types ---------------------------
//Insert at beginning
void insert_beg(){
	node *newnode = (node*)malloc(sizeof(node));
	printf("Enter the value: ");
	scanf("%d",&newnode->data);
	printf("The %d inserted successfully.",newnode->data);
	newnode->next = head;
	head = newnode;
}
//Insert at end
void insert_end(){
	node *newnode, *temp;
	newnode = (node*)malloc(sizeof(node));
	
	printf("Enter the value: ");
	scanf("%d",&newnode->data);
	newnode->next = NULL;
	printf("The %d inserted successfully.",newnode->data);
	
	if(head == NULL){
		head = newnode;
		return;
	}
	temp = head;
	while(temp->next != NULL){
		temp = temp->next;
	}
	temp->next = newnode;
}
//insert after a particular element
void insert_after(){
	int key;
	node *temp, *newnode;
	
	if(head == NULL){
		printf("List Empty.");
		return;
	}
	
	printf("Insert after which element: ");
	scanf("%d",&key);
	
	temp = head;
	
	while(temp!=NULL && temp->data!=key){
		temp = temp->next;
	}
	if(temp==NULL){
		printf("Element not found.!");
		return;
	}
	newnode = (node*)malloc(sizeof(node));
	printf("Enter value: ");
	scanf("%d",&newnode->data);
	
	newnode->next = temp->next;
	temp->next = newnode;
	printf("The %d inserted successfully.",newnode->data);
}

//Insert before a particular element
void insert_before(){
	int key;
	node *temp, *prev, *newnode;
	
	if(head == NULL){
		printf("List empty.");
		return;
	}
	
	printf("Insert after which element: ");
	scanf("%d",&key);
	if(head->data == key){
		insert_beg();
		return;
	}
	temp = head;
	while(temp!= NULL && temp->data != key){
		prev = temp;
		temp = temp->next;
	}
	
	if(temp==NULL){
		printf("Element not found.");
		return;
	}
	newnode = (node*)malloc(sizeof(node));
	printf("Enter value: ");
	scanf("%d",&newnode->data);
	
	prev->next = newnode;
	newnode->next = temp;
	
	printf("The %d inserted successfylly.",newnode->data);
}

//-----------------------Delete types------------------------------
//Delete first node
void delete_beg(){
	node *temp;
	
	if(head == NULL){
		printf("List empty.");
		return;
	}
	temp = head;
	printf("%d deleted successfully.",temp->data);
	head = head->next;
	free(temp);
}
//Delete last node
void delete_end(){
	node *temp, *prev;
	
	if(head == NULL){
		printf("List empty.");
		return;
	}
	if(head->next == NULL){
		printf("Deleted: %d",head->data);
		free(head);
		head = NULL;
		return;
	}
	temp = head;
	while(temp->next != NULL){
		prev = temp;
		temp = temp->next;
	}
	printf("Deleted: %d",temp->data);
	prev->next = NULL;
	free(temp);
}
//Delete middle value
void delete_middle(){
	node *move1Step, *move2Step, *prev;
	if(head == NULL){
		printf("List empty.");
		return;
	}
	move1Step = head;
	move2Step = head;
	
	while(move2Step != NULL && move2Step->next != NULL){
		move2Step = move2Step->next->next;
		prev = move1Step;
		move1Step = move1Step->next;
	}
	
	printf("Deleted middle element: %d\n",move1Step->data);
	prev->next = move1Step->next;
	free(move1Step);
}
//Delete any node by value
void delete_value(){
	int key;
	node *temp, *prev;
	if(head == NULL){
		printf("List empty.");
		return;
	}
	printf("Enter value to delete: ");
	scanf("%d",&key);
	temp = head;
	
	if(head->data == key){
		head = head->next;
		free(temp);
		printf("Deleted: %d",temp->data);
		return;
	}
	while(temp!= NULL && temp->data != key){
		prev = temp;
		temp = temp->next;
	}
	if(temp == NULL){
		printf("Element not found.");
		return;
	}
	prev->next = temp->next;
	printf("Deleted: %d",temp->data);
	free(temp);
	
}

//-----------------------Display types-----------------------------
//Display as it is
void display(){
	node *temp = head;
	if(temp == NULL){
		printf("List empty.");
		return;
	}
	printf("The data in linked list: ");
	while(temp!=NULL){
		printf("%d->",temp->data);
		temp = temp->next;
	}
	printf("NULL\n");
}
//display in reverse order
void reverse_display(node *temp){
	if(temp == NULL){
		printf("List is empty.");
		return;
	}
	reverse_display
	(temp->next);
	printf("%d->",temp->data);
}
//display physically the list
void reverse_list(){
	node *prev=NULL, *curr=head, *next;
	while(curr != NULL){
		next = curr->next;
		curr->next = prev;
		prev = curr;
		curr = next;
	}
	head = prev;
	printf("List reversed physically.");
}

//main function
int main(){
	int ch;
	printf("\n===================================================="); 
	printf("\n\t\tSingly Linked List Menu");
	printf("\n----------------------------------------------------");
	printf("\n\t1. Insert at the beginning.");
	printf("\n\t2. Insert at the end.");
	printf("\n\t3. Insert after a particular element.");
	printf("\n\t4. Insert before a particular element.");
	printf("\n\t5. Delete first node.");
	printf("\n\t6. Delete end node.");
	printf("\n\t7. Delete middle node.");
	printf("\n\t8. Delete any node.");
	printf("\n\t9. Display all node.");
	printf("\n\t10. Reverse display off all nodes.");
	printf("\n\t11. Physically reverse the list.");
	printf("\n\t12. Exit");
	printf("\n====================================================");
	
	while(1){
		printf("\n\nEnter choice: ");
		scanf("%d",&ch);
	
		switch(ch){
			case 1: insert_beg();break;
			case 2: insert_end(); break;
			case 3: insert_after();break;
			case 4: insert_before();break;
			case 5: delete_beg();break;
			case 6: delete_end();break;
			case 7: delete_middle();break;
			case 8: delete_value();break;
			case 9: display();break;
			case 10: reverse_display(head);break;
			case 11: reverse_list();break;
			case 12: printf("Exiting..!!");return 0;
			default: printf("Invalid choice..!");return 0;
		}
	}
	
	return 0;
}


