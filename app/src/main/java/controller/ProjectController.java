/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author rodri
 */
public class ProjectController {

    public void save(Project project) {

        String sql = "INSERT INTO tasks (name," + "description," + "CreatedAt," + "CpdateAt) VALUES (?,?,?,?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(2, project.getName());
            statement.setString(3, project.getDescription());
            statement.setDate(7, new Date(project.getCreateAt().getTime()));
            statement.setDate(8, new Date(project.getUpdateAt().getTime()));
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }
    }

    public void update(Project project) {

        String sql = "UPDATE projects SET (name," + "description," + "CreatedAt," + "CpdateAt) WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // ESTABELECENDO A CONEXÃO COM O BANCO DE DADOS
            connection = ConnectionFactory.getConnection();
            // PREPARANDO A QUERY
            statement = connection.prepareStatement(sql);
            //SETANDO OS VALORES DO STATEMENT
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreateAt().getTime()));
            statement.setDate(4, new Date(project.getUpdateAt().getTime()));
            statement.setInt(5, project.getId());

            //EXECUTANDO A QUERY
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa" + ex.getMessage(), ex);

        }

    }

    public void removeById(int idProject) throws SQLException {
        String sql = "DELETE FROM projects WHERE id =?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            // Criação da conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            // PREPARANDO A QUERY

            statement = connection.prepareStatement(sql);
            //SETANDO OS VALORES
            statement.setInt(1, idProject);
            //EXECUTANDO A QUERY
            statement.execute();

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }
    }

    public List<Project> getAll() throws SQLException {
        String sql = "SELECT * FROM projects WHERE projects = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        List<Project> projects = new ArrayList<>();

        try {
            // criação da conexão
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);

            //VALOR RETORNADO PELA EXECUÇÃO DA QUERY
            resultSet = statement.executeQuery();

            // enquanto houverem valores a serem percorridos pelo resultSet
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setName(resultSet.getString("name"));
                project.setDescription(resultSet.getString("Description"));
                project.setCreateAt(resultSet.getDate("CreatedAt"));
                project.setUpdateAt(resultSet.getDate("UpdateAt"));

                projects.add(project); } 
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }
    }
}
