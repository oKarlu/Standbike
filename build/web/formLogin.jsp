<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
    
    <div class="card" id="telaLogin">
        <%
        String mensagem = (String)request.getSession().getAttribute("mensagem");
        if(mensagem != null){
            request.getSession().removeAttribute("mensagem");
        
    
        %>
        <div class="alert alert-info" ><%=mensagem%></div>
        <%
            }
        %>
        <div class="card-body">
            <form action="gerenciarLogin.do" method="POST">
                <div class="form-group">
                  <label for="login" class="control-label">LOGIN</label>
                  <input type="text" name="login" class="form-control" id="login" aria-describedby="emailHelp" placeholder="INFORME SEU LOGIN">
                </div>
                <div class="form-group">
                  <label for="senha" class="control-label">SENHA</label>
                  <input type="password" name="senha" class="form-control" id="senha" placeholder="INFORME SUA SENHA">
                </div>
                    <button type="submit" class="btn btn-outline-success btn-block">ENTRAR</button>
            </form>
        </div>
    </div>
    </body>
</html>
