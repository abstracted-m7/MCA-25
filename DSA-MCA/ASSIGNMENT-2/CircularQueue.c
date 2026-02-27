#include <stdio.h>
#define MAX 5

int queue[MAX];
int front = -1;
int rear = -1;

// Function to insert element
void insert() {
    int value;

    if ((rear + 1) % MAX == front) {
        printf("Queue is Overflow!\n");
        return;
    }

    printf("Enter the element: ");
    scanf("%d", &value);

    if (front == -1) front = rear = 0;
    else rear = (rear + 1) % MAX;

    queue[rear] = value;
    printf("%d inserted.\n",value);
}

// Function to delete element
void deleteElement() {
    if (front == -1) {
        printf("Queue is Underflow!\n");
        return;
    }

    printf("Deleted element: %d\n", queue[front]);

    if (front == rear) front = rear = -1;
	else front = (front + 1) % MAX;
}

// Function to display elements
void display() {
    if (front == -1) {
        printf("Queue is empty!\n");
        return;
    }

    int i = front;
    printf("Queue: ");
    while (1) {
        printf("%d ", queue[i]);
        if (i == rear) break;
        i = (i + 1) % MAX;
    }
    printf("\n");
}

// Main function
int main() {
    int ch;
    printf("1. Insert\n");
    printf("2. Delete\n");
    printf("3. Display\n");
    printf("4. Exit\n");
    do{
        printf("Choosing : ");
        scanf("%d", &ch);

        switch (ch) {
            case 1: insert(); break;
            case 2: deleteElement(); break;
            case 3: display(); break;
            case 4: return 0;
            default: printf("Invalid choice!\n");
        }
    }while(ch != 4);

    return 0;
}
