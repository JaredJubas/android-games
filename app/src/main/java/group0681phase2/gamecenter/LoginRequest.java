package group0681phase2.gamecenter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * The login request.
 * Adapted from https://www.youtube.com/watch?v=lHBfyQgkiAA
 */
public class LoginRequest extends StringRequest {

    /**
     * The URL of the database.
     * Taken from https://github.com/tonikami/NEWLoginRegister
     */
    private static final String LOGIN_REQUEST_URL = "https://jjmalz98.000webhostapp.com/Login.php";

    /**
     * A Map to map Strings to Strings.
     */
    private Map<String, String> params;

    /**
     * Process the login request.
     *
     * @param username the username
     * @param password the password
     * @param listener the listener
     */
    LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
