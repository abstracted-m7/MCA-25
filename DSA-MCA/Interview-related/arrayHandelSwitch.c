//  using switch case implement array create, insert, delete, display

#include<stdio.h>
//Insert
int insertArray(int arr[], int n){
	int i, pos, value;
	//value take
	printf("Enter the value: ");
	scanf("%d",&value);
	//position take
	printf("Enter the position: ");
	scanf("%d",&pos);
	
	for(i = n-1; i >= pos; i--){ 
		arr[i+1] = arr[i];
	}
	arr[pos] = value;
	return arr;
}

//Delete
void deleteArray(int arr[], int n){
	int i, pos;
	
	//position take
	printf("Enter the position: ");
	scanf("%d",&pos);
	
	for(i = pos; i <= n-1; i++){ //loop
		arr[i] = arr[i+1];
	}
	n--;
	return arr;
}


//Display
void display(int arr[], int n){
	int i;
	for(i = 0;i< n; i++){
		printf("%d\n",arr[i]);
	}
}


int main()
{
	int n, i, choice, len;
	int arr[100];
	// value take from user
	printf("Enter How many element you want: ");
	scanf("%d",&n);
	
	printf("Enter the elements: \n");
	for(i = 0; i < n; i++){
		printf("arr[%d]: ",i);
		scanf("%d",&arr[i]);
	}
	
	do{
		printf("\n====MENU====\n");
		printf("1. Display Element.\n");
		printf("2. Insert on Position.\n");
		printf("3. Delete on Position.\n");
		printf("4. Exit.\n");
		printf("============\n");
		
		printf("What You Want. Enter your choice: ");
		scanf("%d",&choice);
		
		switch(choice){
			case 1:
				display(arr,n); //display array
				break;
			case 2:
				insertArray(arr,n);
				break;
			case 3:
				deleteArray(arr,n);
				break;
			case 4:
				printf("Thank you visit again..!!");
				return 0;
			default:
				printf("Enter correct choice..!!");
		}
	}while(choice != 0);

	
	return 0;
}
