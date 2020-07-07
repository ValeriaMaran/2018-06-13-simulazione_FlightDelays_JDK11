package it.polito.tdp.flightdelays.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.flightdelays.model.Adiacenze;
import it.polito.tdp.flightdelays.model.Airline;
import it.polito.tdp.flightdelays.model.Airport;
import it.polito.tdp.flightdelays.model.Flight;

public class FlightDelaysDAO {

	public List<Airline> loadAllAirlines() {
		String sql = "SELECT id, airline from airlines";
		List<Airline> result = new ArrayList<Airline>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Airline(rs.getString("ID"), rs.getString("airline")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public Map<String,Airport> loadAllAirports(Map<String,Airport> idMap) {
		String sql = "SELECT id, airport, city, state, country, latitude, longitude FROM airports";
		//Map<String,Airport> idMap = new HashMap<String,Airport>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				Airport airport = new Airport(rs.getString("id"), rs.getString("airport"), rs.getString("city"),
					rs.getString("state"), rs.getString("country"), rs.getDouble("latitude"), rs.getDouble("longitude"));
				
				if(idMap.containsKey(rs.getString("id"))==false) {
					idMap.put(rs.getString("id"), airport);
				}
		
			}
			
			conn.close();
			return idMap;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Flight> loadAllFlights() {
		String sql = "SELECT id, airline, flight_number, origin_airport_id, destination_airport_id, scheduled_dep_date, "
				+ "arrival_date, departure_delay, arrival_delay, air_time, distance FROM flights";
		List<Flight> result = new LinkedList<Flight>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Flight flight = new Flight(rs.getInt("id"), rs.getString("airline"), rs.getInt("flight_number"),
						rs.getString("origin_airport_id"), rs.getString("destination_airport_id"),
						rs.getTimestamp("scheduled_dep_date").toLocalDateTime(),
						rs.getTimestamp("arrival_date").toLocalDateTime(), rs.getInt("departure_delay"),
						rs.getInt("arrival_delay"), rs.getInt("air_time"), rs.getInt("distance"));
				result.add(flight);
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	public Map<String,Airline> getStringLineeAeree(){
		String sql = "SELECT id,airline FROM airlines a ORDER BY a.AIRLINE ASC ";
		Map<String,Airline> lineeAeree = new HashMap<String,Airline>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Airline a = new Airline(rs.getString("id"),rs.getString("airline"));
				lineeAeree.put(a.getId(),a);
				
			}
			conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return lineeAeree;
	}
	public List<Adiacenze> getAdiacenze(String id, Map<String, Airport> idMap){
		//Map<String,Airport> aereoporti = new HashMap<String,Airport>();
		List<Adiacenze> rotte = new LinkedList<Adiacenze>();
		//loadAllAirports(aereoporti);
		
		String sql ="SELECT f.ORIGIN_AIRPORT_ID AS air1, f.DESTINATION_AIRPORT_ID AS air2,AVG(f.ARRIVAL_DELAY) AS tot "
				+ "FROM flights f "
				+ "WHERE f.AIRLINE =? "
				+ "GROUP BY f.ORIGIN_AIRPORT_ID,f.DESTINATION_AIRPORT_ID "
				+ "HAVING COUNT(f.FLIGHT_NUMBER)>1";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, id);
			ResultSet rs = st.executeQuery();
				while(rs.next()) {
					if(idMap.containsKey(rs.getString("air1")) && idMap.containsKey(rs.getString("air2"))) {
						Airport a1 = idMap.get(rs.getString("air1"));
						Airport a2 = idMap.get(rs.getString("air2"));
						Adiacenze a = new Adiacenze(a1,a2,rs.getDouble("tot"));
						rotte.add(a);
					}
					
				}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return rotte;
	}
}

