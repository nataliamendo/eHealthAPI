package SCProject.eHealth.model;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idt")
	private int idt;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "dniusuario")
	private String dniusuario;
	
	@Column(name = "idHC")
	private int idHC;
	
	@Column(name = "claveMedico")
	private BigInteger claveMedico;
	
	public int getIdt() {
		return idt;
	}
	public void setIdt(int idt) {
		this.idt = idt;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getDniusuario() {
		return dniusuario;
	}
	public void setDniusuario(String dniusuario) {
		this.dniusuario = dniusuario;
	}
	public int getIdHC() {
		return idHC;
	}
	public void setIdHC(int idHC) {
		this.idHC = idHC;
	}
	public BigInteger getClaveMedico() {
		return claveMedico;
	}
	public void setClaveMedico(BigInteger claveMedico) {
		this.claveMedico = claveMedico;
	}
	
	

}
