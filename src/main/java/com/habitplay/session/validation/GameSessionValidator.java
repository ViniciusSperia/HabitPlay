package com.habitplay.session.validation;

import com.habitplay.session.dto.request.GameSessionRequest;
import com.habitplay.user.model.User;

public interface GameSessionValidator {
    void validate(GameSessionRequest request, User currentUser);
}
