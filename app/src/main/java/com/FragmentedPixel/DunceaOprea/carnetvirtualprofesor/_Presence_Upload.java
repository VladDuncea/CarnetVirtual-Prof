package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class _Presence_Upload extends StringRequest{

    private static final String Site_URL_Login = "http://carnet-virtual.victoriacentre.ro/presence_upload_prof.php";
    private Map<String, String> params;

    _Presence_Upload(String STID, String CValue, String TID, String SBName, String PDate, Response.Listener<String> listener) {
        super(Request.Method.POST, Site_URL_Login, listener, null);
        String AccessCode = "242424";
        params = new HashMap<>();
        params.put("AccessCode",AccessCode);
        params.put("STID",STID);
        params.put("TID",TID);
        params.put("CValue",CValue);
        params.put("SBName",SBName);
        params.put("PDate",PDate);

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
