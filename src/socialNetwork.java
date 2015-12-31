import java.io.IOException;
import java.util.ArrayList;

public class socialNetwork {
    public static void main(String [] args)throws IOException{
        Network net = new Network();

        ArrayList<String> findUser = new ArrayList<String>();

        findUser = net.searchByName("Hadeer Karem");
        String userName = findUser.get(0);
        ArrayList<String> friends = new ArrayList<String>();

        friends = net.mutualFriends("Fatma Mohammed", "Mo'men Al-Malky");
        if(friends.size() == 0)
            System.out.println("No Mutual Friends");
        else
            System.out.println(friends);



    }
}
