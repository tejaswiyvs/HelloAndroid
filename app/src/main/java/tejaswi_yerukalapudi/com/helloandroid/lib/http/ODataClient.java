package tejaswi_yerukalapudi.com.helloandroid.lib.http;

import android.content.Context;
import com.loopj.android.http.*;
import tejaswi_yerukalapudi.com.helloandroid.lib.http.Interfaces.ResponseHandler;
import tejaswi_yerukalapudi.com.helloandroid.model.*;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ODataClient {
    private static final String BASE_URL = "http://1860-6169.el-alt.com/odata/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String relativeUrl, RequestParams params, final AsyncHttpResponseHandler personListResponseHandler) {
        client.get(getAbsoluteUrl(relativeUrl), params, personListResponseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void delete(String url, AsyncHttpResponseHandler responseHandler) {
        client.delete(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
