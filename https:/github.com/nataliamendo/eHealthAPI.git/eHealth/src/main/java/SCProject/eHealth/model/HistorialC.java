package SCProject.eHealth.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "HistorialC")
public class HistorialC {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idhc")
	private int idhc;
	
	@JoinColumn( insertable=true, updatable=true)
	private int idpaciente;

	@Column(name = "temp")
	private int temp;
	
	@Column(name = "presiona")
	private int presiona;
	
	@Column(name = "pulso")
	private int pulso;
	
	@Column(name = "enfermedad")
	private String enfermedad;
	
	
	@ManyToMany(mappedBy="listHC")
	@Column(name = "medico")
	List<Medico> listMedicos = new ArrayList<>();
	
	
	
	
	// 1 Médico = varios Historiales Clínicos
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idmedico")
	Medico medico;*/
	
	public List<Medico> getListMedicos() {
		return listMedicos;
	}
	public void setListMedicos(List<Medico> listMedicos) {
		this.listMedicos = listMedicos;
	}
	public boolean add(Medico e) {
		return listMedicos.add(e);
	}
	public boolean remove(Object o) {
		return listMedicos.remove(o);
	}
	public int getIdpaciente() {
		return idpaciente;
	}
	public void setIdpaciente(int idpaciente) {
		this.idpaciente = idpaciente;
	}
	public int getIdhc() {
		return idhc;
	}
	public void setIdhc(int idhc) {
		this.idhc = idhc;
	}
	public int getTemp() {
		return temp;
	}
	public void setTemp(int temp) {
		this.temp = temp;
	}
	public int getPresiona() {
		return presiona;
	}
	public void setPresiona(int presiona) {
		this.presiona = presiona;
	}
	public int getPulso() {
		return pulso;
	}
	public void setPulso(int pulso) {
		this.pulso = pulso;
	}
	public String getEnfermedad() {
		return enfermedad;
	}
	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}
	

}
