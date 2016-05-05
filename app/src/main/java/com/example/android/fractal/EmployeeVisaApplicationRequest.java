package com.example.android.fractal;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by abc on 01-May-16.
 */
public class EmployeeVisaApplicationRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "http://rubix.co.in/Fractal/VisaApplication2.php";
    private Map<String, String> params;
    String date = new java.sql.Date(Calendar.getInstance().getTime().getTime()).toString();
    public EmployeeVisaApplicationRequest(String name, String code, String country, String visatype,String passportno, String place_issue,String passport_expiery, String startdate, String endDate, String projectNumber, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("employee_name",name);
        params.put("employee_code", code);
        params.put("country",country);
        params.put("visa_type",visatype);
        params.put("passport_number",passportno);
        params.put("place_issue",place_issue);
        params.put("passport_expiry_date",passport_expiery);
        params.put("date_required_from",startdate);
        params.put(" date_required_to" ,endDate);
        params.put("projectcode",projectNumber);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
