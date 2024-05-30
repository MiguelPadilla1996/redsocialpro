/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author DE
 */


import java.util.HashSet;
import molder.Usuario;

public class Registro {
    private HashSet<String> usuariosRegistrados;

    public Registro() {
        this.usuariosRegistrados = new HashSet<>();
    }

    public boolean registrarUsuario(String nombre, String usuario, String contraseña) {
        if (usuariosRegistrados.contains(usuario)) {
            return false;
        }
        usuariosRegistrados.add(usuario);
        Usuario nuevoUsuario = new Usuario(nombre, usuario, contraseña);
        // Guardar nuevoUsuario en una base de datos o colección
        System.out.println("service.Registro.registrarUsuario()" + nuevoUsuario);
        return true;
    }
}

