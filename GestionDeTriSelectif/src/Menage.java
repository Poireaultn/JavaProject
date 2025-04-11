import java.util.ArrayList;

public class Menage {
	// Atributs
	private Integer IDMenage;
	private String Login;
	private String Password;
	private Integer PointFidelite;
	private ArrayList<BonAchat> mesBonAchats;
	private ArrayList<Deposer> mesDepots;
	private ArrayList<Corbeille> mesCorbeilles;
	
	// Constructeurs
	public Menage(Integer iDMenage, String login, String password, Integer pointFidelite) {
		this.IDMenage = iDMenage;
		this.Login = login;
		this.Password = password;
		this.PointFidelite = pointFidelite;
		mesBonAchats = new ArrayList<BonAchat>();
		mesDepots = new ArrayList<Deposer>();
		mesCorbeilles = new ArrayList<Corbeille>();
	}
	
	// Getters
	public Integer getIDMenage() {
		return IDMenage;
	}

	public String getLogin() {
		return Login;
	}

	public String getPassword() {
		return Password;
	}

	public Integer getPointFidelite() {
		return PointFidelite;
	}

	public ArrayList<BonAchat> getMesBonAchats() {
		return mesBonAchats;
	}

	public ArrayList<Deposer> getMesDepots() {
		return mesDepots;
	}

	public ArrayList<Corbeille> getMesCorbeilles() {
		return mesCorbeilles;
	}

	// Setters
	public void setIDMenage(Integer iDMenage) {
		this.IDMenage = iDMenage;
	}

	public void setLogin(String login) {
		this.Login = login;
	}	

	public void setPassword(String password) {
		this.Password = password;
	}	

	public void setPointFidelite(Integer pointFidelite) {
		this.PointFidelite = pointFidelite;
	}	

	public void setMesBonAchats(ArrayList<BonAchat> mesBonAchats) {
		this.mesBonAchats = mesBonAchats;
	}	

	public void setMesDepots(ArrayList<Deposer> mesDepots) {
		this.mesDepots = mesDepots;
	}	

	public void setMesCorbeilles(ArrayList<Corbeille> mesCorbeilles) {
		this.mesCorbeilles = mesCorbeilles;
	}	
}
