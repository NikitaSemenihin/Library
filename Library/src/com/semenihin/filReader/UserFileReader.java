package com.semenihin.filReader;

import com.semenihin.entity.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFileReader implements FileReaderInterface<User>{
    List<User> users = new ArrayList<>();
    private final String filePath = "src/com/semenihin/user.txt";
    private final Pattern pattern =
            Pattern.compile( "(\\d+)\\s+\"([^\"]+)\"\\s+([\\w.%-]+@[\\w.-]+\\.[a-zA-Z]{2,})\\s+([+\\d()\\s-]+)");

    @Override
    public List<User> readEntitiesFromFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    users.add(new User(Integer.parseInt(matcher.group(1)) ,matcher.group(2),
                            matcher.group(3), matcher.group(4)));
                } else {
                    System.out.println("Error on string: " + line);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return users;
    }
}
