package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

class _Catalog_Upload extends StringRequest
{
    private static final String Site_URL_Login = "http://carnet-virtual.victoriacentre.ro/catalog_upload.php";
    private Map<String, String> params;

    _Catalog_Upload(String ID, String Type, Response.Listener<String> listener) {
        super(Request.Method.POST, Site_URL_Login, listener, null);
        String AccessCode = "345345";
        params = new HashMap<>();
        params.put("AccessCode",AccessCode);
        params.put("ID",ID);
        params.put("Type",Type);

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}