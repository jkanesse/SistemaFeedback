<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Detalhes do Produto 2</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <h1>Detalhes do Produto 2</h1>
    <p>Descrição do Produto 2...</p>

    <h2>Feedbacks</h2>
    <ul>
        <c:forEach var="feedback" items="${feedbacks}">
            <li>${feedback.usuario}: ${feedback.comentario} - ${feedback.avaliacao} estrelas</li>
            <p>Data: ${feedback.dataCriacao}</p>
        </c:forEach>
    </ul>

    <h3>Adicionar Feedback</h3>
    <form id="feedbackForm" action="submitFeedback" method="post">
        <input type="hidden" name="produto_id" value="2"> <!-- ID do produto 2 -->
        <label for="usuario">Usuário:</label>
        <input type="text" id="usuario" name="usuario"><br>
        <label for="rating">Nota:</label>
        <input type="number" id="rating" name="rating" min="1" max="5"><br>
        <label for="comment">Comentário:</label><br>
        <textarea id="comment" name="comment" rows="4" cols="50"></textarea><br>
        <input type="submit" value="Enviar">
    </form>

    <a href="produtos.html">Voltar</a>
    <script>
    document.getElementById("feedbackForm").onsubmit = function(event) {
        var usuario = document.getElementById("usuario").value.trim();
        var rating = document.getElementById("rating").value.trim();
        var comentario = document.getElementById("comment").value.trim();

        if (usuario === "" || rating === "" || comentario === "") {
            alert("Todos os campos são obrigatórios!");
            event.preventDefault();  // Impede o envio do formulário
        }
    };
</script>
</body>
</html>

