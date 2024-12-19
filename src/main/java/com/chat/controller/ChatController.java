package com.chat.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.chat.entity.Message;
import com.chat.entity.Room;
import com.chat.helper.MessageRequest;
import com.chat.repo.MessageRepo;
import com.chat.repo.RoomRepo;

import jakarta.transaction.Transactional;

@CrossOrigin("http://localhost:5173")
@Controller
public class ChatController {

	@Autowired
	private RoomRepo roomrepo;
	
	@Autowired
	private MessageRepo messagerepo;

	// for sending and receiving message
	@MessageMapping("/sendmessage/{roomid}")//  "/app/sendmessage"  chat will be send to this url
	@SendTo("/topic/room/{roomid}")                      //  here client will subcribe
	@Transactional
	public Message sendMessage(@DestinationVariable String roomid, @RequestBody MessageRequest req) {

		Message msg = new Message();
		msg.setContent(req.getContent());
		msg.setSender(req.getSender());
		msg.setTimestamp(LocalDateTime.now());

		Room room = roomrepo.findByRoomid(roomid);
		if (room != null) {
			room.getMessages().add(msg);
			msg.setRoom(room);
			roomrepo.save(room);
		}else {
			throw new RuntimeException("Room Not Found Exception");
		}

		return msg;
	}
}