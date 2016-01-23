package com.zchu.log;

/**
 * @author Orhan Obut
 */
public enum LogLevel {

  /**
   * Prints all logs and Written all file
   */
  FULL,
  /**
   * Written to the file
   */
  FILE,
  ERROR,

  /**
   * No log will be printed and Don't write to the file
   */
  NONE
}
