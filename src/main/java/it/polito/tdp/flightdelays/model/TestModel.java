package it.polito.tdp.flightdelays.model;
import java.util.*;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FlightDelaysDAO dao = new FlightDelaysDAO();
		Model m = new Model();
		Map<String,Airport> airports = new HashMap<String,Airport>();
		dao.loadAllAirports(airports);
		m.CreaGrafo("AS");
		System.out.println(m.getVertexNumber());
		System.out.println(m.getEdgesNumber());
		m.getRottePeggiori();
	}

}
