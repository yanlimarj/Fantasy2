package main.java.fantasy;

import java.util.ArrayList;
import java.util.List;

public class InicializadorDeJogadores {

    public static List<Jogador> criarJogadoresIniciais() {
        List<Jogador> jogadores = new ArrayList<>();

        jogadores.add(new Jogador("Duro", "GenG", Jogador.Posicao.SUP, 12));
        jogadores.add(new Jogador("Ruler", "GenG", Jogador.Posicao.ADC, 16));
        jogadores.add(new Jogador("Canyon", "GenG", Jogador.Posicao.JUNGLE, 15));
        jogadores.add(new Jogador("Chovy", "GenG", Jogador.Posicao.MID, 17));
        jogadores.add(new Jogador("Kiin", "GenG", Jogador.Posicao.TOP, 14));

        jogadores.add(new Jogador("Faker", "T1", Jogador.Posicao.MID, 16));
        jogadores.add(new Jogador("Gumayusi", "T1", Jogador.Posicao.ADC, 15));
        jogadores.add(new Jogador("Smash", "T1", Jogador.Posicao.ADC, 15));
        jogadores.add(new Jogador("Keria", "T1", Jogador.Posicao.SUP, 13));
        jogadores.add(new Jogador("Oner", "T1", Jogador.Posicao.JUNGLE, 14));
        jogadores.add(new Jogador("Doran", "T1", Jogador.Posicao.TOP, 14));

        jogadores.add(new Jogador("Lehends", "NS", Jogador.Posicao.SUP, 12));
        jogadores.add(new Jogador("Jiwoo", "NS", Jogador.Posicao.ADC, 16));
        jogadores.add(new Jogador("Gideon", "NS", Jogador.Posicao.JUNGLE, 15));
        jogadores.add(new Jogador("Calix", "NS", Jogador.Posicao.MID, 17));
        jogadores.add(new Jogador("Kingen", "NS", Jogador.Posicao.TOP, 14));

        jogadores.add(new Jogador("Peter", "KT", Jogador.Posicao.SUP, 12));
        jogadores.add(new Jogador("deokdam", "KT", Jogador.Posicao.ADC, 16));
        jogadores.add(new Jogador("Cuzz", "KT", Jogador.Posicao.JUNGLE, 15));
        jogadores.add(new Jogador("Bdd", "KT", Jogador.Posicao.MID, 17));
        jogadores.add(new Jogador("PerfecT", "KT", Jogador.Posicao.TOP, 14));

        jogadores.add(new Jogador("Pleata", "DRX", Jogador.Posicao.SUP, 12));
        jogadores.add(new Jogador("Teddy", "DRX", Jogador.Posicao.ADC, 16));
        jogadores.add(new Jogador("Sponge", "DRX", Jogador.Posicao.JUNGLE, 15));
        jogadores.add(new Jogador("Ucal", "DRX", Jogador.Posicao.MID, 17));
        jogadores.add(new Jogador("Rich", "DRX", Jogador.Posicao.TOP, 14));

        jogadores.add(new Jogador("Kellin", "BFX", Jogador.Posicao.SUP, 12));
        jogadores.add(new Jogador("Diable", "BFX", Jogador.Posicao.ADC, 16));
        jogadores.add(new Jogador("Raptor", "BFX", Jogador.Posicao.JUNGLE, 15));
        jogadores.add(new Jogador("VicLa", "BFX", Jogador.Posicao.MID, 17));
        jogadores.add(new Jogador("Clear", "BFX", Jogador.Posicao.TOP, 14));

        jogadores.add(new Jogador("BeryL", "DK", Jogador.Posicao.SUP, 12));
        jogadores.add(new Jogador("Aiming", "DK", Jogador.Posicao.ADC, 16));
        jogadores.add(new Jogador("Lucid", "DK", Jogador.Posicao.JUNGLE, 15));
        jogadores.add(new Jogador("ShowMaker", "DK", Jogador.Posicao.MID, 17));
        jogadores.add(new Jogador("Siwoo", "DK", Jogador.Posicao.TOP, 14));

        jogadores.add(new Jogador("Life", "DNF", Jogador.Posicao.SUP, 12));
        jogadores.add(new Jogador("Berserker", "DNF", Jogador.Posicao.ADC, 16));
        jogadores.add(new Jogador("Pyosik", "DNF", Jogador.Posicao.JUNGLE, 15));
        jogadores.add(new Jogador("BuLLDoG", "DNF", Jogador.Posicao.MID, 17));
        jogadores.add(new Jogador("DuDu", "DNF", Jogador.Posicao.TOP, 14));

        jogadores.add(new Jogador("DeLight", "HLE", Jogador.Posicao.SUP, 12));
        jogadores.add(new Jogador("Viper", "HLE", Jogador.Posicao.ADC, 16));
        jogadores.add(new Jogador("Peanut", "HLE", Jogador.Posicao.JUNGLE, 15));
        jogadores.add(new Jogador("Zeka", "HLE", Jogador.Posicao.MID, 17));
        jogadores.add(new Jogador("Zeus", "HLE", Jogador.Posicao.TOP, 14));

        jogadores.add(new Jogador("Pollu", "BRO", Jogador.Posicao.SUP, 12));
        jogadores.add(new Jogador("Hype", "BRO", Jogador.Posicao.ADC, 16));
        jogadores.add(new Jogador("HamBak", "BRO", Jogador.Posicao.JUNGLE, 15));
        jogadores.add(new Jogador("Clozer", "BRO", Jogador.Posicao.MID, 17));
        jogadores.add(new Jogador("Morgan", "BRO", Jogador.Posicao.TOP, 14));


        return jogadores;
    }
}
