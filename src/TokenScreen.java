import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class TokenScreen {
        public static void main(String[] args) {
// Create frame with title Registration Demo
                JFrame frame = new JFrame();
                frame.setTitle("Access token generator");
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
                String[] environmentOptions = {"devInt                ", "qaInt"};
                JComboBox<String> environmentComboBox = new JComboBox<>(environmentOptions);
                String[] systemOptions = {"ImportInt           "};
                JComboBox<String> systemComboBox = new JComboBox<>(systemOptions);
                JTextArea tokenArea = new JTextArea(2, 80);

                // Default values
                nameInput.setText("0180500");
                passwordInput.setText("swadm123");
                environmentComboBox.setSelectedIndex(0); // Define a seleção inicial
                systemComboBox.setSelectedIndex(0);

                // Username
                constr.gridx = 0;
                constr.gridy = 0;
                panel.add(nameLabel, constr);
                constr.gridx = 1;
                panel.add(nameInput, constr);
                // Password
                constr.gridx = 0;
                constr.gridy = 1;
                panel.add(passwordLabel, constr);
                constr.gridx = 1;
                panel.add(passwordInput, constr);
                // Environment
                constr.gridx = 0;
                constr.gridy = 2;
                panel.add(environmentLabel, constr);
                constr.gridx = 1;
                panel.add(environmentComboBox, constr);
                // System
                constr.gridx = 0;
                constr.gridy = 3;
                panel.add(systemLabel, constr);
                constr.gridx = 1;
                panel.add(systemComboBox, constr);
                // Token
                constr.gridx = 0;
                constr.gridy = 4;
                panel.add(tokenLabel, constr);
                constr.gridx = 1;
                panel.add(tokenArea, constr);

//                constr.gridwidth = 2;
//                constr.anchor = GridBagConstraints.CENTER;
                // Buttons
                JButton buttonGenerate = new JButton("Generate");
                JButton buttonCopyClipboard = new JButton("Copy Clipboard");
                JButton buttonExit = new JButton("Exit");

                // Button listeners
                buttonGenerate.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                                try {
                                        tokenArea.setText("Processing .....");
                                        tokenArea.setText(Actions.actionGenerate(nameInput, passwordInput, systemComboBox, environmentComboBox, tokenArea));
                                } catch (IOException ex) {
                                        tokenArea.setText("Error processing token");
                                        throw new RuntimeException(ex);
                                }
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

                constr.gridx = 0;
                constr.gridy = 5;
                panel.add(buttonGenerate, constr);
                constr.gridx = 1;
                constr.gridy = 5;
                panel.add(buttonCopyClipboard, constr);
                constr.gridx = 2;
                constr.gridy = 5;
                panel.add(buttonExit, constr);
//                mainPanel.add(headingPanel);
                mainPanel.add(panel);
                frame.add(mainPanel);
                frame.pack();
                frame.setSize(1200, 270);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }
}
