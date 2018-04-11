package com.techouts.pcomplaints.entities;


import com.techouts.pcomplaints.data.Tools;

/**
 * Created by TO-OW109 on 26-03-2018.
 */

public class ChatMessage {

    private String text;
    private String friendId;
    private String friendName;
    private String friendPhoto;
    private String senderId;
    private String senderName;
    private String senderPhoto;
    private String timestamp;

    public ChatMessage(String text, String timestamp, String friendId, String friendName,
                       String friendPhoto, String senderId, String senderName, String senderPhoto) {
        this.text = text;
        this.timestamp = timestamp;
        this.friendId=friendId;
        this.friendName=friendName;
        this.friendPhoto=friendPhoto;
        this.senderId=senderId;
        this.senderName=senderName;
        this.senderPhoto=senderPhoto;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReadableTime()
    {
        try {
            return Tools.formatTime(Long.valueOf(timestamp));
        }
        catch (NumberFormatException ignored) {
            return null;
        }
    }

    public Friend getReceiver() {
        return new Friend(friendId,friendName,"",friendPhoto);
    }

    public Friend getSender() {
        return new Friend(senderId,senderName,"", senderPhoto);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public long getComparableTimestamp()
    {
        return Long.parseLong(timestamp);
    }
}
