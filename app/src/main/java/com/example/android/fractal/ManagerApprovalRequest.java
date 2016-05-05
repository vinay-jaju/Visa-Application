package com.example.android.fractal;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abc on 01-May-16.
 */
public class ManagerApprovalRequest extends StringRequest {
    private static final String APPROVAL_REQUEST_URL = "http://rubix.co.in/Fractal/ManagerApproval.php";
    private Map<String, String> approval;

    public ManagerApprovalRequest(String username, String status,String comment, Response.Listener<String> listener) {
        super(Request.Method.POST, APPROVAL_REQUEST_URL, listener, null);
        approval = new HashMap<>();
        approval.put("employee_code", username);
        approval.put("status", status);
        approval.put("manager_comment",comment);
    }

    @Override
    public Map<String, String> getParams() {
        return approval;
    }
}
