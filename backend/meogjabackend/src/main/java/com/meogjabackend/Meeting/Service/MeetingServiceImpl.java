package com.meogjabackend.Meeting.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meogjabackend.Meeting.DTO.MeetingDTO;
import com.meogjabackend.Meeting.Mapper.MeetingMapper;



@Service
public class MeetingServiceImpl implements MeetingService {
	@Autowired
	private MeetingMapper meetingMapper;
	
	/*전체 Meeting 불러오기*/
	public List<MeetingDTO> selectMeetingList() throws Exception {
		return meetingMapper.selectMeetingList();
	}

	/*Meeting 생성*/
	public void insertMeeting(MeetingDTO meeting) throws Exception {
		meetingMapper.insertMeeting(meeting);
	}

	/*상세 Meeting 정보 보기*/
	public MeetingDTO selectMeetingDetail(int m_id) throws Exception {
		MeetingDTO meeting = meetingMapper.selectMeetingDetail(m_id);
		return meeting;
	}

	/*Meeting 삭제*/
	public void deleteMeeting(int m_id) throws Exception {
		meetingMapper.deleteMeeting(m_id);
	}
	
	/*status 변경*/
	public void startMeeting(MeetingDTO meeting) throws Exception{
		meetingMapper.startMeeting(meeting);
	}
	
	/*참가자2로 참가*/
	public void joinMeeting2(MeetingDTO meeting) throws Exception{
		meetingMapper.joinMeeting2(meeting);
	}

	/*참가자3로 참가*/
	public void joinMeeting3(MeetingDTO meeting) throws Exception{
		meetingMapper.joinMeeting3(meeting);
	}

	/*참가자4로 참가*/
	public void joinMeeting4(MeetingDTO meeting) throws Exception{
		meetingMapper.joinMeeting4(meeting);
	}
}