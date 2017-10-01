package next.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatmentSetter {
	void setValues(PreparedStatement pstmt) throws SQLException;
}
