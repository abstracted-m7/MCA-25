#include<stdio.h>
int main(){
	int arr[]={2,3,4,56,1};
	int key=2;
	int i;
	int flag=0;
	int size=sizeof(arr)/sizeof(arr[0]);
	for(i=0;i<size;i++){
		if(arr[i]==key){
			printf("key found...");
			flag=1;
			break;
		}
	}
	if(flag!=1){
		printf("key not found");	
	}
	return 0;
}
