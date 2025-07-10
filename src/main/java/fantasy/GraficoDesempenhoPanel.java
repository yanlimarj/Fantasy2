package main.java.fantasy;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GraficoDesempenhoPanel extends JPanel {

    private List<Double> historicoMedias;  // Lista de médias semanais

    public GraficoDesempenhoPanel(List<Double> historicoMedias) {
        this.historicoMedias = historicoMedias;
        setPreferredSize(new Dimension(400, 300)); // Tamanho do painel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Garante que o painel seja limpo antes de desenhar

        // Configuração do gráfico
        g.setColor(Color.BLUE);
        int largura = getWidth();
        int altura = getHeight();

        // Desenhar o eixo X
        g.setColor(Color.BLACK);
        g.drawLine(50, altura - 50, largura - 50, altura - 50); // Linha horizontal
        g.drawLine(largura - 50, altura - 50, largura - 60, altura - 60); // seta direita
        g.drawLine(largura - 50, altura - 50, largura - 60, altura - 40); // seta esquerda

        // Desenhar o eixo Y
        g.drawLine(50, altura - 50, 50, 50); // Linha vertical
        g.drawLine(50, 50, 40, 60); // seta acima
        g.drawLine(50, 50, 60, 60); // seta abaixo

        // Adicionar rótulos de semanas no eixo X
        for (int i = 0; i < historicoMedias.size(); i++) {
            g.drawString("Semana " + (i + 1), 50 + (i * (largura - 100) / historicoMedias.size()), altura - 40);
        }

        // Desenhando as linhas do gráfico
        if (!historicoMedias.isEmpty()) {
            for (int i = 1; i < historicoMedias.size(); i++) {
                int x1 = 50 + ((i - 1) * (largura - 100) / historicoMedias.size());
                int y1 = altura - 50 - (int) (historicoMedias.get(i - 1) * (altura - 100) / 100);  // Escala de pontuação
                int x2 = 50 + (i * (largura - 100) / historicoMedias.size());
                int y2 = altura - 50 - (int) (historicoMedias.get(i) * (altura - 100) / 100);  // Escala de pontuação

                g.setColor(Color.GREEN);
                g.drawLine(x1, y1, x2, y2);  // Linha ligando as médias
                g.fillOval(x1 - 2, y1 - 2, 4, 4);  // Ponto na média
            }
        }
    }
}
