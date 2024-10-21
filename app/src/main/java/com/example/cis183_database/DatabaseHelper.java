package com.example.cis183_database;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String database_name = "Blog.db";
    private static final String users_table_name = "Users";
    private static final String posts_table_name = "Posts";
    public DatabaseHelper(Context c)
    {
        //we will use this to create the database
        //it accepts the context, the name, factory (leave null), and version number
        //If you database becomes corrupt or the information in the database is wrong
        //change the version number
        //super is sued to call the functionality of the base class SQLiteOpenHelper and
        //then executes the extended (DatabaseHelper)

        super(c,database_name,null,2);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Create table in the database
        //execute the sql statement on the database that was passed to the function onCreate called db
        //This can be tricky because we have to write our sql statements as strings

        db.execSQL("CREATE TABLE " + users_table_name + " (userId integer primary key autoincrement not null, fname varchar(50), lname varchar(50), email varchar(50));");
        db.execSQL("CREATE TABLE " + posts_table_name + "(postId integer primary key autoincrement not null, userId integer, category varchar(50), postData varchar(255), foreign key (userId) references " + users_table_name + " (userId));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS " + users_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + posts_table_name + ";");

        onCreate(db);
    }

    public String getUserDbName()
    {
        return users_table_name;
    }

    public String getPostsDbName()
    {
        return posts_table_name;
    }

    public void initAllTables()
    {
        initUsers();
        initPosts();
    }
    //This function will only be used once to add dummy data to my users table
    private void initUsers()
    {
        //We only want to add the data if nothing is currently in the database
        //this will ensure that we do not add the data more than once.
        if(countRecordsFromTable(users_table_name) == 0)
        {
            //get a writeable version of the database
            //we need a writeable version because we are going to write to the database
            SQLiteDatabase db = this.getWritableDatabase();

            //insert dummy data into the users table if there is nothing in the table
            //we do not want to do this more than once so it needs to be surrounded with an if statement.
            db.execSQL("INSERT INTO " + users_table_name + "(fname, lname, email) VALUES ('Andrew', 'OBrien', 'aobrien2@monroeccc.edu');");
            db.execSQL("INSERT INTO " + users_table_name + "(fname, lname, email) VALUES ('Thor', 'Odinson', 'gothunder@gmail.edu');");
            db.execSQL("INSERT INTO " + users_table_name + "(fname, lname, email) VALUES ('Steve', 'Brown', 'steevB@gmail.com');");
            db.execSQL("INSERT INTO " + users_table_name + "(fname, lname, email) VALUES ('Jimmy', 'Smith', 'the_jimbo26@yahoo.com');");
            db.execSQL("INSERT INTO " + users_table_name + "(fname, lname, email) VALUES ('Catherine', 'LaRoy', 'catgirl35@company.gov');");
            db.execSQL("INSERT INTO " + users_table_name + "(fname, lname, email) VALUES ('Megan', 'Kay', 'mkay_ok@hotmail.com');");

            //close the database
            db.close();
        }
    }
    //This function will only be used once to add dummy data to my posts table.
    private void initPosts()
    {
        //We only want to add the data if nothing is currently in the database
        //this will ensure that we do not add the data more than once.
        if(countRecordsFromTable(posts_table_name) == 0)
        {
            //get a writeable version of the database
            //we need a writeable version because we are going to write to the database
            SQLiteDatabase db = this.getWritableDatabase();

            //insert dummy data into the users table if there is nothing in the table
            //we do not want to do this more than once so it needs to be surrounded with an if statement.
            //The user ids for the dummy data will be added manually using the following key based on the dummy data entered for users:
            //|1 - Zackary|2 - Shannon|3 - Gabriel|4 - Harrison|5 - Tito|6 - Willow|
            //When a user posts the userId will populate automatically based off who which user made the post

            db.execSQL("INSERT INTO " + posts_table_name + "(userId, category, postData) VALUES (1, 'Technology', 'This is my first post about technology.  I am posting about my new computer.');");
            db.execSQL("INSERT INTO " + posts_table_name + "(userId, category, postData) VALUES (2, 'Gardening', 'I love gardening.  This is my first post about gardening.');");
            db.execSQL("INSERT INTO " + posts_table_name + "(userId, category, postData) VALUES (2, 'Gardening', 'Corn.  This year I got a bunch of differnet types of corn to grow in my garden.');");
            db.execSQL("INSERT INTO " + posts_table_name + "(userId, category, postData) VALUES (3, 'Baeball', 'I love baseball.  I am playing cathcer, pitch, and outfield.  I got a few homeruns this year');");
            db.execSQL("INSERT INTO " + posts_table_name + "(userId, category, postData) VALUES (3, 'Gaming', 'Videogames are so fun.  My favorite videogame right now is Good Job.');");
            db.execSQL("INSERT INTO " + posts_table_name + "(userId, category, postData) VALUES (4, 'Cartoons', 'Bluey is the best cartoon in the World!  I could watch it all day if my parents let me.');");
            db.execSQL("INSERT INTO " + posts_table_name + "(userId, category, postData) VALUES (5, 'Squirrels', 'I hate squirrels!');");
            db.execSQL("INSERT INTO " + posts_table_name + "(userId, category, postData) VALUES (6, 'Squirrels', 'I love squirrels!');");
            db.execSQL("INSERT INTO " + posts_table_name + "(userId, category, postData) VALUES (1, 'Teaching', 'I enjoy teaching my students about Software Engineering');");


            db.close();
        }
    }
    //I use this function to determine how many records are in a given table passed to the function
    public int countRecordsFromTable(String tableName)
    {
        //get an instance of a readable database
        //we only need readable because we are not adding anything to the database with this action
        SQLiteDatabase db = this.getReadableDatabase();

        //count the number of entries in the table that was passed to the function
        //this is a built-in function1
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);

        //whenever we open the database we need to close it.
        db.close();

        return numRows;
    }

    public String getFNameForUser(int userId)
    {


        String userFName = "";
        //if the userId was found in the table then this userId has a name associated with it
        //get the name
        if(userIdExists(userId))
        {
            //Sql statement to get the fname from a given userId
            String selectStatement = "SELECT fname FROM " + users_table_name + " WHERE userId = '" + userId + "';";

            //get a readable database
            SQLiteDatabase db = this.getReadableDatabase();
            //run the query
            Cursor cursor = db.rawQuery(selectStatement, null);

            if(cursor != null)
            {
                //cursor could return more than one value depending on the query that we run
                //in this case it should only return one value
                //move the cursor to the first returned value
                cursor.moveToFirst();
                //the parameter passed to cursor will be the column where the data is located
                //this query will only return one value so we set this to 0
                userFName = cursor.getString(0).toString();
            }

            db.close();
        }
        //If the userId was not found in the table then there is no name associated with it
        //return an error message.
        else
        {
            userFName = "ERROR: user id not found";
            Log.d("ERROR: " , "no first name found for user with id: " + userId);
        }


        return userFName;
    }

    public boolean userIdExists(int userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        //I want to count the number of records that contain the userId that was passed to us
        //The only two viable values for the count query to return are
        //1 - the userId was found
        //0 - the userId was not found
        //We cannot get anything else returned becuase the userId is the primary key
        //This ensures that no two people can have the same userId
        String checkUserId = "SELECT count(userId) FROM " + users_table_name + " WHERE userId = '" + userId + "';";

        //run the query
        Cursor cursor = db.rawQuery(checkUserId, null);

        //cursor could return more than one value depending on the query that we run
        //in this case it should only return one value
        //move the cursor to the first returned value
        cursor.moveToFirst();
        //we are only returning one thing so the index will be 0
        int count = cursor.getInt(0);

        db.close();

        //If the count is not zero the userId was found in the database.
        if(count != 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void getAllUserDataGivenId(int userId)
    {
        User loggedInUser = null;
        if (userIdExists(userId))
        {
            //I will retrieve all of this information from the user table given the ID passed to me.
            loggedInUser = new User();

            //query to get the information
            String selectQuery = "SELECT * FROM " + users_table_name + " WHERE userId = '" + userId + "';";

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery(selectQuery, null);

            if (cursor != null)
            {
                cursor.moveToFirst();

                loggedInUser.setId(cursor.getInt(0));
                loggedInUser.setFname(cursor.getString(1));
                loggedInUser.setLname(cursor.getString(2));
                loggedInUser.setEmail(cursor.getString(3));

                SessionData.setLoggedInUser(loggedInUser);
            }
        }
        else
        {
            SessionData.setLoggedInUser(null);
            Log.d("Error", "Error");
        }
    }

    public int getNumberOfPosts(int userId)
    {
        // Get database
        SQLiteDatabase db = this.getReadableDatabase();

        // Create query to count posts for a given user ID
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM Posts WHERE userId = " + userId, null);

        // Move to the first result
        cursor.moveToFirst();

        // Get the count
        int count = cursor.getInt(0);

        db.close();

        return count;
    }

    public String getLNameForUser(int userId)
    {


        String userLName = "";
        //if the userId was found in the table then this userId has a name associated with it
        //get the name
        if(userIdExists(userId))
        {
            //Sql statement to get the lname from a given userId
            String selectStatement = "SELECT lname FROM " + users_table_name + " WHERE userId = '" + userId + "';";

            //get a readable database
            SQLiteDatabase db = this.getReadableDatabase();
            //run the query
            Cursor cursor = db.rawQuery(selectStatement, null);

            if(cursor != null)
            {
                //cursor could return more than one value depending on the query that we run
                //in this case it should only return one value
                //move the cursor to the first returned value
                cursor.moveToFirst();
                //the parameter passed to cursor will be the column where the data is located
                //this query will only return one value so we set this to 0
                userLName = cursor.getString(0).toString();
            }

            db.close();
        }
        //If the userId was not found in the table then there is no name associated with it
        //return an error message.
        else
        {
            userLName = "ERROR: user id not found";
            Log.d("ERROR: " , "no first name found for user with id: " + userId);
        }

        return userLName;
    }
    public void addUserToDB(User u)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //we need our sql insert statement to look like this
        // INSERT INTO users (fname, lname, email) VALUES ('Bobby','Smith','Bsmith@bsmith.org');

        db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('" + u.getFname() + "','" + u.getLname() + "','" + u.getEmail() + "');");
        db.close();
    }

    private void getMostRecentPostGivenId(int id)
    {
        //BOOOOOOOOOOOOOOOOOOOOOOOKMARK HEEEEEEEEEEEEEEEEREEEEEEEEEEEEEEEEEEEE!!!!!!!!!!!!!!!!!!!!
        Post rPost = null;
    }
}
