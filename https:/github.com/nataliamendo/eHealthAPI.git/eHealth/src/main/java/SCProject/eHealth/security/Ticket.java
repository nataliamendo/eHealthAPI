package SCProject.eHealth.security;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;


public class Ticket {
	
	public void guardar(BigInteger clavePrivadaM,  BigInteger clavePublicaP, int idHC)
    {
        FileWriter fichero = null;
        BufferedWriter bw = null;

		System.out.println("=================================================");
        try
        {
            fichero = new FileWriter("/iNatalia/Universidad/tickets.txt",true);
            bw = new BufferedWriter (fichero);
          
            String ticket = "*" + clavePrivadaM.toString() + ";" + clavePublicaP.toString() + ";" + idHC;
            
            bw.write(ticket.toString());
            bw.newLine(); //Write permie otras funciones, entre ellas esta que cree una nueva
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        
        finally 
        {
          try {
			bw.close();
          }
          catch (IOException e) 
          {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("problema al guardar en el fichero la instancia");
          }
          finally
          {
        	  System.out.println("---------------- Partida Guardada --------------");
        	  System.out.println("=================================================");
          }
          
        }
    }
}
