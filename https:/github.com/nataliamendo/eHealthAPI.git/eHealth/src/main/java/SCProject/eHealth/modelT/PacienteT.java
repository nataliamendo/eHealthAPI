package SCProject.eHealth.modelT;

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
public class PacienteT {
	private int idp;
	
	private String nombrep;
	
	private String dni;
	
	private String telef;
	
	HistorialCT historialc;
	
	private String pass;
	
	private String salt;
	
	public int getIdp() {
		return idp;
	}
	public void setIdp(int idp) {
		this.idp = idp;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public HistorialCT getHistorialc() {
		return historialc;
	}
	public void setHistorialc(HistorialCT historialc) {
		this.historialc = historialc;
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
