import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/submitFeedback")
public class SubmitFeedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String produtoId = request.getParameter("produto_id");
        String usuario = request.getParameter("usuario");
        String ratingStr = request.getParameter("rating");
        String comentario = request.getParameter("comment");

        // Validação no servidor
        if (usuario == null || usuario.trim().isEmpty() ||
            ratingStr == null || ratingStr.trim().isEmpty() ||
            comentario == null || comentario.trim().isEmpty()) {

            // Define uma mensagem de erro e redireciona de volta para a página do produto
            request.setAttribute("errorMessage", "Todos os campos são obrigatórios!");
            request.getRequestDispatcher("/produtoFeedback?produto_id=" + produtoId).forward(request, response);
            return;
        }

        int avaliacao;
        try {
            avaliacao = Integer.parseInt(ratingStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "Erro: Nota inválida.");
            request.getRequestDispatcher("/produtoFeedback?produto_id=" + produtoId).forward(request, response);
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO feedbacks (produto_id, usuario, avaliacao, comentario) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(produtoId));
            statement.setString(2, usuario);
            statement.setInt(3, avaliacao);
            statement.setString(4, comentario);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erro ao inserir o feedback.");
            request.getRequestDispatcher("/produtoFeedback?produto_id=" + produtoId).forward(request, response);
            return;
        }

        // Redireciona para o servlet que exibe a página com feedbacks atualizados
        response.sendRedirect("produtoFeedback?produto_id=" + produtoId);
    }
}



