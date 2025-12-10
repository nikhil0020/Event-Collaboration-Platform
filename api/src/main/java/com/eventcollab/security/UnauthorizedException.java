/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.eventcollab.security;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
      super(message);
    }
}
