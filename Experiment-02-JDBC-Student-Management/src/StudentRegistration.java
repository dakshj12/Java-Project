package src;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentRegistration extends JFrame implements ActionListener {


    JLabel lblName, lblRoll, lblGender, lblBranch;


    JTextField txtName, txtRoll, txtBranch;

    
    JRadioButton male, female;
    ButtonGroup genderGroup;

    
    JCheckBox terms;

    
    JButton submit, reset;
    Connection con;

    
    public StudentRegistration() {

        connect();

        setTitle("Student Registration Form");
        setSize(450, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    
        lblName = new JLabel("Student Name");
        lblName.setBounds(30, 30, 120, 30);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(170, 30, 180, 30);
        add(txtName);

        
        lblRoll = new JLabel("Roll Number");
        lblRoll.setBounds(30, 80, 120, 30);
        add(lblRoll);

        txtRoll = new JTextField();
        txtRoll.setBounds(170, 80, 180, 30);
        add(txtRoll);

        
        lblGender = new JLabel("Gender");
        lblGender.setBounds(30, 130, 120, 30);
        add(lblGender);

        male = new JRadioButton("Male");
        male.setBounds(170, 130, 80, 30);
        add(male);

        female = new JRadioButton("Female");
        female.setBounds(260, 130, 100, 30);
        add(female);

        genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);

       
        lblBranch = new JLabel("Branch");
        lblBranch.setBounds(30, 180, 120, 30);
        add(lblBranch);

        txtBranch = new JTextField();
        txtBranch.setBounds(170, 180, 180, 30);
        add(txtBranch);

        
        terms = new JCheckBox("I accept Terms & Conditions");
        terms.setBounds(30, 230, 250, 30);
        add(terms);

        submit = new JButton("Submit");
        submit.setBounds(80, 290, 100, 35);
        submit.addActionListener(this);
        add(submit);

        reset = new JButton("Reset");
        reset.setBounds(220, 290, 100, 35);
        reset.addActionListener(this);
        add(reset);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        
        if (e.getSource() == reset) {
            txtName.setText("");
            txtRoll.setText("");
            txtBranch.setText("");
            genderGroup.clearSelection();
            terms.setSelected(false);
            return;
        }

        
        if (txtName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student Name");
            return;
        }

        if (txtRoll.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Roll Number");
            return;
        }

        if (!male.isSelected() && !female.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please select Gender");
            return;
        }

        if (txtBranch.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Branch");
            return;
        }

        if (!terms.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please accept Terms & Conditions");
            return;
        }

        String gender = male.isSelected() ? "Male" : "Female";

        try {

    String sql = "INSERT INTO students(name, roll_no, gender, branch) VALUES (?, ?, ?, ?)";

    PreparedStatement pst = con.prepareStatement(sql);

    pst.setString(1, txtName.getText());
    pst.setString(2, txtRoll.getText());
    pst.setString(3, gender);
    pst.setString(4, txtBranch.getText());

    int rows = pst.executeUpdate();

   if (rows > 0) {
        JOptionPane.showMessageDialog(this, "Registration Successful! Data Saved.");

        txtName.setText("");
        txtRoll.setText("");
        txtBranch.setText("");
        genderGroup.clearSelection();
        terms.setSelected(false);  
    }  

} catch (Exception ex) {
    ex.printStackTrace();
    JOptionPane.showMessageDialog(this, ex.getMessage());
}

        JOptionPane.showMessageDialog(
                this,
                "Registration Successful\n\n"
                        + "Name : " + txtName.getText()
                        + "\nRoll Number : " + txtRoll.getText()
                        + "\nGender : " + gender
                        + "\nBranch : " + txtBranch.getText()
        );  
    }

    public void connect() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");

        con = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/student_db",
            "root",
            "@k@sh0706"
        );

        System.out.println("Database Connected Successfully!");

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static void main(String[] args) {
        new StudentRegistration();
    }
}