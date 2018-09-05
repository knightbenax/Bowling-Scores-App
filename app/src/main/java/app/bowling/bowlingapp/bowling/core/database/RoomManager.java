package app.bowling.bowlingapp.bowling.core.database;

import android.content.Context;

import rabaapp.raba.app.raba.core.database.models.User;

public class RoomManager {

    private static RoomManager roomManager;
    private static AppDatabase appDatabase;

    private Context context;

    public static RoomManager getInstance() {
        if (roomManager == null) {
            synchronized (RoomManager.class) {
                if (roomManager == null) {
                    roomManager = new RoomManager();
                }
            }
        }

        return roomManager;
    }

    public RoomManager with(Context context){
        this.context = context;
        appDatabase = AppDatabase.getAppDatabase(context);

        return this;
    }

    public User getUser(){
        return appDatabase.userDao().getSingleUser();
    }


    public void saveUser(String name, String email, String phone, String token, String refreshToken, String wallet){

        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setEmail(email);
        user.setToken(token);
        user.setRefreshToken(refreshToken);
        user.setWallet(wallet);


        appDatabase.userDao().insert(user);
    }

    public void saveUser(User user){

        appDatabase.userDao().insert(user);
    }

    public boolean userExists(){
        int user_count = appDatabase.userDao().countUsers();

        return user_count > 0;
    }

    public void deleteUsers(){
        appDatabase.userDao().deleteAll();
    }


}
