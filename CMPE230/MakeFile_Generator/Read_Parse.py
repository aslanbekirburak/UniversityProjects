# -*- coding: utf-8 -*-
import os
import re
import sys
c=[]#path of c files
c1=[]#name of c files
h=[]#path of h files
h1=[]#name of h files
h_copy = []#used for checking unused method so ı copy the h names
bba=[]#used for which c files has which h files
from collections import deque
dosya = open('makefile.txt','w')
#I check the extension of the file then send them to the lists.
#each c and h file have seperate name and path
def Check_for_extension(name,path):
    if path.endswith('.c'):
        c.append(path)
        c1.append(name)
    elif path.endswith('.h'):
        h.append(path)
        h_copy.append(name)
        h1.append(name)
#I parse the directory according to '/'.Then ı check the last element of directory.
def Parse_Path(currentdir):
    your_path = currentdir
    fixed_path = currentdir
    path_list = your_path.split('/')
    Check_for_extension(path_list[-1],fixed_path)
#Read the C Files
def Read_C_file(path):

   for i in range(0,path.__len__()):
       with open(path[i]) as myfile:
         readers=myfile.readlines()
         Parse(readers)
#examine the c file lines and take the which .h files used
def Parse(list):
    str = ''.join(list)
    regex=re.compile('#include[\s+]"(.*?)"')
    matches=re.findall(regex,str)
    Compare_header(matches)
    header_list=Assign_name_to_path(matches)
    Check_for_h_file(matches,header_list)
#Main aim of this method is to check that there is an unused fnction or not.
# İf c files has an element in the directory delete the element.If any element is in the list give Warning
def Compare_header(matches):
    for item in range(0,matches.__len__()):
        if (matches[item] in h_copy):
            h_copy.remove(matches[item])
#I take the path and names in different list so ı need to match the path and name
def Assign_name_to_path(matches):
    list_header=[]
    for i in range(0,h1.__len__()):
        for j in range(0,matches.__len__()):
            if (matches[j] in h[i]):
                list_header.append(h[i])
    return list_header
#checking for h files are in the directory,if it is not in the directory give an error,if it is in the directory append them to a list
def Check_for_h_file(matches,header_list):
    checker=True
    for i in matches:
        if(i not in h1):
            print "ERROR Your "+i+" header file not found"
            checker=False
            exit()
    if(checker==True):
        bba.append(header_list)

def Makefile_exe():#used for write program exe lines
    program="program.exe:\t"
    program1=""
    for item in c1:
        program1+=re.search('(.*).c',item).group(1)+'.o'+" "
    program=program+program1
    print program
    dosya.write(program+"\n")
    print "\t\tgcc "+program1+" -o program"
    dosya.write("\t\t\tgcc "+program1+" -o program"+"\n")

def Makefile_objects():#used for write program objects lines
    if(h_copy.__len__()==0):
        Makefile_exe()
        for i in range(0,bba.__len__()):
            program = ""
            program1 = ""
            program+= (re.search('(.*).c', c1[i]).group(1) + '.o') + ":\t"+c[i]+" "
            for k in bba[i]:
                program1+=k+" "
            program+=program1
            print program
            print "\t\t" + "gcc -c -I " + c[i]
            dosya.write(program+"\n")
            dosya.write("\t\t\t" + "gcc -c -I " + c[i]+"\n")
    else:
        dosya.write("WARNING "+h_copy[0]+" elements not used")
        print "WARNING "+h_copy[0]+" elements not used"

qlist = deque([sys.argv[1]])
while qlist:
    currentdir = qlist.popleft()
    dircontents = os.listdir(currentdir)
    for name in dircontents:
        currentitem = currentdir + "/" + name
        if os.path.isdir(currentitem):
            qlist.append(currentitem)
        else:
            Parse_Path(currentitem)
Read_C_file(c)
Makefile_objects()