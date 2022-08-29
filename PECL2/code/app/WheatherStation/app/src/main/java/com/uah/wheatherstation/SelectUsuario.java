package com.uah.wheatherstation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.uah.wheatherstation.data.City;
import com.uah.wheatherstation.tasks.TaskSelectStation;
import com.uah.wheatherstation.tasks.TaskSelectUsuario;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectUsuario extends AppCompatActivity
{
    private String tag = "SelectUsuario";
    private final Context context;
    private EditText usuario;
    private EditText password;
    private Button buttonIniciarSesion;

    public SelectUsuario()
    {
        super();
        this.context = this;
    }

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        this.usuario = this.findViewById(R.id.usuario);
        this.password = this.findViewById(R.id.password);
        this.buttonIniciarSesion = this.findViewById(R.id.buttonIniciarSesion);
        Log.e(tag, "BSelectUsuario");
        buttonIniciarSesion.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Log.e(tag, "Botton");
                obtenerUsuario(usuario.getText().toString(), password.getText().toString());
            }
        });
    }

    private void obtenerUsuario(String nombre, String contrasena)
    {
        new TaskSelectUsuario(this).execute("http://uah-plantas.tech/GetUsuario?nombre="+nombre+"&contrasena="+contrasena);
    }

    public void setUsuario(JSONObject jsonUsuario)
    {
        Log.e(tag,"Loading usuario " + jsonUsuario);
        try {
            int id = jsonUsuario.getInt("id");
            if (id != 0)
            {
                Intent i = new Intent(SelectUsuario.this, SelectMaceta.class);
                i.putExtra("usuario_id", id);
                startActivity(i);
                finish();
            }
            else
            {
                new AlertDialog.Builder(SelectUsuario.this)
                        .setTitle("Error")
                        .setMessage("El usuario no existe")
                        .setCancelable(false)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }
        }catch (Exception e)
        {
            Log.e(tag,"Error: " + e);
        }
    }
}
