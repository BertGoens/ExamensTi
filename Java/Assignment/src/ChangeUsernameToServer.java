import java.io.Serializable;
import java.util.List;

/**
 * Created by Tim on 4/06/2015.
 */
public class ChangeUsernameToServer implements CommandToServer, Serializable {
    private String username;

    ChangeUsernameToServer(String username)
    {
        this.username = username;
    }

    @Override
    public void performOnServer(UsersServer server, ClientConnection currentConnection) {
        server.putOnline(currentConnection, username);
    }
}
