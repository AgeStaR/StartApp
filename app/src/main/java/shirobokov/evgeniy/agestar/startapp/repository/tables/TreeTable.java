package shirobokov.evgeniy.agestar.startapp.repository.tables;

/**
 * Created by Евгений on 14.08.2015.
 */
public class TreeTable {
    public final static String TABLE_NAME = "tree";
    public final static String ID = "id";
    public final static String TITLE = "title";
    public final static String PARENT_ID = "parent_id";
    public final static String CREATE_QUERY = "CREATE TABLE (" + ID + "INTEGER PRIMARY KEY, " + TITLE + " TEXT, " + PARENT_ID + " INTEGER, " + "FOREIGN KEY(" + PARENT_ID + ")" + " " + TABLE_NAME + "(" + ID + ")" + ");";
}
