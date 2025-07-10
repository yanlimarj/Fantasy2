package main.java.fantasy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarPartidaDialog extends JDialog {

    public AdicionarPartidaDialog(JFrame parent, Jogador jogador, int semanaAtual) {
        super(parent, "Adicionar Partida - " + jogador.getNome(), true);
        setSize(600, 700);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(0, 2));

        JTextField campoAdversario = new JTextField();
        JTextField campoCampeao = new JTextField();
        JTextField campoAbates = new JTextField();
        JTextField campoMortes = new JTextField();
        JTextField campoAssistencias = new JTextField();
        JCheckBox checkVitoria = new JCheckBox("Vitória");
        JCheckBox checkStomp = new JCheckBox("Vitória Stomp");
        JTextField campoGold = new JTextField();
        JTextField campoObjNeutros = new JTextField();
        JTextField campoDragsAnciao = new JTextField();
        JTextField campoBaroes = new JTextField();
        JCheckBox checkAtakhan = new JCheckBox("Atakhan");
        JTextField campoTriple = new JTextField();
        JTextField campoQuadra = new JTextField();
        JTextField campoPenta = new JTextField();
        JTextField campoVisao = new JTextField();
        JTextField campoAbatesTime = new JTextField();
        JCheckBox checkPOM = new JCheckBox("POM");
        JTextField campoDano = new JTextField();
        JCheckBox checkProeza = new JCheckBox("Proeza");
        JTextField campoCS = new JTextField();
        JTextField campoSemana = new JTextField(String.valueOf(semanaAtual));

        add(new JLabel("Adversário:")); add(campoAdversario);
        add(new JLabel("Campeão usado:")); add(campoCampeao);
        add(new JLabel("Abates:")); add(campoAbates);
        add(new JLabel("Mortes:")); add(campoMortes);
        add(new JLabel("Assistências:")); add(campoAssistencias);
        add(checkVitoria); add(checkStomp);
        add(new JLabel("Gold total:")); add(campoGold);
        add(new JLabel("Objetivos neutros:")); add(campoObjNeutros);
        add(new JLabel("Dragões Anciões:")); add(campoDragsAnciao);
        add(new JLabel("Barões:")); add(campoBaroes);
        add(checkAtakhan); add(new JLabel(""));
        add(new JLabel("Triple Kills:")); add(campoTriple);
        add(new JLabel("Quadra Kills:")); add(campoQuadra);
        add(new JLabel("Penta Kills:")); add(campoPenta);
        add(new JLabel("Placar de visão:")); add(campoVisao);
        add(new JLabel("Abates do time:")); add(campoAbatesTime);
        add(checkPOM); add(checkProeza);
        add(new JLabel("Dano na partida:")); add(campoDano);
        add(new JLabel("CS:")); add(campoCS);
        add(new JLabel("Semana:")); add(campoSemana);

        JButton btnConfirmar = new JButton("Confirmar");
        add(btnConfirmar); add(new JLabel(""));

        btnConfirmar.addActionListener(e -> {
            try {
                int semana = Integer.parseInt(campoSemana.getText());

                Jogador.Partida partida = new Jogador.Partida(
                        campoAdversario.getText(),
                        campoCampeao.getText(),
                        semana,
                        Integer.parseInt(campoAbates.getText()),
                        Integer.parseInt(campoMortes.getText()),
                        Integer.parseInt(campoAssistencias.getText()),
                        checkVitoria.isSelected(),
                        checkStomp.isSelected(),
                        Integer.parseInt(campoGold.getText()),
                        Integer.parseInt(campoObjNeutros.getText()),
                        Integer.parseInt(campoDragsAnciao.getText()),
                        Integer.parseInt(campoBaroes.getText()),
                        checkAtakhan.isSelected(),
                        Integer.parseInt(campoTriple.getText()),
                        Integer.parseInt(campoQuadra.getText()),
                        Integer.parseInt(campoPenta.getText()),
                        Integer.parseInt(campoVisao.getText()),
                        Integer.parseInt(campoAbatesTime.getText()),
                        checkPOM.isSelected(),
                        Integer.parseInt(campoDano.getText()),
                        checkProeza.isSelected(),
                        Integer.parseInt(campoCS.getText())
                );

                jogador.adicionarPartidaCompleta(partida);
                JOptionPane.showMessageDialog(this, "Partida adicionada com sucesso!");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Verifique os valores numéricos.");
            }
        });

        setVisible(true);
    }
}
