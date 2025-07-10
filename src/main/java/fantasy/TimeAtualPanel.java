package main.java.fantasy;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;

public class TimeAtualPanel extends JPanel {
    private FantasyApp app;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaTime;
    private JLabel totalLabel;

    public TimeAtualPanel(FantasyApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Seu Time", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        modeloLista = new DefaultListModel<>();
        listaTime = new JList<>(modeloLista);
        listaTime.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(listaTime), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new GridLayout(3, 2, 10, 10)); // Ajustado pra 6 botões

        JButton remover = new JButton("Remover Jogador Selecionado");
        remover.addActionListener(e -> removerSelecionado());

        JButton salvar = new JButton("Salvar Time");
        salvar.addActionListener(e -> salvarTimeEmArquivo());

        JButton carregar = new JButton("Carregar Time");
        carregar.addActionListener(e -> carregarTimeDeArquivo());

        JButton voltar = new JButton("Voltar ao Mercado");
        voltar.addActionListener(e -> app.mostrarTela("mercado"));

        JButton detalhes = new JButton("Ver Detalhes");
        detalhes.addActionListener(e -> {
            int indice = listaTime.getSelectedIndex();
            if (indice == -1) return;

            Jogador selecionado = MercadoPanel.timeSelecionado.get(indice);
            app.getMercado().mostrarDetalhes(selecionado);
        });


        totalLabel = new JLabel("Total gasto: $0", JLabel.CENTER);

        botoes.add(remover);
        botoes.add(salvar);
        botoes.add(carregar);
        botoes.add(voltar);
        botoes.add(detalhes);
        botoes.add(totalLabel);

        add(botoes, BorderLayout.SOUTH);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            atualizarLista();
        }
    }

    private void atualizarLista() {
        modeloLista.clear();
        int total = 0;
        int semanaAtual = 2;
        for (Jogador j : MercadoPanel.timeSelecionado) {
            double media = j.getMediaSemanaAtual(semanaAtual);
            String info = String.format("%-10s | %-15s | %-10s | Média: %.1f",
                    j.getPosicao(), j.getNome(), "(" + j.getTime() + ")", media);
            modeloLista.addElement(info);
            total += j.getCusto();
        }
        totalLabel.setText("Total gasto: $" + total);
    }

    private void removerSelecionado() {
        int indice = listaTime.getSelectedIndex();
        if (indice == -1) return;

        Jogador removido = MercadoPanel.timeSelecionado.remove(indice);
        atualizarLista();
        JOptionPane.showMessageDialog(this, removido.getNome() + " foi removido do seu time!");
    }


    private void salvarTimeEmArquivo() {
        try {
            PrintWriter writer = new PrintWriter("meu_time.txt");
            int total = 0;

            for (Jogador j : MercadoPanel.timeSelecionado) {
                writer.println(j.getPosicao() + " - " + j.getNome() + " (" + j.getTime() + ") - $" + j.getCusto());
                total += j.getCusto();
            }

            writer.println();
            writer.println("Total gasto: $" + total);
            writer.close();

            JOptionPane.showMessageDialog(this, "Time salvo com sucesso em 'meu_time.txt'!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o time: " + e.getMessage());
        }
    }

    private void carregarTimeDeArquivo() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("meu_time.txt"));
            String linha;
            MercadoPanel.timeSelecionado.clear();

            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty() || linha.startsWith("Total")) continue;

                // Ex: "Top - Faker (T1) - $10"
                String[] partes = linha.split(" - ");
                if (partes.length != 3) continue;

                String nome = partes[1].substring(0, partes[1].indexOf("(")).trim();

                // Buscar jogador pelo nome
                Jogador encontrado = null;
                for (Jogador j : app.getMercado().getTodosJogadores()) {
                    if (j.getNome().equalsIgnoreCase(nome)) {
                        encontrado = j;
                        break;
                    }
                }

                if (encontrado != null) {
                    MercadoPanel.timeSelecionado.add(encontrado);
                }
            }

            reader.close();
            atualizarLista();
            JOptionPane.showMessageDialog(this, "Time carregado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar o time: " + e.getMessage());
        }
    }
}
