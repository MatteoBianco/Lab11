package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Event.EventType;

public class Simulator {
	
	private PriorityQueue<Event> queue;		//Coda degli eventi
	
	//PARAMETRI DI SIMULAZIONE
	private Double C;	//Livello iniziale del bacino
	private Double k; 	//Fattore di scala
	private Double F_OUT_MIN;	//Flusso in uscita minimo
	private Double F_OUT;	//Flusso in uscita
	private List<Double> dailyCapacity;	//Livello del bacino ogni giorno
	
	//MODELLO DEL MONDO
	private Double Q;	//Capacità massima bacino idrico
	private River river;	//Fiume della simulazione
	private LocalDate firstDay;
	private LocalDate lastDay;
	
	//VALORI DA CALCOLARE
	private Integer NOT_AVAILABLE; 
	private Double AVG_CAPACITY;
	
	public Double getQ() {
		return Q;
	}
	public Integer getNOT_AVAILABLE() {
		return NOT_AVAILABLE;
	}
	public Double getAVG_CAPACITY() {
		return AVG_CAPACITY;
	}
	public void setK(Double k) {
		this.k = k;
	}
	
	public Simulator() {
		
	}
	
	public void init(River river, Double k) {
		this.queue = new PriorityQueue<>();
		this.dailyCapacity = new ArrayList<>();
				
		this.river = river;
		this.k = k;
		
		Double flowAvg = this.river.getAvg()*3600*24;

		this.Q = this.k*flowAvg*30;
		this.C = this.Q/2;
		this.F_OUT_MIN = 0.8*flowAvg;
		
		this.firstDay = this.river.getFirstFlow().getDay();
		this.lastDay = this.river.getLastFlow().getDay();
		
		this.NOT_AVAILABLE = 0;
		this.AVG_CAPACITY = 0.0;
		
		for(Flow f : this.river.getFlows()) {
			queue.add(new Event(f.getDay(), EventType.IN, f.getFlowInDay()));
		}
		queue.add(new Event(this.firstDay, EventType.OUT, this.F_OUT_MIN));
	}
	
	public void run() {
		if(this.queue.isEmpty() || this.queue == null)
			throw new RuntimeException("Nessun evento presente. Controllare il DB, la selezione del fiume "
					+ "o l'inizializzazione della simulazione");
		while(!this.queue.isEmpty()) {
			processEvent(this.queue.poll());
		}
		for(Double c : this.dailyCapacity) {
			System.out.println(c);
			this.AVG_CAPACITY += c;
		}
		this.AVG_CAPACITY = this.AVG_CAPACITY/this.dailyCapacity.size();
	}
	
	private void processEvent(Event e) {
		Double quantity = e.getFlow();
		switch(e.getType()) {
		
			case IN: 
				this.C += quantity;
				if(this.C > this.Q) {
					queue.add(new Event(e.getTime(), EventType.TRACIMAZIONE, null));
				}
				break;
				
			case OUT:
				Double random = Math.random();
				if(random > 0.95) {
					this.F_OUT = 10*quantity;
				}
				else this.F_OUT = quantity;
				
				if(this.C >= this.F_OUT)
					this.C -= this.F_OUT;
				else {
					this.C = 0.0;
					this.NOT_AVAILABLE ++;
				}
				
				if(this.C <= this.Q) {
					this.dailyCapacity.add(this.C);
				}
				
				if(e.getTime().isBefore(this.lastDay)) {
					queue.add(new Event(e.getTime().plusDays(1), EventType.OUT, this.F_OUT_MIN));
				}
				break;
				
			case TRACIMAZIONE:
				if(this.C>this.Q) {
					this.C = this.Q;
					this.dailyCapacity.add(this.C);
				}
		}
	}
	
}
