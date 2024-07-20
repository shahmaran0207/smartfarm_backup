package com.itbank.smartFarm.controller;

import com.itbank.smartFarm.service.ChatService;
import com.itbank.smartFarm.vo.MemberVO;
import com.itbank.smartFarm.vo.MessageVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class MessageController {

    @Autowired
    private ChatService cs;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    @GetMapping("/{senderId}/{receiverId}")
    public List<MessageVO> getMessages(@PathVariable int senderId, @PathVariable int receiverId) {
        return cs.getMessagesBySenderAndReceiver(senderId, receiverId);
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(MessageVO message) {
        cs.saveMessage(message);
        MessageVO savedMessage = cs.getLastMessage();
        messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiverId(), savedMessage);
        messagingTemplate.convertAndSend("/topic/messages/" + message.getSenderId(), savedMessage);
    }
}
