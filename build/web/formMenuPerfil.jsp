
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
        <title>Gerenciar Perfil</title>
        <script type="text/javascript">
            function confirmarExclusao(idMenu,nome, idPerfil){
                if(confirm('Deseja realmente desvincular o menu ' + nome +'?')){
                    location.href='gerenciarMenuPerfil.do?acao=desvincular&idMenu='+idMenu+'&idPerfil='+idPerfil;
                }
            }
        </script>
    </head>
    <body>
        <%
            String mensagem = (String) request.getAttribute("mensagem");
            if(mensagem != null){
                out.println(
                   "<script type='text/javascript'>" +
                   "alert('" + mensagem + "');" +
                   "</script>");
            }
            
        %>
        <div id="container-fluid">
            
            <div id="container-fluid header">
                <%@include file="template/banner.jsp" %>
            </div>
            <div id="container-fluid menu">
                <%@include file="template/menu.jsp" %>
            </div>
            <div id="conteudo" class="bg-background">
                <form action="gerenciarMenuPerfil.do" method="POST" 
                      accept-charset="iso-8859-1,utf-8">
                    <h3 class="text-center mt-5"><br>Gerenciar Perfil</h3>
                    
                    <input type="hidden" id="idPerfil" name="idPerfil" 
                           value="${perfilv.idPerfil}">
                    
                    <div class="form-group row offset-md-2 mt-4">
                        <label for="idnome" 
                               class="col-md-2 form-label btn btn-primary btn-md">${perfilv.nome}</label>
                    </div>
                    <div class="form-group row offset-md-2 mt-4">
                        <label for="menu" 
                               class="col-md-2 form-label btn btn-primary btn-md">Menus</label>
                               <select name="idMenu" required="" id="idMenu" class="form-control">
                                   <option value="">Selecione o Menu</option>
                                   <c:forEach var="m" items="${perfilv.naoMenus}">
                                       <option value="${m.idMenu}">${m.nome}</option>
                                   </c:forEach>
                               </select>
                    </div>
                    
                    <div class="d-md-flex justify-content-md-end mr-3">
                        <button  class="btn btn-primary btn-md mr-2">
                            Vincular&nbsp;
                            <i class="fa-solid fa-floppy-disk"></i>
                        </button>
                        <a href="gerenciarPerfil?acao=listar"
                           class="btn btn-warning btn-md" role="button">
                            Voltar&nbsp;<i class="fa-solid fa-rotate-left"></i>
                            
                        </a>
                        
                    </div> 
                </form>
                    
                <div class="table-responsive">
                            <table class="table table-hover table bordered responsive" 
                                   id="listarMenus">
                                <thead class="bg-primary">
                                    <tr class="text-white">
                                        <th>Código</th>
                                        <th>Nome</th>
                                        <th>Link</th>
                                        <th>Ícone</th>
                                        <th>Exibir</th>
                                        <th>Desvincular</th>
                                    </tr>
                                </thead>
                                
                                <jsp:useBean class="dao.MenuDAO" id="mDao"/>
                                <tbody>
                                <c:forEach var="m" items="${perfilv.menus}" >
                                    <tr>
                                        <td>${m.idMenu}</td>
                                        <td>${m.nome}</td>
                                        <td>${m.link}</td>
                                        <td>${m.icone}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${m.exibir == 1}">
                                                    Sim
                                                </c:when>
                                                <c:otherwise>
                                                    Não
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <button class="btn btn-danger btn-sm"
                                                onclick="confirmarExlusao(${m.idMenu},'${m.nome}',${perfilv.idPerfil})">
                                                Deletar&nbsp;<i class="glyphicon glyphicon-trash"></i>
                                            </button>

                                        </td>
                                    </tr>
                                </c:forEach>    
                                </tbody>
                            </table>
                            
                            
                
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
                $("#listarMenus").dataTable({
                    "bJQueryUI": true,
                    "lengthMenu": [[5, 10, 20, 25, -1], [5, 10, 20, 25, "Todos"]],
                        "oLanguage": {
                            "sProcessing": "Processando..",
                            "sLengthMenu": "Mostrar _MENU_ registros",
                            "sZeroRecords": "Não foram encontrados resultados",
                            "sInfo": "Mostrando de _START_ até _END_ de _TOTAL_ registros",
                            "sInfoEmpty": "Mostrando de 0 até 0 de 0 registros",
                            "sInfoFiltered": "",
                            "sInfoPostFix": "",
                            "sSearch": "Pesquisar",
                            "sUrl": "",
                        "oPaginate": {
                            "sFirst": "Primeiro",
                            "sPrevious": "Anterior",
                            "sNext": "Próximo",
                            "sLast": "Último"
                            }
                        }
                    });
                }); 
            </script>      

            
       </div>
        
        <!--JQuery.js -->
        <script src="js/jquery.min.js"></script>
        <!--Popper.js via cdn -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- Bootstrap.js -->
        <script src="js/bootstrap.min.js"></script>
            
    </body>
</html>

