/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import dao.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton forgotPasswordButton;
    private JLabel logoLabel;

    public LoginScreen() {
        setTitle("Inicia sesión en X");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        
        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Logo
        logoLabel = new JLabel("X", SwingConstants.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 36));
        logoLabel.setForeground(Color.WHITE);

        // Campos de entrada
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usernameField.setBackground(Color.DARK_GRAY);
        usernameField.setForeground(Color.WHITE);
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setBackground(Color.DARK_GRAY);
        passwordField.setForeground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Botón de iniciar sesión
        loginButton = new JButton("Iniciar Sesión");
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Botón de registrarse
        registerButton = new JButton("Registrarse");
        registerButton.setBackground(Color.BLACK);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        registerButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        // Botón de olvido de contraseña
        forgotPasswordButton = new JButton("¿Olvidaste tu contraseña?");
        forgotPasswordButton.setBackground(Color.BLACK);
        forgotPasswordButton.setForeground(Color.WHITE);
        forgotPasswordButton.setFocusPainted(false);
        forgotPasswordButton.setBorderPainted(false);
        forgotPasswordButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Añadir componentes al panel principal
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(registerButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(forgotPasswordButton);
        
        // Añadir el panel principal al frame
        add(mainPanel);

        // Acción para el botón de iniciar sesión
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            int userId = UsuarioDAO.verificarCredenciales(username, password);
            if (userId != -1) {
                dispose();
                new FeedScreen(userId).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Nombre de usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción para el botón de registrarse
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterScreen();
            }
        });

        setVisible(true);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginScreen::new);
    }
}

