import sun.awt.image.ImageWatched;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Network {
	ArrayList<User>allUsers = new ArrayList<User>();

	double edges=Double.POSITIVE_INFINITY;
	double balance=0;

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
	
	//Choice 1
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
	
	//Choice 2
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

	//Choice 3
	//This function returns the shortest path between 2 non-friend users
	public ArrayList<String> shortestPath(String source, String destination) throws IOException{

		allUsers = usersData();

		ArrayList<String> myPath = new ArrayList();
		ArrayList<String> friends = new ArrayList();

		HashMap<String, Boolean> visited = new HashMap<String, Boolean>();
		HashMap<String, String> Predecessors = new HashMap<String, String>();

		for(int i=0; i<allUsers.size(); i++){
			visited.put(allUsers.get(i).name, false);
		}

		Queue queue = new LinkedList();
		Boolean exist = false;

		//BFS start
		queue.add(source);
		visited.remove(source);
		visited.put(source, true);

		while(!queue.isEmpty()){
			String parent = (String)queue.poll();
			friends = searchByName(parent).friendList;

			for(int i=0; i<friends.size(); i++){
				if (friends.get(i).equals(destination)){
					Predecessors.put(friends.get(i), parent);
					exist=true;
					break;
				}
				else if(!visited.get(friends.get(i))){
					Predecessors.put(friends.get(i), parent);

					if(!friends.get(i).equals(destination)){
						queue.add(friends.get(i));
						visited.remove(friends.get(i));
						visited.put(friends.get(i), true);
					}
				}
			}
			if (exist)
				break;
		}

		myPath.add(destination);
		while(!destination.equals(source)){
			destination = Predecessors.get(destination);
			myPath.add(destination);
		}
		return myPath;
	}

	//Choice 4
    //This Function Follows 2 criteria on suggesting friends.
    //The first one is: the max number of links between the 2 users should be 5.
    //The second is: suggest friends working for the same company.
	public LinkedHashSet<String> suggestFriends(String user) throws IOException{

		LinkedHashSet<String> suggestedFriends = new LinkedHashSet<String>();

		//Company Criteria.
		String userCompany = searchByName(user).company;
		for (int i = 0; i < usersData().size(); i++) {
			if (usersData().get(i).company.equals(userCompany) && !usersData().get(i).name.equals(user)){
				suggestedFriends.add(usersData().get(i).name);
			}
		}

		//Max number of links criteria
		HashMap<String, Boolean> visitedUsers = new HashMap<>();
		LinkedHashSet<String> forbidFriends = new LinkedHashSet<String>();
		Queue friendsQueue = new LinkedList<>();
		ArrayList<String> friendList = new ArrayList<String>();
		friendList = searchByName(user).friendList;
		forbidFriends.add(user);
		for (int i = 0; i < friendList.size(); i++) {
			friendsQueue.add(friendList.get(i));
			forbidFriends.add(friendList.get(i));
		}

		String queueHead = "";
		for (int i = 0; i < 4; i++) {
			int noOfLoops = friendsQueue.size();
			for (int j = 0; j < noOfLoops; j++) {
				ArrayList<String> list = new ArrayList<String>();
				queueHead = (String)friendsQueue.poll();
				list = searchByName(queueHead).friendList;

				for (int k = 0; k < list.size(); k++) {
					friendsQueue.add(list.get(k));
					suggestedFriends.add(list.get(k));
				}
			}
		}
		suggestedFriends.removeAll(forbidFriends);
		System.out.println(suggestedFriends);
		return suggestedFriends;
	}
	
	//Choice 5
	public List<List<String>> groups() throws IOException{
		
		allUsers = usersData();
		//ArrayList<List<String>> groups= new ArrayList<List<String>>();
		
		List<List<String>> groups= new ArrayList<List<String>>();
		List<ArrayList<String>> groupsFriends= new ArrayList<ArrayList<String>>();
		//List<String> group= new ArrayList<String>();
		//Set<String> groupFriends= new HashSet<String>();
		
		for(int i=0;i<allUsers.size();i++){
			
			//group.add(allUsers.get(i).name);
			groupsFriends.add(allUsers.get(i).friendList);
			
			groups.add(new ArrayList<String>());
			groups.get(i).add(allUsers.get(i).name) ;
//		group.remove(i);
		}
		
		while(groups.size()>2){
			
			boolean done = false;
			Random rand = new Random();
			int x=rand.nextInt(groups.size());
			int y=rand.nextInt(groupsFriends.get(x).size());
			
			String node=groupsFriends.get(x).get(y);
			
			for(int i=0;i<groups.size();i++){
				for(int j = 0;j<groups.get(i).size();j++){
				if(node.equals(groups.get(i).get(j))){
					groups.get(x).addAll(groups.get(i));	//merge nodes
					groupsFriends.get(x).addAll(groupsFriends.get(i));
					
					groupsFriends.get(x).removeAll(groups.get(x));
					
					groups.remove(i);
					groupsFriends.remove(i);
					done = true;
					break;
				}
				}
				if (done)
					break;
			}
			edges = groupsFriends.get(0).size();
			balance = Math.abs(groups.get(0).size()-groups.get(1).size());	
		}
//		System.out.println("Group 1 : " +groups.get(0));
//		System.out.println(groupsFriends.get(0));
//		
//		System.out.println("Group 2 : " +groups.get(1));
//		System.out.println(groupsFriends.get(1));
		return groups;
		
	}
	
	public void optimalGroups() throws IOException{
		double minEdges = Double.POSITIVE_INFINITY;
		double bestBalance = Double.POSITIVE_INFINITY;
		int[] noOfEdgesTemp = new int[20];
		//HashMap<Integer, List> s=new HashMap<Integer, List>();
		int[] balanceArrayTemp = new int[20];
		List<List<String>> possibleGroups = new ArrayList<List<String>>();
		List<List<String>> best2Groups = new ArrayList<List<String>>();
		
		for(int i=0; i<20; i++){
			//possibleGroups.add(new ArrayList<String>());
			possibleGroups = groups();
			
			if(edges<minEdges){
				minEdges=edges;
				bestBalance=balance;
				best2Groups = possibleGroups ;
			}
			else if(edges==minEdges){
				if(balance<bestBalance){
					minEdges=edges;
					bestBalance=balance;
					best2Groups = possibleGroups ;
				}
				else
					continue;
			}
			else{
				continue;
			}
		
			System.out.println("Group1:"+best2Groups.get(0));
			System.out.println("Group2:"+best2Groups.get(1));
		
		}
	}
}