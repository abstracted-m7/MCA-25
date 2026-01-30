#include <stdio.h>

//Array create
void array_create(int arr[],int size){
	int i;
	for(i=0;i<size;i++){
		printf("Enter Element at arr[%d]",i);
		scanf("%d",&arr[i]);
	}
}
//insert Array
void insert_arr(int arr[],int size,int pos){
	int value,i;
	printf("Enter the value: ");
	scanf("%d",&value);
	for (i=size;i>=pos;i--){
		arr[i]=arr[i-1];
	}
	arr[pos]=value;
	size++;
	printf("Element Insert Successfully...");
}
//Delete Array
void Delete_arr(int arr[],int size){
	int value,i,pos;
	printf("Enter the value: ");
	scanf("%d",&value);
	for (i=size;i>=pos;i--){
		arr[i]=arr[i+1];
	}
	arr[pos]=value;
	size--;
}

//Display Array
void Display_arr(int arr[],int size){
	int i;
	for (i=0;i<size;i++){
		printf("%d ",arr[i]);
	}
}

int main(){
	int choice;
	int n,i;
	printf("Enter How meny number you want to add: ");
	scanf("%d",&n);
	int arr[100];                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              
	for(i=0;i<size;i++){
		printf("Enter Element at arr[%d]",i);
		scanf("%d",&arr[i]);
	}
	
	do{
	printf("\nChoose for Array implementation(insert/delete/display: ");
	printf("\n1-- Insert element.");
	printf("\n2-- Delete element.");
	printf("\n3-- Display Array.");
	printf("\n4-- Exit.");
	printf("\nPlease enter your choice(1,2,3): ");
	scanf("%d",&choice);
	switch(choice){
		case 1:
			insert_arr(arr,size,0);
			break;
		case 2:
			Delete_arr(arr,size);
			break;
		case 3:
			Display_arr(arr,size);
			break;
		case 4:
			return 0;
			break;
	
			
	}
	}
	while(choice>=1 || choice<=4);
	
	
	return 0;
	
}
