import java.io.*;

public class BOOK implements Serializable{
	String bookName;
	int price;
	String author; 
	String publisher;
	boolean checked; //是否在館內 true借出 false館內
	String user; //借閱人
	CLIENT nextUser;
	
	//建構子
	public BOOK(String bookName,String publisher,String author,int price){
		this.bookName  = bookName;
		this.author    = author;
		this.publisher = publisher;
		this.price     = price;
		this.checked  = false;
		this.user     = null;
		this.nextUser = null;
	}
	
	//該本書被借出
	public void lend(String borrower){
		this.user = borrower; // 借閱人
		this.checked = true ; // 借出
	}
	
	public void return_book(){
		this.user = null;     // 修改借閱人為空
		this.checked = false; // 在館內
		
		// 如果有預約者 跳出預約訊息 
		if (this.nextUser != null){
			System.out.println("! - Already reserved. Please inform the user");
			nextUser.printClient();
			this.nextUser = null;
		}
	}

	public void reservation(CLIENT nextUser) {
		System.out.println("Reservation");
		this.nextUser = nextUser;
		System.out.printf("User <%s> reserved book <%s>",nextUser.userName,bookName);
	}
	
	public void printBook(){
		
		System.out.println("< "+bookName+" >");
		System.out.printf("Author: %-15s Publisher: %-10s NT%d\n",author,publisher,price);
		System.out.print("State: ");
		if (this.checked){
			System.out.print("Borrowed\t");
			System.out.println("User: "+this.user);
		}else{
			System.out.println("In LIB.");
		}
	}
	
	public boolean searchBookByName(String bookName){
		
		if (this.bookName.equals(bookName))
			return true;
		else
			return false;
	}
}