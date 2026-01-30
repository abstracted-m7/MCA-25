#include<stdio.h>
int main(){
	int arr[]={5,7,9,10,11};
	int key=5;
	int size=sizeof(arr)/sizeof(arr[0]);
	int low=0;
	int mid;
	int high=size-1;
	int flag=0;
	while(low<=high){
		mid=low+(high-low)/2;
		if(arr[mid]==key){
			printf("Key found..");
			flag=1;
			break;
		}
		else if(mid[arr]<key){
			low=mid+1;
		}else{
			high=mid-1;
		}
	}
	if(flag==0){
		printf("Key not found...");
	}
	return 0;
	
	
}
