import java.io.Serializable;

/**
 * Created by Tim on 4/06/2015.
 */
public class MessageToServer implements CommandToServer, Serializable {
    private String message;
    private String username;

    MessageToServer(String username, String message)
    {
        this.username = username;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
