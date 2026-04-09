#include <stdio.h>
#include <stdlib.h>

typedef struct node {
    int data;
    struct node *left, *right;
}node;

node* insert(node* root, int value) {
    if(root == NULL) {
        node* newNode = (node*)malloc(sizeof(node));
        newNode->data = value;
        newNode->left = newNode->right = NULL;
        return newNode;
    }

    if(value < root->data)
        root->left = insert(root->left, value);
    else
        root->right = insert(root->right, value);

    return root;
}

void preorder(node* root) {
    if(root != NULL) {
        printf("%d ", root->data);
        preorder(root->left);
        preorder(root->right);
    }
}

void inorder(node* root) {
    if(root != NULL) {
        inorder(root->left);
        printf("%d ", root->data);
        inorder(root->right);
    }
}

void postorder(node* root) {
    if(root != NULL) {
        postorder(root->left);
        postorder(root->right);
        printf("%d ", root->data);
    }
}

int main() {
    node* root = NULL;
    int ch, value;

    //-----------------------------------------//
    printf("--Main Menu---");
    printf("\n1. Insert Node");
    printf("\n2. Preorder Traversal");
    printf("\n3. Inorder Traversal");
    printf("\n4. Postorder Traversal");
    printf("\n5. Exit");
    printf("\n----------------------------------\n");

    while(1) {

        printf("\nEnter your choice: ");
        scanf("%d", &ch);

        switch(ch) {

            case 1:
                printf("Enter value to insert: ");
                scanf("%d", &value);
                root = insert(root, value);
                break;

            case 2:
                printf("Preorder Traversal: ");
                preorder(root);
                printf("\n");
                break;

            case 3:
                printf("Inorder Traversal: ");
                inorder(root);
                printf("\n");
                break;

            case 4:
                printf("Postorder Traversal: ");
                postorder(root);
                printf("\n");
                break;

            case 5:
                exit(0);

            default:
                printf("Invalid choice\n");
        }
    }

    return 0;
}