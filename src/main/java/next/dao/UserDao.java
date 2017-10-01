package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
       JdbcTemplate jdbcTemplate = new JdbcTemplate();
       PreparedStatmentSetter pss = new PreparedStatmentSetter(){
    	   public void setValues(PreparedStatement pstmt) throws SQLException {
   			pstmt.setString(1, user.getUserId());
   	        pstmt.setString(2, user.getPassword());
   	        pstmt.setString(3, user.getName());
   	        pstmt.setString(4, user.getEmail());
   		}
       };
       String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
       jdbcTemplate.update(sql, pss);
    }

	public void update(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		PreparedStatmentSetter pss = new PreparedStatmentSetter(){

		@Override
		public void setValues(PreparedStatement pstmt) throws SQLException {
			pstmt.setString(1, user.getUserId());
	    	pstmt.setString(2, user.getEmail());
			
		}
       };
       String sql = "UPDATE USERS SET email = ? WHERE userId = ?";
	    jdbcTemplate.update(sql, pss);
    }

	public List<User> findAll() throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		
		PreparedStatmentSetter pss = new PreparedStatmentSetter(){
			public void setValues(PreparedStatement pstmt) throws SQLException {
			}
		};
		RowMapper<User> rowMapper = new RowMapper() {
			public Object mapRow(ResultSet rs) throws SQLException {
				return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
			}
		};
		 String sql = "SELECT userId, password, name, email FROM USERS";
		 return jdbcTemplate.query(sql,pss,rowMapper);
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, userId);

            rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }

            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
