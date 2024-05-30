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

import java.util.Map;

import molder.Usuario;

public class Autenticacion {
    private Map<String, Usuario> usuarios;

    public Autenticacion(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario iniciarSesion(String usuario, String contrasena) {
        if (usuarios.containsKey(usuario)) {
            Usuario u = usuarios.get(usuario);
            if (u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null;
    }
}
