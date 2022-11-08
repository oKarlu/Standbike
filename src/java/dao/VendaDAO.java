package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Venda;

public class VendaDAO {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql;

    public VendaDAO() throws Exception{}
    
    public boolean registrar(Venda v) {
        
        try{
            sql = "INSERT INTO venda ()"
            
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
        
    }
    
}
