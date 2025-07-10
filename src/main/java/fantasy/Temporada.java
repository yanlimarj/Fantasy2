package main.java.fantasy;

import java.sql.Time;
import java.util.List;
import java.util.Map;

public class Temporada {
    private String nome;
    private List<Jogador> jogadores;
    private Map<String, Time> times; // nome do usuário → time salvo
}
