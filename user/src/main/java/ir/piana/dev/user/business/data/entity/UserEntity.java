package ir.piana.dev.user.business.data.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/23/2019 2:54 PM
 **/
@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Timestamp createdOn;
    @Column
    private Timestamp lastLogin;
}
