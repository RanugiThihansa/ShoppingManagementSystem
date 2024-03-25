import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class UserLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private static final ArrayList<User> usersList = new ArrayList<>();
    static boolean firstPurchase= true;



    public UserLogin() {
        setTitle("User Login");
        setSize(300, 170);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");

        usernameField = new JTextField();
        passwordField = new JPasswordField();

        loadUsersListFromFile();

        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                User user=new User(username,password);

                boolean userExists = false;
                for (User users : usersList) {
                    if (users.getUsername().equals(username)) {
                        userExists = true;
                        break;
                    }
                }
                if (!userExists) {
                    usersList.add(user);
                    saveUsersListToFile();
                }else {
                    firstPurchase=false;
                }

                setVisible(false);
                saveUsersListToFile();
                new ShoppingCenterGUI();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);


        add(panel);
        setVisible(true);
    }


    private void saveUsersListToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Users.txt"))) {
            for (User user : usersList) {
                writer.println(user.getUsername() + "," + user.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsersListFromFile() {
        try (Scanner scanner = new Scanner(new FileReader("Users.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    User user = new User(parts[0], parts[1]);
                    usersList.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
