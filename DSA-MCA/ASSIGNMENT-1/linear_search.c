// Write a C program to implement a linear search method to find a key in an array.
#include <stdio.h>

int main() {
    int arr[] = {10, 20, 30, 40, 50};
    int n = 5;
    int key = 30;
    int i, found = 0;

    for (i = 0; i < n; i++) {
        if (arr[i] == key) {
            printf("Key found at position %d", i + 1);
            found = 1;
            break;
        }
    }

    if (found == 0) {
        printf("Key not found");
    }

    return 0;
}
