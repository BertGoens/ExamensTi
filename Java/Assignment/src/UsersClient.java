import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.lang.Thread.interrupted;

/**
 * Created by Tim on 4/06/2015.
 */
public class UsersClient implements Runnable {

    private Socket clientSocket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String username = "Piet";

    public static void main(String[] args)
    {
        UsersClient c = new UsersClient();
        new Thread(c).start();
    }

    public UsersClient()
    {

    }

    public void logon()
    {
        try {
            clientSocket = new Socket("127.0.0.1", 9000);
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            putOnline(username);
            while(!interrupted())
            {
                CommandToClient commandToClient = (CommandToClient) inputStream.readObject();
                commandToClient.getUsernames().forEach(System.out::println);
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }

    }

    public void putOnline(String name)
    {
        try {
            outputStream.writeObject(new MessageToServer(name, "ONLINE"));
            outputStream.flush();
            getOnlineUsers();
        }
        catch (IOException e)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    public void getOnlineUsers()
    {
        try {
            outputStream.writeObject(new MessageToServer(username, "GETONLINEUSERS"));
            outputStream.flush();
        }
        catch (IOException e)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void run() {
        logon();
    }
}
