package ie.ul.collegetimekeeper.Functions;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import ie.ul.collegetimekeeper.Objects.User;

/**
 * Created by Patryk on 01/05/2017.
 */

public class AddWorkRequest extends StringRequest {

    String tag = "ie.ul.collegetimekeeper";
    // private static final String LOGIN_REQUEST_URL  = "https://collegetimekeeper.000webhostapp.com/login.php";
    private static final String ADD_WORK_REQUEST_URL  = "http://collegetimekeeper.x10host.com/addWork.php";
    private Map<String, String> params;

    public AddWorkRequest(User user, int position, Response.Listener<String> listener){
        super(Request.Method.POST, ADD_WORK_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("userID", user.getId() + "");
        params.put("moduleID", user.getModulesList().get(position).getModuleID());
        params.put("typeWork", user.getModulesList().get(position).getWork().getTypeOfWork());
        params.put("timeSpent", user.getModulesList().get(position).getWork().getTimeSpent() + "");
    }


    public Map<String, String> getParams(){
        return params;
    }



}

