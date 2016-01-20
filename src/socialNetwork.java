import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.sun.xml.internal.ws.api.pipe.NextAction;

public class socialNetwork {
    public static void main(String [] args)throws IOException{
		
    	//BufferedReader br = new BufferedReader(new FileReader("dataFile.txt"));

    	Scanner inputInt = new Scanner (System.in);
    	Scanner inputString = new Scanner (System.in);
    //	Scanner scan = new Scanner (System.in);
    	Network net = new Network();
    	
    	int choice;
//    	int noOfUsers;
//    	String str;
    	String user1;
    	String user2;
    	String check;
//		ArrayList<String> line = new ArrayList<String>();
		
/*		while ((str=br.readLine()) != null) {
			line.add(str);
		}
		noOfUsers = Integer.parseInt(line.get(0));
*/
		System.out.println("          MAIN MENU          ");
    	System.out.println("1. search by user name");
    	System.out.println("2. find mutual friends");
    	System.out.println("3. find the shortest path");
    	System.out.println("4. suggested friends");
    	System.out.println("5. the 2 Groups");
    	System.out.print("\nEnter your choice number: ");
    	choice = inputInt.nextInt();
    	
    	boolean wantToCheck = true;
		do{	
    		switch (choice){
    		case 1:
    			System.out.println("Search about user (Enter his name, please): ");
    			user1 = inputString.nextLine();
    			
//    			for(int i=2; i<noOfUsers+2; i++){
//   				if(line.get(i).contains(user1)){
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
 //   				}
   // 				else if(!line.get(i).contains(user1)){
    //					System.out.println("Sorry user doesn't Exist!");
    	//				break;
    		//		}
    			//}
    			
    		case 2:
    			System.out.println("Want to find the mutual friends between two certain users?");
    			System.out.print("Please, Enter the first user's name: ");
    			user1 = inputString.nextLine();
    			
    			System.out.print("Now, Enter the second user's name: ");
    			user2 = inputString.nextLine();
    			
    			/*for(int i=2; i<noOfUsers+2; i++){
    				if(line.get(i).contains(user1)){
    					for(int j=2; j<noOfUsers+2; j++){
    			*///			if(line.get(j).contains(user2)){    							
    							ArrayList<String> mutuals = new ArrayList<String>();
    							mutuals = net.mutualFriends(user1, user2);
    							
    							if(mutuals.size() > 0)
    								System.out.println("Mutual friends between " +user1+ " and " +user2+ " are: " +mutuals);
    							else
    								System.out.println("There's no mutual friends between " +user1+ " and " +user2);
    							break;
    			/*			}
    					}
    				}
    				else{    					
    					System.out.println("One of users or both of them are not exist");
    					break;
    				}
    			}*/
    			
    			
    		case 3:
    			System.out.println("Want the shortest path between: ");
    			user1 = inputString.nextLine();
    			System.out.println("\nAnd: ");
    			user2 = inputString.nextLine();
    			
    			/*for(int i=2; i<noOfUsers+2; i++){
    				if(line.get(i).contains(user1)){
    					for(int j=2; j<noOfUsers+2; j++){
    						if(line.get(j).contains(user2)){    							*/
    							ArrayList<String> thePath = new ArrayList<String>();
    							thePath = net.shortestPath(user1, user2);
    							
    							System.out.println("The shortest link between " +user1+ " and " +user2+ " is " +thePath);
    							break;
    						/*}
    					}
    				}
    				else{
    					System.out.println("One of users or both of them are not exist");
    					break;
    				}
    			}*/
    			
    			
    		case 4:
    			System.out.println("Suggest friend to user: ");
    			user1 = inputString.nextLine();
    			
    			/*for(int i=2; i<noOfUsers+2; i++){
    				if(line.get(i).contains(user1)){    							*/
    					LinkedHashSet<String> suggestions = new LinkedHashSet<String>();
    					suggestions = net.suggestFriends(user1);
    					
    					System.out.println("The suggested friends to " +user1+ " are " +suggestions);
    					break;
    				/*}
    				else{
    					System.out.println("user doesn't exist!");
    					break;
    				}
    			}*/
    		}

    			System.out.println("\nDo you want to check another feature? (Y/N)");
    		check = inputString.nextLine();
    		
    		if(check.contentEquals("yes") || check.contentEquals("Yes")){		
    			System.out.print("\nEnter your choice number: ");
    	    	choice = inputInt.nextInt();
    	    	wantToCheck = true;
    		}
    		if(check.contentEquals("no") || check.contentEquals("No")){
    			System.out.println("THANKS!");
    			wantToCheck = false;    			
    		}
    	}while(wantToCheck);
    }
}