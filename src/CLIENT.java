import java.io.Serializable;
import java.util.LinkedList;

public class CLIENT implements Serializable{
	String userName;
	String id;
	String email;
	String phone;
	int bookNum;
	LinkedList<BOOK> bookList = new LinkedList<BOOK>();
	
	public CLIENT (String userName,String phone,String id,String email){
		this.userName = userName;
		this.bookNum  = 0;
		this.phone    = phone;
		this.id       = id;
		this.email    = email;
	}

	public void printClient(){
		System.out.printf("%s (%s)\tReading book: %d\n",userName,id,bookNum);
		System.out.printf("Phone: %s  E-Mail: %s\n",phone,email);
	}

	public void lendBook(BOOK e){
		this.bookNum++;
		this.bookList.add(e);
	}
	
	public void returnBook(BOOK e){
		this.bookNum--;
		this.bookList.remove(e);
	}
	public void printBookList (){
		
		for(int i = 0 ; i < this.bookList.size();i++){
			System.out.printf("%d - %s\n",i+1,bookList.get(i).bookName);
		}
	}
}
