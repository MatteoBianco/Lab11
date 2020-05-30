package it.polito.tdp.rivers.db;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public void getAllRivers(Map<Integer, River> idMapRivers) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(!idMapRivers.containsKey(res.getInt("id")))
					idMapRivers.put(res.getInt("id"), new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
	}
	
public List<Flow> getAllFlow(Map<Integer, River> idMapRivers) {
		
		final String sql = "SELECT id, day, flow, river FROM flow";

		List<Flow> flows = new LinkedList<Flow>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				if(idMapRivers.containsKey(res.getInt("river"))) {
					Flow f = new Flow(res.getDate("day").toLocalDate(), 
							res.getDouble("flow"), idMapRivers.get(res.getInt("river")));
					flows.add(f);
					idMapRivers.get(res.getInt("river")).getFlows().add(f);
				}
				else {
					throw new RuntimeException("Error: first get the rivers, than the flows");
				}
			}

			conn.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return flows;
	}
}
