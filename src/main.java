import java.util.*;
import java.io.*;
import java.nio.file.attribute.UserPrincipalLookupService;
	
public class main {
	
	public static final int NumCanBorrow = 5;
	private static Scanner scanner = new Scanner(System.in);
	private static BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
	
	// Main function
	public static void main(String[] args) throws Exception {
		
		LinkedList<BOOK> lib = new LinkedList<BOOK>();
		LinkedList<CLIENT> user = new LinkedList<CLIENT>();
				
		//Load data from file
		lib = loadBookData(lib);
		user = loadUserData(user);
		
		boolean end = false;
		while(!end){
			switch(menu()){
				case 1 :
					System.out.println("Add book");
					addBook(lib);
					break;
				case 2 :
					System.out.println("Add user");
					addUser(user);
					break;
				case 3 :
					System.out.println("Inquire book");
					InquireBook(lib);
					break;
				case 4 :
					System.out.println("Inquire user");
					inquireUser(user);
					break;
				case 5 :
					System.out.println("Delete book");
					deleteBook(lib);
					break;
				case 6 :
					System.out.println("Delete user");
					deleteUser(user);
					break;
				case 7 :
					System.out.println("Modify book info");
					modifyBook(lib);
					break;
				case 8 :
					System.out.println("Modify user info");
					modifyUser(user);
					break;
				case 9 :
					System.out.println("Borrow");
					lendBook(lib,user);
					break;
				case 10 :
					System.out.println("Return");
					returnBook(lib,user);
					break;
				case 11 : 
					System.out.println("Dump all book info");
					printAllBook(lib);
					break;
				case 12 :
					System.out.println("Dump all user info");
					printAllUser(user);
					break;
				case 13 :
					System.out.println("reservation");
					reserved(lib,user);
					break;
				case -1 :
					end = true;
					break;
				default:
					System.out.println("\nERROR!PLEASE RETRY");
			}
			saveBookData(lib);
			saveUserData(user);
		}
		
		System.out.println("==SAVE DATA AND EXIT SYSTEM==");
	}

	public static int menu() throws IOException {
		// print menu
		// input void 
		// output int action number
		System.out.println("\n-------------------MENU------------------");
		System.out.println(" 1:Add book \t\t 2:Add user");
		System.out.println(" 3:Inquire book\t\t 4:Inquire user");
		System.out.println(" 5:Delete book\t\t 6:Delete user");
		System.out.println(" 7:Modify book\t\t 8:Modify user");
		System.out.println(" 9:Borrow\t\t10:Return");
		System.out.println("11:Dump all book\t12:Dump all user");
		System.out.println("13:Reservation");
		System.out.println("-1:End System");
		System.out.print("Action >> ");
		
		//不論輸入和何值都不會break
		try {
			return scanner.nextInt();
		}catch (Exception e) {
			scanner.next(); //清空buffer
			return 0 ;
		}
	}
	
	public static LinkedList<BOOK> loadBookData(LinkedList<BOOK> lib){
		//Load Book Data from file
		try {
		    FileInputStream fin = new FileInputStream("libs.dat");
		    ObjectInputStream ois = new ObjectInputStream(fin);
		    lib = (LinkedList<BOOK>)ois.readObject();
		    ois.close();
		    fin.close();
		    }
		  catch (Exception e) { e.printStackTrace(); }
		return lib;
	}
	
	public static LinkedList<CLIENT> loadUserData(LinkedList<CLIENT> user){
		//Load User data from file
		try {
		    FileInputStream fin = new FileInputStream("users.dat");
		    ObjectInputStream ois = new ObjectInputStream(fin);
		    user = (LinkedList<CLIENT>)ois.readObject();
		    ois.close();
		    fin.close();
		    }
		  catch (Exception e) { e.printStackTrace(); }
		return user;
	}
	
	public static void saveBookData(LinkedList<BOOK> lib){
		//Save Book data to file
		try {
			FileOutputStream fout = new FileOutputStream("libs.dat");
		    ObjectOutputStream oos = new ObjectOutputStream(fout);
		    oos.writeObject(lib);
		    oos.close();
		    }
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void saveUserData(LinkedList<CLIENT> user){
		//Save User data to file
		try {
			FileOutputStream fout = new FileOutputStream("users.dat");
		    ObjectOutputStream oos = new ObjectOutputStream(fout);
		    oos.writeObject(user);
		    oos.close();
		    }
		catch (Exception e) { e.printStackTrace(); }
	}

	public static String promptInputMsg(String msg)throws IOException{
		//Print input str 
		//Return String User input
		System.out.print(msg+" >");
		return (String)buf.readLine();
	}
	
	public static boolean makesure(String str){
		//print input str and if user type "Y" then return true
		//else return false
		System.out.println(str);
		if(scanner.next().equals("Y")){
			return true;
		}else{
			return false;
		}
	}
	
	public static void addBook(LinkedList<BOOK> lib)throws IOException {
		//add book 
		//search book by book name 
		//if the book not exist then get more info and add to library
		//if the book exist then break
		
		String bookName = promptInputMsg("BOOK NAME :");
		int bookID = findBook(lib,bookName);
		if (bookID == -1){
			String publisher = promptInputMsg("Publisher :");
			String author    = promptInputMsg("Author :");
			System.out.print("Price :");
			BOOK newBook = new BOOK(bookName,publisher,author,scanner.nextInt());
			lib.add(newBook);
			System.out.printf("This book <%s> has been added\n",bookName);
			newBook.printBook();
		}else{
			System.out.printf("This book <%s> already in the library\n",bookName);
			lib.get(bookID).printBook();
		}
	}
	
	public static void addUser(LinkedList<CLIENT> user)throws IOException{
		
		String userName = promptInputMsg("User name :");
		
		if(findUserByName(user,userName) >= 0 ){
			System.out.println("The user "+userName+" has existed");
		}else{
			String id = promptInputMsg("User ID :");
			String phone = promptInputMsg("User phone :");
			String email = promptInputMsg("User email :");
			CLIENT newUser = new CLIENT(userName,phone,id,email);
			user.add(newUser);
			System.out.printf("Add user <%s> successful\n",userName);
			newUser.printClient();
			System.out.println();
		}
	}
	
	public static void deleteBook (LinkedList<BOOK> lib)throws IOException{
		
		String bookName = promptInputMsg("BOOK name want to delete : ");
		
		int index = findBook(lib,bookName);
		
		if(index >= 0 ){
			if (makesure("Delete book <" + bookName + "> (Y/N)?")){
				lib.remove(index);
				System.out.println("Delete book <"+bookName+"> from libary");
			}
		}
	}

	public static void deleteUser(LinkedList<CLIENT> user){
		
		System.out.println("User ID want to delete : ");
		String userID = (String)scanner.next();
		
		int index = findUser(user,userID);
		
		if(index >= 0 ){
			String userName = user.get(index).userName;
			if(user.get(index).bookNum!=0){
				System.out.printf("Please return all book,then you can delete user <%s>",userName);
			}else{
				if (makesure("Delete user " + userName + " ? (Y/N)")){
					user.remove(index);
					System.out.println("Delete user <"+userName+"> from member list");
				}	
			}
		}else{
			System.out.println("Can't find this user,please retry");
		}
	}
	
	public static void modifyBook(LinkedList<BOOK> lib)throws IOException{
		
		String bookName = promptInputMsg("Book Name :");
		
		int index = findBook(lib,bookName);
		
		if(index >= 0 ){
			System.out.println("Which item you want to modify ? ");
			System.out.println("1.Author 2.Publiser 3.Price >> ");
			
			switch((int)scanner.nextInt())
			{
				case 1 :
					String newAuthor = promptInputMsg("new author: ");
					lib.get(index).author = newAuthor;
					System.out.println("Successfully modified");
					break;
				case 2 :
					String newPublisher = promptInputMsg("new publiser: ");
					lib.get(index).publisher = newPublisher;
					System.out.println("Successfully modified");
					break;
				case 3 : 
					System.out.print("new price: ");
					lib.get(index).price = scanner.nextInt();
					System.out.println("Successfully modified");
					break;
				default:
					System.out.println("Something error,pls retry");
			}
		}else{
			System.out.println("The book not exit,please retry");
		}
	}
	
	public static void modifyUser(LinkedList<CLIENT> user)throws IOException{
		
		String userID = promptInputMsg("User ID: ");
		int index = findUser(user,userID);
		
		if(index >= 0 ){
			System.out.println("Which item you want to modify ? ");
			System.out.println("1.Name 2.Phone 3.E-mail >> ");
			
			switch((int)scanner.nextInt())
			{
				case 1 :
					String newUserName = promptInputMsg("Modify user name: ");
					user.get(index).userName = newUserName;
					System.out.println("Successfully modified");
					break;
				case 2 :
					String newUserPhone = promptInputMsg("Modify user phone: ");
					user.get(index).phone = newUserPhone;
					System.out.println("Successfully modified");
					break;
				case 3 : 
					String newUserEmail = promptInputMsg("Modify user E-mail: ");
					user.get(index).phone = newUserEmail;
					System.out.println("Successfully modified");
					break;
				default:
					System.out.println("Something error,pls retry");
			}
		}else {
			System.out.println("Something error,pls retry");
		}
	}
	
	public static int findBook(LinkedList<BOOK> lib,String bookName){
		// Search book by book name
		// if the book exist then return the book index in library
		// if not exist return -1
		int index = -1;
		for (int i = 0; i<lib.size();i++){
			if(lib.get(i).searchBookByName(bookName)){
				index = i ;
				break;
			}
		}
		return index;
	}

	public static int findUser(LinkedList<CLIENT> user,String userID){
		
		int index = -1 ;
		for(int i=0 ; i<user.size();i++){
			if(user.get(i).id.equals(userID)){
				index = i ; 
				break;
			}
		}
		return index;
	}

	public static int findUserByName(LinkedList<CLIENT> user,String userName){
		int index = -1 ;
		for(int i=0 ; i<user.size();i++){
			if(user.get(i).userName.equals(userName)){
				index = i ; 
				break;
			}
		}
		return index;
	}

	public static void printAllBook (LinkedList<BOOK> lib){
		
		System.out.println("----------------BOOK LIST----------------");
		for (int i = 0 ; i < lib.size() ;i++){
			lib.get(i).printBook();
		}
		if(lib.size() == 0 ){
			System.out.println("\t\tNull");
		}else{
			System.out.println(">>>Total book number: "+lib.size());
		}
		System.out.println("----------------END LIST-----------------");
		System.out.println();
	}
	
	public static void printAllUser (LinkedList<CLIENT> user){
		
		System.out.println("----------------USER LIST----------------");
		for(int i=0;i<user.size();i++){
			user.get(i).printClient();
		}
		if(user.size() == 0 ){
			System.out.println("\t\tNull");
		}else{
			System.out.println(">>>Total user number: "+user.size());
		}
		
		System.out.println("----------------END LIST-----------------");
		System.out.println();
	}

	public static int includeKey(LinkedList<BOOK> lib,String key,int item) {
		
		//find if book name,publisher,author contain keyword
		//then print the book info that they want to find
		int index = -1;
		switch(item)
		{
		case 1:
			for (int i = 0 ; i<lib.size();i++){
				if(lib.get(i).bookName.toLowerCase().contains(key)){
					index= i;
					lib.get(i).printBook();
				}
			}
			break;
		case 2:
			for (int i = 0 ; i<lib.size();i++){
				if(lib.get(i).publisher.toLowerCase().contains(key)){
					index= i;
					lib.get(i).printBook();
				}
			}
			break;
		case 3:
			for (int i = 0 ; i<lib.size();i++){
				if(lib.get(i).author.toLowerCase().contains(key)){
					index= i;
					lib.get(i).printBook();
				}
			}
			break;
		default:
			System.out.println("Somthing error");
		}
		return index;
	}
	
	public static void InquireBook (LinkedList<BOOK> lib)throws IOException{
		
		System.out.println("Search item");
		System.out.print("1.Book Name 2.Publisher 3.Author   >");
		int x =  (int)scanner.nextInt();
		String key = promptInputMsg("Search(case-insensitive) :").toLowerCase();
		
		includeKey(lib,key,x);
	}
	
	public static void inquireUser (LinkedList<CLIENT> user)throws IOException{
		
		int userIndex = findUser(user,promptInputMsg("User ID"));
		
		if(userIndex >= 0){
			CLIENT auser = user.get(userIndex);
			auser.printClient();
			System.out.println("BOOK LIST");
			auser.printBookList();
		}else{
			System.out.println("Something error,pls retry");
		}
	}

	public static void lendBook(LinkedList<BOOK> lib,LinkedList<CLIENT> user)throws IOException{
		
		// Get user info
		String userID = promptInputMsg("User ID :"); 
		int userIndex = findUser(user,userID);
		
		if (userIndex >= 0 ){
			// Get book info
			String bookName = promptInputMsg("Book Name :");
			int bookIndex = findBook(lib,bookName);
			
			if (bookIndex >= 0){
				if (!lib.get(bookIndex).checked){
					if (user.get(userIndex).bookNum < NumCanBorrow){
						//Borrow
						lib.get(bookIndex).lend(user.get(userIndex).userName);
						user.get(userIndex).lendBook(lib.get(bookIndex));
						System.out.printf("<%s> borrowed <%s>.\n",user.get(userIndex).userName,lib.get(bookIndex).bookName);
					}else{
						System.out.println("Over the upper limit");
					}
				}else{
					//Reservation
					if(lib.get(bookIndex).nextUser==null){
						lib.get(bookIndex).reservation(user.get(userIndex));
					}else{
						System.out.println("Has reserved");
					}
				}
			}else{
				System.out.println("Book not exist, please retry");
			}
		}else {
			// User not exist
			System.out.println("User not exist");
		}
	}

	public static void returnBook(LinkedList<BOOK> lib,LinkedList<CLIENT> user)throws IOException{
		
		String bookName = promptInputMsg("Book Name :");
		int bookIndex = findBook(lib,bookName);
		String userID = promptInputMsg("User ID :");
		int userIndex = findUser(user,userID);
		
		if (bookIndex >=0 && userIndex >= 0){
			if(lib.get(bookIndex).user.equals(user.get(userIndex).userName)){
				lib.get(bookIndex).return_book();
				user.get(userIndex).returnBook(lib.get(bookIndex));
				System.out.printf("<%s> returned <%s>.\n",user.get(userIndex).userName,lib.get(bookIndex).bookName);
			}else{
				System.out.println("Not the borrower");
			}
		}else{
			System.out.println("Something error,pls retry");
		}
	}
	
	public static void reserved (LinkedList<BOOK> lib,LinkedList<CLIENT> user)throws IOException{
		String userID = promptInputMsg("User ID :");
		int userIndex = findUser(user,userID);
		
		if (userIndex >= 0){
			String bookName = promptInputMsg("Book Name :");
			int bookIndex = findBook(lib,bookName);
			if (bookIndex >= 0 ){
				if (lib.get(bookIndex).checked){
					//如果書籍被借出 則預約
					if (lib.get(bookIndex).nextUser==null){
						lib.get(bookIndex).reservation(user.get(userIndex));
					}else{
						System.out.println("Has reserved");
					}
				}else{
					System.out.println("Book in LIB,you can borrow the book");
				}
			}else{
				System.out.println("Book not exist");
			}
			
		}else{
			System.out.println("User not exist");
		}
	}
}


