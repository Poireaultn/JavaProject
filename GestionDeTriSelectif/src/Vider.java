import java.util.Date;

public class Vider {
	// Attributs
	private Integer Quantite;
	private Date HeureDepot;
	private String TypeDechet;
	private Corbeille Corbeille;
	
	// Constructeurs
	public Vider(Integer Quantite, Date HeureDepot, String TypeDechet, Corbeille Corbeille) {
		this.Quantite = Quantite;
		this.HeureDepot = HeureDepot;
		this.TypeDechet = TypeDechet;
		this.Corbeille = Corbeille;
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
	
	public Corbeille getCorbeille() {
		return Corbeille;
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
	
	public void setCorbeille(Corbeille Corbeille) {
		this.Corbeille = Corbeille;
	}
}
