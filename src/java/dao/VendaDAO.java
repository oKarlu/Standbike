package dao;

import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Cliente;
import model.Produto;
import model.ProdutoRecibo;
import model.Recibo;
import model.Usuario;
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
    
    public Recibo recibo(int idVenda) throws SQLException{
        Recibo recibo = new Recibo();
        Venda v = new Venda();
        Cliente c = new Cliente();
        Usuario u = new Usuario();
        sql = "SELECT venda_produto.idVenda, venda.dataVenda, venda.precoTotal, venda.idCliente, cliente.nome, cliente.cpf,"
            + " cliente.telefone, usuario.idUsuario, usuario.nome FROM venda_produto Join produto inner join venda"
            + " inner join cliente inner join usuario on venda_produto.idProduto = produto.idProduto"
            + " and venda.idVenda = venda_produto.idVenda and cliente.idCliente = venda.idCliente"
            + " and usuario.idUsuario = venda.idUsuario and venda.idVenda = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idVenda);
        rs = ps.executeQuery();
        
        if(rs.next()){
            
            v.setIdVenda(rs.getInt("idVenda"));
            v.setDataVenda(rs.getDate("dataVenda"));
            v.setValorTotal(rs.getDouble("precoTotal"));
            System.out.println(v);
            recibo.setVenda(v);

            c.setIdCliente(rs.getInt("idCliente"));
            c.setNome(rs.getString("cliente.nome"));
            c.setCpf(rs.getString("cpf"));
            c.setTelefone(rs.getString("telefone"));
            recibo.setCliente(c);
            
            u.setIdUsuario(rs.getInt("usuario.idUsuario"));
            u.setNome(rs.getString("usuario.nome"));
            recibo.setVendedor(u);
            
            
            
        }
            ConexaoFactory.close(con);
            return recibo;
    }
    
    public ArrayList<ProdutoRecibo> produtoRecibo(int idVenda) throws SQLException{
        ArrayList<ProdutoRecibo> produtos = new ArrayList<>();
        sql = "SELECT venda_produto.idProduto,"
            + " produto.nome, venda_produto.valor, venda_produto.quantidade FROM venda_produto Join produto inner join venda"
            + " on venda_produto.idProduto = produto.idProduto"
            + " and venda.idVenda = venda_produto.idVenda and venda.idVenda = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idVenda);
        rs = ps.executeQuery();
        while (rs.next()) {
            ProdutoRecibo pr = new ProdutoRecibo();
            
            Produto p = new Produto();
            p.setIdProduto(rs.getInt("idProduto"));
            p.setNome(rs.getString("produto.nome"));
            pr.setProduto(p);
            
            VendaProduto vp = new VendaProduto();
            vp.setPrecoUnitario(rs.getDouble("valor"));
            vp.setQtd(rs.getInt("quantidade"));
            pr.setVp(vp);
            
            produtos.add(pr);
        }
        
        return produtos;
    }

    public Venda getCarregarPorId(int idVenda) throws SQLException{
        Venda v = new Venda();
        sql = "SELECT * FROM venda WHERE idVenda = ?";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idVenda);
        rs = ps.executeQuery();
        if(rs.next()){
            v.setIdVenda(rs.getInt("idVenda"));
            v.setDataVenda(rs.getDate("dataVenda"));
            v.setValorTotal(rs.getDouble("precoTotal"));
            
            Cliente c = new Cliente();
            c.setIdCliente(rs.getInt("idCliente"));
            v.setCliente(c);
            
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("idUsuario"));
            v.setVendedor(u);
            
            
        }
            ConexaoFactory.close(con);
            return v;
    }
    
}
