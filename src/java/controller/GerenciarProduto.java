package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Produto;
import dao.ProdutoDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "GerenciarProduto", urlPatterns = {"/gerenciarProduto"})
public class GerenciarProduto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idProduto = request.getParameter("idProduto");
        String mensagem = "";
        
        Produto c = new Produto();
        ProdutoDAO cdao = new ProdutoDAO();
        
        try {
            if(acao.equals("listar")){
                ArrayList<Produto> produtos = new ArrayList<>();
                produtos = cdao.getLista();
                for(Produto produto: produtos){
                    System.out.println(produto);
                }
                RequestDispatcher dispatcher =
                        getServletContext().
                                getRequestDispatcher("/listarProdutos.jsp");
                request.setAttribute("produtos", produtos);
                dispatcher.forward(request, response);
            }else if(acao.equals("alterar")){
                c = cdao.getCarregarPorId(Integer.parseInt(idProduto));
                if(c.getIdProduto() > 0 ){
                    RequestDispatcher dispatcher =
                        getServletContext().
                            getRequestDispatcher("/cadastrarProduto.jsp");
                    request.setAttribute("produto", c);
                    dispatcher.forward(request, response);
                    
                }else{
                    mensagem = "Produto não encontrado na base dados!";
                }
                
            }else if(acao.equals("desativar")){
                c.setIdProduto(Integer.parseInt(idProduto));
                if(cdao.desativar(c)){
                    mensagem = "Produto desativado com sucesso!";
                    
                }else{
                    mensagem = "Falha ao desativar o produto!";
                }
          
               
            }else if(acao.equals("ativar")){
                c.setIdProduto(Integer.parseInt(idProduto));
                if(cdao.ativar(c)){
                     mensagem = "Produto ativado com sucesso!";
                }else{
                    mensagem = "Falha ao ativar o produto!";
                }
            
            
            }else{
                response.sendRedirect("/index.jsp");
            }
            
        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }
        
        out.println(
            "<script type='text/javascript'>" +
            "alert('" + mensagem + "');" +
            "location.href='gerenciarProduto?acao=listar';" +
            "</script>" );
      
    }

  
    @Override
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String idProduto = request.getParameter("idProduto");
        String nome = request.getParameter("nome");
        String descricao = request.getParameter("descricao");
        String estoque = request.getParameter("estoque");
        String preco = request.getParameter("preco");
        String status = request.getParameter("status");
        String mensagem = "";
        
        Produto p = new Produto();
        ProdutoDAO pdao = new ProdutoDAO();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            
            if(!idProduto.isEmpty()){
                p.setIdProduto(Integer.parseInt(idProduto));
            }
            
            if(nome.isEmpty() || nome.equals("")){
                request.setAttribute("msg", "Informe o nome do produto!");
                despacharRequisicao(request, response);
               
            }else{
                p.setNome(nome);
            }
            
            if(descricao.isEmpty() || descricao.equals("")){
                request.setAttribute("msg", "Informe a descricao do produto!");
                despacharRequisicao(request, response);
               
            }else{
                p.setDescricao(descricao);
            }
            
            if(estoque.isEmpty() || estoque.equals("")){
                request.setAttribute("msg", "Informe o estoque do produto!");
                despacharRequisicao(request, response);
               
            }else{
                p.setEstoque(Integer.parseInt(estoque));
            }
            
            if(preco.isEmpty() || preco.equals("")){
                request.setAttribute("msg", "Informe o preco do produto!");
                despacharRequisicao(request, response);
               
            }else{
                p.setPreco(Double.parseDouble(preco));
            }
            
            if(status.isEmpty() || status.equals("")){
                request.setAttribute("msg", "Informe o status do Produto!");
                despacharRequisicao(request, response);
            }else{
                p.setStatus(Integer.parseInt(status));
            }
            
            if(pdao.gravar(p)){
                mensagem = "Produto gravado com sucesso na base de dados!";
            }else{
                mensagem = "Falha ao gravar o produto na base de dados!";
            }
        } catch (SQLException e){
           mensagem = "Erro: " + e.getMessage();
           e.printStackTrace();
        }
        
        
        out.println(
                "<script type='text/javascript'>" +
                "alert('" + mensagem + "');" +
                "location.href='gerenciarProduto?acao=listar';" +
                "</script>"
        
        );
        
       
    }
    
    private void despacharRequisicao(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException{
        getServletContext().
                        getRequestDispatcher("/cadastrarProduto.jsp").
                            forward(request, response);
        
    }
}
