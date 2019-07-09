package ir.piana.dev.user.business.operation;

import io.reactivex.Single;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/18/2019 3:19 PM
 **/
public interface UserOperation {
    Single<Boolean> sendVerification(String email, String password);
    public Single<Boolean> verifyEmail(String link, String mail);
    public Single<Boolean> setPassword(String mail, String password);

//    static void main(String[] args) throws NoSuchAlgorithmException {
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
