package com.psudoinstagram.model;

import java.sql.*;
import java.util.ArrayList;

public class Data {
    //public static ArrayList<User> allUsersNo = new ArrayList<>();
    public static ArrayList<Post> postsNODatabase = new ArrayList<>();

    public static ArrayList<Post>HomePost = new ArrayList<>();

    private static Connection connection;
    private static Statement statement;

    public static void makeConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/instagram",
                "root","ASDF58136f");
        statement = connection.createStatement();
    }

    public static void closeConnection() throws SQLException {
        if (connection!=null){
            connection.close();
        }
    }
    public static int createUser(User user) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into users (userName, userPass) values ('%s','%s')",user.userName,user.userPass),
                Statement.RETURN_GENERATED_KEYS);
        ResultSet result = statement.getGeneratedKeys();
        result.next();
        int id = result.getInt(1);
        closeConnection();
        return id;
    }

    public static void deleteUser(User user) throws SQLException {
        makeConnection();
        statement.execute(String.format("delete from users where postId = %d",user.id));
        closeConnection();
    }
    public static ArrayList<User> getAllUsers() throws SQLException {
        makeConnection();
        ResultSet resultSet = statement.executeQuery("select * from users");
        ArrayList<User>allUsers = new ArrayList<>();
        while (resultSet.next()){
            allUsers.add(new User(resultSet.getInt("id"),resultSet.getString("userName"),
                    resultSet.getString("userPass")));
        }
        closeConnection();
        return allUsers;
    }
    public static int createPost(Post post, User user) throws SQLException {
        makeConnection();
        statement.execute(String.format("insert into posts (user_id,postText) values ('%d','%s')",user.id,
                        post.postText),
                Statement.RETURN_GENERATED_KEYS);

        ResultSet result = statement.getGeneratedKeys();
        result.next();
        int id = result.getInt(1);
        closeConnection();
        return id;
    }
    public static ArrayList<Post> getAllPosts() throws SQLException {
        makeConnection();
        ResultSet resultSet = statement.executeQuery("select * from posts");
        ArrayList<Post>allPosts = new ArrayList<>();
        while (resultSet.next()){
            allPosts.add(new Post(resultSet.getInt("id"),resultSet.getString("postText"), resultSet.getInt("user")));
        }
        closeConnection();
        return allPosts;
    }
    public static ArrayList<Post> getProfileUserPosts(User user) throws SQLException {
        makeConnection();
        ResultSet resultSet = statement.executeQuery(String.format("select * from posts where user_id = ('%d')",user.id));
        ArrayList<Post>allPostsProfile = new ArrayList<>();
        while (resultSet.next()){
            allPostsProfile.add(new Post(resultSet.getInt("id"),resultSet.getString("postText"),
                    resultSet.getInt("user_id")));
        }
        closeConnection();
        return allPostsProfile;
    }
    public static ArrayList<Post> getHomePagePosts(User user) throws SQLException {
        makeConnection();
        ResultSet resultSet = statement.executeQuery(String.format("select * from posts where user_id != ('%d')",user.id));
        ArrayList<Post>allPostsProfile = new ArrayList<>();
        while (resultSet.next()){
            allPostsProfile.add(new Post(resultSet.getInt("id"),resultSet.getString("postText"),
                    resultSet.getInt("user_id")));
        }
        closeConnection();
        return allPostsProfile;
    }


    public static void updateUser(User user) throws SQLException {
        makeConnection();

        statement.execute(String.format("update users set userName =%s, userPass = %s where postId=%d",
                user.userName,user.userPass,user.id));

        closeConnection();
    }
    public static boolean isfollowing(User user1,User user2) throws SQLException {
        makeConnection();
        statement.execute(String.format("select EXISTS(SELECT * from following WHERE user_id1=%d and " +
                                "use_id2 =%d",user1.id, user2.id));

        ResultSet result = statement.getGeneratedKeys();
        System.out.println(result);
        System.out.println(statement);
        closeConnection();
        return false;
    }
}
