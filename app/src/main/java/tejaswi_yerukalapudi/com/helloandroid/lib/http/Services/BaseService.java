package tejaswi_yerukalapudi.com.helloandroid.lib.http.Services;

import tejaswi_yerukalapudi.com.helloandroid.lib.http.ODataClient;

/**
 * Created by Teja on 7/13/15.
 */
public class BaseService {

    protected static final String BASE_URL = "http://1860-6169.el-alt.com/odata/";

    protected static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
