package it.polito.tdp.flightdelays.
model;
import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.flightdelays.db.FlightDelaysDAO;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.*;
public class Model {
	SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> grafo;
	Map<String,Airport> idMapAereoporti;	
	FlightDelaysDAO dao;
	List<Adiacenze> rotte;
	
	
	public Model() {
		dao = new FlightDelaysDAO();
		idMapAereoporti = new HashMap<String,Airport>();
	}
	public List<String> getMappaLinee(){
		List<String> linee = new LinkedList<String>();
		
		for(Airline a : dao.getStringLineeAeree().values()) {
			String s = a.getId()+"-"+a.getName();
			linee.add(s);
		}
		return linee;
	}
	public void CreaGrafo(String id) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		dao.loadAllAirports(idMapAereoporti); 
		this.rotte = dao.getAdiacenze(id, idMapAereoporti);
		for(Airport a : idMapAereoporti.values()) {
			grafo.addVertex(a);
		}
		for(Adiacenze a : rotte) {
			Airport sa = a.getA1();
			Airport da = a.getA2();
			double peso =a.getPeso()/(LatLngTool.distance(new LatLng(sa.getLatitude(),sa.getLongitude()), 
					new LatLng(da.getLatitude(),da.getLongitude()), LengthUnit.KILOMETER));
			a.setPeso(peso);
			Graphs.addEdgeWithVertices(grafo,sa, da,peso);
		}
	}
		
	
	public int getVertexNumber() {
		return grafo.vertexSet().size();
	}
	public int getEdgesNumber() {
		return grafo.edgeSet().size();
	}
	
	public List<Adiacenze> getRottePeggiori(){
		Collections.sort(rotte);
		int i = 0;
		List<Adiacenze> rottePeggiori = new ArrayList<Adiacenze>();
		while(rottePeggiori.size()!=10) {
			Adiacenze a = rotte.get(i);
			a.setPeso(-a.getPeso());
			rottePeggiori.add(a);
			i++;
		}
		return rottePeggiori;
	}
}