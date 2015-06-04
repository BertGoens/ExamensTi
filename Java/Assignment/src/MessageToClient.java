import java.io.Serializable;
import java.util.List;

/**
 * Created by Tim on 4/06/2015.
 */
public class MessageToClient implements CommandToClient, Serializable {
    List<String> usernames;

    public MessageToClient(List<String> usernames)
    {
        this.usernames = usernames;
    }

    @Override
    public List<String> getUsernames() {
        return usernames;
    }
}
