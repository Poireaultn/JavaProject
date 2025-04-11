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
	
	// Setters / Getters
	public Integer getIDBonAchat() {
		return IDBonAchat;
	}
	public void setIDBonAchat(Integer iDBonAchat) {
		IDBonAchat = iDBonAchat;
	}
	public Integer getPrix() {
		return Prix;
	}
	public void setPrix(Integer prix) {
		Prix = prix;
	}
	public String getCategorieProduit() {
		return CategorieProduit;
	}
	public void setCategorieProduit(String categorieProduit) {
		CategorieProduit = categorieProduit;
	}
	public Date getDateExpiration() {
		return DateExpiration;
	}
	public void setDateExpiration(Date dateExpiration) {
		DateExpiration = dateExpiration;
	}	
	
}
