package tejaswi_yerukalapudi.com.helloandroid.lib.http.Interfaces;

/**
 * Created by Teja on 7/2/15.
 */
public interface ResponseHandler {
    public void onComplete(Object data);
    public void onError(int code);
}
