<%@page import="model.VendaProduto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.ClienteDAO"%>
<%@page import="model.Cliente"%>
<%@page import="model.Venda"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        <script type="text/javascript">
            Function excluir(index, item){
                if(confirm("Tem certeza que deja excluir o item"+item+"?")){
                    window.open("genrenciarCarrinho.do?acao=del&index="+index,"_self");
                }
            }
            
        </script>
        
        <title>Finalizar Venda</title>
    </head>
    <body>
        <div class="container-fluid">
            <%@include file="template/banner.jsp" %>
            <%@include file="template/menu.jsp" %>
            <<h3>Finalizar Venda</h3>
            <%
            String msg = (String) request.getAttribute("msg");
            if(msg != null){
                out.println(
                   "<script type='text/javascript'>" +
                   "alert('" + msg + "');" +
                   "</script>");
            }
            
            Venda v = new Venda();
            Cliente c = new Cliente();
            try{
                
                    v = (Venda)session.getAttribute("venda");
                
                
            }catch(Exception e){
                out.print("Erro:" + e);
            }
            
        %>
        
            <br /><br />
            Vendedor: <%=v.getVendedor().getNome()%>
            <br />
            Cliente: <%=v.getCliente().getNome()%><br />
            
            <table class="table table-striped table-bordered table-hover display" id="listarVenda">
                <thead>
                    <tr>
                        <th>Item</th>
                        <th>Produto</th>
                        <th>Quantidade</th>
                        <th>Valor</th>
                        <th>Total</th>
                        <th> Remover</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        double total = 0;
                        int cont = 0;
                        for(VendaProduto vp:v.getCarrinho()){
                        
                        
                    %>
                    <tr>
                        <td align="center"><%=cont+1%></td>
                        <td><%=vp.getProduto().getNome()%>
                        <td><%=vp.getQuantidade()%></td>
                        <td>R$ <%=vp.getValor()%></td>
                        <td>R$ <%=vp.getQuantidade()*vp.getValor()%></td>
                        <td align="center">
                            <a href="#" onclick="excluir(<%=cont%>,<%=cont+1%>)" class="btn btn-danger">
                                <i class="glyphicon glyphicon-trash"></i>
                            </a>
                        </td>
                    </tr>
                    <%
                        total = total + (vp.getQuantidade()*vp.getValor());
                        cont++;
                        }
                    %>        
                </tbody>
            </table>
            
            
            <a href="listarCliente.jsp" class="btn btn-warning">Cancelar</a>
            <a href="formVenda.jsp?acao=c" class="btn btn-sucess">Continuar Vendendo</a>
            <a href="gerenciarVenda.do" class="btn btn-sucess">Confirmar Venda</a>
            
        </div>
                
                <script type="text/javascript" src="datatables/jquery.js"></script>    
                <script type="text/javascript" src="datatables/jquery.dataTables.min.js"></script>   
                <script type="text/javascript">
                    $(document).ready(function(){
                        $("#listarVenda").dataTable({
                            "bJQueryUI": true,
                            "oLanguage": {
                                "sProcessing":"Processando...",
                                "sLengthMenu": "Mostrar _MENU_ registros",
                                "sZeroRecords": "Não foram encontrados resultados",
                                "sInfo": "Mostrar de _START_ até _END_ de _TOTAL_ registros",
                                "sInfoEmpty":"Mostrar de 0 até 0 de 0 registros",
                                "sInfoFiltered": "",
                                "sInfoPostFix":"",
                                "sSearch": "Pesquisar",
                                "sUrl":"",
                                "oPaginate":{
                                    "sFirst":"Primeiro",
                                    "sPrevious":"Anterior",
                                    "sNext":"Próximo",
                                    "slast":"Último"
                                }
                            }
                        })
                    })  
                </script>   
    </body>
</html>
