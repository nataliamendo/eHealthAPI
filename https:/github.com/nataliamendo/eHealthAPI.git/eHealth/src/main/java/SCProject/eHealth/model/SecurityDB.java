package SCProject.eHealth.model;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SecurityDB {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ids")
	int ids;
	
	@Column(name = "dni")
	String dni;
	
	@Column(name = "e")
	BigInteger e;
	
	@Column(name = "d")
	BigInteger d;
	
	@Column(name = "n")
	BigInteger n;

	@Column(name = "salt")
	String salt;

	public int getIds() {
		return ids;
	}

	public void setIds(int ids) {
		this.ids = ids;
	}

	public BigInteger getN() {
		return n;
	}

	public void setN(BigInteger n) {
		this.n = n;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public BigInteger getE() {
		return e;
	}

	public void setE(BigInteger e) {
		this.e = e;
	}

	public BigInteger getD() {
		return d;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
}
