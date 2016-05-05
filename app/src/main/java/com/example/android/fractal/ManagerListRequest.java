package com.example.android.fractal;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abc on 01-May-16.
 */
public class ManagerListRequest extends StringRequest {
    public static final String DATA_URL = "http://rubix.co.in/Fractal/ManagerList.php";
    private Map<String,String> params;
    public ManagerListRequest(String project_code, Response.Listener<String> listener){
        super(Request.Method.POST,DATA_URL,listener,null);
        params = new HashMap<>();
        params.put("projectcode",project_code);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
