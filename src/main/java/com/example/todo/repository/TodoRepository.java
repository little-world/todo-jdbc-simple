package com.example.todo.repository;

import com.example.todo.model.Todo;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class TodoRepository {


  private final DataSource dataSource;

  public TodoRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public List<Todo> findAll() {
    String sql = "SELECT id, task FROM todo";

    // 1) get a Connection,
    // 2) prepare the statement,
    // 3) execute it,
    // 4) walk the ResultSet by hand.
    //  close all three
    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

      List<Todo> todos = new ArrayList<>();
      while (rs.next()) {
        todos.add(mapRow(rs));
      }
      return todos;

    } catch (SQLException ex) {
      // Plain JDBC only throws the checked SQLException - there's no
      // equivalent of Spring's unchecked DataAccessException hierarchy,
      // so callers either declare "throws SQLException" everywhere or,
      // as here, it gets wrapped.
      throw new RuntimeException("Failed to load todos", ex);
    }
  }


  public Todo save(Todo todo) {
    String sql = "INSERT INTO todo (task) VALUES (?)";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, todo.getTask());
      ps.executeUpdate();

      try (ResultSet keys = ps.getGeneratedKeys()) {
        if (keys.next()) {
          todo.setId(keys.getLong(1));
        }
      }
    } catch (SQLException ex) {
      throw new RuntimeException("Failed to save todo", ex);
    }
    return todo;
  }

  private Todo mapRow(ResultSet rs) throws SQLException {
    return new Todo(
        rs.getLong("id"),
        rs.getString("task")
    );
  }

  public void delete(Long id) {
    String sql = "DELETE FROM todo WHERE id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      ps.setLong(1, id);
      ps.executeUpdate();
    } catch (SQLException ex) {
      throw new RuntimeException("Failed to delete", ex);
    }
  }

  public void update(Todo todo) {

    String sql = "UPDATE todo SET task = ? WHERE id = ?";

    try (Connection connection = dataSource.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      ps.setString(1, todo.getTask());
      ps.setLong(2, todo.getId());
      ps.executeUpdate();

    } catch (SQLException ex) {
      throw new RuntimeException("Failed to update todo", ex);
    }
  }
}
