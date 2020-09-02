package com.stepin.coronaapp.model;

public class EnforceLocal {
    public int id;
    public String user_id;
    public String enforce_type;
    public String title;
    public String description;
    public String created;
    public EnforceLocal(int id, String luser_id, String enforce_type, String ltitle,  String ldescription,String created_time){
        this.id = id;
        this.user_id = luser_id;
        this.enforce_type = enforce_type;
        this.title = ltitle;
        this.description = ldescription;
        this.created = created_time;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEnforce_type() {
        return enforce_type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnforce_type(String enforce_type) {
        this.enforce_type = enforce_type;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
