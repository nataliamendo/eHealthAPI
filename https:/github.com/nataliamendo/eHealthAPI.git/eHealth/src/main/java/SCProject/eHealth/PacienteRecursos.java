package SCProject.eHealth;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import SCProject.eHealth.model.HistorialC;
import SCProject.eHealth.model.Medico;
import SCProject.eHealth.model.Mensaje;
import SCProject.eHealth.model.Paciente;
import SCProject.eHealth.model.SecurityDB;
import SCProject.eHealth.model.Ticket;
import SCProject.eHealth.modelT.HistorialCT;
import SCProject.eHealth.modelT.MedicoT;
import SCProject.eHealth.modelT.PacienteT;
import SCProject.eHealth.security.RSA;

@Path("/paciente")
public class PacienteRecursos {

	InterfaceEBD ie = OperacionesEBD.getInstance();

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String myresource() {
		return " Hola, llega hasta PacienteResources";
	}

	@GET
	@Path("/autenticaAPI")
	@Produces(Mediatype.EHEALTH_CERTIFICADO)
	public Certificate certificado() throws IOException,
			NoSuchAlgorithmException, InvalidKeySpecException,
			CertificateException {
		
		Certificate certs = null;
		certs = ie.getCertificateAPI();
		

		System.out.println("****"+certs.getPublicKey());
		String clave = certs.getPublicKey().toString();
		return certs;

	}
	
	@PUT
	@Path("/actualizarPassP/")
	@Produces(Mediatype.EHEALTH_PACIENTE)
	public void ActualizarPassP(Paciente p) {
		System.out.println("ActualizarPassP");
		boolean actualizado = false;
		boolean res;
		Paciente p1 = ie.getPacienteByNombre(p.getNombrep());
		p1.setPass(p.getPass());
		p1.setSalt(p.getSalt());
		Date d = new Date();
		p1.setDate(d);
		
		res = ie.actualizarPassP(p1);
		if (res == true) {
			actualizado = true;
		} else {
			actualizado = false;
		}
		
	}
	
	@PUT
	@Path("/actualizarPassM/")
	@Produces(Mediatype.EHEALTH_MEDICO)
	public void ActualizarPassM(Medico m) {
		boolean actualizado = false;
		boolean res;
		Medico m1 = ie.getMedicoByNombre(m.getNombreM());
		m1.setListHC(ie.getHCofMedico(m1.getDnim()));
		m1.setPass(m.getPass());
		m1.setSalt(m.getSalt());
		Date d = new Date();
		m1.setDate(d);
		res = ie.actualizarPassMedico(m1);
		if (res == true) {
			actualizado = true;
		} else {
			actualizado = false;
		}
		
	}

	// Obtener historial clínico de un paciente
	// los valores como el idpaciente, idhc, temperatura, presion, pulso quedan
	// encriptados

	// Obtener historial clínico de un paciente
	// los valores como el idpaciente, idhc, temperatura, presion, pulso quedan
	// encriptados

	@GET
	@Path("/{idpaciente}/encrypt")
	@Produces(Mediatype.EHEALTH_HCP)
	public HistorialCT dameHistorialCEncrypted(
			@PathParam("idpaciente") int idpaciente) {
		HistorialC hc1 = new HistorialC();
		HistorialCT hcp = new HistorialCT();

		Paciente p = ie.getPacienteById(idpaciente);

		// Otenemos el historial guardado en la base de datos:
		hc1 = ie.getHCByDNI(p.getDni());
		List<Medico> lm = ie.getMedicosByHCid(hc1.getIdhc());
		hc1.setListMedicos(lm);

		// Lo pasamos por RSA para encriptarlo
		// se guarda sobre objeto HistorialCP (soporta BigInteger)
		RSA rsa = new RSA(32);
		SecurityDB s = new SecurityDB();
		s = ie.getSecurityDBByDNIuser(p.getDni());
		rsa.setD(s.getD());
		rsa.setE(s.getE());
		rsa.setN(s.getN());
		hcp.setPresiona(rsa.encrypt((BigInteger.valueOf(hc1.getPresiona()))));
		hcp.setTemp(rsa.encrypt((BigInteger.valueOf(hc1.getTemp()))));
		hcp.setPulso(rsa.encrypt((BigInteger.valueOf(hc1.getPulso()))));
		hcp.setIdpaciente(BigInteger.valueOf(idpaciente));
		hcp.setEnfermedad(hc1.getEnfermedad());
		hcp.setIdhc(rsa.encrypt(BigInteger.valueOf(hc1.getIdhc())));
		hcp.setListMedicos(null);

		System.out.println(hcp.getEnfermedad());
		System.out.println(hcp.getPulso());

		return hcp;
	}

	@GET
	@Path("idhc/{idhc}/encrypt")
	@Produces(Mediatype.EHEALTH_HCP)
	public HistorialCT dameHistorialCEncryptedByidHC(@PathParam("idhc") int idhc) {
		HistorialC hc1 = new HistorialC();
		HistorialCT hcp = new HistorialCT();

		// Otenemos el historial guardado en la base de datos:
		hc1 = ie.getHCByIdhc(idhc);
		// List<Medico> lm = ie.getMedicosByHCid(hc1.getIdhc());
		// hc1.setListMedicos(lm);

		// obtenemos el paciente asociado al idhc
		Paciente p = ie.getPacienteById(hc1.getIdpaciente());

		// Lo pasamos por RSA para encriptarlo
		// se guarda sobre objeto HistorialCP (soporta BigInteger)
		RSA rsa = new RSA(32);
		SecurityDB s = new SecurityDB();
		s = ie.getSecurityDBByDNIuser(p.getDni());
		rsa.setD(s.getD());
		rsa.setE(s.getE());
		rsa.setN(s.getN());
		hcp.setPresiona(rsa.encrypt((BigInteger.valueOf(hc1.getPresiona()))));
		hcp.setTemp(rsa.encrypt((BigInteger.valueOf(hc1.getTemp()))));
		hcp.setPulso(rsa.encrypt((BigInteger.valueOf(hc1.getPulso()))));
		hcp.setEnfermedad(hc1.getEnfermedad());
		hcp.setIdpaciente(BigInteger.valueOf(hc1.getIdpaciente()));
		hcp.setListMedicos(null);

		return hcp;
	}

	@GET
	@Path("/{idpaciente}")
	@Produces(Mediatype.EHEALTH_HC)
	public HistorialC dameHistorialC(@PathParam("idpaciente") String idpaciente) {

		HistorialC hc1 = new HistorialC();

		// Otenemos el historial guardado en la base de datos:
		hc1 = ie.getHCByDNI(idpaciente);

		// añadimos la lista de médicos:
		// List<Medico> lm = ie.getMedicosByHCid(hc1.getIdhc());
		// hc1.setListMedicos(lm);

		return hc1;
	}

	@GET
	@Path("/p/{idp}")
	public HistorialC getHistorialCP(@PathParam("idp") String idp) {
		HistorialC hc = ie.getHCByDNI(idp);
		return hc;
	}

	@GET
	@Path("/listpacientes")
	public List<Paciente> getPacienteP() {
		List<Paciente> listp = ie.getPacientesList();
		for (int i = 0; i < listp.size(); i++) {
			// listp.get(i).setHistorialc(null);
			listp.get(i).setHistorialc(ie.getHCByDNI(listp.get(i).getDni()));
		}
		return listp;
	}

	// Métodos Añadidos
	@GET
	@Path("/securityP/{dni}")
	public SecurityDB getSecurityClass(@PathParam("dni") String dni) {

		SecurityDB s = new SecurityDB();

		// Obtenemos el paciente a partir de su id:
		// Paciente p = ie.getPacienteById(idpaciente);

		s = ie.getSecurityDBByDNIuser(dni);

		return s;

	}

	// Métodos Añadidos
	@GET
	@Path("/securityPid/{idp}")
	public SecurityDB getSecurityClass(@PathParam("idp") int idp) {

		SecurityDB s = new SecurityDB();

		// Obtenemos el paciente a partir de su id:
		Paciente p = ie.getPacienteById(idp);
		System.out.println(idp);

		s = ie.getSecurityDBByDNIuser(p.getDni());

		return s;

	}

	@GET
	@Path("/autenticar/{dnim}/{idhc}")
	@Produces(Mediatype.EHEALTH_HC)
	public HistorialCT autenticarM(@PathParam("dnim") String dnim,
			@PathParam("idhc") int idhc) {

		SecurityDB s = new SecurityDB();

		s = ie.getSecurityDBByDNIuser(dnim);
		Ticket t = new Ticket();
		t.setDniusuario(dnim);
		t.setClaveMedico(s.getD());
		t.setIdHC(idhc);
		Date fecha = new Date();
		System.out.println(fecha.getDay());
		System.out.println(fecha.getMonth());
		t.setFecha(fecha);

		HistorialC hc1 = new HistorialC();
		HistorialCT hcp = new HistorialCT();

		if (ie.addTicket(t) == true) {

			// Otenemos el historial guardado en la base de datos:
			hc1 = ie.getHCByIdhc(idhc);
			List<Medico> lm = ie.getMedicosByHCid(hc1.getIdhc());
			hc1.setListMedicos(lm);

			// obtenemos el paciente asociado al idhc
			Paciente p = ie.getPacienteById(hc1.getIdpaciente());

			// Lo pasamos por RSA para encriptarlo
			// se guarda sobre objeto HistorialCP (soporta BigInteger)
			RSA rsa = new RSA(32);
			s = ie.getSecurityDBByDNIuser(p.getDni());
			rsa.setD(s.getD());
			rsa.setE(s.getE());
			rsa.setN(s.getN());
			hcp.setPresiona(rsa.encrypt((BigInteger.valueOf(hc1.getPresiona()))));
			hcp.setTemp(rsa.encrypt((BigInteger.valueOf(hc1.getTemp()))));
			hcp.setPulso(rsa.encrypt((BigInteger.valueOf(hc1.getPulso()))));
			hcp.setEnfermedad(hc1.getEnfermedad());
			hcp.setIdpaciente(BigInteger.valueOf(hc1.getIdpaciente()));
			hcp.setListMedicos(null);
		}

		return hcp;

	}

	@GET
	@Path("/listhc/{dnim}")
	@Produces(Mediatype.EHEALTH_HC)
	public List<HistorialCT> getListHCfoMedico(@PathParam("dnim") String dnim) {
		List<HistorialC> lhc = new ArrayList<HistorialC>();
		// Medico m = ie.getMedicoById(idm);
		lhc = ie.getHCofMedico(dnim);

		List<HistorialCT> lhct = new ArrayList<HistorialCT>();

		for (int i = 0; i < lhc.size(); i++) {
			lhc.get(i).setListMedicos(null);
			HistorialCT hct = new HistorialCT();
			SecurityDB s = new SecurityDB();
			Paciente p = ie.getPacienteById(lhc.get(i).getIdpaciente());
			s = ie.getSecurityDBByDNIuser(p.getDni());

			// Ciframos con las claves obtenidas del paciente:
			RSA rsa2 = new RSA(32);
			rsa2.setD(s.getD());
			rsa2.setE(s.getE());
			rsa2.setN(s.getN());

			// hct.setEnfermedad(lhc.get(i).getEnfermedad());
			hct.setIdhc(rsa2.encrypt((BigInteger.valueOf(lhc.get(i).getIdhc()))));
			// hct.setPresiona(rsa2.encrypt(BigInteger.valueOf(lhc.get(i).getPresiona())));
			// hct.setPulso(rsa2.encrypt(BigInteger.valueOf(lhc.get(i).getPulso())));
			// hct.setTemp(rsa2.encrypt(BigInteger.valueOf(lhc.get(i).getTemp())));
			hct.setListMedicos(null);
			hct.setIdpaciente(BigInteger.valueOf(lhc.get(i).getIdpaciente()));

			lhct.add(hct);

		}
		return lhct;
	}

	/*
	 * LA API DEBERÍA SER QUIÉN COMPROBARA LAS PASSWORDS + SALT. De esta forma
	 * eliminamos que se envie salt entre API-Android
	 */
	@GET
	@Path("/login/{pacientename}")
	@Produces(Mediatype.EHEALTH_PACIENTE)
	public PacienteT getPacienteByName(@PathParam("pacientename") String pn) {
		Paciente p = ie.getPacienteByNombre(pn);

		Date date = new Date();
		Date d = p.getDate();
		//date.setMonth(3);
		
		//se supone que la fecha del paciente no puede ser mayor a +3meses
		//que coincida con hoy
		d.setMonth(3); //añadimos los 3 meses de caducidad a esa fecha
		

		PacienteT pt = new PacienteT();
		//comprobamos que la fecha+3meses siga estando antes que la fecha actual
		if (date.before(d)) {
			// la contraseña NO ha caducado (la contraseña + 3 meses está
			// después de la fecha actual
			pt.setIdp(p.getIdp());
			pt.setPass(p.getPass());
			pt.setSalt(p.getSalt());
		} else {
			// la contraseña es posterior a la fecha de caducidad. Ha caducado.
			pt.setDni("renueva");
			pt.setPass("renueva");
			pt.setSalt("renueva");
		}

		return pt;
	}

	@GET
	@Path("/loginm/{mediconombre}")
	@Produces(Mediatype.EHEALTH_PACIENTE)
	public MedicoT getNombreByName(@PathParam("mediconombre") String mn) {
		Medico m = ie.getMedicoByNombre(mn);

		MedicoT mt = new MedicoT();
		// mt.setIdmedico((Integer) null);

		Date date = new Date();
		Date d = m.getDate();
		d.setMonth(3);
		Date dp = new Date();

		if (date.after(d)) {
			// la contraseña NO ha caducado (la contraseña + 3 meses está
			// después de la fecha actual
			mt.setSalt(m.getSalt());
			mt.setPass(m.getPass());
			mt.setDnim(m.getDnim());

		} else {
			// la contraseña es posterior a la fecha de caducidad. Ha caducado.
			mt.setDnim("renueva");
			mt.setPass("renueva");
			mt.setSalt("renueva");
		}

		return mt;
	}

	@PUT
	@Path("/actualizar/{idHC}/{idp}")
	@Produces(Mediatype.EHEALTH_HC)
	@Consumes(Mediatype.EHEALTH_HC)
	public boolean Actualizar(@PathParam("idHC") int idHC, HistorialC hc,
			@PathParam("idp") int idp) {
		boolean actualizado = false;
		boolean res;
		Paciente p = ie.getPacienteById(idp);
		SecurityDB s = ie.getSecurityDBByDNIuser(p.getDni());
		RSA rsa = new RSA(32);
		rsa.setD(s.getD());
		rsa.setE(s.getE());
		rsa.setN(s.getN());

		HistorialC hcd = ie.getHCByIdhc(idHC);
		hcd.setPresiona((rsa.decrypt(BigInteger.valueOf(hc.getPresiona()))
				.intValue()));
		hcd.setPulso((rsa.decrypt(BigInteger.valueOf(hc.getPulso())).intValue()));
		hcd.setTemp((rsa.decrypt(BigInteger.valueOf(hc.getTemp())).intValue()));

		res = ie.actualizarBD(hcd, idHC);
		if (res == true) {
			actualizado = true;
		} else {
			actualizado = false;
		}
		return actualizado;
	}

	@POST
	@Path("/localiza/{idp}")
	@Consumes(Mediatype.EHEALTH_LOCALIZACION)
	@Produces(Mediatype.EHEALTH_MENSAJE)
	public Mensaje Localizacion(Localizacion l, @PathParam("idp") int idp) {
		CalcDistancia cd = new CalcDistancia();
		double lat = l.getLatitud();
		double lon = l.getLongitud();
		double ret;
		String nom;

		double retBellvitge = cd.getDistanceb(lat, lon);
		ret = retBellvitge;
		double retQuiron = cd.getDistancec(lat, lon);
		double ret2 = cd.getDistance(ret, retQuiron);
		if (ret == ret2) {
			nom = "Bellvitge";
			ret2 = ret;
		} else
			nom = "Hospital Quiron";
		ret = retQuiron;

		double retHMar = cd.getDistanced(lat, lon);
		double ret3 = cd.getDistance(ret, retHMar);
		if (ret3 == ret) {
			nom = nom;
		} else
			nom = "Hospital Del Mar";

		System.out.println("Dist bellvitge " + retBellvitge);
		System.out.println("Dist Quiron " + retQuiron);
		System.out.println("Dist Mar " + retHMar);

		String respuesta = "Tu hospital más cercano es " + nom;

		Mensaje m = new Mensaje();
		m.setMensaje(respuesta);
		return m;
	}

}
