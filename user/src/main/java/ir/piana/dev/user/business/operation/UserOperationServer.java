package ir.piana.dev.user.business.operation;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.SingleSource;
import ir.piana.dev.core.api.exception.IllegalArgumentException;
import ir.piana.dev.user.business.data.entity.UserEntity;
import ir.piana.dev.user.business.data.service.UserService;
import ir.piana.dev.user.business.model.ContactModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/18/2019 3:19 PM
 **/
@Component
@Profile("development, server")
public class UserOperationServer implements UserOperation {
    private Logger logger = LoggerFactory.getLogger(UserOperationServer.class);

    @Value("${piana.email.send}")
    private boolean sendMail;

    @Autowired
    private EmailOperation emailOperation;

    @Autowired
    private UserService userService;

    private Random random = new Random();

    @Value("${piana.email.link.prefix}")
    private String linkPrefix;

    private Map<String, ContactModel> contactMap = new LinkedHashMap<>();

    private Map<String, String> uuidMap = new LinkedHashMap<>();

    @Override
    public Completable sendVerificationCode(String email) {
        return userService.findOrCreate(email)
                .flatMap(userEntity ->
                        (SingleSource<UserEntity>) sin -> {
                            if(userEntity.getVerified() > 0)
                                sin.onError(new IllegalArgumentException("email-already-existed-and-verified"));
                            sin.onSuccess(userEntity);
                        }
                ).flatMapCompletable(userEntity -> (CompletableSource) next -> {
                    String link = linkPrefix + "user/credential/sign-up/verify";
                    if (uuidMap.containsKey(email)) {
                        uuidMap.remove(email);
                    }
                    UUID uuid = UUID.randomUUID();
                    String linkVar = Base64.getEncoder().encodeToString(
                            uuid.toString().concat(":").concat(email).getBytes());
                    link = link.concat("?link=" + linkVar);
                    uuidMap.put(email, uuid.toString());

                    if (sendMail) {
                        emailOperation.sendEmail(email, "ارسال مجدد فعالسازی", link);
                        logger.info("email send : " + link);
                    } else
                        logger.info("email not send : " + link);
                    next.onComplete();
                });
    }

    @Override
    public Completable verifyEmailByLink(String link) {
        return Completable.create(emitter -> {
            String decLink = new String(Base64.getDecoder().decode(link.getBytes()));
            String uuid = decLink.split(":")[0];
            String mail = decLink.split(":")[1];
            if (uuidMap.containsKey(mail)) {
                uuid.equalsIgnoreCase(uuidMap.get(mail));
                emitter.onComplete();
            }
            emitter.onError(new IllegalArgumentException("invalid-link"));
        });
    }

    public Completable resetPassword(Long userId, String password) {
        return userService.resetPassword(userId, password);
    }


//    public static void main(String[] args) throws NoSuchAlgorithmException {
//        SecretKeySpec secret_key = new SecretKeySpec("my-pass".getBytes(), "HmacSHA256");
//
//        String sha256hex = Hashing.hmacSha256(secret_key)
//                .hashString("ali", StandardCharsets.UTF_8)
//                .toString();
//        String s2 = Hashing.hmacSha256(secret_key)
//                .hashString("ali", StandardCharsets.UTF_8)
//                .toString();
//
//        System.out.println(sha256hex);
//        System.out.println(sha256hex.length());
//    }
}
