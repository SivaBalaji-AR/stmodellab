package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class JdbcTest {
    private Connection con;
    @BeforeEach
    public void setUp() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/modellab", "root", "Siva@2005");
        assertNotNull(con,"The connection should be established");
      }
      @Test
      public void insert() throws SQLException
      {
        int id=3;
        String name="pers3";
        String in="insert into model (id,name) value(?,?)";
        try{
            PreparedStatement ps=con.prepareStatement(in);
            ps.setInt(1,id);
            ps.setString(2, name);
            int rowaffected=ps.executeUpdate();
            assertEquals(1,rowaffected,"one row should be affected");
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
      }
     @Test
      public void select() throws SQLException
      {
        String sqlsel="select * from model where id=?";
        try(PreparedStatement p=con.prepareStatement(sqlsel)){
            p.setInt(1, 2);
            try(ResultSet rs=p.executeQuery()){
                assertTrue(rs.next(),"There should be one value for the specific id");
                assertEquals("pers2", rs.getString("name"),"The name is wrong.the data is not inserted correctly");
            }
        } 
      }
      @AfterEach
      public void end() throws SQLException
      {
        if(con!=null && !con.isClosed()){
            con.close();
        }
      }
}