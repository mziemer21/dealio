package com.example.yelp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YelpParser {
    
    private String yelp_response;
    JSONArray businesses;
     
    /**
     * Sets Yelp's response for this class
     * @param response
     */
    public void setResponse(String response){yelp_response = response;}
     
    /**
     * Returns the set Yelp response
     * @return string yelp_response
     */
    public String getResponse(){return yelp_response;}
     
    public void parseBusiness() throws JSONException{
        JSONObject o1 = new JSONObject(yelp_response);
        businesses = o1.getJSONArray("businesses");
    }
    
    public Integer getJSONSize(){return businesses.length();}
     
    public String getBusinessName(int i) throws JSONException{return businesses.getJSONObject(i).get("name").toString();}
    
    public String getBusinessRating(int i) throws JSONException{return businesses.getJSONObject(i).get("rating").toString();}
     
    public String getBusinessMobileURL(int i) throws JSONException{ return businesses.getJSONObject(i).get("mobile_url").toString();} 
    
    public String getBusinessId(int i) throws JSONException{ return businesses.getJSONObject(i).get("id").toString();}
    
    public String getBusinessDisplayPhone(int i) throws JSONException{ return businesses.getJSONObject(i).get("display_phone").toString();}
    
    public String getBusinessPhone(int i) throws JSONException{ return businesses.getJSONObject(i).get("phone").toString();}
    
    public String getBusinessDistance(int i) throws JSONException{ return businesses.getJSONObject(i).get("distance").toString();}
    
    public Object getBusinessLocation(int i) throws JSONException { return businesses.getJSONObject(i).get("location");}
    
    public String getBusinessAddress(JSONObject location) throws JSONException{ return location.get("address").toString().replaceAll("\\[", "").replaceAll("\\]","").replace("\"","");}
    
    public String getBusinessCity(JSONObject location) throws JSONException{ return location.get("city").toString();}
    
    public String getBusinessZipcode(JSONObject location) throws JSONException{ return location.get("postal_code").toString();}
    
    public String getBusinessState(JSONObject location) throws JSONException{ return location.get("state_code").toString();}
    
}
