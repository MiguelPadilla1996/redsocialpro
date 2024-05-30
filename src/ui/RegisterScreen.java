/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

/**
 *
 * @author DE
 */
import util.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterScreen extends JFrame {
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> monthComboBox;
    private JComboBox<String> dayComboBox;
    private JComboBox<String> yearComboBox;
    private JButton registerButton;
    private JButton backButton;

    public RegisterScreen() {
        setTitle("Crea tu cuenta");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Crea tu cuenta", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10)); // Cambiado de 6 a 5 filas
        panel.setBackground(Color.BLACK);

        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);
        nameField = new JTextField();
        panel.add(nameField);

        JLabel usernameLabel = new JLabel("Usuario:");
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);
        usernameField = new JTextField();
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JLabel dobLabel = new JLabel("Fecha de nacimiento:");
        dobLabel.setForeground(Color.WHITE);
        panel.add(dobLabel);
        JPanel dobPanel = new JPanel(new GridLayout(1, 3));
        dobPanel.setBackground(Color.BLACK);
        monthComboBox = new JComboBox<>(new String[]{"Mes", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"});
        dayComboBox = new JComboBox<>(generateNumbers(1, 31, "Día"));
        yearComboBox = new JComboBox<>(generateNumbers(1900, 2023, "Año"));
        dobPanel.add(monthComboBox);
        dobPanel.add(dayComboBox);
        dobPanel.add(yearComboBox);
        panel.add(dobPanel);

        add(panel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        registerButton = new JButton("Siguiente");
        registerButton.setBackground(Color.GRAY);
        registerButton.setForeground(Color.WHITE);
        backButton = new JButton("Atrás");
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        buttonPanel.add(backButton);
        buttonPanel.add(registerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String month = (String) monthComboBox.getSelectedItem();
                String day = (String) dayComboBox.getSelectedItem();
                String year = (String) yearComboBox.getSelectedItem();

                if (name.isEmpty() || username.isEmpty() || password.isEmpty() || "Mes".equals(month) || "Día".equals(day) || "Año".equals(year)) {
                    JOptionPane.showMessageDialog(RegisterScreen.this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (registerUser(name, username, password)) {
                        JOptionPane.showMessageDialog(RegisterScreen.this, "Usuario registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(RegisterScreen.this, "Error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private boolean registerUser(String name, String username, String password) {
        String query = "INSERT INTO usuarios (nombre, usuario, contrasena) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String[] generateNumbers(int start, int end, String defaultItem) {
        String[] numbers = new String[end - start + 2];
        numbers[0] = defaultItem;
        for (int i = start; i <= end; i++) {
            numbers[i - start + 1] = String.valueOf(i);
        }
        return numbers;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterScreen::new);
    }
}
