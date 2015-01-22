package SCProject.eHealth;

import java.io.FileNotFoundException;
import java.security.cert.CertificateException;
import java.util.Date;

import SCProject.eHealth.model.Paciente;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, CertificateException {
		InterfaceEBD ie = OperacionesEBD.getInstance();
//		
//		double lat = 41.42;
//		double lon = 2.13;
//		CalcDistancia cd = new CalcDistancia();
//		double ret;
//		String nom;
//		
//		double retBellvitge = cd.getDistanceb(lat , lon);
//		ret = retBellvitge;
//		double retQuiron = cd.getDistancec(lat, lon);
//		double ret2 = cd.getDistance(ret, retQuiron);
//		if (ret == ret2){
//			 nom = "Bellvitge";
//			 ret2=ret;
//		}
//		else 
//			nom="Hospital Quiron";
//		ret= retQuiron;
//		
//		double retHMar = cd.getDistanced(lat, lon);
//		double ret3 = cd.getDistance(ret, retHMar);
//		if (ret3 == ret){
//			 nom = nom;
//		}
//		else 
//			nom="Hospital Del Mar";
//		
//
//		System.out.println("Dist bellvitge " + retBellvitge);
//		System.out.println("Dist Quiron " + retQuiron);
//		System.out.println("Dist Mar " + retHMar);
//		
//		System.out.println("Tu hospital más cercano es " + nom);
//		
//		
		
		Paciente p = ie.getPacienteById(1);
		Date d = p.getDate();
		d.setMonth(6);
		p.setDate(d);
		
	

		
//		String pass="password";
//		SHA1 sha1 = new SHA1();
//		String passsha1 = null;
//		try {
//			passsha1 = sha1.get_SHA_1_SecurePassword(pass, sha1.getSalt());
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		Paciente p = ie.getPacienteByNombre("paciente1");
//		System.out.println("*" + p.getNombrep());
//		
//		if(p.getPass().equals(passsha1))
//		{
//			System.out.println("Contra correcta");
//		}
//		else
//		{
//			System.out.println("Contra incorrecta");
//		}
		
		//if()
		
		// * * * * * * * PAILLIER * * * * * *
		
//		HistorialC hc = ie.getHCById(1);
//		System.out.println("1 *** HC datos: " );
//		System.out.println(" = " + hc.getIdhc());
//		System.out.println(" = " + hc.getEnfermedad());
//		System.out.println(" = " + hc.getPresiona());
//		
//		//Encriptamos:
//		HistorialCP hcp = new HistorialCP();
//		
//		//Otenemos el historial guardado en la base de datos:
//		hc = ie.getHCById(1);
//		
//		//Lo pasamos por Paillier para encriptarlo 
//		//se guarda sobre objeto HistorialCP (soporta BigInteger)
//		Paillier p = new Paillier(); 		
//		hcp.setPrimop(p.p);
//		hcp.setPrimoq(p.q);
//		hcp.setIdhc(p.Encryption((BigInteger.valueOf(hc.getIdhc()))));
//		
//		
//		System.out.println("2 *** Campo encritado: ");
//		System.out.println(" = " + hcp.getIdhc());
//		
//		//Desencriptar
//		BigInteger primop = hcp.getPrimop();
//		BigInteger primoq = hcp.getPrimoq();
//		Paillier p2= new Paillier(primop, primoq);
//		HistorialCP hc2 = new HistorialCP();
//		hc2.setIdhc(p2.Decryption(hcp.getIdhc()));
//		System.out.println("3 *** Desencripado: ");
//		System.out.println(" = " + hc2.getIdhc());
		
//		// * * * * * * * OBTENER HC PACIENTE * * * * * 
//		HistorialC hc = ie.getHCByDNI("12345671A");
//		System.out.println(" " +  hc.getEnfermedad());

		// * * * * * * * LISTA MEDICOS * * * * * *
//		ie.getMedicosByHCid(1);
		
		// * * * * * * * LISTA PACIENTES * * * * *
		//ie.getPacientesList();
		
		// * * * * * * * LISTA DE HC DE UN MEDICO * * * * * *
		//ie.getHCofMedico("19876543Z");
		
		// * * * * * * * Cifrar/Descifrar * * * * * *
//		HistorialC hc1 = new HistorialC();
//		HistorialCT hcp = new HistorialCT();
//		
//		hc1 = ie.getHCByDNI("12345673A");
//		List<Medico> lm = ie.getMedicosByHCid(hc1.getIdhc());
//		hc1.setListMedicos(lm);
//		
//		RSA rsa = new RSA(32);
//		hcp.setPresiona(rsa.encrypt((BigInteger.valueOf(hc1.getPresiona()))));
//		hcp.setTemp(rsa.encrypt((BigInteger.valueOf(hc1.getTemp()))));
//		hcp.setPulso(rsa.encrypt((BigInteger.valueOf(hc1.getPulso()))));
//		hcp.setEnfermedad(hc1.getEnfermedad());
//		hcp.setListMedicos(null);
//		
//		//Desenciframos
//		SecurityDB s = new SecurityDB();
//		s = ie.getSecurityDBByDNIuser("12345673A");
//		RSA rsa2 = new RSA(32);
//		rsa2.setD(s.getD());
//		rsa2.setE(s.getE());
//		rsa2.setN(s.getN());
//		
//		System.out.println(rsa.decrypt(hcp.getPresiona()));
//		System.out.println(hcp.getEnfermedad());
//		System.out.println(rsa.decrypt(hcp.getPulso()));
//		System.out.println(rsa.decrypt(hcp.getTemp()));
		
		// * * * * * * * LISTA DE HC DE UN MEDICO * * * * * *
//		ie.getHCofMedico("19876543Z");
		
		
//		InputStream fis = new FileInputStream("certificado2.crt");
//		CertificateFactory cf  = CertificateFactory.getInstance("X509");
//		Certificate cert = cf.generateCertificate(fis);
		
		//Certificate cert = ie.getCertificateAPI();
		
	//	System.out.println(ie.getCertificateAPI());
		
	}

}
