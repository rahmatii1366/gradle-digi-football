package ir.piana.dev.test.business.service;

import io.reactivex.Single;
import ir.piana.dev.test.server.api.dto.PostDto;
import ir.piana.dev.test.server.api.dto.ResponseDto;
import ir.piana.dev.test.server.api.service.TestApi;
import org.springframework.stereotype.Component;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/18/2019 2:21 PM
 **/
@Component
public class TestApiImpl implements TestApi {
    @Override
    public Single<ResponseDto> postTest(PostDto argument) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setEmail("hey");
        return Single.just(responseDto);
    }
}
