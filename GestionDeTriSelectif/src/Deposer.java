import java.util.Date;

public class Deposer {
	// Atributs
	private Integer Quantite;
	private Date HeureDepot;
	private String TypeDechet;
	private Poubelle Poubelle;
	
	// Constructeurs
	public Deposer(Integer Quantite, Date HeureDepot, String TypeDechet, Poubelle Poubelle) {
		this.Quantite = Quantite;
		this.HeureDepot = HeureDepot;
		this.TypeDechet = TypeDechet;
		this.Poubelle = Poubelle;
	}
	
	// Getters
	public Integer getQuantite() {
		return Quantite;
	}
	
	public Date getHeureDepot() {
		return HeureDepot;
	}
	
	public String getTypeDechet() {
		return TypeDechet;
	}
	
	public Poubelle getPoubelle() {
		return Poubelle;
	}
	
	// Setters
	public void setQuantite(Integer Quantite) {
		this.Quantite = Quantite;
	}
	
	public void setHeureDepot(Date HeureDepot) {
		this.HeureDepot = HeureDepot;
	}
	
	public void setTypeDechet(String TypeDechet) {
		this.TypeDechet = TypeDechet;
	}
	
	public void setPoubelle(Poubelle Poubelle) {
		this.Poubelle = Poubelle;
	}
}
