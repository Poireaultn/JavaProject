package modele;
import java.util.Scanner;

public class OutilSaisie {
    private static Scanner scanner = new Scanner(System.in);

    public static String lireTexte(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public static int lireEntier(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.println("Veuillez entrer un nombre valide.");
            scanner.next();  
        }
        int valeur = scanner.nextInt();
        scanner.nextLine(); 
        return valeur;
    }

    public static void fermerScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
