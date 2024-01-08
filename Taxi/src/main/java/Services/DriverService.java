package Services;

import Model.Driver;
import com.example.taxi.BdConnexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DriverService {
    private BdConnexion bdConnexion;

    private Authentification authentification;

    public DriverService() {
        this.bdConnexion = new BdConnexion();
    }

    public DriverService(Authentification authentification) {
        this.bdConnexion = new BdConnexion();
        this.authentification = authentification;
    }

    public void registerDriver(Driver driver) {
        String utilisateurQuery = "INSERT INTO utilisateur (fullname, telephone, role, email, password, datenaissance, sexe, city) VALUES (?, ?, 'driver', ?, ?, ?, ?, ?)";

        try (PreparedStatement utilisateurStatement = bdConnexion.getConnection().prepareStatement(utilisateurQuery, Statement.RETURN_GENERATED_KEYS)) {
            utilisateurStatement.setString(1, driver.getNom());
            utilisateurStatement.setString(2, driver.getTelephone());
            utilisateurStatement.setString(3, driver.getEmail());
            utilisateurStatement.setString(4, driver.getPassword());
            utilisateurStatement.setString(5, String.valueOf(driver.getDateDeNaissance()));
            utilisateurStatement.setString(6, driver.getSexe());
            utilisateurStatement.setString(7, driver.getCity());

            int affectedRows = utilisateurStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = utilisateurStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        String driverQuery = "INSERT INTO driver (userId, cartype) VALUES (?, ?)";

                        try (PreparedStatement driverStatement = bdConnexion.getConnection().prepareStatement(driverQuery)) {
                            driverStatement.setInt(1, userId);
                            driverStatement.setString(2, driver.getCarType());

                            driverStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM utilisateur JOIN driver ON utilisateur.id = driver.userId";

        try (PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Driver driver = new Driver();
                driver.setNom(resultSet.getString("fullname"));
                driver.setCarType(resultSet.getString("cartype"));
                driver.setId(resultSet.getInt("id"));


                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }


}