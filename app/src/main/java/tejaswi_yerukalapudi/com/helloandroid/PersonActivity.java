package tejaswi_yerukalapudi.com.helloandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import tejaswi_yerukalapudi.com.helloandroid.model.Person;


public class PersonActivity extends Activity {
    public static final String PERSON_KEY = "PERSON";

    private Person mPerson;
    private TextView mTitleTextView;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mDOBEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);


        mPerson = (Person) getIntent().getSerializableExtra(PERSON_KEY);
        if (mPerson == null) {
            mPerson = new Person();
        }

        this.setupFields();
    }

    public void personToolBarSaveBtnClicked(View v) {
        Intent result = new Intent();
        this.mPerson.setFirstName(this.mFirstNameEditText.getText().toString());
        this.mPerson.setLastName(this.mLastNameEditText.getText().toString());
        result.putExtra(PersonActivity.PERSON_KEY, this.mPerson);
        setResult(RESULT_OK, result);
        this.finish();
    }


    private void setupFields() {
        this.mFirstNameEditText = (EditText) findViewById(R.id.personFirstNameTxt);
        this.mLastNameEditText = (EditText) findViewById(R.id.personLastNameTxt);
        this.mFirstNameEditText.setText(mPerson.getFirstName());
        this.mLastNameEditText.setText(mPerson.getLastName());
    }
}
