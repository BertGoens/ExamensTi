import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tim on 4/06/2015.
 */
public class UsersServer {

    private ServerSocket serverSocket;
    private HashMap<ClientConnection, InetAddress> connections;
    private HashMap<ClientConnection, String> users;

    public static void main(String[] args) {
        UsersServer s = new UsersServer();
    }

    public UsersServer()
    {
        startServer();
    }

    public void startServer()
    {
        try {
            serverSocket = new ServerSocket(9000);
            connections = new HashMap<ClientConnection, InetAddress>();
            users = new HashMap<ClientConnection, String>();
            while(true)
            {
                Socket userSocket = serverSocket.accept();
                ClientConnection clientConnection = new ClientConnection(userSocket, this);
                new Thread(clientConnection).start();
                connections.put(clientConnection, userSocket.getInetAddress());
            }
        }
        catch (IOException e)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }

    }

    public synchronized void putOnline(ClientConnection connection, String name)
    {
        if(!users.containsKey(connection))
            users.put(connection, name);
        else
            users.replace(connection, name);
    }

    public synchronized List<String> getOnlineUsers()
    {
        return Collections.unmodifiableList(new ArrayList<String>(users.values()));
    }
}
