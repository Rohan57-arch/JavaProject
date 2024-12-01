import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;

public class MainGUI extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signInButton, aboutButton, backButton;
    private JLabel messageLabel;

    // Valid usernames and password
    private final List<String> validUsernames = Arrays.asList("Adnoo123", "Aban911", "Afra338", "Sayed008", "Rohan67");
    private final String validPassword = "123456";

    private JPanel mainPanel; // Holds both Home and About panels
    private CardLayout cardLayout; // For switching between Home and About

    public MainGUI() {
        setTitle("SkyVoyager");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Apply modern Nimbus Look-and-Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to apply Nimbus look and feel.");
        }

        // Initialize CardLayout and main panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add Home and About pages to the main panel
        mainPanel.add(createHomePage(), "HomePage");
        mainPanel.add(createAboutPage(), "AboutPage");

        // Show the Home Page initially
        add(mainPanel);
        cardLayout.show(mainPanel, "HomePage");
    }

    // Create the Home Page
    private JPanel createHomePage() {
        JPanel homePanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("Background.png");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Title Label
        JLabel titleLabel = new JLabel("Welcome to SkyVoyager", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        homePanel.add(titleLabel, gbc);

        // Username Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        homePanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        homePanel.add(usernameField, gbc);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        homePanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        homePanel.add(passwordField, gbc);

        // Sign-In Button
        signInButton = new JButton("Sign In");
        signInButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        signInButton.setBackground(new Color(70, 130, 180));
        signInButton.setForeground(Color.WHITE);
        signInButton.setFocusPainted(false);
        signInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText().trim();
                String password = new String(passwordField.getPassword());

                // Validate credentials
                if (validUsernames.contains(username) && password.equals(validPassword)) {
                    CombinedBookingSystem hotelGUI = new CombinedBookingSystem();
                    hotelGUI.setVisible(true);
                    dispose(); // Close login window
                } else {
                    messageLabel.setText("Invalid Username or Password.");
                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        homePanel.add(signInButton, gbc);

        // About Button
        aboutButton = new JButton("About");
        aboutButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        aboutButton.setBackground(new Color(100, 149, 237));
        aboutButton.setForeground(Color.WHITE);
        aboutButton.setFocusPainted(false);
        aboutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "AboutPage"); // Switch to About Page
            }
        });
        gbc.gridy = 4;
        homePanel.add(aboutButton, gbc);

        // Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageLabel.setForeground(Color.RED);
        gbc.gridy = 5;
        homePanel.add(messageLabel, gbc);

        return homePanel;
    }

    // Create the About Page
   // Create the About Page
   private JPanel createAboutPage() {
    // Custom JPanel for the About page with background image
    JPanel aboutPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon icon = new ImageIcon("AboutBackground.png"); // Replace with your image path
            g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };
    aboutPanel.setLayout(new BorderLayout()); // Use BorderLayout for positioning components

    // Title Label
    JLabel aboutTitle = new JLabel("About SkyVoyager", SwingConstants.CENTER);
    aboutTitle.setFont(new Font("Serif", Font.BOLD, 24));
    aboutTitle.setForeground(Color.WHITE); // Ensure the text color is visible
    aboutPanel.add(aboutTitle, BorderLayout.NORTH);

    // About Text (with transparent background)
    JTextArea aboutText = new JTextArea(
        "Welcome to SkyVoyager – your one-stop solution for seamless travel planning. " +
        "Our platform connects you to the best flights, hotels, and travel services, " +
        "making your journey smooth and enjoyable.\n\n" +
        "SkyVoyager stands out with its innovative technology, customer-centric approach, " +
        "and commitment to sustainable travel. Whether you're booking flights, " +
        "planning personalized itineraries, or finding eco-friendly accommodations, " +
        "we're here to make it effortless.\n\n" +
        "Join us to explore the world with ease, responsibility, and style."
    );
    aboutText.setFont(new Font("SansSerif", Font.PLAIN, 14));
    aboutText.setForeground(Color.BLACK); // Set text color to white
    aboutText.setOpaque(false); // Make the background transparent
    aboutText.setEditable(false);
    aboutText.setLineWrap(true);
    aboutText.setWrapStyleWord(true);

    // Add the text area inside a JScrollPane to ensure proper layout
    JScrollPane scrollPane = new JScrollPane(aboutText);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove the border for cleaner look
    aboutPanel.add(scrollPane, BorderLayout.CENTER);

    // Back Button
    backButton = new JButton("Back");
    backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
    backButton.setBackground(new Color(100, 149, 237));
    backButton.setForeground(Color.WHITE);
    backButton.setFocusPainted(false);
    backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(mainPanel, "HomePage"); // Switch back to Home Page
        }
    });
    aboutPanel.add(backButton, BorderLayout.SOUTH);

    return aboutPanel;
}




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
