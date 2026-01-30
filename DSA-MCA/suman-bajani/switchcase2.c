#include <stdio.h>

//Array create
void array_create(int arr[]){
	int i ,num;
	printf("Enter How meny number you want to add: ");
	scanf("%d",&num);
	
	for(i=0;i<num;i++){
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
	int arr[100];
	int size=sizeof(arr)/sizeof(arr[0]);
	do{
	printf("Choose for Array implementation(insert/delete/display: ");
	printf("\n1-- Create Array.");
	printf("\n2-- Insert element.");
	printf("\n3-- Delete element.");
	printf("\n4-- Display Array.");
	printf("\n5-- Exit.");
	printf("\nPlease enter your choice(1,2,3): ");
	scanf("%d",&choice);
	switch(choice){
		case 1:
			array_create(arr);
			break;
		case 2:
			insert_arr(arr,size,0);
			break;
		case 3:
			Delete_arr(arr,size);
			break;
		case 4:
		
			Display_arr(arr,size);
			break;
	}
	}
	while(choice>=1 || choice<=3);
	
	
	return 0;
	
}
