package main.java.fantasy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPanel extends JPanel {
    public LoginPanel(FantasyApp app) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel userLabel = new JLabel("Usuário:");
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("Senha:");
        JPasswordField passField = new JPasswordField(15);

        JButton loginButton = new JButton("Entrar");

        loginButton.addActionListener(e -> {
            // Simula login sempre válido
            app.mostrarTela("mercado");
        });

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; add(userLabel, gbc);
        gbc.gridx = 1; add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; add(passLabel, gbc);
        gbc.gridx = 1; add(passField, gbc);

        gbc.gridwidth = 2; gbc.gridx = 0; gbc.gridy = 2;
        add(loginButton, gbc);
    }
}
