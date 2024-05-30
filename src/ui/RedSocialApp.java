/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ui;

/**
 *
 * @author DE
 */
import javax.swing.*;
import java.awt.*;

public class RedSocialApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Red Social App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Barra de navegación
        JPanel navigationPanel = new JPanel();
        navigationPanel.setBackground(Color.LIGHT_GRAY);
        navigationPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));

        JButton homeButton = new JButton("Inicio");
        JButton searchButton = new JButton("Buscar");
        JButton notificationsButton = new JButton("Notificaciones");
        JButton messagesButton = new JButton("Mensajes");

        navigationPanel.add(homeButton);
        navigationPanel.add(searchButton);
        navigationPanel.add(notificationsButton);
        navigationPanel.add(messagesButton);

        // Área de contenido para los tuits
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        // Ejemplo de un tuit
        JPanel tweetPanel = new JPanel();
        tweetPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tweetPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

        JLabel usernameLabel = new JLabel("Usuario123:");
        JTextArea tweetTextArea = new JTextArea("Este es un tuit de ejemplo. ¡Hola, mundo!");
        tweetTextArea.setWrapStyleWord(true);
        tweetTextArea.setLineWrap(true);
        tweetTextArea.setEditable(false);

        tweetPanel.add(usernameLabel);
        tweetPanel.add(tweetTextArea);

        contentPanel.add(tweetPanel);

        // Agrega más tuits aquí...

        // Coloca los paneles en el frame
        frame.add(navigationPanel, BorderLayout.NORTH);
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
