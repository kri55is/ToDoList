package pounpong.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText mEditItemField;
    int mPositionItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String textItem = getIntent().getStringExtra("textItem");
        mPositionItem = getIntent().getIntExtra("positionItem", -1);

        mEditItemField = (EditText) findViewById(R.id.editItemField);
        mEditItemField.setText(textItem);
        mEditItemField.requestFocus();
    }

    public void onSaveButton(View view) {
        Intent data = new Intent();
        String text =  mEditItemField.getEditableText().toString();
        data.putExtra("textItem",text);
        data.putExtra("positionItem", mPositionItem);

        setResult(RESULT_OK, data);
        finish();
    }
}
