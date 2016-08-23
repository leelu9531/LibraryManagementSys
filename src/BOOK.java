import java.io.*;

public class BOOK implements Serializable{
	String bookName;
	int price;
	String author; 
	String publisher;
	boolean checked; //�O�_�b�]�� true�ɥX false�]��
	String user; //�ɾ\�H
	CLIENT nextUser;
	
	//�غc�l
	public BOOK(String bookName,String publisher,String author,int price){
		this.bookName  = bookName;
		this.author    = author;
		this.publisher = publisher;
		this.price     = price;
		this.checked  = false;
		this.user     = null;
		this.nextUser = null;
	}
	
	//�ӥ��ѳQ�ɥX
	public void lend(String borrower){
		this.user = borrower; // �ɾ\�H
		this.checked = true ; // �ɥX
	}
	
	public void return_book(){
		this.user = null;     // �ק�ɾ\�H����
		this.checked = false; // �b�]��
		
		// �p�G���w���� ���X�w���T�� 
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