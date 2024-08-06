import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class TokenScreen {
        public static void main(String[] args) {
// Create frame with title Registration Demo
                JFrame frame = new JFrame();
                frame.setTitle("Access token generator - International - V2.1");
                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
                JPanel panel = new JPanel(new GridBagLayout());
// Constraints for the layout
                GridBagConstraints constr = new GridBagConstraints();
                constr.insets = new Insets(5, 5, 5, 5);
                constr.anchor = GridBagConstraints.WEST;
// Setting initial grid values to 0, 0

                JLabel nameLabel = new JLabel("Username :");
                JLabel passwordLabel = new JLabel("Password :");
                JLabel environmentLabel = new JLabel("Environment :");
                JLabel systemLabel = new JLabel("System :");
                JLabel tokenLabel = new JLabel("Token :");

                JTextField nameInput = new JTextField(20);
                JTextField passwordInput = new JPasswordField(20);
                String[] environmentOptions = {
                        "568 - devInt            ",
                        "568 - qaInt"
                };
                JComboBox<String> environmentComboBox = new JComboBox<>(environmentOptions);
                String[] systemOptions = {"ImportInt                 "};
                JComboBox<String> systemComboBox = new JComboBox<>(systemOptions);
                JTextArea tokenArea = new JTextArea(5, 20);
                tokenArea.setLineWrap(true);  // Habilita a quebra de linha
                tokenArea.setWrapStyleWord(true);  // Habilita a quebra de palavras

                // Default values
                nameInput.setText("0180500");
                passwordInput.setText("swadm123");
                environmentComboBox.setSelectedIndex(0); // Define a seleção inicial
                systemComboBox.setSelectedIndex(0);

                Integer coluna = 0;
                // Username
                constr.gridx = coluna;
                constr.gridy = 0;
                panel.add(nameLabel, constr);
                constr.gridx = coluna + 1;
                panel.add(nameInput, constr);
                // Password
                constr.gridx = coluna;
                constr.gridy = 1;
                panel.add(passwordLabel, constr);
                constr.gridx = coluna + 1;
                panel.add(passwordInput, constr);
                // Environment
                constr.gridx = coluna;
                constr.gridy = 2;
                panel.add(environmentLabel, constr);
                constr.gridx = coluna + 1;
                panel.add(environmentComboBox, constr);
                // System
                constr.gridx = coluna;
                constr.gridy = 3;
                panel.add(systemLabel, constr);
                constr.gridx = coluna + 1;
                panel.add(systemComboBox, constr);
                // Token
                constr.gridx = coluna;
                constr.gridy = 4;
                panel.add(tokenLabel, constr);
                constr.gridx = coluna + 1;
                panel.add(tokenArea, constr);

//                constr.gridwidth = 2;
//                constr.anchor = GridBagConstraints.CENTER;
                // Buttons
                JButton buttonGenerate = new JButton(" Generate ");
                JButton buttonClear = new JButton(" Clear ");
                JButton buttonCopyClipboard = new JButton(" Copy ");
                JButton buttonExit = new JButton(" Exit ");

                // Button listeners
                buttonGenerate.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                                SwingUtilities.invokeLater(() -> tokenArea.setText("Processing ....."));

                                new Thread(() -> {
                                        try {
                                                tokenArea.setText(Actions.actionGenerate(nameInput, passwordInput, systemComboBox, environmentComboBox, tokenArea));
                                        } catch (IOException ex) {
                                                tokenArea.setText("Error processing token");
                                                throw new RuntimeException(ex);
                                        }
                                }).start();


                        }
                });
                buttonCopyClipboard.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                Actions.actionCopyClipboard(tokenArea);
                        }
                });
                buttonExit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                Actions.actionExit(frame);
                        }
                });
                buttonClear.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                                tokenArea.setText("");
                        }
                });

                constr.gridx = 0;
                constr.gridy = 6;
                panel.add(buttonGenerate, constr);
                constr.gridx = 2;
                constr.gridy = 6;
                panel.add(buttonClear, constr);
                constr.gridx = 1;
                constr.gridy = 6;
                panel.add(buttonCopyClipboard, constr);
                constr.gridx = 3;
                constr.gridy = 6;
                panel.add(buttonExit, constr);
//                mainPanel.add(headingPanel);
                mainPanel.add(panel);
                frame.add(mainPanel);
                frame.pack();
                frame.setSize(600, 350);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }
}
