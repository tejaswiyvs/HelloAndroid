package tejaswi_yerukalapudi.com.helloandroid.lib.http;

import android.content.Context;
import com.loopj.android.http.*;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ODataClient {
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String BASE_URL = "http://1860-6169.el-alt.com/odata/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    private static final int STATUS_FAILURE = -1;

    public static void get(String relativeUrl, RequestParams params, final AsyncHttpResponseHandler personListResponseHandler) {
        client.get(getAbsoluteUrl(relativeUrl), params, personListResponseHandler);
    }

    public static void postJson(Context ctx, String relativeUrl, String jsonBody, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException {
        client.post(ctx, getAbsoluteUrl(relativeUrl), getJsonEntity(jsonBody), JSON_CONTENT_TYPE, responseHandler);
    }

    public static void putJson(Context ctx, String relativeUrl, String jsonBody, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException {
        client.put(ctx, getAbsoluteUrl(relativeUrl), getJsonEntity(jsonBody), JSON_CONTENT_TYPE, responseHandler);
    }

    public static void patch(Context ctx, String relativeUrl, String jsonBody, Callback callback) throws Exception {
        Request request = new Request.Builder().url(getAbsoluteUrl(relativeUrl)).patch(RequestBody.create(JSON_MEDIA_TYPE, jsonBody)).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public static void delete(Context ctx, String relativeUrl, AsyncHttpResponseHandler responseHandler) {
        client.delete(ctx, getAbsoluteUrl(relativeUrl), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static StringEntity getJsonEntity(String jsonBody) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(jsonBody);
        entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, JSON_CONTENT_TYPE));
        return entity;
    }
}
