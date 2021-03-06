package it.polito.tdp.flightdelays.model;

public class Adiacenze implements Comparable<Adiacenze> {
	Airport a1;
	Airport a2;
	Double peso;
	public Adiacenze(Airport a1, Airport a2, double peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}
	public Airport getA1() {
		return a1;
	}
	public void setA1(Airport a1) {
		this.a1 = a1;
	}
	public Airport getA2() {
		return a2;
	}
	public void setA2(Airport a2) {
		this.a2 = a2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return String.format("Adiacenze [a1=%s, a2=%s, peso=%s+\n]", a1, a2, peso);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenze other = (Adiacenze) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!a1.equals(other.a1))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		return true;
	}
	@Override
	public int compareTo(Adiacenze o) {
		return (this.peso.compareTo(o.peso));
	}

	public String toStringRottePeggiori() {
		String s = "Aereoporto di Partenza: "+a1.toString()+" Aereoporto di Arrivo:  "+a2.toString()+" Peso: "+this.peso.toString()+"\n";
		return s;
	}
	
}
