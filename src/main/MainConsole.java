package main;

import model.*;
import utils.*;
import java.io.*;
import java.util.*;

/**
 Point d'entrée de la version console.
 Propose le chargement d'un fichier ou la génération aléatoire,
 puis compare DFS et BFS.
 */

public class MainConsole {
    public static void main(String[] args) {
        System.out.println("=== RÉSOLUTION DE LABYRINTHE ===\n");
        
        Labyrinthe laby = null;
        
        // Menu
        System.out.println("1. Charger labyrinthe depuis fichier");
        System.out.println("2. Générer labyrinthe aléatoire");
        System.out.print("Choix : ");
        
        Scanner scanner = new Scanner(System.in);
        int choix = scanner.nextInt();
        
        try {
            if (choix == 1) {
                System.out.print("Nom du fichier (dans dossier labyrinthes/) : ");
                String nomFichier = scanner.next();
                laby = LectureFichier.charger("labyrinthes/" + nomFichier);
            } else {
                System.out.print("Nombre de lignes : ");
                int lignes = scanner.nextInt();
                System.out.print("Nombre de colonnes : ");
                int colonnes = scanner.nextInt();
                laby = GenerationAleatoire.generer(lignes, colonnes);
            }
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
            return;
        }
        
        System.out.println("\n=== LABYRINTHE ORIGINAL ===");
        laby.afficherConsole();
        
        // Résolution DFS
        System.out.println("\n=== RÉSOLUTION DFS ===");
        Labyrinthe labyDFS = laby.copie();
        SolveurDFS dfs = new SolveurDFS(labyDFS);
        
        if (dfs.aTrouve()) {
            labyDFS.marquerChemin(dfs.getChemin());
            System.out.println("Chemin trouvé !");
            System.out.println("Longueur du chemin : " + dfs.getChemin().size());
            labyDFS.afficherConsole();
        } else {
            System.out.println("Aucun chemin trouvé !");
        }
        System.out.println("Étapes explorées : " + dfs.getEtapes());
        System.out.printf("Temps d'exécution : %.3f ms\n", dfs.getTempsExecution() / 1_000_000.0);
        
        // Résolution BFS
        System.out.println("\n=== RÉSOLUTION BFS ===");
        Labyrinthe labyBFS = laby.copie();
        SolveurBFS bfs = new SolveurBFS(labyBFS);
        
        if (bfs.aTrouve()) {
            labyBFS.marquerChemin(bfs.getChemin());
            System.out.println("Chemin trouvé !");
            System.out.println("Longueur du chemin : " + bfs.getChemin().size());
            labyBFS.afficherConsole();
        } else {
            System.out.println("Aucun chemin trouvé !");
        }
        System.out.println("Étapes explorées : " + bfs.getEtapes());
        System.out.printf("Temps d'exécution : %.3f ms\n", bfs.getTempsExecution() / 1_000_000.0);
        
        // Comparaison
        System.out.println("\n=== COMPARAISON ===");
        System.out.printf("DFS - Étapes: %d, Temps: %.3f ms\n", dfs.getEtapes(), dfs.getTempsExecution() / 1_000_000.0);
        System.out.printf("BFS - Étapes: %d, Temps: %.3f ms\n", bfs.getEtapes(), bfs.getTempsExecution() / 1_000_000.0);
        
        if (dfs.aTrouve() && bfs.aTrouve()) {
            System.out.println("BFS donne le chemin le plus court : " + bfs.getChemin().size() + " cases");
            System.out.println("DFS peut être plus rapide mais pas optimal.");
        }
        
        scanner.close();
    }
}