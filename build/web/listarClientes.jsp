<%@ page language="java" contentType="text/html; charset=UTF-8" 
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0",
              shrink-to-fit=no>
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="fonts/css/all.css" type="text/css">
        <link rel="stylesheet" href="css/menu.css" type="text/css">
        <link rel="stylesheet" href="css/styles.css" type="text/css">
        <link rel="stylesheet" href="datatables/css/dataTables.bootstrap4.min.css" type="text/css">
        <link rel="stylesheet" href="datatables/css/jquery.dataTables.min.css" type="text/css">
        <title>Listar Clientes</title>
    </head>
    <body>
        <div id="container-fluid">
            
            <div id="container-fluid header">
                <%@include file="template/banner.jsp" %>
            </div>
            <div id="container-fluid menu">
                <%@include file="template/menu.jsp" %>
            </div>
            <div id="conteudo" class="bg-background">
                <div class="h-100 justify-content-center align-items-center">
                    <div class="col-12">
                        <br><br><br><h3 class="text-center mt-3">Listagem de Clientes</h3>
                        <div class="col-sm-12" style="padding-bottom: 20px">
                            <a href="cadastrarCliente.jsp"
                                class="btn btn-primary btn-md"
                                role="button">Cadastrar Cliente&nbsp;
                                <i class="fa-solid fa-floppy-disk"></i>
                            </a>
                        </div>
                        <div class="table-responsive">
                            <table class="table table-hover table-bordered responsive" 
                                   id="listarClientes">
                                <thead class="bg-primary">
                                    <tr class="text-white">
                                        <th>Nome</th>
                                        <th>Endereco</th>
                                        <th>Email</th>
                                        <th>Telefone</th>
                                        <th>Status</th>
                                        <th>A????o</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${clientes}" var="c">
                                    <tr>
                                        <td>${c.nome}</td>
                                        <td>${c.endereco}</td>
                                        <td>${c.email}</td>
                                        <td>${c.telefone}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${c.status == 1}">
                                                    ativado
                                                </c:when>
                                                <c:otherwise>
                                                    desativado
                                                </c:otherwise>
                                            </c:choose>
                                            
                                        </td>
                                        <td>
                                            <script type="text/javascript">
                                                function confirmDesativar(id, nome){
                                                    if(confirm('Deseja desativar o cliente ' +
                                                       nome + '?')){
                                                       location.href="gerenciarCliente?acao=desativar&idCliente="+id;
                                                    }
                                                }
                                                
                                                function confirmAtivar(id, nome){
                                                    if(confirm('Deseja ativar o Cliente ' +
                                                       nome + '?')){
                                                       location.href="gerenciarCliente?acao=ativar&idCliente="+id;
                                                    }
                                                }
                                            </script>
                                            <a href="gerenciarCliente?acao=alterar&idCliente=${c.idCliente}"
                                               class="btn btn-primary btn-sm" role="button">
                                               Alterar&nbsp;<i class="fa-solid fa-pen-to-square"></i>
                                            </a>
                                            <c:choose>
                                                <c:when test="${c.status == 1}">
                                                    <button class="btn btn-danger btn-sm"
                                                        onclick="confirmDesativar('${c.idCliente}','${c.nome}')">
                                                        Desativar&nbsp;
                                                        <i class="fas fa-user fa-user-lock"></i>
                                                    </button>
                                                    
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-success btn-sm"
                                                        onclick="confirmAtivar('${c.idCliente}', '${c.nome}')">
                                                        Ativar&nbsp;
                                                        <i class="fa-solid fa-user-shield"></i>
                                                    </button>
                                                    
                                                </c:otherwise>
                                            </c:choose>
                                            <a href="formVenda.jsp?acao=novo&idCliente=${c.idCliente}" 
                                               class="btn btn-primary btn-sm" role="button">Realizar Venda&nbsp;
                                                <i class="fas fa-shopping-cart"></i>
                                            </a>    
                                        </td>
                                    </tr>
                                </c:forEach>    
                                </tbody>
                            </table>
                            
                            
                            
                        </div><!-- fim da div responsive -->
                    </div><!-- fim da div col-12 -->
                    
                    
                </div><!-- fim da div justify-content -->
                
            </div><!-- fim da div content -->
            
            
            
        </div>
            
        <!--JQuery.js -->
        <script src="js/jquery.min.js"></script>
        <!--Popper.js via cdn -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- Bootstrap.js -->
        <script src="js/bootstrap.min.js"></script>
        <script src="datatables/js/jquery.dataTables.min.js"></script>
        <script src="datatables/js/dataTables.bootstrap4.min.js"></script>
       
        <script type="text/javascript">
             $(document).ready(function () {
                $("#listarClientes").DataTable({
                    "bJQueryUI": true,
                    "lengthMenu": [[5, 10, 20, 25, -1], [5, 10, 20, 25, "Todos"]],
                        "oLanguage": {
                            "sProcessing": "Processando..",
                            "sLengthMenu": "Mostrar _MENU_ registros",
                            "sZeroRecords": "N??o foram encontrados resultados",
                            "sInfo": "Mostrando de _START_ at?? _END_ de _TOTAL_ registros",
                            "sInfoEmpty": "Mostrando de 0 at?? 0 de 0 registros",
                            "sInfoFiltered": "",
                            "sInfoPostFix": "",
                            "sSearch": "Pesquisar",
                            "sUrl": "",
                        "oPaginate": {
                            "sFirst": "Primeiro",
                            "sPrevious": "Anterior",
                            "sNext": "Pr??ximo",
                            "sLast": "??ltimo"
                            }
                        }
                    });
                }); 
            </script>    
    </body>
</html>
