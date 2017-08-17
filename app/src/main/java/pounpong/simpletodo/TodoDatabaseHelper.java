package pounpong.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Emilie on 17/08/2017.
 */

public class TodoDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "TodoDatabaseHelper";
    private static TodoDatabaseHelper sInstance = null;

    public static synchronized TodoDatabaseHelper getInstance(Context context){
        if (sInstance == null){
            sInstance = new TodoDatabaseHelper(context.getApplicationContext());
        }
         return sInstance;
    }

    // Database Info
    private static final String DATABASE_NAME = "todoDatabase";
    private static final int DATABASE_VERSION = 2;

    // Table Names
    private static final String TABLE_TODO = "todo";

    // Table Columns
    private static final String KEY_TODO_ID = "id";
    private static final String KEY_TODO_TEXT = "todoText";
    private static final String KEY_TODO_CHECKED = "todoChecked";

    private TodoDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TODO_TABLE = "CREATE TABLE " +
                TABLE_TODO +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_TODO_TEXT + " TEXT," + // Define a foreign key
                KEY_TODO_CHECKED + " INTEGER" +
                ")";

        db.execSQL(CREATE_TODO_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
            onCreate(db);
        }
    }

    // Insert a item into the database
    public void addTodoItem(TodoItem item) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            // The item might already exist in the database (i.e. the same item created multiple items).
            //long itemId = addOrUpdateItem(item);

            ContentValues values = new ContentValues();
            values.put(KEY_TODO_TEXT, item.getItemText());
            values.put(KEY_TODO_CHECKED, item.getCheckStatus());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_TODO, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }
    }

    // Insert or update a item in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // item already exists) optionally followed by an INSERT (in case the item does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the item's primary key if we did an update.
    public long addOrUpdateItem(TodoItem item) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        long itemId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_TODO_TEXT,item.getItemText());
            values.put(KEY_TODO_CHECKED, item.getCheckStatus());

            // First try to update the item in case the item already exists in the database
            // This assumes itemText are unique
            int rows = db.update(TABLE_TODO, values, KEY_TODO_TEXT + "= ?", new String[]{item.getItemText()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the item we just updated
                String itemsSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_TODO_ID, TABLE_TODO, KEY_TODO_TEXT);
                Cursor cursor = db.rawQuery(itemsSelectQuery, new String[]{String.valueOf(item.getItemText())});
                try {
                    if (cursor.moveToFirst()) {
                        itemId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // item with this text did not already exist, so insert new item
                itemId = db.insertOrThrow(TABLE_TODO, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update item");
        } finally {
            db.endTransaction();
        }
        return itemId;
    }

    public ArrayList<TodoItem> getAllItems() {
        ArrayList<TodoItem> items = new ArrayList<>();


        String ITEMS_SELECT_QUERY =
                String.format("SELECT * FROM " + TABLE_TODO);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    TodoItem newItem = new TodoItem();
                    newItem.setItemText(cursor.getString(cursor.getColumnIndex(KEY_TODO_TEXT)));
                    int checked = cursor.getInt(cursor.getColumnIndex(KEY_TODO_CHECKED));
                    newItem.setCheckStatus(checked != 0);

                    items.add(newItem);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }

    public ArrayList<String> getAllItemsText() {
        ArrayList<String> items = new ArrayList<>();


        String ITEMS_SELECT_QUERY =
                String.format("SELECT * FROM " + TABLE_TODO);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(ITEMS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    String text = cursor.getString(cursor.getColumnIndex(KEY_TODO_TEXT));

                    items.add(text);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get items from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return items;
    }


    // Update the item's checked status
    public int updateItemCheckedStatus(TodoItem item, String newText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO_CHECKED, newText);

        // Updating text for item with that newText
        return db.update(TABLE_TODO, values, KEY_TODO_TEXT + " = ?",
                new String[] { String.valueOf(item.getItemText()) });
    }

    // Update the item's text
    public int updateItemText(String oldText, String newText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TODO_TEXT, newText);

        // Updating text for item with that oldText
        return db.update(TABLE_TODO, values, KEY_TODO_TEXT + " = ?",
                new String[] { String.valueOf(newText) });
    }

    public void deleteAllItems() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_TODO, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all items");
        } finally {
            db.endTransaction();
        }
    }


}
