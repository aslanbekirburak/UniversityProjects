public class DBMS {
		
	public static void main(String[] args) {
		try {
		SystemCatalog systemCatalog = new SystemCatalog();
		systemCatalog.menu();
		}
		catch(Throwable e) {
			System.err.println("An unexpected error has occurred:");
			e.printStackTrace();
			System.exit(1); 
		}
}
	
	}
