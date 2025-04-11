public class Dechet {
	// Attributs
	private Integer IDDechet;
	private String TypeDechet;
	private Integer Poids;
	private Corbeille Corbeille;
	
	// Constructeurs
	public Dechet(Integer IDDechet, String TypeDechet, Integer Poids, Corbeille Corbeille) {
		this.IDDechet = IDDechet;
		this.TypeDechet = TypeDechet;
		this.Poids = Poids;
		this.Corbeille = Corbeille;
	}
	
	// Getters
	public Integer getIDDechet() {
		return IDDechet;
	}
	
	public String getTypeDechet() {
		return TypeDechet;
	}
	
	public Integer getPoids() {
		return Poids;
	}
	
	public Corbeille getCorbeille() {
		return Corbeille;
	}
	
	// Setters
	public void setIDDechet(Integer IDDechet) {
		this.IDDechet = IDDechet;
	}
	
	public void setTypeDechet(String TypeDechet) {
		this.TypeDechet = TypeDechet;
	}
	
	public void setPoids(Integer Poids) {
		this.Poids = Poids;
	}
	
	public void setCorbeille(Corbeille Corbeille) {
		this.Corbeille = Corbeille;
	}
}
