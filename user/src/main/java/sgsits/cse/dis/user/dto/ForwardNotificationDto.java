package sgsits.cse.dis.user.dto;

import java.util.List;

/**
 * The type Forward notification dto.
 */
public class ForwardNotificationDto {

	/**
	 * The Username list.
	 */
	private List<String> usernameList;
	/**
	 * The Notification id.
	 */
	private String notificationId;
	/**
	 * The Comment.
	 */
	private String comment;

	/**
	 * Instantiates a new Forward notification dto.
	 *
	 * @param usernameList the username list
	 */
	public ForwardNotificationDto(List<String> usernameList) {
		this.usernameList = usernameList;
	}

	/**
	 * Instantiates a new Forward notification dto.
	 *
	 * @param usernameList   the username list
	 * @param notificationId the notification id
	 * @param comment        the comment
	 */
	public ForwardNotificationDto(List<String> usernameList, String notificationId, String comment) {
		this.usernameList = usernameList;
		this.notificationId = notificationId;
		this.comment = comment;
	}

	/**
	 * Gets username list.
	 *
	 * @return the username list
	 */
	public List<String> getUsernameList() {
		return usernameList;
	}

	/**
	 * Sets username list.
	 *
	 * @param usernameList the username list
	 */
	public void setUsernameList(List<String> usernameList) {
		this.usernameList = usernameList;
	}

	/**
	 * Gets notification id.
	 *
	 * @return the notification id
	 */
	public String getNotificationId() {
		return notificationId;
	}

	/**
	 * Sets notification id.
	 *
	 * @param notificationId the notification id
	 */
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	/**
	 * Gets comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets comment.
	 *
	 * @param comment the comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
