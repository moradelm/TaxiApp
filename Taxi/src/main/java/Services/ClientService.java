package Services;

import Model.Client;
import com.example.taxi.BdConnexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private BdConnexion bdConnexion;

    public ClientService() {
        this.bdConnexion = new BdConnexion();
    }

    public void registerClient(Client client) {
        String utilisateurQuery = "INSERT INTO utilisateur (fullname, telephone, role, email, password, datenaissance, sexe, city) VALUES (?, ?, 'client', ?, ?, ?, ?, ?)";

        try (PreparedStatement utilisateurStatement = bdConnexion.getConnection().prepareStatement(utilisateurQuery, Statement.RETURN_GENERATED_KEYS)) {
            utilisateurStatement.setString(1, client.getNom());
            utilisateurStatement.setString(2, client.getTelephone());
            utilisateurStatement.setString(3, client.getEmail());
            utilisateurStatement.setString(4, client.getPassword());
            utilisateurStatement.setString(5, String.valueOf(client.getDateDeNaissance()));
            utilisateurStatement.setString(6, client.getSexe());
            utilisateurStatement.setString(7, client.getCity());

            int affectedRows = utilisateurStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = utilisateurStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        // Now insert into the client table using the generated user ID
                        String clientQuery = "INSERT INTO client (userId) VALUES (?)";

                        try (PreparedStatement clientStatement = bdConnexion.getConnection().prepareStatement(clientQuery)) {
                            clientStatement.setInt(1, userId);

                            clientStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT fullname, telephone FROM utilisateur WHERE role = 'client'";

        try (PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Client client = new Client();
                client.setNom(resultSet.getString("fullname"));
                client.setTelephone(resultSet.getString("telephone"));

                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }



}
