package com.contact.manager.services.impl;

import com.contact.manager.dto.MessageDTO;
import com.contact.manager.entities.Message;
import com.contact.manager.entities.Teacher;
import com.contact.manager.exceptions.ResourceNotFoundException;
import com.contact.manager.repositories.MessageRepository;
import com.contact.manager.services.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TeacherServiceImpl teacherService;

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${project.image}")
    private String path;

    @Override
    public MessageDTO sendMessage(MessageDTO messageDTO, MultipartFile messageAttachment,
                                  Long senderId, Long receiverId) throws IOException {
        Message message = this.dtoToMessage(messageDTO);

        // if have attachment
        if (!Objects.equals(messageAttachment.getOriginalFilename(), "")) {
            String alumniImageName = this.fileService.uploadImage(path, messageAttachment);
            message.setAttachment(alumniImageName);
        }

        message.setSender(this.teacherService.getTeacherById(senderId));
        message.setReceiver(this.teacherService.getTeacherById(receiverId));
        return this.messageToDTO(this.messageRepository.save(message));
    }

    @Override
    public boolean messageConfirmed(Long messageId) {
        this.messageRepository.save(this.getMessageById(messageId));
        return true;
    }

    @Override
    public List<MessageDTO> getAllMessageBySender(Long senderId) {
        Teacher sender = this.teacherService.getTeacherById(senderId);
        return this.messageRepository
                .findBySender(sender)
                .stream()
                .map(this::messageToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getAllMessageByReceiver(Long receiverId) {
        Teacher receiver = this.teacherService.getTeacherById(receiverId);
        return this.messageRepository
                .findByReceiver(receiver)
                .stream()
                .map(this::messageToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<MessageDTO> getAllMessageBySenderAndReceiver(Long senderId, Long receiverId) {
        Teacher sender = this.teacherService.getTeacherById(senderId);
        Teacher receiver = this.teacherService.getTeacherById(receiverId);
        return this.messageRepository
                .findBySenderAndReceiver(sender, receiver)
                .stream()
                .map(this::messageToDTO)
                .collect(Collectors.toList());
    }

    // get message by id
    public Message getMessageById(Long messageId) {
        return this.messageRepository.findById(messageId).orElseThrow(() ->
                new ResourceNotFoundException("message", "id", messageId));
    }

    // message to DTO
    public MessageDTO messageToDTO(Message message) {
        return this.modelMapper.map(message, MessageDTO.class);
    }

    // dto to teacher
    public Message dtoToMessage(MessageDTO messageDTO) {
        return this.modelMapper.map(messageDTO, Message.class);
    }

}
