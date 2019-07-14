package ir.piana.dev.user.business.data.entity;

import ir.piana.dev.core.api.entity.PianaEntity;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/23/2019 2:54 PM
 **/
@Entity
@Table(name = "users")
@Data
public class UserEntity extends UserBaseEntity<Long> {
    @Id
    @SequenceGenerator(name="users_id_seq", sequenceName="users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="users_id_seq")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Integer verified;

    @Column
    private Timestamp createdOn;

    @Column
    private Timestamp lastLogin;

    public UserEntity() {
    }

    public UserEntity(String email) {
        this(email, null, null);
    }

    public UserEntity(String email, String password) {
        this(email, password, null);
    }

    public UserEntity(String email, String password, Integer verified) {
        this(email, password, verified, null);
    }

    public UserEntity(String email, String password, Integer verified, Timestamp timestamp) {
        this.email = email;
        this.password = password;
        this.verified = verified;
        this.createdOn = timestamp;
    }

    public static UserEntity from(String email) {
        return new UserEntity(email);
    }

    public static UserEntity from(String email, Integer verified) {
        return new UserEntity(email, null, verified);
    }
}
