public class Menage {
    private int identifiantMenage;
    private String identifiantConnexion;
    private String motDePasse;
    private double pointsFidelite;
    private String role; // Ajout du rôle (utilisateur ou employé)

    // Getters et setters

    public int getIdentifiant() {
        return identifiantMenage;
    }

    public void setIdentifiantMenage(int identifiantMenage) {
        this.identifiantMenage = identifiantMenage;
    }

    public String getIdentifiantConnexion() {
        return identifiantConnexion;
    }

    public void setIdentifiantConnexion(String identifiantConnexion) {
        this.identifiantConnexion = identifiantConnexion;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public double getPointsFidelite() {
        return pointsFidelite;
    }

    public void setPointsFidelite(double pointsFidelite) {
        this.pointsFidelite = pointsFidelite;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void ajouterPoints(double points) {
        this.pointsFidelite += points;
    }

    public void retirerPoints(double points) {
        this.pointsFidelite -= points;
    }
}


