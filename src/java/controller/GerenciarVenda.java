/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.VendaDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.MaskFormatter;
import model.Produto;
import model.ProdutoRecibo;
import model.Recibo;
import model.Venda;
import model.VendaProduto;

/**
 *
 * @author Carlos Marinho
 */
@WebServlet(name = "GerenciarVenda", urlPatterns = {"/gerenciarVenda"})
public class GerenciarVenda extends HttpServlet {

    public GerenciarVenda(){
        super();
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
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String mensagem = "";
        String acao = request.getParameter("acao");
        try{
            Venda v = (Venda) session.getAttribute("venda");
            VendaDAO vDao = new VendaDAO();
            
            if(acao.equals("registrar")){
                if(vDao.registrar(v)){
                    mensagem = "Venda realizada com sucesso!";
                }else{
                    mensagem = "Falha ao registrar a venda!";
                }
            } else if (acao.equals("listar")){
                ArrayList<Venda> vendas = new ArrayList<>();
                    vendas = vDao.getLista();
                    for(Venda venda: vendas){
                        System.out.println(venda);
                    }
                    RequestDispatcher dispatcher =
                            getServletContext().
                                    getRequestDispatcher("/listarVendas.jsp");
                    request.setAttribute("vendas", vendas);
                    dispatcher.forward(request, response);
            } else if(acao.equals("report")){
                gerarRecibo(request, response);
            }
        }catch(SQLException e){
             out.print("Erro: " + e.getMessage());
             e.printStackTrace();
        }
        
        out.print(
            "<script type='text/javascript'>" +
            "alert('" + mensagem + "');" +
            "location.href='listarVendas.jsp';" +
            "</script>");
        
    }
    
     protected void gerarRecibo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
            int index = Integer.parseInt(request.getParameter("idVenda"));
            Document documento = new Document();
            Recibo recibo = new Recibo();
            VendaDAO vdao = new VendaDAO();
            
            String patternDate = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(patternDate); 
            
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
                
                recibo = vdao.recibo(index);
                documento.add(new Paragraph("Recibo - StandBike"));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Data da Venda: " + sdf.format(recibo.getVenda().getDataVenda())));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Nome do Vendedor: " + recibo.getVendedor().getNome()));
                documento.add(new Paragraph(" "));
                documento.add(new Paragraph("Nome do Cliente: " + recibo.getCliente().getNome()));
                documento.add(new Paragraph("Telefone: " + formatarString(recibo.getCliente().getTelefone(), "(##)#####-####")));
                documento.add(new Paragraph("CPF: " + formatarString(recibo.getCliente().getCpf(), "###.###.###-##")));
                
  
                documento.add(new Paragraph(" "));
                //criar tabela
                PdfPTable tabela = new PdfPTable(4);
                PdfPCell col1 = new PdfPCell(new Paragraph("Código"));
                PdfPCell col2 = new PdfPCell(new Paragraph("Produto"));
                PdfPCell col3 = new PdfPCell(new Paragraph("Quantidade"));
                PdfPCell col4 = new PdfPCell(new Paragraph("Preco"));
                tabela.addCell(col1);
                tabela.addCell(col2);
                tabela.addCell(col3);
                tabela.addCell(col4);
                //popular a tabela
                ArrayList<ProdutoRecibo> lista = new ArrayList<>();
                lista = vdao.produtoRecibo(index);
                for(int i = 0; i < lista.size(); i++){
                    tabela.addCell(String.valueOf(lista.get(i).getProduto().getIdProduto()));
                    tabela.addCell(lista.get(i).getProduto().getNome());
                    tabela.addCell(String.valueOf(lista.get(i).getVp().getQtd()));
                    tabela.addCell(String.valueOf(lista.get(i).getVp().getPrecoUnitario()));
                    
                }
                
                documento.add(tabela);
                documento.add(new Paragraph("Total: R$" + recibo.getVenda().getValorTotal()));
                documento.close();

            }catch(Exception e){
                System.out.println("Erro: " + e.getMessage());
                e.printStackTrace();
                documento.close();
            }
    }
     
    public static String formatarString(String texto, String mascara) throws ParseException {
        MaskFormatter mf = new MaskFormatter(mascara);
        mf.setValueContainsLiteralCharacters(false);
        return mf.valueToString(texto);
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
        response.setContentType("text/html; charset=UTF-8");
	PrintWriter out = response.getWriter();
	HttpSession session = request.getSession();
	String acao = request.getParameter("acao");
	System.out.println("Ac�o: " + acao);
				
	if(acao.equals("alterarQtd")) {
            try {
		Venda v = (Venda) session.getAttribute("venda");
		ArrayList<VendaProduto> carrinho = 
			(ArrayList<VendaProduto>)session.getAttribute("carrinho");
		String []qtd = request.getParameterValues("qtd");
		for(int i = 0; i < carrinho.size(); i++) {
                    carrinho.get(i).setQtd(Integer.parseInt(qtd[i]));
		}
		session.setAttribute("carrinho", carrinho);
				
		session.setAttribute("venda", v);
				
		response.sendRedirect("formFinalizarVenda.jsp");
				
		}catch(Exception e) {
                    out.print("Erro: " + e.getMessage());
                    e.printStackTrace();
		}
    }
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
