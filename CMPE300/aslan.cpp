
#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <cstdlib>
#include <math.h>
#include <cstring>
using namespace std;

int main(int argc, char *argv[]) {
	if(argc<5){//check for enough parameter is inserted otherwise give an error
		   	cout<<"please enter valid inputs"<<endl;
		   	cout<<"mpic++ denoise.cpp -o bekir"<<endl;
		   	cout<<"mpiexec -n <processor number> bekir <input file> <output file> <beta> <pi>"<<endl;
}else{
	//Definition part ,some function are defined in here   
    MPI_Init(NULL, NULL);
    int world_rank, world_size;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);
    int i,j;
    int num_slaves = world_size-1;
    int numberOfRows = 200/num_slaves;
    double beta = atof(argv[3]);
    double pi = atof(argv[4]);
    double gamma = 0.5*(log10((1-pi)/pi));
    //If it is a master processor,read the input file and send to the slaves and receive from slaves then write to the file
   if(world_rank == 0) {
         ifstream read ;
         read.open(argv[1]);        
        int sendarray[200][200];        
        int val;
        //read file
        if(read){
            for(i=0; i<200; i++){
                for(j=0; j<200; j++){                  
                    read>>sendarray[i][j] ;                    
                }
            }
        }
        //sen to the slaves
        for(i=1; i<world_size; i++){
            MPI_Send(sendarray[(i-1)*numberOfRows], numberOfRows*200, MPI_INT, i, 0, MPI_COMM_WORLD);
        }
        //receive from slaves
        int returnedarray[200][200];
        for(i=1; i<world_size; i++){
            MPI_Recv(returnedarray[(i-1)*numberOfRows], numberOfRows*200, MPI_INT, i, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }
        //write the returned array to the array
        ofstream output;
        output.open(argv[2]);
        for(int i=0;i<200;i++){
            for(int j= 0; j< 200; j++){
                output << returnedarray[i][j] << " " ;
            }
            output << "\n";
        }
        read.close();
        output.close();
    }else{
    	//Define some arrays to be in the communication between processors
        int subarr[numberOfRows][200];
        int copy[numberOfRows][200];
        int receivedarray1[200];
        int receivedarray2[200];
        MPI_Recv(subarr[0], numberOfRows*200, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        //iterate the processor for the processor
        //for all processors iterate 500000/num_slaves
  		int T = 500000/num_slaves;
  		//Get the copy of the subarray(copy of slaves) 
        for(int i= 0;i< numberOfRows;i++){
            for(int j= 0; j< 200;j++){
                copy[i][j] = subarr[i][j];
            }
        }
        for(int k= 0;k < T; k++){
        	//generate some random number
            int x = rand()%numberOfRows;
            int y = rand()%200;
            int sum = 0;
            //if it is the lower row of the processor one,it needs a communication with second processor highest row
             if(world_rank==1){
                MPI_Send((copy[numberOfRows-1]), 1*200, MPI_INT, 2, 0, MPI_COMM_WORLD);  
                MPI_Recv(receivedarray2, 200, MPI_INT, 2, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            //if it is the upper row of the processor last,it needs a communication with last_processor-1 processor highest row    
            }else if(world_rank == world_size-1){
                MPI_Send((copy[0]), 1*200, MPI_INT, world_size -2, 0, MPI_COMM_WORLD);
                MPI_Recv(receivedarray1, 200, MPI_INT, world_size-2, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);//if it is the lowest row of the processor one,it needs a communication with second processor highest row
            //if it is the upper row of the processor last,it needs a communication with last_processor-1 processor highest row    
            }else{
                MPI_Send((copy[0]), 1*200, MPI_INT, world_rank-1, 0, MPI_COMM_WORLD);
                MPI_Recv(receivedarray1, 200, MPI_INT, world_rank-1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
               	MPI_Send((copy[numberOfRows-1]), 1*200, MPI_INT, world_rank+1, 0, MPI_COMM_WORLD);
                MPI_Recv(receivedarray2, 200, MPI_INT, world_rank+1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            }//In this clause ,program sum the around of the candiate coordinate        
            if(x == 0 || x == numberOfRows-1){
                if(world_rank == 1){//if candidate coordinate is in the first processor
                    if(x==numberOfRows-1){//if it is lower line of first processor
                    	sum+=copy[x-1][y-1]+copy[x-1][y]+copy[x-1][y+1];
                    	sum+=copy[x][y-1]+copy[x][y+1];
                        sum += receivedarray2[y-1] + receivedarray2[y] + receivedarray2[y+1];
                    }else if(x==0){//if it is upper line of the first processor
                    	sum+=copy[x+1][y-1]+copy[x+1][y]+copy[x+1][y+1];
                    	sum+=copy[x][y-1]+copy[x][y+1];						
                    }
                }else if(world_rank == world_size-1){//if the candidate is in the lowest processor
                    if(x==0){//if it is upper line of the last processor
                        sum+=copy[x+1][y-1]+copy[x+1][y]+copy[x+1][y+1];
                    	sum+=copy[x][y-1]+copy[x][y+1];
						sum+=receivedarray1[y-1] + receivedarray1[y] + receivedarray1[y+1];       
                    }else if(x==numberOfRows-1){//if it is lower line of last processor
                    	sum+=copy[x-1][y-1]+copy[x-1][y]+copy[x-1][y+1];
                    	sum+=copy[x][y-1]+copy[x][y+1];                        
                    }
                }else{//if it is the middle processors
                    if(x == 0){//if it is upper line of the middle processor
                        sum+=copy[x+1][y-1]+copy[x+1][y]+copy[x+1][y+1];
                    	sum+=copy[x][y-1]+copy[x][y+1];
						sum+=receivedarray1[y-1] + receivedarray1[y] + receivedarray1[y+1];                    	
						 }
                    else{//if it is upper line of the middle processor
                    	sum+=copy[x-1][y-1]+copy[x-1][y]+copy[x-1][y+1];
                    	sum+=copy[x][y-1]+copy[x][y+1];
                        sum += receivedarray2[y-1] + receivedarray2[y] + receivedarray2[y+1];
                    }
                }
            }else{//if the candidate coordinate is in the middle of the processor bloks
                for(int i= x-1;i<x+2;i++){
                    for(int j = y-1; j< y+2;j++){
                        sum += copy[i][j];
                    }
                }
                sum = sum - copy[x][y];
            }
            int acc = (-2)*gamma*subarr[x][y]*copy[x][y] + (-2)*beta*copy[x][y]*sum;
            int a = rand();
            //compare if it is for being negative or not
            if(log(a)<exp(acc)){
                copy[x][y] = -1*copy[x][y];
                }	
        }//send to master processor
        MPI_Send(&(copy[0][0]), numberOfRows*200, MPI_INT, 0, 1, MPI_COMM_WORLD);
	}
    MPI_Finalize();}
}
