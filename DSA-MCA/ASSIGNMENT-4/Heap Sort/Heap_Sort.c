#include <stdio.h>

void heapify(int arr[], int n, int i);
void hSort(int arr[], int n);

int main() {
    int arr[] = {20,10,40,30,70};
    int n = 5, i;

    hSort(arr, n);

    printf("Sorted array: ");
    for(i = 0; i < n; i++)
        printf("%d ", arr[i]);

    return 0;
}

//heapify, left = l, right = r, temp = t
void heapify(int arr[], int n, int i) {
    int largest = i, l = 2*i + 1, r = 2*i + 2, t;

    if(l < n && arr[l] > arr[largest])
        largest = l;

    if(r < n && arr[r] > arr[largest])
        largest = r;

    if(largest != i) {
        t = arr[i];
        arr[i] = arr[largest];
        arr[largest] = t;

        heapify(arr, n, largest);
    }
}
//HeapSort function, temp = t
void hSort(int arr[], int n) {
    int i, t;

    for(i = n/2 - 1; i >= 0; i--)
        heapify(arr, n, i);

    for(i = n-1; i > 0; i--) {
        t = arr[0];
        arr[0] = arr[i];
        arr[i] = t;

        heapify(arr, i, 0);
    }
}