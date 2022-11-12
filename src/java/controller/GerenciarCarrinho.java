/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProdutoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Produto;
import model.Venda;
import model.VendaProduto;

/**
 *
 * @author Carlos Marinho
 */
//@WebServlet(name = "GerenciarCarrinho", urlPatterns = {"/gerenciarCarrinho.do"})
public class GerenciarCarrinho extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GerenciarCarrinho</title>");            
            out.println("</head>");
            out.println("<body>");
            HttpSession session = request.getSession();
            try{
                Venda v = (Venda) session.getAttribute("venda");
                ArrayList<VendaProduto> carrinho = v.getCarrinho();
                String acao = request.getParameter("acao");
                ProdutoDAO pDao = new ProdutoDAO();
                if(acao.equals("add")){
                    Produto p = new Produto();
                    int idProduto = Integer.parseInt(request.getParameter("idProduto"));
                    p = pDao.getCarregarPorId(idProduto);
                    int quantidade = Integer.parseInt(request.getParameter("quantidade"));
                    VendaProduto vp = new VendaProduto();
                    vp.setProduto(p);
                    vp.setQuantidade(quantidade);
                    vp.setValor(p.getPreco());
                    carrinho.add(vp);
                    v.setCarrinho(carrinho);
                    session.setAttribute("venda", v);
                    response.sendRedirect("formVenda.jsp?acao=c");
                }else if(acao.equals("del")){
                    int index = Integer.parseInt(request.getParameter("index"));
                    carrinho.remove(index);
                    v.setCarrinho(carrinho);
                    session.setAttribute("venda", v);
                    response.sendRedirect("finalizarVenda.jsp");
                }
                
            }catch(Exception e){
                out.print(e);
            }
            
            
            
            out.println("<h1>Servlet GerenciarCarrinho at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
