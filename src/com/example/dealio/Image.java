package com.example.dealio;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Image")
public class Image extends ParseObject {
 
    public Image() {
        // A default constructor is required.
    }
 
    public String getTitle() {
        return getString("title");
    }
 
    public void setTitle(String title) {
        put("title", title);
    }
 
    public ParseUser getUser() {
        return getParseUser("user");
    }
 
    public void setUser(ParseUser user) {
        put("user", user);
    }
 
    public String getTags() {
        return getString("tags");
    }
 
    public void setTags(String tags) {
        put("tags", tags);
    }
 
    public ParseFile getImageFile() {
        return getParseFile("image");
    }
 
    public void setImageFile(ParseFile file) {
        put("image", file);
    }
    
    public ParseObject getEstablishment() {
        return getParseObject("establishment");
    }
 
    public void setEstablishment(ParseObject parseObject) {
        put("establishment", parseObject);
    }
 
}
