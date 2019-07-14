package ir.piana.dev.user.business.data.service;

import io.reactivex.Completable;
import io.reactivex.Single;
import ir.piana.dev.core.api.exception.IllegalArgumentException;
import ir.piana.dev.core.api.exception.ResourceNotFoundException;
import ir.piana.dev.core.api.func.TransactionalAction;
import ir.piana.dev.core.api.func.TransactionalFunc0;
import ir.piana.dev.user.business.data.entity.UserEntity;
import ir.piana.dev.user.business.data.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/23/2019 3:10 PM
 **/
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Single<Boolean> isEmailExist(String email) {
        return Single.fromCallable(() ->
                userRepository.findByEmail(email))
                        .map(userEntity -> userEntity != null ? true : false);
    }

    public Single<UserEntity> getByEmail(String email) {
        return Single.fromCallable(() ->
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException()));
    }

    public Single<UserEntity> getByExample(String email) {
        return Single.fromCallable(() -> {
            ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
            Example<UserEntity> example = Example.of(UserEntity.from("email"), caseInsensitiveExampleMatcher);
            return userRepository.findOne(example)
                    .orElseThrow(() -> new ResourceNotFoundException("user not exist"));
        });
    }

    public Single<UserEntity> findOrCreate(String email) {
        return Single.fromCallable((TransactionalFunc0<UserEntity, ?>) () -> {
            ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
            Example<UserEntity> example = Example.of(UserEntity.from(email), caseInsensitiveExampleMatcher);
            Optional<UserEntity> one = userRepository.findOne(example);
            if (one.isPresent())
                return one.get();
            UserEntity userEntity = new UserEntity(email);
            userRepository.save(userEntity);
            return userEntity;
        });
    }

    public Completable resetPassword(Long userId, String passsword) {
        return Completable.fromAction((TransactionalAction<IllegalArgumentException>) () -> {
            Optional<UserEntity> one = userRepository.findById(userId);
            if (one.isPresent())
                throw new IllegalArgumentException("user not exist");
            UserEntity userEntity = one.get();
            userEntity.setPassword(passsword);
            userRepository.save(userEntity);
        });
    }
}
