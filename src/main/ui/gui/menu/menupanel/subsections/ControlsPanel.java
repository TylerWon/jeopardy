package ui.gui.menu.menupanel.subsections;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents a panel displaying the controls for Jeopardy
public class ControlsPanel extends JPanel {

    private static final Font TITLE_FONT = new Font("", Font.BOLD, 16);
    private static final Font BUTTON_FONT = new Font("", Font.BOLD, 12);

    private JPanel eastPanel;
    private JButton menu;
    private JPanel container;

    // EFFECTS: constructs a controls panel which displays the controls for Jeopardy
    public ControlsPanel(JPanel container) {
        super();
        this.container = container;
        initializeGraphics();
        addTitle();
        addEastPanel();
        addText();
    }

    // MODIFIES: this
    // EFFECTS: renders the graphics for this
    private void initializeGraphics() {
        setLayout(new BorderLayout(0, 10));
    }

    // MODIFIES: this
    // EFFECTS: adds title to this
    private void addTitle() {
        JLabel title = new JLabel("Controls");
        title.setFont(TITLE_FONT);
        title.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        add(title, BorderLayout.NORTH);
    }

    // MODIFIES: this
    // EFFECTS: adds new panel to EAST of this which contains menu button
    private void addEastPanel() {
        eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        add(eastPanel, BorderLayout.EAST);

        addMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: adds menu button to a panel contained in EAST of this
    private void addMenuButton() {
        menu = new JButton("Menu");
        menu.setFocusable(false);
        menu.setFont(BUTTON_FONT);
        menu.addActionListener(new MenuButtonListener());
        eastPanel.add(menu, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: adds text to a panel contained in WEST of this
    private void addText() {
        JTextArea text = new JTextArea("Mouse Controls:\n"
                + "Left Click: interact with buttons\n\n"
                + "Keyboard Controls:\n"
                + "a: player 1 buzz in\n"
                + "c: player 2 buzz in\n"
                + "n: player 3 buzz in\n"
                + "l: player 4 buzz in\n");
        text.setEditable(false);
        text.setBackground(this.getBackground());

        add(text, BorderLayout.WEST);
    }

    // Represents an ActionListener which listens for when the menu button is pressed
    private class MenuButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == menu) {
                ControlsPanel.this.setVisible(false);
                container.setVisible(true);
            }
        }
    }
}
