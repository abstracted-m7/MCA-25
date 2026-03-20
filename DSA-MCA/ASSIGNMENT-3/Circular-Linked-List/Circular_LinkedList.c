#include <stdio.h>
#include <stdlib.h>

typedef struct node {
    int data;
    struct node* next;
} node;
struct node* head = NULL;
//-----------------------------------------------------Insert Types-------------------------------------------------------------------//
// Insert at the beginning
void insert_beg() {
	int val;
	printf("Enter value: ");
	scanf("%d",&val);
	
    node *newnode = (node*)malloc(sizeof(node));
    newnode->data = val;
    
    if (head == NULL) {
        newnode->next = newnode;
        head = newnode;
        printf("Inserted (%d) Successfully..!!",head->data);
        return;
    }

    node *temp = head;
    while (temp->next != head) {
        temp = temp->next;
    }
    
    newnode->next = head;
    temp->next = newnode;
    head = newnode;
    printf("Inserted (%d) Successfully..!!",head->data);
}

// Insert at the end
void insert_end() {
	int val;
	printf("Enter value: ");
	scanf("%d",&val);
	
    node *newnode = (node*)malloc(sizeof(node));
    newnode->data = val;

    if (head == NULL) {
        newnode->next = newnode;
        head = newnode;
        return;
    }

    node *temp = head;
    while (temp->next != head) {
        temp = temp->next;
    }
    
    temp->next = newnode;
    newnode->next = head;
    printf("Inserted (%d) Successfully..!!",newnode->data);
}
//-----------------------------------------------------Delete Types-------------------------------------------------------------------//
// Delete first node
void delete_beg() {
    if (head == NULL){
    	printf("List Empty.");
    	return;
	}

    node *temp = head, *last = head;

    if (temp->next == head) {
        free(temp);
        head = NULL;
        return;
    }

    while (last->next != head) {
        last = last->next;
    }

    head = head->next;
    last->next = head;
    printf("Deleted Element: %d",temp->data);
	free(temp);
}

// Delete last node
void delete_end() {
    if (head == NULL){
    	printf("List Empty.");
    	return;
	}

    node *temp = head, *prev = NULL;

    if (temp->next == head) {
        printf("Deleted Element: %d",temp->data);
        free(temp);
        head = NULL;
        return;
    }

    while (temp->next != head) {
        prev = temp;
        temp = temp->next;
    }

    prev->next = head;
    printf("Deleted Element: %d",temp->data);
    free(temp);
}
//-----------------------------------------------------Display-------------------------------------------------------------------//
// Display all nodes
void display() {
    if (head == NULL) {
        printf("List empty.\n");
        return;
    }
    node* temp = head;
    do {
        printf("%d -> ", temp->data);
        temp = temp->next;
    } while (temp != head);
    printf("head\n");
}
//-----------------------------------------------------main function-------------------------------------------------------------------//
int main() {
int ch;
	
	while(1){

		printf("\n============================================");
		printf("\n\tCircular Linked List Menu");
		printf("\n------------------------------------------");
		printf("\n\t1. Insert at the beginning");
		printf("\n\t2. Insert at the end");
		printf("\n\t3. Delete first node");
		printf("\n\t4. Delete last node");
		printf("\n\t5. Display all nodes");
		printf("\n\t6. Exit");
		printf("\n============================================");
		printf("\nEnter the choice: ");
		scanf("%d",&ch);
		switch(ch){
			case 1: insert_beg(); break;
			case 2: insert_end(); break;
			case 3: delete_beg(); break;
			case 4:	delete_end(); break;
			case 5: display(); break;
			case 6: printf("Exiting..!!");return 0;
			default: printf("Invalid choice..!!"); return 0;
		}
	}

    return 0;
}
