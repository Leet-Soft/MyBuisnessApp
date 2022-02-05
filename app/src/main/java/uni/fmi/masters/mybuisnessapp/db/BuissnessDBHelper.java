package uni.fmi.masters.mybuisnessapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import uni.fmi.masters.mybuisnessapp.entity.UserEntity;

public class BuissnessDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "mydb.sqlite";
    public static final int DB_VERSION = 1;

    public static final String USER_TABLE = "user";

    public static final String USER_ID = "id";
    public static final String USER_EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PASSWORD = "password";
    public static final String GENDER = "gender";
    public static final String AVATAR = "avatar";

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + USER_TABLE
            + "('" + USER_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + USER_EMAIL + "' varchar(250) NOT NULL UNIQUE," +
            "'" + FIRST_NAME + "' varchar(100)," +
            "'" + LAST_NAME + "' varchar(100)," +
            "'" + PASSWORD + "' varchar(60) NOT NULL," +
            "'" + GENDER + "' varchar(100)," +
            "'" + AVATAR + "' varchar(250));";

    public static final String SQL_ERROR = "SQL Error";

    public BuissnessDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertUser(UserEntity user){

        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(USER_EMAIL, user.getEmail());
            cv.put(FIRST_NAME, user.getFirstName());
            cv.put(LAST_NAME, user.getLastName());
            cv.put(PASSWORD, user.getPassword());
            cv.put(GENDER, user.getGender());
            cv.put(AVATAR, user.getAvatar());

            long id = db.insert(USER_TABLE, null, cv);

            if (id != -1)
                return true;
            else
                return false;
        }finally {
            if(db != null)
                db.close();
        }
    }

    public boolean checkCredentials(String username, String password){

        SQLiteDatabase db = null;
        Cursor c = null;
        try{
            db = getReadableDatabase();

            String query = "SELECT * FROM " + USER_TABLE +
                    " WHERE " + USER_EMAIL + " = '" + username + "'" +
                    " AND " + PASSWORD + " = '" + password + "';";

            c = db.rawQuery(query, null);

            return c.moveToFirst();

        }catch (SQLException e){
            Log.e(SQL_ERROR, e.getMessage());
        }finally {
            if(c != null)
                c.close();

            if(db != null)
                db.close();
        }

        return false;
    }
}
