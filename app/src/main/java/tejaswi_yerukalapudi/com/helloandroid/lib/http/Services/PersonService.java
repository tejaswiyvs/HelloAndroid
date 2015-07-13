package tejaswi_yerukalapudi.com.helloandroid.lib.http.Services;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.okhttp.Callback;

import java.io.UnsupportedEncodingException;

import tejaswi_yerukalapudi.com.helloandroid.lib.http.ODataClient;
import tejaswi_yerukalapudi.com.helloandroid.model.Person;

/**
 * Created by Teja on 7/13/15.
 */
public class PersonService extends BaseService {

    public void getPersonList(Context context, AsyncHttpResponseHandler responseHandler) {
        ODataClient.get(getAbsoluteUrl("Customers"), new RequestParams(), responseHandler);
    }

    public void addPerson(Context context, Person p, AsyncHttpResponseHandler responseHandler) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        String content = gson.toJson(p);
        ODataClient.postJson(context, getAbsoluteUrl("Customers"), content, responseHandler);
    }

    public void deletePerson(Context context, String personId, AsyncHttpResponseHandler responseHandler) {
        ODataClient.delete(context, getAbsoluteUrl("Customers('" + personId + "')"), responseHandler);
    }

    public void updatePerson(Context context, Person p, Callback callback) throws Exception {
        Gson gson = new Gson();
        String content = gson.toJson(p);
        ODataClient.patch(context, getAbsoluteUrl("Customers('" + p.getCustomerID() + "')"), content, callback);
    }
 }
