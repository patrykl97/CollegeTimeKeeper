package ie.ul.collegetimekeeper.Functions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import ie.ul.collegetimekeeper.Objects.User;

/**
 * Created by Patryk on 04/04/2017.
 */

public class AllModulesRequest extends StringRequest {

   // private static final String MODULE_REQUEST_URL = "https://collegetimekeeper.000webhostapp.com/getAllModules.php";
    private static final String MODULE_REQUEST_URL = "http://collegetimekeeper.x10host.com/getAllModules.php";
    private Map<String, String> params;

    public AllModulesRequest(Response.Listener<String> listener) {
        super(Method.GET, MODULE_REQUEST_URL, listener, null);
        params = new HashMap<>();
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }


}