package SCProject.eHealth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;


@Entity
public class Medico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmedico")
	private int idmedico;
	
	@Column(name = "nombreM")
	private String nombreM;
	
	@Column(name = "dnim")
	private String dnim;
	
	@Column(name = "telefm")
	private String telefm;
	
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
	
	 @ManyToMany(cascade=CascadeType.ALL)
	    @JoinTable(name="Medico_HistorialC", joinColumns={@JoinColumn(referencedColumnName="idmedico")}
	                                        , inverseJoinColumns={@JoinColumn(referencedColumnName="idhc")}) 
	List<HistorialC> listHC= new ArrayList<HistorialC>();
	 
	 
	// * ** * * 
	 //Relación con la TABLA DE USERPASS
	/*@OneToOne(fetch=FetchType.LAZY , cascade=CascadeType.ALL)
	@JoinColumn(name="UserKey_idUser")
	UserKey up;*/
	//** * * * *

	public List<HistorialC> getListHC() {
		return listHC;
	}

	public void setListHC(List<HistorialC> listHC) {
		this.listHC = listHC;
	}

	public void add(HistorialC hc) {
		listHC.add(hc);
		
	}

	public boolean remove(Object o) {
		return listHC.remove(o);
	}

	public int getIdmedico() {
		return idmedico;
	}

	public void setIdmedico(int idmedico) {
		this.idmedico = idmedico;
	}

	public String getNombreM() {
		return nombreM;
	}

	public void setNombreM(String nombreM) {
		this.nombreM = nombreM;
	}

	public String getDnim() {
		return dnim;
	}

	public void setDnim(String dnim) {
		this.dnim = dnim;
	}

	public String getTelefm() {
		return telefm;
	}

	public void setTelefm(String telefm) {
		this.telefm = telefm;
	}

	
	

}
