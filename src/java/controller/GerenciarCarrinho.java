package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Produto;
import dao.ProdutoDAO;
import model.Venda;
import model.VendaProduto;


public class GerenciarCarrinho extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public GerenciarCarrinho() {
        super();
     
    }

	
	protected void doGet(HttpServletRequest request, 
		HttpServletResponse response) 
		throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html;charset=UTF-8");
		
		String acao = request.getParameter("acao");
		System.out.println("Ação:" + acao);
		
		String idProduto = request.getParameter("idProduto");
		System.out.println("idProduto: " + idProduto);
				
		HttpSession session = request.getSession();
		Venda v = (Venda)session.getAttribute("venda");
		 
		try {
			
			ProdutoDAO pdao = new ProdutoDAO();
			Produto produto = new Produto();
			produto = pdao.getCarregarPorId(
					Integer.parseInt(idProduto));
			ArrayList<VendaProduto> carrinho = 
					new ArrayList<VendaProduto>();
			if(acao.equals("add")) {
				if(session.getAttribute("carrinho") == null) {
					carrinho.add(new VendaProduto(pdao.getCarregarPorId(
							Integer.parseInt(idProduto)), 1));
					VendaProduto vp = new VendaProduto();
					vp.setProduto(produto);
					vp.setQtd(1);
					vp.setPrecoUnitario(produto.getPreco());
					carrinho.add(vp);
					
				
				}else{
					carrinho =
							(ArrayList<VendaProduto>) session.getAttribute("carrinho");
					int index = 
						isExisting(Integer.parseInt(
								request.getParameter("idProduto")), carrinho);
					if(index == -1) {
						carrinho.add(new VendaProduto(pdao.getCarregarPorId(
								Integer.parseInt(idProduto)), 1));
					}else {
						int quantidade = carrinho.get(index).getQtd() + 1;
						carrinho.get(index).setQtd(quantidade);
					}
					
				}
				v.setCarrinho(carrinho);
				session.setAttribute("carrinho", carrinho);
				session.setAttribute("venda", v);
				response.sendRedirect("formVenda.jsp?acao=c");
			} else if (acao.equals("del")){
                            int index = Integer.parseInt(request.getParameter("index"));
                            carrinho.remove(index);
                            v.setCarrinho(carrinho);
                            session.setAttribute("venda", v);
                            response.sendRedirect("formFinalizarVenda.jsp");
                        } 
                					
		} catch (SQLException e) {
			out.print("Erro: " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	private int isExisting(int id, ArrayList<VendaProduto> carrinho) {
		for (int i = 0; i < carrinho.size(); i++)
			if(carrinho.get(i).getProduto().getIdProduto() == id)
				return i;
			return -1;
	
	}


	protected void doPost(HttpServletRequest request, 
		HttpServletResponse response) 
		throws ServletException, IOException {
		
		
	}

}
