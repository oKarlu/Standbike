package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import dao.ClienteDAO;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "GerenciarCliente", urlPatterns = {"/gerenciarCliente"})
public class GerenciarCliente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idCliente = request.getParameter("idCliente");
        String mensagem = "";
        
        Cliente c = new Cliente();
        ClienteDAO cdao = new ClienteDAO();
        
        try {
            if(acao.equals("listar")){
                ArrayList<Cliente> clientes = new ArrayList<>();
                clientes = cdao.getLista();
                for(Cliente cliente: clientes){
                    System.out.println(cliente);
                }
                RequestDispatcher dispatcher =
                        getServletContext().
                                getRequestDispatcher("/listarClientes.jsp");
                request.setAttribute("clientes", clientes);
                dispatcher.forward(request, response);
            }else if(acao.equals("alterar")){
                c = cdao.getCarregarPorId(Integer.parseInt(idCliente));
                if(c.getIdCliente() > 0 ){
                    RequestDispatcher dispatcher =
                        getServletContext().
                            getRequestDispatcher("/cadastrarCliente.jsp");
                    request.setAttribute("cliente", c);
                    dispatcher.forward(request, response);
                    
                }else{
                    mensagem = "Cliente não encontrado na base dados!";
                }
                
            }else if(acao.equals("desativar")){
                c.setIdCliente(Integer.parseInt(idCliente));
                if(cdao.desativar(c)){
                    mensagem = "Cliente desativado com sucesso!";
                    
                }else{
                    mensagem = "Falha ao desativar o cliente!";
                }
          
               
            }else if(acao.equals("ativar")){
                c.setIdCliente(Integer.parseInt(idCliente));
                if(cdao.ativar(c)){
                     mensagem = "Cliente ativado com sucesso!";
                }else{
                    mensagem = "Falha ao ativar o cliente!";
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
            "location.href='gerenciarCliente?acao=listar';" +
            "</script>" );
      
    }

  
    @Override
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String idCliente = request.getParameter("idCliente");
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String endereco = request.getParameter("endereco");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String dataCadastro = request.getParameter("dataCadastro");
        String status = request.getParameter("status");
        String mensagem = "";
        
        Cliente c = new Cliente();
        ClienteDAO cdao = new ClienteDAO();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            
            if(!idCliente.isEmpty()){
                c.setIdCliente(Integer.parseInt(idCliente));
            }
            
            if(nome.isEmpty() || nome.equals("")){
                request.setAttribute("msg", "Informe o nome do cliente!");
                despacharRequisicao(request, response);
               
            }else{
                c.setNome(nome);
            }
            
            if(cpf.isEmpty() || cpf.equals("")){
                request.setAttribute("msg", "Informe o cpf do cliente!");
                despacharRequisicao(request, response);
               
            }else{
                c.setCpf(cpf);
            }
            
            if(endereco.isEmpty() || endereco.equals("")){
                request.setAttribute("msg", "Informe o endereco do cliente!");
                despacharRequisicao(request, response);
               
            }else{
                c.setEndereco(endereco);
            }
            
            if(email.isEmpty() || email.equals("")){
                request.setAttribute("msg", "Informe o email do cliente!");
                despacharRequisicao(request, response);
               
            }else{
                c.setEmail(email);
            }
            
            if(telefone.isEmpty() || telefone.equals("")){
                request.setAttribute("msg", "Informe o telefone do cliente!");
                despacharRequisicao(request, response);
               
            }else{
                c.setTelefone(telefone);
            }
            
            if(dataCadastro.isEmpty() || dataCadastro.equals("")){
                request.setAttribute("msg", "Informe a data do cadastro!");
                despacharRequisicao(request, response);
            }else{
                c.setDataCadastro(df.parse(dataCadastro));
            }
            
            if(status.isEmpty() || status.equals("")){
                request.setAttribute("msg", "Informe o status do Cliente!");
                despacharRequisicao(request, response);
            }else{
                c.setStatus(Integer.parseInt(status));
            }
            
            if(cdao.gravar(c)){
                mensagem = "Cliente gravado com sucesso na base de dados!";
            }else{
                mensagem = "Falha ao gravar o cliente na base de dados!";
            }
        } catch (ParseException pe) {
            mensagem = "Erro: " + pe.getMessage();
        } catch (SQLException e){
           mensagem = "Erro: " + e.getMessage();
           e.printStackTrace();
        }
        
        
        out.println(
                "<script type='text/javascript'>" +
                "alert('" + mensagem + "');" +
                "location.href='gerenciarCliente?acao=listar';" +
                "</script>"
        
        );
        
       
    }
    
    private void despacharRequisicao(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException{
        getServletContext().
                        getRequestDispatcher("/cadastrarCliente.jsp").
                            forward(request, response);
        
    }
}
