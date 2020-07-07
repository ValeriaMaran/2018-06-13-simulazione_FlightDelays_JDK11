package it.polito.tdp.flightdelays.db;

import java.sql.Connection;

import it.polito.tdp.flightdelays.model.Adiacenze;
import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Airport;
import it.polito.tdp.flightdelays.model.Model;
import java.util.*;
public class TestDAO {

	public static void main(String[] args) {
		Model m = new Model();
		Map<String, Airport> mappa = new HashMap<String,Airport>();
		
		try {
			Connection connection = DBConnect.getConnection();
			connection.close();
			System.out.println("Test PASSED");

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}
		

		FlightDelaysDAO dao = new FlightDelaysDAO();

		//System.out.println(dao.loadAllAirlines());
		//System.out.println(dao.loadAllAirports());
		//System.out.println(dao.loadAllFlights());
		Map<String,Airline> porcoddio = new HashMap<String,Airline>();
		porcoddio = dao.getStringLineeAeree();
		/*for(Airline a : porcoddio.values()) {
			System.out.println(a.getName().toString()+"\n");
		}*/
		
		dao.loadAllAirports(mappa);
		/*for(Airport a : mappa.values()) {
			System.out.println(a.toString()+"\n");
		}
		
		m.CreaGrafo();
		System.out.print(m.getVertexNumber());*/
		List<Adiacenze> rotte = new LinkedList<Adiacenze>();
		for(Adiacenze a :rotte) {
			System.out.print(a.toString()+"\n");
		}
		System.out.println(rotte.size());
		System.out.println(mappa.size());
	
		if(mappa.containsKey("ABQ")) {
			System.out.print("ok");
		}
		m.CreaGrafo("AS");
		System.out.println(m.getVertexNumber());
	}
	
}
