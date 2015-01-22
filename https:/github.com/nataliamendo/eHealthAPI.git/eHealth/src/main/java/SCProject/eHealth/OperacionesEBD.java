package SCProject.eHealth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import SCProject.eHealth.hibernate.HibernateUtil;
import SCProject.eHealth.model.HistorialC;
import SCProject.eHealth.model.Medico;
import SCProject.eHealth.model.Paciente;
import SCProject.eHealth.model.SecurityDB;
import SCProject.eHealth.model.Ticket;

public class OperacionesEBD implements InterfaceEBD {

	private static OperacionesEBD instance = null;

	public static OperacionesEBD getInstance() {
		if (instance == null)
			instance = new OperacionesEBD();

		return instance;

	}
	
	
	public Certificate getCertificateAPI() throws FileNotFoundException, CertificateException
	{
		InputStream fis = new FileInputStream("/CeHealth.crt");
//		
//		FileReader fr = new FileReader("certificado2.crt");
//		BufferedReader br = new  BufferedReader(fr);
		
		
		CertificateFactory cf  = CertificateFactory.getInstance("X509");
		Certificate cert = cf.generateCertificate(fis);
		
		System.out.println(cert.getPublicKey());
		return cert;
	}

	public boolean actualizarBD(HistorialC hca, int idHC) {
		boolean actualizado = false;
		HistorialC hc = new HistorialC();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			Query query = session.createQuery("from HistorialC where idHC = '"
					+ idHC + "'");
			hc = (HistorialC) query.uniqueResult();

			hc.setPresiona(hca.getPresiona());
			hc.setPulso(hca.getPulso());
			hc.setTemp(hca.getTemp());
			hc.setListMedicos(this.getMedicosByHCid(idHC));

			session.update(hc);

			transaction.commit();
			actualizado = true;

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return actualizado;

	}

	public void addSecurity(SecurityDB s) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			session.save(s);
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	// añadir usuario
	@Override
	public String addPaciente(Paciente p) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			session.save(p);
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	// Añadir Historial Clínico
	@Override
	public String addHC(HistorialC hc, Paciente p) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();

			session.update(p);

			session.save(hc);
			// transaction.commit();

			// p.setHistorialc(hc);
			transaction.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}

	// Añadir Historial Clínico
	@Override
	public String addMedico(Medico m, Paciente p, HistorialC hc) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {

			transaction = session.beginTransaction();
			hc.getListMedicos().add(m);
			m.getListHC().add(hc);
			session.save(m);
			session.update(hc);

			p.setHistorialc(hc);
			session.update(p);

			transaction.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}

	/***
	 * nuevo
	 * 
	 */

	public String updateMedico(Medico m, HistorialC hc, Paciente p) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {

			transaction = session.beginTransaction();

			session.update(m);

			session.update(hc);
			session.update(p);

			transaction.commit();

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return null;
	}

	// Obtener historial de paciente a partir de su idpaciente
	@Override
	public HistorialC getHCByDNI(String dnipaciente) {

		// obtenemos el paciente:
		Paciente p = this.getPacienteByDNI(dnipaciente);
		int idpaciente = p.getIdp();

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		HistorialC hc = new HistorialC();

		try {
			transaction = session.beginTransaction();

			Query query = session
					.createQuery("from HistorialC where idpaciente = '"
							+ idpaciente + "'");
			hc = (HistorialC) query.uniqueResult();
			// hc.setListMedicos(this.getMedicosByHCid(hc.getIdhc()));
			hc.setListMedicos(null);
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return hc;
	}

	@Override
	public HistorialC getHCByIdhc(int idhc) {

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		HistorialC hc = new HistorialC();

		try {
			transaction = session.beginTransaction();

			Query query = session.createQuery("from HistorialC where idhc = '"
					+ idhc + "'");
			hc = (HistorialC) query.uniqueResult();
			// hc.setListMedicos(this.getMedicosByHCid(hc.getIdhc()));
			hc.setListMedicos(null);
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return hc;
	}

	// Obtener medicos implicados en HC a partir de su idhistorial
	@Override
	public List<Medico> getMedicosByHCid(int idhc) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<Medico> ml = new ArrayList<Medico>();

		try {
			transaction = session.beginTransaction();
			Query query = session
					.createQuery("from Medico as m inner join fetch m.listHC as hc where hc.idhc='"
							+ idhc + "'");

			ml = (List<Medico>) query.list();
			for (int i = 0; i < ml.size(); i++) {

				System.out.println("Medicos: " + ml.get(i).getNombreM());
			}

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			// session.close();
		}
		return ml;
	}

	// lista de historiales clínicos de un médico:
	@Override
	public List<HistorialC> getHCofMedico(String dnim) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		Medico m = this.getMedicoByDNI(dnim);
		List<HistorialC> lhc = new ArrayList<HistorialC>();
		List<HistorialC> lhcf = new ArrayList<HistorialC>();

		// primero obtnemos la lista de médicos de los historiales clínicos
		int i = 0;
		lhc = this.getHisotiralCList();
		while (i < lhc.size()) {
			// Para cada hisotiral, comprobar si en la lista de médicos aparece
			// el médico con idmedico
			int j = 0;
			while (j < lhc.get(i).getListMedicos().size()) {
				if (lhc.get(i).getListMedicos().get(j).getIdmedico() == m
						.getIdmedico()) {
					HistorialC hc = lhc.get(i);
					lhcf.add(hc);
					System.out.println(hc.getIdhc());
				}

				j++;
			}

			i++;
		}

		return lhcf;
	}

	// lista de pacientes:
	@Override
	public List<Paciente> getPacientesList() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<Paciente> listP = new ArrayList<Paciente>();

		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("From Paciente");
			listP = (List<Paciente>) query.list();

			for (int i = 0; i < listP.size(); i++) {
				System.out.println("Nombre paciente: "
						+ listP.get(i).getNombrep());
			}

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			// session.close();
		}
		return listP;
	}

	public List<HistorialC> getHisotiralCList() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		List<HistorialC> listHC = new ArrayList<HistorialC>();

		try {
			transaction = session.beginTransaction();
			Query query = session.createQuery("From HistorialC");
			listHC = (List<HistorialC>) query.list();

			for (int i = 0; i < listHC.size(); i++) {
				listHC.get(i).setListMedicos(
						this.getMedicosByHCid(listHC.get(i).getIdhc()));
			}

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			// session.close();
		}
		return listHC;
	}

	// *********************Código nuevo: Añadir al usuario
	/*
	 * @Override public void addPasswords(UserKey p) { Session session =
	 * HibernateUtil.getSessionFactory().openSession(); Transaction transaction
	 * = null;
	 * 
	 * try { transaction = session.beginTransaction();
	 * 
	 * session.save(p); transaction.commit();
	 * 
	 * } catch (HibernateException e) { transaction.rollback();
	 * e.printStackTrace(); } finally { session.close(); }
	 * 
	 * }
	 */

	// obtener paciente por su nombre
	public Paciente getPacienteByNombre(String nombreP) {
		Paciente p = new Paciente();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Query query = session.createQuery("from Paciente where nombrep='"
					+ nombreP + "'");
			p = (Paciente) query.uniqueResult();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return p;
	}

	public Paciente getPacienteById(int idpaciente) {
		Paciente p = new Paciente();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Query query = session.createQuery("from Paciente where idp='"
					+ idpaciente + "'");
			p = (Paciente) query.uniqueResult();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return p;
	}

	public SecurityDB getSecurityDBByDNIuser(String userDNI) {

		SecurityDB s = new SecurityDB();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Query query = session.createQuery("from SecurityDB where dni='"
					+ userDNI + "'");
			s = (SecurityDB) query.uniqueResult();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return s;
	}

	public Paciente getPacienteByDNI(String dni) {
		Paciente p = new Paciente();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Query query = session.createQuery("from Paciente where dni='" + dni
					+ "'");
			p = (Paciente) query.uniqueResult();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return p;
	}

	// obtener medico por su nombre
	public Medico getMedicoByNombre(String nombreM) {
		Medico m = new Medico();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Query query = session.createQuery("from Medico where nombreM='"
					+ nombreM + "'");
			m = (Medico) query.uniqueResult();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return m;
	}

	public Medico getMedicoByDNI(String dni) {
		Medico m = new Medico();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Query query = session.createQuery("from Medico where dnim='" + dni
					+ "'");
			m = (Medico) query.uniqueResult();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return m;
	}

	public Medico getMedicoById(int idm) {
		Medico m = new Medico();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;

		try {
			transaction = session.beginTransaction();

			Query query = session.createQuery("from Medico where idmedico='"
					+ idm + "'");
			m = (Medico) query.uniqueResult();
			transaction.commit();

		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return m;
	}

	@Override
	public boolean addTicket(Ticket t) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		boolean done = false;

		try {
			transaction = session.beginTransaction();

			session.save(t);
			transaction.commit();
			done = true;
		} catch (HibernateException e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return done;
	}
	
	public boolean actualizarPassP(Paciente p) {
		boolean actualizado = false;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			
			session.update(p);

			transaction.commit();
			actualizado = true;

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return actualizado;

	}
	public boolean actualizarPassMedico(Medico m) {
		boolean actualizado = false;

		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			
			session.update(m);

			transaction.commit();
			actualizado = true;

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return actualizado;

	}


}
