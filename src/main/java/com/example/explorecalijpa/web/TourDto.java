package com.example.explorecalijpa.web;

import com.example.explorecalijpa.model.Difficulty;
import com.example.explorecalijpa.model.Region;

public class TourDto {
  private String title;
  private String description;
  private String blurb;
  private Integer price;
  private String duration;
  private String bullets;
  private String keywords;
  private String tourPackageCode;
  private Difficulty difficulty;
  private Region region;

  // getters and setters
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getBlurb() {
    return blurb;
  }

  public void setBlurb(String blurb) {
    this.blurb = blurb;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getBullets() {
    return bullets;
  }

  public void setBullets(String bullets) {
    this.bullets = bullets;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getTourPackageCode() {
    return tourPackageCode;
  }

  public void setTourPackageCode(String tourPackageCode) {
    this.tourPackageCode = tourPackageCode;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }
}
