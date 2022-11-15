package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Venda;

public class VendaDAO {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql;

    public boolean registrar(Venda v) throws SQLException{
        
        return false;
    }

    
     
}
