package com.stepin.coronaapp.model;

import java.util.List;

public class ConversationMessage {

    public String status;
    public String msg;
    public List<Data> allMsg;

    public static class Data {

        public int id, senderId, receiverId, parentId, seen;
        public String conversationId, createdAt, updated_at, msg;

        public Data(int senderId, String conversationId, String createdAt, String msg) {
            this.id = 0;
            this.receiverId = 1;
            this.parentId = 0;
            this.seen = 0;
            this.msg = msg;
            this.senderId = senderId;
            this.conversationId = conversationId;
            this.createdAt = createdAt;
            this.updated_at = null;
        }

        public String getMsg() {
            return msg;
        }

        public Data() {
        }

        public int getId() {
            return id;
        }

        public int getSenderId() {
            return senderId;
        }

        public int getReceiverId() {
            return receiverId;
        }

        public int getParentId() {
            return parentId;
        }

        public int getSeen() {
            return seen;
        }

        public String getConversationId() {
            return conversationId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdated_at() {
            return updated_at;
        }
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<Data> getAllMsg() {
        return allMsg;
    }
}
