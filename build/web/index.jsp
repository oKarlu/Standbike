
<%@page import="controller.GerenciarLogin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<%
    GerenciarLogin.verificarAcesso(request, response);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0,
              maximum-scale=1, user-scalable=no shrink-to-fit=no">
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="fonts/css/all.css" type="text/css">
        <link rel="stylesheet" href="css/menu.css" type="text/css">
        <link rel="stylesheet" href="css/styles.css" type="text/css">
        <title>Home</title>
    </head>
    <body id="fundo">
            <div id="container-fluid header">
                <%@include file="template/banner.jsp" %>
            </div>
            <div id="container-fluid menu">
                <%@include file="template/menu.jsp" %>
            </div>
            <div style="" id="telaindex">
                <h1 class="text-center mt-3">Missão</h1>
                    <br>
                    <h5 style="padding-right: 1cm;
                        padding-left: 1cm;">
                      Incentivar o uso da bicicleta como um meio de transporte saudável que não polui, contribuindo para a sustentabilidade. 
                      Prestar serviços de alta qualidade com agilidade e confiança, atendendo a todos os tipos de clientes com competência, ética e dedicação, buscando sempre a satisfação.
                    </h5>
                    <br>
                    <h1 class="text-center mt-3">Visão</h1>
                    <br>
                    <h5 style="padding-right: 1cm;
                        padding-left: 1cm;">
                      Ser uma das líderes no segmento de bicicletas, visando sempre honestidade, integridade, qualidade e eficiência; além de fixar nossa marca na mente dos consumidores 
                      como símbolo de seriedade e confiança dentro do determinado ramo.
                    </h5>
                    <br>
                    <h1 class="text-center mt-3">Valores</h1>
                    <br>
                    <h5 style="padding-right: 1cm;
                        padding-left: 1cm;">
                      1- Superar as expectativas de nossos clientes;<br>

                      2- Prestar serviços com idoneidade e comprometimento;<br>

                      3- Trabalhar em equipe com agilidade e responsabilidade, aprendendo com o diferencial de cada um;<br>

                      4- Buscar sempre a qualidade em nossos produtos e serviços, inovando constantemente.
                    </h5>
            </div>
        
        <!--JQuery.js -->
        <script src="js/jquery.min.js"></script>
        <!--Popper.js via cdn -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- Bootstrap.js -->
        <script src="js/bootstrap.min.js"></script>
            
    </body>
</html>
