package SCProject.eHealth;

import java.io.FileNotFoundException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.List;

import SCProject.eHealth.model.HistorialC;
import SCProject.eHealth.model.Medico;
import SCProject.eHealth.model.Paciente;
import SCProject.eHealth.model.SecurityDB;
import SCProject.eHealth.model.Ticket;

public interface InterfaceEBD {

	String addPaciente(Paciente p);

	String addHC(HistorialC hc, Paciente p);

	String addMedico(Medico m, Paciente p, HistorialC hc);

	public HistorialC getHCByDNI(String idpaciente);

	List<Medico> getMedicosByHCid(int idhc);

	List<Paciente> getPacientesList();

	//void addPasswords(UserKey p);
	
	public Paciente getPacienteByNombre(String nombreP);
	
	public Medico getMedicoByNombre(String nombreM);

	public void addSecurity(SecurityDB s);
	
	public List<HistorialC> getHCofMedico(String dnim);
	public Paciente getPacienteById(int idpaciente);
	public SecurityDB getSecurityDBByDNIuser(String userDNI);
	public Medico getMedicoById(int idm);
	public String updateMedico(Medico m, HistorialC hc,Paciente p);
	public HistorialC getHCByIdhc(int idhc);
	public boolean addTicket(Ticket t);
	public boolean actualizarBD(HistorialC hca, int idHC);
	public Certificate getCertificateAPI() throws FileNotFoundException, CertificateException;
	//public String getCertificateAPI() throws FileNotFoundException, CertificateException;
	public boolean actualizarPassP(Paciente p);
	public boolean actualizarPassMedico(Medico m);
	
}
