package tejaswi_yerukalapudi.com.helloandroid.lib.helper;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Teja on 7/8/15.
 */
public class Helper {
    public static void showToast(Context ctx, String message) {
        Toast t = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
        t.show();
    }
}
