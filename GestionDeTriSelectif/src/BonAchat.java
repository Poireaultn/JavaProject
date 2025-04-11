import java.util.Date;

public class BonAchat {
	// Attributs
	private Integer IDBonAchat;
	private Integer Prix;
	private String CategorieProduit;
	
	// Constructeur
	private Date DateExpiration;
	public BonAchat(Integer iDBonAchat, Integer prix, String categorieProduit, Date dateExpiration) {
		this.IDBonAchat = iDBonAchat;
		this.Prix = prix;
		this.CategorieProduit = categorieProduit;
		this.DateExpiration = dateExpiration;
	}
	
	// Getters
	public Integer getIDBonAchat() {
		return IDBonAchat;
	}

	public Integer getPrix() {
		return Prix;
	}
	
	public String getCategorieProduit() {
		return CategorieProduit;
	}

	public Date getDateExpiration() {
		return DateExpiration;
	}

	// Setters 
	public void setIDBonAchat(Integer iDBonAchat) {
		this.IDBonAchat = iDBonAchat;
	}
	
	public void setPrix(Integer prix) {
		this.Prix = prix;
	}
	
	public void setCategorieProduit(String categorieProduit) {
		this.CategorieProduit = categorieProduit;
	}
	
	public void setDateExpiration(Date dateExpiration) {
		this.DateExpiration = dateExpiration;
	}	
	
}
