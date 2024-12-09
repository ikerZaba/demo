package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.ArrayList;
import java.util.List; 

public class GestorBDD {
    Connection c; 

    private Connection conectar(){
        try {
            return DriverManager.getConnection("jdbc:sqlite:bddPeliculas.db");
        } catch (SQLException ex) {
            System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
            System.exit(0);
        }
        
        return null;
    }

    public void crearTablas(){
        try{
            c = conectar();
            try (Statement stmt = c.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS Peliculas " +
                        "(ID_IMDB TEXT PRIMARY KEY     NOT NULL," +
                        " TITULO           TEXT    NOT NULL, " +
                        " AÑO            INT     NOT NULL, " +
                        " TRAMA        TEXT NOT NULL)";
                stmt.executeUpdate(sql);
            }
            c.close();

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
    }

    public void insertarListaPeliculas(ArrayList<Pelicula> listaPeliculas){
        String sql = "INSERT INTO Peliculas(ID_IMDB, TITULO, AÑO, TRAMA) VALUES(?,?,?,?)";
        try{
            c = conectar();
            for(Pelicula pelicula : listaPeliculas){
                PreparedStatement pstmt = c.prepareStatement(sql); 
                pstmt.setString(1, pelicula.getId());  
                pstmt.setString(2, pelicula.getTitulo());
                pstmt.setInt(3, pelicula.getAño());
                pstmt.setString(4, pelicula.getTrama());  
                pstmt.executeUpdate();
            }
            c.close();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        
    }

    public void getInfo(){
        try {

        c = conectar();

            try (Statement stmt = c.createStatement(); ResultSet rs = stmt.executeQuery( "SELECT * FROM Peliculas;" )) {
                
                while ( rs.next() ) {
                    String id = rs.getString("ID_IMDB");
                    String  titulo = rs.getString("TITULO");
                    int año  = rs.getInt("AÑO");
                    String  trama = rs.getString("TRAMA");
                    
                    System.out.println( "ID = " + id );
                    System.out.println( "titulo = " + titulo );
                    System.out.println( "año = " + año );
                    System.out.println( "trama = " + trama );
                    System.out.println();
                }
            }
        c.close();
        } catch ( SQLException ex ) {
            System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
            System.exit(0);
        }
    }

    public List<Pelicula> getPeliculas() {
        List<Pelicula> listaPeliculas = new ArrayList<>();
        Pelicula p;
        try {

            c = conectar();
    
                try (Statement stmt = c.createStatement(); ResultSet rs = stmt.executeQuery( "SELECT * FROM Peliculas;" )) {
                    
                    while ( rs.next() ) {
                        String id = rs.getString("ID_IMDB");
                        String  titulo = rs.getString("TITULO");
                        int año  = rs.getInt("AÑO");
                        String  trama = rs.getString("TRAMA");
                        
                        p = new Pelicula(id, titulo, año, trama);
                        listaPeliculas.add(p);
                    }
                }
            c.close();
            return listaPeliculas;
            } catch ( SQLException ex ) {
                System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
                System.exit(0);
            }
        return null;
    }

}
