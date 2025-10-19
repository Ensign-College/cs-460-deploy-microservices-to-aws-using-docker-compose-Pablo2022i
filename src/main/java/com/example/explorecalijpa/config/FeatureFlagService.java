package com.example.explorecalijpa.config;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

// Checks if a feature is on or off
@Component
public class FeatureFlagService {

  private final Environment env;

  public FeatureFlagService(Environment env) {
    this.env = env;
  }

  public boolean isEnabled(String featureName) {
    return env.getProperty("features." + featureName, Boolean.class, false);
  }
}
