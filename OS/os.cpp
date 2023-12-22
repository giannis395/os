#include <stdio.h>
#include <stdlib.h>

struct process{
	int id;
	int state; // ready(0) running(1) blocked(2)
	int priority;
	int pc;
	bool io;
	int memory_point;
	int burst_time;
	
};

void fill_task(struct process task[], int n );

main(){
	printf("Enter the number of tasks: ");
	int n;
	scanf("%d", &n);
	struct process task[n];
	fill_task(task, n);
	system("Pause");
}

void fill_task(struct process task[], int n ){
	for (int i=0; i<n; i++){
		task[i].id = i;
		task[i].state = 0;
		task[i].priority = rand() % 7 + 1;
		task[i].pc = 0;
		task[i].io = rand() % 2;
		task[i].memory_point = i;
		task[i].burst_time = rand() % 20 + 1;
	}
}


struct priorityquee(){
	
}

