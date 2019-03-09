#include <iostream>
#include <ios>
#include <fstream>
#include <string>
#include <vector>
#include <queue>
#include <cstdio>
#include <sstream>
#include "read.h"
#include "process.h"
#include "semaphore.h"

using namespace std;
deque<semaphore> semaphoreList;//a semaphore queue is created
//At this method ý created my waiting queue
void sListCreate()
{
	for (int i = 0; i < 10; i++)
	{
		semaphore s(i);
		semaphoreList.push_back(s);
	}
}


deque<process> rrs;//my ready queue
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
this function write the process ready queue on the console and output.txt.
*/
void WriteQueue(deque<process> rrs, int time)
{
	//write to the output.txt
	out << time << "::HEAD-";
	for (int j = 0; j < rrs.size(); j++)
	{
		out << rrs[j].process_name << "-";
	}
	out << "TAIL"<<endl;
	//write to the console
	cout << endl << time << "::HEAD-";
	for (int j = 0; j < rrs.size(); j++)
	{
		cout << rrs[j].process_name << "-";
	}
	cout << "TAIL";
}
/*
this function write the process waiting queue on the console and output_1|2|3|etc..txt.
*/

void WriteWaitingQueue(deque<semaphore> List,int num_semaphore,int time)
{
	int a = num_semaphore;
	stringstream ss;
	ss << a;
	string str = ss.str();
	string name = "output_" + str + ".txt";
	//waiting_out=fopen (name.c_str(),"a");	
	
	ofstream waiting_out(name.c_str(), ios_base::app | ios_base::out);
	waiting_out << time << "::HEAD-";
	for (int j = 0; j < List[num_semaphore].processQueue.size(); j++)
	{
		waiting_out << List[num_semaphore].processQueue[j].process_name << "-";
	}
	waiting_out << "TAIL"<<endl;
	
	waiting_out.close();
}
/*
this method main aim is to add the procees to the ready and while adding to the list make some checkings
*/
deque<process> AddQueue(deque<process> rrs, vector<process>& process_vector, int time)
{
	for (int i = 0; i < process_vector.size(); i++)
	{
		if (process_vector[i].arrival_time <= time && !process_vector[i].IsFinish && !process_vector[i].IsThisInQueue)//checks the arrival time of process and processes are finished or not.
		//at the same time chceks the process is inside the waiting queue
		{
			if (!rrs.empty())//if queue is empty add to front of the list or add to the end.
			{
				rrs.push_back(process_vector[i]);
				process_vector[i].IsThisInQueue = true;
			}
			else
			{
				rrs.push_front(process_vector[i]);
				process_vector[i].IsThisInQueue = true;
			}
		}
	}

	return rrs;
}
/*
The process is adding to the waiting queue and deleted from ready queue.
*/
void add_waiting(int num_semaphore, process p, vector<process>& process_vector,int j,int time)
{
	p.index = j + 1;
	semaphoreList[num_semaphore].processQueue.push_back(p);//add to the semaphorelist elment waiting queue
	WriteWaitingQueue(semaphoreList,num_semaphore,time);
	rrs.pop_front();//delete from the ready queue
	WriteQueue(rrs, time);
}
/*
The process is adding to the ready queue and deleted from waiting queue.
*/
void addToReady(int num_semaphore, process p, vector<process>& process_vector,int time)
{
	if (!semaphoreList[num_semaphore].processQueue.empty())
	{	
		rrs.push_back(semaphoreList[num_semaphore].processQueue[0]);
		semaphoreList[num_semaphore].processQueue.pop_front();//delete from the waiting queue
		semaphoreList[num_semaphore].IsThereWork = true;//make the semaphorelist elemnt busy
		WriteWaitingQueue(semaphoreList,num_semaphore,time);
	}	
	//WriteQueue(rrs, time);
}
/*
this is submethod for other methods.this method split the wait and sign and return their last integer value.(For ex:WaitS_5 returns 5)
*/
int token_number(string name)
{
	string s = name;
	string delimiter = "_";
	string token = s.substr(6, s.find(delimiter));
	stringstream convert(token);
	int x = 0;
	convert >> x;
	return x;
}
/*
this methods checks every time that Code file comes to the Wait or Sign.Ýf comes them check the semaphore is empty or not. 
*/
bool inc_dec_semaphore(string instructor, process p, vector<process>& process_vector,int j,int time)
{
	string s(instructor);
	if (s.find("s") == 0 || s.find("S") == 0)
	{
		int num = token_number(s);
		semaphoreList[num].IsThereWork = false;//discharge the semaphorelist
		addToReady(num, p, process_vector,time);//send to the ready queue
	}
	if (s.find("w") == 0 || s.find("W") == 0)
	{
		int num = token_number(s);
		if (semaphoreList[num].IsThereWork)//If the semphore full so semaphore has already busy
		{
			add_waiting(num, p, process_vector,j,time);//send to the waiting queue
			return true;
		}
		else
			semaphoreList[num].IsThereWork = true;//convert empty semaphore to the busy.
	}
	return false;
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

	int count = 0;
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
				if (inc_dec_semaphore(temp[j].inst_name, rrs.front(), process_vector, j,time))//check that Code file comes to sign or wait instructor.
				//If semaphore aldreay full ,method sretuns true and toatl will be 0
				{
					total = 0;
					break;
				}
				time += temp[j].burst;//adding total burst time to the time.
				rrs = AddQueue(rrs, process_vector, time);
				total = total + temp[j].burst;//calculating the total burst time. 
				if (total >= 100)
				{
					process_vector[rrs.front().process_num].index = j + 1;//set process index
					if (temp[j].inst_name == "exit")//buraya if ile inst_name kontrolü yapýlýr#eger var olmayan semaphore ise devam et#eger var olan semapore ise pop yap ve waiting quuee ekle.
					{
						process_vector[rrs.front().process_num].IsFinish = true;//if instr comes to the exit, change the bool value 
						process_vector[rrs.front().process_num].IsThisInQueue = false;//if the process is not in the ready queue
						rrs.pop_front();
						break;
					}
					else
					{   //buraya if ile inst_name kontrolü yapýlýr
						process_vector[rrs.front().process_num].IsThisInQueue = false;//if the process is not in the ready queue
						rrs.pop_front();//total of instr's burst time bigger than quantum time and dosn't reach the exit time.
					}
					total = 0;
					break;
				}
				else if (temp[j].inst_name == "exit")
				{
					process_vector[rrs.front().process_num].IsFinish = true;//if instr comes to the exit, change the bool value 
					process_vector[rrs.front().process_num].IsThisInQueue = false;//if the process is not in the ready queue
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
	WriteQueue(rrs, time);
}
int main() {
	sListCreate();
	vector<process> process;
	Create_process(process);
	run_process(process);
}
