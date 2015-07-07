package tejaswi_yerukalapudi.com.helloandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.google.gson.reflect.TypeToken;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

import tejaswi_yerukalapudi.com.helloandroid.lib.http.ODataClient;
import tejaswi_yerukalapudi.com.helloandroid.model.Person;


public class PersonActivity extends Activity {
    public static final String PERSON_KEY = "person";
    public static final String DELETE_FLAG = "deleted";
    private static final String SAVE_FAILURE_MESSAGE = "Oops, something went wrong while trying to save your data. Please try again!";

    AsyncHttpResponseHandler responseHandler = new AsyncHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            PersonActivity.this.performFinish(false);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            PersonActivity.this.hideSpinner();
            Toast t = Toast.makeText(PersonActivity.this, SAVE_FAILURE_MESSAGE, Toast.LENGTH_SHORT);
            t.show();
        }
    };

    private Person mPerson;
    private boolean mEditing;
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

        mEditing = true;
        mPerson = (Person) getIntent().getSerializableExtra(PERSON_KEY);
        // If a person object wasn't passed to us explicitly, we assume the user
        // is trying to create a new record
        if (mPerson == null) {
            mEditing = false;
            mPerson = new Person();
        }

        this.setupFields();
    }

    public void personSaveBtnClicked(View v) {
        this.showSpinner("Saving..");
        this.mPerson.setContactName(this.mFirstNameEditText.getText().toString());
        // this.mPerson.setLastName(this.mLastNameEditText.getText().toString());
        Gson gson = new Gson();
        String content = gson.toJson(mPerson);
        try {
            if (this.mEditing) {
                this.postUpdate(content);
            }
            else {
                this.postInsert(content);
            }
        }
        catch(UnsupportedEncodingException ex) {
            this.hideSpinner();
            Toast t = Toast.makeText(this, SAVE_FAILURE_MESSAGE, Toast.LENGTH_SHORT);
            t.show();
        }
    }

    public void personDeleteBtnClicked(View v) {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Attention");
        dialog.setMessage("Are you sure you wish to delete record?");
        dialog.setCancelable(true);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PersonActivity.this.deletePerson();
                dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
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

    private void deletePerson() {
        this.showSpinner("Deleting..");
        ODataClient.delete(PersonActivity.this, "Customers('" + mPerson.getCustomerID() + "')", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                PersonActivity.this.performFinish(true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                PersonActivity.this.hideSpinner();
                Toast t = Toast.makeText(PersonActivity.this, SAVE_FAILURE_MESSAGE, Toast.LENGTH_SHORT);
                t.show();
            }
        });
    }

    private void postUpdate(String content) throws UnsupportedEncodingException {
        ODataClient.putJson(PersonActivity.this, "Customers('" + mPerson.getCustomerID() + "')", content, responseHandler);
    }

    private void postInsert(String content) throws  UnsupportedEncodingException {
        ODataClient.postJson(PersonActivity.this, "Customers", content, responseHandler);
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
        if (!this.mEditing) {
            this.mDeleteBtn.setEnabled(false);
            this.mDeleteBtn.setVisibility(View.GONE);
        }
//        this.mLastNameEditText.setText(mPerson.getLastName());
    }

    private void showSpinner(String message) {
        this.mSpinner = new ProgressDialog(this);
        this.mSpinner.setMessage(message);
        this.mSpinner.setCancelable(false);
        this.mSpinner.show();
    }

    private void hideSpinner() {
        if (this.mSpinner != null) {
            this.mSpinner.hide();
        }
    }
}
