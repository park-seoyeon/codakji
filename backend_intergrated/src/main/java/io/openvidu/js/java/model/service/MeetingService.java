package io.openvidu.js.java.model.service;

import java.util.List;

import io.openvidu.js.java.model.MeetingDto;


public interface MeetingService {

	List<MeetingDto> allMeetingRooms();

	int getNewRoomNumber();

	void createRoom(MeetingDto meetingDto);

	boolean enterRoom(int room_number);

	boolean leaveRoom(int room_number);

	int getRoomPeopleCnt(int room_number);

	boolean deleteRoom(int room_number);

	int checkRoom(int room_number);

	MeetingDto getRoomInfo(int room_number);

}
