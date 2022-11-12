/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.PerfilDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Perfil;
import org.apache.tomcat.dbcp.dbcp2.SQLExceptionList;

/**
 *
 * @author Carlos Marinho
 */
//@WebServlet(name = "GerenciarMenuPerfil", urlPatterns = {"/gerenciarMenuPerfil.do"})
public class GerenciarMenuPerfil extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=utf-8");
        String mensagem = "";
        String idPerfil = request.getParameter("idPerfil");
        String acao = request.getParameter("acao");
        
        PerfilDAO pDao = new PerfilDAO();
        Perfil p = new Perfil();
        
        try{
            if(acao.equals("gerenciar")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    p = pDao.getCarregarPorId(Integer.parseInt(idPerfil));
                    if(p.getIdPerfil() > 0){
                        RequestDispatcher disp = getServletContext().getRequestDispatcher("/formMenuPerfil.jsp");
                        request.setAttribute("perfilv", p);
                        disp.forward(request, response);
                    }else{
                        mensagem = "Perfil não encontado";
                    }
                }else{
                    mensagem = "Acesso Negado!";
                }
            }
            if(acao.equals("desvincular")){
                String idMenu = request.getParameter("idMenu");
                if(idMenu.isEmpty() || idMenu.equals("")){
                    mensagem = "O campo Código do menu deverá ser selecionado";
                }else{
                    if(pDao.desvincular(Integer.parseInt(idMenu), Integer.parseInt(idPerfil))){
                        mensagem = "Desvinculado com sucesso!";
                    }else{
                        mensagem = "Erro ao desvincular";
                    }
                }
            }
            
            
        }catch(SQLException e){
            mensagem = "Erro " + e.getMessage();
            e.printStackTrace();
        }
        out.println(
            "<script type='text/javascript'>" +
            "alert('" + mensagem + "');" +
            "location.href='gerenciarMenuPerfil.do?acao=gerenciar&idPerfil="+idPerfil+"';" +
            "</script>" );
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        String mensagem = "";
        String idPerfil = request.getParameter("idPerfil");
        String idMenu = request.getParameter("idMenu");
        
        try{
            if(idPerfil.isEmpty()||idPerfil.equals("")){
                mensagem = "Informe o Perfil!";
            } else if(idMenu.isEmpty()||idMenu.equals("")){
                mensagem = "Informe o Menu!";
            } else{
                PerfilDAO pDao = new PerfilDAO();
                if(pDao.vincular(Integer.parseInt(idMenu), Integer.parseInt(idPerfil))){
                    mensagem = "Vinculado com sucesso!";
                } else{
                    mensagem = "Erro ao vincular o menu ao perfil";
                }
            }
            
        }catch(Exception e){
            out.print(e);
            mensagem = "Erro ao executar";
        }
        out.println(
            "<script type='text/javascript'>" +
            "alert('" + mensagem + "');" +
            "location.href='gerenciarMenuPerfil.do?acao=gerenciar&idPerfil="+idPerfil+"';" +
            "</script>" );

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
