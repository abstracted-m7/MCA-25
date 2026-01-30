#include <stdio.h>
int main(){
	int arr[100],pos,value,i,size;
	
	printf("Enter size: ");
	scanf("%d",&size);
	
	for (i=0;i<size;i++){
		printf("arr[%d]",i);
		scanf("%d",&arr[i]);		
	}
	
	printf("Enter position: ",size+1);
	scanf("%d",&pos);
	printf("Enter value: ");
	scanf("%d",&value);
	
	
//	//for insert
	for (i=size;i>=pos;i--){
		arr[i]=arr[i-1];
	}
	arr[pos]=value;
	size++;
	
	for (i=0;i<size;i++){
		printf("%d\n",arr[i]);
	}


	return 0;
}
