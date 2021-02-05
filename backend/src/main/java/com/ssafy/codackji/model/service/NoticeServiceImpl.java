package com.ssafy.codackji.model.service;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.codackji.model.NoticeDto;
import com.ssafy.codackji.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public boolean writeNotice(NoticeDto noticeDto) throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).writeNotice(noticeDto) ==1;
	}

	@Override
	public List<NoticeDto> listNotice() throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).listNotice();
	}

	@Override
	public boolean updateNotice(NoticeDto noticeDto) throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).updateNotice(noticeDto) ==1;
	}

	@Override
	public boolean deleteNotice(int notice_number) throws Exception {
		return sqlSession.getMapper(NoticeMapper.class).deleteNotice(notice_number) ==1;
	}

}
