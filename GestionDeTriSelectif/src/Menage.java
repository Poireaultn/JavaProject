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
	
	// Setters / Getters
	public Integer getIDMenage() {
		return IDMenage;
	}

	public void setIDMenage(Integer iDMenage) {
		IDMenage = iDMenage;
	}

	public String getLogin() {
		return Login;
	}

	public void setLogin(String login) {
		Login = login;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public Integer getPointFidelite() {
		return PointFidelite;
	}

	public void setPointFidelite(Integer pointFidelite) {
		PointFidelite = pointFidelite;
	}

	public ArrayList<BonAchat> getMesBonAchats() {
		return mesBonAchats;
	}

	public void setMesBonAchats(ArrayList<BonAchat> mesBonAchats) {
		this.mesBonAchats = mesBonAchats;
	}

	public ArrayList<Deposer> getMesDepots() {
		return mesDepots;
	}

	public void setMesDepots(ArrayList<Deposer> mesDepots) {
		this.mesDepots = mesDepots;
	}

	public ArrayList<Corbeille> getMesCorbeilles() {
		return mesCorbeilles;
	}

	public void setMesCorbeilles(ArrayList<Corbeille> mesCorbeilles) {
		this.mesCorbeilles = mesCorbeilles;
	}
	
	
	
	
	
}
