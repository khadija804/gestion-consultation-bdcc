package ma.enset.gestionconsultationbdcc.dao;

import ma.enset.gestionconsultationbdcc.entities.Consultation;
import ma.enset.gestionconsultationbdcc.entities.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultationDao  implements IConsultationDao{
    @Override
    public void create(Consultation consultation) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO CONSULTATION(date_consultation,description,id_patient)" + "values(?,?,?)");
        pstm.setString(1,consultation.getDate());
        pstm.setString(2,consultation.getDescription());
        pstm.setInt(3,consultation.getPatient().getId_patient());
        pstm.executeUpdate();
    }

    @Override
    public void update(Consultation consultation) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE CONSULTATION SET date_consultation = ?, description = ?, id_patient = ? WHERE id_consultation = ?");
        pstm.setString(1,consultation.getDate());
        pstm.setString(2,consultation.getDescription());
        pstm.setInt(3,consultation.getPatient().getId_patient());
        pstm.setInt(4,consultation.getId());
        pstm.executeUpdate();
    }

    @Override
    public void delete(Consultation consultation) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM CONSULTATION WHERE id_consultation = ?");
        pstm.setInt(1,consultation.getId());
        pstm.executeUpdate();
    }

    @Override
    public List<Consultation> findAll() throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM CONSULTATION C JOIN PATIENTS P ON C.id_patient = P.id_patient");
        ResultSet rs = pstm.executeQuery();
        List<Consultation> consultations = new ArrayList<>();
        while (rs.next()){
            Consultation c = new Consultation();
            Patient p = new Patient();
            c.setId(rs.getInt("id_consultation"));
            c.setDate(rs.getString("date_consultation"));
            c.setDescription(rs.getString("description"));
            p.setId_patient(rs.getInt("id_patient"));
            c.setPatient(p);
            consultations.add(c);
        }
        return consultations;
    }

    @Override
    public Consultation findById(Integer id) throws SQLException {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM CONSULTATION C JOIN PATIENTS P ON C.id_patient = P.id_patient WHERE C.id_consultation = ?");
        pstm.setInt(1,id);
        ResultSet rs = pstm.executeQuery();
        Consultation c = new Consultation();
        Patient p = new Patient();
        if (rs.next()){
            c.setId(rs.getInt("id_consultation"));
            c.setDate(rs.getString("date_consultation"));
            c.setDescription(rs.getString("description"));
            p.setId_patient(rs.getInt("id_patient"));
            c.setPatient(p);
        }
        return c;
    }
}
