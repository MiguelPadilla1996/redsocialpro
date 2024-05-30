/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package molder;

/**
 *
 * @author DE
 */
public class Comentario {
    private Usuario usuario;
    private String contenido;

    public Comentario(Usuario usuario, String contenido) {
        this.usuario = usuario;
        this.contenido = contenido;
    }

    // Getters y Setters

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    

}

