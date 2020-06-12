package sgsits.cse.dis.user.dto;

import java.util.Date;

/**
 * The type Notification dto.
 */
public class NotificationDto {

    /**
     * The Id.
     */
    private String id;
    /**
     * The Heading.
     */
    private String heading;
    /**
     * The Description.
     */
    private String description;
    /**
     * The Link.
     */
    private String link;
    /**
     * The Is read.
     */
    private Boolean isRead;
    /**
     * The Date.
     */
    private Date date;
    /**
     * The Comment.
     */
    private String comment;
    
    /**
     * The Is favourite
     */
    private Boolean isFavourite;
    

    /**
     * Instantiates a new Notification dto.
     */
    public NotificationDto() {
    }

    /**
     * Instantiates a new Notification dto.
     *
     * @param id          the id
     * @param heading     the heading
     * @param description the description
     * @param link        the link
     * @param isRead      the is read
     * @param date        the date
     * @param comment     the comment
     * @param isFavourite the is favourite
     */
    public NotificationDto(final String id, final String heading, final String description,
                           final String link, Boolean isRead, final Date date, final String comment,
                           Boolean isFavourite) {
        this.id = id;
        this.heading = heading;
        this.description = description;
        this.link = link;
        this.isRead = isRead;
        this.date = date;
        this.comment = comment;
        this.isFavourite = isFavourite;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
		return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
		this.date = date;
	}

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets heading.
     *
     * @return the heading
     */
    public String getHeading() {
        return heading;
    }

    /**
     * Sets heading.
     *
     * @param heading the heading
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets link.
     *
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets link.
     *
     * @param link the link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Gets read.
     *
     * @return the read
     */
    public Boolean getRead() {
        return isRead;
    }

    /**
     * Sets read.
     *
     * @param read the read
     */
    public void setRead(Boolean read) {
        isRead = read;
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

	public Boolean getIsFavourite() {
		return isFavourite;
	}

	public void setIsFavourite(Boolean favourite) {
		this.isFavourite = favourite;
	}
}
