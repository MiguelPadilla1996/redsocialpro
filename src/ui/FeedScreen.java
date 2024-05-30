/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;

import util.DatabaseConnection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Stack;

public class FeedScreen extends JFrame {

    private JTextArea postArea;
    private JButton tweetButton;
    private JButton deleteButton;
    private JButton logoutButton; // Botón de cierre de sesión
    private JPanel feedPanel;
    private JScrollPane scrollPane;
    private JPanel sidebarPanel;
    private Stack<JPanel> postsStack;
    private int userId;
    

    public FeedScreen(int userId) {
        this.userId = userId;

        setTitle("Feed de Publicaciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800 , 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        postsStack = new Stack<>();

        // Layout principal
        setLayout(new BorderLayout());

        // Barra lateral
        sidebarPanel = createSidebarPanel();
        add(sidebarPanel, BorderLayout.WEST);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        add(mainPanel, BorderLayout.CENTER);

        // Área de publicación
        JPanel postPanel = createPostPanel();
        mainPanel.add(postPanel, BorderLayout.NORTH);

        // Panel de publicaciones con scroll
        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setBackground(Color.BLACK);

        scrollPane = new JScrollPane(feedPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        loadPostsFromDatabase();
        setVisible(true);
    }

    private JPanel createSidebarPanel() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(Color.BLACK);
        sidebar.setPreferredSize(new Dimension(100, getHeight()));

        String[] icons = {"🏠", "🔍", "🔔", "✉️", "📄", "⚙️"};
        for (String icon : icons) {
            JButton button = new JButton(icon);
            button.setForeground(Color.WHITE);
            button.setBackground(Color.BLACK);
            button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            button.setFocusPainted(false);
            sidebar.add(button);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Botón de cierre de sesión
        logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(Color.BLACK);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        sidebar.add(Box.createVerticalGlue()); // Empuja el botón hacia abajo
        sidebar.add(logoutButton);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        return sidebar;
    }

    private JPanel createPostPanel() {
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBackground(Color.BLACK);
        postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        postArea = new JTextArea("¿Qué está pasando?!");
        postArea.setLineWrap(true);
        postArea.setWrapStyleWord(true);
        postArea.setBackground(Color.DARK_GRAY);
        postArea.setForeground(Color.WHITE);
        postArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        postArea.setPreferredSize(new Dimension(getWidth(), 100));
        postArea.setDocument(new JTextFieldLimit(300));

        tweetButton = new JButton("Postear");
        tweetButton.setBackground(Color.BLUE);
        tweetButton.setForeground(Color.WHITE);
        tweetButton.setFocusPainted(false);
        tweetButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tweetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = postArea.getText();
                if (!text.trim().isEmpty()) {
                    if (addPostToDatabase(userId, text)) {
                        addPost("Usuario", text); // Puedes cambiar "Usuario" por el nombre del usuario si tienes esa información
                        postArea.setText("");
                    } else {
                        JOptionPane.showMessageDialog(FeedScreen.this, "Error al guardar la publicación.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        JButton trashButton = new JButton("\uD83D\uDEAE"); // Emoji de bote de basura
        trashButton.setBackground(Color.BLACK);
        trashButton.setForeground(Color.WHITE);
        trashButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        trashButton.setFocusPainted(false);
        trashButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el panel de publicación padre del botón
                JPanel parentPanel = (JPanel) trashButton.getParent();
                // Obtener el índice del panel de publicación dentro de feedPanel
                int index = feedPanel.getComponentZOrder(parentPanel);
                // Obtener el componente en la posición index de postsStack
                JPanel postToRemove = postsStack.get(index);
                // Eliminar la publicación de la base de datos
                removePostFromDatabase(postToRemove);
                // Remover el panel de publicación de la interfaz de usuario
                feedPanel.remove(postToRemove);
                // Revalidar y repintar el feedPanel
                feedPanel.revalidate();
                feedPanel.repaint();
            }
        });

        deleteButton = new JButton("Eliminar Último Post");
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeLastPost();
            }
        });

        JPanel postInputPanel = new JPanel(new BorderLayout());
        postInputPanel.setBackground(Color.BLACK);
        postInputPanel.add(postArea, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.add(tweetButton);
        buttonsPanel.add(trashButton);
        buttonsPanel.add(deleteButton);

        postPanel.add(postInputPanel, BorderLayout.CENTER);
        postPanel.add(buttonsPanel, BorderLayout.SOUTH);
        tweetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = postArea.getText();
                if (!text.trim().isEmpty()) {
                    if (addPostToDatabase(userId, text)) {
                        String username = getUsernameFromDatabase(userId); // Obtener el nombre del usuario
                        addPost(username, text); // Usar el nombre del usuario
                        postArea.setText("");
                    } else {
                        JOptionPane.showMessageDialog(FeedScreen.this, "Error al guardar la publicación.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        trashButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        // Obtener el panel de publicación padre del botón
        JPanel parentPanel = (JPanel) trashButton.getParent();
        // Obtener el índice del panel de publicación dentro de feedPanel
        int index = feedPanel.getComponentZOrder(parentPanel);
        // Obtener el componente en la posición index de postsStack
        JPanel postToRemove = postsStack.get(index);
        // Obtener el contenido de la publicación
        String content = ((JLabel) postToRemove.getComponent(0)).getText();
        // Obtener el ID de la publicación
        int postId = obtenerIdDeLaPublicacion(content);
        // Eliminar la publicación por su ID
        removePostById(postId);
    }
});


        return postPanel;
    }

    private void removePostFromDatabase(JPanel postToRemove) {
        // Obtener el índice del panel de publicación dentro de feedPanel
        int index = feedPanel.getComponentZOrder(postToRemove);
        // Obtener el componente en la posición index de postsStack
        JPanel postPanel = postsStack.get(index);

        // Obtener el contenido de la publicación
        String content = ((JLabel) postPanel.getComponent(0)).getText(); // Suponiendo que el texto está en el primer componente del panel

        // Realizar cualquier lógica adicional, como obtener el ID de la publicación
        int postId = obtenerIdDeLaPublicacion(content); // Aquí debes implementar la lógica para obtener el ID de la publicación

        // Eliminar la publicación de la base de datos
        if (eliminarPublicacionDeLaBaseDeDatos(postId)) {
            // Si la eliminación es exitosa, también eliminamos la publicación de la interfaz de usuario
            feedPanel.remove(postPanel);
            postsStack.remove(postPanel);
            feedPanel.revalidate();
            feedPanel.repaint();
        } else {
            // Manejar el caso en el que la eliminación de la publicación de la base de datos falle
            JOptionPane.showMessageDialog(FeedScreen.this, "Error al eliminar la publicación de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

// Método para obtener el ID de la publicación a partir de su contenido
    private int obtenerIdDeLaPublicacion(String content) {
        
        String query = "SELECT id FROM red_social.publicaciones WHERE contenido = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, content);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Retorna -1 si no se encuentra el ID
    }

// Método para eliminar la publicación de la base de datos
    private boolean eliminarPublicacionDeLaBaseDeDatos(int postId) {
        String query = "DELETE FROM publicaciones WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, postId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Devuelve true si se eliminó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private void removePostById(int postId) {
    // Eliminar la publicación de la base de datos
    if (eliminarPublicacionDeLaBaseDeDatos(postId)) {
        // Buscar el panel de publicación correspondiente al postId y eliminarlo de la interfaz de usuario
        for (int i = 0; i < postsStack.size(); i++) {
            JPanel postPanel = postsStack.get(i);
            String content = ((JLabel) postPanel.getComponent(0)).getText();
            int id = obtenerIdDeLaPublicacion(content);
            if (id == postId) {
                feedPanel.remove(postPanel);
                postsStack.remove(postPanel);
                feedPanel.revalidate();
                feedPanel.repaint();
                return; // Terminar el bucle una vez que se haya eliminado la publicación
            }
        }
    } else {
        // Manejar el caso en el que la eliminación de la publicación de la base de datos falle
        JOptionPane.showMessageDialog(FeedScreen.this, "Error al eliminar la publicación de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


 private void addPost(String user, String text) {
    JPanel postPanel = new JPanel(new BorderLayout());
    postPanel.setBackground(Color.DARK_GRAY);
    postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JLabel postLabel = new JLabel("<html>" + user + ":<br>" + text.replaceAll("\n", "<br>") + "</html>");
    postLabel.setForeground(Color.WHITE);

    // Panel para comentarios
    JPanel commentPanel = new JPanel();
    commentPanel.setLayout(new BoxLayout(commentPanel, BoxLayout.Y_AXIS));
    commentPanel.setBackground(Color.DARK_GRAY);

    // Separador de comentarios
    JLabel separator = new JLabel("Comentarios");
    separator.setForeground(Color.LIGHT_GRAY);
    commentPanel.add(separator);

    // Área de texto para escribir comentarios
    JTextArea commentArea = new JTextArea("Escribe un comentario...");
    commentArea.setLineWrap(true);
    commentArea.setWrapStyleWord(true);
    commentArea.setBackground(Color.LIGHT_GRAY);
    commentArea.setForeground(Color.BLACK);
    commentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    commentArea.setPreferredSize(new Dimension(getWidth(), 50));

    // Botón para enviar comentarios
    JButton commentButton = new JButton("Comentar");
    commentButton.setBackground(Color.BLUE);
    commentButton.setForeground(Color.WHITE);
    commentButton.setFocusPainted(false);
    commentButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    commentButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String commentText = commentArea.getText();
        if (!commentText.trim().isEmpty()) {
            JPanel postPanel = (JPanel) commentButton.getParent().getParent(); // Obtener el panel de la publicación actual
            JLabel postLabel = (JLabel) postPanel.getComponent(0); // Obtener la etiqueta de la publicación
            String content = postLabel.getText(); // Obtener el contenido de la publicación
            int postId = obtenerIdDeLaPublicacion(content); // Obtener el ID de la publicación
             String username = getUsernameFromDatabase(userId);
            addComment(commentPanel, username, commentText); // Cambia "Usuario" por el nombre del usuario si lo tienes
            commentArea.setText("");
            addCommentToDatabase(postId, userId, commentText); // Agrega los parámetros postId y userId según corresponda
        }
    }
});

    JPanel commentInputPanel = new JPanel(new BorderLayout());
    commentInputPanel.setBackground(Color.DARK_GRAY);
    commentInputPanel.add(commentArea, BorderLayout.CENTER);
    commentInputPanel.add(commentButton, BorderLayout.EAST);

    postPanel.add(postLabel, BorderLayout.CENTER);
    postPanel.add(commentPanel, BorderLayout.SOUTH);
    postPanel.add(commentInputPanel, BorderLayout.NORTH);

    feedPanel.add(postPanel);
    postsStack.push(postPanel);
    feedPanel.revalidate();
    feedPanel.repaint();
}
   private void addComment(JPanel commentPanel, String user, String text) {
    JLabel commentLabel = new JLabel("<html>" + user + ": " + text.replaceAll("\n", "<br>") + "</html>");
    commentLabel.setForeground(Color.WHITE);
    commentPanel.add(commentLabel);
    commentPanel.revalidate();
    commentPanel.repaint();
}
    private boolean addCommentToDatabase(int postId, int userId, String content) {
        String query = "INSERT INTO comentarios (publicacion_id, usuario_id, contenido, fecha_comentario) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, postId);
            statement.setInt(2, userId);
            statement.setString(3, content);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private boolean addPostToDatabase(int userId, String content) {
        String query = "INSERT INTO publicaciones (usuario_id, contenido, fecha_publicacion) VALUES (?, ?, CURRENT_TIMESTAMP)";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            statement.setString(2, content);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getUsernameFromDatabase(int userId) {
        String query = "SELECT nombre FROM usuarios WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Usuario";
    }
private void loadPostsFromDatabase() {
    String query = "SELECT u.nombre, p.contenido, p.id AS publicacion_id "
            + "FROM publicaciones p "
            + "JOIN usuarios u ON p.usuario_id = u.id "
            + "ORDER BY p.id ASC";
    try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
        while (resultSet.next()) {
            String username = resultSet.getString("nombre");
            String content = resultSet.getString("contenido");
            int postId = resultSet.getInt("publicacion_id");
            addPost(username, content);
            loadCommentsFromDatabase(postId);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void loadCommentsFromDatabase(int postId) {
    String query = "SELECT u.nombre, c.contenido "
            + "FROM comentarios c "
            + "JOIN usuarios u ON c.usuario_id = u.id "
            + "WHERE c.publicacion_id = ? "
            + "ORDER BY c.id ASC";
    try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, postId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String username = resultSet.getString("nombre");
            String content = resultSet.getString("contenido");
            addComment(postsStack.peek(), username, content); // Añadir comentario al post actual
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    private void removeLastPost() {
        if (!postsStack.isEmpty()) {
            JPanel lastPost = postsStack.pop();
            feedPanel.remove(lastPost);
            feedPanel.revalidate();
            feedPanel.repaint();
        }
    }

    private void logout() {
        dispose(); // Cierra la ventana actual
        new LoginScreen().setVisible(true); // Abre la pantalla de inicio de sesión
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FeedScreen(1)); // Pasa un userId de prueba
    }
}

class JTextFieldLimit extends javax.swing.text.PlainDocument {

    private int limit;

    JTextFieldLimit(int limit) {
        super();
        this.limit = limit;
    }

    JTextFieldLimit(int limit, boolean upper) {
        super();
        this.limit = limit;
    }

    @Override
    public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
        if (str == null) {
            return;
        }

        if ((getLength() + str.length()) <= limit) {
            super.insertString(offset, str, attr);
        }
    }
}

class CustomScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = Color.DARK_GRAY;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private JButton createZeroButton() {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(0, 0));
        button.setMinimumSize(new Dimension(0, 0));
        button.setMaximumSize(new Dimension(0, 0));
        return button;
    }
}
