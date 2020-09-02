package com.stepin.coronaapp.model;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    String status;
    String msg;
    UserData data;
    String conversationId;

    public User() {
    }

    public User(String status, String msg, UserData data, String conversationId) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.conversationId = conversationId;
    }

    public String getStatus() {
        return status;
    }

    public UserData getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public class UserData {
        int id, age, collection_id;
        String firstName, lastName, email, gender, state, profileImage, enforce_level, gover_level, border_level, gbv_level, chv_level;

        public UserData(int id, int age, int collection_id, String firstName, String lastName, String email, String gender, String state, String profileImage,String enforce_level,String gover_level,String border_level,String gbv_level,String chv_level) {
            this.id = id;
            this.age = age;
            this.collection_id = collection_id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.gender = gender;
            this.state = state;
            this.profileImage = profileImage;
            this.enforce_level = enforce_level;
            this.gover_level = gover_level;
            this.border_level = border_level;
            this.gbv_level = gbv_level;
            this.chv_level = chv_level;
        }

        public UserData() {
        }

        public int getId() {
            return id;
        }

        public int getCollection_id() {
            return collection_id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() { return lastName; }

        public String getEmail() {
            return email;
        }

        public String getGender() {
            return gender;
        }

        public int getAge() {
            return age;
        }

        public String getState() {
            return state;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getBorder_level() {
            return border_level;
        }

        public String getChv_level() {
            return chv_level;
        }

        public String getEnforce_level() {
            return enforce_level;
        }

        public String getGbv_level() {
            return gbv_level;
        }

        public String getGover_level() {
            return gover_level;
        }

        public void setBorder_level(String border_level) {
            this.border_level = border_level;
        }

        public void setChv_level(String chv_level) {
            this.chv_level = chv_level;
        }

        public void setGbv_level(String gbv_level) {
            this.gbv_level = gbv_level;
        }

        public void setGover_level(String gover_level) {
            this.gover_level = gover_level;
        }

        public void setEnforce_level(String enforce_level) {
            this.enforce_level = enforce_level;
        }
    }

    public User getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Utils.sharedPerfName, Context.MODE_PRIVATE);
        String firstName = preferences.getString("first_name", null);
        String lastName = preferences.getString("last_name", null);
        String email = preferences.getString("email", null);
        String gender = preferences.getString("gender", null);
        String state = preferences.getString("state", null);
        String conversationId = preferences.getString("conversation_id", null);
        String profileImage = preferences.getString("profile_image", "");
        String enforce_level = preferences.getString("enforce_level","0");
        String gover_level = preferences.getString("gover_level","0");
        String border_level = preferences.getString("border_level","0");
        String gbv_level = preferences.getString("gbv_level","0");
        String chv_level = preferences.getString("chv_level","0");
        int age = preferences.getInt("age", -1);
        int id = preferences.getInt("id", -1);
        int collection_id = preferences.getInt("collection_id", -1);

        if (firstName == null || lastName == null || enforce_level == null || gover_level == null || border_level == null || gbv_level == null || chv_level == null || email == null || gender == null || state == null || age == -1 || id == -1 || conversationId == null || collection_id == -1) {
            return null;
        }

        UserData data = new UserData(id, age, collection_id, new Utils().capitalize(firstName), new Utils().capitalize(lastName), email, new Utils().capitalize(gender), new Utils().capitalize(state), profileImage,enforce_level,gover_level,border_level,gbv_level,chv_level);
        return new User("success", "", data, conversationId);
    }

    public String getConversationId() {
        return conversationId;
    }
}
