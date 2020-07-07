package it.polito.tdp.flightdelays.model;

import java.util.Comparator;

public class ComparatorByNomeAereoporto implements Comparator<Airport> {

	@Override
	public int compare(Airport o1, Airport o2) {
		
		return o1.getName().compareTo(o2.getName());
	}

}
