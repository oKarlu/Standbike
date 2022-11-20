package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import dao.ClienteDAO;
import java.io.FileOutputStream;
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
                if(GerenciarLogin.verificarPermissao(request, response)){
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
                }else{
                    mensagem = "Acesso Negado!";
                }
                
            }else if(acao.equals("alterar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
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
                }else{
                    mensagem = "Acesso Negado!";
                }
                
            }else if(acao.equals("desativar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    c.setIdCliente(Integer.parseInt(idCliente));
                    if(cdao.desativar(c)){
                        mensagem = "Cliente desativado com sucesso!";

                    }else{
                        mensagem = "Falha ao desativar o cliente!";
                    }
                }else{
                    mensagem = "Acesso Negado!";
                }
          
               
            }else if(acao.equals("ativar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    c.setIdCliente(Integer.parseInt(idCliente));
                    if(cdao.ativar(c)){
                        mensagem = "Cliente ativado com sucesso!";
                    }else{
                        mensagem = "Falha ao ativar o cliente!";
                    }
                }else{
                    mensagem = "Acesso Negado!";
                }
            
            
            }else if(acao.equals("report")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    gerarRelatorio(request, response);
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
            "location.href='gerenciarCliente?acao=listar';" +
            "</script>" );
      
    }
    
    protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
            Document documento = new Document();
            ClienteDAO cdao = new ClienteDAO();
            
            try{
                //tipo de documento
                response.setContentType("application/pdf");
                //nome do documento
                response.reset();
                response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
                //Cria o documento
                PdfWriter.getInstance(documento, response.getOutputStream());
                //Abrir o documento
                documento.open();
                documento.add(new Paragraph("Lista de clientes"));
                documento.add(new Paragraph(" "));
                //criar tabela
                PdfPTable tabela = new PdfPTable(3);
                PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
                PdfPCell col2 = new PdfPCell(new Paragraph("Telefone"));
                PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
                tabela.addCell(col1);
                tabela.addCell(col2);
                tabela.addCell(col3);
                //popular a tabela
                ArrayList<Cliente> lista = new ArrayList<>();
                lista = cdao.getLista();
                for(int i = 0; i < lista.size(); i++){
                    tabela.addCell(lista.get(i).getNome());
                    tabela.addCell(lista.get(i).getTelefone());
                    tabela.addCell(lista.get(i).getEmail());
                }
                documento.add(tabela);
                documento.close();

            }catch(Exception e){
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
                documento.close();
            }
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
