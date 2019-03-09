#ifndef _semaphore__
#define _semaphore_
#include<iostream>
#include <fstream>
#include <string>
#include <vector>
#include "process.h"
#include <queue>

using namespace std;

class semaphore
{
public:
	semaphore(int _queueNum);
	deque<process> processQueue;
	int queueNum;
	bool IsThereWork;//semaphore is full or not
private:

};

semaphore::semaphore(int _queueNum)
{
	IsThereWork = false;
	queueNum = _queueNum;
}
#endif
