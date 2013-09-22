package com.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.model.Circle;

// This class is resonsible for talking to the database
// and getting the value.

// This class automatically registered as a spring bean.
@Component
public class JdbcDaoImpl {

	@Autowired
	private DataSource dataSource;
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Circle getCircle(int circleId){
		
		Connection connection = null;
		try{
		
			connection = dataSource.getConnection();
			
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM circle where id = ?");
			ps.setInt(1, circleId);
			
			Circle circle = null;
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				circle = new Circle(circleId, rs.getString("name"));
			}
			rs.close();
			ps.close();
			return circle;
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			try {
				connection.close();
			} catch (SQLException e){
				e.printStackTrace();
			}
		}
	}
}
