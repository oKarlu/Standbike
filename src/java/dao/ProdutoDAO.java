package dao;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Produto;
import java.sql.SQLException;

public class ProdutoDAO {

    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
    
    public boolean gravar(Produto prod) throws SQLException{
        con = ConexaoFactory.conectar();
        
        if(prod.getIdProduto() == 0){
            sql = "INSERT INTO produto(nome, descricao, estoque, preco, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getNome());
            ps.setString(2, prod.getDescricao());
            ps.setInt(3, prod.getEstoque());
            ps.setDouble(4, prod.getPreco());
            ps.setInt(5, prod.getStatus());
            
                    
        } else {
            sql  = "UPDATE produto SET nome = ?, descricao = ?, estoque = ?, preco = ?, status = ? WHERE idProduto = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getNome());
            ps.setString(2, prod.getDescricao());
            ps.setInt(3, prod.getEstoque());
            ps.setDouble(4, prod.getPreco());
            ps.setInt(5, prod.getStatus());
            ps.setInt(6, prod.getIdProduto());
        }
            ps.executeUpdate();
            ConexaoFactory.close(con);
            
            return true;
    }
    
    
    public ArrayList<Produto> getLista() throws SQLException {
        ArrayList<Produto> produtos = new ArrayList<>();
        sql = "SELECT * FROM produto";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            Produto cli = new Produto();
            cli.setIdProduto(rs.getInt("idProduto"));
            cli.setNome(rs.getString("nome"));
            cli.setDescricao(rs.getString("descricao"));
            cli.setEstoque(rs.getInt("estoque"));
            cli.setPreco(rs.getDouble("preco"));
            cli.setStatus(rs.getInt("status"));

            produtos.add(cli);
        }
        ConexaoFactory.close(con);
        return produtos;
    }
    
    
    
    public Produto getCarregarPorId(int idProduto) throws SQLException{
        Produto p = new Produto();
        sql = "SELECT idProduto, nome, descricao, estoque, preco, status "
            + "FROM produto WHERE idProduto = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idProduto);
        rs = ps.executeQuery();
        
        if(rs.next()){
            p.setIdProduto(rs.getInt("idProduto"));
            p.setNome(rs.getString("nome"));
            p.setDescricao(rs.getString("descricao"));
            p.setEstoque(rs.getInt("estoque"));
            p.setPreco(rs.getDouble("preco"));
            p.setStatus(rs.getInt("status"));
            
        }
            ConexaoFactory.close(con);
            return p;
        
    }
  
    
    public boolean ativar(Produto prod) throws SQLException{
        sql = "UPDATE produto SET status = 1 "
            + "WHERE idProduto = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, prod.getIdProduto());
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;
    }
    
    public boolean desativar(Produto prod) throws SQLException{
        sql = "UPDATE produto SET status = 0 "
            + "WHERE idProduto = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, prod.getIdProduto());
        ps.executeUpdate();
        
        ConexaoFactory.close(con);
        return true;
    }
}