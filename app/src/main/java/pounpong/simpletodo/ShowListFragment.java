package pounpong.simpletodo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by emilie on 8/15/17.
 */

public class ShowListFragment extends Fragment {

    private static final String TAG = "ShowListFragment";
    //This is the request code of the edited item
    private final int REQUEST_CODE = 20;

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    Button btnAddItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_list, parent, false);

        populateArrayItems();
        lvItems = v.findViewById(R.id.lvItem);
        try {
            lvItems.setAdapter(aToDoAdapter);
            etEditText = v.findViewById(R.id.etEditText);

            lvItems.setOnItemLongClickListener(itemLongClickHandler);
            lvItems.setOnItemClickListener(itemClickHandler);
        }
        catch (Exception e){
            Log.d(TAG, e.toString());
        }
        btnAddItem = v.findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(btnClickHandler);
        return v;
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
                Intent intent = new Intent(getActivity(), EditItemActivity.class);
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
    public void onActivityResult(int request_code, int result_code, Intent data){
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
        else{
            Log.d(TAG,"REQUEST CODE or/and RESULT CODE not good: REQUEST_CODE=" + REQUEST_CODE + "RESULT_OK =" + RESULT_OK);
        }
    }

    public void readItems(){
        File filesDir = getActivity().getApplicationContext().getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            todoItems = new ArrayList<>(FileUtils.readLines(file));
        }
        catch(IOException e){
        }
    }
    public void writeItems(){
        File filesDir = getActivity().getApplicationContext().getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(file,todoItems);
        }
        catch(IOException e){

        }
    }

    public void populateArrayItems(){
        readItems();
        if (todoItems == null){
            todoItems =  new ArrayList<>();
        }
        aToDoAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,todoItems);
    }

    private View.OnClickListener btnClickHandler = new View.OnClickListener(){
        public void onClick(View v)
        {
            aToDoAdapter.add(etEditText.getText().toString());
            etEditText.setText("");
            writeItems();
        }
    };


}
