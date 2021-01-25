package com.edu.aralpan.aralpanmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.edu.aralpan.aralpanmobile.model.Question;
import com.edu.aralpan.aralpanmobile.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";

    //DB version, table and database name
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "quizdb";
    private static final String DB_TABLE = "quiztable";
    private static final String DB_TABLE_USER = "usertable";

    //table column namesDatabaseHelper
    private static final String KEY_ID = "id";
    private static final String KEY_QUES = "question";
    private static final String KEY_ANSWER = "answer";
    private static final String KEY_OPTA = "optA";
    private static final String KEY_OPTB = "optB";
    private static final String KEY_OPTC = "optC";
    private static final String KEY_OPTD = "optD";
    private static final String KEY_LEVEL = "level";


    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME= "user_name";
    private static final String KEY_USER_GAME_MODE= "user_game_mode";
    private static final String KEY_USER_GAME_LEVEL= "user_game_level";
    private static final String KEY_USER_GAME_SCORE= "user_game_score";



    private SQLiteDatabase dbase;
    private int rowCount = 0;



    public DbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;
        String sqlQuery = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT )", DB_TABLE, KEY_ID, KEY_QUES, KEY_ANSWER, KEY_OPTA, KEY_OPTB, KEY_OPTC, KEY_OPTD, KEY_LEVEL);
        String sqlQueryUser = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT,  %s TEXT,  %s TEXT,  %s TEXT )", DB_TABLE_USER, KEY_USER_ID, KEY_USER_NAME, KEY_USER_GAME_MODE, KEY_USER_GAME_LEVEL, KEY_USER_GAME_SCORE);

        Log.d("TaskDBHelper", "Query to form table" + sqlQuery);
        db.execSQL(sqlQuery);
        db.execSQL(sqlQueryUser);
        addQuestions();
    }

    public long addUserDetail(String name, String mode, String level, String score) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, name);
        values.put(KEY_USER_GAME_MODE, mode);
        values.put(KEY_USER_GAME_LEVEL, level);
        values.put(KEY_USER_GAME_SCORE, score);

        // insert row in students table
        long insert = db.insert(DB_TABLE_USER, null, values);
        return insert;
    }

    public ArrayList<UserModel> getAllUsers() {
        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();

        String selectQuery = "SELECT  * FROM " + DB_TABLE_USER + " ORDER BY " + "-" + KEY_USER_GAME_SCORE + " LIMIT 10" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                UserModel userModel = new UserModel();
                userModel.setId(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                userModel.setUser(c.getString(c.getColumnIndex(KEY_USER_NAME)));
                userModel.setUser_game_mode(c.getString(c.getColumnIndex(KEY_USER_GAME_MODE)));
                userModel.setUser_level(c.getString(c.getColumnIndex(KEY_USER_GAME_LEVEL)));
                userModel.setUser_score(c.getString(c.getColumnIndex(KEY_USER_GAME_SCORE)));

                // adding to Students list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        return userModelArrayList;
    }


    private void addQuestions() {
        Question q1 = new Question("Sa anong taon natapos ni Galileo ang teleskopyo?", "1609", "1760", "1582", "1612","1612", "Easy" );
        this.addQuestionToDB(q1);
        Question q2 = new Question("Sino ang binigyan ng titulong (The Navigator)?", "Amerigo Vespucci", "Christopher Columbus", "Ferdinand Magellan", "Prinsipe Henry","Prinsipe Henry", "Easy" );
        this.addQuestionToDB(q2);
        Question q3 = new Question("Pangalwa sa pinakamaliit na kontinente ng daigdig", "Asya", "Antartica", "Africa", "Europe","Europe", "Easy");
        this.addQuestionToDB(q3);
        Question q4 = new Question("Tumutukoy sa muling pagsilang o rebirth ng kulturang klasikal ng Greece na sumibol sa bansang Italya.", "Rebolusyon", "Renaissance", "Repormasyon", "Reaksiyon","Reaksiyon", "Easy" );
        this.addQuestionToDB(q4);
        Question q5 = new Question("Ito ay tumutukoy sa isang teritoryo na pinananahan ng mamamayan na may magkakatulad na wika, kultura, relihiyon at kasaysayan at napasasailalim sa isang pamahalaan.", "Lungsod-estado", "Nation-state", "Repormasyon", "Nasyonalismo","Physiocrats", "Average" );
        this.addQuestionToDB(q5);
        Question q6 = new Question("Isang kaganapan na yumanig sa mga Kristyano na humantong sa pagkakahati ng Simbahang Kristyano.", "Rebolusyon", "Renaissance", "Repormasyon", "Reaksyon","Reaksyon", "Average" );
        this.addQuestionToDB(q6);
        Question q7 = new Question("Ito ay isang patakarang pang-ekonomiya na nakatuon sa pag-iipon ng mga mahahalagang metal tulad ng ginto at tanso.", "Bullionism", "Imperyalismo", "Merkantilismo", "Nationalismo","Nationalismo", "Average" );
        this.addQuestionToDB(q7);
        Question q8 = new Question("Sino ang  (Ama ng Humanismo)?", "Desiderious Erasmus", "Francesco Petrarch", "Giovanni Boccacio", "William Shakespare","William Shakespare", "Difficult" );
        this.addQuestionToDB(q8);
        Question q9 = new Question("Ang estatwa ni David ay gawa ng pinakasikat na iskultor ng Renaissance na si ______.", "Leonardo da Vinci", "Michelangelo Bounarotti", "Raphael Santi", "Sir Isaac Newton","Sir Isaac Newton", "Difficult" );
        this.addQuestionToDB(q9);
        Question q10 = new Question("Ito ang nagbigay-daan sa pagsakop ng isang makapangyarihang bansa sa isang mahinang bansa.", "Eksplorasyon", "Imperyalismo", "Kolonisasyon", "Kolonyalismo","Kolonyalismo", "Difficult" );
        this.addQuestionToDB(q10);

    }

    public void addQuestionToDB(Question q){
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, q.getQuestion());
        values.put(KEY_ANSWER,q.getAnswer());
        values.put(KEY_OPTA,q.getOptA());
        values.put(KEY_OPTB,q.getOptB());
        values.put(KEY_OPTC,q.getOptC());
        values.put(KEY_OPTD,q.getOptD());
        values.put(KEY_LEVEL,q.getLevel());
        dbase.insert(DB_TABLE, null, values);
    }

    public List<Question> getAllQuestions(){
        List <Question> questionList = new ArrayList<Question>();

        dbase = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM "+DB_TABLE+" WHERE level = 'Easy' " ;
        Cursor cursor = dbase.rawQuery("select * from quiztable where level='Easy'",null );
        rowCount = cursor.getCount();

        if(cursor.moveToFirst()){
            do{
                Question q = new Question();
                q.setId(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setAnswer(cursor.getString(2));
                q.setOptA(cursor.getString(3));
                q.setOptB(cursor.getString(4));
                q.setOptC(cursor.getString(5));
                q.setOptD(cursor.getString(6));
                q.setLevel(cursor.getString(7));

                questionList.add(q);

            }while (cursor.moveToNext());
        }
        return questionList;
    }

    public List<Question> getAllQuestionsAverage(){
        List <Question> questionList = new ArrayList<Question>();

        dbase = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM "+DB_TABLE+" WHERE level = 'Easy' " ;
        Cursor cursor = dbase.rawQuery("select * from quiztable where level='Average'",null );
        rowCount = cursor.getCount();

        if(cursor.moveToFirst()){
            do{
                Question q = new Question();
                q.setId(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setAnswer(cursor.getString(2));
                q.setOptA(cursor.getString(3));
                q.setOptB(cursor.getString(4));
                q.setOptC(cursor.getString(5));
                q.setOptD(cursor.getString(6));
                q.setLevel(cursor.getString(7));

                questionList.add(q);

            }while (cursor.moveToNext());
        }
        return questionList;
    }

    public List<Question> getAllDifficult(){
        List <Question> questionList = new ArrayList<Question>();

        dbase = this.getReadableDatabase();
        //String selectQuery = "SELECT * FROM "+DB_TABLE+" WHERE level = 'Easy' " ;
        Cursor cursor = dbase.rawQuery("select * from quiztable where level='Difficult'",null );
        rowCount = cursor.getCount();

        if(cursor.moveToFirst()){
            do{
                Question q = new Question();
                q.setId(cursor.getInt(0));
                q.setQuestion(cursor.getString(1));
                q.setAnswer(cursor.getString(2));
                q.setOptA(cursor.getString(3));
                q.setOptB(cursor.getString(4));
                q.setOptC(cursor.getString(5));
                q.setOptD(cursor.getString(6));
                q.setLevel(cursor.getString(7));

                questionList.add(q);

            }while (cursor.moveToNext());
        }
        return questionList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+DB_TABLE_USER);
        onCreate(db);
    }

    public int getRowCount(){
        return rowCount;
    }
}
