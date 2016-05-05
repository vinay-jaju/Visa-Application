package com.example.android.fractal;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abc on 01-May-16.
 */
public class EmployeeStatusRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://rubix.co.in/Fractal/GetStatus.php";
    private Map<String, String> params;

    public EmployeeStatusRequest(String username, Response.Listener<String> listener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("employee_code", username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
