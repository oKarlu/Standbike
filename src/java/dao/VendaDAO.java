package dao;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
            psvp.setDouble(4, vp.getProduto().getPreco());
            psvp.execute();
        }
        
        ConexaoFactory.close(con);
        return true;
    }
    
    public ArrayList<Venda> getLista()throws SQLException{
        con = ConexaoFactory.conectar();
        sql = "SELECT idVenda, dataVenda, precoTotal, idCliente, idUsuario FROM venda";
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        ArrayList<Venda> vendas = new ArrayList<>();
        while(rs.next()){
            Venda v = new Venda();
            v.setIdVenda(rs.getInt("idVenda"));
            v.setDataVenda(rs.getDate("dataVenda"));
            
            ClienteDAO cDao = new ClienteDAO();
            v.setCliente(cDao.getCarregarPorId(rs.getInt("idCliente")));
            
            UsuarioDAO uDao = new UsuarioDAO();
            v.setVendedor(uDao.getCarregarPorId(rs.getInt("idUsuario")));
            
            v.setValorTotal(rs.getDouble("precoTotal"));
            
            vendas.add(v);
        }
        ConexaoFactory.close(con);
        return vendas;
    }

    
     
}
