
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
        <title>Cadastro de Venda</title>
    </head>
    <body>
        <div class="container-fluid">
            <%@include file="template/banner.jsp" %>
            <%@include file="template/menu.jsp" %>
            <<h3>Cadastrar Nova Venda</h3>
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
                String acao = request.getParameter("acao");
                ClienteDAO cDao = new ClienteDAO();
                if(acao.equals("novo")){
                    int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                    c = cDao.getCarregarPorId(idCliente);
                    v.setCliente(c);
                    v.setVendedor(ulogado);
                    v.setCarrinho(new ArrayList<>());
                    session.setAttribute("venda", v);
                }else{
                    v = (Venda)session.getAttribute("venda");
                }
                
            }catch(Exception e){
                out.print("Erro:" + e);
            }
            
        %>
        
            <br /><br />
            Vendedor: <%=v.getVendedor().getNome()%>
            <br/>
            Cliente: <%=v.getCliente().getNome()%>
            <br/>
            <h4>Catálogo: (<%=v.getCarrinho().size()%> itens no Carrinho</h4>
            <jsp:useBean class="dao.ProdutoDAO" id="produto"/>
            <c:forEach var="p" itens="${produto.lista}">
                <div id="prod${p.idProduto}">
                    <form action="gerenciarCarrinho.do" method="GET">
                        <input type="hidden" name="acao" value="add">
                        <input type="hidden" name="idProduto" value="${p.idProduto}"
                        ${p.nome}
                        <input type="number" name="quantidade" value="1" style="width: 40px">
                        <button class="btn btn-default">
                            <i class="glyphicon glyphicon">Comprar</i>
                        </button>
                            
                    </form>
                
                </div>
                
            </c:forEach>
            
            <a href="listarCliente.jsp" class="btn btn-warning">Cancelar</a>
            <a href="formFinalizarVenda.jsp" class="btn btn-sucess">Finalizar Venda</a>
        </div>
            
        <!-- a 
        <div id="container-fluid"> 
            
            <div id="header">
                <jsp:include page="template/banner.jsp"></jsp:include>
            </div>
            <div id="menu">
                <jsp:include page="template/menu.jsp"></jsp:include>
            </div>
            <div id="conteudo" class="bg-background">
                <form action="gerenciarPerfil" method="POST" 
                      accept-charset="iso-8859-1,utf-8">
                    <h3 class="text-center mt-5">Cadastrar Nova Venda</h3>
                    
                    <input type="hidden" id="idperfil" name="idPerfil" 
                           value="${perfil.idPerfil}">
                    
                    <div class="form-group row offset-md-2 mt-4">
                        <label for="idnome" 
                               class="col-md-2 form-label btn btn-primary btn-md">Nome</label>
                        <div class="col-md-6">
                            <input type="text" name="nome" id="idnome" 
                                   class="form-control" value="${perfil.nome}">
                            
                        </div>
                    </div>
                    <div class="form-group row offset-md-2 mt-3">
                        <label for="iddata" 
                               class="col-md-2 form-label btn btn-primary btn-md">Data de Cadastro</label>
                        <div class="col-md-6">
                            <input type="date" name="dataCadastro" id="iddata" 
                                   class="form-control" value="${perfil.dataCadastro}">
                            
                        </div>
                    </div>
                    <div class="form-group row offset-md-2 mt-3">
                        <label for="idstatus" 
                               class="col-md-2 form-label btn btn-primary btn-md">Status</label>
                        <div class="col-md-6">
                            <select id="idstatus" name="status"
                                    class="form-control-sm mt-3">
                                <option value="">Escolha uma Opção</option>
                                <option value="1"
                                    <c:if test="${perfil.status == 1}"> 
                                        selected=""
                                    </c:if>>Ativado</option>
                                <option value="0"
                                    <c:if test="${perfil.status == 0}">
                                        selected=""
                                    </c:if>>Desativado</option>
                                
                                
                            </select>
                            
                        </div>
                    </div>
                    <div class="d-md-flex justify-content-md-end mr-3">
                        <button  class="btn btn-primary btn-md mr-2">
                            Gravar&nbsp;
                            <i class="fa-solid fa-floppy-disk"></i>
                        </button>
                        <a href="gerenciarPerfil?acao=listar"
                           class="btn btn-warning btn-md" role="button">
                            Voltar&nbsp;<i class="fa-solid fa-rotate-left"></i>
                            
                        </a>
                        
                    </div>
                    
                    
                    
                </form>
                
            </div>
            
       </div>-->
        
        <!--JQuery.js -->
        <script src="js/jquery.min.js"></script>
        <!--Popper.js via cdn -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- Bootstrap.js -->
        <script src="js/bootstrap.min.js"></script> !>
            
    </body>
</html>
