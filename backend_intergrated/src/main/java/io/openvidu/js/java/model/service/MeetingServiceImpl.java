package io.openvidu.js.java.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.openvidu.js.java.model.MeetingDto;
import io.openvidu.js.java.model.mapper.MeetingMapper;



@Service
public class MeetingServiceImpl implements MeetingService{
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<MeetingDto> allMeetingRooms() {
		return sqlSession.getMapper(MeetingMapper.class).allMeetingRooms();
	}

	@Override
	public int getNewRoomNumber() {
		return sqlSession.getMapper(MeetingMapper.class).getNewRoomNumber();
	}

	@Override
	public void createRoom(MeetingDto meetingDto) {
		sqlSession.getMapper(MeetingMapper.class).createRoom(meetingDto);
	}

	@Override
	public boolean enterRoom(int room_number) {
		return sqlSession.getMapper(MeetingMapper.class).enterRoom(room_number) == 1;
	}

	@Override
	public boolean leaveRoom(int room_number) {
		return sqlSession.getMapper(MeetingMapper.class).leaveRoom(room_number)==1;
	}

	@Override
	public int getRoomPeopleCnt(int room_number) {
		return sqlSession.getMapper(MeetingMapper.class).getRoomPeopleCnt(room_number);
	}

	@Override
	public boolean deleteRoom(int room_number) {
		return sqlSession.getMapper(MeetingMapper.class).deleteRoom(room_number) == 1;
	}

	@Override
	public int checkRoom(int room_number) {
		return sqlSession.getMapper(MeetingMapper.class).checkRoom(room_number);
	}

	@Override
	public MeetingDto getRoomInfo(int room_number) {
		return sqlSession.getMapper(MeetingMapper.class).getRoomInfo(room_number);
	}

}
