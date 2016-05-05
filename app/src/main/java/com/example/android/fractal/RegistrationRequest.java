package com.example.android.fractal;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abc on 30-Apr-16.
 */
public class RegistrationRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://rubix.co.in/Fractal/Register.php";
    private Map<String,String> params;

    public RegistrationRequest(String name, String username, String email, String phone, String password, Response.Listener<String> listener){
        super(Request.Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("name",name);
        params.put("username",username);
        params.put("email",email);
        params.put("phone",phone);
        params.put("password",password);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
