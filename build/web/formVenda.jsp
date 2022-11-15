<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@page import="model.Usuario"%>
<%@page import="model.Cliente"%>
<%@page import="model.Venda"%>
<%@page import="model.VendaProduto"%>
<%@page import="model.Produto"%>
<%@page import="dao.ClienteDAO"%>
<%@page import="dao.ProdutoDAO"%>
<%@page import="controller.GerenciarLogin"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0,
              shrink-to-fit=no">
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="fonts/css/all.css" type="text/css">
        <link rel="stylesheet" href="css/menu.css" type="text/css">
        <link rel="stylesheet" href="css/styles.css" type="text/css">
        <title>Cadastro de Venda</title>
    </head>
    <body>
        <%
        //Http 1.1
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        //HTTP 1.0
        response.setHeader("Pragma", "no-cache");
        //Proxie
        response.setHeader("Expires", "0");
        %>
        <div id="container-fluid">
            
            <div id="container-fluid header">
                <%@include file="template/banner.jsp" %>
            </div>
            <div id="container-fluid menu">
                <%@include file="template/menu.jsp" %>
            </div>
            <div id="conteudo" class="bg-background">
                <%
                   Venda v = new Venda();
                   Cliente c = new Cliente();
                   ulogado = GerenciarLogin.verificarAcesso(request, response);
                   request.setAttribute("ulogado", ulogado);
                   try{
                        String acao = request.getParameter("acao");
                        ClienteDAO cDao = new ClienteDAO();
                        if(acao.equals("novo")){
                            int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                            c = cDao.getCarregarPorId(idCliente);
                            v.setCliente(c);
                            v.setVendedor(ulogado);
                            v.setCarrinho(new ArrayList<VendaProduto>());
                            session.setAttribute("venda", v);
                        }else{
                            v = (Venda)session.getAttribute("venda");
                        }
                    
                        
                    }catch(SQLException e){
                        out.print("Erro: " + e.getMessage());
                        e.printStackTrace();
                   }

                %>
                <div class="h-100 justify-content-center align-items-center">
                    <div class="col-12">
                        <div>
                            <h4 class="text-center mt-1">
                                <br><br><br>Carrinho(<%= v.getCarrinho().size() %>)
                            </h4>
                        </div>
                        <div class="table-responsive">
                            <div class="d-sm-flex justify-content-sm-end">
                                <a href="gerenciarCliente?acao=listar"
                                    class="btn btn-sm btn-danger mr-2"
                                    roll="button">
                                    Cancelar&nbsp;<i class="fas fa-stop-circle"></i>
                                </a>
                                <a href="formFinalizarVenda.jsp"
                                    class="btn btn-sm btn-primary mr-2"
                                    roll="button">
                                    Finalizar&nbsp;<i class="fas fa-cash-register"></i>
                                </a>
                            </div>
                            <table class="table">
                                <thead>
                                    <tr>
                                        <%
                                           ProdutoDAO pDao = new ProdutoDAO();
                                           ArrayList<Produto> lista = pDao.getLista();
                                           int salto = 0;
                                           for(Produto produto : lista){
                                           
                                        %>
                                       <th>
                                           <div>
                                               <img class="center" width="180" height="140"
                                                    src="imagens_produto/<%= produto.getNomeArquivo()%>">
                                           </div>
                                           <div>
                                               <%=produto.getNome() %>
                                           </div>
                                           <div>
                                               R$&nbsp;<fmt:formatNumber pattern="#,##0.00" 
                                                        value="<%= produto.getPreco()%>" />
                                           </div>
                                           <div>
                                               <input type="hidden" name="idProduto"/>
                                               <input type="numner" name="qtd" value="1"
                                                      size="4" maxlength="3" max="<%=produto.getEstoque()%>" readonly/><!-- readonly  -->
                                           </div>
                                           <div>
                                               <a href="gerenciarCarrinho?acao=add&idProduto=<%=produto.getIdProduto()%>&qtd=1"
                                                  class="btn btn-primary btn-sm" role="button">
                                                   Adicionar&nbsp;<i class="fas fa-cart-plus"></i>
                                               </a>
                                           </div>
                                        <% salto++;
                                            if(salto == 3){
                                        %>
                                        </th>
                                    </tr>
                                        <%
                                            salto = 0;
                                            }
                                        }
                                        %>
                                        
                                </thead>
                            </table>
                                        
                    
                        </div>
                
                    </div>
                </div>
  
            </div>
        </div>
        
        <!--JQuery.js -->
        <script src="js/jquery.min.js"></script>
        <!--Popper.js via cdn -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- Bootstrap.js -->
        <script src="js/bootstrap.min.js"></script>
        <script src="datatables/js/jquery.dataTables.min.js"></script>
        <script src="datatables/js/dataTables.bootstrap4.min.js"></script>
       
              
    </body>
</html>
