package ui;
/*
import dao.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Interfaz extends JFrame {
    private JTextArea postArea;
    private JButton tweetButton;
    private JButton deleteButton;
    private JPanel feedPanel;
    private JScrollPane scrollPane;
    private JPanel sidebarPanel;
    private Stack<JPanel> postsStack;

    public  Interfaz() {
        setTitle("Feed de Publicaciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
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
                    addPost(text);
                    postArea.setText("");
                }
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
        buttonsPanel.add(deleteButton);

        postPanel.add(postInputPanel, BorderLayout.CENTER);
        postPanel.add(buttonsPanel, BorderLayout.SOUTH);

        return postPanel;
    }

    private void addPost(String text) {
        JPanel postPanel = new JPanel(new BorderLayout());
        postPanel.setBackground(Color.DARK_GRAY);
        postPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel postLabel = new JLabel("<html>" + text.replaceAll("\n", "<br>") + "</html>");
        postLabel.setForeground(Color.WHITE);

        postPanel.add(postLabel, BorderLayout.CENTER);

        feedPanel.add(postPanel);
        postsStack.push(postPanel);
        feedPanel.revalidate();
        feedPanel.repaint();
    }

    private void removeLastPost() {
        if (!postsStack.isEmpty()) {
            JPanel lastPost = postsStack.pop();
            feedPanel.remove(lastPost);
            feedPanel.revalidate();
            feedPanel.repaint();
        }
    }

   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(FeedScreen::new);
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
}
*/