package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;

public abstract class SelectJdbcTemplate {

	abstract void setValues(PreparedStatement pstmt) throws SQLException;
}
