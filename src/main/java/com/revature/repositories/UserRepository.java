package com.revature.repositories;

import com.revature.models.ReimbursementType;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class UserRepository {
    private String baseQuery = "SELECT * FROM project_1.ers_users eu ";
    private String baseInsert = "INSERT INTO project_1.ers_users ";
    private String baseUpdate = "UPDATE project_1.ers_users eu ";

    public UserRepository(){
        super();
    }

    //---------------------------------- CREATE -------------------------------------------- //

    /**
     * A method tho add a new user to the database, hashes passwords before inserting
     * @param newUser the user to be added
     * @return returns true if one and only one row was inserted
     * @throws SQLException e
     */
    public boolean addUser(User newUser) throws SQLException {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseInsert +
                         "(username, password, first_name, last_name, email, user_role_id)\n" +
                         "VALUES(?, project_1.crypt(?, project_1.gen_salt('bf', 10)), ?, ?, ?, ?);\n";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,newUser.getUsername());
            ps.setString(2,newUser.getPassword());
            ps.setString(3,newUser.getFirstname());
            ps.setString(4,newUser.getLastname());
            ps.setString(5,newUser.getEmail());
            ps.setInt(6,newUser.getUserRole().ordinal() + 1);
            //get the number of affected rows
            int rowsInserted = ps.executeUpdate();
            return rowsInserted != 0;
        }
    }

    //---------------------------------- READ -------------------------------------------- //

    /**
     * A method to get a single user by ID
     * @param userId the user ID to search for
     * @return returns an optional user
     * @throws SQLException e
     */
    public Optional<User> getAUserById(Integer userId) throws SQLException {
        Optional<User> user = Optional.empty();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            String sql = baseQuery + "WHERE eu.id=? ";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1,userId);
            ResultSet rs = psmt.executeQuery();
            user = mapResultSet(rs).stream().findFirst();
        }
        return user;
    }

    /**
     * A method to get a single User by email
     * @param email the email address to search the DB for
     * @return returns an Optional user
     * @throws SQLException e
     */
    public Optional<User> getAUserByEmail(String email) throws SQLException {
        Optional<User> user = Optional.empty();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            String sql = baseQuery + "WHERE eu.id=? ";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,email);
            ResultSet rs = psmt.executeQuery();
            user = mapResultSet(rs).stream().findFirst();
        }
        return user;
    }

    /**
     * A method to get a single user by a given username and password
     * @param userName the users username
     * @param password the users password
     * @return returns an optional user
     * @throws SQLException e
     */
    public Optional<User> getAUserByUsernameAndPassword(String userName, String password) throws SQLException {
        Optional<User> user = Optional.empty();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            String sql = baseQuery + "WHERE username = ? AND  password = project_1.crypt(?, password)";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setString(1,userName);
            psmt.setString(2,password);
            ResultSet rs = psmt.executeQuery();
            user = mapResultSet(rs).stream().findFirst();
        }
        return user;
    }

    /**
     * A method to get a set of all users from the database by Role
     * @param role the role to search for
     * @return returns a set of users
     * @throws SQLException e
     */
    public Set<User> getAllUsersByRole(Role role) throws SQLException {
        Set<User> user = new HashSet<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            String sql = baseQuery + "WHERE eu.user_role_id=? ";
            PreparedStatement psmt = conn.prepareStatement(sql);
            psmt.setInt(1,role.ordinal() + 1);
            ResultSet rs = psmt.executeQuery();
            user = mapResultSet(rs);
        }
        return user;
    }

    //---------------------------------- UPDATE -------------------------------------------- //

    /**
     * A method to update the Role of a User in the database
     * @param userId ID of the user in the DB
     * @param newRole new Role of the User
     * @return Returns true if 1 and only row was affected
     * @throws SQLException
     */
    public boolean updateAUserRoleById(Integer userId, Role newRole) throws SQLException {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = baseUpdate +
                    "SET user_role_id=?\n" +
                    "WHERE username=? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,newRole.ordinal() + 1);
            ps.setInt(2,userId);
            int rowsInserted = ps.executeUpdate();
            return rowsInserted != 0;
        }
    }

    //---------------------------------- DELETE -------------------------------------------- //

    /**
     * A method to delete a single User from the database
     * @param userId the ID of the record to be deleted
     * @return returns true if one and only one record is updated
     * @throws SQLException
     */
    public boolean deleteAUserById(Integer userId) throws SQLException {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM project_1.ers_users\n" +
                         "WHERE id=? ";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            //get the number of affected rows
            int rowsInserted = ps.executeUpdate();
            return rowsInserted != 0;
        }
    }



    //---------------------------------- UTIL -------------------------------------------- //

    /**
     * A method to map the result sets from the users queries
     * @param rs a result set
     * @return a set of users
     * @throws SQLException e
     */
    private Set<User> mapResultSet(ResultSet rs) throws SQLException {
        Set<User> users = new HashSet<>();
        while (rs.next()){
            User temp = new User();
            temp.setUserId(rs.getInt("id"));
            temp.setUsername(rs.getString("username"));
            temp.setPassword(rs.getString("password"));
            temp.setFirstname(rs.getString("first_name"));
            temp.setLastname(rs.getString("last_name"));
            temp.setUserRole(Role.getByName(rs.getString("user_role_id")));
            users.add(temp);
        }
        return users;
    }
}
