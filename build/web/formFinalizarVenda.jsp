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
        <title>Finalizar Venda</title>
        <script type="text/javascript">
            function excluir(index, item){
                if(confirm("Tem certeza que deseja excluir o item " 
                        + item + "?"))
                    window.open("gerenciarCarrinho?acao=del&index="+index, "_self");
            }
            
            function alterarQuantidade(index, item){
                if(confirm("Tem certeza que deseja alterar a quantidade em "+qtd+" do item " 
                        + item + "?"))
                    window.open("gerenciarCarrinho?acao=alterar&index="+index+"?quantidade="+pegarQuantidade());
            }
            
            
            
        </script>
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
            <div id="conteudo" >
                <%
                    Venda v = new Venda();
                    Produto produto = new Produto();
                    try{
                        v = (Venda) session.getAttribute("venda");
                    }catch(Exception e){
                        out.print("Erro" + e.getMessage());
                        e.printStackTrace();
                    }
                
                %>
                <div>
                    <div class="h-100 justify-content-center align-items-center">
                        <!-- action="gerenciarVenda?acao=alterarQtd" -->
                        <form  method="POST">
                            <h3 class="text-center mt-5"><br>Finalizar Venda</h3>
                            <div class="form-group row offset-sm-3 col-md-6 justify-content-center">
                                <label for="idCliente" class="col-md-2 form-label btn btn-primary btn-md">Cliente</label>
                                <input type="text" class="form-control" name="cliente"
                                       id="idCliente" readonly value="<%=v.getCliente().getNome() %>">
                            </div>
                            <table class="table table-hover table-bordered table-active">
                                <thead class="bg-primary">
                                    <tr class="text-white">
                                        <th>Item</th>
                                        <th>Produto</th>
                                        <th>Quantidade</th>
                                        <th>Preço Unitário</th>
                                        <th>Subtotal</th>
                                        <th>Ação</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                    double total = 0;
                                    int cont = 0;
                                    for(VendaProduto vp: v.getCarrinho()){
                                        
                                    %>
                                        <tr>
                                            <td align="center"><%= cont +1 %></td>
                                            <td align="rounded">
                                                <div class="text-center">
                                                    <img class="rounded" width="64" height="64"
                                                        src="imagens_produto/<%= vp.getProduto().getNomeArquivo() %>">
                                                </div>
                                                <div class="text-center">
                                                    <%= vp.getProduto().getNome()%>
                                                </div>
                                                </td>
                                            <td>
                                                <input type="text" name="qtd" id="qtd" placeholder="<%= vp.getQtd() %>"
                                                       style="width:50px; height:25px" readonly/>
                                            </td>
                                            <td>
                                                R$&nbsp;<fmt:formatNumber pattern="#,##0.00" 
                                                        value="<%=vp.getProduto().getPreco() %>" />
                                            </td>
                                            <td>
                                                R$&nbsp;<fmt:formatNumber pattern="#,##0.00"
                                                value="<%= vp.getQtd() * vp.getProduto().getPreco() %>"/>
                                            </td>
                                            <td>
                                                <!--<a href="#" 
                                                    onclick="alterarQuantidade(<%= cont %>, <%= cont+1 %>)"
                                                    class="btn btn-primary btn-sm"
                                                    role="button">
                                                    Alterar quantidade&nbsp;
                                                    <i class="fas fa-edit"></i>
                                                 </a>-->
                                                <a href="#" 
                                                    onclick="excluir(<%= cont %>, <%= cont+1 %>)" 
                                                    class="btn btn-danger btn-sm"
                                                    role="button">
                                                    Excluir&nbsp;
                                                    <i class="fas fa-trash-alt"></i>
                                                 </a>
                                            </td>
                                        </tr>
                                </tbody>
                                <%
                                    if(v.getCarrinho().size() > 0){
                    			total = total + (vp.getQtd() * vp.getProduto().getPreco());
                                        cont++;
                           		v.setValorTotal(total);
                    			
                                    }else{
                    			total = total - (vp.getQtd() * vp.getProduto().getPreco());
                    			cont++;
                                        v.setValorTotal(total);
                                    }
                    			
                                }
                                %>
                            </table>
                            <div class="form-group row offset-sm-4 ">
                                <label for="valorTotal" class="col-md-2 text-left btn btn-sm btn-secondary">Preço Total</label>
                                <div class="col-md-3">
                                    <input type="text" class="form-control"
                                        name="valorTotal" id="valorTotal" readonly
                                        value="R$&nbsp;<fmt:formatNumber pattern="#,##0.00" value="<%= total %>"/>">
                                </div>
                            </div>
                            <div class="d-sm-flex justify-content-sm-end">
                                <a href="gerenciarCliente?acao=listar"
                                   class="btn btn-danger btn-md mr-2"
                                    role="button">
                                    Cancelar&nbsp;<i class="fas fa-stop-circle"></i>
                                </a>
                                <a href="formVenda.jsp?acao=continuar"
                                   class="btn btn-success btn-md mr-2"
                                   role="button">
                                   Continuar Comprando&nbsp;<i class="fas fa-cart-plus"></i>
                                </a>
                                <a href="gerenciarVenda?acao=registrar"
                                   class="btn btn-primary btn-md mr-2"
                                   role="button">
                                   Confirmar Venda&nbsp;<i class="fas fa-money-check"></i>
                                </a>
                                
                            </div>
                        </form>
                            
                    </div><!-- Div justify-content -->
                </div><!-- Div bg-background -->
            </div><!-- Div conteúdo -->
        </div><!-- Div container -->
        
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
