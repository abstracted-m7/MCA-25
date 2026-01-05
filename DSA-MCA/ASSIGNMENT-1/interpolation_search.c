// Write a C program to find a key through the Interpolation search method.
#include <stdio.h>

int main() {
    int arr[] = {10, 20, 30, 40, 50};
    int n = 5;
    int key = 40;

    int low = 0, high = n - 1;
    int pos;
    int found = 0;

    while (low <= high && key >= arr[low] && key <= arr[high]) {

        // Interpolation formula
        pos = low + ((key - arr[low]) * (high - low)) /
                     (arr[high] - arr[low]);

        if (arr[pos] == key) {
            printf("Key(%d) found at position %d", key, pos + 1);
            found = 1;
            break;
        }

        if (arr[pos] < key)
            low = pos + 1;
        else
            high = pos - 1;
    }

    if (found == 0) {
        printf("Key not found");
    }

    return 0;
}
