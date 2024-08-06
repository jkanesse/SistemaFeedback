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

        int avaliacao;
        try {
            avaliacao = Integer.parseInt(ratingStr);
        } catch (NumberFormatException e) {
            response.getWriter().write("Erro: Nota inválida.");
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
            response.getWriter().write("Erro ao inserir o feedback.");
            return;
        }

        // Redireciona para a página de feedback do produto correspondente
        response.sendRedirect("produtoFeedback?produto_id=" + produtoId);
    }
}


