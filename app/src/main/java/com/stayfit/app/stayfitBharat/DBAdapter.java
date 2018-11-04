package com.stayfit.app.stayfitBharat;

/**
 * Created by bruker on 19.06.2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {

    /* 01 Variables ---------------------------------------- */
    private static final String databaseName = "dietbystram";
    private static final int databaseVersion = 57;

    /* 02 Database variables ------------------------------- */
    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;


    /* 03 Class DbAdapter ---------------------------------- */
    public DBAdapter(Context ctx){
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    /* 04 DatabaseHelper ------------------------------------ */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, databaseName, null, databaseVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try{
                // Create table goal
                db.execSQL("CREATE TABLE IF NOT EXISTS goal (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " goal_id INT, "+
                        " goal_current_weight INT, "+
                        " goal_target_weight INT, "+
                        " goal_i_want_to VARCHAR, "+
                        " goal_weekly_goal VARCHAR, "+
                        " goal_date DATE, "+
                        " goal_activity_level INT, " +
                        " goal_energy_bmr INT, "+
                        " goal_proteins_bmr INT, "+
                        " goal_carbs_bmr INT, "+
                        " goal_fat_bmr INT, "+
                        " goal_energy_diet INT, "+
                        " goal_proteins_diet INT, "+
                        " goal_carbs_diet INT, "+
                        " goal_fat_diet INT, "+
                        " goal_energy_with_activity INT, "+
                        " goal_proteins_with_activity INT, "+
                        " goal_carbs_with_activity INT, "+
                        " goal_fat_with_activity INT, "+
                        " goal_energy_with_activity_and_diet INT, "+
                        " goal_proteins_with_activity_and_diet INT, "+
                        " goal_carbs_with_activity_and_diet INT, "+
                        " goal_fat_with_activity_and_diet INT, "+
                        " goal_notes VARCHAR);");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                // Create tables
                db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " user_id INTEGER, " +
                        " user_email VARCHAR," +
                        " user_password VARCHAR, " +
                        " user_salt VARCHAR, " +
                        " user_alias VARCHAR," +
                        " user_dob DATE, " +
                        " user_gender INT, " +
                        " user_location VARHCAR, " +
                        " user_height INT, " +
                        " user_mesurment VARHCAR, " +
                        " user_last_seen TIME," +
                        " user_note VARCHAR);");

            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            try{
                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary_cal_eaten (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " fdce_id INTEGER, " +
                        " fdce_date DATE, " +
                        " fdce_meal_no INT, " +
                        " fdce_eaten_energy INT, " +
                        " fdce_eaten_proteins INT, " +
                        " fdce_eaten_carbs INT, " +
                        " fdce_eaten_fat INT);");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

            try{
                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary_sum (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " food_diary_sum_id INTEGER, " +
                        " food_diary_sum_date DATE, " +
                        " food_diary_sum_energy INT, " +
                        " food_diary_sum_proteins INT, " +
                        " food_diary_sum_carbs INT, " +
                        " food_diary_sum_fat INT);");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }



            try{
                db.execSQL("CREATE TABLE IF NOT EXISTS food_diary (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " fd_id INTEGER," +
                        " fd_date DATE," +
                        " fd_meal_number INT," +
                        " fd_food_id INT," +
                        " fd_serving_size_gram DOUBLE," +
                        " fd_serving_size_gram_mesurment VARCHAR," +
                        " fd_serving_size_pcs DOUBLE," +
                        " fd_serving_size_pcs_mesurment VARCHAR," +
                        " fd_energy_calculated DOUBLE," +
                        " fd_protein_calculated DOUBLE," +
                        " fd_carbohydrates_calculated DOUBLE," +
                        " fd_fat_calculated DOUBLE" +
                        " fd_meal_id INT);");

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            try{
                db.execSQL("CREATE TABLE IF NOT EXISTS categories (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " category_id INTEGER," +
                        " category_name VARCHAR," +
                        " category_parent_id INT," +
                        " category_icon VARCHAR," +
                        " category_note VARCHAR);");

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS food (" +
                        " _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        " food_id INTEGER, " +
                        " food_name VARCHAR," +
                        " food_manufactor_name VARCHAR," +
                        " food_store VARCHAR," +
                        " food_description VARCHAR," +
                        " food_serving_size_gram DOUBLE," +
                        " food_serving_size_gram_mesurment VARCHAR," +
                        " food_serving_size_pcs DOUBLE," +
                        " food_serving_size_pcs_mesurment VARCHAR," +
                        " food_energy DOUBLE," +
                        " food_proteins DOUBLE," +
                        " food_carbohydrates DOUBLE," +
                        " food_fat DOUBLE," +
                        " food_energy_calculated DOUBLE," +
                        " food_proteins_calculated DOUBLE," +
                        " food_carbohydrates_calculated DOUBLE," +
                        " food_fat_calculated DOUBLE," +
                        " food_user_id INT," +
                        " food_barcode VARCHAR," +
                        " food_category_id INT," +
                        " food_thumb VARCHAR," +
                        " food_image_a VARCHAR," +
                        " food_image_b VARCHAR," +
                        " food_image_c VARCHAR," +
                        " food_last_used DATE," +
                        " food_language VARCHAR," +
                        " food_notes VARCHAR);");



            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


            // ! All tables that are going to be dropped need to be listed here

            db.execSQL("DROP TABLE IF EXISTS goal");
            db.execSQL("DROP TABLE IF EXISTS users");
            db.execSQL("DROP TABLE IF EXISTS food_diary_cal_eaten");
            db.execSQL("DROP TABLE IF EXISTS food_diary_sum");
            db.execSQL("DROP TABLE IF EXISTS food_diary");
            db.execSQL("DROP TABLE IF EXISTS categories");
            db.execSQL("DROP TABLE IF EXISTS food");
            onCreate(db);

            String TAG = "Tag";
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");

        } // end public void onUpgrade
    } // DatabaseHelper


    /* 05 Open database --------------------------------------------------------- */
    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    /* 06 Close database --------------------------------------------------------- */
    public void close() {
        DBHelper.close();
    }

    /* 07 Quote smart ------------------------------------------------------------ */
    public String quoteSmart(String value){
        // Is numeric?
        boolean isNumeric = false;
        try {
            double myDouble = Double.parseDouble(value);
            isNumeric = true;
        }
        catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        if(isNumeric == false){
            // Escapes special characters in a string for use in an SQL statement
            if (value != null && value.length() > 0) {
                value = value.replace("\\", "\\\\");
                value = value.replace("'", "\\'");
                value = value.replace("\0", "\\0");
                value = value.replace("\n", "\\n");
                value = value.replace("\r", "\\r");
                value = value.replace("\"", "\\\"");
                value = value.replace("\\x1a", "\\Z");
            }
        }

        value = "'" + value + "'";

        return value;
    }
    public double quoteSmart(double value) { return value; }
    public int quoteSmart(int value) { return value; }
    public long quoteSmart(long value) { return value; }

    /* 08 Insert data ------------------------------------------------------------ */
    public void insert(String table, String fields, String values){

        try {
            db.execSQL("INSERT INTO " + table +  "(" + fields + ") VALUES (" + values + ")");
        }
        catch(SQLiteException e){
            System.out.println("Insert error: " + e.toString());
            Toast.makeText(context, "Error: " +  e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    /* 09 Count ------------------------------------------------------------------ */
    public int count(String table)
    {
        try {
            Cursor mCount = db.rawQuery("SELECT COUNT(*) FROM " + table + "", null);
            mCount.moveToFirst();
            int count = mCount.getInt(0);
            mCount.close();
            return count;
        }
        catch(SQLiteException e){
            return -1;
        }

    }

    /* 10 Select ----------------------------------------------------------------- */
    /* Select example:
           Cursor allCategories;
           String fields[] = new String[] {
                                "category_id",
                                "category_name",
                                "category_parent_id"
             };
            allCategories = db.select("categories", fields);
    */
    // Select
    public Cursor select(String table, String[] fields) throws SQLException
    {
        Cursor mCursor = db.query(table, fields, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // Select All where (String)
    public Cursor select(String table, String[] fields, String whereClause, String whereCondition) throws SQLException
    {
        Cursor mCursor = db.query(table, fields, whereClause + "=" + whereCondition, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // Select All where (String)
    public Cursor select(String table, String[] fields, String[] whereClause, String[] whereCondition, String[] whereAndOr) throws SQLException
    {
        /*
        Cursor cursorFdce;
        String fieldsFdce[] = new String[] {
                "_id",
                "fdce_id",
                "fdce_date",
                "fdce_meal_no",
                "fdce_eaten_energy",
                "fdce_eaten_proteins",
                "fdce_eaten_carbs",
                "fdce_eaten_fat"
        };
        String whereClause[] = new String[]{
                "fdce_date",
                "fdce_meal_no"
        };
        String whereCondition[] = new String[]{
                stringDateSQL,
                stringMealNumberSQL
        };
        String whereAndOr[] = new String[]{
                "AND"
        };*/
        String where = "";
        int arraySize = whereClause.length;
        for(int x=0;x<arraySize;x++) {
            if(where.equals("")) {
                where = whereClause[x] + "=" + whereCondition[x];
            }
            else{
                where = where + " " + whereAndOr[x-1] + " " + whereClause[x] + "=" + whereCondition[x];
            }
        }
        //Toast.makeText(context, where, Toast.LENGTH_SHORT).show();

        Cursor mCursor = db.query(table, fields, where, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    // Select All where (Long)
    public Cursor select(String table, String[] fields, String whereClause, long whereCondition) throws SQLException {
        Cursor mCursor = db.query(table, fields, whereClause + "=" + whereCondition, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    // Select with order
    public Cursor select(String table, String[] fields, String whereClause, String whereCondition, String orderBy, String OrderMethod) throws SQLException
    {
        Cursor mCursor = null;
        if(whereClause.equals("")) {
            // We dont want to se where
            mCursor = db.query(table, fields, null, null, null, null, orderBy + " " + OrderMethod, null);
        }
        else {
             mCursor = db.query(table, fields, whereClause + "=" + whereCondition, null, null, null, orderBy + " " + OrderMethod, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    /* 11 Update ----------------------------------------------------------------- */
        /* Update example:
        long id = 1;
        String value = "xxt@doesthiswork.com";
        String valueSQL = db.quoteSmart(value);
        db.update("users", "user_id", id, "user_email", valueSQL);
         */
        /*String updateFields[] = new String[] {
                    "fd_serving_size_gram",
                    "fd_serving_size_pcs",
                    "fd_energy_calculated",
                    "fd_protein_calculated",
                    "fd_carbohydrates_calculated",
                    "fd_fat_calculated"
            };
            String updateValues[] = new String[] {
                    fdServingSizeGramSQL,
                    stringFdServingSizePcsSQL,
                    stringFdEnergyCalcualtedSQL,
                    stringFdProteinsCalcualtedSQL,
                    stringFdCarbohydratesCalcualtedSQL,
                    stringFdFatCalcualtedSQL
            };
            long longPrimaryKey = Long.parseLong(currentFdId);
            db.update("food_diary", "_id", longPrimaryKey, updateFields, updateValues);
            */
    public boolean update(String table, String primaryKey, long rowId, String field, String value) throws SQLException {
        // Toast.makeText(context, "UPDATE " + table + " SET " + field + "=" + value + " WHERE " + primaryKey + "=" + rowId, Toast.LENGTH_SHORT).show();

        // Remove first and last value of value
        value = value.substring(1, value.length()-1); // removes apostrophe after running quote smart

        ContentValues args = new ContentValues();
        args.put(field, value);
        return db.update(table, args, primaryKey + "=" + rowId, null) > 0;
    }
    public boolean update(String table, String primaryKey, long rowId, String field, double value) throws SQLException {
        ContentValues args = new ContentValues();
        args.put(field, value);
        return db.update(table, args, primaryKey + "=" + rowId, null) > 0;
    }
    public boolean update(String table, String primaryKey, long rowId, String field, int value) throws SQLException {
        ContentValues args = new ContentValues();
        args.put(field, value);
        return db.update(table, args, primaryKey + "=" + rowId, null) > 0;
    }
    public boolean update(String table, String primaryKey, long rowID, String fields[], String values[]) throws SQLException {


        ContentValues args = new ContentValues();
        int arraySize = fields.length;
        for(int x=0;x<arraySize;x++){
            // Remove first and last value of value
            values[x] = values[x].substring(1, values[x].length()-1); // removes apostrophe after running quote smart

            // Put
            args.put(fields[x], values[x]);

            // Toast.makeText(context, fields[x].toString() + "=" + values[x].toString(), Toast.LENGTH_SHORT).show();
        }

        return db.update(table, args, primaryKey + "=" + rowID, null) > 0;
    }

    /* 12 Delete ----------------------------------------------------------------- */
    // Delete a particular record
    public int delete(String table, String primaryKey, long rowID) throws SQLException {
        return db.delete(table, primaryKey + "=" + rowID, null);
    }
}
