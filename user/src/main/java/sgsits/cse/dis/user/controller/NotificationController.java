package sgsits.cse.dis.user.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import sgsits.cse.dis.user.dto.ForwardNotificationDto;
import sgsits.cse.dis.user.dto.NotificationDto;
import sgsits.cse.dis.user.dto.SendNotificationRequestDto;
import sgsits.cse.dis.user.jwt.JwtResolver;
import sgsits.cse.dis.user.service.NotificationService;

import java.util.List;

/**
 * The type User notification controller.
 */
@Api(value = "Notification Controller")
@RestController
@Controller
@RequestMapping(path = "/userNotificationController")
public class NotificationController {

    /**
     * The Notification service.
     */
    private final NotificationService notificationService;

    /**
     * The constant LOG.
     */
    public static Logger LOG = LoggerFactory.getLogger(NotificationController.class);

    /**
     * Instantiates a new User notification controller.
     *
     * @param notificationService the notification service
     */
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Gets all notifications.
     *
     * @param authHeader the auth header
     * @return the user name
     */
    @GetMapping(value = "/getAllNotifications")
    public List<NotificationDto> getUserName(@RequestHeader("Authorization") final String authHeader) {
        final String username = JwtResolver.getUsernameFromAuthHead(authHeader);
        return notificationService.getAllNotification(username);
    }

    /**
     * Mark as read.
     *
     * @param notificationId the notification id
     * @param authHeader     the auth header
     */
    @GetMapping(value = "/markAsRead/{notificationId}")
    public void markAsRead(@PathVariable("notificationId") final String notificationId,
                           @RequestHeader("Authorization") final String authHeader) {
        final String username = JwtResolver.getUsernameFromAuthHead(authHeader);
        notificationService.markAsRead(notificationId, username);
    }

    /**
     * Mark  all as read.
     *
     * @param authHeader the auth header
     */
    @GetMapping(value = "/markAllAsRead")
    public void markAllAsRead(@RequestHeader("Authorization") final String authHeader) {
        final String username = JwtResolver.getUsernameFromAuthHead(authHeader);
        notificationService.markAllAsRead(username);
    }

    /**
     * Send notification to all.
     *
     * @param notificationRequest the notification request
     */
    @PostMapping(value = "/sendNotificationToAll")
    public void sendNotificationToAll(@RequestBody final SendNotificationRequestDto notificationRequest) {
        notificationService.sendNotificationToAll(notificationRequest.getNotification());
    }

    /**
     * Send notification to participants.
     *
     * @param notificationRequest the notification request
     */
    @PostMapping(value = "/sendNotificationToParticipants")
    public void sendNotificationToParticipants(@RequestBody final SendNotificationRequestDto notificationRequest) {
        notificationService.sendNotificationToParticipants(notificationRequest.getNotification(),
                notificationRequest.getUsernameList());
    }

    /**
     * Send notification to all except.
     *
     * @param notificationRequest the notification request
     */
    @PostMapping(value = "/sendNotificationByTypeExceptGivenUsers")
    public void sendNotificationToAllExcept(@RequestBody final SendNotificationRequestDto notificationRequest) {
        notificationService.sendNotificationByTypeExceptGivenUsers(notificationRequest.getNotification(),
                notificationRequest.getTypeList(),
                notificationRequest.getUsernameList());
    }

    /**
     * Send notification to all except.
     *
     * @param notificationRequest the notification request
     */
    @PostMapping(value = "/sendNotificationByType")
    public void sendNotificationByType(@RequestBody final SendNotificationRequestDto notificationRequest) {
        notificationService.sendNotificationByType(notificationRequest.getNotification(),
                notificationRequest.getTypeList());
    }

    /**
     * Forward notification.
     *
     * @param forwardRequest the forward request
     */
    @PostMapping(value = "/forwardNotification")
    public void forwardNotification(@RequestBody final ForwardNotificationDto forwardRequest) {
        notificationService.forwardNotification(forwardRequest.getNotificationId(),
                forwardRequest.getUsernameList(), forwardRequest.getComment());
    }
    

    /**
     * 
     * Delete notification.
     * 
     * @param notificationId
     * @param authHeader
     */
    @GetMapping(value = "/deleteNotification/{notificationId}")
    public void deleteNotification(@PathVariable("notificationId") final String notificationId,@RequestHeader("Authorization") final String authHeader) {
        final String username = JwtResolver.getUsernameFromAuthHead(authHeader);
        notificationService.deleteNotification(notificationId, username);
    }
    
    
    /**
     * Mark as favourite.
     * 
     * @param notificationId
     * @param authHeader
     */
    @GetMapping(value = "/markAsFavourite/{notificationId}")
    public void markAsFavourite(@PathVariable("notificationId") final String notificationId,@RequestHeader("Authorization") final String authHeader) {
        final String username = JwtResolver.getUsernameFromAuthHead(authHeader);
        notificationService.markAsFavourite(notificationId, username);
    }
}
    
    
    