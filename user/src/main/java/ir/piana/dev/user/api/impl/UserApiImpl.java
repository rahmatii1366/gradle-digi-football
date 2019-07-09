package ir.piana.dev.user.api.impl;

import io.reactivex.Single;
import ir.piana.dev.user.business.operation.UserOperation;
import ir.piana.dev.user.server.api.dto.*;
import ir.piana.dev.user.server.api.service.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 6:19 PM
 **/
@Component
public class UserApiImpl implements UserApi {

    @Autowired
    private UserOperation userOperation;

    @Override
    public Single<ResponseDto> signup(SignUpDto argument) {
        return userOperation.sendVerification(argument.getEmail(), argument.getPassword())
                .map(b -> {
                    ResponseDto responseDto = new ResponseDto();
                    if(b) {
                        responseDto.setCode(0);
                        responseDto.setCargo("verification link send to email");
                    } else {
                        responseDto.setCode(1);
                        responseDto.setCargo("signup failed");
                    }
            return responseDto;
        });
    }

    @Override
    public Single<ResponseDto> signupVerify(String link, String mail) {
        return userOperation.verifyEmail(link, mail).map(b -> {
            ResponseDto responseDto = new ResponseDto();
            if(b) {
                responseDto.setCode(0);
                responseDto.setCargo("verification successfully");
            } else {
                responseDto.setCode(1);
                responseDto.setCargo("verification failed");
            }
            return responseDto;
        });
    }

    @Override
    public Single<ResponseDto> resetPassword(SignupPasswordDto argument) {
        return userOperation.setPassword(argument.getPassword(), argument.getPassword())
                .map(b -> {
                    ResponseDto responseDto = new ResponseDto();
                    if(b) {
                        responseDto.setCode(0);
                        responseDto.setCargo("password reset");
                    } else {
                        responseDto.setCode(1);
                        responseDto.setCargo("password not reset");
                    }
                    return responseDto;
                });
    }

    @Override
    public Single<ResponseDto> login(LoginDto argument) {
        return null;
    }

    @Override
    public Single<ResponseDto> retrievePersonInfo() {
        return null;
    }

    @Override
    public Single<ResponseDto> savePersonInfo(PersonInfoDto argument) {
        return null;
    }

    @Override
    public Single<SampleDto> sayHello() {
        return Single.just(new SampleDto().builder().message("hello world!").build());
    }

    @Override
    public Single<ResponseDto> setPersonPicture(PersonPictureDto argument) {
        return null;
    }

    @Override
    public Single<ResponseDto> unsetPersonPicture() {
        return null;
    }

    @Override
    public Single<ResponseDto> updatePersonInfo(PersonInfoDto argument) {
        return null;
    }
}
