/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author DE
 */

import molder.Usuario;

public class SeguimientoService {
    public void seguirUsuario(Usuario seguidor, Usuario seguido) {
        if (!seguidor.getSeguidos().contains(seguido)) {
            seguidor.getSeguidos().add(seguido);
            seguido.getSeguidores().add(seguidor);
        }
    }

    public void dejarDeSeguirUsuario(Usuario seguidor, Usuario seguido) {
        seguidor.getSeguidos().remove(seguido);
        seguido.getSeguidores().remove(seguidor);
    }
}
