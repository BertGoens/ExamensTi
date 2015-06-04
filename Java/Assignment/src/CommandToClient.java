import java.io.Serializable;

/**
 * Created by Tim on 4/06/2015.
 */
public interface CommandToClient extends Serializable {
    void performOnClient(UsersClient client);
}
