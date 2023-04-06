package Zadanie09;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class GUI extends JFrame{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUI::new);
    }

    public GUI(){
        super("Wykres funkcji");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(500, 500));
        Sinus sinus = new Sinus();
        JPanel bottomMenu = new BottomMenu(sinus);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        add(sinus);
        add(bottomMenu);
        setVisible( true );
    }


    class Sinus extends JPanel {

        int N;
        float K;

        public Sinus() {
            N = 1;
            K = 1;
        }

        private int calcSin(double x, int scale) {
            x *= Math.PI;
            float sum = 0;
            for (int i = 1; i <= N; i++) {
                sum += -2 * Math.pow(-1, i) * Math.sin(i * x) / i;
            }
            sum *= scale;
            return Math.round(sum);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension size = getSize();
            int scale = size.height / 10;
            int step = 1;
            int points = (size.width - 20) / step;
            float sinStep = (float) K / (points / 2);

            for (int i = 0;  i < size.width - 20; i += step) {
                g.fillOval(i + 10, calcSin((double) i * sinStep, scale) + getHeight()/2, 7, 7);
            }
        }

        public void setN(int n) {
            N = n;
            repaint();
        }

        public void setK(float k) {
            K = k;
            repaint();
        }
    }

    class BottomMenu extends JPanel {

        Sinus sinus;
        Nslider nslider;
        KTextField kTextField;

        public BottomMenu(Sinus sinus) {
            setMaximumSize(new Dimension(500, 100));
            this.sinus = sinus;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            nslider = new Nslider();
            kTextField = new KTextField();

            add(nslider);
            add(kTextField);
        }

        private void kChanged(float k) {
            sinus.setK(k);
        }

        private void nChanged(int n) {
            sinus.setN(n);
        }


        class Nslider extends JPanel implements ChangeListener {
            JSlider slider;
            JLabel N;

            @Override
            public void stateChanged(ChangeEvent e) {

                N.setText("N = " + slider.getValue());
                nChanged(slider.getValue());
            }

            public Nslider() {
                slider = new JSlider(1, 25, 1);

                slider.setPaintTicks(true);
                slider.setPaintTrack(true);
                slider.setPaintLabels(true);
                slider.setMajorTickSpacing(4);
                slider.setMajorTickSpacing(1);

                slider.addChangeListener(this);

                N = new JLabel("N = 1");
                add(slider);
                add(N);
            }
        }

        class KTextField extends JPanel{
            JTextField jTextField;
            JLabel K;

            public KTextField() {
                jTextField = new JTextField("1.0", 5);
                K = new JLabel("K = 1.0");
                jTextField.getDocument().addDocumentListener((AllChangesListener) e -> {
                    String string = jTextField.getText();
                    try {
                        float number = Float.parseFloat(string);
                        if(number < 1.0 || number > 10) {
                            K.setText("K jest za małe bądź za duże");
                        }
                        else {
                            K.setText("K = " + number);
                            kChanged(number);
                        }
                    }
                    catch (NumberFormatException exception) {
                        K.setText("K is not a number");
                    }
                });
                add(jTextField);
                add(K);
            }

            @FunctionalInterface
            interface AllChangesListener extends DocumentListener {
                void change(DocumentEvent e);


                @Override
                default void insertUpdate(DocumentEvent e) {
                    change(e);
                }

                @Override
                default void removeUpdate(DocumentEvent e) {
                    change(e);
                }

                @Override
                default void changedUpdate(DocumentEvent e){
                    change(e);
                }
            }
        }


    }
}

