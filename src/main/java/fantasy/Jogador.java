package main.java.fantasy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Jogador implements Serializable {
    private static final long serialVersionUID = 1L;
    public enum Posicao {
        TOP, JUNGLE, MID, ADC, SUP
    }

    public static class Partida implements Serializable {
        private static final long serialVersionUID = 1L;

        public String adversario;
        public String campeaoUsado;
        public int semana;

        public int abates;
        public int mortes;
        public int assistencias;
        public boolean vitoria;
        public boolean vitoriaStomp;
        public int gold;
        public int objetivosNeutros;
        public int dragsAnciao;
        public int baroes;
        public boolean atakhan;
        public int tripleKills;
        public int quadraKills;
        public int pentaKills;
        public int placarVisao;
        public int abatesTime;
        public boolean pom;
        public int dano;
        public boolean proeza;
        public int cs;

        public int pontuacaoTotal;

        public Partida(String adversario, String campeaoUsado, int semana, int abates, int mortes, int assistencias, boolean vitoria, boolean vitoriaStomp, int gold, int objetivosNeutros, int dragsAnciao,
                       int baroes, boolean atakhan, int tripleKills, int quadraKills, int pentaKills, int placarVisao, int abatesTime, boolean pom, int dano, boolean proeza, int cs) {

            this.adversario = adversario;
            this.campeaoUsado = campeaoUsado;
            this.semana = semana;
            this.abates = abates;
            this.mortes = mortes;
            this.assistencias = assistencias;
            this.vitoria = vitoria;
            this.vitoriaStomp = vitoriaStomp;
            this.gold = gold;
            this.objetivosNeutros = objetivosNeutros;
            this.dragsAnciao = dragsAnciao;
            this.baroes = baroes;
            this.atakhan = atakhan;
            this.tripleKills = tripleKills;
            this.quadraKills = quadraKills;
            this.pentaKills = pentaKills;
            this.placarVisao = placarVisao;
            this.abatesTime = abatesTime;
            this.pom = pom;
            this.dano = dano;
            this.proeza = proeza;
            this.cs = cs;

            calcularPontuacao();
        }

        private void calcularPontuacao() {
            double pontuacao = 0;
            pontuacao += abates * 1.5;
            pontuacao -= mortes * 2;
            pontuacao += assistencias;
            pontuacao += vitoria ? 3 : -3;
            if (vitoriaStomp) pontuacao += 5;
            pontuacao += gold * 0.0005;
            pontuacao += objetivosNeutros;
            pontuacao += dragsAnciao * 5;
            pontuacao += baroes * 3;
            if (atakhan) pontuacao += 5;
            pontuacao += tripleKills * 4;
            pontuacao += quadraKills * 6;
            pontuacao += pentaKills * 10;
            pontuacao += placarVisao * 0.1;

            int participacao = abates + assistencias;
            double porcentagem = abatesTime > 0 ? ((double) participacao / abatesTime) * 100 : 0;
            if (porcentagem >= 95) pontuacao += 10;
            else if (porcentagem >= 85) pontuacao += 7;
            else if (porcentagem >= 70) pontuacao += 5;

            if (pom) pontuacao += 8;
            pontuacao += dano * 0.0001;
            if (proeza) pontuacao += 1.5;
            pontuacao += cs * 0.01;

            pontuacaoTotal = (int) Math.round(pontuacao);
        }

        public int getSemana() {
            return semana;
        }

        public double getPontuacao() {
            return pontuacaoTotal;
        }

    }


    private List<Double> historicoMedias = new ArrayList<>();

    private String nome;
    private String time;
    private Posicao posicao;
    private int custo;
    private List<Partida> partidas;

    public Jogador(String nome, String time, Posicao posicao, int custo) {
        this.nome = nome;
        this.time = time;
        this.posicao = posicao;
        this.custo = custo;
        this.partidas = new ArrayList<>();
    }

    public void adicionarPartidaCompleta(Partida partida) {
        partidas.add(partida);
    }

    // Getters
    public String getNome() { return nome; }
    public String getTime() { return time; }
    public Posicao getPosicao() { return posicao; }
    public int getCusto() { return custo; }
    public void setCusto(int novoCusto) {
        this.custo = novoCusto;
    }
    public List<Partida> getPartidas() { return partidas; }

    public double getMediaSemanaAtual(int semanaAtual) {
        return partidas.stream()
                .filter(p -> p.semana == semanaAtual)
                .mapToDouble(p -> p.pontuacaoTotal)
                .average()
                .orElse(0);
    }

    public double getMediaSemanaPassada(int semanaAtual) {
        return partidas.stream()
                .filter(p -> p.semana == semanaAtual - 1)
                .mapToDouble(p -> p.pontuacaoTotal)
                .average()
                .orElse(0);
    }

    public static void salvarJogador(Jogador jogador) {
        try {
            FileOutputStream fileOut = new FileOutputStream("jogadores/" + jogador.getNome() + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(jogador);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void atualizarMediaSemana(int semanaAtual) {
        double media = getMediaSemanaAtual(semanaAtual);

        // Garante que a lista tenha tamanho suficiente
        while (historicoMedias.size() < semanaAtual) {
            historicoMedias.add(0.0);
        }

        historicoMedias.set(semanaAtual - 1, media);
    }

    public List<Double> getHistoricoMedias() {
        int maxSemana = 0;
        for (Partida p : partidas) {
            if (p.semana > maxSemana) {
                maxSemana = p.semana;
            }
        }

        List<Double> historico = new ArrayList<>();
        for (int i = 1; i <= maxSemana; i++) {
            historico.add(getMediaSemanaAtual(i));
        }

        return historico;
    }

    public Double getPontuacaoDaSemana(int semana) {
        return partidas.stream()
                .filter(p -> p.getSemana() == semana)
                .mapToDouble(Partida::getPontuacao)
                .average()
                .orElse(0.0);
    }







    @Override
    public String toString() {
        return nome + " - " + posicao + " - " + time + " - $" + custo;
    }
}
