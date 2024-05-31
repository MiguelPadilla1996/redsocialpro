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

    private JTextArea areaPublicaciones;
    private JButton tweetButton;
    private JButton botonBorrar;
    private JButton botonSalir; // Botón de cierre de sesión
    private JPanel panelPublicaciones;
    private JScrollPane scrollPane;
    private JPanel panelLateral;
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

        
        setLayout(new BorderLayout());

        
        panelLateral = crearPanelLateral();
        add(panelLateral, BorderLayout.WEST);

        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);
        add(mainPanel, BorderLayout.CENTER);

        
        JPanel postPanel = crearPanelPublicaciones();
        mainPanel.add(postPanel, BorderLayout.NORTH);

        
        panelPublicaciones = new JPanel();
        panelPublicaciones.setLayout(new BoxLayout(panelPublicaciones, BoxLayout.Y_AXIS));
        panelPublicaciones.setBackground(Color.BLACK);

        scrollPane = new JScrollPane(panelPublicaciones);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        cargarPublicaciones();
        
        Timer timer = new Timer(50000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            actualizarPublicacionesYComentarios();
        }
    });
    timer.start();
        
        
        setVisible(true);
    }

    private JPanel crearPanelLateral() {
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
        botonSalir = new JButton("Cerrar Sesión");
        botonSalir.setForeground(Color.WHITE);
        botonSalir.setBackground(Color.BLACK);
        botonSalir.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botonSalir.setFocusPainted(false);
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        sidebar.add(Box.createVerticalGlue()); // Empuja el botón hacia abajo
        sidebar.add(botonSalir);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        return sidebar;
    }

    private JPanel crearPanelPublicaciones() {
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBackground(Color.BLACK);
        postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        areaPublicaciones = new JTextArea("¿Qué está pasando?!");
        areaPublicaciones.setLineWrap(true);
        areaPublicaciones.setWrapStyleWord(true);
        areaPublicaciones.setBackground(Color.DARK_GRAY);
        areaPublicaciones.setForeground(Color.WHITE);
        areaPublicaciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        areaPublicaciones.setPreferredSize(new Dimension(getWidth(), 100));
        areaPublicaciones.setDocument(new JTextFieldLimit(300));

        tweetButton = new JButton("Postear");
        tweetButton.setBackground(Color.BLUE);
        tweetButton.setForeground(Color.WHITE);
        tweetButton.setFocusPainted(false);
        tweetButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tweetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = areaPublicaciones.getText();
                if (!text.trim().isEmpty()) {
                    if (guardarPublicacion(userId, text)) {
                        addPost("Usuario", text); // Puedes cambiar "Usuario" por el nombre del usuario si tienes esa información
                        areaPublicaciones.setText("");
                    } else {
                        JOptionPane.showMessageDialog(FeedScreen.this, "Error al guardar la publicación.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        

        botonBorrar = new JButton("Eliminar Último Post");
        botonBorrar.setBackground(Color.RED);
        botonBorrar.setForeground(Color.WHITE);
        botonBorrar.setFocusPainted(false);
        botonBorrar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botonBorrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeLastPost();
            }
        });

        JPanel postInputPanel = new JPanel(new BorderLayout());
        postInputPanel.setBackground(Color.BLACK);
        postInputPanel.add(areaPublicaciones, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonsPanel.setBackground(Color.BLACK);
        buttonsPanel.add(tweetButton);
        
        buttonsPanel.add(botonBorrar);

        postPanel.add(postInputPanel, BorderLayout.CENTER);
        postPanel.add(buttonsPanel, BorderLayout.SOUTH);
        tweetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = areaPublicaciones.getText();
                if (!text.trim().isEmpty()) {
                    if (guardarPublicacion(userId, text)) {
                        String username = traerNombreDeLaBaseDeDatos(userId); // Obtener el nombre del usuario
                        addPost(username, text); // Usar el nombre del usuario
                        areaPublicaciones.setText("");
                    } else {
                        JOptionPane.showMessageDialog(FeedScreen.this, "Error al guardar la publicación.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
   


        return postPanel;
    }

    

// Método para obtener el ID de la publicación a partir de su contenido
  private int obtenerIdDeLaPublicacion(int userId) {
    String query = "SELECT id FROM publicaciones WHERE usuario_id = ?";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt( 1, userId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt("id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Retorna -1 si no se encuentra el ID
}
private void actualizarPublicacionesYComentarios() {
    panelPublicaciones.removeAll(); // Limpia el panel de publicaciones antes de cargar nuevas
    postsStack.clear(); // Limpia la pila de publicaciones

    cargarPublicaciones(); // Carga todas las publicaciones

    // Para cada publicación en el panel, cargar sus comentarios
    for (Component component : panelPublicaciones.getComponents()) {
        if (component instanceof JPanel) {
            JPanel postPanel = (JPanel) component;
            JLabel postLabel = (JLabel) postPanel.getComponent(0);
            String content = postLabel.getText();
            int postId = obtenerIdDeLaPublicacion(userId); // Obtén el ID de la publicación
            JPanel commentPanel = (JPanel) postPanel.getComponent(2);
            cargarComentarios(postId, commentPanel);
        }
    }

    panelPublicaciones.revalidate();
    panelPublicaciones.repaint();
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
            int id = obtenerIdDeLaPublicacion(userId);
            if (id == postId) {
                panelPublicaciones.remove(postPanel);
                postsStack.remove(postPanel);
                panelPublicaciones.revalidate();
                panelPublicaciones.repaint();
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
    JTextArea commentArea = new JTextArea("");
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
                int postId = obtenerIdDeLaPublicacion(userId);
                
                String username = traerNombreDeLaBaseDeDatos(userId);
                addComment(commentPanel, username, commentText); // Cambia "Usuario" por el nombre del usuario si lo tienes
                commentArea.setText("");
                guardarComentario(postId, userId, commentText); 
                
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

    panelPublicaciones.add(postPanel);
    postsStack.push(postPanel);
    panelPublicaciones.revalidate();
    panelPublicaciones.repaint();

    // Cargar comentarios para esta publicación
    int postId = obtenerIdDeLaPublicacion(userId);
    cargarComentarios(postId, commentPanel);
}

  

   private void addComment(JPanel commentPanel, String user, String text) {
    JLabel commentLabel = new JLabel("<html>" + user + ": " + text.replaceAll("\n", "<br>") + "</html>");
    commentLabel.setForeground(Color.WHITE);
    commentPanel.add(commentLabel);
    commentPanel.revalidate();
    commentPanel.repaint();
}
    private boolean guardarComentario(int postId, int userId, String content) {
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


    private boolean guardarPublicacion(int userId, String content) {
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

    private String traerNombreDeLaBaseDeDatos(int userId) {
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
private void cargarPublicaciones() {
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
            
               
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void cargarComentarios(int postId, JPanel commentPanel) {
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
            addComment(commentPanel, username, content); // Añadir comentario al panel de comentarios de la publicación
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private void removeLastPost() {
        if (!postsStack.isEmpty()) {
            JPanel lastPost = postsStack.pop();
            panelPublicaciones.remove(lastPost);
            panelPublicaciones.revalidate();
            panelPublicaciones.repaint();
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
