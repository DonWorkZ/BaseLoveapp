package com.school.loveapp;

public class USERList {

    private String userID;
    private String userName;
    private String userAGE;
    private String userGender;
    private String userInter;
    private String userMSG;
    private byte[] userImage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAGE() {
        return userAGE;
    }

    public void setUserAGE(String userAGE) {
        this.userAGE = userAGE;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserInter() {
        return userInter;
    }

    public void setUserInter(String userInter) {
        this.userInter = userInter;
    }

    public byte[] getUserImage() {
        return userImage;
    }

    public void setUserImage(byte[] userImage) {
        this.userImage = userImage;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserMSG() {
        return userMSG;
    }

    public void setUserMSG(String userMSG) {
        this.userMSG = userMSG;
    }
}
