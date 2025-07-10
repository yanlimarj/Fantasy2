package main.java.fantasy;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static main.java.fantasy.Jogador.salvarJogador;

public class MercadoPanel extends JPanel {
    private FantasyApp app;
    private DefaultListModel<Jogador> modeloLista;
    private static JList<Jogador> listaJogadores;
    private List<Jogador> todosJogadores;

    public static List<Jogador> timeSelecionado = new ArrayList<>();

    public MercadoPanel(FantasyApp app) {
        this.app = app;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Mercado de Jogadores", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Modelo e lista
        modeloLista = new DefaultListModel<>();
        listaJogadores = new JList<>(modeloLista);
        listaJogadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listaJogadores), BorderLayout.CENTER);


        todosJogadores = carregarTodosJogadores();
        todosJogadores.sort(Comparator.comparing(Jogador::getNome));

        if (todosJogadores.isEmpty()) {
            todosJogadores = InicializadorDeJogadores.criarJogadoresIniciais();
            for (Jogador j : todosJogadores) {
                salvarJogador(j);
            }
        }

        atualizarLista(todosJogadores);

        // Botões
        JPanel botoes = new JPanel(new GridLayout(2, 1));

        JPanel filtros = new JPanel();
        for (Jogador.Posicao pos : Jogador.Posicao.values()) {
            JButton filtro = new JButton(pos.name());
            filtro.addActionListener(e -> aplicarFiltro(pos));
            filtros.add(filtro);
        }
        JButton mostrarTodos = new JButton("Todos");
        mostrarTodos.addActionListener(e -> atualizarLista(todosJogadores));
        filtros.add(mostrarTodos);

        JPanel acoes = new JPanel();
        JButton selecionar = new JButton("Selecionar");
        JButton detalhes = new JButton("Ver Detalhes");
        JButton adicionarPartida = new JButton("Adicionar Partida");
        JButton verTime = new JButton("Ver Meu Time");
        JButton fecharSemana = new JButton("Fechar Semana");
        fecharSemana.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Informe a nova semana (ex: 3):");
            if (input != null) {
                try {
                    int novaSemana = Integer.parseInt(input);
                    fecharSemana(novaSemana);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Número inválido.");
                }
            }
        });
        acoes.add(fecharSemana);


        selecionar.addActionListener(e -> selecionarJogador());
        detalhes.addActionListener(e -> mostrarDetalhes());
        adicionarPartida.addActionListener(e -> adicionarPartida());
        verTime.addActionListener(e -> app.mostrarTela("time"));

        acoes.add(selecionar);
        acoes.add(detalhes);
        acoes.add(adicionarPartida);
        acoes.add(verTime);

        botoes.add(filtros);
        botoes.add(acoes);
        add(botoes, BorderLayout.SOUTH);
    }

    private void atualizarLista(List<Jogador> jogadores) {
        modeloLista.clear();
        new File("jogadores").mkdirs();
        for (Jogador j : jogadores) {
            modeloLista.addElement(j);
        }
    }

    private void aplicarFiltro(Jogador.Posicao posicao) {
        List<Jogador> filtrados = todosJogadores.stream()
                .filter(j -> j.getPosicao() == posicao)
                .collect(Collectors.toList());
        atualizarLista(filtrados);
    }

    private void selecionarJogador() {
        Jogador selecionado = listaJogadores.getSelectedValue();
        if (selecionado == null) return;

        // Verifica se já tem jogador da mesma posição
        boolean jaTemNaPosicao = timeSelecionado.stream()
                .anyMatch(j -> j.getPosicao() == selecionado.getPosicao());

        if (jaTemNaPosicao) {
            JOptionPane.showMessageDialog(this, "Você já escolheu um jogador para essa posição!");
            return;
        }

        // Verifica limite de orçamento
        int totalAtual = timeSelecionado.stream().mapToInt(Jogador::getCusto).sum();
        if (totalAtual + selecionado.getCusto() > 50) {
            JOptionPane.showMessageDialog(this, "Você ultrapassou o limite de $50!");
            return;
        }

        timeSelecionado.add(selecionado);
        JOptionPane.showMessageDialog(this, selecionado.getNome() + " foi adicionado ao seu time!");
    }



    public void mostrarDetalhes() {
        Jogador j = listaJogadores.getSelectedValue();
        if (j == null) return;

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));

        // Foto do jogador
        ImageIcon foto = carregarImagem("imagens/" + j.getNome() + ".png", 100, 100);
        if (foto != null) {
            JLabel fotoLabel = new JLabel(foto);
            fotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            painel.add(fotoLabel);
        }

        // Infos principais
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append("Nome: ").append(j.getNome()).append("<br>");
        sb.append("Time: ").append(j.getTime()).append("<br>");
        sb.append("Posição: ").append(j.getPosicao()).append("<br>");
        sb.append("Custo: $").append(j.getCusto()).append("<br><br>");

        List<Double> historico = j.getHistoricoMedias();
        int ultimaSemanaComMedia = 0;

        for (int i = historico.size(); i > 0; i--) {
            if (historico.get(i - 1) > 0) {
                ultimaSemanaComMedia = i;
                break;
            }
        }

        if (ultimaSemanaComMedia > 0) {
            sb.append("Média Semana ").append(ultimaSemanaComMedia).append(": ")
                    .append(String.format("%.1f", j.getMediaSemanaAtual(ultimaSemanaComMedia))).append("<br>");
        }
        if (ultimaSemanaComMedia > 1) {
            sb.append("Média Semana ").append(ultimaSemanaComMedia - 1).append(": ")
                    .append(String.format("%.1f", j.getMediaSemanaAtual(ultimaSemanaComMedia - 1))).append("<br>");
        }

        sb.append("</html>");

        JLabel infoLabel = new JLabel(sb.toString());
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(infoLabel);

        painel.add(Box.createVerticalStrut(10));

        // Gráfico de desempenho
        List<Double> historicoMedias = new ArrayList<>();
        for (int i = 1; i <= j.getPartidas().size(); i++) {
            historicoMedias.add(j.getMediaSemanaAtual(i));
        }

        GraficoDesempenhoPanel graficoPanel = new GraficoDesempenhoPanel(historicoMedias);
        painel.add(graficoPanel);

        painel.add(Box.createVerticalStrut(10));

        // Abas por semana
        List<Jogador.Partida> todasPartidas = j.getPartidas();

        Map<Integer, List<Jogador.Partida>> partidasPorSemana = new TreeMap<>();
        for (Jogador.Partida p : todasPartidas) {
            partidasPorSemana.computeIfAbsent(p.semana, k -> new ArrayList<>()).add(p);
        }

        JTabbedPane abasSemanas = new JTabbedPane();

        for (Map.Entry<Integer, List<Jogador.Partida>> entry : partidasPorSemana.entrySet()) {
            int semana = entry.getKey();
            List<Jogador.Partida> partidasSemana = entry.getValue();

            JPanel painelSemana = new JPanel();
            painelSemana.setLayout(new BoxLayout(painelSemana, BoxLayout.Y_AXIS));

            for (Jogador.Partida p : partidasSemana) {
                JPanel linhaPartida = new JPanel(new FlowLayout(FlowLayout.LEFT));

                String champ = p.campeaoUsado.toLowerCase();
                int tamanho = 40;
                ImageIcon iconeChamp = imagemCircular("imagens/champs/" + champ + ".png", tamanho);

                JPanel painelImagem = new JPanel();
                painelImagem.setOpaque(false);
                painelImagem.setLayout(new BorderLayout());
                painelImagem.setPreferredSize(new Dimension(tamanho, tamanho));
                painelImagem.setBorder(new EmptyBorder(0, 0, 0, 0));

                if (iconeChamp != null) {
                    JLabel imagemLabel = new JLabel(iconeChamp);
                    imagemLabel.setBorder(null);
                    painelImagem.add(imagemLabel, BorderLayout.CENTER);
                }

                linhaPartida.add(painelImagem);

                String texto = "vs " + p.adversario + " | Champ: " + p.campeaoUsado + " | Pontos: " + p.pontuacaoTotal;
                linhaPartida.add(new JLabel(texto));

                painelSemana.add(linhaPartida);
            }

            abasSemanas.addTab("Semana " + semana, painelSemana);
        }

        // Seleciona automaticamente a aba da semana mais recente
        abasSemanas.setSelectedIndex(abasSemanas.getTabCount() - 1);

        painel.add(abasSemanas);

        JOptionPane.showMessageDialog(this, painel, "Detalhes do Jogador", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarDetalhes(Jogador j) {
        listaJogadores.setSelectedValue(j, true);
        mostrarDetalhes();
    }




    // Função auxiliar para carregar e redimensionar imagem
    private static ImageIcon carregarImagem(String caminho, int largura, int altura) {
        try {
            ImageIcon img = new ImageIcon(caminho);
            Image image = img.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } catch (Exception e) {
            return null;
        }
    }

    private static ImageIcon imagemCircular(String caminho, int tamanho) {
        try {
            BufferedImage original = ImageIO.read(new File(caminho));
            Image img = original.getScaledInstance(tamanho, tamanho, Image.SCALE_SMOOTH);

            BufferedImage circular = new BufferedImage(tamanho, tamanho, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circular.createGraphics();

            // Suavização e corte circular
            g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, tamanho, tamanho));
            g2.drawImage(img, 0, 0, null);
            g2.dispose();

            return new ImageIcon(circular);
        } catch (Exception e) {
            return null;
        }
    }




    private void adicionarPartida() {
        Jogador jogador = listaJogadores.getSelectedValue();
        if (jogador == null) {
            JOptionPane.showMessageDialog(this, "Selecione um jogador primeiro!");
            return;
        }

        // Solicita a semana ao usuário
        String inputSemana = JOptionPane.showInputDialog(this, "Informe o número da semana:");
        int semanaAtual;

        try {
            semanaAtual = Integer.parseInt(inputSemana);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Número da semana inválido!");
            return;
        }

        new AdicionarPartidaDialog((JFrame) SwingUtilities.getWindowAncestor(this), jogador, semanaAtual);
        ArquivoUtils.salvarJogador(jogador);

    }



    public static List<Jogador> carregarTodosJogadores() {
        List<Jogador> jogadores = new ArrayList<>();
        File pasta = new File("jogadores");

        if (!pasta.exists()) pasta.mkdirs();

        File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".dat"));
        if (arquivos != null) {
            for (File arq : arquivos) {
                try {
                    FileInputStream fileIn = new FileInputStream(arq);
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    Jogador j = (Jogador) in.readObject();
                    jogadores.add(j);
                    in.close();
                    fileIn.close();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return jogadores;
    }

    public void fecharSemana(int novaSemana) {
        int semanaAnterior = novaSemana - 1;

        fazerBackupDaSemana(semanaAnterior);
        AtualizadorDeCustos.atualizarCustos(todosJogadores, novaSemana);

        for (Jogador j : todosJogadores) {
            salvarJogador(j);
        }

        atualizarLista(todosJogadores);

        JOptionPane.showMessageDialog(this, "Semana " + semanaAnterior + " finalizada!\nCustos atualizados para a Semana " + novaSemana + ".\nBackup criado em: backups/semana_" + semanaAnterior);
    }


    private void fazerBackupDaSemana(int semana) {
        File pastaOrigem = new File("jogadores");
        File pastaDestino = new File("backups/semana_" + semana);

        if (!pastaDestino.exists()) {
            pastaDestino.mkdirs();
        }

        File[] arquivos = pastaOrigem.listFiles((dir, name) -> name.endsWith(".dat"));
        if (arquivos != null) {
            for (File arq : arquivos) {
                File destino = new File(pastaDestino, arq.getName());
                try {
                    Files.copy(arq.toPath(), destino.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    public List<Jogador> getTodosJogadores() {
        return todosJogadores;
    }
}
