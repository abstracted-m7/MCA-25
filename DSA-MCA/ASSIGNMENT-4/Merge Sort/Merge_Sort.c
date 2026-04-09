#include <stdio.h>

void merge(int arr[], int l, int m, int r);
void mSort(int arr[], int l, int r);

int main() {
    int arr[] = {20,10,40,30,70};
    int n = 5, i;

    mSort(arr, 0, n-1);

    printf("Sorted array: ");
    for(i = 0; i < n; i++)
        printf("%d ", arr[i]);

    return 0;
}
//merge function, left = l, mid = m, right = r, temp[] = t[]
void merge(int arr[], int l, int m, int r) {
    int i=l, j=m+1, k=l;
    int t[100];

    while(i<=m && j<=r) {
        if(arr[i] < arr[j])
            t[k++] = arr[i++];
        else
            t[k++] = arr[j++];
    }

    while(i<=m)
        t[k++] = arr[i++];

    while(j<=r)
        t[k++] = arr[j++];

    for(i=l; i<=r; i++)
        arr[i] = t[i];
}

void mSort(int arr[], int l, int r) {
    if(l < r) {
        int m = (l+r)/2;
        mSort(arr, l, m);
        mSort(arr, m+1, r);
        merge(arr, l, m, r);
    }
}