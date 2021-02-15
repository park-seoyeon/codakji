package io.openvidu.js.java.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.model.NoticeDto;

@Mapper
public interface NoticeMapper {

	public int writeNotice(NoticeDto noticeDto) throws SQLException;

	public List<NoticeDto> listNotice() throws SQLException;
	
	public int updateViews(int notice_number) throws SQLException;

	public int updateNotice(NoticeDto noticeDto) throws SQLException;

	public int deleteNotice(int notice_number) throws SQLException;
}
