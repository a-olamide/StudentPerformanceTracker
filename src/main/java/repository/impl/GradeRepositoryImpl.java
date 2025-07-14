package repository.impl;

import repository.GradeRepository;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Grade;

public class GradeRepositoryImpl implements GradeRepository {
    @Override
    public void save(Grade grade) {
        String sql = "INSERT INTO grades (student_id, subject_id, score, date_recorded) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, grade.getStudentId());
            stmt.setInt(2, grade.getSubjectId());
            stmt.setDouble(3, grade.getScore());
            stmt.setDate(4, Date.valueOf(grade.getDateRecorded()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Grade grade) {
        String sql = "UPDATE grades SET score=?, date_recorded=? WHERE grade_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, grade.getScore());
            stmt.setDate(2, Date.valueOf(grade.getDateRecorded()));
            stmt.setInt(3, grade.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int gradeId) {
        String sql = "DELETE FROM grades WHERE grade_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gradeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Grade findById(int gradeId) {
        String sql = "SELECT * FROM grades WHERE grade_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, gradeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Grade(
                        rs.getInt("grade_id"),
                        rs.getInt("student_id"),
                        rs.getInt("subject_id"),
                        rs.getDouble("score"),
                        rs.getDate("date_recorded").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Grade> findByStudentId(int studentId) {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades WHERE student_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grades.add(new Grade(
                        rs.getInt("grade_id"),
                        rs.getInt("student_id"),
                        rs.getInt("subject_id"),
                        rs.getDouble("score"),
                        rs.getDate("date_recorded").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }
}
