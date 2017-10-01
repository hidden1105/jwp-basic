package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;
import next.model.User;

public class JdbcTemplate {
    public void update(String sql, PreparedStatmentSetter pss) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);

            pstmt.executeUpdate();
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public <T>List<T> query(String sql, PreparedStatmentSetter pss, RowMapper<T> rowMapper) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);
            
            rs = pstmt.executeQuery();
            
            List<T> result = new ArrayList<T>();
            while (rs.next()) {
            	result.add(rowMapper.mapRow(rs));
            }
            
            return result;
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }

            if (con != null) {
                con.close();
            }
        }
    }

    public <T> T queryForObject(String sql, PreparedStatmentSetter pss, RowMapper<T> rowMapper) throws SQLException {
    	List<T> result = query(sql, pss, rowMapper);
    	if (result.isEmpty()) {
    		return null;
    	}
    	return result.get(0);
    }
}
