package com.allpoint.model;

import java.util.Date;

import com.urbanairship.richpush.RichPushMessage;

/**
 * CustomRichPushMessage
 * 
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class CustomRichPushMessage {
	private RichPushMessage richPushMessage;
	private boolean isChecked = false;
	private boolean isSelected = false;

	public CustomRichPushMessage(final RichPushMessage richPushMessage) {
		this.richPushMessage = richPushMessage;
	}

	public void setRichPushMessage(final RichPushMessage richPushMessage) {
		this.richPushMessage = richPushMessage;
	}

	public CustomRichPushMessage setSelected(final boolean selected) {
		this.isSelected = selected;
		return this;
	}

	public CustomRichPushMessage setChecked(final boolean checked) {
		this.isChecked = checked;
		return this;
	}

	public CustomRichPushMessage setRead(final boolean read) {
		if (read) {
			this.richPushMessage.markRead();
		} else {
			this.richPushMessage.markUnread();
		}
		return this;
	}

	public boolean isRead() {
		return this.richPushMessage.isRead();
	}

	public String getMessageId() {
		return this.richPushMessage.getMessageId();
	}

	public String getTitle() {
		return this.richPushMessage.getTitle();
	}

	public Date getSendDate() {
		return this.richPushMessage.getSentDate();
	}

	public RichPushMessage getRichPushMessage() {
		return this.richPushMessage;
	}

	public boolean isChecked() {
		return this.isChecked;
	}

	public boolean isSelected() {
		return this.isSelected;
	}
}
