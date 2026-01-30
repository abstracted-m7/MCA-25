#include<stdio.h>
int main(){
	int arr[]={12,23,45,67,89};
	int key=12;
	int low=0;
	int high=0;
	int pos;
	int flag=0;
	while(low<=high && key>=arr[low]&&key<=arr[high]){
		pos=low+((key-arr[low])*(high-low))/
	}
	
	
	//for delete
//	for(i=pos-1;i<n-1;i++){
//		arr[i]=arr[i+1];
//	}
//	n--;
//	
//	for (i=0;i<n;i++){
//		printf("%d\n",arr[i]);
//	}
	return 0;
}
