import java.util.ArrayList;

public class Corbeille {
	// Atributs
	private Integer IDCorbeille;
	private ArrayList<Dechet> mesDechets;
	
	// Constructeurs
	public Corbeille(Integer IDCorbeille) {
		this.IDCorbeille = IDCorbeille;
		this.mesDechets = new ArrayList<Dechet>();
	}
	
	// Getters
	public Integer getIDCorbeille() {
		return IDCorbeille;
	}
	
	public ArrayList<Dechet> getMesDechets() {
		return mesDechets;
	}
	
	// Setters
	public void setIDCorbeille(Integer IDCorbeille) {
		this.IDCorbeille = IDCorbeille;
	}
	
	public void setMesDechets(ArrayList<Dechet> mesDechets) {
		this.mesDechets = mesDechets;
	}
}
