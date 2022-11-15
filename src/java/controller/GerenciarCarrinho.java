/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ProdutoDAO;
import java.io.IOException;
import java.sql.SQLException;
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
//@WebServlet(name = "GerenciarCarrinho", urlPatterns = {"/gerenciarCarrinho"})
public class GerenciarCarrinho extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public GerenciarCarrinho(){
        
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

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String mensagem = "";
        
        try{
            Venda v =(Venda) session.getAttribute("venda");
            ArrayList<VendaProduto> carrinho = v.getCarrinho();
            String acao = request.getParameter("acao");
            String idProduto = request.getParameter("idProduto");
            String qtd = request.getParameter("qtd");
            ProdutoDAO pDao = new ProdutoDAO();
            if(acao.equals("add")){
                if(GerenciarLogin.verificarPermissao(request, response)){
                    Produto produto = new Produto();
                    produto = pDao.getCarregarPorId(Integer.parseInt(idProduto));
                    VendaProduto vp = new VendaProduto();
                    vp.setProduto(produto);
                    vp.setQtd(Integer.parseInt(qtd));
                    //TODO Multiplicar o preco pela quantidade
                    vp.setPrecoUnitario(produto.getPreco());
                    carrinho.add(vp);
                    v.setCarrinho(carrinho);
                    session.setAttribute("venda", v);
                    response.sendRedirect("formVenda.jsp?acao=continuar");
                }else{
                    mensagem = "Acesso Negado!";
                }
            }
            
        }catch (SQLException e){
            out.print("Erro: " + e.getMessage());
            e.printStackTrace();
        }
        
        out.print(
            "<script type='text/javascript'>" +
            "alert('" + mensagem + "');" +
            "location.href='gerenciarMenu?acao=listar';" +
            "</script>");
        
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

/*private int isExisting(int id, ArrayList<VendaProduto> carrinho){
    for(int i = 0; i < carrinho.size(); i++)
        if(carrinho.get(i).getProduto().getIdProduto == id)
            return i;
        return -1;
}*/
