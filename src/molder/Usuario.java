/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package molder;

/**
 *
 * @author DE
 */
import java.util.*;

public class Usuario {
    private String nombre;
    private String usuario;
    private String contrasena;
    private Stack<Publicacion> publicaciones;
    private LinkedList<Usuario> seguidos;
    private LinkedList<Usuario> seguidores;

    public Usuario(String nombre, String usuario, String contrasena) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.publicaciones = new Stack<>();
        this.seguidos = new LinkedList<>();
        this.seguidores = new LinkedList<>();
    }

    // Getters y Setters
    // Métodos para añadir/quitar publicaciones, seguidos, seguidores, etc.

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Stack<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public void setPublicaciones(Stack<Publicacion> publicaciones) {
        this.publicaciones = publicaciones;
    }

    public LinkedList<Usuario> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(LinkedList<Usuario> seguidos) {
        this.seguidos = seguidos;
    }

    public LinkedList<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(LinkedList<Usuario> seguidores) {
        this.seguidores = seguidores;
    }
    
}
