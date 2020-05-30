package it.polito.tdp.rivers.db;

import java.util.HashMap;
import java.util.Map;

public class TestRiversDAO {

	public static void main(String[] args) {
		RiversDAO dao = new RiversDAO();
		Map idMap = new HashMap<>();
		dao.getAllRivers(idMap);
		System.out.println(idMap.values());
		System.out.println(idMap.values().size());
		System.out.println(dao.getAllFlow(idMap).size());
	}

}
