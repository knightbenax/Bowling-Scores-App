package app.bowling.bowlingapp.bowling.core.database;

import javax.inject.Inject;

import app.bowling.bowlingapp.bowling.core.database.models.User;


public class DataStore {


    private OfflineStore offlineStore;

    @Inject
    public DataStore(OfflineStore offlineStore){
        this.offlineStore = offlineStore;
    }

    public void saveUser(String name, String email, String phone, String token, String refreshToken, String wallet){

        offlineStore.saveUser(name, email, phone, token, refreshToken, wallet);

    }


    public void saveUser(User user){
        offlineStore.saveUser(user);
    }


    public boolean userExist(){
        return offlineStore.userExists();
    }

    public void deleteAllUsers(){
        offlineStore.deleteUsers();
    }

    public User getUser(){
        return offlineStore.getUser();
    }

}
