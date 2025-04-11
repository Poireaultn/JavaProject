import java.util.ArrayList;

public class Poubelle {
	// Atributs
	private Integer IDPoubelle;
	private String Ville;
	private String Quartier;
	private String TypePoubelle;
	private Integer Capacite;
	private CentreDeTri CentreDeTri;
	private ArrayList<Dechet> mesDechets;
	private ArrayList<Vider> mesDepots;
	
	// Constructeurs	
	public Poubelle(Integer IDPoubelle, String Ville, String Quartier, String TypePoubelle, Integer Capacite, CentreDeTri CentreDeTri) {
		this.IDPoubelle = IDPoubelle;
		this.Ville = Ville;
		this.Quartier = Quartier;
		this.TypePoubelle = TypePoubelle;
		this.Capacite = Capacite;
		this.CentreDeTri = CentreDeTri;
		this.mesDechets = new ArrayList<Dechet>();
		this.mesDepots = new ArrayList<Vider>();
	}
	
	// Getters
	public Integer getIDPoubelle() {
		return IDPoubelle;
	}
	
	public String getVille() {
		return Ville;
	}
	
	public String getQuartier() {
		return Quartier;
	}
	
	public String getTypePoubelle() {
		return TypePoubelle;
	}
	
	public Integer getCapacite() {
		return Capacite;
	}
	
	public CentreDeTri getCentreDeTri() {
		return CentreDeTri;
	}
	
	public ArrayList<Dechet> getMesDechets() {
		return mesDechets;
	}
	
	public ArrayList<Vider> getMesDepots() {
		return mesDepots;
	}
	
	// Setters
	public void setIDPoubelle(Integer IDPoubelle) {
		this.IDPoubelle = IDPoubelle;
	}
	
	public void setVille(String Ville) {
		this.Ville = Ville;
	}
	
	public void setQuartier(String Quartier) {
		this.Quartier = Quartier;
	}
	
	public void setTypePoubelle(String TypePoubelle) {
		this.TypePoubelle = TypePoubelle;
	}
	
	public void setCapacite(Integer Capacite) {
		this.Capacite = Capacite;
	}
	
	public void setCentreDeTri(CentreDeTri CentreDeTri) {
		this.CentreDeTri = CentreDeTri;
	}
	
	public void setMesDechets(ArrayList<Dechet> mesDechets) {
		this.mesDechets = mesDechets;
	}
	
	public void setMesDepots(ArrayList<Vider> mesDepots) {
		this.mesDepots = mesDepots;
	}
}
