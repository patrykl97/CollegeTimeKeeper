package ie.ul.collegetimekeeper.Functions;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;
import java.util.Map;

import ie.ul.collegetimekeeper.Objects.User;

/**
 * Created by Patryk on 31/03/2017.
 */

public class RegisterRequest extends StringRequest{

   // private static final String REGISTER_REQUEST_URL = "https://collegetimekeeper.000webhostapp.com/register.php";
    private static final String REGISTER_REQUEST_URL = "http://collegetimekeeper.x10host.com/register.php";
    private Map<String, String> params;
    String tag = "ie.ul.collegetimekeeper";

    public RegisterRequest(User user, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        this.params = new HashMap<>();
        this.params.put("id", user.getId() + "");
        this.params.put("name", user.getName());
        this.params.put("surname", user.getSurname());
        this.params.put("email", user.getEmail());
        this.params.put("password", user.getPassword());
        this.params.put("collegeName", user.getCollegeName());
        this.params.put("userType", user.getUserType());
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }



}
