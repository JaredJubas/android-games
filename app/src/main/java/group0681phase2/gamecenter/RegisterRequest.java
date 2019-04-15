package group0681phase2.gamecenter;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.Response;

import java.util.Map;
import java.util.HashMap;

/**
 * The register request.
 * Adapted from https://www.youtube.com/watch?v=T7Z4GVFaT4A
 */
public class RegisterRequest extends StringRequest {

    /**
     * The URL of the database.
     * Taken from https://github.com/tonikami/NEWLoginRegister
     */
    private static final String REGISTER_REQUEST_URL = "https://jjmalz98.000webhostapp.com/Register.php";

    /**
     * A Map to map Strings to Strings.
     */
    private Map<String, String> params;

    /**
     * Process the register request.
     *
     * @param name     the name
     * @param username the username
     * @param password the password
     * @param listener the listener
     */
    RegisterRequest(String name, String username, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
