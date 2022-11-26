package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Perfil;
import dao.PerfilDAO;


@WebServlet(name = "GerenciarPerfil", urlPatterns = {"/gerenciarPerfil"})
public class GerenciarPerfil extends HttpServlet {

    
 
    @Override
    protected void doGet(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
        String idPerfil = request.getParameter("idPerfil");
        String mensagem = "";
        
        Perfil p = new Perfil();
        PerfilDAO pdao = new PerfilDAO();
        
        try {
            if(acao.equals("listar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    ArrayList<Perfil> perfis = new ArrayList<>();
                    perfis = pdao.getLista();
                    for(Perfil perfil: perfis){
                        System.out.println(perfil);
                    }
                    RequestDispatcher dispatcher =
                            getServletContext().
                                    getRequestDispatcher("/listarPerfis.jsp");
                    request.setAttribute("perfis", perfis);
                    dispatcher.forward(request, response);
                }else{
                    mensagem = "Acesso Negado!";
                }
            }else if(acao.equals("alterar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    p = pdao.getCarregarPorId(Integer.parseInt(idPerfil));
                    if(p.getIdPerfil() > 0 ){
                        RequestDispatcher dispatcher =
                            getServletContext().
                                getRequestDispatcher("/cadastrarPerfil.jsp");
                        request.setAttribute("perfil", p);
                        dispatcher.forward(request, response);

                    }else{
                        mensagem = "Perfil não encontrado na base dados!";
                    }
                }else{
                    mensagem = "Acesso Negado!";
                }
                
            }else if(acao.equals("deletar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    p.setIdPerfil(Integer.parseInt(idPerfil));
                    if(pdao.deletar(p)){
                        mensagem = "Perfil deletado com sucesso!";

                    }else{
                        mensagem = "Falha ao deletar o perfil!";
                    }
                }else{
                    mensagem = "Acesso Negado!";
                }     
            
            }else if(acao.equals("desativar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    p.setIdPerfil(Integer.parseInt(idPerfil));
                    if(pdao.desativar(p)){
                        mensagem = "Perfil desativado com sucesso!";
                    
                    }else{
                        mensagem = "Falha ao desativar o perfil!";
                    }
                 }else{
                    mensagem = "Acesso não autorizado!";
                 }
                 
            }else if(acao.equals("ativar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    p.setIdPerfil(Integer.parseInt(idPerfil));
                    if(pdao.ativar(p)){
                        mensagem = "Perfil ativado com sucesso!";
                    }else{
                        mensagem = "Falha ao ativar o perfil!";
                    }
                }else{
                     mensagem = "Acesso não autorizado!";
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
            "location.href='gerenciarPerfil?acao=listar';" +
            "</script>" );
      
    }

  
    @Override
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String idPerfil = request.getParameter("idPerfil");
        String nome = request.getParameter("nome");
        String status = request.getParameter("status");
        String mensagem = "";
        
        Perfil p = new Perfil();
        PerfilDAO pdao = new PerfilDAO();
        try {
            
            if(!idPerfil.isEmpty()){
                p.setIdPerfil(Integer.parseInt(idPerfil));
            }
            
            if(nome.isEmpty() || nome.equals("")){
                request.setAttribute("msg", "Informe o nome do perfil!");
                despacharRequisicao(request, response);
               
            }else{
                p.setNome(nome);
            }
            
            if(status.isEmpty() || status.equals("")){
                request.setAttribute("msg", "Informe o status do Cliente!");
                despacharRequisicao(request, response);
                throw new SQLException("Status nulo");
                
            }else{
                p.setStatus(Integer.parseInt(status));
            }
            
            if(pdao.gravar(p)){
                mensagem = "Perfil salvo na base de dados";
            }
        } catch (SQLException e){
            mensagem = "Erro: " + e.getMessage();
        }
        
        out.println(
                "<script type='text/javascript'>" +
                "alert('" + mensagem + "');" +
                "location.href='gerenciarPerfil?acao=listar';" +
                "</script>"
        
        );
        
       
    }
    
    private void despacharRequisicao(HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException{
        getServletContext().
                        getRequestDispatcher("/cadastrarPerfil.jsp").
                            forward(request, response);
        
    }



}
