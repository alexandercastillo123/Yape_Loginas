import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class YapeLogin extends JFrame implements ActionListener {
    private final JPasswordField passwordField;
    private final StringBuilder inputPassword;
    private final String correctPassword = "123456";
    private JLabel placeholderLabel;  // Eliminar final y declarar como no final
    private final JLabel forgotPasswordLabel;

    public YapeLogin() {
        // Configuración de la ventana
        setTitle("Login-Yape");
        setSize(385, 730);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);

        inputPassword = new StringBuilder();

        // Panel superior (morado)
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBackground(new Color(128, 0, 128));
        panelSuperior.setPreferredSize(new Dimension(385, 365));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 0, 20, 0);

        // Imagen
        JLabel imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/qrf.png"));
        Image img = icon.getImage().getScaledInstance(165, 165, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);
        imageLabel.setIcon(icon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.setPreferredSize(new Dimension(200, 200));
        imagePanel.setBorder(null); // Sin borde visible
        panelSuperior.add(imagePanel, gbc);

        add(panelSuperior, BorderLayout.NORTH);

        // Panel del teclado numérico
        JPanel tecladoPanel = new JPanel(new BorderLayout());
        tecladoPanel.setBackground(Color.WHITE);

        // Campo de contraseña
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new OverlayLayout(passwordPanel));
        passwordPanel.setPreferredSize(new Dimension(300, 60));
        passwordPanel.setBackground(Color.WHITE); // Fondo blanco
        passwordPanel.setBorder(null); // Eliminar borde alrededor de los paneles

        passwordField = new JPasswordField(10);
        passwordField.setFont(new Font("Arial", Font.BOLD, 24));
        passwordField.setHorizontalAlignment(JPasswordField.CENTER);
        passwordField.setEchoChar('•');
        passwordField.setOpaque(false); // Hacer el campo transparente
        passwordField.setBorder(BorderFactory.createEmptyBorder()); // Eliminar borde del JPasswordField
        passwordPanel.add(passwordField);

        placeholderLabel = new JLabel("Ingresa tu clave");
        placeholderLabel.setFont(new Font("Arial", Font.BOLD, 24));
        placeholderLabel.setForeground(new Color(128, 0, 128));
        placeholderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        placeholderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        placeholderLabel.setVisible(true);
        passwordPanel.add(placeholderLabel);

        tecladoPanel.add(passwordPanel, BorderLayout.NORTH);

        // Botones del teclado numérico
        JPanel botonesPanel = new JPanel(new GridLayout(4, 3, 10, 10));
        botonesPanel.setBackground(Color.WHITE);
        botonesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Espacio alrededor de los botones

        // Números y botones fijos
        String[] botonesFijos = {"", "Borrar"};
        ArrayList<String> numeros = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            numeros.add(String.valueOf(i));
        }
        Collections.shuffle(numeros); // Mezclar solo los números

        int indexNumeros = 0;
        for (int i = 0; i < 12; i++) {
            JButton boton;
            if (i == 9) { // Botón vacío
                boton = new JButton(botonesFijos[0]);
                boton.setEnabled(false);
                boton.setBackground(Color.WHITE); // Color blanco para el botón vacío
                boton.setBorder(null); // Sin borde
            } else if (i == 11) { // Botón borrar
                boton = new JButton();
                boton.setBackground(Color.WHITE); // Color blanco para el botón de borrar
                boton.setFocusPainted(false);
                boton.setPreferredSize(new Dimension(50, 50)); // Tamaño más pequeño
                boton.setBorder(null); // Sin borde

                // Añadir imagen como ícono
                ImageIcon borrarIcon = new ImageIcon(getClass().getResource("/img/borrar.png"));
                Image borrarImg = borrarIcon.getImage().getScaledInstance(30, 20, Image.SCALE_SMOOTH);
                borrarIcon = new ImageIcon(borrarImg);
                boton.setIcon(borrarIcon);
            } else {
                // Botón numérico
                boton = new JButton(numeros.get(indexNumeros++));
                boton.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente más pequeña
                boton.setBackground(new Color(240, 240, 240)); // Fondo claro
                boton.setFocusPainted(false);
                boton.setPreferredSize(new Dimension(50, 50)); // Tamaño más pequeño
                boton.setBorder(null); // Sin borde
            }
            boton.addActionListener(this);
            botonesPanel.add(boton);
        }

        tecladoPanel.add(botonesPanel, BorderLayout.CENTER);

        add(tecladoPanel, BorderLayout.CENTER);

        // Texto "¿Olvidaste tu clave?" en la parte inferior
        forgotPasswordLabel = new JLabel("¿OLVIDASTE TU CLAVE?");
        forgotPasswordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        forgotPasswordLabel.setForeground(new Color(56, 194, 177)); // Verde agua #26C6DA
        forgotPasswordLabel.setHorizontalAlignment(SwingConstants.CENTER);
        forgotPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.add(forgotPasswordLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (e.getSource() instanceof JButton) {
            JButton boton = (JButton) e.getSource();

            if (boton.getIcon() != null) { // Si es el botón con la imagen
                if (inputPassword.length() > 0) {
                    inputPassword.deleteCharAt(inputPassword.length() - 1);
                    passwordField.setText(inputPassword.toString());

                    if (inputPassword.length() == 0) {
                        placeholderLabel.setVisible(true);
                    }
                }
            } else {
                // Botones con texto
                if (inputPassword.length() < 6) {
                    inputPassword.append(comando);
                    passwordField.setText(inputPassword.toString());

                    if (inputPassword.length() == 1) {
                        placeholderLabel.setVisible(false);
                    }
                }

                if (inputPassword.length() == 6) {
                    verificarClave();
                }
            }
        }
    }

    private void verificarClave() {
        if (inputPassword.toString().equals(correctPassword)) {
            JOptionPane.showMessageDialog(this, "¡Ha ingresado satisfactoriamente!");
        } else {
            JOptionPane.showMessageDialog(this, "Clave incorrecta, intente nuevamente.");
        }

        inputPassword.setLength(0);
        passwordField.setText("");
        placeholderLabel.setVisible(true);
    }

    public static void main(String[] args) {
        new YapeLogin();
    }
}
