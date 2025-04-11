import java.util.ArrayList;

public class CentreDeTri {
	// Attributs
	private Integer IDCentreTri;
	private String Nom;
	private String Adresse;
	private ArrayList<Poubelle> mesPoubelles;
	private ArrayList<Partenariat> mesPartenariats;
	
	// Constructeurs
	public CentreDeTri(Integer IDCentreTri, String Nom, String Adresse) {
		this.IDCentreTri = IDCentreTri;
		this.Nom = Nom;
		this.Adresse = Adresse;
		this.mesPoubelles = new ArrayList<Poubelle>();
		this.mesPartenariats = new ArrayList<Partenariat>();
	}
	
	// Getters
	public Integer getIDCentreTri() {
		return IDCentreTri;
	}
	
	public String getNom() {
		return Nom;
	}
	
	public String getAdresse() {
		return Adresse;
	}
	
	public ArrayList<Poubelle> getMesPoubelles() {
		return mesPoubelles;
	}
	
	public ArrayList<Partenariat> getMesPartenariats() {
		return mesPartenariats;
	}
	
	// Setters
	public void setIDCentreTri(Integer IDCentreTri) {
		this.IDCentreTri = IDCentreTri;
	}
	
	public void setNom(String Nom) {
		this.Nom = Nom;
	}
	
	public void setAdresse(String Adresse) {
		this.Adresse = Adresse;
	}
	
	public void setMesPoubelles(ArrayList<Poubelle> mesPoubelles) {
		this.mesPoubelles = mesPoubelles;
	}
	
	public void setMesPartenariats(ArrayList<Partenariat> mesPartenariats) {
		this.mesPartenariats = mesPartenariats;
	}
}
