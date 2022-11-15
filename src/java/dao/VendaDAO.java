package dao;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Venda;
import model.VendaProduto;

public class VendaDAO {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql;

    public boolean registrar(Venda v) throws SQLException{
        con = ConexaoFactory.conectar();
        sql = "INSERT INTO venda (dataVenda, precoTotal, idCliente, idUsuario) " +
                "VALUES (now(), ?, ?, ?)";
        ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setDouble(1, v.getValorTotal());
        ps.setInt(2, v.getCliente().getIdCliente());
        ps.setInt(3, v.getVendedor().getIdUsuario());
        ps.execute();
        rs = ps.getGeneratedKeys();
        if(rs.next()){
            v.setIdVenda(rs.getInt(1));
        }
        
        for(VendaProduto vp : v.getCarrinho()){
            String sql_vp = 
                    "INSERT INTO venda_produto(idVenda, idProduto, quantidade, valor) "
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement psvp = con.prepareCall(sql_vp);
            psvp.setInt(1, v.getIdVenda());
            psvp.setInt(2, vp.getProduto().getIdProduto());
            psvp.setInt(3, vp.getQtd());
            psvp.setDouble(4, vp.getPrecoUnitario());
            psvp.execute();
        }
        
        ConexaoFactory.close(con);
        return true;
    }

    
     
}
