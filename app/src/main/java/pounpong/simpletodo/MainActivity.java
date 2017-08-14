package pounpong.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    //This is the request code of the edited item
    private final int REQUEST_CODE = 20;

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItem);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(itemLongClickHandler);
        lvItems.setOnItemClickListener(itemClickHandler);
    }

    private  AdapterView.OnItemLongClickListener itemLongClickHandler= new AdapterView.OnItemLongClickListener(){
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            Log.d(TAG, "long click received at position " + position);
            try {
                todoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();

            }
            catch (Exception e){
                Log.d(TAG, e.toString());
            }
            return true;
        }
    };

    private AdapterView.OnItemClickListener itemClickHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id)
        {
            Log.d("MainActivity", "one click received at position " + position);
            //Do something only if we have items in the list
            try{
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("textItem", todoItems.get(position));
                intent.putExtra("positionItem", position);
                startActivityForResult(intent, REQUEST_CODE);
            }
            catch (Exception e){
                Log.d(TAG, e.toString());
            }
        }
    };

    @Override
    protected void onActivityResult(int request_code, int result_code, Intent data){
        //We got new value for an item
        if (result_code == RESULT_OK && request_code == REQUEST_CODE){
            String textItem = data.getExtras().getString("textItem");
            int positionItem = data.getIntExtra("positionItem", -1);

            int sizeToDo = todoItems.size();
            if(positionItem != -1
                    && positionItem < sizeToDo
                    && textItem != null){
                todoItems.set(positionItem, textItem);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();

            }
        }
    }

    public void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }
        catch(IOException e){
        }
    }
    public void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(file,todoItems);
        }
        catch(IOException e){

        }
    }

    public void populateArrayItems(){
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,todoItems);

    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
}