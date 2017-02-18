package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

/**
 * Created by vlad_ on 18.02.2017.
 */
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class _Catalog_Update extends StringRequest {
    private static final String Site_URL_Login = "http://carnet-virtual.victoriacentre.ro/catalog_update.php";
    private Map<String, String> params;

    public _Catalog_Update(String STID, String SBName, Response.Listener<String> listener) {
        super(Request.Method.POST, Site_URL_Login, listener, null);
        String AccessCode = "876543";
        params = new HashMap<>();
        params.put("AccessCode",AccessCode);
        params.put("STID",STID);
        params.put("SBName",SBName);

    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
