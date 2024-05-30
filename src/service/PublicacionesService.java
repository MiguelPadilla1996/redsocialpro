/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author DE
 */

import molder.Publicacion;
import molder.Usuario;

public class PublicacionesService {
    public void publicarTuit(Usuario usuario, String contenido) {
        Publicacion nuevaPublicacion = new Publicacion(contenido);
        usuario.getPublicaciones().push(nuevaPublicacion);
    }
}
