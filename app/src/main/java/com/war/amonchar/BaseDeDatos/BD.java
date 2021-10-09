package com.war.amonchar.BaseDeDatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.war.amonchar.Modelo.Ingrediente;
import com.war.amonchar.Modelo.Usuario;

import java.util.ArrayList;

public class BD extends SQLiteOpenHelper {

    // CONSTANTES BD
    private static final String NOMBRE_BD = "aMonchar.bd";
    private static final int VERSION_BD = 1;


    // NOMBRES DE TABLAS
    private static final String tbUsuarios = "USUARIOS";
    private static final String tbListacompra = "LISTACOMPRA";

//-------------------------------------- SENTENCIAS SQL --------------------------------------------

    // USUARIOS
    private static final String TABLA_USUARIOS = "CREATE TABLE "+tbUsuarios+" (NOMBRE_USUARIO TEXT PRIMARY KEY NOT NULL,"
            +" CORREO TEXT NOT NULL, CONTRASENIA TEXT NOT NULL, NOMBRE TEXT, APELLIDOS TEXT, BIOGRAFIA TEXT, FOTOGRAFIA TEXT, LOGUEADO INT)";

    // LISTA DE COMPRA
    private static final String TABLA_LISTACOMPRA = "CREATE TABLE "+tbListacompra+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" NOMBRE TEXT NOT NULL, COMPRADO INT NOT NULL)";

//-------------------------------------- CONSTRUCTOR -----------------------------------------------

    public BD(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

//-------------------------------------- OVERRIDE METHODS ------------------------------------------

    // CREAR LA BASE DE DATOS
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_USUARIOS);
        db.execSQL(TABLA_LISTACOMPRA);
    }
    // ACTUALIZAR LA BASE DE DATOS
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tbUsuarios);
        db.execSQL("DROP TABLE IF EXISTS " + tbListacompra);
    }

//--------------------------------------------------------------------------------------------------
//-------------------------------------- USUARIOS METHODS ------------------------------------------
//--------------------------------------------------------------------------------------------------

    // AGREGAR UN USUARIO NUEVO
    public boolean agregarUsuario(Usuario usuario){

        SQLiteDatabase db = getWritableDatabase();

        if (usuario != null){
            String nombreUsuario = usuario.getNombreUsuario();
            String correo = usuario.getCorreo();
            String contrasenia = usuario.getContrasenia();
            String nombre = usuario.getNombre();
            String apellidos = usuario.getApellidos();
            String biografia = usuario.getBiografia();
            String fotografia = String.valueOf(usuario.getFotografia());

            if (db != null){
                db.execSQL("INSERT INTO "+tbUsuarios+" VALUES ('"+nombreUsuario+"', '"+correo+"', '"+contrasenia+"', '"+nombre+"', '"
                        +apellidos+"', '"+biografia+"', '"+fotografia+"', 0)");
                db.close();
                return true;
            }
        }
        return false;
    }// Fin de agregarUsuario

//--------------------------------------------------------------------------------------------------

    // MODIFICAR USUARIO
    public boolean modificarUsuario(Usuario usuario){

        SQLiteDatabase db = getReadableDatabase();

        if (usuario != null){
            String nombreUsuario = usuario.getNombreUsuario();
            String correo = usuario.getCorreo();
            String contrasenia = usuario.getContrasenia();
            String nombre = usuario.getNombre();
            String apellidos = usuario.getApellidos();
            String biografia = usuario.getBiografia();
            String fotografia = String.valueOf(usuario.getFotografia());

            if (db != null){
                Cursor cursor = db.rawQuery("SELECT * FROM "+tbUsuarios+" WHERE NOMBRE_USUARIO = '"+nombreUsuario+"'", null);
                cursor.moveToFirst();

                Usuario usuarioBD = new Usuario();

                if (cursor.getCount() != 0){
                    usuarioBD.setNombreUsuario(cursor.getString(0));
                    usuarioBD.setCorreo(cursor.getString(1));
                    usuarioBD.setContrasenia(cursor.getString(2));
                    usuarioBD.setNombre(cursor.getString(3));
                    usuarioBD.setApellidos(cursor.getString(4));
                    usuarioBD.setBiografia(cursor.getString(5));
                    usuarioBD.setFotografia(Uri.parse(cursor.getString(6)));
                }
                if (nombreUsuario.equals(usuarioBD.getNombreUsuario())){
                    db = getWritableDatabase();
                    db.execSQL("UPDATE "+tbUsuarios+" SET NOMBRE_USUARIO = '"+nombreUsuario+"',  CORREO = '"+correo+"',  " +
                            "CONTRASENIA = '"+contrasenia+"',  NOMBRE = '"+nombre+"',  APELLIDOS = '"+apellidos+"',  BIOGRAFIA = '"+biografia+"'" +
                            ", FOTOGRAFIA = '"+fotografia+"' WHERE NOMBRE_USUARIO = '"+nombreUsuario+"'");
                    db.close();
                    return true;
                }
            }
        }
        return false;
    }// Fin de modificarUsuario

//--------------------------------------------------------------------------------------------------

    // OBTENER ARRAY DE USUARIOS
    public ArrayList<Usuario> getUsuarios(){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM USUARIOS", null);

        cursor.moveToFirst();

        ArrayList<Usuario> list = new ArrayList<>();

        while (!cursor.isAfterLast()){
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(cursor.getString(0));
            usuario.setCorreo(cursor.getString(1));
            usuario.setContrasenia(cursor.getString(2));
            usuario.setNombre(cursor.getString(3));
            usuario.setApellidos(cursor.getString(4));
            usuario.setBiografia(cursor.getString(5));
            usuario.setFotografia(Uri.parse(cursor.getString(6)));
            int log = cursor.getInt(7);
            if (log == 0){
                usuario.setLogueado(false);
            }else{
                usuario.setLogueado(true);
            }
            list.add(usuario);
            cursor.moveToNext();
        }// Fin del while

        cursor.close();
        return list;
    }// Fin de getUsuarios


//--------------------------------------------------------------------------------------------------

    // VALIDAR USUARIO
    public boolean validarUsuario(String correo, String contrasenia, boolean logueado){

        SQLiteDatabase db = getReadableDatabase();

        if (db != null){
            Cursor cursor = db.rawQuery("SELECT * FROM "+tbUsuarios+" WHERE CORREO = '"+correo+"'", null);
            cursor.moveToFirst();

            Usuario usuarioBD = new Usuario();

            if (cursor.getCount() != 0){
                usuarioBD.setNombreUsuario(cursor.getString(0));
                usuarioBD.setCorreo(cursor.getString(1));
                usuarioBD.setContrasenia(cursor.getString(2));
                usuarioBD.setNombre(cursor.getString(3));
                usuarioBD.setApellidos(cursor.getString(4));
                usuarioBD.setBiografia(cursor.getString(5));
                usuarioBD.setFotografia(Uri.parse(cursor.getString(6)));
                int log = cursor.getInt(7);
                if (log == 0){
                    usuarioBD.setLogueado(false);
                }else{
                    usuarioBD.setLogueado(true);
                }
            }

            if (correo.equals(usuarioBD.getCorreo()) && contrasenia.equals(usuarioBD.getContrasenia())){
                int loginInt = 0;
                if (logueado){
                    loginInt = 1;
                }else{
                    loginInt = 0;
                }
                db = getWritableDatabase();
                db.execSQL("UPDATE "+tbUsuarios+" SET LOGUEADO = '"+loginInt+"' WHERE CORREO = '"+correo+"'");
                db.close();
                return true;
            }
        }

        return false;
    }// Fin de validarUsuario


//--------------------------------------------------------------------------------------------------
//-------------------------------------- LISTA COMPRA METHODS --------------------------------------
//--------------------------------------------------------------------------------------------------

    // AGREGAR UN INGREDIENTE NUEVO
    public boolean agregarIngrediente(Ingrediente ingrediente){

        SQLiteDatabase db = getWritableDatabase();

        if (ingrediente != null){
            if (!ingrediente.getNombre().equals("")){
                int id = ingrediente.getId();
                String nombre = ingrediente.getNombre();
                boolean compradoB = ingrediente.isComprado();

                int comprado = 0;

                if (compradoB){
                    comprado = 1;
                }else {
                    comprado = 0;
                }

                if (db != null){
                    db.execSQL("INSERT INTO "+tbListacompra+"(NOMBRE, COMPRADO) VALUES ('"+nombre+"', '"+comprado+"')");
                    db.close();
                    return true;
                }
            }
        }
        return false;
    }// Fin de agregarIngrediente
//--------------------------------------------------------------------------------------------------

    // MODIFICAR INGREDIENTE
    public boolean modificarIngrediente(Ingrediente ingrediente){

        SQLiteDatabase db = getReadableDatabase();

        if (ingrediente != null){
            int id = ingrediente.getId();
            String nombre = ingrediente.getNombre();
            boolean comprado = ingrediente.isComprado();

            if (db != null){
                Cursor cursor = db.rawQuery("SELECT * FROM "+tbListacompra+" WHERE ID = '"+id+"'", null);
                cursor.moveToFirst();

                Ingrediente ingredienteBD = new Ingrediente();

                int compradoInt = 0;

                if (cursor.getCount() != 0){
                    ingredienteBD.setId(cursor.getInt(0));
                    ingredienteBD.setNombre(cursor.getString(1));
                    compradoInt = cursor.getInt(2);

                    if (compradoInt == 0){
                        ingredienteBD.setComprado(false);
                    }else {
                        ingredienteBD.setComprado(true);
                    }
                }
                if (id == ingredienteBD.getId()){
                    db = getWritableDatabase();
                    db.execSQL("UPDATE "+tbListacompra+" SET NOMBRE = '"+nombre+"',  COMPRADO = '"+compradoInt+"' WHERE ID = '"+id+"'");
                    db.close();
                    return true;
                }
            }
        }
        return false;
    }// Fin de modificarIngrediente

//--------------------------------------------------------------------------------------------------

    // OBTENER ARRAY DE INGREDIENTES
    public ArrayList<Ingrediente> getIngredientes(){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+tbListacompra, null);

        cursor.moveToFirst();

        ArrayList<Ingrediente> list = new ArrayList<>();

        int compradoInt = 0;

        while (!cursor.isAfterLast()){
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setId(cursor.getInt(0));
            ingrediente.setNombre(cursor.getString(1));

            compradoInt = cursor.getInt(2);

            if (compradoInt == 0){
                ingrediente.setComprado(false);
            }else {
                ingrediente.setComprado(true);
            }
            list.add(ingrediente);
            cursor.moveToNext();
        }// Fin del while

        cursor.close();
        return list;
    }// Fin de getUsuarios

//--------------------------------------------------------------------------------------------------

    // ELIMINAR INGREDIENTE
    public boolean eliminarIngrediente(int idIngrediente){
        SQLiteDatabase db = getReadableDatabase();

        if (db != null) {
            Cursor cursor = db.rawQuery("SELECT * FROM " + tbListacompra + " WHERE ID = '" + idIngrediente + "'", null);
            cursor.moveToFirst();

            if (cursor.getCount() != 0){
                if (cursor.getInt(0) == idIngrediente){
                    db = getWritableDatabase();
                    db.execSQL("DELETE FROM "+tbListacompra+" WHERE ID = '"+idIngrediente+"'");
                    db.close();
                    return true;
                }
            }
        }
        return false;
    }// Fin de eliminarIngrediente



}// Fin de la clase