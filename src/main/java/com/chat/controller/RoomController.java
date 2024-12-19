package com.chat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.chat.entity.Message;
import com.chat.entity.Room;
import com.chat.repo.RoomRepo;

@RestController
@RequestMapping("/room")
@CrossOrigin("http://localhost:5173")
public class RoomController {
   
	@Autowired
	private RoomRepo roomrepo;
	
	//CREATE ROOM
	@PostMapping("")
	public ResponseEntity<?> createroom(@RequestBody String roomid) {		
		//check if already exists
		System.out.println("called");
		if(roomrepo.findByRoomid(roomid)!=null) {
			return ResponseEntity.badRequest().body("Room ALready Exists");
		}	
		//else
		Room room=new Room();
		room.setRoomid(roomid);
		Room save = roomrepo.save(room);
		return ResponseEntity.status(HttpStatus.CREATED).body(room);
	}
	
	
	
	//GET ROOM
	@GetMapping("/{roomid}")
	public ResponseEntity<?> getroom(@PathVariable String roomid){

		Room room = roomrepo.findByRoomid(roomid);

		//check if not exists
		if(room==null) {
			return ResponseEntity.badRequest().body("Room not found ");
		}

		return ResponseEntity.ok(room);
	}
	
	
	//GET MESSAGES FROM ROOM
	@GetMapping("/{roomid}/messages")
	public ResponseEntity<List<Message>> getmessage(@PathVariable String roomid){
		
		Room room = roomrepo.findByRoomid(roomid);
		//check if not exists
		if(room==null) {
			return ResponseEntity.badRequest().build();
		}
		List<Message> messages = room.getMessages();
		
		return ResponseEntity.ok(messages);
	}
	
}
