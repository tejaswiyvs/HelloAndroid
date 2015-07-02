package tejaswi_yerukalapudi.com.helloandroid;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import tejaswi_yerukalapudi.com.helloandroid.model.*;

public class DashboardActivity extends Activity {

    private Spinner mDropDown;
    private ListView mScheduledAppointmentsList;
    private ListView mHistoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.bindUi();
    }

    public void patientListBtnClicked(View v) {
        Intent intent = new Intent(this, PersonListActivity.class);
        intent.putExtra("personId", "12345");
        startActivity(intent);
    }

    private void bindUi() {
        this.mDropDown = (Spinner) findViewById(R.id.dashboardSpecialtySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, android.R.array.organizationTypes, android.R.layout.simple_spinner_item);
        this.configureAppointmentList();
        this.configureHistoryList();
    }

    private void configureAppointmentList() {
        this.mScheduledAppointmentsList = (ListView) findViewById(R.id.dashboardScheduledAppointmentsList);
    }

    private void configureHistoryList() {
        this.mHistoryList = (ListView) findViewById(R.id.dashboardPatientHistoryList);
    }
}
