package ir.piana.dev.test.api.impl;

import io.reactivex.Completable;
import io.reactivex.Single;
import ir.piana.dev.test.server.api.dto.PostDto;
import ir.piana.dev.test.server.api.dto.ResponseDto;
import ir.piana.dev.test.server.api.service.TestApi;
import ir.piana.dev.user.client.api.service.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/18/2019 2:21 PM
 **/
@Component
public class TestApiImpl implements TestApi {
    @Autowired
    private UserApi userApi;

    @Override
    public Completable getTest() {

        return Completable.fromAction(() -> {
            userApi.sayHello().subscribe(System.out::println);
        });
    }

    @Override
    public Single<ResponseDto> postTest(PostDto argument) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setEmail("hey");


        return Single.just(responseDto);
    }
}
