package com.example.roomfloydentrega;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;

@Database(entities = {Evento.class}, version = 1, exportSchema = false)
public abstract class BaseDeDatos extends RoomDatabase {

    private static volatile BaseDeDatos db;

    public abstract EventosDao obtenerEventosDao();

    public static BaseDeDatos getInstance(final Context context){
        if (db == null){
            synchronized (BaseDeDatos.class){
                if (db == null) {
                    db = Room.databaseBuilder(context, BaseDeDatos.class, "app.dbb")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return db;
    }

    @Dao
    interface EventosDao {
        @Insert
        void insertar(Evento evento);

        @Query("SELECT * FROM EVENTO")
        LiveData<List<Evento>> obtener();
    }
}
