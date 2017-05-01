package ie.ul.collegetimekeeper.Functions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import ie.ul.collegetimekeeper.Objects.Module;
import ie.ul.collegetimekeeper.Objects.User;

/**
 * Created by Patryk on 04/04/2017.
 */

public class ModuleRequest extends StringRequest{

   // private static final String MODULE_REQUEST_URL = "https://collegetimekeeper.000webhostapp.com/getModules.php";
    private static final String MODULE_REQUEST_URL = "http://collegetimekeeper.x10host.com/getModules.php";

    private Map<String, String> params;

    public ModuleRequest(User user, Response.Listener<String> listener) {
        super(Request.Method.POST, MODULE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("id", user.getId() + "");
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
