package molder;

import java.util.*;
import molder.Comentario;
import molder.Usuario;

public class Publicacion {
    private String contenido;
    private LinkedList<Comentario> comentarios;
    private LinkedList<Usuario> likes;

    public Publicacion(String contenido) {
        this.contenido = contenido;
        this.comentarios = new LinkedList<>();
        this.likes = new LinkedList<>();
    }

    // Getters y Setters
    // Métodos para añadir/quitar comentarios, likes, etc.

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LinkedList<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(LinkedList<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public LinkedList<Usuario> getLikes() {
        return likes;
    }

    public void setLikes(LinkedList<Usuario> likes) {
        this.likes = likes;
    }

    
}
