package org.example.gameapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HeroNotFoundException extends RuntimeException {
    public HeroNotFoundException(int id){
        super("герой не найден под id" + id);
    }
}
