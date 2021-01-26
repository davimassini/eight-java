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

import Control.CtrlCadUsuario;
import DAO.UsuarioDAO;
import Model.Usuario;

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

public class IUCadastrarUsuario extends JFrame {

	static Point compCoords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUCadastrarUsuario frame = new IUCadastrarUsuario();
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
	JTextField cadCPF = new JtextFieldSomenteNumeros(11);
	private JTextField cadNomeUsuario = new JTextField();
	private JTextField cadEnderecoEmail = new JTextField();
	private JPasswordField cadSenhaUsuario = new JPasswordField();
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
	
	public IUCadastrarUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFont(new Font("Open Sans", Font.PLAIN, 12));
		setBounds(100, 100, 630, 680);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(IUCadastrarUsuario.class.getResource("/View/images/iconBarra.png")));	
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
		lblLogoLog.setIcon(new ImageIcon(IUCadastrarUsuario.class.getResource("/View/images/logo-footer-mini.png")));
		
		lblBemVindoLogin = DefaultComponentFactory.getInstance().createTitle("Seja bem vindo ao cadastro da eight");
		lblBemVindoLogin.setForeground(Color.DARK_GRAY);
		lblBemVindoLogin.setFont(new Font("Gotham Medium", Font.BOLD, 25));
		lblBemVindoLogin.setBounds(75, 120, 463, 35);
		panelCadastrar.add(lblBemVindoLogin);
		
		lblSlogamLogin = DefaultComponentFactory.getInstance().createTitle("Digite abaixo as informa\u00E7\u00F5es para o cadastro do novo produto");
		lblSlogamLogin.setForeground(Color.DARK_GRAY);
		lblSlogamLogin.setFont(new Font("Gotham Light", Font.PLAIN, 11));
		lblSlogamLogin.setBounds(75, 155, 465, 13);
		panelCadastrar.add(lblSlogamLogin);
		
		cadCPF.setBounds(75, 215, 465, 40);
		cadCPF.setUI(new HintTextFieldUI("Números do CPF", true));
		panelCadastrar.add(cadCPF);
		cadCPF.setForeground(Color.BLACK);
		cadCPF.setBackground(Color.WHITE);
		cadCPF.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		cadCPF.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		cadCPF.setColumns(10);
		
		cadNomeUsuario.setUI(new HintTextFieldUI("Nome completo", true));
		cadNomeUsuario.setForeground(Color.BLACK);
		cadNomeUsuario.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		cadNomeUsuario.setColumns(10);
		cadNomeUsuario.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		cadNomeUsuario.setBackground(Color.WHITE);
		cadNomeUsuario.setBounds(75, 266, 465, 40);
		panelCadastrar.add(cadNomeUsuario);

		cadSenhaUsuario.setBounds(75, 317, 465, 40);
		cadSenhaUsuario.setUI(new HintTextFieldUI("Senha do usuário", true));
		panelCadastrar.add(cadSenhaUsuario);
		cadSenhaUsuario.setForeground(Color.BLACK);
		cadSenhaUsuario.setBackground(Color.WHITE);
		cadSenhaUsuario.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		cadSenhaUsuario.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		
		cadEnderecoEmail.setUI(new HintTextFieldUI("Endereço de email", true));
		cadEnderecoEmail.setForeground(Color.BLACK);
		cadEnderecoEmail.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		cadEnderecoEmail.setColumns(10);
		cadEnderecoEmail.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		cadEnderecoEmail.setBackground(Color.WHITE);
		cadEnderecoEmail.setBounds(75, 368, 465, 40);
		panelCadastrar.add(cadEnderecoEmail);
		
		JButton btnCadastrarUsuario = new JButton("Cadastrar usu\u00E1rio");
		btnCadastrarUsuario.setBounds(75, 419, 465, 40);
		btnCadastrarUsuario.setBorder(null);
		btnCadastrarUsuario.setIcon(new ColorIcon(Color.DARK_GRAY, btnCadastrarUsuario.getWidth(), btnCadastrarUsuario.getHeight()));
		btnCadastrarUsuario.setHorizontalTextPosition(JButton.CENTER);
		btnCadastrarUsuario.setVerticalTextPosition(JButton.CENTER);
		btnCadastrarUsuario.setMargin(new Insets(0, 0, 0, 0));
		btnCadastrarUsuario.setBackground(Color.DARK_GRAY);
		btnCadastrarUsuario.setFont(new Font("Gotham Book", Font.PLAIN, 16));
		btnCadastrarUsuario.setFocusPainted(false);
		btnCadastrarUsuario.setForeground(Color.WHITE);
		panelCadastrar.add(btnCadastrarUsuario);
		
		JButton btnPesquisarUsuario = new JButton("Pesquisar usu\u00E1rio");
		btnPesquisarUsuario.setIcon(new ColorIcon(Color.DARK_GRAY, btnCadastrarUsuario.getWidth(), btnCadastrarUsuario.getHeight()));
		btnPesquisarUsuario.setVerticalTextPosition(SwingConstants.CENTER);
		btnPesquisarUsuario.setMargin(new Insets(0, 0, 0, 0));
		btnPesquisarUsuario.setHorizontalTextPosition(SwingConstants.CENTER);
		btnPesquisarUsuario.setForeground(Color.WHITE);
		btnPesquisarUsuario.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnPesquisarUsuario.setFocusPainted(false);
		btnPesquisarUsuario.setBorder(null);
		btnPesquisarUsuario.setBackground(Color.DARK_GRAY);
		btnPesquisarUsuario.setBounds(75, 470, 465, 40);
		panelCadastrar.add(btnPesquisarUsuario);
		
		JButton btnAlterarUsuario = new JButton("Alterar usu\u00E1rio");
		btnAlterarUsuario.setIcon(new ColorIcon(Color.DARK_GRAY, btnCadastrarUsuario.getWidth(), btnCadastrarUsuario.getHeight()));
		btnAlterarUsuario.setVerticalTextPosition(SwingConstants.CENTER);
		btnAlterarUsuario.setMargin(new Insets(0, 0, 0, 0));
		btnAlterarUsuario.setHorizontalTextPosition(SwingConstants.CENTER);
		btnAlterarUsuario.setForeground(Color.WHITE);
		btnAlterarUsuario.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnAlterarUsuario.setFocusPainted(false);
		btnAlterarUsuario.setBorder(null);
		btnAlterarUsuario.setBackground(Color.DARK_GRAY);
		btnAlterarUsuario.setBounds(73, 521, 465, 40);
		panelCadastrar.add(btnAlterarUsuario);
		
		JButton btnExcluirUsuario = new JButton("Excluir usu\u00E1rio");
		btnExcluirUsuario.setIcon(new ColorIcon(Color.DARK_GRAY, btnCadastrarUsuario.getWidth(), btnCadastrarUsuario.getHeight()));
		btnExcluirUsuario.setVerticalTextPosition(SwingConstants.CENTER);
		btnExcluirUsuario.setMargin(new Insets(0, 0, 0, 0));
		btnExcluirUsuario.setHorizontalTextPosition(SwingConstants.CENTER);
		btnExcluirUsuario.setForeground(Color.WHITE);
		btnExcluirUsuario.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnExcluirUsuario.setFocusPainted(false);
		btnExcluirUsuario.setBorder(null);
		btnExcluirUsuario.setBackground(Color.DARK_GRAY);
		btnExcluirUsuario.setBounds(73, 572, 465, 40);
		panelCadastrar.add(btnExcluirUsuario);
		
		btnCadastrarUsuario.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnCadastrarUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnCadastrarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCadastrarUsuario.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							banco.abrirConexao();
							banco.stmt = banco.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
							String query = "SELECT * FROM usuarios";
							banco.resultset = banco.stmt.executeQuery(query);
							
							Usuario usuarios = new Usuario();
							usuarios.setCpf(cadCPF.getText());
							usuarios.setNomeCompleto(cadNomeUsuario.getText());
							usuarios.setEnderecoEmail(cadEnderecoEmail.getText());
							usuarios.setSenha(cadSenhaUsuario.getText());
							
							UsuarioDAO dao = new UsuarioDAO();
						    dao.adicionarUsuario(usuarios);
							
				            banco.fecharConexao();
				           
						}catch(Exception ec){}
					}
				});
			}
		});
		
		btnPesquisarUsuario.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnPesquisarUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnPesquisarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPesquisarUsuario.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CtrlCadUsuario ctrlCadUsuario = new CtrlCadUsuario();
						try {
							Usuario usuarios = ctrlCadUsuario.procurarUsuario(cadCPF.getText());
							cadNomeUsuario.setText(usuarios.getNomeCompleto());
							cadEnderecoEmail.setText(usuarios.getEnderecoEmail());
							cadEnderecoEmail.setText(usuarios.getSenha());
							
				            banco.fecharConexao();
				           
						}catch(Exception ec){}
					}
				});
			}
		});
		
		btnAlterarUsuario.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnAlterarUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnAlterarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAlterarUsuario.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							banco.abrirConexao();
							banco.stmt = banco.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
							String query = "SELECT * FROM usuarios";
							banco.resultset = banco.stmt.executeQuery(query);
							
							Usuario usuarios = new Usuario();
							usuarios.setCpf(cadCPF.getText());
							usuarios.setNomeCompleto(cadNomeUsuario.getText());
							usuarios.setEnderecoEmail(cadEnderecoEmail.getText());
							usuarios.setSenha(cadSenhaUsuario.getText());
							
							String input = cadCPF.getText();
							int count = input.length();
							
							int stop = 0;
							
							UsuarioDAO dao = new UsuarioDAO();
						    dao.alterarUsuario(usuarios);
							
				            banco.fecharConexao();
				           
						}catch(Exception ec){}
					}
				});
			}
		});
		
		btnExcluirUsuario.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnExcluirUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnExcluirUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnExcluirUsuario.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							banco.abrirConexao();
							banco.stmt = banco.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
							String query = "SELECT * FROM usuarios";
							banco.resultset = banco.stmt.executeQuery(query);
							
							Usuario usuarios = new Usuario();
							usuarios.setCpf(cadCPF.getText());
							
							String input = cadCPF.getText();
							int count = input.length();
							
							int stop = 0;
							
							UsuarioDAO dao = new UsuarioDAO();
						    dao.deletarUsuario(usuarios);
							
				            banco.fecharConexao();
				           
						}catch(Exception ec){}
					}
				});
			}
		});
	}
}