import java.io.*;

public class SystemCatalog {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader in = new BufferedReader(input);
    
    //construction of the class
  public SystemCatalog() throws IOException{
    	
    	File f = new File("SystemCatalog.txt");  //Creates SystemCatalog file
    	boolean success = (new File("DataFiles")).mkdir();
    	   
    	  try {
    		  RandomAccessFile out = new RandomAccessFile(f, "rw");
    		  out.seek(0);
    		  if(success)
    		     out.writeInt(0);    //Writes number of types as zero
    	  } catch (IOException e) {
  			e.printStackTrace();
    	  } 	
    }
  public void menu2() {
  	try {
  		//Prints operations
  		System.out.println("\nRecord Operations:");
	        System.out.println("(1) Insert a record");
	        System.out.println("(2) Delete a record");
	        System.out.println("(3) Update a record");
	        System.out.println("(4) List all records");
	        System.out.println("(5) Search for a record");
	        System.out.println("\n(6) Exit");
	        System.out.println();
	        int choice = Integer.parseInt(in.readLine());
	        System.out.println();
	        if(choice == 1) {
	        	InsertRecord();
	        	menu2();
	        }
	        else if(choice == 2) {
	        	DeleteRecord();	
	        	menu2();
	        }
	        else if(choice == 3) {
	        	UpdateRecord();	
	        	menu2();
	        }
	        else if(choice == 4) {
	        	ListRecords();
	        	menu2();
	        }
	        else if(choice == 5) {
	        	SearchRecord();
	        	menu2();
	        }
	        else if(choice == 6) {
	        	System.out.println("System is closed!");
	        	return;
	        }
	        else 
		        menu2();
	        
  	} catch (IOException e) {
			e.printStackTrace();
		}
	}	          
    /*
    public void menu() {
    	try {
    		//Prints operations
		System.out.println("Which operation you want to do?\n");
		System.out.println("Table Operations:");
        System.out.println("(1) Create a table");
        System.out.println("(2) Delete a table");
        System.out.println("(3) List all tables");
        System.out.println("\nRecord Operations:");
        System.out.println("(4) Insert a record");
        System.out.println("(5) Delete a record");
        System.out.println("(6) Update a record");
        System.out.println("(7) List all records");
        System.out.println("(8) Search for a record");
        System.out.println("\n(9) Exit");
        System.out.println();
      
        int choice = Integer.parseInt(in.readLine());
        System.out.println();
       
        //calls the corresponding methods according to choice of the user
        if(choice == 1) {
        	CreateType();
        	menu();
        }
        else if(choice == 2) {
        	DeleteType();
        	menu();
        }
        else if(choice == 3) {
        	ListTypes();
        	menu();
        }
       
        else 
	        menu();
     
    } catch (IOException e) {
		e.printStackTrace();
	}
    }*/
  public void menu()
	{
		System.out.println("which operation do you want to do?");
		 System.out.println("1-DML");
		 System.out.println("2-DDL");
			int choice;
			try {
				choice = Integer.parseInt(in.readLine());
			if(choice==1)
			{
				menu1();
			}
			else if(choice==2){
				menu2();
			}
			else{
				menu();
			}
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			
}			
	  public void menu1() {
	    	try {
	    		//Prints operations
			System.out.println("Which operation do you want to do?\n");
			System.out.println("Type Operations:");
	        System.out.println("(1) Create a type");
	        System.out.println("(2) Delete a type");
	        System.out.println("(3) List all types");
	        System.out.println("\n(4) Main Menu");
	        System.out.println();
	        int choice = Integer.parseInt(in.readLine());
	        System.out.println();
	    	 
	    	 
	        //calls the corresponding methods according to choice of the user
	        if(choice == 1) {
	        	CreateType();
	        	menu1();
	        }
	        else if(choice == 2) {
	        	DeleteType();
	        	menu1();
	        }
	        else if(choice == 3) {
	        	ListTypes();
	        	menu1();
	        }
	        else 
		        menu();}
	        catch (IOException e) {
	  			e.printStackTrace();
	  		}
	  	}	 
  
    //Decides whether the type with this name exists or not
    public boolean isType(String typeName) {
    	File dir = new File("DataFiles");
    	String[] files = dir.list();
    	for(int i = 0; i < files.length; i++) {
    		if (files[i].equals(typeName + ".txt")) 
    			return true;		
    	}
    	return false;
    }
    
    //Creates a type
    public void CreateType() {
    	System.out.println("\nName of the type:\n");
    	System.out.println();
    	try {
    		//Reads type name
            String typeName = in.readLine();
            
            boolean bool1 = false;
            boolean bool2 = false;
            
    	    while(bool1 || bool2) {
    	    	bool1 = isType(typeName);
                bool2 = typeName.length() > 10;
    	    	if(bool1)
    			  System.out.println("\nThere is a type with this name. Write another name:\n");
    		     
    		  else
    			  System.out.println("\nType name cannot be more than 10 characters. Write another name:\n");
    		  typeName = in.readLine();	
    		  
    	}   
    	  
    	    //Calls corresponding method of the Type class to make other operations on creating table
    	   Type type = new Type();
    	   boolean bool = type.CreateType(typeName);
    	   if(bool == false)
    		   return;
    	   
    	   RandomAccessFile out = new RandomAccessFile("SystemCatalog.txt", "rw");
    	   out.seek(0);
    	   int numberOfTypes = out.readInt();
    	   numberOfTypes++;
    	   out.seek(0);
    	   out.writeInt(numberOfTypes);
    	   
    	   //Adds the information of new type to System Catalog file
    	   out.seek((numberOfTypes - 1) * 232 + 4);
    	   
    	   for(int i = 0; i < typeName.length(); i++) 
    		   out.writeChar(typeName.charAt(i));
    	 
    	   for(int i = 0; i < 10 - typeName.length(); i++)
    		   out.writeChar('*');
    	   
    	   out.writeInt(0);
    	   out.writeInt(0);
    	   out.writeInt(type.NumOfFields);
    	   
    	   for(int i = 0; i < type.NumOfFields; i++) {
    	      for(int j = 0; j < type.fieldNames[i].length(); j++) 
    		   out.writeChar(type.fieldNames[i].charAt(j));
    	 
    	      for(int j = 0; j < 10 - type.fieldNames[i].length(); j++)
    		   out.writeChar('*');
    	   }
    	   for(int i = 0; i < (10 - type.NumOfFields) * 10; i++) 
    		   out.writeChar('*');
    	   
    	 out.close();
    	} catch (IOException ex) {
            ex.printStackTrace();
}
    	System.out.println("\nType is created!!!\n----------------------------------------------------------\n"); 
}
    
    //Deletes a type
    public void DeleteType() {
    	System.out.println("\nName of the type:\n");
    	try {
    		//Reads type name
            String typeName = in.readLine();
             
            while(!isType(typeName)) {
            	System.out.println("\nThere is no type with this name. Write another type name:\n");
            	System.out.println("(In order to see the list of typs press 0 and enter!)\n");
            	typeName = in.readLine();
            	if(typeName.equals("0")) {
            		ListTypes();
            		DeleteType();
            		return;
            	}
            		
            }
            
            RandomAccessFile out = new RandomAccessFile("SystemCatalog.txt", "rw");
            out.seek(0);
            
            //updates type information in System Catalog file
            int numberOfTypes = out.readInt();
            numberOfTypes--;
            out.seek(0);
            out.writeInt(numberOfTypes);
       
        	out.seek(numberOfTypes * 232 + 4);
        	
        	Byte[] typeInfo = new Byte[232];
        	
        	for(int i = 0; i < 232; i++)
        		typeInfo[i] = out.readByte();
        	
        	for(long i = 4; i <= numberOfTypes * 232 + 4; i += 232) {
        		out.seek(i);
        		int length = 0;
        		for(int j = 0; j < typeName.length(); j++) {       			
        			if(typeName.charAt(j) == out.readChar()) 
        				length++;    		
        		}
        		if(length == typeName.length() && (out.readChar() == '*' || length == 10))
        			break;
        	}
        	
        	long position = out.getFilePointer() - 2;
        	out.seek(position - 2*typeName.length());
        	
        	for(int i = 0; i < 232; i++) 
        		out.writeByte(typeInfo[i]);
            
        	   //deletes data and index files of the type
        	   File f1 = new File("DataFiles/" + typeName + ".txt");
        	   File f2 = new File(typeName + "_idx.txt");
        	   f1.delete();
        	   f2.delete();
           
       out.close();
    	} catch (IOException ex) {
            ex.printStackTrace();    	
    }
    	System.out.println("\nType is deleted!\n----------------------------------------------------------\n"); 
}
    
    //Lists all types and all information of types
    public boolean ListTypes() {
    	try {
    	  RandomAccessFile out = new RandomAccessFile("SystemCatalog.txt", "rw");
          out.seek(0);
          
          int numberOfTypes = out.readInt();
          if(numberOfTypes == 0){
        	  System.out.println("\nThere is no type in the database system!!!\nYou are redirecting to menu...\n...");
              return false;
          } else if(numberOfTypes == 1)
               System.out.println("\nThere is " + numberOfTypes + " type!\n");
          else 
        	  System.out.println("\nThere are " + numberOfTypes + " types!\n");
          for(int i = 1; i <= numberOfTypes; i++) {
        	  out.seek((i - 1) * 232 + 4);
        	  System.out.print(i + ". Type name: ");
        	 
        	  for(int j = 0; j < 10; j++) {
        		  char c = out.readChar();
        		  if(c != '*')
        			  System.out.print(c);
         		 
         	  }
        	  
        	  System.out.print("\nNumber of pages: " + out.readInt() + ", Number of records: " + out.readInt());
        	  int numOfFields = out.readInt();
        	  System.out.println(", Number of fields: " + numOfFields);
        	  System.out.println("\nPrimary Key and Field Names:");
        	  for(int j = 0; j < numOfFields; j++) {
        		  for(int k = 0; k < 10; k++) {
        			  char ch = out.readChar();
        			  if(ch != '*')
        			     System.out.print(ch);
        		  }
        		  if(j != numOfFields - 1) 
        		     System.out.print(", ");
        	  }
        	  System.out.println("\n----------------------------------------------------------\n");
        	  
          }
          
          out.close();
    	} catch (IOException ex) {
            ex.printStackTrace();    	
    }
    	return true;
    }
    public void InsertRecord() {
    	System.out.println("\nTable name:\n");
    	try {
    		
    		//reads table name
    		String typeName = in.readLine();
    		while(!isType(typeName)) {
    			System.out.println("\nThere is no table with this name. Write another table name:\n");
    			System.out.println("(In order to see the list of tables press 0 and enter!)\n");
            	typeName = in.readLine();
            	if(typeName.equals("0")) {
            		boolean bool = ListTypes();
            		if(bool) 
            		   InsertRecord();
            		return;
            	}
    		}
    		
    		//calls corresponding method of Table class for other operations on inserting a record
    		Type type = new Type();
    		type.InsertRecord(typeName);
    		
    		
    		
    		
    	} catch (IOException ex) {
            ex.printStackTrace();
}
    }
    
    //lists all records in a specific table
    public void ListRecords() {
    	System.out.println("\nTable name:\n");
    	try {
    		//reads table name
    		String typeName = in.readLine();
    		while(!isType(typeName)) {
    			System.out.println("\nThere is no table with this name. Write another table name:\n");
    			System.out.println("(In order to see the list of tables press 0 and enter!)\n");
            	typeName = in.readLine();
            	if(typeName.equals("0")) {
            		boolean bool = ListTypes();
            		if(bool)
            		  ListRecords();
            		return;
            	}
    		}
    		//calls corresponding method of Table class for other operations on listing records
    		Type type = new Type();
    		type.ListRecords(typeName);
    		
    		
    	} catch (IOException ex) {
            ex.printStackTrace();
}
    	
    }
    
    //updates a record in the specific table
    public void UpdateRecord() {
    	System.out.println("\nTable name:\n");
    	try {
    		//reads table name
    		String typeName = in.readLine();
    		while(!isType(typeName)) {
    			System.out.println("\nThere is no table with this name. Write another table name:\n");
    			System.out.println("(In order to see the list of tables press 0 and enter!)\n");
    			typeName = in.readLine();
            	if(typeName.equals("0")) {
            		boolean bool = ListTypes();
            		if(bool)
            		   UpdateRecord();
            		return;
            	}
    		}
    		//calls corresponding method of Table class for other operations on updating a record
    		Type type = new Type();
    		type.UpdateRecord(typeName);
    		
    		
    	} catch (IOException ex) {
            ex.printStackTrace();
}
    }
    
    //deletes a record in a specific table
    public void DeleteRecord() {
    	System.out.println("\nType name:\n");
    	try {
    		//reads table name
    		String typeName = in.readLine();
    		while(!isType(typeName)) {
    			System.out.println("\nThere is no type with this name. Write another type name:\n");
    			System.out.println("(In order to see the list of types press 0 and enter!)\n");
    			typeName = in.readLine();
            	if(typeName.equals("0")) {
            		boolean bool = ListTypes();
            		if(bool)
            		   DeleteRecord();
            		return;
            	}
    		}
    		//calls corresponding method of Table class for other operations on updating a record
    		Type type = new Type();
    		type.DeleteRecord(typeName);
    		
    		
    	} catch (IOException ex) {
            ex.printStackTrace();
}
    }
    
    //searches a record in a specific table
    public void SearchRecord() {
    	System.out.println("\nType name:\n");
    	try {
    		String typeName = in.readLine();
    		while(!isType(typeName)) {
    			System.out.println("\nThere is no Type with this name. Write another Type name:\n");
    			System.out.println("(In order to see the list of Types press 0 and enter!)\n");
    			typeName = in.readLine();
            	if(typeName.equals("0")) {
            		boolean bool = ListTypes();
            		if(bool)
            		   SearchRecord();
            		return;
            	}
    		}
    		//calls corresponding method of Table class for other operations on searching a record
    		Type type = new Type();
    		type.SearchRecord(typeName);
    		
    		
    	} catch (IOException ex) {
            ex.printStackTrace();
}
    
}
    
} 