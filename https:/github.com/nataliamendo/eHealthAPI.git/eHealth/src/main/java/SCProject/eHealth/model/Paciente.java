package SCProject.eHealth.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Paciente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idp")
	private int idp;
	
	@Column(name = "nombrep")
	private String nombrep;
	
	@Column(name = "dni")
	private String dni;
	
	@Column(name = "telef")
	private String telef;
	
	@Column(name = "pass")
	private String pass;
	
	@Column(name = "salt")
	private String salt;
	
	@Column(name="date")
	private Date date;
	

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	@OneToOne(fetch=FetchType.LAZY , cascade=CascadeType.ALL)
	@JoinColumn(name="HistorialC_idhc")
	HistorialC historialc;
	
	// * ** * * 
		 //Relación con la TABLA DE USERPASS
	/*@OneToOne(fetch=FetchType.LAZY , cascade=CascadeType.ALL)
	@JoinColumn(name="UserKey_idUser")
	UserKey up;*/
	// * * * ** * *
	
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	/*public UserKey getUp() {
		return up;
	}
	public void setUp(UserKey up) {
		this.up = up;
	}*/
	public HistorialC getHistorialc() {
		return historialc;
	}
	public void setHistorialc(HistorialC historialc) {
		this.historialc = historialc;
	}
//	//coordenadas donde se encuentra el paciente
//	//Geocoding ObjGeocod=new Geocoding();
//	private int xp;
//	private int yp;
	
	
	public int getIdp() {
		return idp;
	}
	public void setIdp(int idp) {
		this.idp = idp;
	}
	public String getNombrep() {
		return nombrep;
	}
	public void setNombrep(String nombrep) {
		this.nombrep = nombrep;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getTelef() {
		return telef;
	}
	public void setTelef(String telef) {
		this.telef = telef;
	}
	
	
}
