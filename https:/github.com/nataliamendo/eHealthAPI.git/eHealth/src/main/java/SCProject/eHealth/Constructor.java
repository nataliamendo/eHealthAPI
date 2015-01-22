package SCProject.eHealth;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import SCProject.eHealth.model.HistorialC;
import SCProject.eHealth.model.Medico;
import SCProject.eHealth.model.Paciente;
import SCProject.eHealth.model.SecurityDB;
import SCProject.eHealth.model.Ticket;
import SCProject.eHealth.security.RSA;
import SCProject.eHealth.security.SHA1;

public class Constructor {
	public static void main(String[] args) throws NoSuchAlgorithmException {
		InterfaceEBD ie = OperacionesEBD.getInstance();
	
		Ticket t = new Ticket();
		t.setClaveMedico(BigInteger.valueOf(0));
		t.setDniusuario("11111111");
		t.setIdHC(1);
		Date fecha = new Date();
		System.out.println (fecha.getDay());
		System.out.println (fecha.getMonth());
		t.setFecha(fecha);
		ie.addTicket(t);
		
		int ip = 1;
		while (ip < 7) {
			// 1-Añadir paciente

			Paciente p = new Paciente();
			p.setDni("1234567" + ip + "A");
			p.setNombrep("paciente" + ip);
			p.setTelef("61234567" + ip);
			Date date = new Date();
			p.setDate(date);
			
			//Guardamos la password de usuario
			SHA1 sha1 = new SHA1();
			String salt = sha1.getSalt();
			p.setPass(sha1.get_SHA_1_SecurePassword("password",salt));
			p.setSalt(salt);
			
			
			ie.addPaciente(p);
			
			//1.1. Añadimos al usuario campos de seguridad
			RSA rsa = new RSA(32);
			rsa.generateKeys();
			BigInteger e = rsa.getE();
			BigInteger d = rsa.getD();
			BigInteger n = rsa.getN();
			SecurityDB s = new SecurityDB();
			s.setD(d);
			s.setN(n);
			s.setE(e);
			s.setSalt(salt);
			s.setDni(p.getDni());
			
			System.out.println(s.getSalt()+","+s.getD()+","+s.getE());
			
			ie.addSecurity(s);

			// 2- Añadir historial clínico
			HistorialC hc = new HistorialC();
			hc.setEnfermedad("enfermedad" + ip);
			hc.setIdpaciente(ip);
			hc.setPresiona(16);
			hc.setPulso(60);
			hc.setTemp(36);

			ie.addHC(hc, p);

			// añadimos médico
			int im = 0;
			while (im < 3) {
				
				
				if(ip == 1)
				{
					//la primera vez que entra en el bucle, hay que crear los médicos
					Medico m = new Medico();
					m.setDnim(im + "9876543Z");
					m.setNombreM("Medico" + im);
					m.setTelefm("6876531" + ip + im);
					Date datem = new Date();
					m.setDate(datem);
					
					String saltm = sha1.getSalt();
					m.setPass(sha1.get_SHA_1_SecurePassword("password",saltm));
					m.setSalt(saltm);
					
					RSA rsa2 = new RSA(32);
					rsa.generateKeys();
					BigInteger e2 = rsa.getE();
					BigInteger d2 = rsa.getD();
					BigInteger n2 = rsa.getN();
					
					s.setD(d2);
					s.setN(n2);
					s.setE(e2);
					s.setSalt(salt);
					s.setDni(m.getDnim());
					ie.addSecurity(s);
					ie.addMedico(m, p, hc);
					im++;
					

				}
				else
				{
					//debe coger los médicos ya creados y meterlos en los otros historiales clínicos

					Medico m = ie.getMedicoById(im+1);
					m.setListHC(ie.getHCofMedico(m.getDnim()));
					m.getListHC().add(hc);
					p.setHistorialc(hc);
					hc.setListMedicos(ie.getMedicosByHCid(hc.getIdhc()));
					hc.getListMedicos().add(m);
					
					ie.updateMedico(m, hc, p);
					
					im++;
					
				}
				
			}
		

			
			ip++;
		}
		

	}
	
}
