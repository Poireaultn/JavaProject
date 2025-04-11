import java.util.ArrayList;

public class Commerce {
	// Attributs
	private Integer IDCommerce;
	private String Nom;
	private ArrayList<BonAchat> mesBonAchats;
	
	// Constructeurs
	public Commerce(Integer IDCommerce, String Nom) {
		this.IDCommerce = IDCommerce;
		this.Nom = Nom;
		this.mesBonAchats = new ArrayList<BonAchat>();
	}
	
	// Getters
	public Integer getIDCommerce() {
		return IDCommerce;
	}
	
	public String getNom() {
		return Nom;
	}
	
	public ArrayList<BonAchat> getMesBonAchats() {
		return mesBonAchats;
	}
	
	// Setters
	public void setIDCommerce(Integer IDCommerce) {
		this.IDCommerce = IDCommerce;
	}
	
	public void setNom(String Nom) {
		this.Nom = Nom;
	}
	
	public void setMesBonAchats(ArrayList<BonAchat> mesBonAchats) {
		this.mesBonAchats = mesBonAchats;
	}
}
