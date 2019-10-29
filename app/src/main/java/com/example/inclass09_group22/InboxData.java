package com.example.inclass09_group22;

public class InboxData {
    String sender_fname, sender_lname, id, sender_id, receiver_id, message, subject, created_at, updated_at;

    @Override
    public String toString() {
        return "InboxData{" +
                "sender_fname='" + sender_fname + '\'' +
                ", sender_lname='" + sender_lname + '\'' +
                ", id='" + id + '\'' +
                ", sender_id='" + sender_id + '\'' +
                ", receiver_id='" + receiver_id + '\'' +
                ", message='" + message + '\'' +
                ", subject='" + subject + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                '}';
    }
}
