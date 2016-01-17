import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import com.sun.xml.internal.ws.api.pipe.NextAction;

public class socialNetwork {
    public static void main(String [] args)throws IOException{

    	Scanner input = new Scanner (System.in);
    	Network net = new Network();
    	
    	int choice;
    	String user1;
    	String user2;

    	System.out.println("          MAIN MENU          ");
    	System.out.println("1. search by user name");
    	System.out.println("2. find mutual friends");
    	System.out.println("3. find the shortest path");
    	System.out.println("4. suggested friends");
    	System.out.println("5. the 2 Groups");
    	System.out.print("\nEnter your choice number: ");
    	
    	choice = input.nextInt();
    	
    	switch (choice){
    	case 1:
    		System.out.print("Search about user (Enter his name, please): ");
    		user1 = input.next();
    		
    		String name = net.searchByName(user1).name;
    		String occupation = net.searchByName(user1).occupation;
    		String company = net.searchByName(user1).company;
    		String address = net.searchByName(user1).address;
    		ArrayList<String> friends = net.searchByName(user1).friendList;
    		
    		//User searchedUser = new User(name, occupation, company, address);
    		System.out.println("User's name: " +name);
    		System.out.println("User's occupation: " +occupation);
    		System.out.println("User's company: " +company);
    		System.out.println("User's address: " +address);
    		System.out.println("User's friend list: " +friends);
    		
    		break;
    		
    	case 2:
    		System.out.println("Want to find the mutual friends between two certain users?");
    		System.out.print("Please, Enter the first user's name: ");
    		user1 = input.nextLine();
    		
    		System.out.print("Now, Enter the second user's name: ");
    		user2 = input.nextLine();
    		
    		ArrayList<String> mutuals = new ArrayList<String>();
    		mutuals = net.mutualFriends(user1, user2);
    		
    		if(mutuals.size() >= 0)
    			System.out.println("Mutual friends between " +user1+ " and " +user2+ " are: " +mutuals);
    		else
    			System.out.println("There's no mutual friends between " +user1+ " and " +user2);
    	}
    }
}