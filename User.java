import java.util.ArrayList;


public class User {
	String name, occupation, company, address;
	ArrayList<User>friendList;
	
	public User(String name, String occupation, String company, String address){
		this.name = name;
		this.occupation = occupation;
		this.company = company;
		this.address = address;
	}
	
	public void addFriends(User user){
		
	}
}