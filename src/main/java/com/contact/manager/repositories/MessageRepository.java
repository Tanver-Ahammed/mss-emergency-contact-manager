package com.contact.manager.repositories;

import com.contact.manager.entities.Message;
import com.contact.manager.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySender(Teacher sender);

    List<Message> findByReceiver(Teacher receiver);

    List<Message> findBySenderAndReceiver(Teacher sender, Teacher receiver);

}
