package ie.ul.collegetimekeeper.Functions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import ie.ul.collegetimekeeper.Objects.User;

/**
 * Created by Patryk on 01/05/2017.
 */

public class UserModulesRequest extends StringRequest{
    private static final String USER_MODULES_REQUEST_URL = "http://collegetimekeeper.x10host.com/getModules.php";
    private Map<String, String> params;
    String tag = "ie.ul.collegetimekeeper";

    public UserModulesRequest(User user, Response.Listener<String> listener) {
        super(Request.Method.POST, USER_MODULES_REQUEST_URL, listener, null);
        this.params = new HashMap<>();
        this.params.put("id", user.getId() + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }



}
