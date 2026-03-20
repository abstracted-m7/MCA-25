#include <stdio.h>
#include <stdlib.h>

// Structure for a node
typedef struct node {
    int data;
    struct node* prev;
    struct node* next;
} node;

struct node* head = NULL;
//-----------------------------------------------------Insert Types-------------------------------------------------------------------//
// Insert at the beginning
void insert_beg() {
	int val;
	printf("\nEnter value: ");
	scanf("%d",&val);
	
    node *newnode = (node*)malloc(sizeof(node));
    newnode->data = val;
    newnode->prev = NULL;
    newnode->next = head;

    if (head != NULL) {
        head->prev = newnode;
    }
    head = newnode;
    printf("Inserted Element: %d", val);
}

// Insert at the end
void insert_end() {
	int val;
	printf("\nEnter value: ");
	scanf("%d",&val);
	
    node *newnode = (node*)malloc(sizeof(node));
    node *temp = head;
    newnode->data = val;
    newnode->next = NULL;

    if (head == NULL) {
        newnode->prev = NULL;
        head = newnode;
        return;
    }

    while (temp->next != NULL) {
        temp = temp->next;
    }

    temp->next = newnode;
    newnode->prev = temp;
    printf("Inserted Element: %d", val);
}
//-----------------------------------------------------delete types-------------------------------------------------------------------//
// Delete first node
void delete_first() {
    if (head == NULL) {
        printf("List is empty.");
        return;
    }
    node *temp = head;
    head = head->next;
    if (head != NULL) {
        head->prev = NULL;
    }
    printf("Deleted element: %d.",temp->data);
    free(temp);
}

// Delete last node
void delete_last() {
    if (head == NULL) {
        printf("List is empty.");
        return;
    }
    node *temp = head;
    if (temp->next == NULL) {
        head = NULL;
        printf("Deleted element: %d",temp->data);
		free(temp);
    } else {
        while (temp->next != NULL) {
            temp = temp->next;
        }
        temp->prev->next = NULL;
    	printf("Deleted element: %d",temp->data);
        free(temp);
    }
}
//-----------------------------------------------------display-------------------------------------------------------------------//
// Display all nodes
void display() {
    node *temp = head;
    if (head == NULL) {
        printf("List is empty.");
        return;
    }
    printf("List: ");
    while (temp != NULL) {
        printf("%d <-> ", temp->data);
        temp = temp->next;
    }
    printf("NULL");
}

//-----------------------------------------------------main function-------------------------------------------------------------------//
int main() {
	int ch;
	
	while(1){

		printf("\n============================================");
		printf("\n\tDoubly Linked List Menu");
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
			case 3: delete_first(); break;
			case 4:	delete_last(); break;
			case 5: display(); break;
			case 6: printf("Exiting..!!");return 0;
			default: printf("Invalid choice..!!"); return 0;
		}
	}

    return 0;
}
