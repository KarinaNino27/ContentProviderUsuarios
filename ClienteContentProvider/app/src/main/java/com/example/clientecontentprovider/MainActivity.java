package com.example.clientecontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public  TextView nombre;
    public TextView apellido;
    public TextView id;
    private void consultarContentProvider() {
        Cursor cursor = getContentResolver().query(
                UsuarioContrato.CONTENT_URI,
                UsuarioContrato.COLUMNS_NAME,
                null, null, null
        );
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Log.d("Cliente",
                        cursor.getInt(0) + " - " + cursor.getString(1)
                );
            }
        } else {
            Log.d("UsuarioContentProvider",
                    "No regresa"
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        id = findViewById(R.id.id);
        Cursor c = getContentResolver().query(UserDictionary.Words.CONTENT_URI,
                new String[]{UserDictionary.Words.WORD,
                        UserDictionary.Words.LOCALE},
                null, null, null
        );
        if (c != null) {
            while (c.moveToNext()) {
                Log.d("Uri",
                        c.getString(0) + " - " + c.getString(1)
                ); }}
        findViewById(R.id.btnInsert).setOnClickListener(
                view -> {
                    ContentValues cv = new ContentValues();
                    cv.put(UsuarioContrato.COLUMN_FIRSTNAME, nombre.getText().toString());
                    cv.put(UsuarioContrato.COLUMN_LASTNAME, apellido.getText().toString());
                    Uri uriInsert = getContentResolver().insert(UsuarioContrato.CONTENT_URI, cv);
                    Log.d("Cliente", uriInsert.toString());
                    Toast.makeText(this, "Usuario agregado: \n" +
                            uriInsert.toString(), Toast.LENGTH_SHORT).show();
                }
        );


        findViewById(R.id.btnUpdate).setOnClickListener(
                view -> {

                    ContentValues cv = new ContentValues();
                    cv.put(UsuarioContrato.COLUMN_FIRSTNAME, nombre.getText().toString());
                    cv.put(UsuarioContrato.COLUMN_LASTNAME, apellido.getText().toString());

                    int elemtosAfectados = getContentResolver().update(
                            Uri.withAppendedPath(UsuarioContrato.CONTENT_URI, id.getText().toString()),
                            cv,
                            null, null
                    );

                    Log.d("Cliente", "Elementos afectados: " + elemtosAfectados);
                    Toast.makeText(this, "Usuario actualizado: \n" +
                            elemtosAfectados, Toast.LENGTH_SHORT).show();


                }
        );

        findViewById(R.id.btnConsultar).setOnClickListener(v -> {
            consultarContentProvider();
        });
        //DELETE, borra buscando un id especifico
        findViewById(R.id.btnDelete).setOnClickListener(v -> {

            int elementosAfectados = getContentResolver().delete(
                    Uri.withAppendedPath(UsuarioContrato.CONTENT_URI, id.getText().toString()),
                    null, null
            );

            Log.d("ClienteEliminado", "Elementos afectados: " + elementosAfectados);
        });

        findViewById(R.id.btnQueryNL).setOnClickListener(view -> {

            String selectionClause = UsuarioContrato.COLUMN_FIRSTNAME + " = ? AND " + UsuarioContrato.COLUMN_LASTNAME + " = ?";
            String[] selectionArgs = {nombre.getText().toString(), apellido.getText().toString()};

            Cursor cursor = getContentResolver().query(
                    UsuarioContrato.CONTENT_URI,
                    UsuarioContrato.COLUMNS_NAME,
                    selectionClause, selectionArgs, null
            );
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Log.d("ClienteEspeficio",
                            cursor.getInt(0) + " - " + cursor.getString(1)
                    );
                }
            } else {
                Log.d("UsuarioContentProvider",
                        "NO Regresa"
                );
            }
        });
    }
}