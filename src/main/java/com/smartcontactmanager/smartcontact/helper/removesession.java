package com.smartcontactmanager.smartcontact.helper;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Component
public class removesession {

    public void removeVerificationMessageFromSession() {

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                    .getRequest();
            HttpSession session = request.getSession();
            session.removeAttribute("message");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }
}
