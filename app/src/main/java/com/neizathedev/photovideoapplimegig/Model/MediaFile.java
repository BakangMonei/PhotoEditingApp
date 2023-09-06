package com.neizathedev.photovideoapplimegig.Model;

import java.util.Objects;

/**
 * @Author: Monei Bakang Mothuti
 * @Date: Friday July 2023
 * @Time: 11:42 PM
 */

public class MediaFile {
    // Attributes
    private String email;
    private String fileName;
    private String fileUrl;
    private String fileType;

    //Super Constructor
    public MediaFile(){
    }

    // Constructors
    public MediaFile(String email, String fileName, String fileUrl, String fileType) {
        this.email = email;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    // Getters

    public String getEmail() {
        return email;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    // toString()

    @Override
    public String toString() {
        return "MediaFile[" +
                " email: " + email + '\'' +
                ", fileName: " + fileName + '\'' +
                ", fileUrl: " + fileUrl + '\'' +
                ", fileType: " + fileType + '\'' +
                ']';
    }

    // equals() & hashCodes()

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaFile mediaFile = (MediaFile) o;
        return email.equals(mediaFile.email) && fileName.equals(mediaFile.fileName) && fileUrl.equals(mediaFile.fileUrl) && fileType.equals(mediaFile.fileType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, fileName, fileUrl, fileType);
    }
}
