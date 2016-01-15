import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;

public class socialNetwork {
    public static void main(String [] args)throws IOException{
        Network net = new Network();

        LinkedHashSet<String> Aya =  new LinkedHashSet<>();
        Aya = net.suggestFriends("Mo'men Al-Malky");

    }
}
