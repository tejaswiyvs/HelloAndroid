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

    public void get(Context context, String relativeUrl, RequestParams params, final ResponseHandler personListResponseHandler) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();

            String sEndPointPath = getAbsoluteUrl("Person");

            RequestParams params = new RequestParams();
            client.get(context, sEndPointPath, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Gson gson = new Gson();
                    String response = responseBody.toString();
                    Type type = new TypeToken<ArrayList<Person>>() {
                    }.getType();
                    ArrayList<Person> personList = gson.fromJson(response, type);
                    ODataClient.this.mResponseHandler.onComplete(personList);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    ODataClient.this.mResponseHandler.onError(statusCode);
                }
            });
            params.put("$filter", "CustomerID eq '45f69102-669e-4407-ae25-04aefa5a4414'");
        } catch (Exception ex){
            ODataClient.this.mResponseHandler.onError(-1);
        }

    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
