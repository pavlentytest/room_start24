package ru.samsung.itschool.mdev.roomvsfragment.db;

import android.content.Context;

import androidx.room.Room;

public class DBClient {
    // Singletone
    private Context context;
    private static DBClient instance;
    private AppDB appDatabase;

    private DBClient(Context _context) {
        this.context = _context;
        this.appDatabase = Room.databaseBuilder(_context, AppDB.class,"MyTasks24").build();
    }

    public static DBClient getInstance(Context _context) {
        if(instance == null) {
            instance = new DBClient(_context);
        }
        return instance;
    }

    public AppDB getAppDatabase() {
        return appDatabase;
    }
}