package ir.piana.dev.user.business.data.service;

import io.reactivex.Single;
import ir.piana.dev.user.business.data.entity.UserEntity;
import ir.piana.dev.user.business.data.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/23/2019 3:10 PM
 **/
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Single<Boolean> isEmailExist(String email) {
        return Single.fromCallable(() ->
                userRepository.findByEmail(email))
                        .map(userEntity -> userEntity != null ? true : false);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Single<UserEntity> getByEmail(String email) {
        return Single.fromCallable(() ->
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException()));
    }
}
