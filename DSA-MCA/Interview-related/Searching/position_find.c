/*
    Write a program to print all the locations at which a particular element (taken as input) is found in a list and also print the total number of times it occurs in the list. The location starts from 1.

    for example, if there are 4 elements in the array 
    5
    6
    5
    7
    If the element to search is 5 then the output will be 
    5 is present at location 1
    5 is present at location 3
    5 is present 2 times in the array.

*/

#include <stdio.h>

int main() {
    int n, i, key, count = 0;
    int arr[100];

    printf("Enter number of elements: ");
    scanf("%d", &n);

    printf("Enter elements:\n");
    for (i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }

    printf("Enter element to search: ");
    scanf("%d", &key);

    for (i = 0; i < n; i++) {
        if (arr[i] == key) {
            printf("%d is present at location %d\n", key, i + 1);
            count++;
        }
    }

    if (count > 0)
        printf("%d is present %d times in the array.\n", key, count);
    else
        printf("%d is not present in the array.\n", key);

    return 0;
}
