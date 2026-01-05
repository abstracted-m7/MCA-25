// Write a program to print the position of the smallest number of an array
#include <stdio.h>

int main()
{
    int arr[6] = {3, 8, 6, 2, 7, 1};
    int i, n = sizeof(arr) / sizeof(arr[0]);
    int min = arr[0];
    int pos = 0; 

    for (i = 1; i < n; i++)
    {
        if (arr[i] < min)
        {
            min = arr[i];
            pos = i;
        }
    }

    printf("min num= %d\n", min);
    printf("idx= %d\n", pos);

    return 0;
}

