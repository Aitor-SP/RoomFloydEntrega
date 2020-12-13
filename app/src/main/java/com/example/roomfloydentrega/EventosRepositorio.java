package com.example.roomfloydentrega;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventosRepositorio {
    Executor executor = Executors.newSingleThreadExecutor();
    private final BaseDeDatos.EventosDao eventosDao;

    public EventosRepositorio(Application application) {
        eventosDao = BaseDeDatos.getInstance(application).obtenerEventosDao();
    }

    public void insertar(String evento, String fecha, Uri imagenSeleccionada) {
        executor.execute(() -> {
            eventosDao.insertar(new Evento(evento, fecha, imagenSeleccionada.toString()));
            });
    }

    public LiveData<List<Evento>> obtener() {
        return eventosDao.obtener();
    }
}
