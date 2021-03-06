package View;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;

import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

import org.apache.commons.io.FileUtils;

import DAO.CategoriaDAO;
import DAO.ProdutoDAO;
import Model.Categoria;
import Model.Produto;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import static java.nio.file.StandardOpenOption.*;

import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.*;
import java.util.*;
import java.awt.Window.Type;

public class IUGerenciamento extends JFrame {

	static Point compCoords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUGerenciamento frame = new IUGerenciamento();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	ConexaoMySQL banco = new ConexaoMySQL();
	JPanel panelErro = new JPanel();
	JLabel lblErro = new JLabel();
	JPanel panelConfirmacao = new JPanel();
	JLabel lblConfirmacao = new JLabel();
	File tempFile;
	Path pathFile, emailFile;
	ToggleSwitch ts = new ToggleSwitch();
	JTextField cadPrecoProduto = new JtextFieldSomenteNumeros(5);
	JTextField cadEstoqueProduto = new JtextFieldSomenteNumeros(2);
	JTextField cadClassificacaoProduto = new JtextFieldSomenteNumeros(1);
	private JTextField cadCPF;
	private int controle = 0;
	private JLabel lblBemVindoLogin;
	private JLabel lblSlogamLogin;
	private Timer timer;
	private long startTime = -1;
	private long duration = 5000;

	/**
	 * Create the frame.
	 */
	public final class JtextFieldSomenteNumeros extends JTextField {
		private int maximoCaracteres=-1;
		
		public JtextFieldSomenteNumeros() {
		        super();
		        addKeyListener(new java.awt.event.KeyAdapter() {
		            @Override
		    public void keyTyped(java.awt.event.KeyEvent evt) {
		        jTextFieldKeyTyped(evt);}});
		    }
		 
		public JtextFieldSomenteNumeros(int maximo) {
		    setMaximoCaracteres(maximo);
		   
		    addKeyListener(new java.awt.event.KeyAdapter() {
		    @Override
		    public void keyTyped(java.awt.event.KeyEvent evt) {
		        jTextFieldKeyTyped(evt);}});
		    }
		
		private void jTextFieldKeyTyped(KeyEvent evt) {       
			String caracteres="0987654321";
		
			if(!caracteres.contains(evt.getKeyChar()+"")){
				evt.consume();
			}
			if((getText().length()>=getMaximoCaracteres())&&(getMaximoCaracteres()!=-1)){
				evt.consume();
				setText(getText().substring(0,getMaximoCaracteres()));
			}
		}
		 
	   public int getMaximoCaracteres() {
		        return maximoCaracteres;
	   }
	   public void setMaximoCaracteres(int maximoCaracteres) {
		        this.maximoCaracteres = maximoCaracteres;
	   }
	}
	
	@SuppressWarnings("unused")
	private static class RoundedBorder implements Border {

	    private int radius;


	    RoundedBorder(int radius) {
	        this.radius = radius;
	    }


	    public Insets getBorderInsets(Component c) {
	        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
	    }


	    public boolean isBorderOpaque() {
	        return true;
	    }


	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
	    }
	}
	
	public class HintTextFieldUI extends BasicTextFieldUI implements FocusListener {
		
	    private String hint;
	    private boolean hideOnFocus;
	    private Color color;

	    public Color getColor() {
	        return color;
	    }

	    public void setColor(Color color) {
	        this.color = color;
	        repaint();
	    }

	    private void repaint() {
	        if(getComponent() != null) {
	            getComponent().repaint();           
	        }
	    }

	    public boolean isHideOnFocus() {
	        return hideOnFocus;
	    }

	    public void setHideOnFocus(boolean hideOnFocus) {
	        this.hideOnFocus = hideOnFocus;
	        repaint();
	    }

	    public String getHint() {
	        return hint;
	    }

	    public void setHint(String hint) {
	        this.hint = hint;
	        repaint();
	    }
	    public HintTextFieldUI(String hint) {
	        this(hint,false);
	    }

	    public HintTextFieldUI(String hint, boolean hideOnFocus) {
	        this(hint,hideOnFocus, null);
	    }

	    public HintTextFieldUI(String hint, boolean hideOnFocus, Color color) {
	        this.hint = hint;
	        this.hideOnFocus = hideOnFocus;
	        this.color = color;
	    }

	    @Override
	    protected void paintSafely(Graphics g) {
	        super.paintSafely(g);
            g.setFont(new Font("Gotham Book", Font.PLAIN, 12));
	        JTextComponent comp = getComponent();
	        if(hint!=null && comp.getText().length() == 0 && (!(hideOnFocus && comp.hasFocus()))){
	            if(color != null) {
	                g.setColor(color);
	            } else {
	                g.setColor(new Color(105, 105, 105));              
	            }
	            int padding = (comp.getHeight() - comp.getFont().getSize())/2;
	            g.drawString(hint, 10, comp.getHeight()-padding-1);    
	        }
	    }

	    
	    public void focusGained(FocusEvent e) {
	        if(hideOnFocus) repaint();
	    }

	    @Override
	    public void focusLost(FocusEvent e) {
	        if(hideOnFocus) repaint();
	    }
	    @Override
	    protected void installListeners() {
	        super.installListeners();
	        getComponent().addFocusListener(this);
	    }
	    @Override
	    protected void uninstallListeners() {
	        super.uninstallListeners();
	        getComponent().removeFocusListener(this);
	    }
	}
	
	public class ToggleSwitch extends JPanel {
	    private boolean activated = false;
	    private Color switchColor = new Color(200, 200, 200), buttonColor = new Color(255, 255, 255), borderColor = Color.DARK_GRAY;
	    private Color activeSwitch = new Color(51, 51, 51);
	    private BufferedImage puffer;
	    private int borderRadius = 10;
	    private Graphics2D g;
	    public ToggleSwitch() {
	        super();
	        setVisible(true);
	        addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseReleased(MouseEvent arg0) {
	                activated = !activated;
	                repaint();
	            }
	        });
	        setCursor(new Cursor(Cursor.HAND_CURSOR));
	        setBounds(0, 0, 41, 21);
	    }
	    @Override
	    public void paint(Graphics gr) {
	        if(g == null || puffer.getWidth() != getWidth() || puffer.getHeight() != getHeight()) {
	            puffer = (BufferedImage) createImage(getWidth(), getHeight());
	            g = (Graphics2D)puffer.getGraphics();
	            RenderingHints rh = new RenderingHints(
	            RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
	            g.setRenderingHints(rh);
	        }
	        g.setColor(activated?activeSwitch:switchColor);
	        g.fillRoundRect(0, 0, this.getWidth()-1,getHeight()-1, 5, borderRadius);
	        g.setColor(borderColor);
	        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 5, borderRadius);
	        g.setColor(buttonColor);
	        if(activated) {
	            g.fillRoundRect(getWidth()/2, 1,  (getWidth()-1)/2 -2, (getHeight()-1) - 2,  borderRadius, borderRadius);
	            g.setColor(borderColor);
	            g.drawRoundRect((getWidth()-1)/2, 0, (getWidth()-1)/2, (getHeight()-1), borderRadius, borderRadius);
	        }
	        else {
	            g.fillRoundRect(1, 1, (getWidth()-1)/2 -2, (getHeight()-1) - 2,  borderRadius, borderRadius);
	            g.setColor(borderColor);
	            g.drawRoundRect(0, 0, (getWidth()-1)/2, (getHeight()-1), borderRadius, borderRadius);
	        }

	        gr.drawImage(puffer, 0, 0, null);
	    }
	    public boolean isActivated() {
	        return activated;
	    }
	    public void setActivated(boolean activated) {
	        this.activated = activated;
	    }
	    public Color getSwitchColor() {
	        return switchColor;
	    }
	    /**
	     * Unactivated Background Color of switch
	     */
	    public void setSwitchColor(Color switchColor) {
	        this.switchColor = switchColor;
	    }
	    public Color getButtonColor() {
	        return buttonColor;
	    }
	    /**
	     * Switch-Button color
	     */
	    public void setButtonColor(Color buttonColor) {
	        this.buttonColor = buttonColor;
	    }
	    public Color getBorderColor() {
	        return borderColor;
	    }
	    /**
	     * Border-color of whole switch and switch-button
	     */
	    public void setBorderColor(Color borderColor) {
	        this.borderColor = borderColor;
	    }
	    public Color getActiveSwitch() {
	        return activeSwitch;
	    }
	    public void setActiveSwitch(Color activeSwitch) {
	        this.activeSwitch = activeSwitch;
	    }
	    /**
	     * @return the borderRadius
	     */
	    public int getBorderRadius() {
	        return borderRadius;
	    }
	    /**
	     * @param borderRadius the borderRadius to set
	     */
	    public void setBorderRadius(int borderRadius) {
	        this.borderRadius = borderRadius;
	    }

	}
	
	public class DropShadowPanel extends JPanel {

	    private static final long serialVersionUID = 1L;

	    public int pixels;

	    public DropShadowPanel(int pix) {
	        this.pixels = pix;
	        Border border = BorderFactory.createEmptyBorder(pixels, pixels, pixels, pixels);
	        this.setBorder(BorderFactory.createCompoundBorder(this.getBorder(), border));
	        this.setLayout(new BorderLayout());
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        int shade = 0;
	        int topOpacity = 80;
	        for (int i = 0; i < pixels; i++) {
	            g.setColor(new Color(shade, shade, shade, ((topOpacity / pixels) * i)));
	            g.drawRect(i, i, this.getWidth() - ((i * 2) + 1), this.getHeight() - ((i * 2) + 1));
	        }
	    }
	}
	
	public class ColorIcon implements Icon {
	    private Color color;
	    private int width;
	    private int height;

	    public ColorIcon(Color color, int width, int height)
	    {
	        this.color = color;
	        this.width = width;
	        this.height = height;
	    }

	    public int getIconWidth()
	    {
	        return width;
	    }

	    public int getIconHeight()
	    {
	        return height;
	    }

	    public void paintIcon(Component c, Graphics g, int x, int y)
	    {
	        g.setColor(color);
	        g.fillRect(x, y, width, height);
	    }
	}
	
	public IUGerenciamento() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFont(new Font("Open Sans", Font.PLAIN, 12));
		setBounds(100, 100, 630, 400);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(IUGerenciamento.class.getResource("/View/images/iconBarra.png")));	
		setUndecorated(true);
		setResizable(false);
		
		JPanel content = new JPanel();
		content.setBackground(Color.DARK_GRAY);
		content.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(content);
		content.setLayout(null);
		
		compCoords = null;
		JPanel titlePanel = new JPanel();
		titlePanel.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseReleased(MouseEvent e) {
                compCoords = null;
            }

            public void mousePressed(MouseEvent e) {
                compCoords = e.getPoint();
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }
		});
		titlePanel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
            public void mouseMoved(MouseEvent e) {
            }

            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - compCoords.x, currCoords.y - compCoords.y);
            }
		});
		content.setLayout(null);
		titlePanel.setForeground(Color.WHITE);
		titlePanel.setBackground(Color.DARK_GRAY);
		titlePanel.setBounds(0, 0, 630, 30);
		content.add(titlePanel);
		titlePanel.setLayout(null);
		
		
		JLabel lblTitulo = new JLabel("eight Store");
		lblTitulo.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(279, 8, 72, 14);
		titlePanel.add(lblTitulo);
		
		JButton btnExit = new JButton("X");
		btnExit.setFocusPainted(false);
		btnExit.setBorder(null);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(Color.DARK_GRAY);
		btnExit.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnExit.setBounds(567, 0, 63, 30);
		btnExit.setIcon(new ColorIcon(Color.DARK_GRAY, btnExit.getWidth(), btnExit.getHeight()));
		btnExit.setHorizontalTextPosition(JButton.CENTER);
		btnExit.setVerticalTextPosition(JButton.CENTER);
		btnExit.setMargin(new Insets(0, 0, 0, 0));
		titlePanel.add(btnExit);
		
		JButton btnMinimize = new JButton("\u2014");
		btnMinimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnMinimize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});
		btnMinimize.setBorder(null);
		btnMinimize.setFocusPainted(false);
		btnMinimize.setForeground(Color.WHITE);
		btnMinimize.setFont(new Font("Dialog", Font.PLAIN, 15));
		btnMinimize.setBackground(Color.DARK_GRAY);
		btnMinimize.setBounds(504, 0, 63, 30);
		btnMinimize.setIcon( new ColorIcon(Color.DARK_GRAY, btnMinimize.getWidth(), btnMinimize.getHeight()));
		btnMinimize.setHorizontalTextPosition(JButton.CENTER);
		btnMinimize.setVerticalTextPosition(JButton.CENTER);
		btnMinimize.setMargin( new Insets(0, 0, 0, 0));
		titlePanel.add(btnMinimize);
		
		JLabel lblIcone = new JLabel("");
		lblIcone.setIcon(new ImageIcon(IUHome.class.getResource("/View/images/icon.png")));
		lblIcone.setBounds(-5, 0, 45, 30);
		titlePanel.add(lblIcone);
		
		JPanel panelCadastrar = new JPanel();
		panelCadastrar.setBounds(0, 30, 630, 650);
		content.add(panelCadastrar);
		panelCadastrar.setBackground(Color.WHITE);
		panelCadastrar.setLayout(null);
		
		JLabel lblLogoLog = new JLabel("");
		lblLogoLog.setLabelFor(panelCadastrar);
		lblLogoLog.setBounds(75, 45, 80, 35);
		panelCadastrar.add(lblLogoLog);
		lblLogoLog.setVerticalAlignment(SwingConstants.TOP);
		lblLogoLog.setIcon(new ImageIcon(IUGerenciamento.class.getResource("/View/images/logo-footer-mini.png")));
		
		lblBemVindoLogin = DefaultComponentFactory.getInstance().createTitle("Bem vindo ao gerenciamento da eight");
		lblBemVindoLogin.setForeground(Color.DARK_GRAY);
		lblBemVindoLogin.setFont(new Font("Gotham Medium", Font.BOLD, 25));
		lblBemVindoLogin.setBounds(75, 120, 480, 35);
		panelCadastrar.add(lblBemVindoLogin);
		
		lblSlogamLogin = DefaultComponentFactory.getInstance().createTitle("Escolha abaixo alguma das op\u00E7\u00F5es de cadastro");
		lblSlogamLogin.setForeground(Color.DARK_GRAY);
		lblSlogamLogin.setFont(new Font("Gotham Light", Font.PLAIN, 11));
		lblSlogamLogin.setBounds(75, 155, 465, 13);
		panelCadastrar.add(lblSlogamLogin);
		
		JButton btnCadastrarCategoria = new JButton("Cadastrar categoria");
		btnCadastrarCategoria.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IUCadastrarCategoria cadCategoria = new IUCadastrarCategoria();
				cadCategoria.setVisible(true);
			}
		});
		btnCadastrarCategoria.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnCadastrarCategoria.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnCadastrarCategoria.setBounds(75, 200, 230, 40);
		btnCadastrarCategoria.setBorder(null);
		btnCadastrarCategoria.setIcon(new ColorIcon(Color.DARK_GRAY, btnCadastrarCategoria.getWidth(), btnCadastrarCategoria.getHeight()));
		btnCadastrarCategoria.setHorizontalTextPosition(JButton.CENTER);
		btnCadastrarCategoria.setVerticalTextPosition(JButton.CENTER);
		btnCadastrarCategoria.setMargin(new Insets(0, 0, 0, 0));
		btnCadastrarCategoria.setBackground(Color.DARK_GRAY);
		btnCadastrarCategoria.setFont(new Font("Gotham Book", Font.PLAIN, 16));
		btnCadastrarCategoria.setFocusPainted(false);
		btnCadastrarCategoria.setForeground(Color.WHITE);
		panelCadastrar.add(btnCadastrarCategoria);
		
		JButton btnCadastrarProduto = new JButton("Cadastrar produto");
		btnCadastrarProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IUCadastrarProduto cadProduto = new IUCadastrarProduto();
				cadProduto.setVisible(true);
			}
		});
		btnCadastrarProduto.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnCadastrarProduto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnCadastrarProduto.setVerticalTextPosition(SwingConstants.CENTER);
		btnCadastrarProduto.setMargin(new Insets(0, 0, 0, 0));
		btnCadastrarProduto.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCadastrarProduto.setForeground(Color.WHITE);
		btnCadastrarProduto.setFont(new Font("Gotham Book", Font.PLAIN, 16));
		btnCadastrarProduto.setFocusPainted(false);
		btnCadastrarProduto.setBorder(null);
		btnCadastrarProduto.setIcon(new ColorIcon(Color.DARK_GRAY, btnCadastrarCategoria.getWidth(), btnCadastrarCategoria.getHeight()));
		btnCadastrarProduto.setBackground(Color.DARK_GRAY);
		btnCadastrarProduto.setBounds(325, 200, 230, 40);
		panelCadastrar.add(btnCadastrarProduto);
		
		JButton btnCadastrarUsuario = new JButton("Cadastrar usu\u00E1rio");
		btnCadastrarUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IUCadastrarUsuario cadUsuario = new IUCadastrarUsuario();
				cadUsuario.setVisible(true);
			}
		});
		btnCadastrarUsuario.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnCadastrarUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnCadastrarUsuario.setVerticalTextPosition(SwingConstants.CENTER);
		btnCadastrarUsuario.setMargin(new Insets(0, 0, 0, 0));
		btnCadastrarUsuario.setHorizontalTextPosition(SwingConstants.CENTER);
		btnCadastrarUsuario.setForeground(Color.WHITE);
		btnCadastrarUsuario.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnCadastrarUsuario.setFocusPainted(false);
		btnCadastrarUsuario.setBorder(null);
		btnCadastrarUsuario.setIcon(new ColorIcon(Color.DARK_GRAY, btnCadastrarCategoria.getWidth(), btnCadastrarCategoria.getHeight()));
		btnCadastrarUsuario.setBackground(Color.DARK_GRAY);
		btnCadastrarUsuario.setBounds(75, 261, 230, 40);
		panelCadastrar.add(btnCadastrarUsuario);
		
		JButton btnPesquisarUsuarios = new JButton("Pesquisar usu\u00E1rios");
		btnPesquisarUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IUCadastrarUsuario cadUsuario = new IUCadastrarUsuario();
				cadUsuario.setVisible(true);
			}
		});
		btnPesquisarUsuarios.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnPesquisarUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnPesquisarUsuarios.setVerticalTextPosition(SwingConstants.CENTER);
		btnPesquisarUsuarios.setMargin(new Insets(0, 0, 0, 0));
		btnPesquisarUsuarios.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPesquisarUsuarios.setForeground(Color.WHITE);
		btnPesquisarUsuarios.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnPesquisarUsuarios.setFocusPainted(false);
		btnPesquisarUsuarios.setBorder(null);
		btnPesquisarUsuarios.setBackground(Color.DARK_GRAY);
		btnPesquisarUsuarios.setBounds(325, 261, 230, 40);
		panelCadastrar.add(btnPesquisarUsuarios);
		

	}
}