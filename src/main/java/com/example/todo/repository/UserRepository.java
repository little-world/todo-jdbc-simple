package com.example.todo.repository;

import com.example.todo.model.Todo;
import com.example.todo.model.User;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {


  private final DataSource dataSource;

  public UserRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<User> findAll() {
    String sql = "SELECT id, name FROM users";

    // 1) get a Connection,
    // 2) prepare the statement,
    // 3) execute it,
    // 4) walk the ResultSet by hand.
    //  close all three
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

      List<User> users = new ArrayList<>();
      while (rs.next()) {
        users.add(mapRow(rs));
      }
      return users;

    } catch (SQLException ex) {
      // Plain JDBC only throws the checked SQLException - there's no
      // equivalent of Spring's unchecked DataAccessException hierarchy,
      // so callers either declare "throws SQLException" everywhere or,
      // as here, it gets wrapped.
      throw new RuntimeException("Failed to load todos", ex);
    }
  }

  public User save(User user) {
    String sql = "INSERT INTO users (name) VALUES (?)";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, user.getName());
      ps.executeUpdate();

      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
          user.setId(keys.getLong(1));
        }
      }
    } catch (SQLException ex) {
      throw new RuntimeException("Failed to save todo", ex);
    }
    return user;
  }

  private User mapRow(ResultSet rs) throws SQLException {
    return new User(
        rs.getLong("id"),
        rs.getString("name")
    );
  }

  public void delete(Long id) {
    String sql = "DELETE FROM users WHERE id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    } catch (SQLException ex) {
      throw new RuntimeException("Failed to delete", ex);
    }
  }

  public void update(User user) {

    String sql = "UPDATE users SET name = ? WHERE id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, user.getName());
      ps.setLong(2, user.getId());
      ps.executeUpdate();

    } catch (SQLException ex) {
      throw new RuntimeException("Failed to update todo", ex);
    }
  }
}
