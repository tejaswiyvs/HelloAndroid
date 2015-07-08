package tejaswi_yerukalapudi.com.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Callback;

import org.apache.http.Header;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import tejaswi_yerukalapudi.com.helloandroid.lib.helper.Helper;
import tejaswi_yerukalapudi.com.helloandroid.lib.http.ODataClient;
import tejaswi_yerukalapudi.com.helloandroid.model.Person;


public class PersonActivity extends Activity {
    public static final String PERSON_KEY = "person";
    public static final String DELETE_FLAG = "deleted";
    private static final boolean RECORD_DELETED = true;
    private static final boolean RECORD_UPDATED = false;

    private Person mPerson;
    private TextView mTitleTextView;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mDOBEditText;
    private ProgressDialog mSpinner;
    private Button mDeleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        mPerson = (Person) getIntent().getSerializableExtra(PERSON_KEY);
        // If a person object wasn't passed to us, assume that the caller wants to create a new record.
        if (mPerson == null) mPerson = new Person();
        this.setupFields();
    }

    public void personSaveBtnClicked(View v) {
        if (this.mFirstNameEditText.getText().toString().isEmpty()) {
            this.mFirstNameEditText.requestFocus();
            this.mFirstNameEditText.setError(getString(R.string.person_name_empty_error));
            return;
        }

        this.showSpinner(getString(R.string.person_saving_msg));
        this.mPerson.setContactName(this.mFirstNameEditText.getText().toString());
        try {
            this.post(mPerson);
        }
        catch(Exception ex) {
            this.hideSpinner();
            Helper.showToast(this, getString(R.string.person_network_failed_message));
        }
    }

    public void personDeleteBtnClicked(View v) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle(getString(R.string.attention));
        dialog.setMessage(getString(R.string.person_delete_confirmation_msg));
        dialog.setCancelable(true);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            PersonActivity.this.deletePerson();
            dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void backBtnClicked(View v) {
        super.onBackPressed();
    }

    private void post(Person person) throws Exception {
        Gson gson = new Gson();
        String content = gson.toJson(person);
        if (this.newRecord()) {
            this.postInsert(content);
        }
        else {
            this.postUpdate(content);
        }
    }

    private void postUpdate(String content) throws Exception {
        ODataClient.patch(PersonActivity.this, getResourcePath(), content, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                PersonActivity.this.apiError();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    PersonActivity.this.apiError();
                    return;
                }
                PersonActivity.this.performFinish(RECORD_UPDATED);
            }
        });
    }

    private void postInsert(String content) throws UnsupportedEncodingException {
        ODataClient.postJson(PersonActivity.this, "Customers", content, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                PersonActivity.this.performFinish(RECORD_UPDATED);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                PersonActivity.this.apiError();
            }
        });
    }

    private void deletePerson() {
        this.showSpinner(getString(R.string.person_deleting_msg));
        ODataClient.delete(PersonActivity.this, getResourcePath(), new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                PersonActivity.this.performFinish(RECORD_DELETED);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                PersonActivity.this.apiError();
            }
        });
    }

    private void performFinish(boolean deleted) {
        this.hideSpinner();
        Intent result = new Intent();
        result.putExtra(PersonActivity.PERSON_KEY, PersonActivity.this.mPerson);
        result.putExtra(PersonActivity.DELETE_FLAG, deleted);
        setResult(RESULT_OK, result);
        PersonActivity.this.finish();
    }

    private void setupFields() {
        this.mFirstNameEditText = (EditText) findViewById(R.id.personFirstNameTxt);
        this.mLastNameEditText = (EditText) findViewById(R.id.personLastNameTxt);
        this.mFirstNameEditText.setText(mPerson.getContactName());
        this.mDeleteBtn = (Button) findViewById(R.id.personDeleteBtn);
        if (this.newRecord()) {
            this.mDeleteBtn.setEnabled(false);
            this.mDeleteBtn.setVisibility(View.GONE);
        }
        this.mSpinner = new ProgressDialog(this);
    }

    private void showSpinner(String message) {
        if (this.mSpinner.isShowing()) {
            this.mSpinner.dismiss();
        }
        this.mSpinner.setMessage(message);
        this.mSpinner.setCancelable(false);
        this.mSpinner.show();
    }

    private void hideSpinner() {
        if (this.mSpinner != null) {
            this.mSpinner.dismiss();
        }
    }

    private void apiError() {
        PersonActivity.this.hideSpinner();
        Helper.showToast(PersonActivity.this, getString(R.string.person_network_failed_message));
    }

    private String getResourcePath() {
        if (this.mPerson == null || this.mPerson.getCustomerID() == null || this.mPerson.getCustomerID().isEmpty()) { return "Customers"; }
        return "Customers('" + this.mPerson.getCustomerID() + "')";
    }

    private boolean newRecord() {
        return (this.mPerson.getCustomerID() == null || this.mPerson.getCustomerID().isEmpty());
    }
}
