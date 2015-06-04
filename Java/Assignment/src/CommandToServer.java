import java.io.Serializable;
import java.util.List;

/**
 * Created by Tim on 4/06/2015.
 */
public interface CommandToServer extends Serializable {
    void performOnServer(UsersServer server, ClientConnection currentConnection);
}
