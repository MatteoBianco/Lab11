package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.Comparator;

public class Event implements Comparable<Event> {

	public enum EventType {
		IN,
		OUT,
		TRACIMAZIONE;
	}
	
	private LocalDate time;
	private EventType type;
	private Double flow;
	
	public Event(LocalDate time, EventType type, Double flow) {
		super();
		this.time = time;
		this.type = type;
		this.flow = flow;
	}
	
	public LocalDate getTime() {
		return time;
	}
	public void setTime(LocalDate time) {
		this.time = time;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public Double getFlow() {
		return flow;
	}
	public void setFlow(Double flow) {
		this.flow = flow;
	}

	@Override
	public int compareTo(Event o) {
		if(this.time.compareTo(o.time) != 0)
			return this.time.compareTo(o.time);
		else return this.type.compareTo(o.type);
	}

}
