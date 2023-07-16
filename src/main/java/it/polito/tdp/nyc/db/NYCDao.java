package it.polito.tdp.nyc.db;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.nyc.model.Adiacenza;
import it.polito.tdp.nyc.model.City;
import it.polito.tdp.nyc.model.Hotspot;
import it.polito.tdp.nyc.model.Provider;


public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				
	
				Hotspot h = new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode"));
				result.add(h);
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return result;
	}
	
	
	
	public void getAllCity(Map<String, City> idMap) {
		
		String sql="SELECT DISTINCT city "
				+ "FROM nyc_wifi_hotspot_locations ";
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet res = st.executeQuery();

			//if(!idMap.containsKey(res.getString("city"))) {
				
				while (res.next()) {
					
					City c = new City(res.getString("city"));
					idMap.put(c.getCity(), c);
				//}
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
	}
	
	
	
	public List<Provider> getAllProvider(){
		String sql = "SELECT DISTINCT provider FROM nyc_wifi_hotspot_locations";
		List<Provider> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Provider(res.getString("provider")));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}

	
	public List<City> getVerticiGrafo(Provider p, Map<String, City> idMap) {
		
		String sql="SELECT city "
				+ "FROM nyc_wifi_hotspot_locations "
				+ "WHERE provider= ? "
				+ "GROUP BY city";
		
		List<City> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,p.getProvider());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				City c = new City(res.getString("city"));
				result.add(c);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return result;
		
	}

	public List<Adiacenza> getArchiGrafo(Provider p, Map<String, City> idMap) {
		String sql = "SELECT h1.City AS c1, h2.City AS c2, (AVG(h1.Latitude)+AVG(h2.Latitude))-(AVG(h1.Longitude)+AVG(h2.Longitude)) AS peso "
				+ "FROM nyc_wifi_hotspot_locations h1, nyc_wifi_hotspot_locations h2 "
				+ "WHERE h1.Provider=h2.Provider AND h1.City>h2.City AND h1.Provider= ? "
				+ "GROUP BY c1,c2" ;
		
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1,p.getProvider());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Adiacenza(idMap.get(res.getString("c1")),idMap.get(res.getString("c2")), res.getDouble("peso")));
				
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
		
	}
	
}
