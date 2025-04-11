import java.util.Date;

public class Partenariat {
	// Attributs
	private String Contrat;
	private Date DateDebut;
	private Date DateFin;
	private Commerce Commerce;
	
	// Constructeurs	
	public Partenariat(String Contrat, Date DateDebut, Date DateFin, Commerce Commerce) {
		this.Contrat = Contrat;
		this.DateDebut = DateDebut;
		this.DateFin = DateFin;
		this.Commerce = Commerce;
	}
	
	// Getters
	public String getContrat() {
		return Contrat;
	}
	
	public Date getDateDebut() {
		return DateDebut;
	}
	
	public Date getDateFin() {
		return DateFin;
	}
	
	public Commerce getCommerce() {
		return Commerce;
	}
	
	// Setters
	public void setContrat(String Contrat) {
		this.Contrat = Contrat;
	}
	
	public void setDateDebut(Date DateDebut) {
		this.DateDebut = DateDebut;
	}
	
	public void setDateFin(Date DateFin) {
		this.DateFin = DateFin;
	}
	
	public void setCommerce(Commerce Commerce) {
		this.Commerce = Commerce;
	}
}
