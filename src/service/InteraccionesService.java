/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author DE
 */

import molder.Comentario;
import molder.Publicacion;
import molder.Usuario;

public class InteraccionesService {
    public void comentarPublicacion(Publicacion publicacion, Usuario usuario, String contenido) {
        Comentario comentario = new Comentario(usuario, contenido);
        publicacion.getComentarios().add(comentario);
    }

    public void darLike(Publicacion publicacion, Usuario usuario) {
        if (!publicacion.getLikes().contains(usuario)) {
            publicacion.getLikes().add(usuario);
        }
    }

    public void quitarLike(Publicacion publicacion, Usuario usuario) {
        publicacion.getLikes().remove(usuario);
    }
}

