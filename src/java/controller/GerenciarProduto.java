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
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@WebServlet(name = "GerenciarProduto", urlPatterns = {"/gerenciarProduto"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5, //10MB
        maxFileSize = 1024 * 1024 * 1024, //1GB
        maxRequestSize = 1024 * 1024 * 50) //50GB
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
        
        Produto p = new Produto();
        ProdutoDAO pdao = new ProdutoDAO();
        
        try {
            if(acao.equals("listar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    ArrayList<Produto> produtos = new ArrayList<>();
                    produtos = pdao.getLista();
                    for(Produto produto: produtos){
                        System.out.println(produto);
                    }
                    RequestDispatcher dispatcher =
                            getServletContext().
                                    getRequestDispatcher("/listarProdutos.jsp");
                    request.setAttribute("produtos", produtos);
                    dispatcher.forward(request, response);
                }else{
                    mensagem = "Acesso Negado!";
                }
            }else if(acao.equals("alterar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    p = pdao.getCarregarPorId(Integer.parseInt(idProduto));
                    if(p.getIdProduto() > 0 ){
                        RequestDispatcher dispatcher =
                            getServletContext().
                                getRequestDispatcher("/cadastrarProduto.jsp");
                        request.setAttribute("produto", p);
                        dispatcher.forward(request, response);
                    
                    }else{
                        mensagem = "Produto não encontrado na base dados!";
                    }
                }else{
                    mensagem = "Acesso Negado!";
                }
                
                
            }else if(acao.equals("desativar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    p.setIdProduto(Integer.parseInt(idProduto));
                    if(pdao.desativar(p)){
                        mensagem = "Produto desativado com sucesso!";

                    }else{
                        mensagem = "Falha ao desativar o produto!";
                    }
                }else{
                    mensagem = "Acesso Negado!";
                }
               
            }else if(acao.equals("ativar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    p.setIdProduto(Integer.parseInt(idProduto));
                    if(pdao.ativar(p)){
                         mensagem = "Produto ativado com sucesso!";
                    }else{
                        mensagem = "Falha ao ativar o produto!";
                    }
                }else{
                    mensagem = "Acesso Negado!";
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
        String preco = request.getParameter("preco");
        String status = request.getParameter("status");
        
        Part parte = request.getPart("nomeArquivo");
        String fileName = extractFileName(parte);
        String savePath = "H:\\TCC\\Standbike\\web\\imagens_produto\\" + fileName;
        
        File fileSaveDir = new File(savePath);
        parte.write(savePath + File.separator);
        String filePath = savePath + File.separator + fileName;
        
        String mensagem = "";
        
        Produto p = new Produto();
        ProdutoDAO pdao = new ProdutoDAO();
        try {
            
            if(!idProduto.isEmpty()){
                p.setIdProduto(Integer.parseInt(idProduto));
            }
            
            if(nome.isEmpty() || nome.equals("")){
                request.setAttribute("msg", "Informe o nome do produto!");
                despacharRequisicao(request, response);
               
            }else{
                p.setNome(nome);
            }
            
            double novoPreco = 0;
            if(preco.isEmpty() || preco.equals("")){
                request.setAttribute("msg", "Informe o preco do produto!");
                despacharRequisicao(request, response);
               
            }else{
                novoPreco = Double.parseDouble(preco.replace(".", "").replace(",", "."));
            }
            
            if(status.isEmpty() || status.equals("")){
                request.setAttribute("msg", "Informe o status do produto!");
                despacharRequisicao(request, response);
            }else{
                p.setStatus(Integer.parseInt(status));
            }
            
            p.setPreco(novoPreco);
            p.setDescricao(descricao);
            p.setNomeArquivo(fileName);
            p.setCaminho(savePath);
 
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
    
    private String extractFileName(Part parte){
        String contentDisp = parte.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for(String s : items){
            if(s.trim().startsWith("filename")){
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
