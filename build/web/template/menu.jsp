
<%@page import="controller.GerenciarLogin"%>
<%@page import="model.Usuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Usuario ulogado = GerenciarLogin.verificarAcesso(request, response);
    request.setAttribute("ulogado", ulogado);

%>
<div class="pull-right ml-3"><b>Bem Vindo, <c:if test="${ulogado!=null}">${ulogado.nome}|</b></c:if>
    <a href="gerenciarLogin.do">Sair</a>
</div>
<header class="mt-auto">
        <nav class="navbar navbar-expand-lg navbar-light">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

            <div class="collapse navbar-collapse" 
             id="navbarSupportedContent">
                <ul class="navbar-nav ml-md-auto">
                    <c:if test="${ulogado!=null && ulogado.perfil!=null}">
                        <c:forEach var="menu" items="${ulogado.perfil.menus}">
                            <c:if test="${menu.exibir==1}">
                                <li class="nav-item">
                                    <a class="nav-link" href="${menu.link}">${menu.nome}</a>
                                </li>
                            </c:if>
                        </c:forEach>

                    </c:if>
                </ul>

            </div>
        
        </nav>
</header>
    
