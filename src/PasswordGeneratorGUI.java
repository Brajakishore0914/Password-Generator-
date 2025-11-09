import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordGeneratorGUI extends JFrame {

    // Character sets for password generation
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()_+-=[]{}|;:',.<>?/`~";
    private static final SecureRandom rnd = new SecureRandom();

    public PasswordGeneratorGUI() {
        setTitle("Modern Password Generator");
        setSize(480, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // --- ICON LOADING (Classpath Method for Distributable JAR) ---
        try {
            // IMPORTANT: This path (starting with /) assumes the icon file
            // is placed in a 'resources' folder that is correctly added to the classpath.
            java.net.URL iconURL = getClass().getResource("C:\\Users\\tapas\\OneDrive\\Desktop\\IdeaProjects\\Password Generator\\resources\\ChatGPT Image Nov 9, 2025, 04_53_19 PM.png");
            if (iconURL != null) {
                Image icon = new ImageIcon(iconURL).getImage();
                setIconImage(icon);
            } else {
                System.err.println("Icon resource not found in classpath: /password_icon.png");
            }
        } catch (Exception e) {
            System.err.println("Error loading window icon: " + e.getMessage());
        }
        // ------------------------------------------------------------------

        // Modern Background
        getContentPane().setBackground(new Color(36, 41, 47));
        setLayout(null);

        JLabel title = new JLabel("Secure Password Generator");
        title.setBounds(90, 10, 300, 30);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        add(title);

        JLabel lenLabel = new JLabel("Length:");
        lenLabel.setForeground(Color.WHITE);
        lenLabel.setBounds(40, 60, 60, 25);
        add(lenLabel);

        JTextField lengthField = new JTextField("12");
        lengthField.setBounds(110, 60, 50, 25);
        add(lengthField);

        JCheckBox upper = new JCheckBox("Uppercase (A-Z)", true);
        JCheckBox lower = new JCheckBox("Lowercase (a-z)", true);
        JCheckBox digits = new JCheckBox("Digits (0-9)", true);
        JCheckBox symbols = new JCheckBox("Symbols (!@#$)", true);

        // Position and style the checkboxes
        JCheckBox[] boxes = {upper, lower, digits, symbols};
        int y = 60;
        for (JCheckBox box : boxes) {
            y += 30;
            box.setBounds(180, y, 200, 25);
            box.setForeground(Color.WHITE);
            box.setBackground(new Color(36, 41, 47));
            add(box);
        }

        JTextField outputField = new JTextField();
        outputField.setBounds(40, 170, 400, 30);
        outputField.setEditable(false);
        outputField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(outputField);

        // Modern Button Style for Generate
        JButton generateBtn = new JButton("Generate");
        generateBtn.setBounds(40, 130, 150, 30);
        generateBtn.setBackground(new Color(65, 132, 243));
        generateBtn.setForeground(Color.WHITE);
        generateBtn.setFocusPainted(false);
        generateBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(generateBtn);

        // Modern Button Style for Copy
        JButton copyBtn = new JButton("Copy");
        copyBtn.setBounds(220, 130, 80, 30);
        copyBtn.setBackground(new Color(88, 166, 255));
        copyBtn.setForeground(Color.WHITE);
        copyBtn.setFocusPainted(false);
        copyBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(copyBtn);

        // Action Listener for Generate Button
        generateBtn.addActionListener(e -> {
            int length;
            try {
                length = Integer.parseInt(lengthField.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid number for length.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String password = generatePassword(length,
                    upper.isSelected(), lower.isSelected(),
                    digits.isSelected(), symbols.isSelected());

            outputField.setText(password);
        });

        // Action Listener for Copy Button
        copyBtn.addActionListener(e -> {
            String password = outputField.getText();
            if (!password.isEmpty()) {
                StringSelection select = new StringSelection(password);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(select, null);
            }
        });

        setVisible(true);
    }

    /**
     * Generates a secure random password based on specified criteria.
     */
    public static String generatePassword(int length, boolean upper, boolean lower,
                                          boolean digits, boolean symbols) {

        if (length <= 0) return "";

        List<Character> pass = new ArrayList<>();
        StringBuilder all = new StringBuilder();

        // Ensure at least one character from each selected set is included
        if (upper) { all.append(UPPER); pass.add(random(UPPER)); }
        if (lower) { all.append(LOWER); pass.add(random(LOWER)); }
        if (digits) { all.append(DIGITS); pass.add(random(DIGITS)); }
        if (symbols) { all.append(SYMBOLS); pass.add(random(SYMBOLS)); }

        // Fill the rest with the password length from the combined pool
        for (int i = pass.size(); i < length; i++) {
            if (!all.isEmpty()) {
                pass.add(random(all.toString()));
            } else {
                // This happens if no character type was selected (shouldn't occur if
                // checkboxes are handled correctly, but good for robustness)
                return "ERROR: Select character types";
            }
        }

        // Shuffle the characters to randomize the position of the required characters
        Collections.shuffle(pass, rnd);

        StringBuilder out = new StringBuilder();
        for (char c : pass) out.append(c);
        return out.toString();
    }

    /**
     * Returns a single random character from the given string pool.
     */
    private static char random(String s) {
        return s.charAt(rnd.nextInt(s.length()));
    }

    public static void main(String[] args) {
        // Runs the GUI setup on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> new PasswordGeneratorGUI());
    }
}