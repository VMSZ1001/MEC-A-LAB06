/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.juegoscasino.java;

import java.util.Scanner;

public class JuegosAzar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione un juego de azar:");
            System.out.println("1. Juego de Dados");
            System.out.println("2. Tragamonedas");
            System.out.println("0. Salir");

            int opcion = scanner.nextInt();

            if (opcion == 1) {
                jugarDados();
            } else if (opcion == 2) {
                jugarTragamonedas();
            } else if (opcion == 0) {
                break;
            } else {
                System.out.println("Opción inválida. Por favor, seleccione una opción válida.");
            }
        }

        scanner.close();
    }

    private static void jugarDados() {
        int resultado = (int) (Math.random() * 6) + 1;
        System.out.println("Resultado del juego de dados: " + resultado);
    }

    private static void jugarTragamonedas() {
        int resultado1 = (int) (Math.random() * 10);
        int resultado2 = (int) (Math.random() * 10);
        int resultado3 = (int) (Math.random() * 10);
        System.out.println("Resultados del juego de tragamonedas: " + resultado1 + ", " + resultado2 + ", " + resultado3);
    }
}


/*import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JuegosAzarGUI extends JFrame {
    private JLabel resultadoDadosLabel;
    private JLabel resultadoTragamonedasLabel;
    private JButton jugarDadosButton;
    private JButton jugarTragamonedasButton;
    private JLabel dadosImageLabel;
    private JLabel tragamonedasImageLabel;

    public JuegosAzarGUI() {
        // Configuración de la ventana
        setTitle("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear componentes
        resultadoDadosLabel = new JLabel("Resultado del juego de dados");
        resultadoTragamonedasLabel = new JLabel("Resultados del juego de tragamonedas");
        jugarDadosButton = new JButton("Jugar Dados");
        jugarTragamonedasButton = new JButton("Jugar Tragamonedas");
        dadosImageLabel = new JLabel(new ImageIcon("dados.png")); // Ruta de la imagen de dados
        tragamonedasImageLabel = new JLabel(new ImageIcon("tragamonedas.png")); // Ruta de la imagen de tragamonedas

        // Configurar paneles
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(resultadoDadosLabel);
        panelSuperior.add(dadosImageLabel);
        panelSuperior.add(resultadoTragamonedasLabel);
        panelSuperior.add(tragamonedasImageLabel);

        JPanel panelInferior = new JPanel();
        panelInferior.add(jugarDadosButton);
        panelInferior.add(jugarTragamonedasButton);

        // Agregar paneles a la ventana
        add(panelSuperior, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Agregar eventos de botones
        jugarDadosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jugarDados();
            }
        });

        jugarTragamonedasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jugarTragamonedas();
            }
        });

        // Mostrar la ventana
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void jugarDados() {
        // Lógica del juego de dados
    }

    private void jugarTragamonedas() {
        // Lógica del juego de tragamonedas
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzarGUI();
            }
        });
    }
}*/
