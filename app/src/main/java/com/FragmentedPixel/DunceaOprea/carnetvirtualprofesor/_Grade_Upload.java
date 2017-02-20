package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


class _Grade_Upload extends StringRequest{
    private static final String Site_URL_Login = "http://carnet-virtual.victoriacentre.ro/grade_upload_prof.php";
    private Map<String, String> params;

    _Grade_Upload(String STID, String GValue, String TID, String SBName, String GDate, String CValue, String GSemester, Response.Listener<String> listener) {
        super(Request.Method.POST, Site_URL_Login, listener, null);
        String AccessCode = "565656";
        params = new HashMap<>();
        params.put("AccessCode",AccessCode);
        params.put("STID",STID);
        params.put("TID",TID);
        params.put("GValue",GValue);
        params.put("SBName",SBName);
        params.put("GDate",GDate);
        params.put("CValue",CValue);
        params.put("GSemester",GSemester);

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
