package com.ssafy.codackji.model.service;

import java.util.List;

import com.ssafy.codackji.model.NoticeDto;

public interface NoticeService {
	public boolean writeNotice(NoticeDto noticeDto) throws Exception;
	public List<NoticeDto> listNotice() throws Exception;
	public boolean updateNotice(NoticeDto noticeDto) throws Exception;
	public boolean deleteNotice(int notice_number) throws Exception;
}
