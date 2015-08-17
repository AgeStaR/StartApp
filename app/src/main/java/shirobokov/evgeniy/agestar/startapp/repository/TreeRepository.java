package shirobokov.evgeniy.agestar.startapp.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import shirobokov.evgeniy.agestar.startapp.models.Tree;
import shirobokov.evgeniy.agestar.startapp.repository.tables.TreeTable;

/**
 * Created by Евгений on 14.08.2015.
 */
public class TreeRepository {

    private SQLiteDatabaseHelper db;

    public TreeRepository(SQLiteDatabaseHelper db) {
        this.db = db;
    }

    public List<Tree> getTreeList() {
        SQLiteDatabase database = db.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TreeTable.TABLE_NAME + " ORDER BY " + TreeTable.ORDER + " ASC;";
        List<Tree> treeList = new ArrayList<>();

        Cursor c = database.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Tree tree = new Tree();
                tree.setId(c.getLong((c.getColumnIndex(TreeTable.ID))));
                tree.setTitle((c.getString(c.getColumnIndex(TreeTable.TITLE))));
                tree.setOrder((c.getLong(c.getColumnIndex(TreeTable.ORDER))));
                if (c.isNull(c.getColumnIndex(TreeTable.PARENT_ID))) {
                    tree.setParentId(null);
                } else {
                    tree.setParentId(c.getLong(c.getColumnIndex(TreeTable.PARENT_ID)));
                }
                treeList.add(tree);
            } while (c.moveToNext());
        }
        return treeList;
    }

    public void createTree(Tree tree) {
        SQLiteDatabase database = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TreeTable.ID, tree.getId());
        values.put(TreeTable.TITLE, tree.getTitle());
        values.put(TreeTable.ORDER, tree.getOrder());
        values.put(TreeTable.PARENT_ID, tree.getParentId());
        database.insertOrThrow(TreeTable.TABLE_NAME, null, values);
    }

    public void createTreeList(List<Tree> treeList) {
        for (Tree tree : treeList) {
            createTree(tree);
        }
    }

    public void deleteAll() {
        SQLiteDatabase database = db.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TreeTable.TABLE_NAME;
        database.execSQL(deleteQuery);
    }
}
