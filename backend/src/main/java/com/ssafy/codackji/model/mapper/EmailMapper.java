package com.ssafy.codackji.model.mapper;

import java.sql.SQLException;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EmailMapper {

	public boolean updateCertification(String email) throws SQLException;
}
