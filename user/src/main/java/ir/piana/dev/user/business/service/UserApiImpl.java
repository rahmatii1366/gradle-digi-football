package ir.piana.dev.user.business.service;

import io.reactivex.Single;
import ir.piana.dev.user.server.api.dto.PostDto;
import ir.piana.dev.user.server.api.dto.ResponseDto;
import ir.piana.dev.user.server.api.service.UserApi;
import org.springframework.stereotype.Component;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/16/2019 6:19 PM
 **/
@Component
public class UserApiImpl implements UserApi {
    @Override
    public Single<ResponseDto> postTest(PostDto argument) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setEmail("hey");
        return Single.just(responseDto);
    }
}
