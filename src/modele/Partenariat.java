package modele;

import java.time.LocalDate;

public class Partenariat {
    private String contrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int identifiantCommerce;

    public Partenariat(String contrat, LocalDate dateDebut, LocalDate dateFin, int identifiantCommerce) {
        this.contrat = contrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.identifiantCommerce = identifiantCommerce;
    }

    public String getContrat() {
        return contrat;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public int getIdentifiantCommerce() {
        return identifiantCommerce;
    }
}
