package com.chat.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chat.entity.Room;

public interface RoomRepo extends JpaRepository<Room, Integer> {

	Room findByRoomid(String roomid);
}
