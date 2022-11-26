
package dao;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import model.Menu;


public class MenuDAO {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql;
    
    public boolean gravar(Menu m)throws SQLException{
        con = ConexaoFactory.conectar();
        if(m.getIdMenu() == 0){
            sql = "INSERT INTO menu (nome, link, exibir, status) VALUES (?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getNome());
            ps.setString(2, m.getLink());
            ps.setInt(3, m.getExibir());
            ps.setInt(4, m.getStatus());
            
        }else{
            sql = "UPDATE menu SET nome = ?, link = ?, exibir = ? status = ? WHERE idMenu = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, m.getNome());
            ps.setString(2, m.getLink());
            ps.setInt(3, m.getExibir());
            ps.setInt(4, m.getStatus());
            ps.setInt(5, m.getIdMenu());
            
            
        }
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
        
    }
    
    public ArrayList<Menu> getLista()throws SQLException{
        ArrayList<Menu> menus = new ArrayList<>();
        sql= "SELECT * FROM menu";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            Menu m = new Menu();
            m.setIdMenu(rs.getInt("idMenu"));
            m.setNome(rs.getString("nome"));
            m.setLink(rs.getString("link"));
            m.setExibir(rs.getInt("exibir"));
            m.setStatus(rs.getInt("status"));
            menus.add(m);
            
        }
        
        ConexaoFactory.close(con);
        return menus;
    }
    
   public Menu getCarregarPorId(int idMenu)throws SQLException{
       Menu m = new Menu();
       sql = "SELECT * FROM menu WHERE idMenu = ?";
       con = ConexaoFactory.conectar();
       ps = con.prepareStatement(sql);
       ps.setInt(1, idMenu);
       rs = ps.executeQuery();
       if(rs.next()){
           m.setIdMenu(rs.getInt("idMenu"));
           m.setNome(rs.getString("nome"));
           m.setLink(rs.getString("link"));
           m.setExibir(rs.getInt("exibir"));
           m.setStatus(rs.getInt("status"));
       }
       
       ConexaoFactory.close(con);
       return m;
       
   }
   
   public boolean deletar(Menu m)throws SQLException{
       sql = "DELETE FROM menu WHERE idMenu = ?";
       
       con = ConexaoFactory.conectar();
       ps = con.prepareStatement(sql);
       ps.setInt(1, m.getIdMenu());
       ps.execute();
       ConexaoFactory.close(con);
       return true;
   }
   
   public boolean ativar(Menu m) throws SQLException{
        sql = "UPDATE menu SET status = 1 "
            + "WHERE idMenu = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, m.getIdMenu());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
    public boolean desativar(Menu m) throws SQLException{
        sql = "UPDATE menu SET status = 0 "
            + "WHERE idMenu = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, m.getIdMenu());
        ps.executeUpdate();
        
        ConexaoFactory.close(con);
        return true;
    }
  
}
