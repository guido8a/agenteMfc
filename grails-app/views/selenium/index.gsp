<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Panel de Control - Selenium Firefox</title>
    <!-- Agregamos Bootstrap vía CDN para que se vea moderno y limpio -->
%{--    <link rel="stylesheet" href="https://bootstrapcdn.com">--}%
    <asset:javascript src="jquery-3.3.1.min.js"/>
    <asset:javascript src="bootstrap-3.3.2/bootstrap.js"/>
</head>
<body>
<div class="container mt-5">
    <div class="card shadow">
        <div class="card-header bg-primary text-white">
            <h3 class="mb-0">🤖 Automatización con Selenium</h3>
        </div>
%{--        <a href="#" class="btn btn-success" id="btnAceptar">Aceptar</a>--}%
    </div>
</div>

<div class="container mt-5" style="text-align: center">
    <div class="mt-2">
        <g:textField name="numero" id="numeroOferta" class="form-control" value="${1}"/>
    </div>
    <div class="mt-2">
        <a href="#" class="btn btn-success" id="btnAceptar">Aceptar</a>
    </div>
</div>

<script type="text/javascript">
    $("#btnAceptar").click(function () {
        var no = $("#numeroOferta").val();
        location.href="${createLink(controller: 'selenium', action: 'iniciar')}?id=" + no
    })
</script>

</body>
</html>
