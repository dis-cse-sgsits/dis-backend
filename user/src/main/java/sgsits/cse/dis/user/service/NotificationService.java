package sgsits.cse.dis.user.service;

import sgsits.cse.dis.user.dto.NotificationDto;
import sgsits.cse.dis.user.model.Notification;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * The interface Notification service.
 */
public interface NotificationService {
    /**
     * Gets all notification.
     *
     * @param username the username
     * @return the all notification
     * @throws EntityNotFoundException the entity not found exception
     */
    List<NotificationDto> getAllNotification(final String username) throws EntityNotFoundException;

    /**
     * Send notification notification.
     *
     * @param notification the notification
     * @return the notification
     */
    void sendNotificationToAll(final Notification notification);

    /**
     * Send to participants notification.
     *
     * @param notification the notification
     * @param usernameList the username list
     * @return the notification
     */
    void sendNotificationToParticipants(final Notification notification, final List<String> usernameList);

    /**
     * Send notification to all except.
     *
     * @param notification the notification
     * @param typeList     the type list
     * @param usernameList the username list
     */
    void sendNotificationByTypeExceptGivenUsers(final Notification notification, final List<String> typeList, final List<String> usernameList);

    /**
     * Send notification by type.
     *
     * @param notification the notification
     * @param types        the types
     */
    void sendNotificationByType(final Notification notification, final List<String> types);

    /**
     * Mark as read.
     *
     * @param notificationId the notification id
     * @param username       the username
     */
    void markAsRead(final String notificationId, final String username);

    /**
     * Mark all as read.
     *
     * @param username the username
     */
    void markAllAsRead(final String username);

    /**
     * Forward notification.
     *
     * @param notificationId the notification id
     * @param usernameList   the username list
     * @param comment        the comment
     */
    void forwardNotification(final String notificationId, final List<String> usernameList, final String comment);
    
    /**
     * Delete notification.
     * 
     * @param notificationId
     * @param username
     */
    void deleteNotification(final String notificationId, final String username);
    
    /**
     * Mark as favourite.
     * 
     * @param notificationId
     * @param username
     */
    void markAsFavourite(final String notificationId, final String username);
    
    
}
