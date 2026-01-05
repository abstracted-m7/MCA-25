// Write a C program to find a key through a binary search method.
#include <stdio.h>

int main() {
    int arr[] = {10, 20, 30, 40, 50};
    int n = 5;
    int key = 40;
    int low = 0, high = n - 1, mid;
    int found = 0;

    while (low <= high) {
        mid = low + (low + high) / 2;

        if (arr[mid] == key) {
            printf("Key(%d) found at position %d", key, mid + 1);
            found = 1;
            break;
        } else if (arr[mid] < key) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }

    if (found == 0) {
        printf("Key not found");
    }

    return 0;
}
