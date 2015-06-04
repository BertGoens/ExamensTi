import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Tim on 4/06/2015.
 */
public class ClientConnection implements Runnable {
    private Socket socket;
    private UsersServer server;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public ClientConnection(Socket socket, UsersServer server)
    {
        this.socket = socket;
        this.server = server;
        try {

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }

    }

    private void receiveFromClient()
    {
        try {
            Object msg;
            while((msg = inputStream.readObject()) != null)
            {
                CommandToServer commandToServer = (CommandToServer) msg;
                processCommand(commandToServer);
            }
        }
        catch (IOException | ClassNotFoundException e)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    private void processCommand(CommandToServer cmd)
    {
        cmd.performOnServer(server, this);
    }

    public void sendToClient(CommandToClient cmd) {
        try {
            outputStream.writeObject(cmd);
        }
        catch (IOException e)
        {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void run() {
        receiveFromClient();
    }
}
