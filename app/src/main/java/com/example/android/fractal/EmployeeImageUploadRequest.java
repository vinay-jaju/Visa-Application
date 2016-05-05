package com.example.android.fractal;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abc on 01-May-16.
 */
public class EmployeeImageUploadRequest extends StringRequest {
    private static final String IMAGE_UPLOAD_URL = "http://rubix.co.in/Fractal/ImageUpload.php";
    private Map<String,String> params;

    public EmployeeImageUploadRequest(String image1, String image2, String image3, String image4, String employee_code, Response.Listener<String> listener){
        super(Request.Method.POST,IMAGE_UPLOAD_URL,listener,null);
        params = new HashMap<>();
        params.put("img1",image1);
        params.put("img2",image2);
        params.put("img3",image3);
        params.put("img4",image4);
        params.put("employee_code",employee_code);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
