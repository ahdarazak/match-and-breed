package com.heroku.java.Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.stereotype.Service;
import com.heroku.java.Model.AdminModel;

@Service
public class AdminServices {
    private final DataSource dataSource;

    public AdminServices(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Login: Authenticate Admin
    public AdminModel authenticateAdmin(String username, String password) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                AdminModel admin = new AdminModel();
                admin.setAdminID(resultSet.getInt("adminID"));
                admin.setUsername(resultSet.getString("username"));
                admin.setPassword(resultSet.getString("password"));
                admin.setFullname(resultSet.getString("fullname"));
                admin.setEmail(resultSet.getString("email"));
                return admin;
            }
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    // Create Account: Signup Admin
    public void signupAdmin(AdminModel admin) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String insertAccountSql = "INSERT INTO admin(username, password, fullname, email) VALUES(?, ?, ?, ?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertAccountSql);

            insertStatement.setString(1, admin.getUsername());
            insertStatement.setString(2, admin.getPassword());
            insertStatement.setString(3, admin.getFullname());
            insertStatement.setString(4, admin.getEmail());

            insertStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    // Display All Admins
    public List<AdminModel> getAllAdmins() throws SQLException {
        List<AdminModel> adminList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM admin ORDER BY adminID");

            while (resultSet.next()) {
                int adminID = resultSet.getInt("adminID");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fullname = resultSet.getString("fullname");
                String email = resultSet.getString("email");

                AdminModel admin = new AdminModel(adminID, username, password, fullname, email);
                adminList.add(admin);
            }
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
        return adminList;
    }

    // Update Account: Get Admin by ID
    public AdminModel getAdminDetails(int adminID) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM admin WHERE adminID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, adminID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String fullname = resultSet.getString("fullname");
                String email = resultSet.getString("email");

                return new AdminModel(adminID, username, password, fullname, email);
            }
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    // Update Admin Account
    public void updateAdmin(int adminID, String username, String password, String fullname, String email) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE admin SET username = ?, password = ?, fullname = ?, email = ? WHERE adminID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fullname);
            statement.setString(4, email);
            statement.setInt(5, adminID);

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    // Delete Admin Account
    public boolean deleteAdmin(int adminID) {
        try (Connection connection = dataSource.getConnection()) {
            String deleteSql = "DELETE FROM admin WHERE adminID = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, adminID);

            int rowsAffected = deleteStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
