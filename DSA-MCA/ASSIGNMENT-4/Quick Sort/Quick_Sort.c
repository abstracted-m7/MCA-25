#include <stdio.h>

void qsort(int arr[], int l, int h);
int parti(int arr[], int l, int h);

//main function
int main() {
    int arr[] = {20,10,40,30,100,70};
    int n = 6, i;

    qSort(arr, 0, n-1);

    printf("Sorted array: ");
    for(i = 0; i < n; i++)
        printf("%d ", arr[i]);

    return 0;
}

//Quick sort function, l = low, h = high
void qSort(int arr[], int l, int h) { 
    if(l < h) {
        int pi = parti(arr, l, h);
        qSort(arr, l, pi-1);
        qSort(arr, pi+1, h);
    }
}

// partition function, l = low, h = high, t = temp
int parti(int arr[], int l, int h) { 
    int pivot = arr[h];
    int i = l-1, j, t;

    for(j = l; j < h; j++) {
        if(arr[j] < pivot) {
            i++;
            t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
    }

    t = arr[i+1];
    arr[i+1] = arr[h];
    arr[h] = t;

    return i+1;
}
