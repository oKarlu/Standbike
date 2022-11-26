
<%@ page language="java" contentType="text/html; charset=UTF-8" 
         pageEncoding="UTF-8"%>
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
        <title>Cadastro de Perfil</title>
    </head>
    <body>
        <%
        //Http 1.1
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        //HTTP 1.0
        response.setHeader("Pragma", "no-cache");
        //Proxie
        response.setHeader("Expires", "0");
        
        if(session.getAttribute("ulogado") == null){
            response.sendRedirect("formLogin.jsp");
        }
        
        
        
        %>
        <%

            String msg = (String) request.getAttribute("msg");
            if(msg != null){
                 out.println(
                   "<script type='text/javascript'>" +
                   "alert('" + msg + "');" +
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
                <form action="gerenciarMenu" method="POST" 
                      accept-charset="iso-8859-1,utf-8">
                    <h3 class="text-center mt-5"><br>Cadastro de Menus</h3>
                    
                    <input type="hidden" id="idmenu" name="idMenu" 
                           value="${menu.idMenu}">
                    
                    <div class="form-group row offset-md-3 mt-4">
                        <label for="idnome" 
                               class="col-md-1 form-label btn btn-primary btn-md">Nome</label>
                        <div class="col-md-6">
                            <input type="text" name="nome" id="idnome" 
                                   class="form-control" value="${menu.nome}">
                            
                        </div>
                    </div>
                    <div class="form-group row offset-md-3 mt-4">
                        <label for="idlink" class="col-md-1 form-label btn btn-primary btn-md">Link</label>
                        <div class="col-md-6">
                            <input type="text" name="link" id="idlink" 
                                   class="form-control" value="${menu.link}">
                            
                        </div>
                    </div>
                    <div class="form-group row offset-md-3 mt-3">
                        <label for="idexibir" 
                               class="col-md-1 form-label mt-2 btn btn-primary btn-md">Exibir</label>
                        <div class="col-md-6">
                            <select id="idexibir" name="exibir"
                                    class="form-control-sm mt-2">
                                <option value=""selected>Escolha uma opção</option>
                            <c:choose>
                                <c:when test="${menu.exibir == 1}">
                                    <option value="${menu.exibir}" selected>
                                        Sim
                                    </option>
                                    <option value="0">
                                        Não
                                    </option>
                                </c:when>
                                <c:otherwise>
                                    <option value="1">
                                        Sim
                                    </option>
                                    <option value="${menu.exibir == 0}" selected>
                                        Não
                                    </option>
                                </c:otherwise>
                             </c:choose>
                            </select>
                        </div>
                    </div>
                    <div class="form-group row offset-md-3 mt-3">
                        <label for="idstatus" class="col-md-1 form-label btn btn-primary btn-md mt-2">Status</label>
                        <div class="col-md-6">
                            <select id="idstatus" name="status"
                                class="form-control-sm mt-2">
                                <option value="" selected>
                                    Escolha uma opção
                                </option>
                                <c:choose>
                                    <c:when test="${menu.status == 1}">
                                        <option value="${menu.status}" selected>
                                            Ativado
                                        </option>
                                        <option value="0">
                                            Desativado
                                        </option>
                                        
                                    </c:when>
                                    <c:otherwise>
                                        <option value="1">
                                            Ativado
                                        </option>
                                        <option value="${menu.status == 0}" selected>
                                            Desativado
                                        </option>
                                        
                                    </c:otherwise>
                                
                                </c:choose>
                                
                                
                            </select>
                            
                        </div>
                    </div>               
                                   
                    <div class="d-md-flex justify-content-md-end mr-3">
                        <button  class="btn btn-primary btn-md mr-2">
                            Gravar&nbsp;
                            <i class="fa-solid fa-floppy-disk"></i>
                        </button>
                        <a href="gerenciarMenu?acao=listar"
                           class="btn btn-warning btn-md" role="button">
                            Voltar&nbsp;<i class="fa-solid fa-rotate-left"></i>
                            
                        </a>
                        
                    </div>
                    
                    
                    
                </form>
                
            </div>
            
       </div>
        
        <!--JQuery.js -->
        <script src="js/jquery.min.js"></script>
        <!--Popper.js via cdn -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- Bootstrap.js -->
        <script src="js/bootstrap.min.js"></script>
            
    </body>
</html>
