import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Network {
	ArrayList<User>allUsers = new ArrayList<User>();


	ArrayList<User> usersData() throws IOException{
		int noOfUsers = 0;
		int startIndexOfFriendList = 0;
		//open input stream dataFile.txt for reading purpose.
		BufferedReader br = new BufferedReader(new FileReader("dataFile.txt"));
		try {
			//the lines on file
			ArrayList<String> line = new ArrayList<String>();
			//list of users
			ArrayList<String> users = new ArrayList<String>();
			//list of friends
			ArrayList<String> friends = new ArrayList<String>();

			String str;
			while ((str = br.readLine()) != null) {
				line.add(str);
			}
			//get the no of users in the social network by reading the first line in file
			noOfUsers = Integer.parseInt(line.get(0));
			//split the lists
			//users list
			for (int i = 2; i < line.size() ; i++) {
				if (line.get(i).equals("")){
					startIndexOfFriendList = i+1;
					break;
				}

				else {
					users.add(line.get(i));
				}
			}

			//friends list
			for (int i = startIndexOfFriendList; i < line.size(); i++) {
				friends.add(line.get(i));
			}

			//merge the lists
			//this list contains each user's info and friends
			ArrayList<User> usersInfo = new ArrayList<User>();

			for (int i = 0; i < noOfUsers; i++) {
				ArrayList<String> user = new  ArrayList<String>(Arrays.asList(users.get(i).split(", ")));
				String name = user.get(0);
				String occ = user.get(1);
				String company = user.get(2);
				String address = user.get(3);

				User newUser = new User(name, occ, company, address);

				ArrayList<String> userFriend = new  ArrayList<String>(Arrays.asList(friends.get(i).split(", ")));
				newUser.friendList = userFriend;

				usersInfo.add(newUser);
			}

			return usersInfo;
		} finally {
			br.close();
		}
	}

    //Register new users.
	public void register(){
		
	}
	
    //Search for specific user by his name and return his info.
	public User searchByName(String searchName) throws IOException{
		allUsers = usersData();

		User foundUser = new User("", "", "", "");

		for (int i = 0; i < allUsers.size(); i++) {
			if (allUsers.get(i).name.equals(searchName)){

				String name = allUsers.get(i).name;
				String occupation = allUsers.get(i).occupation;
				String company = allUsers.get(i).company;
				String address = allUsers.get(i).address;

				foundUser = new User(name, occupation, company, address);
				foundUser.friendList = allUsers.get(i).friendList;

				break;
			}
		}

		return foundUser;
	}
	
    //This function returns a list of mutual friends between 2 users.
	public ArrayList<String> mutualFriends(String user1, String user2) throws IOException{
		ArrayList<String> mutualFriends = new ArrayList<String>();

        ArrayList<String> userInfo1 = new ArrayList<String>();
		ArrayList<String> userInfo2 = new ArrayList<String>();

		userInfo1 = searchByName(user1).friendList;
		userInfo2 = searchByName(user2).friendList;

		for (int i = 0; i < userInfo1.size(); i++) {
			for (int j = 0; j < userInfo2.size(); j++) {
				if (userInfo1.get(i).equals(userInfo2.get(j))){
					mutualFriends.add(userInfo1.get(i));
					break;
				}
			}
		}

		return mutualFriends;
	}

	public boolean hasMutualFriend(String user1, String user2) throws IOException{
		boolean mutual = false;

		ArrayList<String> listOfMutualFriends = new ArrayList<String>();
		listOfMutualFriends = mutualFriends(user1, user2);
		if (listOfMutualFriends.size() == 0)
			mutual = false;
		else
			mutual = true;

		return mutual;
	}
    
	//This function returns the shortest path between 2 non-friend users
	public ArrayList<String> shortestPath(String source, String destination) throws IOException{
		allUsers = usersData();
		ArrayList<String> myPath = new ArrayList();
		ArrayList<String> friends = new ArrayList();
		
		ArrayList<String> user1Info = new ArrayList<String>();
		ArrayList<String> user2Info = new ArrayList<String>();
		
		user1Info = searchByName(source).friendList;
		user2Info = searchByName(destination).friendList;
		
		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
		HashMap<String, String> Predecessors = new HashMap<String, String>();
		
		
		for(int i=0; i<allUsers.size(); i++){
			
			visited.put(allUsers.get(i).name, false);
		}
		
		Queue queue = new LinkedList();
		Queue backTrack = new LinkedList();
		
		//BFS start
		queue.add(source);
		visited.remove(source);
		visited.put(source, true);
		
		while(!queue.isEmpty()){
			 String parent = (String)queue.poll();
			 friends = searchByName(parent).friendList;
//			 System.out.println(friends);
			 
			 for(int i=0; i<friends.size(); i++){
//				 System.out.println(visited.get(friends.get(i)));
				 if(!visited.get(friends.get(i)))
					 Predecessors.put(friends.get(i), parent);
				 
				 if(!friends.get(i).equals(destination) && !visited.get(friends.get(i))){
					 queue.add(friends.get(i));
					 visited.remove(friends.get(i));
					 visited.put(friends.get(i), true);
				 }
				 else if (friends.get(i).equals(destination))
					 break;
			 }
		}
		
		myPath.add(destination);
		while(!destination.equals(source)){
			destination = Predecessors.get(destination);
			myPath.add(destination);
			System.out.println(destination);
		}
		
		myPath.remove(myPath.size()-1);
		return myPath;
	}

	public boolean isFriend(String user1, String user2) throws IOException{
		boolean friend = false;
		ArrayList<String> userInfo = new ArrayList<String>();
		userInfo = searchByName(user1).friendList;
		for (int i =0; i < userInfo.size(); i++) {
			if (user2.equals(userInfo.get(i)))
				friend = true;
		}

		return friend;
	}

    //This Function Follows 2 criteria on suggesting friends.
    //The first one is: the max number of links between the 2 users should be 5.
    //The second is: suggest friends working for the same company.
	public LinkedHashSet<String> suggestFriends(String user) throws IOException{

		LinkedHashSet<String> suggestedFriends = new LinkedHashSet<String>();

		ArrayList<String> listSuggestedFriends = new ArrayList<String>();

		ArrayList<String> friends = new ArrayList<String>();
		friends = searchByName(user).friendList;

		ArrayList<String> forbiddenSuggestion = new ArrayList<String>();
		forbiddenSuggestion.add(user);
		forbiddenSuggestion.addAll(friends);


		String userCompany = searchByName(user).company;
		for (int i = 0; i < usersData().size(); i++) {
			if (usersData().get(i).company.equals(userCompany) && !usersData().get(i).name.equals(user) && !isFriend(user, usersData().get(i).name)){
				listSuggestedFriends.add(usersData().get(i).name);
			}
		}

		for (int j = 0; j < 4 ; j++) {
			for (int i = 0; i < friends.size(); i++) {
				ArrayList<String> temp = new ArrayList<String>();
				temp = searchByName(friends.get(i)).friendList;
				listSuggestedFriends.addAll(temp);
			}
			friends.clear();
			friends.addAll(listSuggestedFriends);
		}

//		ArrayList<String> tempFriends = new ArrayList<String>();
//		tempFriends.addAll(suggestedFriends);
//		for (int i = 0; i < forbiddenSuggestion.size(); i++) {
//			for (int j = 0; j < tempFriends.size(); j++) {
//				if (forbiddenSuggestion.get(i).equals(tempFriends.get(j))){
//					tempFriends.remove(i);
//				}
//			}
//		}
//		suggestedFriends.clear();
//		suggestedFriends.addAll(tempFriends);
		return suggestedFriends;
	}
	
	public void groups(){
		
	}
}
