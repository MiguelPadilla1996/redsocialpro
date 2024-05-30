/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author DE
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;
import molder.Usuario;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;

public class UsuarioDAO {
    private final Connection connection;

    public UsuarioDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }

    public void insertarUsuario(Usuario usuario) {
        String query = "INSERT INTO usuarios (nombre, usuario, contraseña) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getUsuario());
            statement.setString(3, usuario.getContrasena());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para verificar las credenciales del usuario en la base de datos
    public static int verificarCredenciales(String username, String password) {
        String query = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indica credenciales incorrectas
    }
    
    public void actualizarUsuario(Usuario usuario) {
        String query = "UPDATE usuarios SET nombre = ?, usuario = ?, contraseña = ? WHERE usuario = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getUsuario());
            statement.setString(3, usuario.getContrasena());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    // Agrega aquí otros métodos para actualizar, eliminar y recuperar usuarios}
