#include<stdio.h>
#include<stdlib.h>

struct node{
    int data;
    struct node *next;
};

struct node *head = NULL;

//--------------------------------------------------INSERT OPERATIONS-------------------------------------------//

// Insert at beginning
void insert_beg(){
    struct node *newnode = (struct node*)malloc(sizeof(struct node));

    printf("Enter value: ");
    scanf("%d",&newnode->data);

    newnode->next = head;
    head = newnode;

    printf("Inserted successfully.\n");
}

// Insert at end
void insert_end(){
    struct node *newnode,*temp;

    newnode = (struct node*)malloc(sizeof(struct node));

    printf("Enter value: ");
    scanf("%d",&newnode->data);

    newnode->next = NULL;

    if(head == NULL){
        head = newnode;
        return;
    }

    temp = head;

    while(temp->next != NULL){
        temp = temp->next;
    }

    temp->next = newnode;

    printf("Inserted successfully.\n");
}

// Insert after a particular element
void insert_after(){
    int key;
    struct node *temp,*newnode;

    if(head == NULL){
        printf("List Empty\n");
        return;
    }

    printf("Insert after which value: ");
    scanf("%d",&key);

    temp = head;

    while(temp!=NULL && temp->data!=key){
        temp = temp->next;
    }

    if(temp==NULL){
        printf("Element not found\n");
        return;
    }

    newnode = (struct node*)malloc(sizeof(struct node));

    printf("Enter value: ");
    scanf("%d",&newnode->data);

    newnode->next = temp->next;
    temp->next = newnode;

    printf("Inserted successfully\n");
}

// Insert before a particular element
void insert_before(){
    int key;
    struct node *temp,*prev,*newnode;

    if(head == NULL){
        printf("List Empty\n");
        return;
    }

    printf("Insert before which value: ");
    scanf("%d",&key);

    if(head->data == key){
        insert_beg();
        return;
    }

    temp = head;

    while(temp!=NULL && temp->data!=key){
        prev = temp;
        temp = temp->next;
    }

    if(temp==NULL){
        printf("Element not found\n");
        return;
    }

    newnode = (struct node*)malloc(sizeof(struct node));

    printf("Enter value: ");
    scanf("%d",&newnode->data);

    prev->next = newnode;
    newnode->next = temp;

    printf("Inserted successfully\n");
}

//--------------------------------------------------DELETE OPERATIONS-------------------------------------------//

// Delete first node
void delete_beg(){
    struct node *temp;

    if(head==NULL){
        printf("List Empty\n");
        return;
    }

    temp = head;

    printf("Deleted: %d\n",temp->data);

    head = head->next;

    free(temp);
}

// Delete last node
void delete_end(){
    struct node *temp,*prev;

    if(head==NULL){
        printf("List Empty\n");
        return;
    }

    if(head->next==NULL){
        printf("Deleted: %d\n",head->data);
        free(head);
        head=NULL;
        return;
    }

    temp=head;

    while(temp->next!=NULL){
        prev=temp;
        temp=temp->next;
    }

    printf("Deleted: %d\n",temp->data);

    prev->next=NULL;

    free(temp);
}

// Delete middle node
void delete_middle(){
    struct node *move1Step,*move2Step,*prev;

    if(head==NULL){
        printf("List Empty\n");
        return;
    }

    move1Step=head;
    move2Step=head;

    while(move2Step!=NULL && move2Step->next!=NULL){
        move2Step=move2Step->next->next;
        prev=move1Step;
        move1Step=move1Step->next;
    }

    printf("Deleted middle element: %d\n",move1Step->data);

    prev->next=move1Step->next;

    free(move1Step);
}

// Delete any node by value
void delete_value(){
    int key;
    struct node *temp,*prev;

    if(head==NULL){
        printf("List Empty\n");
        return;
    }

    printf("Enter value to delete: ");
    scanf("%d",&key);

    temp=head;

    if(head->data==key){
        head=head->next;
        free(temp);
        printf("Deleted successfully\n");
        return;
    }

    while(temp!=NULL && temp->data!=key){
        prev=temp;
        temp=temp->next;
    }

    if(temp==NULL){
        printf("Element not found\n");
        return;
    }

    prev->next=temp->next;

    free(temp);

    printf("Deleted successfully\n");
}

//--------------------------------------------------DISPLAY----------------------------------------------------//

void display(){
    struct node *temp=head;

    if(temp==NULL){
        printf("List Empty\n");
        return;
    }

    printf("Linked List: ");

    while(temp!=NULL){
        printf("%d -> ",temp->data);
        temp=temp->next;
    }

    printf("NULL\n");
}

// Reverse display (using recursion)
void reverse_display(struct node *temp){
    if(temp==NULL)
        return;

    reverse_display(temp->next);

    printf("%d ",temp->data);
}

// Physically reverse the list
void reverse_list(){
    struct node *prev=NULL,*curr=head,*next;

    while(curr!=NULL){
        next=curr->next;
        curr->next=prev;
        prev=curr;
        curr=next;
    }

    head=prev;

    printf("List Reversed Successfully\n");
}

//--------------------------------------------------MAIN MENU--------------------------------------------------//

int main(){
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

    int ch;

    while(1){

        printf("Enter choice: ");
        scanf("%d",&ch);

        switch(ch){

            case 1: insert_beg(); break;
            case 2: insert_end(); break;
            case 3: insert_after(); break;
            case 4: insert_before(); break;
            case 5: delete_beg(); break;
            case 6: delete_end(); break;
            case 7: delete_middle(); break;
            case 8: delete_value(); break;
            case 9: display(); break;
            case 10: reverse_display(head); break;
            case 11: reverse_list(); break;
            case 12: printf("Thank You, Visit Again..!!"); exit(0);

            default: printf("Invalid Choice\n");
        }
    }

    return 0;
}