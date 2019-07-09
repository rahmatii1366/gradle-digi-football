package ir.piana.dev.user.business.operation;

import io.reactivex.Single;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/18/2019 3:19 PM
 **/
@Component
@Profile("test")
public class UserOperationTest implements UserOperation {
    private Logger logger = LoggerFactory.getLogger(UserOperationTest.class);

    @Value("${piana.email.send}")
    private boolean sendMail;

    @Autowired
    private EmailOperation emailOperation;

    private Random random = new Random();

    @Value("${piana.email.link.prefix}")
    private String linkPrefix;

    private Map<String, String> linkMap = new LinkedHashMap<>();
    private Map<String, String> passwordMap = new LinkedHashMap<>();
    private Map<String, String> loginMap = new LinkedHashMap<>();

//    public Completable sendVerification(String email, String password) {
//        return Completable.fromAction(() -> {
//            if(loginMap.containsKey(email)) {
//            }
//            if(linkMap.containsKey(email)) {
//                String uuid = linkMap.get(email);
//                String link = linkPrefix + "user/credential/sign-up/verify" + "?link=" + uuid;
//                emailService.sendEmail(email, "ارسال مجدد فعالسازی", link);
//            } else {
//                UUID uuid = UUID.randomUUID();
//                String link = linkPrefix + "user/credential/sign-up/verify" + "?link=" + uuid.toString() + "&mail=" + email;
//                linkMap.put(email, uuid.toString());
//                emailService.sendEmail(email, "ارسال فعالسازی", link);
//            }
//        });
//    }

    public Single<Boolean> sendVerification(String email, String password) {
        return Single.create(emitter -> {
            if(loginMap.containsKey(email)) {
                emitter.onError(new Exception("user already registered."));
                return;
            }
            String link = linkPrefix + "user/credential/sign-up/verify";
            if(linkMap.containsKey(email)) {
                String uuid = linkMap.get(email);
                link = link.concat("?link=" + uuid + "&mail=" + email);
                passwordMap.replace(email, password);
            } else {
                UUID uuid = UUID.randomUUID();
                link = link.concat("?link=" + uuid.toString() + "&mail=" + email);
                linkMap.put(email, uuid.toString());
                passwordMap.put(email, password);
            }
            if(sendMail) {
                emailOperation.sendEmail(email, "ارسال مجدد فعالسازی", link);
                logger.info("email send : " + link);
            } else
                logger.info("email not send : " + link);
            emitter.onSuccess(true);
        });
    }

    public Single<Boolean> verifyEmail(String link, String mail) {
        return Single.create(emitter -> {
            if (linkMap.containsKey(mail)) {
                String storedLink = linkMap.get(mail);
                if (link.equalsIgnoreCase(storedLink)) {
                    loginMap.put(mail, passwordMap.get(mail));
                    emitter.onSuccess(true);
                }
            }
            emitter.onSuccess(false);
        });
    }

    public Single<Boolean> setPassword(String mail, String password) {
        return Single.create(emitter -> {
            if (!passwordMap.containsKey(mail)) {
                passwordMap.put(mail, password);
                emitter.onSuccess(true);
            }
            emitter.onSuccess(false);
        });
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
