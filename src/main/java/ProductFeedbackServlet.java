import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/produtoFeedback")
public class ProductFeedbackServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String produtoId = request.getParameter("produto_id");
        List<Feedback> feedbacks = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT usuario, avaliacao, comentario, data_criacao FROM feedbacks WHERE produto_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(produtoId));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String usuario = resultSet.getString("usuario");
                int avaliacao = resultSet.getInt("avaliacao");
                String comentario = resultSet.getString("comentario");
                String dataCriacao = resultSet.getString("data_criacao");

                feedbacks.add(new Feedback(usuario, avaliacao, comentario, dataCriacao));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("feedbacks", feedbacks);
        request.getRequestDispatcher("/produto.jsp").forward(request, response);
    }
}

class Feedback {
    private String usuario;
    private int avaliacao;
    private String comentario;
    private String dataCriacao;

    public Feedback(String usuario, int avaliacao, String comentario, String dataCriacao) {
        this.usuario = usuario;
        this.avaliacao = avaliacao;
        this.comentario = comentario;
        this.dataCriacao = dataCriacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public String getComentario() {
        return comentario;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }
}

