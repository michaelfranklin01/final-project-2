import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SimpleCalendar extends JFrame {
    private final JPanel calendarPanel = new JPanel(new GridLayout(7, 7, 5, 5)); // Include the row for headers
    private final JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
    private Calendar calendar = new GregorianCalendar();
    private JButton todayButton = new JButton("Today");

    public SimpleCalendar() {
        initializeUI();
        updateCalendar();
    }

    private void initializeUI() {
        setTitle("Simple Calendar");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Navigation panel
        JPanel navPanel = new JPanel();
        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");
        navPanel.add(prevButton);
        navPanel.add(todayButton);
        navPanel.add(monthLabel);
        navPanel.add(nextButton);

        add(navPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);

        prevButton.addActionListener(e -> changeMonth(-1));
        nextButton.addActionListener(e -> changeMonth(1));
        todayButton.addActionListener(e -> returnToToday());

        // Initially hide the "Today" button
        todayButton.setVisible(false);
    }

    private void updateCalendar() {
        calendarPanel.removeAll();

        // Resetting the headers every time we update the calendar
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String header : headers) {
            calendarPanel.add(new JLabel(header, SwingConstants.CENTER));
        }

        Calendar iterator = (Calendar) calendar.clone();
        iterator.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeek = iterator.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = iterator.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Check if the current view is of the current month and year
        Calendar today = new GregorianCalendar();
        boolean isCurrentMonth = iterator.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                                 iterator.get(Calendar.YEAR) == today.get(Calendar.YEAR);

        // Add empty cells for days before the first of the month
        for (int i = 1; i < dayOfWeek; i++) {
            calendarPanel.add(new JLabel(""));
        }

        // Populate days in the month
        for (int i = 1; i <= daysInMonth; i++) {
            JLabel dayLabel = new JLabel(Integer.toString(i), SwingConstants.CENTER);
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            calendarPanel.add(dayLabel);
            if (isCurrentMonth && i == today.get(Calendar.DAY_OF_MONTH)) {
                dayLabel.setOpaque(true);
                dayLabel.setBackground(Color.CYAN);
            }
            dayLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(null, "Day " + i + " clicked!");
                }
            });
        }

        monthLabel.setText(new SimpleDateFormat("MMMM YYYY").format(calendar.getTime()));
        
        // Show or hide the "Today" button based on whether the current view is today's month
        todayButton.setVisible(!isCurrentMonth);

        // Revalidate and repaint to ensure updates are displayed
        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private void changeMonth(int delta) {
        calendar.add(Calendar.MONTH, delta);
        updateCalendar();
    }

    private void returnToToday() {
        calendar = new GregorianCalendar(); // Reset to current date
        updateCalendar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleCalendar sc = new SimpleCalendar();
            sc.setVisible(true);
        });
    }
}


