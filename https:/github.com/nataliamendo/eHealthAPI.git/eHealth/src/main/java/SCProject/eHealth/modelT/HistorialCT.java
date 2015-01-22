package SCProject.eHealth.modelT;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class HistorialCT {
	
	private BigInteger idhc;
	
	private BigInteger idpaciente;

	private BigInteger temp;
	
	private BigInteger presiona;
	
	private BigInteger pulso;
	
	private String enfermedad;
	
	private BigInteger primop;

	private BigInteger primoq;	

	List<MedicoT> listMedicos = new ArrayList<>();
	
	public BigInteger getPrimop() {
		return primop;
	}
	public void setPrimop(BigInteger primop) {
		this.primop = primop;
	}
	public BigInteger getPrimoq() {
		return primoq;
	}
	public void setPrimoq(BigInteger primoq) {
		this.primoq = primoq;
	}	
	public List<MedicoT> getListMedicos() {
		return listMedicos;
	}
	public void setListMedicos(List<MedicoT> listMedicos) {
		this.listMedicos = listMedicos;
	}
	public boolean add(MedicoT e) {
		return listMedicos.add(e);
	}
	public boolean remove(Object o) {
		return listMedicos.remove(o);
	}
	
	public String getEnfermedad() {
		return enfermedad;
	}
	public void setEnfermedad(String enfermedad) {
		this.enfermedad = enfermedad;
	}
	public BigInteger getIdhc() {
		return idhc;
	}
	public void setIdhc(BigInteger idhc) {
		this.idhc = idhc;
	}
	public BigInteger getIdpaciente() {
		return idpaciente;
	}
	public void setIdpaciente(BigInteger idpaciente) {
		this.idpaciente = idpaciente;
	}
	public BigInteger getTemp() {
		return temp;
	}
	public void setTemp(BigInteger temp) {
		this.temp = temp;
	}
	public BigInteger getPresiona() {
		return presiona;
	}
	public void setPresiona(BigInteger presiona) {
		this.presiona = presiona;
	}
	public BigInteger getPulso() {
		return pulso;
	}
	public void setPulso(BigInteger pulso) {
		this.pulso = pulso;
	}

}
