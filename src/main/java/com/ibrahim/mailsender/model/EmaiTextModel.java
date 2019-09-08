package com.ibrahim.mailsender.model;

/**
 *
 * @author Ibrahim Chowdhury
 */
public class EmaiTextModel {
    
    public int id;
    public String subject;
    public String body;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
    
    
    
}
