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

public class SetLecturerRequest extends StringRequest{

    String tag = "ie.ul.collegetimekeeper";
   // private static final String SET_LECTURER_REQUEST  = "https://collegetimekeeper.000webhostapp.com/setLecturer.php";
    private static final String SET_LECTURER_REQUEST  = "http://collegetimekeeper.x10host.com/setLecturer.php";
    private Map<String, String> params;

    public SetLecturerRequest(User user, int index, Response.Listener<String> listener){
        super(Request.Method.POST, SET_LECTURER_REQUEST, listener , null);
        params = new HashMap<>();
        params.put("lecturerID", user.getModulesList().get(index).getLecturerID() + "");
        params.put("moduleCode", user.getModulesList().get(index).getModuleID());
    }


    public Map<String, String> getParams(){
        return params;
    }

}

