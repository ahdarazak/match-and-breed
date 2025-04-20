package com.heroku.java.Services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.stereotype.Service;
import com.heroku.java.Model.CustomerModel;

import jakarta.servlet.http.HttpSession;

@Service
public class CustomerServices {
    private final DataSource dataSource;

    public CustomerServices(DataSource dataSource, HttpSession session) {
        this.dataSource = dataSource;
    }

    // Login: Authenticate Customer
    public CustomerModel authenticateUser(String username, String password) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM customer WHERE customerUsername = ? AND customerPassword = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                CustomerModel customer = new CustomerModel();
                customer.setCustomerID(resultSet.getInt("customerID"));
                customer.setCustomerName(resultSet.getString("customerName"));
                customer.setCustomerEmail(resultSet.getString("customerEmail"));
                customer.setCustomerPassword(resultSet.getString("customerPassword"));
                customer.setCustomerPhone(resultSet.getString("customerPhone"));
                customer.setCustomerUsername(resultSet.getString("customerUsername"));
                return customer;
            }
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    // Create Account: Signup Customer
    public void signupCustomer(CustomerModel customer) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String insertAccountSql = "INSERT INTO customer(customerName, customerEmail, customerPassword, customerPhone, customerUsername) VALUES(?,?,?,?,?)";
            PreparedStatement insertStatement = connection.prepareStatement(insertAccountSql);

            insertStatement.setString(1, customer.getCustomerName());
            insertStatement.setString(2, customer.getCustomerEmail());
            insertStatement.setString(3, customer.getCustomerPassword());
            insertStatement.setString(4, customer.getCustomerPhone());
            insertStatement.setString(5, customer.getCustomerUsername());

            insertStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    // Display All Customers
    public List<CustomerModel> getAllCustomers() throws SQLException {
        List<CustomerModel> customerList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM customer ORDER BY customerID");

            while (resultSet.next()) {
                int customerID = resultSet.getInt("customerID");
                String customerName = resultSet.getString("customerName");
                String customerEmail = resultSet.getString("customerEmail");
                String customerPassword = resultSet.getString("customerPassword");
                String customerPhone = resultSet.getString("customerPhone");
                String customerUsername = resultSet.getString("customerUsername");

                CustomerModel customer = new CustomerModel(customerID, customerName, customerEmail, customerPassword, customerPhone, customerUsername);
                customerList.add(customer);
            }
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
        return customerList;
    }

    // Update Account: Get Customer by ID
    public CustomerModel getAccountDetails(int customerID) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM customer WHERE customerID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("customerName");
                String email = resultSet.getString("customerEmail");
                String phone = resultSet.getString("customerPhone");
                String username = resultSet.getString("customerUsername");

                return new CustomerModel(customerID, name, email, null, phone, username); // returning CustomerModel object
            }
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    // Update Customer Account
    public void updateAccount(int customerID, String customerName, String customerEmail, String customerPhone, String customerUsername) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE customer SET customerName = ?, customerEmail = ?, customerPhone = ?, customerUsername = ? WHERE customerID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, customerName);
            statement.setString(2, customerEmail);
            statement.setString(3, customerPhone);
            statement.setString(4, customerUsername);
            statement.setInt(5, customerID);

            statement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw e;
        }
    }

    // Delete Customer Account
    public boolean deleteAccount(int customerID) {
        try (Connection connection = dataSource.getConnection()) {
            String deleteSql = "DELETE FROM customer WHERE customerID = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
            deleteStatement.setInt(1, customerID);

            int rowsAffected = deleteStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
