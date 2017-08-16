package pounpong.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private static final String TAG = "EditItemActivity";

    EditText mEditItemField;
    int mPositionItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the information about the item and its id
        String textItem = getIntent().getStringExtra("textItem");
        mPositionItem = getIntent().getIntExtra("positionItem", -1);

        mEditItemField = (EditText) findViewById(R.id.editItemField);

        if (textItem != null) {
            mEditItemField.setText(textItem);
        }
        mEditItemField.requestFocus();
    }

    public void onSaveButton(View view) {
        Intent data = new Intent();
        String text =  mEditItemField.getEditableText().toString();
        data.putExtra("textItem",text);
        //if  mPositionItem == -1 it's a problem as it's an index in an array
        if (mPositionItem > -1) {
            data.putExtra("positionItem", mPositionItem);

            setResult(RESULT_OK, data);
        }
        else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
}
