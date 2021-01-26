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

public class IULoginCadastro extends JFrame {

	static Point compCoords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IULoginCadastro frame = new IULoginCadastro();
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
	JTextField logLoginField = new JTextField();
	
	private JTextField cadLoginField;
	private JPasswordField cadPasswordField;
	private JTextField cadNomeCompleto;
	private JTextField cadCPF;
	private int controle = 0;
	private JLabel lblBemVindoLogin;
	private JLabel lblSlogamLogin;
	private JLabel lblBemVindoCadastro;
	private JLabel lblCadastroSlogam;
	private JLabel lblBemVindoRec;
	private JLabel lblRecuperacaoSlogam;
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
	
	public IULoginCadastro() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFont(new Font("Open Sans", Font.PLAIN, 12));
		setBounds(100, 100, 1440, 900);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(IULoginCadastro.class.getResource("/View/images/iconBarra.png")));	
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
		titlePanel.setBounds(0, 0, 1440, 30);
		content.add(titlePanel);
		titlePanel.setLayout(null);
		
		
		JLabel lblTitulo = new JLabel("eight Store");
		lblTitulo.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		lblTitulo.setForeground(Color.WHITE);
		lblTitulo.setBounds(684, 8, 72, 14);
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
		btnExit.setBounds(1377, 0, 63, 30);
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
		btnMinimize.setBounds(1314, 0, 63, 30);
		btnMinimize.setIcon( new ColorIcon(Color.DARK_GRAY, btnMinimize.getWidth(), btnMinimize.getHeight()));
		btnMinimize.setHorizontalTextPosition(JButton.CENTER);
		btnMinimize.setVerticalTextPosition(JButton.CENTER);
		btnMinimize.setMargin( new Insets(0, 0, 0, 0));
		titlePanel.add(btnMinimize);
		
		JLabel lblIcone = new JLabel("");
		lblIcone.setIcon(new ImageIcon(IUHome.class.getResource("/View/images/icon.png")));
		lblIcone.setBounds(-5, 0, 45, 30);
		titlePanel.add(lblIcone);
		
		JLabel lblBRA = new JLabel("BRA");
		lblBRA.setForeground(Color.WHITE);
		lblBRA.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				lblBRA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblBRA.setHorizontalAlignment(SwingConstants.LEFT);
		lblBRA.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
		lblBRA.setBounds(1228, 860, 32, 14);
		content.add(lblBRA);
		
		JLabel lblENG = new JLabel("ENG");
		lblENG.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				lblENG.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblENG.setForeground(Color.LIGHT_GRAY);
		lblENG.setHorizontalAlignment(SwingConstants.CENTER);
		lblENG.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
		lblENG.setBounds(1298, 860, 32, 14);
		content.add(lblENG);
		
		JLabel lblITA = new JLabel("ITA");
		lblITA.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				lblITA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblITA.setForeground(Color.LIGHT_GRAY);
		lblITA.setHorizontalAlignment(SwingConstants.RIGHT);
		lblITA.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
		lblITA.setBounds(1368, 860, 32, 14);
		content.add(lblITA);
		
		CardLayout c1 = new CardLayout();
		
		JPanel panelCardLayout = new JPanel();
		panelCardLayout.setBackground(Color.WHITE);
		panelCardLayout.setLayout(c1);
		panelCardLayout.setBounds(0, 30, 630, 870);
		content.add(panelCardLayout);
		
		JPanel panelLogin = new JPanel();
		panelCardLayout.add(panelLogin, "1");
		panelLogin.setBackground(Color.WHITE);
		panelLogin.setLayout(null);
		
		JPanel panelCadastro = new JPanel();
		panelCardLayout.add(panelCadastro, "2");
		panelCadastro.setBackground(Color.WHITE);
		panelCadastro.setLayout(null);
		
		c1.show(panelCardLayout, "1");
		
		JLabel lblLogoLog = new JLabel("");
		lblLogoLog.setLabelFor(panelLogin);
		lblLogoLog.setBounds(75, 45, 80, 35);
		panelLogin.add(lblLogoLog);
		lblLogoLog.setVerticalAlignment(SwingConstants.TOP);
		lblLogoLog.setIcon(new ImageIcon(IULoginCadastro.class.getResource("/View/images/logo-footer-mini.png")));
		
		lblBemVindoLogin = DefaultComponentFactory.getInstance().createTitle("Seja bem vindo ao aplicativo da eight");
		lblBemVindoLogin.setForeground(Color.DARK_GRAY);
		lblBemVindoLogin.setFont(new Font("Gotham Medium", Font.BOLD, 25));
		lblBemVindoLogin.setBounds(75, 120, 463, 26);
		panelLogin.add(lblBemVindoLogin);
		
		lblSlogamLogin = DefaultComponentFactory.getInstance().createTitle("Digite abaixo as suas informa\u00E7\u00F5es de login e senha para acessar sua conta");
		lblSlogamLogin.setForeground(Color.DARK_GRAY);
		lblSlogamLogin.setFont(new Font("Gotham Light", Font.PLAIN, 11));
		lblSlogamLogin.setBounds(75, 155, 465, 13);
		panelLogin.add(lblSlogamLogin);
		
		logLoginField.setBounds(75, 215, 465, 40);
		logLoginField.setUI(new HintTextFieldUI("Endereço de email", true));
		panelLogin.add(logLoginField);
		logLoginField.setForeground(Color.BLACK);
		logLoginField.setBackground(Color.WHITE);
		logLoginField.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		logLoginField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		logLoginField.setColumns(10);
		
		JPasswordField logPasswordField = new JPasswordField();
		//logPasswordField.setUI(new HintTextFieldUI("Senha", true));
		logPasswordField.setMargin(new Insets(0,20,0,0));
		logPasswordField.setBounds(75, 266, 465, 40);
		logPasswordField.setForeground(Color.BLACK);
		logPasswordField.setToolTipText("");
		logPasswordField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		logPasswordField.setBackground(Color.WHITE);
		logPasswordField.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		panelLogin.add(logPasswordField);
		
		JButton btnLogLogar = new JButton("Entrar");
		btnLogLogar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnLogLogar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnLogLogar.setBounds(82, 381, 465, 40);
		btnLogLogar.setBorder(null);
		btnLogLogar.setIcon(new ColorIcon(Color.DARK_GRAY, btnLogLogar.getWidth(), btnLogLogar.getHeight()));
		btnLogLogar.setHorizontalTextPosition(JButton.CENTER);
		btnLogLogar.setVerticalTextPosition(JButton.CENTER);
		btnLogLogar.setMargin(new Insets(0, 0, 0, 0));
		btnLogLogar.setBackground(Color.DARK_GRAY);
		btnLogLogar.setFont(new Font("Gotham Book", Font.PLAIN, 16));
		btnLogLogar.setFocusPainted(false);
		btnLogLogar.setForeground(Color.WHITE);
		panelLogin.add(btnLogLogar);
		
		try {		
			pathFile = Paths.get("preferencias.xml");
			emailFile = Paths.get("email.xml");
		    
			if(Files.exists(pathFile) && Files.exists(emailFile)){
			    String expectedData = "<ACTIVATED>TRUE</ACTIVATED>";
			    List<String> lines = Files.readAllLines(Paths.get("preferencias.xml"));
			    List<String> emailLines = Files.readAllLines(Paths.get("email.xml"));
			    
			    if(lines.toString().contains(expectedData)){
			    	ts.activated = true;
			    	logLoginField.setText(emailLines.toString().replace("[", "").replace("]", ""));
			    }else{
			    	ts.activated = false;
			    }
			}else{
				try(BufferedWriter writer = Files.newBufferedWriter(pathFile, Charset.forName("UTF-8"))){
					writer.write("<ACTIVATED>FALSE</ACTIVATED>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				try(BufferedWriter writer = Files.newBufferedWriter(emailFile, Charset.forName("UTF-8"))){
					writer.write("<EMAIL></EMAIL>");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		    
		}catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		
		ts.setBounds(75, 334, 40, 23);
		ts.setBorderRadius(0);
		panelLogin.add(ts);
		ts.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(logLoginField.getText().isEmpty()){
					try(BufferedWriter writer = Files.newBufferedWriter(pathFile, Charset.forName("UTF-8"))){
						writer.write("<ACTIVATED>FALSE</ACTIVATED>");
					} catch (IOException e) {
						e.printStackTrace();
					}
					try(BufferedWriter writer = Files.newBufferedWriter(emailFile, Charset.forName("UTF-8"))){
						writer.write("<EMAIL></EMAIL>");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					if(ts.activated == true){
						try(BufferedWriter writer = Files.newBufferedWriter(pathFile, Charset.forName("UTF-8"))){
							writer.write("<ACTIVATED>TRUE</ACTIVATED>");
						} catch (IOException e) {
							e.printStackTrace();
						}
						try(BufferedWriter writer = Files.newBufferedWriter(emailFile, Charset.forName("UTF-8"))){
							writer.write(logLoginField.getText());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}else{
						try(BufferedWriter writer = Files.newBufferedWriter(pathFile, Charset.forName("UTF-8"))){
							writer.write("<ACTIVATED>FALSE</ACTIVATED>");
						} catch (IOException e) {
							e.printStackTrace();
						}
						try(BufferedWriter writer = Files.newBufferedWriter(emailFile, Charset.forName("UTF-8"))){
							writer.write("<EMAIL></EMAIL>");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		JLabel lblLembrarDeMim = new JLabel("Lembrar de mim");
		lblLembrarDeMim.setHorizontalAlignment(SwingConstants.LEFT);
		lblLembrarDeMim.setForeground(Color.DARK_GRAY);
		lblLembrarDeMim.setFont(new Font("Gotham Book", Font.BOLD, 13));
		lblLembrarDeMim.setBounds(125, 339, 120, 14);
		panelLogin.add(lblLembrarDeMim);
		
		JLabel lblRecuperarSenha = new JLabel("Esqueceu sua senha?");
		lblRecuperarSenha.setBounds(391, 336, 149, 21);
		panelLogin.add(lblRecuperarSenha);
		lblRecuperarSenha.setBackground(Color.LIGHT_GRAY);
		lblRecuperarSenha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRecuperarSenha.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				lblRecuperarSenha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblRecuperarSenha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				c1.show(panelCardLayout, "3");
			}
		});
		lblRecuperarSenha.setFont(new Font("Gotham Book", Font.BOLD, 11));
		lblRecuperarSenha.setForeground(Color.LIGHT_GRAY);
		
		JLabel lblOthers = new JLabel("\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014 ou \u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014");
		lblOthers.setHorizontalAlignment(SwingConstants.CENTER);
		lblOthers.setFont(new Font("Gotham Book", Font.BOLD, 10));
		lblOthers.setBounds(75, 461, 463, 14);
		panelLogin.add(lblOthers);
		
		JButton btnEntrarFacebook = new JButton("Entrar com o Facebook");
		btnEntrarFacebook.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				btnEntrarFacebook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnEntrarFacebook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnEntrarFacebook.setForeground(Color.WHITE);
		btnEntrarFacebook.setBorder(null);
		btnEntrarFacebook.setFont(new Font("Gotham Book", Font.PLAIN, 11));
		btnEntrarFacebook.setFocusPainted(false);
		btnEntrarFacebook.setBackground(new Color(59,89,152));
		btnEntrarFacebook.setBounds(75, 515, 200, 35);
		btnEntrarFacebook.setIcon(new ColorIcon(new Color(59,89,152), btnEntrarFacebook.getWidth(), btnEntrarFacebook.getHeight()));
		btnEntrarFacebook.setHorizontalTextPosition(JButton.CENTER);
		btnEntrarFacebook.setVerticalTextPosition(JButton.CENTER);
		btnEntrarFacebook.setMargin(new Insets(0, 0, 0, 0));
		panelLogin.add(btnEntrarFacebook);
		
		JButton btnEntrarGoogle = new JButton("Entrar com o Google");
		btnEntrarGoogle.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				btnEntrarGoogle.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnEntrarGoogle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEntrarGoogle.setMargin(new Insets(0, 0, 0, 0));
		btnEntrarGoogle.setBorder(null);
		btnEntrarGoogle.setForeground(Color.WHITE);
		btnEntrarGoogle.setFont(new Font("Gotham Book", Font.PLAIN, 11));
		btnEntrarGoogle.setFocusPainted(false);
		btnEntrarGoogle.setBackground(new Color(219,68, 55));
		btnEntrarGoogle.setBounds(338, 515, 200, 35);
		btnEntrarGoogle.setIcon(new ColorIcon(new Color(219,68, 55), btnEntrarGoogle.getWidth(), btnEntrarGoogle.getHeight()));
		btnEntrarGoogle.setHorizontalTextPosition(JButton.CENTER);
		btnEntrarGoogle.setVerticalTextPosition(JButton.CENTER);
		panelLogin.add(btnEntrarGoogle);
		
		JLabel lblLogCadastrar = new JLabel("Ainda n\u00E3o tem uma conta? Registre-se.");
		lblLogCadastrar.setBounds(175, 595, 265, 14);
		panelLogin.add(lblLogCadastrar);
		lblLogCadastrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogCadastrar.setForeground(Color.DARK_GRAY);
		lblLogCadastrar.setFont(new Font("Gotham Book", Font.BOLD, 13));
		
		lblLogCadastrar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				lblLogCadastrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				
			}
		});
		
		JLabel lblLogoCad = new JLabel("");
		lblLogoCad.setLabelFor(panelCadastro);
		lblLogoCad.setIcon(new ImageIcon(IULoginCadastro.class.getResource("/View/images/logo-footer-mini.png")));
		lblLogoCad.setVerticalAlignment(SwingConstants.TOP);
		lblLogoCad.setBounds(75, 45, 80, 35);
		panelCadastro.add(lblLogoCad);
		
		lblBemVindoCadastro = DefaultComponentFactory.getInstance().createTitle("Seja bem vindo a cria\u00E7\u00E3o de contas");
		lblBemVindoCadastro.setForeground(Color.DARK_GRAY);
		lblBemVindoCadastro.setFont(new Font("Gotham Medium", Font.BOLD, 25));
		lblBemVindoCadastro.setBounds(75, 120, 463, 26);
		panelCadastro.add(lblBemVindoCadastro);
		
		lblCadastroSlogam = DefaultComponentFactory.getInstance().createTitle("Digite abaixo as suas informa\u00E7\u00F5es de login e senha para criar sua conta");
		lblCadastroSlogam.setForeground(Color.DARK_GRAY);
		lblCadastroSlogam.setFont(new Font("Gotham Light", Font.PLAIN, 11));
		lblCadastroSlogam.setBounds(75, 155, 465, 13);
		panelCadastro.add(lblCadastroSlogam);
		
		cadNomeCompleto = new JTextField();
		cadNomeCompleto.setUI(new HintTextFieldUI("Nome completo", true));
		cadNomeCompleto.setForeground(Color.BLACK);
		cadNomeCompleto.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		cadNomeCompleto.setColumns(10);
		cadNomeCompleto.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		cadNomeCompleto.setBackground(Color.WHITE);
		cadNomeCompleto.setBounds(75, 215, 465, 40);
		panelCadastro.add(cadNomeCompleto);
		
		cadCPF = new JtextFieldSomenteNumeros(11);
		cadCPF.setUI(new HintTextFieldUI("CPF", true));
		cadCPF.setForeground(Color.BLACK);
		cadCPF.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		cadCPF.setColumns(10);
		cadCPF.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		cadCPF.setBackground(Color.WHITE);
		cadCPF.setBounds(75, 266, 465, 40);
		panelCadastro.add(cadCPF);
		
		cadLoginField = new JTextField();
		cadLoginField.setUI(new HintTextFieldUI("Endereço de email", true));
		cadLoginField.setBounds(75, 317, 465, 40);
		panelCadastro.add(cadLoginField);
		cadLoginField.setForeground(Color.BLACK);
		cadLoginField.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		cadLoginField.setColumns(10);
		cadLoginField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		cadLoginField.setBackground(Color.WHITE);
		
		cadPasswordField = new JPasswordField();
		cadPasswordField.setUI(new HintTextFieldUI("Senha", true));
		cadPasswordField.setToolTipText("");
		cadPasswordField.setForeground(Color.BLACK);
		cadPasswordField.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		cadPasswordField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		cadPasswordField.setBackground(Color.WHITE);
		cadPasswordField.setBounds(75, 368, 465, 40);
		panelCadastro.add(cadPasswordField);
		
		JButton btnCadConfirmar = new JButton("Registrar");
		btnCadConfirmar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				btnCadConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnCadConfirmar.setFont(new Font("Gotham Book", Font.PLAIN, 16));
		btnCadConfirmar.setFocusPainted(false);
		btnCadConfirmar.setBackground(Color.WHITE);
		btnCadConfirmar.setForeground(Color.WHITE);
		btnCadConfirmar.setBorder(null);
		btnCadConfirmar.setOpaque(false);
		btnCadConfirmar.setBounds(75, 435, 463, 40);
		btnCadConfirmar.setIcon( new ColorIcon(Color.DARK_GRAY, btnCadConfirmar.getWidth(), btnCadConfirmar.getHeight()));
		btnCadConfirmar.setHorizontalTextPosition(JButton.CENTER);
		btnCadConfirmar.setVerticalTextPosition(JButton.CENTER);
		btnCadConfirmar.setMargin(new Insets(0, 0, 0, 0));
		panelCadastro.add(btnCadConfirmar);
		
		JLabel lblLogEntrar = new JLabel("Criou sua conta? Volte para a \u00E1rea de login.");
		lblLogEntrar.setBounds(161, 500, 291, 14);
		panelCadastro.add(lblLogEntrar);
		lblLogEntrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogEntrar.setForeground(Color.BLACK);
		lblLogEntrar.setFont(new Font("Gotham Book", Font.BOLD, 13));
		
		JPanel panelRecuperar = new JPanel();
		panelCardLayout.add(panelRecuperar, "3");
		panelRecuperar.setLayout(null);
		
		JLabel lblLogoRec = new JLabel("");
		lblLogoRec.setLabelFor(panelLogin);
		lblLogoRec.setBounds(75, 45, 80, 35);
		panelRecuperar.add(lblLogoRec);
		lblLogoRec.setVerticalAlignment(SwingConstants.TOP);
		lblLogoRec.setIcon(new ImageIcon(IULoginCadastro.class.getResource("/View/images/logo-footer-mini.png")));
		
		lblBemVindoRec = DefaultComponentFactory.getInstance().createTitle("Seja bem vindo ao aplicativo da eight");
		lblBemVindoRec.setForeground(Color.DARK_GRAY);
		lblBemVindoRec.setFont(new Font("Gotham Medium", Font.BOLD, 25));
		lblBemVindoRec.setBounds(75, 120, 463, 26);
		panelRecuperar.add(lblBemVindoRec);
		
		lblRecuperacaoSlogam = DefaultComponentFactory.getInstance().createTitle("Digite abaixo as suas informa\u00E7\u00F5es de login para recuperar sua senha");
		lblRecuperacaoSlogam.setForeground(Color.DARK_GRAY);
		lblRecuperacaoSlogam.setFont(new Font("Gotham Light", Font.PLAIN, 11));
		lblRecuperacaoSlogam.setBounds(75, 155, 465, 13);
		panelRecuperar.add(lblRecuperacaoSlogam);
		
		JTextField logRecField = new JTextField();
		logRecField.setBounds(75, 215, 465, 40);
		logRecField.setUI(new HintTextFieldUI("Endereço de email", true));
		panelRecuperar.add(logRecField);
		logRecField.setForeground(Color.BLACK);
		logRecField.setBackground(Color.WHITE);
		logRecField.setFont(new Font("Gotham Book", Font.PLAIN, 13));
		logRecField.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.LIGHT_GRAY, 1, false), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
		logRecField.setColumns(10);
		
		JButton btnRecConfirmar = new JButton("Recuperar");
		btnRecConfirmar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				btnRecConfirmar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		btnRecConfirmar.setFont(new Font("Gotham Book", Font.PLAIN, 16));
		btnRecConfirmar.setFocusPainted(false);
		btnRecConfirmar.setBackground(Color.WHITE);
		btnRecConfirmar.setForeground(Color.WHITE);
		btnRecConfirmar.setBorder(null);
		btnRecConfirmar.setOpaque(false);
		btnRecConfirmar.setBounds(75, 275, 463, 40);
		btnRecConfirmar.setIcon( new ColorIcon(Color.DARK_GRAY, btnCadConfirmar.getWidth(), btnCadConfirmar.getHeight()));
		btnRecConfirmar.setHorizontalTextPosition(JButton.CENTER);
		btnRecConfirmar.setVerticalTextPosition(JButton.CENTER);
		btnRecConfirmar.setMargin(new Insets(0, 0, 0, 0));
		panelRecuperar.add(btnRecConfirmar);
		
		JLabel lblRecEntrar = new JLabel("Volte para a \u00E1rea de login.");
		lblRecEntrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				c1.show(panelCardLayout, "1");
			}
		});
		lblRecEntrar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				lblRecEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		lblRecEntrar.setBounds(211, 330, 190, 14);
		panelRecuperar.add(lblRecEntrar);
		lblRecEntrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecEntrar.setForeground(Color.BLACK);
		lblRecEntrar.setFont(new Font("Gotham Book", Font.BOLD, 13));
		
		lblLogEntrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controle = 0;
				c1.show(panelCardLayout, "1");
				
				panelLogin.remove(panelErro);
				panelErro.remove(lblErro);
				panelCadastro.remove(panelConfirmacao);
				panelConfirmacao.remove(lblConfirmacao);
				
				if(controle == 0) {
	        		btnLogLogar.setBounds(75, 381, 465, 40);
	        		lblOthers.setBounds(75, 461, 463, 14);
	        		btnEntrarFacebook.setBounds(75, 515, 200, 35);
	        		btnEntrarGoogle.setBounds(338, 515, 200, 35);
	        		lblLogCadastrar.setBounds(175, 595, 265, 14);
	        		
				}
			}
		});
		lblLogEntrar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				lblLogEntrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
		});
		
		btnLogLogar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle = 0;
				
				if(controle != 0) {
					panelCadastro.remove(panelErro);
					panelErro.remove(lblErro);
					panelCadastro.remove(panelConfirmacao);
					panelConfirmacao.remove(lblConfirmacao);
				}
				
				try{  
					banco.abrirConexao();
					banco.stmt= banco.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					String query;
					query = "SELECT * FROM usuarios";
					banco.resultset = banco.stmt.executeQuery(query);
					
				    int stop = 0;
				    int privilegioUsuario = 0;
				    String nomePessoa = "null";
		           
		            while(banco.resultset.next() && stop == 0 ) {
		            	if(logLoginField.getText().equals(banco.resultset.getString("email")) && logPasswordField.getText().equals(banco.resultset.getString("senha"))){
		            	 	stop = 1;
		            	 	privilegioUsuario = banco.resultset.getInt("privilegio");
		                }else{
		                	stop = 0;
		                }
		            }
		            
		            if(stop == 1){
		            	controle = 1;
		            	
		            	try {
						    tempFile = File.createTempFile("login", ".txt");
						    tempFile.deleteOnExit();
				    	    BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
				    	    bw.write(logLoginField.getText());
				    	    bw.close();
						    
						;
						} catch (IOException x) {
						    System.err.format("IOException: %s%n", x);
						}
		            	
		            	btnLogLogar.setBounds(75, 451, 465, 40);
		        		lblOthers.setBounds(75, 542, 463, 14);
		        		btnEntrarFacebook.setBounds(75, 607, 200, 35);
		        		btnEntrarGoogle.setBounds(338, 607, 200, 35);
		        		lblLogCadastrar.setBounds(175, 704, 265, 14);

					    panelConfirmacao.setLayout(null);
					    panelConfirmacao.setBackground(new Color(30, 215, 97));
					    panelConfirmacao.setBounds(75, 381, 465, 50);
		        		panelLogin.add(panelConfirmacao);
		        		
		        		lblConfirmacao.setText("Você logou com sucesso!");
		        		lblConfirmacao.setHorizontalAlignment(SwingConstants.LEFT);
		        		lblConfirmacao.setForeground(Color.WHITE);
		        		lblConfirmacao.setBounds(10, 11, 210, 28);
		        		lblConfirmacao.setFont(new Font("Gotham Book", Font.PLAIN, 12));
		        		panelConfirmacao.add(lblConfirmacao);
		        		
		            	if(privilegioUsuario == 1) {
		            		IUGerenciamento logado = new IUGerenciamento();
			            	logado.setVisible(true);
			            	dispose();
		            	}else if(privilegioUsuario == 2) {
		            		IUGerenciamento logado = new IUGerenciamento();
			            	logado.setVisible(true);
			            	dispose();
		            	}else if(privilegioUsuario == 3) {
		            		IUGerenciamento logado = new IUGerenciamento();
			            	logado.setVisible(true);
			            	dispose();
		            	}
		            }else{
		            	controle = 1;
		            	
		            	if((logLoginField.getText().isEmpty()) || (logPasswordField.getText().isEmpty())) {
		            		controle = 1;
			        		btnLogLogar.setBounds(75, 451, 465, 40);
			        		lblOthers.setBounds(75, 542, 463, 14);
			        		btnEntrarFacebook.setBounds(75, 607, 200, 35);
			        		btnEntrarGoogle.setBounds(338, 607, 200, 35);
			        		lblLogCadastrar.setBounds(175, 704, 265, 14);
			        		panelErro.setBackground(new Color(255, 0, 0));
			        		panelErro.setBounds(75, 381, 465, 50);
			        		panelLogin.add(panelErro);
			        		panelErro.setLayout(null);
			        		
			        		lblErro.setText("Existem campos vazios!");
			        		lblErro.setHorizontalAlignment(SwingConstants.LEFT);
			        		lblErro.setBounds(10, 11, 210, 28);
			        		lblErro.setHorizontalAlignment(SwingConstants.LEFT);
			        		lblErro.setFont(new Font("Gotham Book", Font.PLAIN, 12));
			        		lblErro.setForeground(Color.WHITE);
			        		panelErro.add(lblErro);
		            	}else{
		            		controle = 1;
		            		
			        		btnLogLogar.setBounds(75, 451, 465, 40);
			        		lblOthers.setBounds(75, 542, 463, 14);
			        		btnEntrarFacebook.setBounds(75, 607, 200, 35);
			        		btnEntrarGoogle.setBounds(338, 607, 200, 35);
			        		lblLogCadastrar.setBounds(175, 704, 265, 14);
			        		
			            	logLoginField.setText("");
			            	logPasswordField.setText("");
			        		panelErro.setBackground(new Color(255, 0, 0));
			        		panelErro.setBounds(75, 381, 465, 50);
			        		panelLogin.add(panelErro);
			        		panelErro.setLayout(null);
			        		
			        		lblErro.setText("Email ou senha incorretos!");
			        		lblErro.setHorizontalAlignment(SwingConstants.LEFT);
			        		lblErro.setBounds(10, 11, 210, 28);
			        		lblErro.setFont(new Font("Gotham Book", Font.PLAIN, 12));
			        		lblErro.setForeground(Color.WHITE);
			        		panelErro.add(lblErro);
		            	}
		            }
					
		            banco.fecharConexao();
		           
				}catch(Exception ec){}
			}
		});
		
		lblLogCadastrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controle = 0;
				c1.show(panelCardLayout, "2");
				
				panelCadastro.remove(panelErro);
				panelErro.remove(lblErro);
				panelLogin.remove(panelConfirmacao);
				panelConfirmacao.remove(lblConfirmacao);
				
				if(controle == 0) {
					btnCadConfirmar.setBounds(75, 435, 463, 40);
					lblLogEntrar.setBounds(161, 500, 291, 14);
				}
			}
		});
		btnCadConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controle = 0;
				
				if(controle != 0) {
					panelCadastro.remove(panelErro);
					panelErro.remove(lblErro);
					panelCadastro.remove(panelConfirmacao);
					panelConfirmacao.remove(lblConfirmacao);
				}
				try {
					banco.abrirConexao();
					banco.stmt = banco.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					String query = "SELECT * FROM usuarios";
					banco.resultset = banco.stmt.executeQuery(query);
					
					Usuario usuarios = new Usuario();
					usuarios.setCpf(cadCPF.getText());
					usuarios.setNomeCompleto(cadNomeCompleto.getText());
					usuarios.setEnderecoEmail(cadLoginField.getText());
					usuarios.setSenha(cadPasswordField.getText());
					
					String input = cadCPF.getText();
					int count = input.length();
					
					int stop = 0;
					
		            while(banco.resultset.next() && stop == 0 ) {
		            	controle = 1;
						if(cadCPF.getText().equals(banco.resultset.getString("cpf"))) {
							stop = 1;
		                }else if(cadLoginField.getText().equals(banco.resultset.getString("email"))){
		                	stop = 2;
		                }else if((cadNomeCompleto.getText().isEmpty()) || (cadCPF.getText().isEmpty()) || (cadLoginField.getText().isEmpty()) || (cadPasswordField.getText().isEmpty())){
		                	stop = 3;
		                }else if(count < 11) {
		                	stop = 4;
		                }else {
		                	stop = 0;
		                }
		            }
					
		            if(stop == 0){
						controle = 1;
						
					    panelConfirmacao.setLayout(null);
					    panelConfirmacao.setBackground(new Color(30, 215, 97));
					    panelConfirmacao.setBounds(75, 435, 465, 50);
		        		panelCadastro.add(panelConfirmacao);
		        		
		        		lblConfirmacao.setText("Sua conta foi criada com sucesso!");
		        		lblConfirmacao.setHorizontalAlignment(SwingConstants.LEFT);
		        		lblConfirmacao.setForeground(Color.WHITE);
		        		lblConfirmacao.setBounds(10, 11, 210, 28);
		        		lblConfirmacao.setFont(new Font("Gotham Book", Font.PLAIN, 12));
		        		panelConfirmacao.add(lblConfirmacao);
		        		
		        		btnCadConfirmar.setBounds(75, 515, 463, 40);
		        		lblLogEntrar.setBounds(161, 580, 291, 14);
						
						UsuarioDAO dao = new UsuarioDAO();
					    dao.adicionarUsuario(usuarios);
		            }else if(stop == 1){
		            	controle = 1;
		            	
		            	cadNomeCompleto.setText(cadNomeCompleto.getText());
		            	cadLoginField.setText(cadLoginField.getText());
		            	cadPasswordField.setText("");
		            	cadCPF.setText("");
		            	
		        		panelErro.setLayout(null);
		        		panelErro.setBackground(Color.RED);
						panelErro.setBounds(75, 435, 465, 50);
		        		panelCadastro.add(panelErro);
		        		
		        		lblErro.setText("Este CPF j\u00E1 est\u00E1 em uso!");
		        		lblErro.setHorizontalAlignment(SwingConstants.LEFT);
		        		lblErro.setForeground(Color.WHITE);
						lblErro.setBounds(10, 11, 210, 28);
		        		lblErro.setFont(new Font("Gotham Book", Font.PLAIN, 12));
		        		panelErro.add(lblErro);
		        		
		        		btnCadConfirmar.setBounds(75, 515, 463, 40);
		        		lblLogEntrar.setBounds(161, 580, 291, 14);
		            }else if(stop == 2){
		            	controle = 1;
		            	
		            	cadNomeCompleto.setText(cadNomeCompleto.getText());
		            	cadLoginField.setText("");
		            	cadPasswordField.setText("");
		            	cadCPF.setText(cadCPF.getText());

		            	panelErro.setLayout(null);
		            	panelErro.setBackground(Color.RED);
						panelErro.setBounds(75, 435, 465, 50);
		        		panelCadastro.add(panelErro);
		        		
		        		lblErro.setText("Este email j\u00E1 est\u00E1 em uso!");
		        		lblErro.setHorizontalAlignment(SwingConstants.LEFT);
		        		lblErro.setForeground(Color.WHITE);
						lblErro.setBounds(10, 11, 210, 28);
		        		lblErro.setFont(new Font("Gotham Book", Font.PLAIN, 12));
		        		panelErro.add(lblErro);
		        		
		        		btnCadConfirmar.setBounds(75, 515, 463, 40);
		        		lblLogEntrar.setBounds(161, 580, 291, 14);
		            }else if(stop == 3) {
		            	controle = 1;
		            	
	            		cadNomeCompleto.setText(cadNomeCompleto.getText());
	            		cadLoginField.setText(cadLoginField.getText());
	            		cadPasswordField.setText(cadPasswordField.getText());
	            		cadCPF.setText(cadCPF.getText());

	            		panelErro.setLayout(null);
	            		panelErro.setBackground(Color.RED);
						panelErro.setBounds(75, 435, 465, 50);
		        		panelCadastro.add(panelErro);
		        		
		        		lblErro.setText("Existem campos vazios!");
		        		lblErro.setHorizontalAlignment(SwingConstants.LEFT);
		        		lblErro.setForeground(Color.WHITE);
						lblErro.setBounds(10, 11, 210, 28);
		        		lblErro.setFont(new Font("Gotham Book", Font.PLAIN, 12));
		        		panelErro.add(lblErro);
		        		
		        		btnCadConfirmar.setBounds(75, 515, 463, 40);
		        		lblLogEntrar.setBounds(161, 580, 291, 14);
		            }else if(stop == 4){
		            	controle = 1;
		            	
		            	cadNomeCompleto.setText(cadNomeCompleto.getText());
		            	cadLoginField.setText(cadLoginField.getText());
		            	cadPasswordField.setText("");
		            	cadCPF.setText("");

		            	panelErro.setLayout(null);
		            	panelErro.setBackground(Color.RED);
						panelErro.setBounds(75, 435, 465, 50);
		        		panelCadastro.add(panelErro);
		        		
		        		lblErro.setText("Este CPF é inv\u00E1lido!");
		        		lblErro.setHorizontalAlignment(SwingConstants.LEFT);
		        		lblErro.setForeground(Color.WHITE);
						lblErro.setBounds(10, 11, 210, 28);
		        		lblErro.setFont(new Font("Gotham Book", Font.PLAIN, 12));
		        		panelErro.add(lblErro);
		        		
		        		btnCadConfirmar.setBounds(75, 515, 463, 40);
		        		lblLogEntrar.setBounds(161, 580, 291, 14);
		            }
				}catch(Exception ec){}
			}
		});
		// FAZER VARIAVEL DE CONTROLE PRA VERIFICAR O IDIOMA
		// VERIFICAR QUAIS PAÍSES TEM CPF
		lblBRA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(cadCPF.getParent() != panelCadastro){
					panelCadastro.add(cadCPF);
					panelCadastro.repaint();
				}
				
				lblBemVindoLogin.setText("Seja bem vindo ao aplicativo da eight");
				lblSlogamLogin.setText("Digite abaixo as suas informações de login e senha para acessar sua conta");
				logLoginField.setUI(new HintTextFieldUI("Endereço de email", true));
				logPasswordField.setUI(new HintTextFieldUI("Senha", true));
				btnLogLogar.setText("Logar");
				lblOthers.setText("———————————————————————— ou ————————————————————————");
				lblLembrarDeMim.setText("Lembrar de mim");
				lblRecuperarSenha.setText("Esqueceu sua senha?");
				btnEntrarFacebook.setText("Entrar com o Facebook");
				btnEntrarGoogle.setText("Entrar com o Google");
				lblLogCadastrar.setText("Ainda não tem uma conta? Registre-se.");
				lblLogCadastrar.setBounds(175, 595, 265, 14);

				lblBemVindoCadastro.setText("Seja bem vindo a criação de contas");
				lblCadastroSlogam.setText("Digite abaixo as suas informações de login e senha para criar sua conta");
				cadNomeCompleto.setUI(new HintTextFieldUI("Nome completo", true));
				cadLoginField.setUI(new HintTextFieldUI("Endereço de email", true));
				cadPasswordField.setUI(new HintTextFieldUI("Senha", true));
				btnCadConfirmar.setText("Registrar");
				lblLogEntrar.setText("Criou sua conta? Volte para a área de login.");
				lblLogEntrar.setBounds(161, 505, 291, 14);

				lblBemVindoRec.setText("Seja bem vindo ao aplicativo da eight");
				lblRecuperacaoSlogam.setText("Digite abaixo as suas informações de login para recuperar sua senha");
				logRecField.setUI(new HintTextFieldUI("Endereço de email", true));
				btnRecConfirmar.setText("Recuperar");
				lblRecEntrar.setText("Volte para a área de login.");
				lblRecEntrar.setBounds(211, 330, 190, 14);
				
				lblBRA.setForeground(Color.WHITE);
				lblENG.setForeground(Color.LIGHT_GRAY);
				lblITA.setForeground(Color.LIGHT_GRAY);
			}
		});
		
		lblENG.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if(cadCPF.getParent() == panelCadastro){
					panelCadastro.remove(cadCPF);
					panelCadastro.repaint();
				}
				
				lblBemVindoLogin.setText("Welcome to eight");
				lblSlogamLogin.setText("Write behind your datas to sign in");
				logLoginField.setUI(new HintTextFieldUI("Email address", true));
				logPasswordField.setUI(new HintTextFieldUI("Password", true));
				btnLogLogar.setText("Sign in");
				lblOthers.setText("———————————————————————— or ————————————————————————");
				lblLembrarDeMim.setText("Remember me");
				lblRecuperarSenha.setText("Forgot your password?");
				btnEntrarFacebook.setText("Sign in with Facebook");
				btnEntrarGoogle.setText("Sign in with Google");
				lblLogCadastrar.setText("Don't have a account yet? Register here.");
				lblLogCadastrar.setBounds(175, 595, 285, 14);
				
				lblBemVindoCadastro.setText("Welcome to register area");
				lblCadastroSlogam.setText("Write behind your datas to sign up");
				cadNomeCompleto.setUI(new HintTextFieldUI("Full name", true));
				cadLoginField.setUI(new HintTextFieldUI("Email address", true));
				cadPasswordField.setUI(new HintTextFieldUI("Password", true));
				btnCadConfirmar.setText("Register");
				lblLogEntrar.setText("Return to the sign in area");
				
				lblBemVindoRec.setText("Welcome to recover area");
				lblRecuperacaoSlogam.setText("Write your email address behind to recover");
				logRecField.setUI(new HintTextFieldUI("Email address", true));
				btnRecConfirmar.setText("Recover");
				lblRecEntrar.setText("Return to the sign in area");
				lblRecEntrar.setBounds(211, 330, 190, 14);
				
				lblBRA.setForeground(Color.LIGHT_GRAY);
				lblENG.setForeground(Color.WHITE);
				lblITA.setForeground(Color.LIGHT_GRAY);
			}
		});
		
		lblITA.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				lblBRA.setForeground(Color.LIGHT_GRAY);
				lblENG.setForeground(Color.LIGHT_GRAY);
				lblITA.setForeground(Color.WHITE);
			}
		});
		
		JLabel lblBackground = new JLabel("");
		lblBackground.setIcon(new ImageIcon(IULoginCadastro.class.getResource("/View/imagesRoupas/background.png")));
		lblBackground.setBounds(330, 0, 1440, 900);
		content.add(lblBackground);
	}
}