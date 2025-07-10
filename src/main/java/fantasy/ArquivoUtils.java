package main.java.fantasy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ArquivoUtils {

    public static void salvarJogador(Jogador jogador) {
        try {
            File pasta = new File("jogadores");
            if (!pasta.exists()) pasta.mkdirs();

            FileOutputStream fileOut = new FileOutputStream("jogadores/" + jogador.getNome() + ".dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(jogador);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
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
}
