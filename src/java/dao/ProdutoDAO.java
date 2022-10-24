package dao;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Produto;

public class ProdutoDAO {
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
    
    public boolean gravar(Produto prod) throws SQLException{
        con = ConexaoFactory.conectar();
        
        if(prod.getIdProduto() == 0){
            sql = "INSERT INTO produto(nome, descricao, estoque, preco, nomeArquivo, caminho, status)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getNome());
            ps.setString(2, prod.getDescricao());
            ps.setInt(3, prod.getEstoque());
            ps.setDouble(4, prod.getPreco());
            ps.setString(5, prod.getNomeArquivo());
            ps.setString(6, prod.getCaminho());
            ps.setInt(7, prod.getStatus());
            
                    
        } else {
            sql  = "UPDATE produto "
                    + "SET nome = ?, descricao = ?, estoque = ?, "
                    + "preco = ?, nomeArquivo = ?, caminho = ?, status = ?"
                    + "WHERE idProduto = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, prod.getNome());
            ps.setString(2, prod.getDescricao());
            ps.setInt(3, prod.getEstoque());
            ps.setDouble(4, prod.getPreco());
            ps.setString(5, prod.getNomeArquivo());
            ps.setString(6, prod.getCaminho());
            ps.setInt(7, prod.getStatus());
            ps.setInt(8, prod.getIdProduto());
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
            Produto prod = new Produto();
            prod.setIdProduto(rs.getInt("idProduto"));
            prod.setNome(rs.getString("nome"));
            prod.setDescricao(rs.getString("descricao"));
            prod.setEstoque(rs.getInt("estoque"));
            prod.setPreco(rs.getDouble("preco"));
            prod.setNomeArquivo(rs.getString("nomeArquivo"));
            prod.setCaminho(rs.getString("caminho"));
            prod.setStatus(rs.getInt("status"));

            produtos.add(prod);
        }
        ConexaoFactory.close(con);
        return produtos;
    }
    
    
    
    public Produto getCarregarPorId(int idProduto) throws SQLException{
        Produto p = new Produto();
        sql = "SELECT idProduto, nome, descricao, estoque, preco, nomeArquivo, caminho, status "
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
            p.setNomeArquivo(rs.getString("nomeArquivo"));
            p.setCaminho(rs.getString("caminho"));
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
