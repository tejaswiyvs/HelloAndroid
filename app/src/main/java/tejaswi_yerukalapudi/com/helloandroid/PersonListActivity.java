package tejaswi_yerukalapudi.com.helloandroid;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import tejaswi_yerukalapudi.com.helloandroid.lib.http.ODataClient;
import tejaswi_yerukalapudi.com.helloandroid.model.Person;
import tejaswi_yerukalapudi.com.helloandroid.model.PersonList;

public class PersonListActivity extends Activity {

    private static final int ADD_PERSON_REQUEST = 10;
    private static final int EDIT_PERSON_REQUEST = 20;

    private ProgressDialog mSpinner;
    private ListView mPersonListView;
    private ArrayAdapter<Person> mAdapter;
    private List<Person> mPersonList;
    private PersonList mPersonListHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_list);
        this.setupList();
        this.fetchData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_PERSON_REQUEST) {
            if (resultCode == RESULT_OK) {
                Person p = (Person) data.getSerializableExtra(PersonActivity.PERSON_KEY);
                this.mPersonList.add(p);
            }
        }
        else if (requestCode == EDIT_PERSON_REQUEST) {
            if (resultCode == RESULT_OK) {
                Person p = (Person) data.getSerializableExtra(PersonActivity.PERSON_KEY);
                int idx = -1;
                for (int i = 0; i < mPersonList.size(); i++) {
                    if (mPersonList.get(i).getCustomerID() == p.getCustomerID()) {
                        idx = i;
                        break;
                    }
                }
                if (idx == -1) { return; }
                this.mPersonList.set(idx, p);
            }
        }

        this.mAdapter.notifyDataSetChanged();
    }

    // Event Handlers
    public void personListToolbarAddBtnClicked(View v) {
        this.showPerson(null);
    }

    private void fetchData() {
        this.showSpinner("Loading ...");
        ODataClient.get("Customers", new RequestParams(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson gson = new Gson();
                String response = new String(responseBody);
                PersonList personList = gson.fromJson(response, PersonList.class);
                PersonListActivity.this.mPersonListHolder = personList;
                PersonListActivity.this.mPersonList.clear();
                PersonListActivity.this.mPersonList.addAll(personList.getPersonList());
                PersonListActivity.this.mAdapter.notifyDataSetChanged();
                PersonListActivity.this.hideSpinner();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(PersonListActivity.this, "Something went wrong while trying to fetch your data. Please try again!", Toast.LENGTH_SHORT);
            }
        });
    }

    private void showSpinner(String message) {
        this.mSpinner = new ProgressDialog(this);
        this.mSpinner.setMessage(message);
        this.mSpinner.setCancelable(false);
        this.mSpinner.show();
    }

    private void hideSpinner() {
        this.mSpinner.hide();
    }

    // Helpers
    private void setupList() {
        this.mPersonList = new ArrayList<Person>();
        this.mPersonListView = (ListView) findViewById(R.id.personListListView);
        this.mAdapter = new PersonListAdapter(this, R.layout.person_list_row, this.mPersonList);
        this.mPersonListView.setAdapter(this.mAdapter);
        this.mPersonListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                Person p = (Person) adapter.getItemAtPosition(position);
                PersonListActivity.this.showPerson(p);
            }
        });
//        View emptyView = getLayoutInflater().inflate(R.layout.person_list_empty_layout, null);
//        addContentView(emptyView, this.mPersonListView.getLayoutParams());
//        this.mPersonListView.setEmptyView(emptyView);
    }

    private void showPerson(Person p) {
        Intent intent = new Intent(PersonListActivity.this, PersonActivity.class);
        if (p != null) {
            intent.putExtra(PersonActivity.PERSON_KEY, p);
            startActivityForResult(intent, EDIT_PERSON_REQUEST);
        }
        else {
            startActivityForResult(intent, ADD_PERSON_REQUEST);
        }
    }
}

class PersonListAdapter extends ArrayAdapter<Person> {

    public PersonListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PersonListAdapter(Context context, int resource, List<Person> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.person_list_row, parent, false);
        }

        Person p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.personListRowNameTextView);

            if (tt1 != null) {
                tt1.setText(p.getContactName());
            }

        }

        return v;
    }
}