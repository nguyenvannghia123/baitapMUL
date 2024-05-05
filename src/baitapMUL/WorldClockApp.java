package baitapMUL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class WorldClockApp {

    private JFrame mainFrame;
    private JTextField timezoneField;

    public WorldClockApp() {
        createGUI();
    }

    private void createGUI() {
        mainFrame = new JFrame("World Clock App");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 200);
        mainFrame.setLayout(new BorderLayout());

        // Panel chứa đồng hồ chạy liên tục
        JPanel clockPanel = new JPanel();
        clockPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel clockLabel = new JLabel();
        clockLabel.setFont(new Font("Arial", Font.BOLD, 24));
        clockPanel.add(clockLabel);
        mainFrame.add(clockPanel, BorderLayout.CENTER);

        // Panel chứa textfield và button nhập múi giờ
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        timezoneField = new JTextField(10);
        inputPanel.add(timezoneField);

        JButton createClockButton = new JButton("Tạo đồng hồ mới");
        createClockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String timezone = timezoneField.getText().trim();
                if (!timezone.isEmpty()) {
                    createNewClock(timezone);
                }
            }
        });
        inputPanel.add(createClockButton);

        mainFrame.add(inputPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);

        // Thread để cập nhật đồng hồ chạy liên tục
        Thread clockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        String currentTime = sdf.format(calendar.getTime());
                        clockLabel.setText(currentTime);
                        Thread.sleep(1000); // Ngủ 1 giây trước khi cập nhật lại đồng hồ
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        clockThread.start();
    }

    private void createNewClock(String timezoneId) {
        JFrame newClockFrame = new JFrame("Đồng hồ - " + timezoneId);
        newClockFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newClockFrame.setSize(300, 150);

        JLabel timezoneLabel = new JLabel("Múi giờ: " + timezoneId);
        timezoneLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        newClockFrame.add(timezoneLabel, BorderLayout.NORTH);

        JLabel newClockLabel = new JLabel();
        newClockLabel.setFont(new Font("Arial", Font.BOLD, 24));
        newClockFrame.add(newClockLabel, BorderLayout.CENTER);

        Thread newClockThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Calendar calendar = Calendar.getInstance();
                        TimeZone timezone = TimeZone.getTimeZone(timezoneId);
                        calendar.setTimeZone(timezone);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        sdf.setTimeZone(timezone);
                        String currentTime = sdf.format(calendar.getTime());
                        newClockLabel.setText(currentTime);
                        Thread.sleep(1000); // Ngủ 1 giây trước khi cập nhật lại đồng hồ
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        newClockThread.start();

        newClockFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WorldClockApp();
            }
        });
    }
}
