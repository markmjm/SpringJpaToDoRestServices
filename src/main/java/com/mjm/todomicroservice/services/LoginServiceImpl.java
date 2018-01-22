package com.mjm.todomicroservice.services;

import com.mjm.todomicroservice.doas.UserDao;
import com.mjm.todomicroservice.entities.User;
import com.mjm.todomicroservice.utilities.EncryptionUtils;
import com.mjm.todomicroservice.utilities.JwtUtils;
import com.mjm.todomicroservice.utilities.UserNotInDatabaseException;
import com.mjm.todomicroservice.utilities.UserNotLoggedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;

    @Autowired
    EncryptionUtils encryptionUtils;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public Optional<User> getUserFromDb(String email, String pwd) throws UserNotInDatabaseException {
        Optional<User> userr = userDao.findUserByEmail(email);
        if (userr.isPresent()) {
            User user = userr.get();
            if (!encryptionUtils.decrypt(user.getPassword()).equals(pwd)) {
                throw new UserNotInDatabaseException("Wrong email or password");
            }
        }else {
            throw new UserNotInDatabaseException("Invalid email");
        }
        return userr;
    }

    @Override
    public String createJwt(String email, String name, Date date) throws UnsupportedEncodingException {
        date.setTime(date.getTime() + (300*1000));
        return jwtUtils.generateJwt(email, name, date);
    }

    @Override
    public Map<String, Object> verifyJwtAndGetData(HttpServletRequest request) throws UnsupportedEncodingException, UserNotLoggedException{
        String jwt = jwtUtils.getJwtFromHttpRequest(request);
        if(jwt == null){
            throw new UserNotLoggedException("User is not not logged.  Login first.");
        }
        return  jwtUtils.jwt2Map(jwt);
    }
}
