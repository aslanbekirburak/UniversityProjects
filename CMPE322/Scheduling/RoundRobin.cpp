#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <queue>
#include <cstdio>
#include "read.h"
#include "process.h"

using namespace std;
ofstream out("output.txt");//definition of output.txt
/*
	this function reads the denifition.txt file then create the proceses according to process_name - which_code - arrival_time
*/
void Create_process(vector<process>& process_vector)
{
	ifstream file("definition.txt");
	string process_name;
	string which_code;
	int arrival_time;
	int process_num = 0;
	while (file >> process_name >> which_code >> arrival_time)
	{
		process newprocess(process_name, which_code, arrival_time, process_num);
		process_num++;
		process_vector.push_back(newprocess);
	}
	file.close();

}
/*
	this function write the process queue on the console and output.txt.
*/
void WriteQueue(deque<process> rrs, int time)
{
	//write to the output.txt
	out<<time<<"::HEAD-";
	for (int j = 0; j < rrs.size(); j++)
	{
		out << rrs[j].process_name << "-";
	}
	out<<"TAIL";
	//write to the console
	cout << endl << time << "::HEAD-";
	for (int j = 0; j < rrs.size(); j++)
	{
		cout << rrs[j].process_name << "-";
	}
	cout << "TAIL";
}
/*
	this function firstly checks whether new process comes to queue or not.another thing checks the queue is empty or not.
	last thing at this function ,If current process is running and there is no new proceses ,any process can't add to the list.
*/
deque<process> AddQueue(deque<process> rrs, vector<process>& process_vector, int time)
{
	for (int i = 0; i < process_vector.size(); i++)
	{
		if (process_vector[i].arrival_time <= time && !process_vector[i].IsFinish)//checks the arrival time of process and processes are finished or not
		{
			if (!rrs.empty())//if queue is empty add to the list ,no need to checks same process or not.
			{
				bool var = false;
				for (int j = 0; j < rrs.size(); j++)
				{
					if (rrs[j].which_code == process_vector[i].which_code) //check same process continue to work
					{
						var = true;
						break;
					}
				}
				if (!var)
				{
					rrs.push_back(process_vector[i]);
				}
			}
			else
			{
				rrs.push_front(process_vector[i]);
			}
		}
	}

	return rrs;
}
/*
	round robin scheduler rules are inside this function.first of all i add a process to the queue then continue with while loop.
	For loop starts from the queues's front element index then until the this process instr. size(according to their instr.'s burst times).	
	At every while loop iterations go to the AddQueue and make checks.  
*/
void run_process(vector<process>& process_vector)
{
	int total = 0;
	int time = 0;
	read r;
	deque<process> rrs;
	int count=0;
	vector<code_num> temp;//temporary vector which is used for calculating the process burst times.(it can change according to the which_code runs)
	int sayac = 0;
	while (true)
	{		
		rrs = AddQueue(rrs, process_vector, time);
		WriteQueue(rrs, time);
		if (!rrs.empty())
		{
			temp = r.read_all(rrs.front().which_code);//queues first process's code_number assign to the temp. 
			for (int j = rrs.front().index; j < temp.size(); j++)//every process has a index number to find at which process it will stop and continue. 
			{
				time += temp[j].burst;//adding total burst time to the time.
				rrs = AddQueue(rrs, process_vector, time);
				total = total + temp[j].burst;//calculating the total burst time. 
				if (total >= 100)
				{
					process_vector[rrs.front().process_num].index = j+1;//set process index
					if (temp[j].inst_name == "exit")
					{
						process_vector[rrs.front().process_num].IsFinish = true;//if instr comes to the exit, change the bool value 
						rrs.pop_front();
						break;
					}
					else
					{
						rrs.pop_front();//total of instr's burst time bigger than quantum time and dosn't reach the exit time.
					}
					total = 0;
					break;
				}
				else if (temp[j].inst_name == "exit")
				{
					process_vector[rrs.front().process_num].IsFinish = true;//if instr comes to the exit, change the bool value 
					rrs.pop_front();
					total = 0;
					break;
				}
			}
		}
		else
			time = time + 10;
		
		bool AllFinish = true;
		for (int z = 0; z < process_vector.size(); z++)
		{
			if (!process_vector[z].IsFinish)	//checks the all process's are finished,
				AllFinish = false;				//change the bool value. 
		}

		if (AllFinish)//all processes are finished exit the while loop.
			break;
	}
	WriteQueue(rrs,time);
}
int main() {
	vector<process> process;
	Create_process(process);
	run_process(process);
}
