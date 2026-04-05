package utils;

import model.Labyrinthe;
import java.io.*;
import java.util.*;

public class LectureFichier {
    public static Labyrinthe charger(String cheminFichier) throws IOException {
        List<String> lignes = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(cheminFichier));
        String ligne;
        while ((ligne = br.readLine()) != null) {
            if (!ligne.trim().isEmpty()) {
                lignes.add(ligne);
            }
        }
        br.close();
        
        if (lignes.isEmpty()) {
            throw new IOException("Fichier vide");
        }
        
        int lignesCount = lignes.size();
        int colonnesCount = lignes.get(0).length();
        
        char[][] grille = new char[lignesCount][colonnesCount];
        for (int i = 0; i < lignesCount; i++) {
            String l = lignes.get(i);
            if (l.length() != colonnesCount) {
                throw new IOException("Lignes de longueurs différentes");
            }
            for (int j = 0; j < colonnesCount; j++) {
                grille[i][j] = l.charAt(j);
            }
        }
        
        return new Labyrinthe(grille);
    }
}