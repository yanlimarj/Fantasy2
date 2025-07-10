package main.java.fantasy;

import java.util.*;

public class AtualizadorDeCustos {
    private static final double FATOR = 0.3;
    private static final int CUSTO_MINIMO = 5;
    private static final int CUSTO_MAXIMO = 20;

    public static void atualizarCustos(List<Jogador> jogadores, int semanaAtual) {
        if (semanaAtual <= 1) return;


        Map<Jogador.Posicao, List<Double>> pontuacoesPorPosicao = new HashMap<>();

        for (Jogador j : jogadores) {
            double media = j.getMediaSemanaPassada(semanaAtual);
            if (media > 0) {
                pontuacoesPorPosicao
                        .computeIfAbsent(j.getPosicao(), k -> new ArrayList<>())
                        .add(media);
            }
        }


        Map<Jogador.Posicao, Double> mediaPorPosicao = new HashMap<>();
        for (Map.Entry<Jogador.Posicao, List<Double>> entry : pontuacoesPorPosicao.entrySet()) {
            List<Double> pontuacoes = entry.getValue();
            double media = pontuacoes.stream().mapToDouble(Double::doubleValue).average().orElse(0);
            mediaPorPosicao.put(entry.getKey(), media);
        }

        // Atualizar custo de cada jogador
        for (Jogador j : jogadores) {
            double pontuacaoJogador = j.getMediaSemanaPassada(semanaAtual);
            Double mediaPosicao = mediaPorPosicao.get(j.getPosicao());

            if (mediaPosicao == null || mediaPosicao == 0) continue;

            double fatorMultiplicador = 1 + ((pontuacaoJogador - mediaPosicao) / mediaPosicao) * FATOR;
            int novoCusto = (int) Math.round(j.getCusto() * fatorMultiplicador);

            novoCusto = Math.max(CUSTO_MINIMO, Math.min(CUSTO_MAXIMO, novoCusto));
            j.setCusto(novoCusto);
        }
    }
}
