package SCProject.eHealth;

import java.math.BigInteger;

import SCProject.eHealth.model.Ticket;

public class generateTicket {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		InterfaceEBD ie = OperacionesEBD.getInstance();
		Ticket t = new Ticket();
		t.setClaveMedico(BigInteger.valueOf(0));
		t.setDniusuario("11111111");
		t.setIdHC(1);
		ie.addTicket(t);

	}

}
