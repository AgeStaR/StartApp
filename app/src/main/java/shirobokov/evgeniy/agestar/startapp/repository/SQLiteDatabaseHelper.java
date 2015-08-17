package shirobokov.evgeniy.agestar.startapp.repository;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.util.Log;

import shirobokov.evgeniy.agestar.startapp.repository.tables.TreeTable;

/**
 * Created by Евгений on 13.08.2015.
 */
public class SQLiteDatabaseHelper extends SQLiteOpenHelper {
    final static int database_version = 1;
    final static String DATABASE_NAME = "evgeniy.shirobokov4.db";
    private Context context;

    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, database_version);
        try {
            this.context = context;
            Log.d("SQLiteDatabaseHelper", "Db created.");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Constructor", e.toString());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(TreeTable.CREATE_QUERY);
            Log.d("OnCreate", "Query executed.");
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d("OnCreate", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
