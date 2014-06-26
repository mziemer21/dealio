package com.example.yelp;


public class Business {
	
	String establishment_id, mobile_url, rating, name, yelp_id, address, city, state, zip, display_phone, phone, distance;
	
	public String getEstablishmentId(){return establishment_id;}
	
	public String getName(){return name;}
    
    public String getRating(){return rating;}
     
    public String getMobileURL(){return mobile_url;} 
    
    public String getYelpId(){return yelp_id;}
    
    public String getDisplayPhone(){return display_phone;}
    
    public String getPhone(){return phone;}
    
    public String getDistance(){return distance;}
    
    public String getAddress(){return address;}
    
    public String getCity(){return city;}
    
    public String getZipcode(){return zip;}
    
    public String getState(){return state;}
    
    //setters
    
    public void setEstablishmentId(String val){establishment_id = val;}
    
    public void setName(String val){name = val;}
    
    public void setRating(String val){rating = val;}
     
    public void setMobileURL(String val){mobile_url = val;} 
    
    public void setYelpId(String val){yelp_id = val;}
    
    public void setDisplayPhone(String val){display_phone = val;}
    
    public void setPhone(String val){phone = val;}
    
    public void setDistance(String val){distance = val;}
    
    public void setAddress(String val){address = val;}
    
    public void setCity(String val){city = val;}
    
    public void setZipcode(String val){zip = val;}
    
    public void setState(String val){state = val;}

}
