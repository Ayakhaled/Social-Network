import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class socialNetwork {
    public static void main(String [] args)throws IOException{
        Network net = new Network();

       // ArrayList<String> userf = new ArrayList<String>();

        //userf = net.searchByName("Muhammad Salah").friendList;

        //System.out.print(userf);
        ArrayList<String> path = new ArrayList<String>();
        
        path = net.shortestPath("Muhammad Salah", "Nada Diaa");
        System.out.print(path);

    }
}
