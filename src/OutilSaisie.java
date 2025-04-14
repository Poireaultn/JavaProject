import java.util.Scanner;

public class OutilSaisie {
    private static Scanner scanner = new Scanner(System.in);

    // Lecture d'un texte
    public static String lireTexte(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    // Lecture d'un entier avec validation
    public static int lireEntier(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Veuillez entrer un nombre valide.");
            scanner.next();  // Consomme l'entrée incorrecte
        }
        int valeur = scanner.nextInt();
        scanner.nextLine(); // Consomme le saut de ligne laissé par nextInt()
        return valeur;
    }

    // Fermeture du scanner (à utiliser si nécessaire)
    public static void fermerScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
