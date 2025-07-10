package main.java.fantasy;

import java.util.ArrayList;
import java.util.List;

public class ControladorFantasy {

    private static int semanaAtual = 1;
    private List<Jogador> listaDeJogadores = new ArrayList<>();

    public ControladorFantasy(List<Jogador> jogadoresIniciais) {
        this.listaDeJogadores = jogadoresIniciais;
    }

    public static int getSemanaAtual() {
        return semanaAtual;
    }

    public void avancarSemana() {
        // Atualiza histórico de todos os jogadores com a média da semana que terminou
        for (Jogador j : listaDeJogadores) {
            j.atualizarMediaSemana(semanaAtual);
        }

        semanaAtual++; // Avança para a próxima semana
    }

    public List<Jogador> getJogadores() {
        return listaDeJogadores;
    }

    public void adicionarJogador(Jogador jogador) {
        listaDeJogadores.add(jogador);
    }

}
