package it.unitn.disi.webarch.facchinetti.chat.ChatApp.util;

import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.Role;
import it.unitn.disi.webarch.facchinetti.chat.ChatApp.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public abstract class CsvUtils {

    public static Set<User> getUsers(InputStream is, boolean hasHeader) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Set<User> users = new HashSet<>();
        String line = null;
        if( hasHeader )
            line = br.readLine();
        while((line = br.readLine()) != null){
            String[] params = line.split(",");
            if( params.length > 1 ){
                User u = new User(params[0].trim(), params[1].trim(), Role.USER);
                users.add(u);
            }
        }
        br.close();
        return users;

    }

}
