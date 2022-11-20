package com.contact.manager.services;

import com.contact.manager.dto.MessageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MessageService {

    // send message
    MessageDTO sendMessage(MessageDTO messageDTO, MultipartFile messageFile, Long senderId, Long receiverId) throws IOException;

    // message confirmed
    boolean messageConfirmed(Long messageId);

    // get all message by sender
    List<MessageDTO> getAllMessageBySender(Long senderId);

    // get all message by receiver
    List<MessageDTO> getAllMessageByReceiver(Long receiverId);

    // get all message by sender & receiver
    List<MessageDTO> getAllMessageBySenderAndReceiver(Long senderId, Long receiverId);

}
