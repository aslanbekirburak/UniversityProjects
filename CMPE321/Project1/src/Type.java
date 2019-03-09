import java.io.*;

public class Type {
	String typeName;
    int NumOfFields;
    int NumOfRecordsEachPage;
    int RecordSize;
    int NumOfRecords;
    int NumOfPages;
    String PrimaryKey;
    long FirstRecordLoc;
    long LastRecordLoc;
    String[] fieldNames;
    int[] fieldLengths;
    boolean foundRecord;
    long location;
    String[] fields;
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader in = new BufferedReader(input);


    public boolean CreateType(String tableName) {
          try {
        	  //reads number of fields of each record in the type
              System.out.println("\nNumber of fields in each record:\n");

              NumOfFields = Integer.parseInt(in.readLine());

 	          while(NumOfFields > 10) {
 		        System.out.println("\nNumber of fields cannot be more than 10. Write a number less than 10:\n");
 		        NumOfFields = Integer.parseInt(in.readLine());
 	   				}

 	          //reads lengths of fields
 	          RecordSize = 0;
 	          
 	       //reads names of fields
 	       fieldNames = new String[NumOfFields];
 	      fieldLengths = new int[NumOfFields];
 	       System.out.println("\nField name and Field Lenght :\n");
 	       for(int i = 1; i <= NumOfFields; i++) {
 	    	  System.out.print(i + ". field length: ");
	  		     int length = Integer.parseInt(in.readLine());
	  		     while(length > 10) {
	  			   System.out.print("\nField length cannot be more than 10. Write another length.\n\n" + i + ". field length: ");
	  			   length = Integer.parseInt(in.readLine());
	  		   }
	  		   fieldLengths[i - 1] = length;
	  		   RecordSize += length;
	  		   
 		   System.out.print(i + ". field name: ");
 		   String fieldName = in.readLine();
 		   while(fieldName.length() > 10) {
 			   System.out.print("\nField name cannot be more than 10 characters. Write another name.\n\n" + i + ". field name: ");
 			   fieldName = in.readLine();
 		   }
 		   fieldNames[i - 1] = fieldName;
 	   }
 	       PrimaryKey = fieldNames[0];
 	      RecordSize = RecordSize * 2;

 	       try {

 	    	  //writes header information to the data file
 	    	  File f1 = new File("DataFiles/" + tableName + ".txt");

 	    	  RandomAccessFile out = new RandomAccessFile(f1, "rw");

 	    	  NumOfRecordsEachPage = 512 / RecordSize;
 	    	  out.writeInt(NumOfRecordsEachPage);
 	    	  out.writeInt(RecordSize);
 	    	  out.writeLong(0);
 	    	  out.writeInt(NumOfFields);
 	    	  for(int i = 0; i < NumOfFields; i++)
 	    		 out.writeInt(fieldLengths[i]);
 	    	 for(int i = 0; i < 10 - NumOfFields; i++)
 	    		 out.writeInt(0);

 	    	  for(int i = 0; i < NumOfFields; i++) {
 	    		  for(int j = 0; j < fieldNames[i].length(); j++)
 	    		      out.writeChar(fieldNames[i].charAt(j));

 	    		 for(int j = 0; j < 10 - fieldNames[i].length(); j++)
	    		      out.writeChar(' ');
 	    	  }

 	    	 for(int i = 0; i < (10 - NumOfFields) * 10; i++)
 	    		   out.writeChar(' ');

 	    	 //writes header information to the index file
 	    	 File f2 = new File (tableName + "_idx.txt");
 	    	 RandomAccessFile out2 = new RandomAccessFile(f2, "rw");
 	    	 out2.writeInt(0);
 	    	 out2.writeInt(2 * fieldLengths[0] + 8);
 	    	 out.close();
 	    	 out2.close();
 	       }
    	  catch (FileNotFoundException ex) {
               ex.printStackTrace();
           } catch (IOException ex) {
               ex.printStackTrace();
 } }   catch (IOException ex) {
     ex.printStackTrace();
    }
       catch (NumberFormatException num) {
    	   System.out.println("\nError! You have writen a character. You are redirecting to menu...\n...\n");
    	   return false;
       }
          return true;

}
    
    public RandomAccessFile RandomAccFileCreate(String typeName,String type){
    	File f1 = new File(typeName + ".txt");
    	try {
			return new RandomAccessFile(f1,type);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return  null;
    }

    public void InsertRecord(String typeName) {
        this.typeName = typeName;
		System.out.println("\nFields:");
		try {
			//File f1 = new File("DataFiles/" + tableName + ".txt");
			//new RandomAccessFile(f1, "rw");
			RandomAccessFile out = RandomAccFileCreate("DataFiles/" + typeName,"rw");

			
			ReadTableHeader(typeName);

			fields = new String[NumOfFields];

			//searches primary key in the index file and determines the location where the record will be inserted to

			//File f2 = new File(tableName + "_idx.txt");
			RandomAccessFile out2 = RandomAccFileCreate(typeName+ "_idx","rw");
			out2.seek(0);
			NumOfRecords = out2.readInt();
			int recordSize = fieldLengths[0] * 2 + 8;
			System.out.println("\nWrite primary key (" + fieldNames[0] + "): ");
			location = 8;
			fields[0] = in.readLine();
			BinarySearch(0, NumOfRecords - 1, fields[0], recordSize);
			boolean bool = fields[0].length() != fieldLengths[0];

			while(foundRecord || bool) {
				if(foundRecord)
				    System.out.println("\nThis primary key exists. Write another primary key:\n");
				else
					System.out.println("\nPrimary key length has to be " + fieldLengths[0] + ".\nWrite another field with length " + fieldLengths[0] + ":");
				fields[0] = in.readLine();
				bool = fields[0].length() != fieldLengths[0];
			    BinarySearch(0, NumOfRecords - 1, fields[0], recordSize);
			}

			//records which are location after the inserted record moves by 1 record size
			for(long i =  (NumOfRecords - 1) * (recordSize) + 8; i >= location; i = i - recordSize) {
				   out2.seek(i);
				   Byte[] b = new Byte[recordSize];
				   for(int j = 0; j < recordSize; j++) {
					   b[j] = out2.readByte();
				   }
				   for(int j = 0; j < recordSize; j++) {
					   out2.writeByte(b[j]);
				   }
			   }

			//writes primary key to the index file
			out2.seek(location);
			for(int i = 0; i < fields[0].length(); i++)
				out2.writeChar(fields[0].charAt(i));

			//change the last record location address
			if(LastRecordLoc == 0) {
				LastRecordLoc = 260;
			    out.seek(260);
			}
			else {
			out.seek(LastRecordLoc + RecordSize);
			LastRecordLoc += RecordSize;
			}

			//writes fields of the new record to data file
			for(int i = 0; i < fieldLengths[0]; i++) {
				out.writeChar(fields[0].charAt(i));
			}

	    	for(int i = 1; i < NumOfFields; i++) {
		 	    System.out.print("\n" + fieldNames[i] + ": ");
		 		String field = in.readLine();

		 		while(field.length() != fieldLengths[i]) {
		 			System.out.println("\nField length has to be " + fieldLengths[i] + ".\nWrite another field with length " + fieldLengths[i] + ":\n");
		 			field = in.readLine();
		 		   }
		 		   fields[i] = field;
		 		   for(int j = 0; j < fieldLengths[i]; j++)
		 			   out.writeChar(fields[i].charAt(j));
		 	   }

	    	//increments number of records
	    	out.seek(8);
	    	out.writeLong(LastRecordLoc);
	    	out2.writeLong(LastRecordLoc);
	    	out2.seek(0);
	    	NumOfRecords++;
	    	out2.writeInt(NumOfRecords);

	    	int pageID = (NumOfRecords - 1) / NumOfRecordsEachPage + 1;

	    	System.out.println("\nThe record is inserted to page #" + pageID + " of table " + typeName + "!!!\n----------------------------------------------------------\n");
	    	UpdateSystemCatalog();
	    	out.close();
	    	out2.close();


		} catch (IOException ex) {
		     ex.printStackTrace();
	    }

    }

    //searches the primary key in the index file by binary search
    //if finds it, foundRecord = true; otherwise foundRecord = false
    //location where the record will be inserted to or deleted from
    public void BinarySearch(long min, long max, String PrimaryKey, int recordSize) {
 	   try {
 		   File f = new File(typeName + "_idx.txt");
 		   RandomAccessFile index = new RandomAccessFile(f, "r");
            if(max < min) {
         	   foundRecord = false;
            }
            else      {
              long mid = (min + max) / 2;
              long mid2 = mid * recordSize + 8;
              index.seek(mid2);

              String str = "";
              for(int i = 0; i < (recordSize - 8)/ 2; i++)
            	  str += index.readChar();

              if (str.compareTo(PrimaryKey) > 0) {
             	location = mid2;
                BinarySearch(min, mid-1, PrimaryKey, recordSize);
              }
              else if (str.compareTo(PrimaryKey) < 0) {
             	location = mid2 + recordSize;
             	BinarySearch(mid+1, max, PrimaryKey, recordSize);
              }
              else {
             	foundRecord = true;
                location = mid2;
              }
            }
            index.close();
 	   } catch(IOException e) {
 		   e.printStackTrace();
 	   }
    }

    public void ListRecords(String tableName) {
    	try {

    		File f2 = new File("DataFiles/" + tableName + ".txt");
    		RandomAccessFile out = new RandomAccessFile(f2, "r");

    		ReadTableHeader(tableName);

			out.seek(260);
			long count = out.getFilePointer();

			if(LastRecordLoc == 0 || LastRecordLoc == count - RecordSize) {
				System.out.println("There is no record in this table. You are redirecting to menu\n...\n\n");
				return;
			}
			else {
			//prints all records in the table with page numbers
			System.out.println("\n");
			while(count <= LastRecordLoc) {
				long pageNum = (count - 260) / RecordSize / NumOfRecordsEachPage + 1;
				System.out.print("Page #" + pageNum + ": ");
				System.out.print((count - 260) / RecordSize + 1 +". Record: ");
				for(int i = 0; i < NumOfFields; i++) {
					System.out.print(fieldNames[i] + ": ");
					for(int j = 0; j < fieldLengths[i]; j++)
						System.out.print(out.readChar());
					if(i != NumOfFields - 1)
					   System.out.print(", ");
				}
				System.out.println();
				count = out.getFilePointer();
			}
			System.out.println("\n----------------------------------------------------------\n");


			}

    	} catch(IOException e) {
  		   e.printStackTrace();
  	   }


    }


    public void UpdateRecord(String tableName) {
    	this.typeName = tableName;
    	try {

    		ReadTableHeader(tableName);

    		if(LastRecordLoc == 0) {
				System.out.println("There is no record in this table. You are redirecting to menu\n...\n\n");
				return;
			}

    		File f1 = new File(tableName + "_idx.txt");
    		RandomAccessFile index = new RandomAccessFile(f1, "r");
    		File f2 = new File("DataFiles/" + tableName + ".txt");
    		RandomAccessFile data = new RandomAccessFile(f2, "rw");

    		System.out.println("\nWrite primary key of the record you want to update:\n");
    		String primaryKey = in.readLine();

    		index.seek(0);
    		NumOfRecords = index.readInt();
    		int recordSize = index.readInt();

    		//searches the record in the index file
    		BinarySearch(0, NumOfRecords - 1, primaryKey, recordSize);

    		while(foundRecord == false) {
    			System.out.println("\nThe primary key is not found. Write another primary key:\n");
    			System.out.println("(In order to see the list of records press 0 and enter!)\n");
    			primaryKey = in.readLine();
    			if(primaryKey.equals("0")) {
    				ListRecords(tableName);
    				UpdateRecord(tableName);
    				return;
    			}

    			BinarySearch(0, NumOfRecords - 1, primaryKey, recordSize);
    		}

    		index.seek(location + recordSize - 8);
    		long address = index.readLong();

    		//goes to location of the record in the data file
    		//prints fields of old record
    		data.seek(address);
    		System.out.println("\nThe record you want to update is in page #" + ((address - 260) / RecordSize / NumOfRecordsEachPage + 1));
    		System.out.println("Fields of the record: ");
    		for(int i = 0; i < NumOfFields; i++) {
    			System.out.print(fieldNames[i] + ": ");
    			for(int j = 0; j < fieldLengths[i]; j++)
    				System.out.print(data.readChar());
    			System.out.print(", ");
    		}
    		System.out.println();


    		System.out.println("\nWrite new fields without primary key:\n");
            data.seek(address + fieldLengths[0]*2);

            //reads new fields
            for(int i = 1; i < NumOfFields; i++) {
            	System.out.print("\n" + fieldNames[i] + ":");
            	String str = in.readLine();
            	while(str.length() != fieldLengths[i]) {
            		System.out.println("\nLength of the field has to be " + fieldLengths[i] + " character.\nWrite a field which has " + fieldLengths[i] + " characters.");
            	    str = in.readLine();
            	}
            //writes new fields
            	for(int j = 0; j < fieldLengths[i]; j++) {
            		char c = str.charAt(j);
            		data.writeChar(c);
            	}
            }

    		System.out.println("\nRecord is updated!!!\n----------------------------------------------------------\n");

    	} catch(IOException e) {
   		   e.printStackTrace();
   	   }
    }

    //reads the information on the table header
    public void ReadTableHeader(String typeName) {
    	try {

    		//File f2 = new File("DataFiles/" + typeName + ".txt");
    		RandomAccessFile out =  RandomAccFileCreate("DataFiles/" + typeName,"r");//new RandomAccessFile(f2, "r");

    		out.seek(0);
    		NumOfRecordsEachPage = out.readInt();
			RecordSize = out.readInt();
			LastRecordLoc = out.readLong();
			NumOfFields = out.readInt();
			fieldLengths = new int[NumOfFields];
			fieldNames = new String[NumOfFields];

			for(int i = 0; i < NumOfFields; i++)
				fieldLengths[i] = out.readInt();

			out.seek(60);
			for(int i = 0; i < NumOfFields; i++) {
				fieldNames[i] = "";
				for(int j = 0; j < 10; j++) {
					char c = out.readChar();
					if(c != '*')
						fieldNames[i] += c;
				}
			}
			out.close();
    	} catch(IOException e) {
   		   e.printStackTrace();
   	   }
    }

    public void DeleteRecord(String tableName) {
    	this.typeName = tableName;
    	ReadTableHeader(tableName);
    	if(LastRecordLoc == 0) {
			System.out.println("There is no record in this table. You are redirecting to menu\n...\n\n");
			return;
		}

    	try {
    	   File f1 = new File(tableName + "_idx.txt");
    	   File f2 = new File("DataFiles/" + tableName + ".txt");
    	   RandomAccessFile index = new RandomAccessFile(f1, "rw");
    	   RandomAccessFile data = new RandomAccessFile(f2, "rw");

    	   index.seek(0);
    	   NumOfRecords = index.readInt();
    	   int recordSize = index.readInt();

    	   //reads primary key and searches it in the index file
    	   System.out.println("\nWrite primary key of the record you want to delete:\n");
    	   String primaryKey = in.readLine();

    	   BinarySearch(0, NumOfRecords - 1, primaryKey, recordSize);

    	   while(!foundRecord) {
    		   System.out.println("\nThe primary key is not found. Write another primary key:\n");
    		   System.out.println("(In order to see the list of records press 0 and enter!)\n");
   			primaryKey = in.readLine();
   			if(primaryKey.equals("0")) {
   				ListRecords(tableName);
   				DeleteRecord(tableName);
   				return;
   			}
    		   BinarySearch(0, NumOfRecords - 1, primaryKey, recordSize);
    	   }

    	   index.seek(location + fieldLengths[0]*2);
    	   long address = index.readLong();

    	   //prints the information of the record
    	   data.seek(address);
   		   System.out.println("\nThe record you want to delete is in page #" + ((address - 260) / RecordSize / NumOfRecordsEachPage + 1));
   		   System.out.println("Fields of the record: ");
   		   for(int i = 0; i < NumOfFields; i++) {
   			System.out.print(fieldNames[i] + ": ");
   			for(int j = 0; j < fieldLengths[i]; j++)
   				System.out.print(data.readChar());
   			System.out.print(", ");
   		}
   		System.out.println("\n");

   	       //records which are location after the deleted record moves by 1 record size
    	   for(long i = location + recordSize; i <= ((NumOfRecords - 1) * recordSize + 8); i += recordSize) {
    		  index.seek(i);
    		  Byte[] b = new Byte[recordSize];
    		  for(int j = 0; j < recordSize; j++)
    			   b[j] = index.readByte();
    	      index.seek(i - recordSize);
    	      for(int j = 0; j < recordSize; j++)
    	    	  index.writeByte(b[j]);
    	   }

    	   //decrements number of records and updates last record location and system catalog file
    	   NumOfRecords--;
    	   index.seek(0);
    	   index.writeInt(NumOfRecords);

    	   data.seek(LastRecordLoc);
    	   String str = "";
    	   for(int i = 0; i < fieldLengths[0]; i++)
    		   str += data.readChar();

    	   data.seek(LastRecordLoc);
    	   Byte[] b = new Byte[RecordSize];
    	   for(int i = 0; i < RecordSize; i++)
    		   b[i] = data.readByte();

    	   data.seek(address);
    	   for(int i = 0; i < RecordSize; i++)
    		   data.write(b[i]);

    	   LastRecordLoc -= RecordSize;
    	   data.seek(8);
    	   data.writeLong(LastRecordLoc);

    	   BinarySearch(0, NumOfRecords - 1, str, recordSize);

    	   index.seek(location + 2 * fieldLengths[0]);
    	   index.writeLong(address);

    	   UpdateSystemCatalog();

    	   System.out.println("\nThe record is deleted!!!\n----------------------------------------------------------\n");
    	   index.close();
    	   data.close();

    	} catch(IOException e) {
    		   e.printStackTrace();
    	   }
    }

    //updates system catalog file with new information
    public void UpdateSystemCatalog() {
    	try {
    	File f3 = new File("SystemCatalog.txt");
    	RandomAccessFile out3 = new RandomAccessFile(f3, "rw");
    	out3.seek(0);
    	int NumOfTables = out3.readInt();

    	for(long i = 4; i <= (NumOfTables-1) * 232 + 4; i += 232) {
    		out3.seek(i);
    		int length = 0;
    		for(int j = 0; j < typeName.length(); j++) {
    			if(typeName.charAt(j) == out3.readChar())
    				length++;
    		}
    		if(length == typeName.length() && (out3.readChar() == '*' || length == 10))
    			break;
    	}

    	long loc = out3.getFilePointer();
    	out3.seek(loc - 2 - 2*typeName.length() + 20);
    	int pageID = (NumOfRecords - 1)/ NumOfRecordsEachPage + 1;
    	out3.writeInt(pageID);
    	out3.writeInt(NumOfRecords);

    } catch(IOException e) {
		   e.printStackTrace();
	   }
    }

    public void SearchRecord(String tableName) {
    	this.typeName = tableName;
    	try {
     	   File f1 = new File(tableName + "_idx.txt");
     	   File f2 = new File("DataFiles/" + tableName + ".txt");
     	   RandomAccessFile index = new RandomAccessFile(f1, "rw");
     	   RandomAccessFile data = new RandomAccessFile(f2, "rw");

     	   ReadTableHeader(tableName);
     	   if(LastRecordLoc == 0) {
				System.out.println("\nThere is no record in this table. You are redirecting to menu\n...\n\n");
				return;
			}

     	   index.seek(0);
     	   NumOfRecords = index.readInt();
     	   int recordSize = index.readInt();

     	   System.out.println("\nWrite primary key you want to search:\n");

     	   //reads primary key
     	   String primaryKey = in.readLine();

     	   //asks the user which operation will be done
     	   System.out.println("\nWhich operation you want to do?\n");
     	   System.out.println("(1) See the record whose primary key is equal to your primary key");
     	   System.out.println("(2) See the record whose primary key is less than your primary key");
     	   System.out.println("(3) See the record whose primary key is more than your primary key\n");

     	   int choice = Integer.parseInt(in.readLine());

     	   BinarySearch(0, NumOfRecords - 1, primaryKey, recordSize);

     	   System.out.println();
     	   //according to choice of the user, prints the corresponding record(s) and page numbers of the record(s)
     	   //record which is equal to the given primary key
     	   if(choice == 1) {
     	     if(!foundRecord) {
     		   System.out.println("\nThe primary key is not found!!!");
     		   return;
     	     }

     	     index.seek(location + 2 * fieldLengths[0]);
     	     long address = index.readLong();
     	     data.seek(address);
     	     System.out.print("\nPage #" + ((address - 260) / RecordSize / NumOfRecordsEachPage + 1) + ": ");
     	     for(int i = 0; i < NumOfFields; i++) {
				   System.out.print(fieldNames[i] + ": ");
				   for(int j = 0; j < fieldLengths[i]; j++)
					   System.out.print(data.readChar());
				   if(i != NumOfFields - 1)
				      System.out.print(", ");
			   }
			   System.out.println("\n");

			   //records which are less than given primary key
     	   } else if(choice == 2) {
     		   index.seek(8);
     		   if(location == 8) {
     			   System.out.println("\nThere is no record whose primary key is less than your primary key!!!");
     			   return;
     		   }
     		   while(index.getFilePointer() < location) {
     			   index.skipBytes(2 * fieldLengths[0]);
     			   long address = index.readLong();
     			   data.seek(address);
     			  System.out.print("Page #" + ((address - 260) / RecordSize / NumOfRecordsEachPage + 1) + ": ");
     			   for(int i = 0; i < NumOfFields; i++) {
     				   System.out.print(fieldNames[i] + ": ");
     				   for(int j = 0; j < fieldLengths[i]; j++)
     					   System.out.print(data.readChar());
     				   if(i != NumOfFields - 1)
     				      System.out.print(", ");
     			   }
     			   System.out.println("\n");
     		   }

     		   //records which are more than given primary key
     	   } else if(choice == 3) {
     		   if(foundRecord)
     		      index.seek(location + recordSize);
     		   else
     			  index.seek(location);
     		   if(location == (NumOfRecords - 1) * recordSize + 8) {
    			   System.out.println("\nThere is no record whose primary key is more than your primary key!!!");
    			   return;
    		   }
     		   while(index.getFilePointer() < (NumOfRecords * recordSize + 8)) {
     			  index.skipBytes(2 * fieldLengths[0]);
     			  long address = index.readLong();
     			  data.seek(address);
     			 System.out.print("Page #" + ((address - 260) / RecordSize / NumOfRecordsEachPage + 1) + ": ");
     			 for(int i = 0; i < NumOfFields; i++) {
   				   System.out.print(fieldNames[i] + ": ");
   				   for(int j = 0; j < fieldLengths[i]; j++)
   					   System.out.print(data.readChar());
   				   System.out.print(", ");
   			   }
     			System.out.println("\n");
     		   }
     	   }
     	  System.out.println("\n----------------------------------------------------------\n");


    	 } catch(IOException e) {
  		   e.printStackTrace();
  	   }
    	catch (NumberFormatException num) {
     	   System.out.println("\nError! You have writen a character. You are redirecting to menu...\n...\n");
        }
    }



}
