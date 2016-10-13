package com.example.administrator.sexyboy;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;


/**
 * Created by Administrator on 2016/10/12.
 */

public class SexyBoyRequest<T> extends JsonRequest {
    private static final String TAG = "SexyBoyRequest";
    private Class<T> mClass;
    private final Gson mGson;


    public SexyBoyRequest(String url, Class<T> clazz, Listener listener, ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
        mGson = new Gson();
        mClass = clazz;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            String result = new String(response.data, PROTOCOL_CHARSET);
            Log.d(TAG,result);
            T resultBean = mGson.fromJson(result,mClass);
            Cache.Entry cacheEntry = HttpHeaderParser.parseCacheHeaders(response);
            return Response.success(resultBean,cacheEntry);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }
}
