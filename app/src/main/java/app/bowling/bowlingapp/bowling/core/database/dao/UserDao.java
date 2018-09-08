package app.bowling.bowlingapp.bowling.core.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import app.bowling.bowlingapp.bowling.core.database.models.User;


@Dao
public interface UserDao {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user LIMIT 1")
    User getSingleUser();

    @Query("SELECT * FROM user WHERE email LIKE :email")
    User findByemail(String email);

    @Query("SELECT * FROM user WHERE email LIKE :phone")
    User findByphone(String phone);

    @Query("SELECT COUNT(*) from user")
    int countUsers();

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Insert
    void insert(User user);

}
