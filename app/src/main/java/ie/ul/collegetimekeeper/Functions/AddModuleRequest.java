package ie.ul.collegetimekeeper.Functions;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import ie.ul.collegetimekeeper.Objects.User;

/**
 * Created by Patryk on 28/04/2017.
 */

public class AddModuleRequest extends StringRequest {

    String tag = "ie.ul.collegetimekeeper";
    private static final String LOGIN_REQUEST_URL  = "https://collegetimekeeper.000webhostapp.com/insertModule.php";
    private Map<String, String> params;

    public AddModuleRequest(User user, int index, Response.Listener<String> listener){
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener , null);
        params = new HashMap<>();
        params.put("moduleCode", user.getModulesList().get(index).getModuleID());
        params.put("moduleName", user.getModulesList().get(index).getModuleTitle());
    }


    public Map<String, String> getParams(){
        return params;
    }

}
