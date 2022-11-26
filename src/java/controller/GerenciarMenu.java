
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Menu;
import dao.MenuDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;


@WebServlet(name = "GerenciarMenu", urlPatterns = {"/gerenciarMenu"})
public class GerenciarMenu extends HttpServlet {

   
  
    @Override
    protected void doGet(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html; UTF-8");
        String acao = request.getParameter("acao");
        String idMenu = request.getParameter("idMenu");
        String mensagem = "";
        
        Menu m = new Menu();
        MenuDAO mdao = new MenuDAO();
        
        try {
               
            if(acao.equals("listar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    ArrayList<Menu> menus = new ArrayList<>();
                    menus = mdao.getLista();
                    for(Menu menu : menus ){
                        System.out.println(menu);
                    }
                    RequestDispatcher dispatcher =  
                            getServletContext().
                                    getRequestDispatcher("/listarMenus.jsp");
                    request.setAttribute("menus", menus);
                    dispatcher.forward(request, response);
            }else{
                mensagem = "Acesso Negado!";
            }
                
            }else if(acao.equals("alterar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    m = mdao.getCarregarPorId(Integer.parseInt(idMenu));
                    if(m.getIdMenu() > 0){
                        RequestDispatcher dispatcher =
                                getServletContext().
                                        getRequestDispatcher("/cadastrarMenu.jsp");
                        request.setAttribute("menu", m);
                        dispatcher.forward(request, response);
                    }else{
                        mensagem = "Menu não encontrado na base de dados!";
                    }
                }else{
                    mensagem = "Acesso Negado!";
                }
                
            }else if(acao.equals("deletar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    m.setIdMenu(Integer.parseInt(idMenu));
                    if(mdao.deletar(m)){
                        mensagem = "Menu deletado com sucesso na base de dados!";

                    }else{
                        mensagem = "Falha ao deletar o menu da base de dados!";
                    }
                }else{
                    mensagem = "Acesso Negado!";
                }
                        
            } else if(acao.equals("desativar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    m.setIdMenu(Integer.parseInt(idMenu));
                    if(mdao.desativar(m)){
                        mensagem = "Perfil desativado com sucesso!";
                    
                    }else{
                        mensagem = "Falha ao desativar o perfil!";
                    }
                 }else{
                    mensagem = "Acesso não autorizado!";
                 }
                 
            }else if(acao.equals("ativar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    m.setIdMenu(Integer.parseInt(idMenu));
                    if(mdao.ativar(m)){
                        mensagem = "Perfil ativado com sucesso!";
                    }else{
                        mensagem = "Falha ao ativar o perfil!";
                    }
                }else{
                     mensagem = "Acesso não autorizado!";
                }
               
            
            
            }
        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }
        
        out.print(
            "<script type='text/javascript'>" +
            "alert('" + mensagem + "');" +
            "location.href='gerenciarMenu?acao=listar';" +
            "</script>");
        
      

    }

   
    @Override
    protected void doPost(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String idMenu = request.getParameter("idMenu");
        String nome = request.getParameter("nome");
        String link = request.getParameter("link");
        String status = request.getParameter("status");
        String exibir = request.getParameter("exibir");
        String mensagem = "";
        
        Menu m = new Menu();
        
        try{
            if(!idMenu.isEmpty()){
                m.setIdMenu(Integer.parseInt(idMenu));

            }


            if(nome.equals("") || nome.isEmpty()){
                request.setAttribute("msg", "Informe o valor para o campo menu!");
                despacharRequisicao(request, response);
            }else{
                m.setNome(nome);
            }

            if(link.equals("") || link.isEmpty()){
                request.setAttribute("msg", "Informe o valor para o campo link do menu!");
                despacharRequisicao(request, response);
            }else{
                 m.setLink(link);
            }

            if(exibir.equals("") || exibir.isEmpty()){
                request.setAttribute("msg", "Informe o valor para o campo exibir!");
                despacharRequisicao(request, response);

            }else{
                try{
                    m.setExibir(Integer.parseInt(exibir));
                }catch(NumberFormatException e){
                    mensagem = "Erro: " + e.getMessage();
                    e.printStackTrace();
                }
            }

            if(status.isEmpty() || status.equals("")){
                request.setAttribute("msg", "Informe o status do Cliente!");
                despacharRequisicao(request, response);
                throw new SQLException("Status nulo");
            }else{
                m.setStatus(Integer.parseInt(status));
            }
        }catch(SQLException e){
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }
        
       
        
        
        MenuDAO mdao = new MenuDAO();
        try {
            if(mdao.gravar(m)){
                mensagem = "Menu cadastrado com sucesso na base de dados!";
            
            }else{
                mensagem = "Falha ao cadastrar o menu na base dados!";
            }
            
        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }
        
        out.println(
            "<script type='text/javascript'>" +
            "alert('" + mensagem + "');" +
            "location.href='gerenciarMenu?acao=listar';" +
            "</script>");
        
    }
    
    private void despacharRequisicao(HttpServletRequest request, 
        HttpServletResponse response)
        throws ServletException, IOException{    
            getServletContext().
                    getRequestDispatcher("/cadastrarMenu.jsp").
                            forward(request, response);
        
        
    }

  

}
