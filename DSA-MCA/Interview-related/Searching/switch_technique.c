// Using switch case implement array create, insert, delete, display
#include <stdio.h>

// Insert element
int insertArray(int arr[], int n) {
    int i, pos, value;

    printf("Enter the value: ");
    scanf("%d", &value);

    printf("Enter the position (0 to %d): ", n);
    scanf("%d", &pos);

    if (pos < 0 || pos > n) {
        printf("Invalid position!\n");
        return n;
    }

    for (i = n - 1; i >= pos; i--) {
        arr[i + 1] = arr[i];
    }

    arr[pos] = value;
    n++;   // increase size
    return n;
}

// Delete element
int deleteArray(int arr[], int n) {
    int i, pos;

    if (n == 0) {
        printf("Array is empty!\n");
        return n;
    }

    printf("Enter the position (0 to %d): ", n - 1);
    scanf("%d", &pos);

    if (pos < 0 || pos >= n) {
        printf("Invalid position!\n");
        return n;
    }

    for (i = pos; i < n - 1; i++) {
        arr[i] = arr[i + 1];
    }

    n--;   // decrease size
    return n;
}

// Display array
void display(int arr[], int n) {
    int i;
    if (n == 0) {
        printf("Array is empty!\n");
        return;
    }

    printf("Array elements:\n");
    for (i = 0; i < n; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

int main() {
    int arr[100];
    int n, i, choice;

    printf("Enter how many elements you want: ");
    scanf("%d", &n);

    printf("Enter the elements:\n");
    for (i = 0; i < n; i++) {
        printf("arr[%d]: ", i);
        scanf("%d", &arr[i]);
    }

    do {
        printf("\n==== MENU ====\n");
        printf("1. Display\n");
        printf("2. Insert\n");
        printf("3. Delete\n");
        printf("4. Exit\n");
        printf("==============\n");

        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1:
                display(arr, n);
                break;

            case 2:
                n = insertArray(arr, n);
                break;

            case 3:
                n = deleteArray(arr, n);
                break;

            case 4:
                printf("Thank you! Visit again.\n");
                return 0;

            default:
                printf("Invalid choice!\n");
        }
    } while (choice != 4);

    return 0;
}
