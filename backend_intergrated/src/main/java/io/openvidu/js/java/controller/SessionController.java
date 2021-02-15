package io.openvidu.js.java.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.openvidu.java.client.ConnectionProperties;
import io.openvidu.java.client.ConnectionType;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.js.java.model.MeetingDto;
import io.openvidu.js.java.model.MemberDto;
import io.openvidu.js.java.model.service.JwtService;
import io.openvidu.js.java.model.service.MeetingService;
import io.openvidu.js.java.model.service.MemberService;

@RestController
@RequestMapping("/sessions")
public class SessionController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private JwtService jwtService;

   // OpenVidu object as entrypoint of the SDK
   private OpenVidu openVidu;

   // Collection to pair session names and OpenVidu Session objects
   private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
   // Collection to pair session names and tokens (the inner Map pairs tokens and
   // role associated)
   private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

   // URL where our OpenVidu server is listening
   private String OPENVIDU_URL;
   // Secret shared with our OpenVidu server
   private String SECRET;

   public SessionController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
      this.SECRET = secret;
      this.OPENVIDU_URL = openviduUrl;
      this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
   }
   
   
   
   @PostMapping("/makeRoom")
   public ResponseEntity<Map<String, Integer>> makeRoom(@RequestBody MeetingDto meetingDto) throws Exception{
       //db에 room추가
	   Map<String, Integer> resultMap = new HashMap<>();
	   String user_token = meetingDto.getToken();
	   if(jwtService.isUsable(user_token) && jwtService.isInTime(user_token)) {
		   MemberDto userinfo = memberService.userInfo(jwtService.getUserEmail(user_token));
		   
		   meetingDto.setRoom_publisher_number(userinfo.getUser_number());
		   meetingDto.setRoom_publisher_name(userinfo.getName());
		   meetingService.createRoom(meetingDto);
		   
		   resultMap.put("room_number",meetingService.getNewRoomNumber());
		   return new ResponseEntity<Map<String,Integer>> (resultMap, HttpStatus.OK);
	   }
	   return new ResponseEntity<Map<String,Integer>> (resultMap, HttpStatus.UNAUTHORIZED);
   }

   @GetMapping("/roominfo/{room_number}")
   public ResponseEntity<MeetingDto> getRoomInfo(@PathVariable("room_number") int room_number){
	   return new ResponseEntity<MeetingDto>(meetingService.getRoomInfo(room_number),HttpStatus.OK);
   }
   
   //String clientData,String sessionName
   @PostMapping("/maketoken")
   public ResponseEntity<Map<String, Object>> joinSession(@RequestBody MeetingDto meetingDto) throws Exception {
	  System.out.println(meetingDto.toString());
	  
	  String user_token = meetingDto.getUser_token();
	  MemberDto userinfo = memberService.userInfo(jwtService.getUserEmail(user_token));
	  
	  int room_number= meetingDto.getRoom_number();
	  String sessionName = room_number+"";

      Map<String, Object> resultMap = new HashMap<>();
      HttpStatus status = null;
      
      if(meetingService.checkRoom(room_number)==0) {//없는 방
    	 resultMap.put("message", FAIL);
    	 return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
      }
      
      System.out.println("Getting sessionId and token | {sessionName}={" + sessionName + "}");

      // Role associated to this user
      OpenViduRole role = OpenViduRole.PUBLISHER;
      // Optional data to be passed to other users when this user connects to the
      // video-call. In this case, a JSON with the value we stored in the HttpSession
      // object on login
      String serverData = "{\"serverData\": \"" + "publisher" + "\"}";

      // Build connectionProperties object with the serverData and the role
      ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC)
            .role(role).data(serverData).build();

      
      if (this.mapSessions.get(sessionName) != null) {
         // Session already exists
         System.out.println("Existing session " + sessionName);
         try {

            // Generate a new token with the recently created connectionProperties
            String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

            // Update our collection storing the new token
            this.mapSessionNamesTokens.get(sessionName).put(token, role);

            // Add all the needed attributes to the template
            resultMap.put("token", token);
            resultMap.put("name", userinfo.getName());
            resultMap.put("message", SUCCESS);

            //db에 룸 인원 +1
            if(!meetingDto.isScreenShare()) meetingService.enterRoom(room_number);
            status = HttpStatus.ACCEPTED;
            
         } catch (Exception e) {
            // If error just return dashboard.html template
            resultMap.put("message", "로그인 실패");
            status = HttpStatus.ACCEPTED;
         }
      } else {
         // New session
         System.out.println("New session " + sessionName);
         try {

            // Create a new OpenVidu Session
            Session session = this.openVidu.createSession();
            // Generate a new token with the recently created connectionProperties
            String token = session.createConnection(connectionProperties).getToken();
            // Store the session and the token in our collections
            this.mapSessions.put(sessionName, session);
            this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
            this.mapSessionNamesTokens.get(sessionName).put(token, role);
            

            // Add all the needed attributes to the template
            resultMap.put("token", token);
            resultMap.put("name",userinfo.getName());
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
         } catch (Exception e) {
            // If error just return dashboard.html template
            resultMap.put("message", "로그인 실패");
            status = HttpStatus.ACCEPTED;
         }
      }
      
      return new ResponseEntity<Map<String, Object>>(resultMap, status);
   }

   @RequestMapping(value = "/removeUser", method = RequestMethod.POST)
   public ResponseEntity<String> removeUser(@RequestBody MeetingDto meetingDto)
         throws Exception {
	   
	   //미팅룸 나갔을 때 401에러를 막기 위해 미팅룸 퇴장과 동시에 만료시간 30분 연장처리
	   //토큰 만료되어도 401에러 리턴 X
	   String user_token = meetingDto.getUser_token();
	   if(jwtService.isUsable(user_token)) {
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(jwtService.getUserEmail(user_token));
			memberDto.setToken(user_token);
		   jwtService.setToken(memberDto);
	   }

	   int room_number = meetingDto.getRoom_number();
	   String sessionName = meetingDto.getRoom_number()+"";
	   String token = meetingDto.getToken();
      //System.out.println("Removing user | {sessionName, token}=" + sessionNameToken);

      // If the session exists
      if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {

         // If the token exists
         if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
            // User left the session
            if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
               // Last user left: session must be removed
               this.mapSessions.remove(sessionName);
            }
            if(!meetingDto.isScreenShare()) {
            	meetingService.leaveRoom(room_number);//인원수 -1
            	if(meetingService.getRoomPeopleCnt(room_number)==0) {//인원수 가져와서 0인지 비교
            		meetingService.deleteRoom(room_number);//인원수 0이면 방 삭제
            	}
            }
            return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
         } else {
            // The TOKEN wasn't valid
            System.out.println("Problems in the app server: the TOKEN wasn't valid");
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
         }

      } else {
         // The SESSION does not exist
         System.out.println("Problems in the app server: the SESSION does not exist");
         return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }
   
   @PostMapping("/checkRoom")
   private ResponseEntity<String> checkRoom(@RequestBody MeetingDto meetingDto) throws Exception{
	   String user_token = meetingDto.getUser_token();
	   if(jwtService.isUsable(user_token) && jwtService.isInTime(user_token)) {
			MemberDto memberDto = new MemberDto();
			memberDto.setEmail(jwtService.getUserEmail(user_token));
			memberDto.setToken(user_token);
		   jwtService.setToken(memberDto);
		   int room_number = meetingDto.getRoom_number();
		   
		   if(meetingService.checkRoom(room_number)>0) {
			   System.out.println("존재하는 방");
			   return new ResponseEntity<String> (SUCCESS, HttpStatus.OK);
		   }else {
			   System.out.println("존재하지 않는방");
			   return new ResponseEntity<String> (FAIL, HttpStatus.OK);
		   }
	   }
	   return new ResponseEntity<String> (FAIL, HttpStatus.UNAUTHORIZED);
   }

   private ResponseEntity<JSONObject> getErrorResponse(Exception e) {
      JSONObject json = new JSONObject();
      json.put("cause", e.getCause());
      json.put("error", e.getMessage());
      json.put("exception", e.getClass());
      return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   private void checkUserLogged(HttpSession httpSession) throws Exception {
      if (httpSession == null || httpSession.getAttribute("loggedUser") == null) {
         throw new Exception("User not logged");
      }
   }

}