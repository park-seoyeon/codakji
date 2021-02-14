package io.openvidu.js.java.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import io.openvidu.js.java.model.MeetingDto;

@Mapper
public interface MeetingMapper {

	List<MeetingDto> allMeetingRooms();

	int getNewRoomNumber();

	void createRoom(MeetingDto meetingDto);

	int enterRoom(int room_number);

	int leaveRoom(int room_number);

	int getRoomPeopleCnt(int room_number);

	int deleteRoom(int room_number);

	int checkRoom(int room_number);

	MeetingDto getRoomInfo(int room_number);

}
