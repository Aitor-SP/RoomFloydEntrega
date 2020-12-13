package com.example.roomfloydentrega;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Evento {
    @PrimaryKey(autoGenerate = true)
            int id;

    String evento;
    String fecha;
    String imagen;

    public Evento(String evento, String fecha, String imagen) {
        this.evento = evento;
        this.fecha = fecha;
        this.imagen = imagen;
    }
}
