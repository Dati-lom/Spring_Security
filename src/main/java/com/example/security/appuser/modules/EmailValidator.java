package com.example.security.appuser.modules;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidator  implements Predicate<String> {
    @Override
    public boolean test(String s) {
        //Regex to validate EMAIl
        String pattern = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$";
        return s.matches(pattern);
    }
}
