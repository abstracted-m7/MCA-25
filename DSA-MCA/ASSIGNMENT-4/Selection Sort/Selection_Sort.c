#include <stdio.h>

int main() {
    int arr[] = {20,10,40,30,70};
    int n = 5, i, j, minIdx, t;

    for(i = 0; i < n - 1; i++) {
        minIdx = i;

        for(j = i + 1; j < n; j++) {
            if(arr[j] < arr[minIdx]) {
                minIdx = j;
            }
        }

        t = arr[i];
        arr[i] = arr[minIdx];
        arr[minIdx] = t;
    }

    printf("Sorted array: ");
    for(i = 0; i < n; i++) {
        printf("%d ", arr[i]);
    }

    return 0;
}