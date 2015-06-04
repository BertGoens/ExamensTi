import java.io.Serializable;
import java.util.List;

/**
 * Created by Tim on 4/06/2015.
 */
public class GetUsersToServer implements CommandToServer, Serializable {

    GetUsersToServer()
    {

    }

    @Override
    public void performOnServer(UsersServer server, ClientConnection currentConnection) {
        currentConnection.sendToClient(new MessageToClient(server.getOnlineUsers()));
    }
}
