#ifndef _process_
#define _process_
#include<iostream>
#include <fstream>
#include <string>
#include "read.h"

using namespace std;
class process {
public:
	string process_name;
	string which_code;
	int arrival_time;
	int Remain_burst_time;
	bool IsFinish;
	int process_num;
	int index = 0;

	process(string _name, string _code, int _Arrival_time, int _process_num);
};
/*
	constructor of the process class.I seperate the variables according to the definiton.txt file and also ý added proce_num whiich is use this like an process ID.
*/
process::process(string _name, string _code, int _Arrival_time, int _process_num)
{
	process_num = _process_num;
	IsFinish = false;
	process_name = _name;
	arrival_time = _Arrival_time;
	which_code = _code;
	index;
}
#endif
