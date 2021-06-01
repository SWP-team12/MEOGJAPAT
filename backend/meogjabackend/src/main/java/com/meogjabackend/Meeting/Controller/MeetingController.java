package com.meogjabackend.Meeting.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.meogjabackend.Meeting.DTO.MeetingDTO;
import com.meogjabackend.Meeting.Service.MeetingService;
import com.meogjabackend.Noti.DAO.NotiDAO;
import com.meogjabackend.Noti.DTO.NotiDTO;

class CreateMeeting {
	public int u_id;
	public String menu;
	public int area;
	public String place;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	public LocalDateTime datetime;
	
	public boolean amity;
	public int ageGroup;
	public int m_number;
}

class UpdateMeeting {
	public int u_id;
	public String action;
	public int status;
}

@RestController
@MapperScan(basePackages="com.meogjabackend.Meeting.Mapper")
public class MeetingController {

	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private NotiDAO notiDAO;
	
	/* GET localhost:8080/meeting*/
	/* GET localhost:8080/meeting?hostId=123*/
	/* GET localhost:8080/meeting?prtId=123*/
	/* 미팅 게시글 전부 불러오기 */
	@GetMapping(value="/meeting")
	public List<MeetingDTO> openMeetingList(
			@RequestParam(value="hostId", defaultValue="0") int hostId,
			@RequestParam(value="prtId", defaultValue="0") int prtId
			) throws Exception {
		ArrayList<MeetingDTO> result = new ArrayList<MeetingDTO>();
		
		List<MeetingDTO> meetingList = meetingService.selectMeetingList();
		
		if (hostId != 0)  result.addAll(meetingList.stream().filter(meeting -> meeting.getU_id() == hostId).collect(Collectors.toList()));
		else if (prtId != 0) {
			result.addAll(meetingList.stream().filter(
					meeting -> meeting.getParticipant_2() == prtId ||
					meeting.getParticipant_3() == prtId ||
					meeting.getParticipant_4() == prtId
					).collect(Collectors.toList()));
		} else result.addAll(meetingList);
		
		return result;
	}
	
	/*게시글 만들기*/
	@PostMapping(value="/meeting")
	public void insertMeeting(@RequestBody CreateMeeting meeting) throws Exception {
		MeetingDTO meetingDTO = new MeetingDTO(
				0, meeting.area, meeting.menu, meeting.place, meeting.datetime,
				meeting.m_number, meeting.amity, meeting.ageGroup, 0,
				0, 0, 0, meeting.u_id);
		meetingService.insertMeeting(meetingDTO);
	}
	
	/*Meeting 게시글 보기*/
	/* GET localhost:8080/meeting/번호 */
	@GetMapping(value="/meeting/{m_id}")
	public MeetingDTO openMeetingDetail(@PathVariable("m_id") int m_id) throws Exception {
		return meetingService.selectMeetingDetail(m_id);
	}

	/*미팅 게시글 삭제 */
	/* u_id비교는 불가..*/
	@DeleteMapping(value="/meeting/{m_id}")
	public void deleteMeeting(@PathVariable("m_id") int m_id) throws Exception {
		meetingService.deleteMeeting(m_id);
	}
	
	/* 미팅 게시글 상태 수정 및 참가 */
	@PutMapping(value="/meeting/{m_id}")
    public void update(@PathVariable("m_id") int m_id, @RequestBody UpdateMeeting updateMeeting) throws Exception {
		
        MeetingDTO meeting = meetingService.selectMeetingDetail(m_id);
        
        /*parameter의 u_id와 게시글의 u_id가 같을시*/
    	if(meeting.getU_id() == updateMeeting.u_id) {
    		/*status를 받아옵니다*/
    		meeting.setStatus(updateMeeting.status);
			meetingService.startMeeting(meeting);
			
			/* 알림 생성 */
			String msg = "";
			String fmt = "참여하신 %d번 모임이 '%s' 상태로 변경되었어요!";
			String status = "";
			
			switch (meeting.getStatus()) {
			case 0:
				status = "모집";
				break;
			case 1:
				status = "확정";
				break;
			case 2:
				status = "계산";
				break;
			default:
				status = "알 수 없음";
			}
			msg = String.format(fmt, meeting.getM_id(), status);
			
			int[] notiList = { meeting.getU_id(), meeting.getParticipant_2(), meeting.getParticipant_3(), meeting.getParticipant_4() };
			
			for (int u_id : notiList) {
				if (u_id != 0) {
					NotiDTO notiDTO = new NotiDTO(0, u_id, meeting.getM_id(), msg);
					notiDAO.makenoti(notiDTO);
				}
				
			}
		}       
    	/*parameter의 u_id와 게시글의 u_id가 다를시*/
    	else {
    		/*action = join 일시*/
    		if (updateMeeting.action.equals("join")) {    	       		
        			if(meeting.getParticipant_2() == 0) {
        				meeting.setParticipant_2(updateMeeting.u_id);
        				meetingService.joinMeeting2(meeting);
        			}
        			else if(meeting.getParticipant_3() == 0) {
        				meeting.setParticipant_3(updateMeeting.u_id);
        				meetingService.joinMeeting3(meeting);
        			}
        			else if(meeting.getParticipant_4() == 0) {
        				meeting.setParticipant_4(updateMeeting.u_id);
        				meetingService.joinMeeting4(meeting);
        			}
        			/*모두 다 차면 참가 불가*/
        			else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This meeting is full");
        		}
        
    		/*action = cancel 일시*/
    		else if(updateMeeting.action.equals("cancel")) {
    			if(meeting.getParticipant_2() == updateMeeting.u_id) {
    				meeting.setParticipant_2(0);
    				meetingService.joinMeeting2(meeting);
    			}
    			else if(meeting.getParticipant_3() == updateMeeting.u_id) {
    				meeting.setParticipant_3(0);
    				meetingService.joinMeeting3(meeting);
    			}
    			else if(meeting.getParticipant_4() == updateMeeting.u_id) {
    				meeting.setParticipant_4(0);
    				meetingService.joinMeeting4(meeting);
    			}
    			/*참가자 목록에 없을 시*/
    			else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not in this meeting");
    		}
    		else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
    	}
	}
}
