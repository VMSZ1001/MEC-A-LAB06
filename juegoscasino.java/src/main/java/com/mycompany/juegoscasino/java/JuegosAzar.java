package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new FlowLayout());

        // crea los paneles de los dados con las imágenes correspondientes
        ImageIcon[] imagenesDados = {
                new ImageIcon("dados1.png"),
                new ImageIcon("dados2.png"),
                new ImageIcon("dados3.png"),
                new ImageIcon("dados4.png"),
                new ImageIcon("dados5.png"),
                new ImageIcon("dados6.png")
        };
        panelDados1 = new DadoPanel(imagenesDados, 200, 200); // ajusta el tamaño del dado 1
        panelDados2 = new DadoPanel(imagenesDados, 200, 200); // ajusta el tamaño del dado 2

        // crea el panel del tragamonedas con las tiras de imágenes correspondientes
        ImageIcon[] tirasTragamonedas = {
                new ImageIcon("tira1.png"),
                new ImageIcon("tira2.png"),
                new ImageIcon("tira3.png")
        };
        panelTragamonedas = new TragamonedasPanel(tirasTragamonedas, 200, 200); // ajusta el tamaño del tragamonedas

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1); // agrega el panel del dado 1
        panelDados.add(panelDados2); // agrega el panel del dado 2

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas); // agrega el panel del tragamonedas

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            panelDados1.pausar();
            panelDados2.pausar();
            panelTragamonedas.pausar();
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(ImageIcon[] imagenes, int ancho, int alto) {
        this.imagenes = imagenes;
        this.caraActual = 0;
        this.timer = new Timer(100, this); // actualiza cada 100 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0; // reinicia la cara actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++; // avanza a la siguiente cara
        if (caraActual >= imagenes.length) {
            caraActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] tiras;
    private int tiraActual;
    private Timer timer;
    private int[] retrasosIniciales;

    public TragamonedasPanel(ImageIcon[] tiras, int ancho, int alto) {
        this.tiras = tiras;
        this.tiraActual = 0;
        this.timer = new Timer(100, this); // actualiza cada 100 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasosIniciales = new int[tiras.length];
        for (int i = 0; i < retrasosIniciales.length; i++) {
            retrasosIniciales[i] = (int) (Math.random() * 1000); // establece un retraso inicial aleatorio para cada tira
        }
    }

    public void lanzarConRetrasoAleatorio() {
        tiraActual = 0; // reinicia la tira actual
        timer.setInitialDelay(retrasosIniciales[tiraActual]); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        tiraActual++; // avanza a la siguiente tira
        if (tiraActual >= tiras.length) {
            tiraActual = 0; // vuelve al inicio si llega al final
        }
        timer.setInitialDelay(retrasosIniciales[tiraActual]); // establece el retraso inicial aleatorio para la siguiente tira
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imageHeight = getHeight() / tiras.length; // altura de cada imagen en la tira
        int y = -tiraActual * imageHeight; // posición vertical inicial de la tira
        for (int i = 0; i < tiras.length; i++) {
            g.drawImage(tiras[i].getImage(), 0, y, getWidth(), y + imageHeight, null);
            y += imageHeight; // incrementa la posición vertical para la siguiente imagen
        }
    }
}

/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private ImageIcon imagenDados = new ImageIcon("dados.png");
    private ImageIcon imagenTragamonedas = new ImageIcon("tragamonedas.png");
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new FlowLayout());

        // crea los paneles de los dados con las imágenes correspondientes
        ImageIcon[] imagenesDados = {
                new ImageIcon("dados1.png"),
                new ImageIcon("dados2.png"),
                new ImageIcon("dados3.png"),
                new ImageIcon("dados4.png"),
                new ImageIcon("dados5.png"),
                new ImageIcon("dados6.png")
        };
        panelDados1 = new DadoPanel(imagenesDados, 200, 200); // ajusta el tamaño del dado 1
        panelDados2 = new DadoPanel(imagenesDados, 200, 200); // ajusta el tamaño del dado 2

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1); // agrega el panel del dado 1
        panelDados.add(panelDados2); // agrega el panel del dado 2

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(new JLabel(imagenTragamonedas));

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            panelDados1.pausar();
            panelDados2.pausar();
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        int resultado1 = (int) (Math.random() * 10);
        int resultado2 = (int) (Math.random() * 10);
        int resultado3 = (int) (Math.random() * 10);
        JOptionPane.showMessageDialog(this, "Resultados del juego de tragamonedas: " + resultado1 + ", " + resultado2 + ", " + resultado3);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(ImageIcon[] imagenes, int ancho, int alto) {
        this.imagenes = imagenes;
        this.caraActual = 0;
        this.timer = new Timer(100, this); // actualiza cada 100 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0; // reinicia la cara actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++; // avanza a la siguiente cara
        if (caraActual >= imagenes.length) {
            caraActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private ImageIcon imagenDados = new ImageIcon("dados.png");
    private ImageIcon imagenTragamonedas = new ImageIcon("tragamonedas.png");
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        // crea los paneles de los dados con las imágenes correspondientes
        ImageIcon[] imagenesDados = {
                new ImageIcon("dados1.png"),
                new ImageIcon("dados2.png"),
                new ImageIcon("dados3.png"),
                new ImageIcon("dados4.png"),
                new ImageIcon("dados5.png"),
                new ImageIcon("dados6.png")
        };
        panelDados1 = new DadoPanel(imagenesDados, 200, 200); // ajusta el tamaño del dado 1
        panelDados2 = new DadoPanel(imagenesDados, 200, 200); // ajusta el tamaño del dado 2

        // crea el panel del tragamonedas con las imágenes correspondientes
        ImageIcon[] imagenesTragamonedas = {
                new ImageIcon("simbolo1.png"),
                new ImageIcon("simbolo2.png"),
                new ImageIcon("simbolo3.png"),
                new ImageIcon("simbolo4.png"),
                new ImageIcon("simbolo5.png")
        };
        panelTragamonedas = new TragamonedasPanel(imagenesTragamonedas, 150, 200); // ajusta el tamaño del tragamonedas

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1); // agrega el panel del dado 1
        panelDados.add(panelDados2); // agrega el panel del dado 2

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas); // agrega el panel del tragamonedas

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            panelDados1.pausar();
            panelDados2.pausar();
            panelTragamonedas.pausar();
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(ImageIcon[] imagenes, int ancho, int alto) {
        this.imagenes = imagenes;
        this.caraActual = 0;
        this.timer = new Timer(100, this); // actualiza cada 100 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0; // reinicia la cara actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++; // avanza a la siguiente cara
        if (caraActual >= imagenes.length) {
            caraActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(ImageIcon[] imagenes, int ancho, int alto) {
        this.imagenes = imagenes;
        this.simboloActual = 0;
        this.timer = new Timer(200, this); // actualiza cada 200 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0; // reinicia el símbolo actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++; // avanza al siguiente símbolo
        if (simboloActual >= imagenes.length) {
            simboloActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        panelDados1 = new DadoPanel(200, 200); // ajusta el tamaño del dado 1
        panelDados2 = new DadoPanel(200, 200); // ajusta el tamaño del dado 2
        panelTragamonedas = new TragamonedasPanel(150, 200); // ajusta el tamaño del tragamonedas

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1); // agrega el panel del dado 1
        panelDados.add(panelDados2); // agrega el panel del dado 2

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas); // agrega el panel del tragamonedas

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            panelDados1.pausar();
            panelDados2.pausar();
            panelTragamonedas.pausar();
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[] {
                new ImageIcon("dados1.png"),
                new ImageIcon("dados2.png"),
                new ImageIcon("dados3.png"),
                new ImageIcon("dados4.png"),
                new ImageIcon("dados5.png"),
                new ImageIcon("dados6.png")
        };
        this.caraActual = 0;
        this.timer = new Timer(100, this); // actualiza cada 100 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0; // reinicia la cara actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++; // avanza a la siguiente cara
        if (caraActual >= imagenes.length) {
            caraActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[] {
                new ImageIcon("simbolo1.png"),
                new ImageIcon("simbolo2.png"),
                new ImageIcon("simbolo3.png"),
                new ImageIcon("simbolo4.png"),
                new ImageIcon("simbolo5.png")
        };
        this.simboloActual = 0;
        this.timer = new Timer(200, this); // actualiza cada 200 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0; // reinicia el símbolo actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++; // avanza al siguiente símbolo
        if (simboloActual >= imagenes.length) {
            simboloActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        // crea los paneles de los dados
        panelDados1 = new DadoPanel(200, 200); // ajusta el tamaño del dado 1
        panelDados2 = new DadoPanel(200, 200); // ajusta el tamaño del dado 2

        // crea el panel del tragamonedas
        panelTragamonedas = new TragamonedasPanel(150, 200); // ajusta el tamaño del tragamonedas

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1); // agrega el panel del dado 1
        panelDados.add(panelDados2); // agrega el panel del dado 2

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas); // agrega el panel del tragamonedas

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            panelDados1.pausar();
            panelDados2.pausar();
            panelTragamonedas.pausar();
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[]{
                new ImageIcon("dados1.png"),
                new ImageIcon("dados2.png"),
                new ImageIcon("dados3.png"),
                new ImageIcon("dados4.png"),
                new ImageIcon("dados5.png"),
                new ImageIcon("dados6.png")
        };
        this.caraActual = 0;
        this.timer = new Timer(100, this); // actualiza cada 100 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0; // reinicia la cara actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++; // avanza a la siguiente cara
        if (caraActual >= imagenes.length) {
            caraActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[]{
                new ImageIcon("simbolo1.png"),
                new ImageIcon("simbolo2.png"),
                new ImageIcon("simbolo3.png"),
                new ImageIcon("simbolo4.png"),
                new ImageIcon("simbolo5.png")
        };
        this.simboloActual = 0;
        this.timer = new Timer(200, this); // actualiza cada 200 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0; // reinicia el símbolo actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++; // avanza al siguiente símbolo
        if (simboloActual >= imagenes.length) {
            simboloActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private JButton salirButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        panelDados1 = new DadoPanel(200, 200);
        panelDados2 = new DadoPanel(200, 200);
        panelTragamonedas = new TragamonedasPanel(150, 200);

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1);
        panelDados.add(panelDados2);

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas);

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        salirButton = new JButton("Salir");
        salirButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);
        add(salirButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                panelDados1.pausar();
                panelDados2.pausar();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                panelTragamonedas.pausar();
            }
        } else if (e.getSource() == salirButton) {
            System.exit(0);
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        
        // Resto del código para el juego de dados
        // ...

        // Ejemplo: Mostrar el resultado de los dados
        int resultadoDados1 = panelDados1.getCaraActual();
        int resultadoDados2 = panelDados2.getCaraActual();
        System.out.println("Resultado de los dados: " + resultadoDados1 + " - " + resultadoDados2);
        
        // Realizar acciones adicionales según el resultado de los dados
        // ...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        
        // Resto del código para el juego de tragamonedas
        // ...
        
        // Ejemplo: Mostrar el resultado del tragamonedas
        int resultadoTragamonedas = panelTragamonedas.getSimboloActual();
        System.out.println("Resultado del tragamonedas: " + resultadoTragamonedas);
        
        // Realizar acciones adicionales según el resultado del tragamonedas
        // ...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            imagenes[i] = new ImageIcon("dados" + (i + 1) + ".png");
        }
        this.caraActual = 0;
        this.timer = new Timer(100, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public int getCaraActual() {
        return caraActual + 1;
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++;
        if (caraActual >= imagenes.length) {
            caraActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            imagenes[i] = new ImageIcon("simbolo" + (i + 1) + ".png");
        }
        this.simboloActual = 0;
        this.timer = new Timer(200, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public int getSimboloActual() {
        return simboloActual + 1;
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++;
        if (simboloActual >= imagenes.length) {
            simboloActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private JButton salirButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        panelDados1 = new DadoPanel(200, 200);
        panelDados2 = new DadoPanel(200, 200);
        panelTragamonedas = new TragamonedasPanel(150, 200);

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1);
        panelDados.add(panelDados2);

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas);

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        salirButton = new JButton("Salir");
        salirButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);
        add(salirButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                panelDados1.pausar();
                panelDados2.pausar();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                panelTragamonedas.pausar();
            }
        } else if (e.getSource() == salirButton) {
            System.exit(0);
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        
        // Resto del código para el juego de dados
        // ...

        // Ejemplo: Mostrar el resultado de los dados
        int resultadoDados1 = panelDados1.getCaraActual();
        int resultadoDados2 = panelDados2.getCaraActual();
        System.out.println("Resultado de los dados: " + resultadoDados1 + " - " + resultadoDados2);
        
        // Realizar acciones adicionales según el resultado de los dados
        // ...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        
        // Resto del código para el juego de tragamonedas
        // ...
        
        // Ejemplo: Mostrar el resultado del tragamonedas
        int resultadoTragamonedas = panelTragamonedas.getSimboloActual();
        System.out.println("Resultado del tragamonedas: " + resultadoTragamonedas);
        
        // Realizar acciones adicionales según el resultado del tragamonedas
        // ...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            imagenes[i] = new ImageIcon("dados" + (i + 1) + ".png");
        }
        this.caraActual = 0;
        this.timer = new Timer(100, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public int getCaraActual() {
        return caraActual + 1;
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++;
        if (caraActual >= imagenes.length) {
            caraActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            imagenes[i] = new ImageIcon("simbolo" + (i + 1) + ".png");
        }
        this.simboloActual = 0;
        this.timer = new Timer(200, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public int getSimboloActual() {
        return simboloActual + 1;
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++;
        if (simboloActual >= imagenes.length) {
            simboloActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarDadosButton;
    private JButton pausarDadosButton;
    private JButton salirDadosButton;
    private JButton jugarTragamonedasButton;
    private JButton pausarTragamonedasButton;
    private JButton salirTragamonedasButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        panelDados1 = new DadoPanel(200, 200);
        panelDados2 = new DadoPanel(200, 200);
        panelTragamonedas = new TragamonedasPanel(150, 200);

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1);
        panelDados.add(panelDados2);

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas);

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarDadosButton = new JButton("Jugar Dados");
        jugarDadosButton.addActionListener(this);

        pausarDadosButton = new JButton("Pausar Dados");
        pausarDadosButton.addActionListener(this);

        salirDadosButton = new JButton("Salir Dados");
        salirDadosButton.addActionListener(this);

        jugarTragamonedasButton = new JButton("Jugar Tragamonedas");
        jugarTragamonedasButton.addActionListener(this);

        pausarTragamonedasButton = new JButton("Pausar Tragamonedas");
        pausarTragamonedasButton.addActionListener(this);

        salirTragamonedasButton = new JButton("Salir Tragamonedas");
        salirTragamonedasButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarDadosButton);
        add(pausarDadosButton);
        add(salirDadosButton);
        add(jugarTragamonedasButton);
        add(pausarTragamonedasButton);
        add(salirTragamonedasButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarDadosButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione el juego de dados.");
            }
        } else if (e.getSource() == pausarDadosButton) {
            panelDados1.pausar();
            panelDados2.pausar();
        } else if (e.getSource() == salirDadosButton) {
            System.exit(0);
        } else if (e.getSource() == jugarTragamonedasButton) {
            if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione el juego de tragamonedas.");
            }
        } else if (e.getSource() == pausarTragamonedasButton) {
            panelTragamonedas.pausar();
        } else if (e.getSource() == salirTragamonedasButton) {
            System.exit(0);
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            imagenes[i] = new ImageIcon("dados" + (i + 1) + ".png");
        }
        this.caraActual = 0;
        this.timer = new Timer(100, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++;
        if (caraActual >= imagenes.length) {
            caraActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            imagenes[i] = new ImageIcon("simbolo" + (i + 1) + ".png");
        }
        this.simboloActual = 0;
        this.timer = new Timer(200, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++;
        if (simboloActual >= imagenes.length) {
            simboloActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}*/
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarDadosButton;
    private JButton pausarDadosButton;
    private JButton salirDadosButton;
    private JButton jugarTragamonedasButton;
    private JButton pausarTragamonedasButton;
    private JButton salirTragamonedasButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        panelDados1 = new DadoPanel(200, 200);
        panelDados2 = new DadoPanel(200, 200);
        panelTragamonedas = new TragamonedasPanel(150, 200);

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1);
        panelDados.add(panelDados2);

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas);

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarDadosButton = new JButton("Jugar");
        jugarDadosButton.addActionListener(this);
        pausarDadosButton = new JButton("Pausar");
        pausarDadosButton.addActionListener(this);
        salirDadosButton = new JButton("Salir");
        salirDadosButton.addActionListener(this);

        jugarTragamonedasButton = new JButton("Jugar");
        jugarTragamonedasButton.addActionListener(this);
        pausarTragamonedasButton = new JButton("Pausar");
        pausarTragamonedasButton.addActionListener(this);
        salirTragamonedasButton = new JButton("Salir");
        salirTragamonedasButton.addActionListener(this);

        JPanel panelBotonesDados = new JPanel();
        panelBotonesDados.setLayout(new FlowLayout());
        panelBotonesDados.add(jugarDadosButton);
        panelBotonesDados.add(pausarDadosButton);
        panelBotonesDados.add(salirDadosButton);

        JPanel panelBotonesTragamonedas = new JPanel();
        panelBotonesTragamonedas.setLayout(new FlowLayout());
        panelBotonesTragamonedas.add(jugarTragamonedasButton);
        panelBotonesTragamonedas.add(pausarTragamonedasButton);
        panelBotonesTragamonedas.add(salirTragamonedasButton);

        add(panelDados);
        add(panelBotonesDados);
        add(panelTragamonedas);
        add(panelBotonesTragamonedas);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarDadosButton) {
            jugarDados();
        } else if (e.getSource() == pausarDadosButton) {
            panelDados1.pausar();
            panelDados2.pausar();
        } else if (e.getSource() == salirDadosButton) {
            System.exit(0);
        } else if (e.getSource() == jugarTragamonedasButton) {
            jugarTragamonedas();
        } else if (e.getSource() == pausarTragamonedasButton) {
            panelTragamonedas.pausar();
        } else if (e.getSource() == salirTragamonedasButton) {
            System.exit(0);
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            imagenes[i] = new ImageIcon("dados" + (i + 1) + ".png");
        }
        this.caraActual = 0;
        this.timer = new Timer(100, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++;
        if (caraActual >= imagenes.length) {
            caraActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            imagenes[i] = new ImageIcon("simbolo" + (i + 1) + ".png");
        }
        this.simboloActual = 0;
        this.timer = new Timer(200, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++;
        if (simboloActual >= imagenes.length) {
            simboloActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}*/
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarDadosButton;
    private JButton pausarDadosButton;
    private JButton salirDadosButton;
    private JButton jugarTragamonedasButton;
    private JButton pausarTragamonedasButton;
    private JButton salirTragamonedasButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        panelDados1 = new DadoPanel(200, 200);
        panelDados2 = new DadoPanel(200, 200);
        panelTragamonedas = new TragamonedasPanel(150, 200);

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1);
        panelDados.add(panelDados2);

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas);

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarDadosButton = new JButton("Jugar Dados");
        jugarDadosButton.addActionListener(this);

        pausarDadosButton = new JButton("Pausar Dados");
        pausarDadosButton.addActionListener(this);

        salirDadosButton = new JButton("Salir Dados");
        salirDadosButton.addActionListener(this);

        jugarTragamonedasButton = new JButton("Jugar Tragamonedas");
        jugarTragamonedasButton.addActionListener(this);

        pausarTragamonedasButton = new JButton("Pausar Tragamonedas");
        pausarTragamonedasButton.addActionListener(this);

        salirTragamonedasButton = new JButton("Salir Tragamonedas");
        salirTragamonedasButton.addActionListener(this);

        add(panelDados);
        add(jugarDadosButton);
        add(pausarDadosButton);
        add(salirDadosButton);
        add(panelTragamonedas);
        add(jugarTragamonedasButton);
        add(pausarTragamonedasButton);
        add(salirTragamonedasButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarDadosButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione el juego de dados.");
            }
        } else if (e.getSource() == pausarDadosButton) {
            if (juegoDadosRadioButton.isSelected()) {
                panelDados1.pausar();
                panelDados2.pausar();
            }
        } else if (e.getSource() == salirDadosButton) {
            System.exit(0);
        } else if (e.getSource() == jugarTragamonedasButton) {
            if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione el juego de tragamonedas.");
            }
        } else if (e.getSource() == pausarTragamonedasButton) {
            if (juegoTragamonedasRadioButton.isSelected()) {
                panelTragamonedas.pausar();
            }
        } else if (e.getSource() == salirTragamonedasButton) {
            System.exit(0);
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            imagenes[i] = new ImageIcon("dados" + (i + 1) + ".png");
        }
        this.caraActual = 0;
        this.timer = new Timer(100, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++;
        if (caraActual >= imagenes.length) {
            caraActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            imagenes[i] = new ImageIcon("simbolo" + (i + 1) + ".png");
        }
        this.simboloActual = 0;
        this.timer = new Timer(200, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++;
        if (simboloActual >= imagenes.length) {
            simboloActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}*/
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarDadosButton;
    private JButton pausarDadosButton;
    private JButton salirDadosButton;
    private JButton jugarTragamonedasButton;
    private JButton pausarTragamonedasButton;
    private JButton salirTragamonedasButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        panelDados1 = new DadoPanel(200, 200);
        panelDados2 = new DadoPanel(200, 200);
        panelTragamonedas = new TragamonedasPanel(150, 200);

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1);
        panelDados.add(panelDados2);

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas);

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarDadosButton = new JButton("Jugar");
        jugarDadosButton.addActionListener(this);

        pausarDadosButton = new JButton("Pausar");
        pausarDadosButton.addActionListener(this);

        salirDadosButton = new JButton("Salir");
        salirDadosButton.addActionListener(this);

        jugarTragamonedasButton = new JButton("Jugar");
        jugarTragamonedasButton.addActionListener(this);

        pausarTragamonedasButton = new JButton("Pausar");
        pausarTragamonedasButton.addActionListener(this);

        salirTragamonedasButton = new JButton("Salir");
        salirTragamonedasButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(juegoDadosRadioButton);
        add(jugarDadosButton);
        add(pausarDadosButton);
        add(salirDadosButton);
        add(juegoTragamonedasRadioButton);
        add(jugarTragamonedasButton);
        add(pausarTragamonedasButton);
        add(salirTragamonedasButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarDadosButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione el juego de dados.");
            }
        } else if (e.getSource() == pausarDadosButton) {
            if (juegoDadosRadioButton.isSelected()) {
                panelDados1.pausar();
                panelDados2.pausar();
            }
        } else if (e.getSource() == salirDadosButton) {
            System.exit(0);
        } else if (e.getSource() == jugarTragamonedasButton) {
            if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione el juego de tragamonedas.");
            }
        } else if (e.getSource() == pausarTragamonedasButton) {
            if (juegoTragamonedasRadioButton.isSelected()) {
                panelTragamonedas.pausar();
            }
        } else if (e.getSource() == salirTragamonedasButton) {
            System.exit(0);
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            imagenes[i] = new ImageIcon("dados" + (i + 1) + ".png");
        }
        this.caraActual = 0;
        this.timer = new Timer(100, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++;
        if (caraActual >= imagenes.length) {
            caraActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            imagenes[i] = new ImageIcon("simbolo" + (i + 1) + ".png");
        }
        this.simboloActual = 0;
        this.timer = new Timer(200, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++;
        if (simboloActual >= imagenes.length) {
            simboloActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}*/
/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private JButton salirButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        panelDados1 = new DadoPanel(200, 200);
        panelDados2 = new DadoPanel(200, 200);
        panelTragamonedas = new TragamonedasPanel(150, 200);

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1);
        panelDados.add(panelDados2);

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas);

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        salirButton = new JButton("Salir");
        salirButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);
        add(salirButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                panelDados1.pausar();
                panelDados2.pausar();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                panelTragamonedas.pausar();
            }
        } else if (e.getSource() == salirButton) {
            System.exit(0);
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            imagenes[i] = new ImageIcon("dados" + (i + 1) + ".png");
        }
        this.caraActual = 0;
        this.timer = new Timer(100, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++;
        if (caraActual >= imagenes.length) {
            caraActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            imagenes[i] = new ImageIcon("simbolo" + (i + 1) + ".png");
        }
        this.simboloActual = 0;
        this.timer = new Timer(200, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++;
        if (simboloActual >= imagenes.length) {
            simboloActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
*/

/*package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private JButton salirButton;
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        panelDados1 = new DadoPanel(200, 200);
        panelDados2 = new DadoPanel(200, 200);
        panelTragamonedas = new TragamonedasPanel(150, 200);

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1);
        panelDados.add(panelDados2);

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas);

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        salirButton = new JButton("Salir");
        salirButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);
        add(salirButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                panelDados1.pausar();
                panelDados2.pausar();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                panelTragamonedas.pausar();
            }
        } else if (e.getSource() == salirButton) {
            System.exit(0);
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código para el juego de dados...
        JOptionPane.showMessageDialog(this, "¡Jugando a los Dados!");
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código para el juego de tragamonedas...
        JOptionPane.showMessageDialog(this, "¡Jugando a Tragamonedas!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[6];
        for (int i = 0; i < 6; i++) {
            imagenes[i] = new ImageIcon("dados" + (i + 1) + ".png");
        }
        this.caraActual = 0;
        this.timer = new Timer(100, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++;
        if (caraActual >= imagenes.length) {
            caraActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(int ancho, int alto) {
        this.imagenes = new ImageIcon[5];
        for (int i = 0; i < 5; i++) {
            imagenes[i] = new ImageIcon("simbolo" + (i + 1) + ".png");
        }
        this.simboloActual = 0;
        this.timer = new Timer(200, this);
        setPreferredSize(new Dimension(ancho, alto));
        this.retrasoInicial = (int) (Math.random() * 1000);
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0;
        timer.setInitialDelay(retrasoInicial);
        timer.start();
    }

    public void pausar() {
        timer.stop();
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++;
        if (simboloActual >= imagenes.length) {
            simboloActual = 0;
        }
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
*/
/*
package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private JButton pausarButton;
    private ImageIcon imagenDados = new ImageIcon("dados.png");
    private ImageIcon imagenTragamonedas = new ImageIcon("tragamonedas.png");
    private DadoPanel panelDados1;
    private DadoPanel panelDados2;
    private TragamonedasPanel panelTragamonedas;

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLayout(new FlowLayout());

        // crea los paneles de los dados con las imágenes correspondientes
        ImageIcon[] imagenesDados = {
                new ImageIcon("dados1.png"),
                new ImageIcon("dados2.png"),
                new ImageIcon("dados3.png"),
                new ImageIcon("dados4.png"),
                new ImageIcon("dados5.png"),
                new ImageIcon("dados6.png")
        };
        panelDados1 = new DadoPanel(imagenesDados, 200, 200); // ajusta el tamaño del dado 1
        panelDados2 = new DadoPanel(imagenesDados, 200, 200); // ajusta el tamaño del dado 2

        // crea el panel del tragamonedas con las imágenes correspondientes
        ImageIcon[] imagenesTragamonedas = {
                new ImageIcon("simbolo1.png"),
                new ImageIcon("simbolo2.png"),
                new ImageIcon("simbolo3.png"),
                new ImageIcon("simbolo4.png"),
                new ImageIcon("simbolo5.png")
        };
        panelTragamonedas = new TragamonedasPanel(imagenesTragamonedas, 150, 200); // ajusta el tamaño del tragamonedas

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(panelDados1); // agrega el panel del dado 1
        panelDados.add(panelDados2); // agrega el panel del dado 2

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(panelTragamonedas); // agrega el panel del tragamonedas

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        pausarButton = new JButton("Pausar");
        pausarButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);
        add(pausarButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jugarButton) {
            if (juegoDadosRadioButton.isSelected()) {
                jugarDados();
            } else if (juegoTragamonedasRadioButton.isSelected()) {
                jugarTragamonedas();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
            }
        } else if (e.getSource() == pausarButton) {
            panelDados1.pausar();
            panelDados2.pausar();
            panelTragamonedas.pausar();
        }
    }

    private void jugarDados() {
        panelDados1.lanzarConRetrasoAleatorio();
        panelDados2.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    private void jugarTragamonedas() {
        panelTragamonedas.lanzarConRetrasoAleatorio();
        // Resto del código...
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}

class DadoPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int caraActual;
    private Timer timer;
    private int retrasoInicial;

    public DadoPanel(ImageIcon[] imagenes, int ancho, int alto) {
        this.imagenes = imagenes;
        this.caraActual = 0;
        this.timer = new Timer(100, this); // actualiza cada 100 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        caraActual = 0; // reinicia la cara actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        caraActual++; // avanza a la siguiente cara
        if (caraActual >= imagenes.length) {
            caraActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[caraActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}

class TragamonedasPanel extends JPanel implements ActionListener {
    private ImageIcon[] imagenes;
    private int simboloActual;
    private Timer timer;
    private int retrasoInicial;

    public TragamonedasPanel(ImageIcon[] imagenes, int ancho, int alto) {
        this.imagenes = imagenes;
        this.simboloActual = 0;
        this.timer = new Timer(200, this); // actualiza cada 200 milisegundos
        setPreferredSize(new Dimension(ancho, alto)); // ajusta el tamaño del panel
        this.retrasoInicial = (int) (Math.random() * 1000); // establece un retraso aleatorio inicial
    }

    public void lanzarConRetrasoAleatorio() {
        simboloActual = 0; // reinicia el símbolo actual
        timer.setInitialDelay(retrasoInicial); // establece el retraso inicial aleatorio
        timer.start(); // inicia la animación
    }

    public void pausar() {
        timer.stop(); // detiene la animación
    }

    public void actionPerformed(ActionEvent e) {
        simboloActual++; // avanza al siguiente símbolo
        if (simboloActual >= imagenes.length) {
            simboloActual = 0; // vuelve al inicio si llega al final
        }
        repaint(); // repinta el panel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenes[simboloActual].getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}*/


/*
package com.mycompany.juegoscasino.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JuegosAzar extends JFrame implements ActionListener {
    private JRadioButton juegoDadosRadioButton;
    private JRadioButton juegoTragamonedasRadioButton;
    private JButton jugarButton;
    private ImageIcon imagenDados = new ImageIcon("dados.png");
    private ImageIcon imagenTragamonedas = new ImageIcon("tragamonedas.png");

    public JuegosAzar() {
        super("Juegos de Azar");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new FlowLayout());

        JPanel panelDados = new JPanel();
        panelDados.setLayout(new FlowLayout());
        juegoDadosRadioButton = new JRadioButton("Juego de Dados");
        panelDados.add(juegoDadosRadioButton);
        panelDados.add(new JLabel(imagenDados));

        JPanel panelTragamonedas = new JPanel();
        panelTragamonedas.setLayout(new FlowLayout());
        juegoTragamonedasRadioButton = new JRadioButton("Tragamonedas");
        panelTragamonedas.add(juegoTragamonedasRadioButton);
        panelTragamonedas.add(new JLabel(imagenTragamonedas));

        ButtonGroup grupoOpciones = new ButtonGroup();
        grupoOpciones.add(juegoDadosRadioButton);
        grupoOpciones.add(juegoTragamonedasRadioButton);

        jugarButton = new JButton("Jugar");
        jugarButton.addActionListener(this);

        add(panelDados);
        add(panelTragamonedas);
        add(jugarButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (juegoDadosRadioButton.isSelected()) {
            jugarDados();
        } else if (juegoTragamonedasRadioButton.isSelected()) {
            jugarTragamonedas();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un juego.");
        }
    }

    private void jugarDados() {
        int resultado = (int) (Math.random() * 6) + 1;
        JOptionPane.showMessageDialog(this, "Resultado del juego de dados: " + resultado);
    }

    private void jugarTragamonedas() {
        int resultado1 = (int) (Math.random() * 10);
        int resultado2 = (int) (Math.random() * 10);
        int resultado3 = (int) (Math.random() * 10);
        JOptionPane.showMessageDialog(this, "Resultados del juego de tragamonedas: " + resultado1 + ", " + resultado2 + ", " + resultado3);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JuegosAzar();
            }
        });
    }
}
*/




