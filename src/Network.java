import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
	public ArrayList<String> searchByName(String searchName) throws IOException{
		allUsers = usersData();

		ArrayList<String> foundUser = new ArrayList<String>();

		for (int i = 0; i < allUsers.size(); i++) {
			if (allUsers.get(i).name.equals(searchName)){

				foundUser.add(allUsers.get(i).name);
				foundUser.add(allUsers.get(i).occupation);
				foundUser.add(allUsers.get(i).company);
				foundUser.add(allUsers.get(i).address);

				foundUser.addAll(allUsers.get(i).friendList);

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

		userInfo1 = searchByName(user1);
		userInfo2 = searchByName(user2);

		for (int i = 4; i < userInfo1.size(); i++) {
			for (int j = 4; j < userInfo2.size(); j++) {
				if (userInfo1.get(i).equals(userInfo2.get(j))){
					mutualFriends.add(userInfo1.get(i));
					break;
				}
			}
		}

		return mutualFriends;
	}
	
    //This function returns the shortest path between 2 non-friend users
	public ArrayList<String> shortestPath(){
		ArrayList<String> myPath = new ArrayList();
		return myPath;
	}
	
    //This Function Follows 2 criteria on suggesting friends.
    //The first one is: the max number of links between the 2 users should be 5.
    //The seconed is: suggest friends working for the same company.
	public ArrayList<String> suggestFriends(){
		ArrayList<String> suggestedFriends = new ArrayList();
		return suggestedFriends;
	}
	
	public void groups(){
		
	}
}
