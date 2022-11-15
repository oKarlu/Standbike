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
            <div id="conteudo">
                <%
                    Venda v = new Venda();
                    Cliente c = new Cliente();
                    try{
                        v = (Venda) session.getAttribute("venda");
                    }catch(Exception e){
                        out.print("Erro" + e.getMessage());
                        e.printStackTrace();
                    }
                
                %>
                <div class="bg-background">
                    <div class="h-100 justify-content-center align-items-center">
                        <div class="col-12">
                        </div>
                        <form action="gerenciarVenda?acao=alterarQtd" method="post">
                            <table class="table table-hover table-striped table-bordered table-active">
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

                                </tbody>
                            </table>
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
