/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author rodri
 */
public class TaskController {

    public void save(Task task) {

        String sql = "INSERT INTO tasks (idProject" + "name," + "description," + "notes," + "deadline," + "createdAt," + "updateAt) VALUES (?,?,?,?,?,?,?,?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }
    }

    public void update(Task task) {

        String sql = "UPDATE  tasks  SET (idProject," + "name," + "description," + "notes," + "deadline," + "createdAt," + "updateAt " + "WHERE id (?,?,?,?,?,?,?,?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // ESTABELECENDO A CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            // PREPARANDO A QUERY
            statement = connection.prepareStatement(sql);
            //SETANDO OS VALORES DO STATEMENT
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            statement.setInt(9, task.getId());

            //EXECUTANDO A QUERY
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa" + ex.getMessage(), ex);

        }

    }

    public void removeById(int taskId) throws SQLException {
        String sql = "DELETE FROM tasks WHERE id =?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Criação da conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            // PREPARANDO A QUERY

            statement = connection.prepareStatement(sql);
            //SETANDO OS VALORES
            statement.setInt(1, taskId);
            //EXECUTANDO A QUERY
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }
    }

    public List<Task> getAll(int idProject) throws SQLException {
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Task> tasks = new ArrayList<Task>();

        try {
            // criação da conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            // SETANDO UM VALOR QUE CORRESPONDE AO FILTRO DE BUSCA
            statement.setInt(1, idProject);
          
            
            //VALOR RETORNADO PELA EXECUÇÃO DA QUERY
            resultSet = statement.executeQuery();

            // enquanto houverem valores a serem percorridos pelo resultSet
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("Description"));
                task.setNotes(resultSet.getString("Notes"));
                task.setIsCompleted(resultSet.getBoolean("Completed"));
                task.setDeadline(resultSet.getDate("Deadline"));
                task.setCreatedAt(resultSet.getDate("CreatedAt"));
                task.setUpdateAt(resultSet.getDate("UpdateAt"));

                tasks.add(task);

            }
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }

        // Lista de Tarefas que foi criada e carregada do Banco de Dados
        return tasks;
    }

}
