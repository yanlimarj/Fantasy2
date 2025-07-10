package main.java.fantasy;

import javax.swing.*;
import java.awt.*;

public class FantasyApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private MercadoPanel mercadoPanel;


    public FantasyApp() {
        setTitle("Fantasy LoL Global");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Telas
        LoginPanel loginPanel = new LoginPanel(this);
        mercadoPanel = new MercadoPanel(this);
        mainPanel.add(mercadoPanel, "mercado");

        TimeAtualPanel timeAtualPanel = new TimeAtualPanel(this);


        // Adiciona os painéis com identificadores
        mainPanel.add(loginPanel, "login");
        mainPanel.add(mercadoPanel, "mercado");
        mainPanel.add(timeAtualPanel, "time");

        add(mainPanel);
        setVisible(true);
    }

    public void mostrarTela(String nomeTela) {
        cardLayout.show(mainPanel, nomeTela);
    }
    public MercadoPanel getMercado() {
        return mercadoPanel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(FantasyApp::new);
    }



}
