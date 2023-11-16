import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;


public class ToDoListApp extends JFrame {
    private DefaultListModel<Task> toDoListModel;
    private JList<Task> toDoList;
    private JTextField taskInput;
    private JButton addButton;
    private JButton removeButton;
    private JButton markCompletedButton;

    public ToDoListApp() {
        // Set up the frame
        setTitle("To-Do List App");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize components
        toDoListModel = new DefaultListModel<>();
        toDoList = new JList<>(toDoListModel);
        toDoList.setCellRenderer(new TaskCellRenderer());
        JScrollPane scrollPane = new JScrollPane(toDoList);
        taskInput = new JTextField();
        addButton = new JButton("Add Task");
        removeButton = new JButton("Remove Task");
        markCompletedButton = new JButton("Mark as Completed");

        // Set font size for components
        Font largerFont = new Font("SansSerif", Font.PLAIN, 16); // Change 16 to the desired font size
        toDoList.setFont(largerFont);
        taskInput.setFont(largerFont);
        addButton.setFont(largerFont);
        removeButton.setFont(largerFont);
        markCompletedButton.setFont(largerFont);

        // Set button background colors
        addButton.setBackground(new Color(129, 143, 180)); // 
        removeButton.setBackground(new Color(245, 232, 199)); // 
        markCompletedButton.setBackground(new Color(155, 190, 200)); //

        // Set layout manager to BorderLayout
        setLayout(new BorderLayout());

        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTask();
            }
        });

        markCompletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                markCompleted();
            }
        });
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Set preferred size for the taskInput field
        Dimension inputFieldSize = new Dimension(200, 30); // Adjust as needed
        taskInput.setPreferredSize(inputFieldSize);

        buttonPanel.add(taskInput);
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(markCompletedButton);
        return buttonPanel;
    }

    private void addTask() {
        String taskDescription = taskInput.getText();
        if (!taskDescription.isEmpty()) {
            Task task = new Task(taskDescription, false);
            toDoListModel.addElement(task);
            taskInput.setText("");
        }
    }

    private void removeTask() {
        int selectedIndex = toDoList.getSelectedIndex();
        if (selectedIndex != -1) {
            toDoListModel.remove(selectedIndex);
        }
    }

    private void markCompleted() {
        int selectedIndex = toDoList.getSelectedIndex();
        if (selectedIndex != -1) {
            Task selectedTask = toDoListModel.getElementAt(selectedIndex);
            selectedTask.setCompleted(!selectedTask.isCompleted());
            toDoList.repaint(); // Refresh the list to update the checkbox
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ToDoListApp().setVisible(true);
            }
        });
    }

    private class Task {
        private String description;
        private boolean completed;

        public Task(String description, boolean completed) {
            this.description = description;
            this.completed = completed;
        }

        public String getDescription() {
            return description;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }

    private class TaskCellRenderer extends JCheckBox implements ListCellRenderer<Task> {
        private Border emptyBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        private Border hoverBorder = BorderFactory.createLineBorder(Color.BLACK);

        @Override
        public Component getListCellRendererComponent(JList<? extends Task> list, Task task, int index, boolean isSelected, boolean cellHasFocus) {
            setText(task.getDescription());
            setSelected(task.isCompleted());
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setFocusPainted(false);

            // Set the background color to yellow
            setBackground(new Color(199, 220, 167));

            // Add border on hover
            if (cellHasFocus) {
                setBorder(hoverBorder);
            } else {
                setBorder(emptyBorder);
            }

            return this;
        }
    }
}
