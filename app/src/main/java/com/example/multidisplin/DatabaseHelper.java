package com.example.multidisplin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "multidisplin";
    public static final String TABLE_ANSWER = "table_answer";
    public static final String TABLE_USER = "table_user";
    public static final String TABLE_GRADE = "table_grade";

    String temp;


    public static final String createTable_answer = "create table " + TABLE_ANSWER +
            "(id INTEGER PRIMARY KEY, nim TXT, name TXT, title TXT, answer TXT)";

    public static final String createTable_User = "create table " + TABLE_USER +
            "(id INTEGER PRIMARY KEY, username TXT, email TXT, password TXT, status BOOL)";

    public static final String createTable_Grade = "create table " + TABLE_GRADE +
            "(id INTEGER PRIMARY KEY, answerId INTEGER, grade TXT, mistake TXT, correction TXT, start_posititons INTEGER, end_position INTEGER)";

    DatabaseHelper (Context context){
        super(context, TABLE_ANSWER, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable_User);
        db.execSQL(createTable_answer);
        db.execSQL(createTable_Grade);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWER );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADE);
        onCreate(db);
    }

//    public boolean addAnswer(int nim, String name, String title, String answer){
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("nim", nim);
//        contentValues.put("name", name);
//        contentValues.put("title", title);
//        contentValues.put("answer",answer);
//
//        sqLiteDatabase.insert(TABLE_ANSWER, null, contentValues);
//        return true;
//    }

    //answer
    public boolean addAnswer(ModelAnswer modelAnswer) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nim", modelAnswer.getNim());
        contentValues.put("name", modelAnswer.getName());
        contentValues.put("title", modelAnswer.getTitle());
        contentValues.put("answer", modelAnswer.getAnswer());

        sqLiteDatabase.insert(TABLE_ANSWER, null, contentValues);
        return true;
    }

    public boolean addAnswerGrammarCheck(ModelGrade modelGrade){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("answerId", modelGrade.getAnswerId());
        contentValues.put("mistake", modelGrade.getMistake());
        contentValues.put("correction", modelGrade.getCorrection());
        contentValues.put("start_posititons", modelGrade.getStart_posititons());
        contentValues.put("end_position", modelGrade.getEnd_position());

        sqLiteDatabase.insert(TABLE_GRADE,null, contentValues);
        return true;
    }

    public boolean addAnswerGrade(ModelGrade modelGrade){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("answerId", modelGrade.getAnswerId());
        contentValues.put("grade", modelGrade.getGrade());

        sqLiteDatabase.insert(TABLE_GRADE,null, contentValues);
        return true;
    }


    // list history di menu
    public ArrayList getName(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<>();

        Cursor cursor =sqLiteDatabase.rawQuery("select id,nim, name from " + TABLE_ANSWER,
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            temp = cursor.getInt(cursor.getColumnIndex("id")) + ")" +
                    "nim: " + cursor.getString(cursor.getColumnIndex("nim")) + "\n" +
                    "name: " + cursor.getString(cursor.getColumnIndex("name"));
            arrayList.add(temp);
            cursor.moveToNext();
        }
        return arrayList;
    }

    public Cursor getAllHistory() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("select id,nim, name, title from " + TABLE_ANSWER,
                null);

        return cursor;
    }

    public Cursor getFilteredHistory(String patern) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ANSWER + " WHERE name like \"%" + patern +"%\"",
                null);

        return cursor;
    }


//    public List<AnswerModel> getTableAnswer() {
//        List<AnswerModel> returnList = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//
//        Cursor cursor =sqLiteDatabase.rawQuery("select * from " + TABLE_ANSWER,
//                null);
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()){
//            int nim = cursor.getInt(cursor.getColumnIndex("nim"));
//            String name = cursor.getString(cursor.getColumnIndex("name"));
//            String title = cursor.getString(cursor.getColumnIndex("title"));
//            String answer = cursor.getString(cursor.getColumnIndex("answer"));
//
//            AnswerModel answerModel = new AnswerModel(nim, name, title, answer);
//            returnList.add(answerModel);
//
//            cursor.moveToNext();
//        }
//
//        return returnList;
//    }


    //result
    public ModelAnswer historyDeatil(String name){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ANSWER + " WHERE name like \"" + name + "\"",
                null);

        if (cursor != null && cursor.moveToFirst()){
            String thisnim = cursor.getString(cursor.getColumnIndex("nim"));
            String thisname = cursor.getString(cursor.getColumnIndex("name"));
            String thistitle = cursor.getString(cursor.getColumnIndex("title"));
            String thisanswer = cursor.getString(cursor.getColumnIndex("answer"));

            ModelAnswer model = new ModelAnswer(thisnim, thisname, thistitle, thisanswer);
            return model;
        }
        return null;
    }

    public ModelAnswer historyDeatil(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ANSWER + " WHERE id =" + id ,
                null);

        if (cursor != null && cursor.moveToFirst()){
            int thisid = cursor.getInt(cursor.getColumnIndex("id"));
            String thisnim = cursor.getString(cursor.getColumnIndex("nim"));
            String thisname = cursor.getString(cursor.getColumnIndex("name"));
            String thistitle = cursor.getString(cursor.getColumnIndex("title"));
            String thisanswer = cursor.getString(cursor.getColumnIndex("answer"));

            ModelAnswer model = new ModelAnswer(thisid, thisnim, thisname, thistitle, thisanswer);
            return model;
        }
        return null;
    }

    // menampilakn result gramarchekresult dari id terakhir yang di insert
    public ModelAnswer gcr(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_ANSWER + " WHERE id = (SELECT MAX(id) FROM " + TABLE_ANSWER + ")"  ,
                null);

        if (cursor != null && cursor.moveToFirst()){
            int thisid = cursor.getInt(cursor.getColumnIndex("id"));
            String thisnim = cursor.getString(cursor.getColumnIndex("nim"));
            String thisname = cursor.getString(cursor.getColumnIndex("name"));
            String thistitle = cursor.getString(cursor.getColumnIndex("title"));
            String thisanswer = cursor.getString(cursor.getColumnIndex("answer"));

            ModelAnswer model = new ModelAnswer(thisid, thisnim, thisname, thistitle, thisanswer);
            return model;
        }
        return null;
    }

    public String getGrade(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_GRADE + " WHERE answerId = " + id + " AND grade IS NOT NULL",
                null);

        if (cursor != null && cursor.moveToFirst()){
            String grade = cursor.getString(cursor.getColumnIndex("grade"));
            return grade;
        }
        return "ini null";
    }

    public ArrayList getMistake(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> mistake = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_GRADE + " WHERE answerId = " + id + " AND mistake IS NOT NULL",
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            temp = cursor.getString(cursor.getColumnIndex("mistake"));
            mistake.add(temp);
            cursor.moveToNext();
        }

        return mistake;
    }

    public ArrayList getCorrection(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> correction = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_GRADE + " WHERE answerId = " + id + " AND correction IS NOT NULL",
                null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            temp = cursor.getString(cursor.getColumnIndex("correction"));
            correction.add(temp);
            cursor.moveToNext();
        }

        return correction;
    }

    //uSER
    public boolean addUser (ModelUser modelUser){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("username", modelUser.getUsername());
        contentValues.put("email", modelUser.getEmail());
        contentValues.put("password", modelUser.getPasword());
        contentValues.put("status", modelUser.isStatus());

        sqLiteDatabase.insert(TABLE_USER, null, contentValues);
        return true;
    }


    //register
    public int checkUser (String username){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_USER + " WHERE username like \"" + username + "\"",
                null);

        if (cursor != null && cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            return id;
        } else{
            return 0;
        }
    }

    //login
    public int checkUser (String username, String pass){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_USER + " WHERE username like \"" + username + "\" and password like \"" + pass + "\"",
                null);

        if (cursor != null && cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            return id;
        } else{
            return 0;
        }
    }

    //setting->change pass
    public int checkUser (int id, String pass){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery( "SELECT * FROM " + TABLE_USER + " WHERE  password LIKE \"" + pass + "\"" ,
                null);

        if (cursor != null && cursor.moveToFirst()){
            return 1;
        }else {
            return 0;
        }
    }


    // setting dan menu
    public String getUsername (int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select username from " + TABLE_USER + " where id = " + id,
                null);

        if (cursor != null && cursor.moveToFirst()){
            String username = cursor.getString(cursor.getColumnIndex("username"));
            return username;
        } else {
            return  null;
        }

    }

    protected int getstatus (int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select status from " + TABLE_USER + " where id = " + id ,
                null);

        if (cursor != null && cursor.moveToFirst()){
            int status = cursor.getInt(cursor.getColumnIndex("status"));
            return status;
        } else {
            return -1;
        }
    }


    //setting
    public boolean Change_userName (int id, String newUsername){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        try {
            String query = "UPDATE " + TABLE_USER + " SET username = \"" + newUsername + "\" WHERE id = "+ id;
            sqLiteDatabase.execSQL(query);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    //setting
    public boolean Change_pass (int id, String pass){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        try {
            String query = "UPDATE " + TABLE_USER + " SET password = \"" + pass + "\" WHERE id = "+ id;
            sqLiteDatabase.execSQL(query);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
