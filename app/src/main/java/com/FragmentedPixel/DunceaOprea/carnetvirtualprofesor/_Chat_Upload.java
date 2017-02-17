package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by elev on 2/13/2017.
 */

public class _Chat_Upload extends StringRequest {
    private static final String Site_URL_Login = "http://carnet-virtual.victoriacentre.ro/chat_upload_prof.php";
    private Map<String, String> params;

    public _Chat_Upload(String Message, String CID,String TID,String TName,String CHType,String CHEDate,Response.Listener<String> listener) {
        super(Request.Method.POST, Site_URL_Login, listener, null);
        String AccessCode = "919191";
        params = new HashMap<>();
        params.put("AccessCode",AccessCode);
        params.put("CID",CID);
        params.put("TID",TID);
        params.put("TName",TName);
        params.put("CHType",CHType);
        params.put("CHEDate",CHEDate);
        params.put("Message", Message);

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
