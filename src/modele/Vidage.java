package modele;

import java.time.LocalDateTime;

public class Vidage {
    private int identifiantVidage;
    private int quantite;
    private LocalDateTime momentDepot;
    private String typeDechet;
    private int identifiantCorbeille;
    private int identifiantPoubelle;

    public Vidage(int identifiantVidage, int quantite, LocalDateTime momentDepot,
                  String typeDechet, int identifiantCorbeille, int identifiantPoubelle) {
        this.identifiantVidage = identifiantVidage;
        this.quantite = quantite;
        this.momentDepot = momentDepot;
        this.typeDechet = typeDechet;
        this.identifiantCorbeille = identifiantCorbeille;
        this.identifiantPoubelle = identifiantPoubelle;
    }

    public int getIdentifiantVidage() {
        return identifiantVidage;
    }

    public int getQuantite() {
        return quantite;
    }

    public LocalDateTime getMomentDepot() {
        return momentDepot;
    }

    public String getTypeDechet() {
        return typeDechet;
    }

    public int getIdentifiantCorbeille() {
        return identifiantCorbeille;
    }

    public int getIdentifiantPoubelle() {
        return identifiantPoubelle;
    }
}
