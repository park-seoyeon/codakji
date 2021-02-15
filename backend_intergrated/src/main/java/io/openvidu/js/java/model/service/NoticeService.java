package io.openvidu.js.java.model.service;

import java.util.List;

import io.openvidu.js.java.model.NoticeDto;


public interface NoticeService {
	public boolean writeNotice(NoticeDto noticeDto) throws Exception;
	public List<NoticeDto> listNotice() throws Exception;
	public boolean updateViews(int notice_number) throws Exception;
	public boolean updateNotice(NoticeDto noticeDto) throws Exception;
	public boolean deleteNotice(int notice_number) throws Exception;
}
