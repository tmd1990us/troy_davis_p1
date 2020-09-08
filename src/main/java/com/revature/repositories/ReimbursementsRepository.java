package com.revature.repositories;

import com.revature.models.Reimbursement;
import com.revature.models.ReimbursementStatus;
import com.revature.models.ReimbursementType;
import com.revature.util.ConnectionFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A class to interact with the database to CRUD reimbursement objects
 */
public class ReimbursementsRepository {
    private String baseQuery = "SELECT * FROM project_1.ers_reimbursements er ";
    private String baseInsert = "INSERT INTO project_1.ers_reimbursements ";
    private String baseUpdate = "UPDATE project_1.ers_reimbursements er ";

    public ReimbursementsRepository(){
        super();
    }

    //---------------------------------- CREATE -------------------------------------------- //
    /**
     * Adds a reimburement to the database, Does not handle Images!
     * @param reimbursement the reimbursement to be added to the DB
     * @throws SQLException e
     * @throws IOException e
     */
    public boolean addReimbursement(Reimbursement reimbursement) throws SQLException, IOException {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseInsert +
                    "(amount, description, author_id, " +
                    "reimbursement_status_id, reimbursement_type_id)\n" +
                    "VALUES(?, ?, ?, 1, ?);\n";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1,reimbursement.getAmount());
            ps.setString(2,reimbursement.getDescription());
            ps.setInt(3,reimbursement.getAuthorId());
            //Reimburesements are submitted with PENDING  status by Default!
            ps.setInt(4,reimbursement.getReimbursementType().ordinal() + 1);

            //get the number of affected rows
            int rowsInserted = ps.executeUpdate();
            return rowsInserted != 0;
        }
    }

    //---------------------------------- READ -------------------------------------------- //

    public Set<Reimbursement> getAllReimbursements() throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseQuery;
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            reimbursements = mapResultSet(rs);
        }
        return reimbursements;
    }

    public Set<Reimbursement> getAllReimbSetByStatus(ReimbursementStatus reStat) throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseQuery + "WHERE er.reimbursement_status_id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,reStat.ordinal() + 1);
            ResultSet rs = ps.executeQuery();
            reimbursements = mapResultSet(rs);
        }
        return reimbursements;
    }

    /**
     * A method to get Reimbursements by the id of the reimbursement itself
     * @param reimbId The ID of the reimbursement in the database that is requested
     * @return returns an Option Reimbursement object
     * @throws SQLException e
     */
    public Optional<Reimbursement> getAReimbByReimbId(Integer reimbId) throws SQLException {
        Optional<Reimbursement> reimbursement = Optional.empty();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseQuery + "WHERE er.id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,reimbId);

            ResultSet rs = ps.executeQuery();

            reimbursement = mapResultSet(rs).stream().findFirst();
        }
        return reimbursement;
    }

    /**
     * A method to get all of the records for an author given their id
     * @param authorId the ID of the author of the reimbursement
     * @return a set of reimbursements mapped by the MapResultSet method
     * @throws SQLException e
     */
    public Set<Reimbursement> getAllReimbSetByAuthorId(Integer authorId) throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseQuery + "WHERE er.author_id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,authorId);

            ResultSet rs = ps.executeQuery();

            reimbursements = mapResultSet(rs);
        }
        return reimbursements;
    }

    /**
     * A method to get all of the records for an author given their id and filter by status
     * @param authorId the ID of the author of the reimbursement
     * @param reStat the status that the reimbursement is to be set to
     * @return a set of reimbursements mapped by the MapResultSet method
     * @throws SQLException e
     */
    public Set<Reimbursement> getAllReimbSetByAuthorIdAndStatus(Integer authorId, ReimbursementStatus reStat) throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseQuery + "WHERE er.author_id=? AND er.reimbursement_status_id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,authorId);
            ps.setInt(2,reStat.ordinal() + 1);
            ResultSet rs = ps.executeQuery();
            reimbursements = mapResultSet(rs);
        }
        return reimbursements;
    }

    /**
     * A method to get all of the records for an author given their id and filter by type
     * @param authorId ID of the Author User
     * @param reType the Type to update the record to
     * @return a set of reimbursements mapped by the MapResultSet method
     * @throws SQLException e
     */
    public Set<Reimbursement> getAllReimbSetByAuthorIdAndType(Integer authorId, ReimbursementType reType) throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseQuery + "WHERE er.author_id=? AND er.reimbursement_type_id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,authorId);
            ps.setInt(2,reType.ordinal() + 1);
            ResultSet rs = ps.executeQuery();
            reimbursements = mapResultSet(rs);
        }
        return reimbursements;
    }

    /**
     * A method to get all of the records for a resolver given their id
     * @param resolverId ID of the Resolver User
     * @return a set of reimbursements mapped by the MapResultSet method
     * @throws SQLException e
     */
    public Set<Reimbursement> getAllReimbSetByResolverId(Integer resolverId) throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseQuery + "WHERE er.resolver_id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,resolverId);

            ResultSet rs = ps.executeQuery();

            reimbursements = mapResultSet(rs);
        }
        return reimbursements;
    }

    /**
     * A method to get all of the records for a resolver given their id and filter by status
     * @param resolverId  ID of the Resolver User
     * @param reStat the status to update the record to
     * @return a set of reimbursements mapped by the MapResultSet method
     * @throws SQLException e
     */
    public Set<Reimbursement> getAllReimbSetByResolverIdAndStatus(Integer resolverId, ReimbursementStatus reStat) throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseQuery + "WHERE er.resolver_id=? AND er.reimbursement_status_id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,resolverId);
            ps.setInt(2,reStat.ordinal() + 1);
            ResultSet rs = ps.executeQuery();
            reimbursements = mapResultSet(rs);
        }
        return reimbursements;
    }

    /**
     * A  method to get all of the records for a resolver given their id and filter by type
     * @param resolverId ID of the Resolver User
     * @param reType type of Reimbursements to select by
     * @return a set of reimbursements mapped by the MapResultSet method
     * @throws SQLException e
     */
    public Set<Reimbursement> getAllReimbSetByResolverIdAndType(Integer resolverId, ReimbursementType reType) throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseQuery + "WHERE er.resolver_id=? AND er.reimbursement_type_id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,resolverId);
            ps.setInt(2,reType.ordinal() + 1);
            ResultSet rs = ps.executeQuery();
            reimbursements = mapResultSet(rs);
        }
        return reimbursements;
    }

    //---------------------------------- UPDATE -------------------------------------------- //
    /**
     * A method to update only the resolved timestamp by the id of the reimbursement
     * @param reimbId The ID of the reimbursement in the database that is requested
     * @param timestamp an SQL timestamp object to set the time resolved to
     * @return returns true if one and only one record was updated
     * @throws SQLException e
     */
    public boolean updateResolvedTimeStampByReimbId(Integer reimbId, Timestamp timestamp) throws SQLException {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseUpdate +
                         "SET resolved=?\n" +
                         "WHERE id=?\n";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setTimestamp(1,timestamp);
            ps.setInt(2,reimbId);
            //get the number of affected rows
            int rowsInserted = ps.executeUpdate();
            return rowsInserted != 0;
        }
    }

    /**
     * A method to update only the resolver ID by the id of the reimbursement
     * @param reimbId The ID of the reimbursement in the database that is requested
     * @param resolverId the ID of the user that resolves the record to update the record to
     * @return returns true if one and only one record was updated
     * @throws SQLException e
     */
    public boolean updateResolverIdByReimbId(Integer reimbId, Integer resolverId) throws SQLException {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseUpdate +
                    "SET resolver_id=?\n" +
                    "WHERE id=?\n";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,resolverId);
            ps.setInt(2,reimbId);
            //get the number of affected rows
            int rowsInserted = ps.executeUpdate();
            return rowsInserted != 0;
        }
    }

    /**
     * A method to update only the Reimb. TYPE by the id of the Reimbursement
     * @param reimbId The ID of the reimbursement in the database that is requested
     * @param reimbursementType the type to update the record to
     * @return returns true if one and only one record was updated
     * @throws SQLException e
     */
    public boolean updateReimbursementTypeByReimbId(Integer reimbId, ReimbursementType reimbursementType) throws SQLException {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseUpdate +
                    "SET reimbursement_type_id=? " +
                    "WHERE er.id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,reimbursementType.ordinal() + 1);
            ps.setInt(2,reimbId);
            //get the number of affected rows
            int rowsInserted = ps.executeUpdate();
            return rowsInserted != 0;
        }
    }

    /**
     * A method to update the status of a reimbursement in the database
     * @param reimbId The ID of the reimbursement in the database that is requested
     * @param newReimbStatus the status to update the record to
     * @return returns true if one and only one record was updated
     * @throws SQLException e
     */
    public boolean updateReimbursementStatusByReimbId(Integer reimbId, ReimbursementStatus newReimbStatus) throws SQLException {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseUpdate +
                         "SET reimbursement_status_id=? " +
                         "WHERE er.id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,newReimbStatus.ordinal() + 1);
            ps.setInt(2,reimbId);
            //get the number of affected rows
            int rowsInserted = ps.executeUpdate();
            return rowsInserted != 0;
        }
    }


    public void addReimbursementImage() throws SQLException, IOException {
        //TODO: create an add image method to recieve and transmit images
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            File file = new File("myimage.gif");
            FileInputStream fis = new FileInputStream(file);
            PreparedStatement ps = conn.prepareStatement(baseInsert + "(reciept) VALUES (?, ?) WHERE ers_reimbursements.id=? ");
            ps.setString(1, file.getName());
            ps.setBinaryStream(2, fis, file.length());
            //TODO: get image ID and attach
            ps.executeUpdate();
            ps.close();
            fis.close();
        }
    }

    //---------------------------------- DELETE -------------------------------------------- //

    /**
     * A method to delete a single Reimbursement from the database
     * @param reimbId the ID of the record to be deleted
     * @return returns true if one and only one record is updated
     * @throws SQLException e
     */
    public boolean delete(Integer reimbId) throws SQLException {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM project_1.ers_reimbursements\n" +
                         "WHERE id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,reimbId);
            //get the number of affected rows
            int rowsInserted = ps.executeUpdate();
            return rowsInserted != 0;
        }
    }

    //---------------------------------- UTIL -------------------------------------------- //
    /**
     * A method to map the result sets from the reimbursement queries
     * @param rs a resultset
     * @return a set of reimbursements
     * @throws SQLException e
     */
    private Set<Reimbursement> mapResultSet(ResultSet rs) throws SQLException {
        Set<Reimbursement> reimbursements = new HashSet<>();
        while (rs.next()){
            Reimbursement temp = new Reimbursement();
            temp.setId(rs.getInt("id"));
            temp.setAmount(rs.getDouble("amount"));
            temp.setSubmitted(rs.getTimestamp("submitted"));
            temp.setResolved(rs.getTimestamp("resolved"));
            temp.setDescription(rs.getString("description"));
            temp.setAuthorId(rs.getInt("author_id"));
            temp.setResolverId(rs.getInt("resolver_id"));
            temp.setReimbursementStatus(ReimbursementStatus.getByName(rs.getString("reimbursement_status_id")));
            temp.setReimbursementType(ReimbursementType.getByName(rs.getString("reimbursement_type_id")));
            reimbursements.add(temp);
        }
        return reimbursements;
    }

}
