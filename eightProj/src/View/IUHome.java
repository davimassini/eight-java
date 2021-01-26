package View;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.Toolkit;

import javax.swing.JLabel;

import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.beans.PropertyChangeEvent;
import java.awt.Label;

import javax.swing.JTextArea;
import javax.swing.JWindow;

public class IUHome {    
	protected JFrame frmPrincipal;
	static Point compCoords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IUHome window = new IUHome();
					window.frmPrincipal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	 ConexaoMySQL banco = new ConexaoMySQL();
	 CardLayout c1 = new CardLayout();
	 JPanel panelCardLayout = new JPanel();
	 
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

	/**
	 * Create the application.
	 */
	public IUHome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
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
	                g.setColor(new Color(255, 255, 255));              
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
	
	public class PanelSlider42<ParentType extends Container> {

		private static final int           RIGHT             = 0x01;
		private static final int           LEFT              = 0x02;
		private static final int           TOP               = 0x03;
		private static final int           BOTTOM            = 0x04;
		private final JPanel               basePanel         = new JPanel();
		private final ParentType           parent;
		private final Object               lock              = new Object();
		private final ArrayList<Component> jPanels           = new ArrayList<Component>();
		private final boolean              useSlideButton    = true;
		private boolean                    isSlideInProgress = false;

		private final JPanel               glassPane;
		{
		    glassPane = new JPanel();
		    glassPane.setOpaque(false);
		    glassPane.addMouseListener(new MouseAdapter() {
		    });
		    glassPane.addMouseMotionListener(new MouseMotionAdapter() {
		    });
		    glassPane.addKeyListener(new KeyAdapter() {
		    });
		}

		public PanelSlider42(final ParentType parent) {
		    if (parent == null) {
		        throw new RuntimeException("ProgramCheck: Parent can not be null.");
		    }
		    if ((parent instanceof JFrame) || (parent instanceof JDialog) || (parent instanceof JWindow) || (parent instanceof JPanel)) {
		    }
		    else {
		        throw new RuntimeException("ProgramCheck: Parent type not supported. " + parent.getClass().getSimpleName());
		    }
		    this.parent = parent;
		    attach();
		    basePanel.setSize(parent.getSize());
		    basePanel.setLayout(new BorderLayout());
		    if (useSlideButton) {
		        final JPanel statusPanel = new JPanel();
		        basePanel.add(statusPanel, BorderLayout.SOUTH);
		        statusPanel.add(new JButton("Slide Left") {
		            private static final long serialVersionUID = 9204819004142223529L;
		            {
		                setMargin(new Insets(0, 0, 0, 0));
		            }
		            {
		                addActionListener(new ActionListener() {
		                    @Override
		                    public void actionPerformed(final ActionEvent e) {
		                        slideLeft();
		                    }
		                });
		            }
		        });
		        statusPanel.add(new JButton("Slide Right") {
		            {
		                setMargin(new Insets(0, 0, 0, 0));
		            }
		            private static final long serialVersionUID = 9204819004142223529L;
		            {
		                addActionListener(new ActionListener() {
		                    @Override
		                    public void actionPerformed(final ActionEvent e) {
		                        slideRight();
		                    }
		                });
		            }
		        });
		        statusPanel.add(new JButton("Slide Up") {
		            {
		                setMargin(new Insets(0, 0, 0, 0));
		            }
		            private static final long serialVersionUID = 9204819004142223529L;
		            {
		                addActionListener(new ActionListener() {
		                    @Override
		                    public void actionPerformed(final ActionEvent e) {
		                        slideTop();
		                    }
		                });
		            }
		        });
		        statusPanel.add(new JButton("Slide Down") {
		            {
		                setMargin(new Insets(0, 0, 0, 0));
		            }
		            private static final long serialVersionUID = 9204819004142223529L;
		            {
		                addActionListener(new ActionListener() {
		                    @Override
		                    public void actionPerformed(final ActionEvent e) {
		                        slideBottom();
		                    }
		                });
		            }
		        });
		    }
		}

		public JPanel getBasePanel() {
		    return basePanel;
		}

		private void attach() {
		    final ParentType w = this.parent;
		    if (w instanceof JFrame) {
		        final JFrame j = (JFrame) w;
		        if (j.getContentPane().getComponents().length > 0) {
		            throw new RuntimeException("ProgramCheck: Parent already contains content.");
		        }
		        j.getContentPane().add(basePanel);
		    }
		    if (w instanceof JDialog) {
		        final JDialog j = (JDialog) w;
		        if (j.getContentPane().getComponents().length > 0) {
		            throw new RuntimeException("ProgramCheck: Parent already contains content.");
		        }
		        j.getContentPane().add(basePanel);
		    }
		    if (w instanceof JWindow) {
		        final JWindow j = (JWindow) w;
		        if (j.getContentPane().getComponents().length > 0) {
		            throw new RuntimeException("ProgramCheck: Parent already contains content.");
		        }
		        j.getContentPane().add(basePanel);
		    }
		    if (w instanceof JPanel) {
		        final JPanel j = (JPanel) w;
		        if (j.getComponents().length > 0) {
		            throw new RuntimeException("ProgramCheck: Parent already contains content.");
		        }
		        j.add(basePanel);
		    }
		}

		public void addComponent(final Component component) {
		    if (jPanels.contains(component)) {
		    }
		    else {
		        jPanels.add(component);
		        if (jPanels.size() == 1) {
		            basePanel.add(component);
		        }
		        component.setSize(basePanel.getSize());
		        component.setLocation(0, 0);
		    }
		}

		public void removeComponent(final Component component) {
		    if (jPanels.contains(component)) {
		        jPanels.remove(component);
		    }
		}

		public void slideLeft() {
		    slide(LEFT);
		}

		public void slideRight() {
		    slide(RIGHT);
		}

		public void slideTop() {
		    slide(TOP);
		}

		public void slideBottom() {
		    slide(BOTTOM);
		}

		private void enableUserInput(final ParentType w) {
		    if (w instanceof JFrame) {
		        ((JFrame) w).getGlassPane().setVisible(false);
		    }
		    if (w instanceof JDialog) {
		        ((JDialog) w).getGlassPane().setVisible(false);
		    }
		    if (w instanceof JWindow) {
		        ((JWindow) w).getGlassPane().setVisible(false);
		    }
		}

		private void disableUserInput(final ParentType w) {
		    if (w instanceof JFrame) {
		        ((JFrame) w).setGlassPane(glassPane);
		    }
		    if (w instanceof JDialog) {
		        ((JDialog) w).setGlassPane(glassPane);
		    }
		    if (w instanceof JWindow) {
		        ((JWindow) w).setGlassPane(glassPane);
		    }
		    glassPane.setVisible(true);
		}

		private void enableTransparentOverylay() {
		    if (parent instanceof JFrame) {
		        ((JFrame) parent).getContentPane().setBackground(jPanels.get(0).getBackground());
		        parent.remove(basePanel);
		        parent.validate();
		    }
		    if (parent instanceof JDialog) {
		        ((JDialog) parent).getContentPane().setBackground(jPanels.get(0).getBackground());
		        parent.remove(basePanel);
		        parent.validate();
		    }
		    if (parent instanceof JWindow) {
		        ((JWindow) parent).getContentPane().setBackground(jPanels.get(0).getBackground());
		        parent.remove(basePanel);
		        parent.validate();
		    }
		}

		private void slide(final int slideType) {
		    if (!isSlideInProgress) {
		        isSlideInProgress = true;
		        final Thread t0 = new Thread(new Runnable() {
		            @Override
		            public void run() {
		                parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		                disableUserInput(parent);
		                slide(true, slideType);
		                enableUserInput(parent);
		                parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		                isSlideInProgress = false;
		            }
		        });
		        t0.setDaemon(true);
		        t0.start();
		    }
		    else {
		        Toolkit.getDefaultToolkit().beep();
		    }
		}

		private void slide(final boolean useLoop, final int slideType) {
		    if (jPanels.size() < 2) {
		        System.err.println("Not enough panels");
		        return;
		    }
		    synchronized (lock) {
		        Component componentOld = null;
		        Component componentNew = null;
		        if ((slideType == LEFT) || (slideType == TOP)) {
		            componentNew = jPanels.remove(jPanels.size() - 1);
		            componentOld = jPanels.get(0);
		            jPanels.add(0, componentNew);
		        }
		        if ((slideType == RIGHT) || (slideType == BOTTOM)) {
		            componentOld = jPanels.remove(0);
		            jPanels.add(componentOld);
		            componentNew = jPanels.get(0);
		        }
		        final int w = componentOld.getWidth();
		        final int h = componentOld.getHeight();
		        final Point p1 = componentOld.getLocation();
		        final Point p2 = new Point(0, 0);
		        if (slideType == LEFT) {
		            p2.x += w;
		        }
		        if (slideType == RIGHT) {
		            p2.x -= w;
		        }
		        if (slideType == TOP) {
		            p2.y += h;
		        }
		        if (slideType == BOTTOM) {
		            p2.y -= h;
		        }
		        componentNew.setLocation(p2);
		        int step = 0;
		        if ((slideType == LEFT) || (slideType == RIGHT)) {
		            step = (int) (((float) parent.getWidth() / (float) Toolkit.getDefaultToolkit().getScreenSize().width) * 40.f);
		        }
		        else {
		            step = (int) (((float) parent.getHeight() / (float) Toolkit.getDefaultToolkit().getScreenSize().height) * 20.f);
		        }
		        step = step < 5 ? 5 : step;
		        basePanel.add(componentNew);
		        basePanel.revalidate();
		        if (useLoop) {
		            final int max = (slideType == LEFT) || (slideType == RIGHT) ? w : h;
		            final long t0 = System.currentTimeMillis();
		            for (int i = 0; i != (max / step); i++) {
		                switch (slideType) {
		                    case LEFT: {
		                        p1.x -= step;
		                        componentOld.setLocation(p1);
		                        p2.x -= step;
		                        componentNew.setLocation(p2);
		                        break;
		                    }
		                    case RIGHT: {
		                        p1.x += step;
		                        componentOld.setLocation(p1);
		                        p2.x += step;
		                        componentNew.setLocation(p2);
		                        break;
		                    }
		                    case TOP: {
		                        p1.y -= step;
		                        componentOld.setLocation(p1);
		                        p2.y -= step;
		                        componentNew.setLocation(p2);
		                        break;
		                    }
		                    case BOTTOM: {
		                        p1.y += step;
		                        componentOld.setLocation(p1);
		                        p2.y += step;
		                        componentNew.setLocation(p2);
		                        break;
		                    }
		                    default:
		                        new RuntimeException("ProgramCheck").printStackTrace();
		                        break;
		                }

		                try {
		                    Thread.sleep(500 / (max / step));
		                } catch (final Exception e) {
		                    e.printStackTrace();
		                }
		            }
		            final long t1 = System.currentTimeMillis();
		        }
		        componentOld.setLocation(-10000, -10000);
		        componentNew.setLocation(0, 0);
		    }
		}
		}
	
	private class RotateLabel extends JLabel {
		public RotateLabel(String text) {
			super(text);
		}
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D gx = (Graphics2D) g;
			gx.rotate(0.2, getX() + getWidth() / 2, getY() + getHeight() / 2); //Rotate 0.2 radians around the center of the label
			super.paintComponent(g);
		}
	}
	
	public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void initialize() {
			frmPrincipal = new JFrame();
			frmPrincipal.getContentPane().setFont(new Font("Gotham Book", Font.PLAIN, 11));
			frmPrincipal.getContentPane().setBackground(Color.WHITE);
			frmPrincipal.setUndecorated(true);
			frmPrincipal.setResizable(false);
			frmPrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(IUHome.class.getResource("/View/images/iconBarra.png")));
			frmPrincipal.setFont(new Font("Dialog", Font.PLAIN, 12));
			frmPrincipal.setTitle("eight Store");
			frmPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmPrincipal.setBounds(100, 100, 1440, 900);
			frmPrincipal.setFont(new Font("Open Sans", Font.PLAIN, 12));
			frmPrincipal.setResizable(false);
			
			
			
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
	                frmPrincipal.setLocation(currCoords.x - compCoords.x, currCoords.y - compCoords.y);
	            }
			});
			frmPrincipal.getContentPane().setLayout(null);
			titlePanel.setForeground(Color.WHITE);
			titlePanel.setBackground(Color.DARK_GRAY);
			titlePanel.setBounds(0, 0, 1440, 30);
			frmPrincipal.getContentPane().add(titlePanel);
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
					frmPrincipal.dispose();
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
					frmPrincipal.setState(JFrame.ICONIFIED);
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
            
			JPanel panelMenuAberto = new JPanel();
			if(c1.getHgap() != 0){
				panelMenuAberto.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseExited(MouseEvent arg0) {
			            new Timer(1, new ActionListener() {
			            	public void actionPerformed(ActionEvent e) {
			            		panelMenuAberto.setLocation(panelMenuAberto.getX() - 5, 30);
			            		if (panelMenuAberto.getX() + panelMenuAberto.getWidth() == 0) {
			            			((Timer) e.getSource()).stop();
			            		}
			            	}
			            }).start();
						}
					@Override
					public void mouseEntered(MouseEvent arg0) {
					}
					});	
			}
			panelMenuAberto.setBackground(Color.WHITE);
			panelMenuAberto.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(255,255,255,80)));
			panelMenuAberto.setBounds(0, 30, 300, 870);
			frmPrincipal.getContentPane().add(panelMenuAberto);
			panelMenuAberto.setLayout(null);
			
			JLabel lblLogo = new JLabel("");
			lblLogo.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					lblLogo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			lblLogo.setIcon(new ImageIcon(IUHome.class.getResource("/View/images/logo-footer-black.png")));
			lblLogo.setBounds(75, 75, 115, 50);
			panelMenuAberto.add(lblLogo);
			
			JLabel lblDestaques = new JLabel("DESTAQUES");
			lblDestaques.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					lblDestaques.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			lblDestaques.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblDestaques.setBounds(75, 180, 91, 14);
			panelMenuAberto.add(lblDestaques);
			
			JLabel lblCasacos = new JLabel("CASACOS");
			lblCasacos.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblCasacos.setBounds(75, 230, 91, 14);
			panelMenuAberto.add(lblCasacos);
			
			JLabel lblCamisetas = new JLabel("CAMISETAS");
			lblCamisetas.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblCamisetas.setBounds(75, 280, 91, 14);
			panelMenuAberto.add(lblCamisetas);
			
			JLabel lblMoletom = new JLabel("MOLETOM");
			lblMoletom.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblMoletom.setBounds(75, 330, 91, 14);
			panelMenuAberto.add(lblMoletom);
			
			JLabel lblCalcas = new JLabel("CAL\u00C7AS");
			lblCalcas.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblCalcas.setBounds(75, 380, 91, 14);
			panelMenuAberto.add(lblCalcas);
			
			JLabel lblSapatos = new JLabel("SAPATOS");
			lblSapatos.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblSapatos.setBounds(75, 430, 91, 14);
			panelMenuAberto.add(lblSapatos);
			
			JLabel lblAcessorios = new JLabel("ACESS\u00D3RIOS");
			lblAcessorios.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblAcessorios.setBounds(75, 480, 96, 14);
			panelMenuAberto.add(lblAcessorios);
			
			JLabel lblSeparador = new JLabel("\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014");
			lblSeparador.setHorizontalAlignment(SwingConstants.CENTER);
			lblSeparador.setForeground(Color.LIGHT_GRAY);
			lblSeparador.setFont(new Font("Gotham Book", Font.BOLD, 10));
			lblSeparador.setBounds(64, 710, 171, 11);
			panelMenuAberto.add(lblSeparador);
			
			JLabel lblBRA = new JLabel("BRA");
			lblBRA.setForeground(Color.BLACK);
			lblBRA.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					lblBRA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			lblBRA.setHorizontalAlignment(SwingConstants.LEFT);
			lblBRA.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblBRA.setBounds(64, 790, 32, 14);
			panelMenuAberto.add(lblBRA);
			
			JLabel lblENG = new JLabel("ENG");
			lblENG.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					lblENG.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			lblENG.setForeground(Color.LIGHT_GRAY);
			lblENG.setHorizontalAlignment(SwingConstants.CENTER);
			lblENG.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblENG.setBounds(134, 790, 32, 14);
			panelMenuAberto.add(lblENG);
			
			JLabel lblITA = new JLabel("ITA");
			lblITA.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					lblITA.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			lblITA.setForeground(Color.LIGHT_GRAY);
			lblITA.setHorizontalAlignment(SwingConstants.RIGHT);
			lblITA.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			lblITA.setBounds(203, 790, 32, 14);
			panelMenuAberto.add(lblITA);
			
			JLabel iconeTwitter = new JLabel("");
			iconeTwitter.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					openWebpage("https://www.twitter.com/eightoficial/");
				}
			});
			iconeTwitter.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					iconeTwitter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			iconeTwitter.setIcon(new ImageIcon(IUHome.class.getResource("/View/images/iconeTwitter32.png")));
			iconeTwitter.setBounds(187, 670, 32, 32);
			panelMenuAberto.add(iconeTwitter);
			
			JLabel iconeInstagram = new JLabel("");
			iconeInstagram.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					openWebpage("https://www.instagram.com/eight.social/");
				}
			});
			iconeInstagram.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					iconeInstagram.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			iconeInstagram.setIcon(new ImageIcon(IUHome.class.getResource("/View/images/iconeInstagram32.png")));
			iconeInstagram.setBounds(134, 670, 32, 32);
			panelMenuAberto.add(iconeInstagram);
			
			JLabel iconeFacebook = new JLabel("");
			iconeFacebook.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					openWebpage("https://www.facebook.com/eight.social/");
				}
			});
			iconeFacebook.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					iconeFacebook.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			iconeFacebook.setIcon(new ImageIcon(IUHome.class.getResource("/View/images/iconeFacebook32.png")));
			iconeFacebook.setBounds(81, 670, 32, 32);
			panelMenuAberto.add(iconeFacebook);
			
			lblBRA.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					lblDestaques.setText("DESTAQUES");
					lblCasacos.setText("CASACOS");
					lblCamisetas.setText("CAMISETAS");
					lblMoletom.setText("MOLETOM");
					lblCalcas.setText("CAL\u00C7AS");
					lblSapatos.setText("SAPATOS");
					lblAcessorios.setText("ACESS\u00D3RIOS");
					
					lblBRA.setForeground(Color.BLACK);
					lblENG.setForeground(Color.LIGHT_GRAY);
					lblITA.setForeground(Color.LIGHT_GRAY);
				}
			});
			
			lblENG.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					lblDestaques.setText("FEATUREDS");
					lblCasacos.setText("COATS");
					lblCamisetas.setText("T-SHIRTS");
					lblMoletom.setText("SWEATSHIRT");
					lblMoletom.setBounds(75, 330, 100, 14);
					lblCalcas.setText("PANTS");
					lblSapatos.setText("SHOES");
					lblAcessorios.setText("ACESSORIES");
					
					lblBRA.setForeground(Color.LIGHT_GRAY);
					lblENG.setForeground(Color.BLACK);
					lblITA.setForeground(Color.LIGHT_GRAY);
				}
			});
			
			lblITA.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					lblDestaques.setText("IN PRIMO PIANO");
					lblDestaques.setBounds(75, 180, 120, 14);
					lblCasacos.setText("GIACCHE");
					lblCamisetas.setText("CAMICIE");
					lblMoletom.setText("TUTA");
					lblCalcas.setText("PANTALONI");
					lblSapatos.setText("SCARPE");
					lblAcessorios.setText("ACESSORI");
					
					lblBRA.setForeground(Color.LIGHT_GRAY);
					lblENG.setForeground(Color.LIGHT_GRAY);
					lblITA.setForeground(Color.BLACK);
				}
			});
			
			Panel panelMenuFechado = new Panel();
			/*panelMenuFechado.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
		            new Timer(1, new ActionListener() {
		            	public void actionPerformed(ActionEvent e) {
		            		panelMenuAberto.setLocation(panelMenuAberto.getX() + 5, 30);
		            		if (panelMenuAberto.getX() + panelMenuAberto.getWidth() == 300) {
		            			((Timer) e.getSource()).stop();
		            		}
		            	}
		            }).start();
				}
			}); */
			panelMenuFechado.setBounds(-125, 30, 125, 870);
			frmPrincipal.getContentPane().add(panelMenuFechado);
			panelMenuFechado.setLayout(null);
			
			JLabel iconeLogoAbrir = new JLabel("");
			iconeLogoAbrir.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					iconeLogoAbrir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			iconeLogoAbrir.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
	            new Timer(1, new ActionListener() {
	            	public void actionPerformed(ActionEvent e) {
	            		panelMenuAberto.setLocation(panelMenuAberto.getX() + 5, 30);
	            		if (panelMenuAberto.getX() + panelMenuAberto.getWidth() == 300) {
	            			((Timer) e.getSource()).stop();
	            		}
	            	}
	            }).start();
	            c1.show(panelCardLayout, "0");
				}
			});
			iconeLogoAbrir.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
	            new Timer(1, new ActionListener() {
	            	public void actionPerformed(ActionEvent e) {
	            		panelMenuFechado.setLocation(panelMenuFechado.getX() - 5, 30);
	            		if (panelMenuFechado.getX() + panelMenuFechado.getWidth() == 0) {
	            			((Timer) e.getSource()).stop();
	            		}
	            	}
	            }).start();
				}
			});
			iconeLogoAbrir.setIcon(new ImageIcon(IUHome.class.getResource("/View/images/logoFechado.png")));
			iconeLogoAbrir.setBounds(21, 35, 80, 80);
			panelMenuFechado.add(iconeLogoAbrir);
			
			JLabel iconeAbrir = new JLabel("\u2261");
			iconeAbrir.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					iconeAbrir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			iconeAbrir.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
		            new Timer(1, new ActionListener() {
		            	public void actionPerformed(ActionEvent e) {
		            		panelMenuFechado.setLocation(panelMenuFechado.getX() - 5, 30);
		            		if (panelMenuFechado.getX() + panelMenuFechado.getWidth() == 0) {
		            			((Timer) e.getSource()).stop();
		            		}
		            	}
		            }).start();
					}
			});
			iconeAbrir.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					new Timer(1, new ActionListener() {
		            	public void actionPerformed(ActionEvent e) {
		            		panelMenuAberto.setLocation(panelMenuAberto.getX() + 5, 30);
		            		if (panelMenuAberto.getX() + panelMenuAberto.getWidth() == 300) {
		            			((Timer) e.getSource()).stop();
		            		}
		            	}
		            }).start();
					}
				});
			iconeAbrir.setHorizontalAlignment(SwingConstants.CENTER);
			iconeAbrir.setFont(new Font("Tahoma", Font.PLAIN, 90));
			iconeAbrir.setBounds(22, 375, 80, 80);
			
			panelMenuFechado.add(iconeAbrir);
			
			Panel panelCarrinhoAberto = new Panel();
			panelCarrinhoAberto.setLayout(null);
			panelCarrinhoAberto.setBounds(1440, 30, 500, 870);
			frmPrincipal.getContentPane().add(panelCarrinhoAberto);
			
			JLabel iconeRecolherCarrinho = new JLabel("");
			iconeRecolherCarrinho.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					new Timer(1, new ActionListener() {
		            	public void actionPerformed(ActionEvent e) {
		            		panelCarrinhoAberto.setLocation(panelCarrinhoAberto.getX() + 5, 30);
		            		if (panelCarrinhoAberto.getX() + panelCarrinhoAberto.getWidth() == 1940) {
		            			((Timer) e.getSource()).stop();
		            		}
		            	}
		            }).start();
				}
			});
			iconeAbrir.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					iconeRecolherCarrinho.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			iconeRecolherCarrinho.setIcon(new ImageIcon(IUHome.class.getResource("/View/images/iconeCarrinhoVoltar.png")));
			iconeRecolherCarrinho.setBounds(25, 15, 32, 32);
			panelCarrinhoAberto.add(iconeRecolherCarrinho);
			
			JLabel lblOCarrinhoEst = new JLabel("O carrinho est\u00E1 vazio!");
			lblOCarrinhoEst.setFont(new Font("Gotham Book", Font.BOLD, 18));
			lblOCarrinhoEst.setBounds(150, 411, 199, 48);
			panelCarrinhoAberto.add(lblOCarrinhoEst);
			
			JLabel lblTotalr = new JLabel("Total: 00R$");
			lblTotalr.setFont(new Font("Gotham Book", Font.BOLD, 18));
			lblTotalr.setBounds(35, 795, 112, 48);
			panelCarrinhoAberto.add(lblTotalr);
			
			JLabel lblFinalizarCompra = new JLabel("Finalizar compra");
			lblFinalizarCompra.setEnabled(false);
			lblFinalizarCompra.setFont(new Font("Gotham Book", Font.BOLD, 15));
			lblFinalizarCompra.setBounds(334, 795, 131, 48);
			panelCarrinhoAberto.add(lblFinalizarCompra);
			
			Panel panelCarrinhoConta = new Panel();
			panelCarrinhoConta.setLayout(null);
			panelCarrinhoConta.setBounds(1315, 30, 125, 870);
			frmPrincipal.getContentPane().add(panelCarrinhoConta);
			
			JLabel iconePerfil = new JLabel("");
			iconePerfil.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					iconePerfil.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			iconePerfil.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					IULoginCadastro logar = new IULoginCadastro();
					logar.setVisible(true);
				}
			});
			iconePerfil.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/iconePerfil64.png")));
			iconePerfil.setHorizontalAlignment(SwingConstants.CENTER);
			iconePerfil.setFont(new Font("Tahoma", Font.PLAIN, 90));
			iconePerfil.setBounds(30, 45, 64, 64);
			panelCarrinhoConta.add(iconePerfil);
			
			JLabel iconeCarrinho = new JLabel("");
			iconeCarrinho.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent arg0) {
					iconeCarrinho.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			iconeCarrinho.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
					new Timer(1, new ActionListener() {
		            	public void actionPerformed(ActionEvent e) {
		            		panelCarrinhoAberto.setLocation(panelCarrinhoAberto.getX() - 5, 30);
		            		if (panelCarrinhoAberto.getX() + panelCarrinhoAberto.getWidth() == 1440) {
		            			((Timer) e.getSource()).stop();
		            		}
		            	}
		            }).start();
					}
				});
			iconeCarrinho.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/iconeCarrinho64.png")));
			iconeCarrinho.setHorizontalAlignment(SwingConstants.CENTER);
			iconeCarrinho.setFont(new Font("Tahoma", Font.PLAIN, 90));
			iconeCarrinho.setBounds(30, 403, 64, 64);
			panelCarrinhoConta.add(iconeCarrinho);
			
			JLabel eightVersao = new JLabel("eight Store v1.0");
			eightVersao.setEnabled(false);
			eightVersao.setHorizontalAlignment(SwingConstants.CENTER);
			eightVersao.setFont(new Font("Gotham Book", Font.PLAIN, 13));
			eightVersao.setBounds(10, 840, 105, 14);
			panelCarrinhoConta.add(eightVersao);
			
			JLabel eightCopyright = new JLabel("eight \u00A9 2018 - 2019");
			eightCopyright.setEnabled(false);
			eightCopyright.setFont(new Font("Gotham Book", Font.PLAIN, 10));
			eightCopyright.setBounds(10, 855, 104, 11);
			panelCarrinhoConta.add(eightCopyright);
			
			panelCardLayout.setBounds(125, 30, 1190, 870);
			frmPrincipal.getContentPane().add(panelCardLayout);
			panelCardLayout.setLayout(c1);
			
			c1.show(panelCardLayout, "0");
			
			JLabel lblNewLabel = new JLabel("");
			panelCardLayout.add(lblNewLabel, "0");
			lblNewLabel.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/slider-img-1-1.jpg")));
			
			Panel panelDestaques = new Panel();
			panelDestaques.setBackground(new Color(40,40,40));
			panelCardLayout.add(panelDestaques, "1");
			panelDestaques.setLayout(null);
			
			JPanel produtoDestaque = new JPanel();
			produtoDestaque.setName("3");
			produtoDestaque.setBounds(0, 0, 596, 870);
			produtoDestaque.setBackground(new Color(182, 228, 251));
			panelDestaques.add(produtoDestaque);
			produtoDestaque.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					produtoDestaque.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			produtoDestaque.setLayout(null);
			
			JPanel primeiroProduto = new JPanel();
			primeiroProduto.setName("cod2");
			primeiroProduto.setBackground(new Color(215, 196, 235));
			primeiroProduto.setBounds(596, 0, 297, 345);
			panelDestaques.add(primeiroProduto);
			primeiroProduto.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					primeiroProduto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			primeiroProduto.setLayout(null);
			
			JPanel segundoProduto = new JPanel();
			segundoProduto.setName("cod3");
			segundoProduto.setLayout(null);
			segundoProduto.setBackground(new Color(255, 238, 177));
			segundoProduto.setBounds(893, 0, 297, 345);
			panelDestaques.add(segundoProduto);
			segundoProduto.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					segundoProduto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			segundoProduto.setLayout(null);
			
			JPanel terceiroProduto = new JPanel();
			terceiroProduto.setName("cod4");
			terceiroProduto.setLayout(null);
			terceiroProduto.setBackground(new Color(243, 243, 245));
			terceiroProduto.setBounds(596, 345, 297, 345);
			panelDestaques.add(terceiroProduto);
			terceiroProduto.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					terceiroProduto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			terceiroProduto.setLayout(null);
			
			JPanel quartoProduto = new JPanel();
			quartoProduto.setName("cod5");
			quartoProduto.setLayout(null);
			quartoProduto.setBackground(new Color(255, 206, 211));
			quartoProduto.setBounds(893, 345, 297, 345);
			panelDestaques.add(quartoProduto);
			quartoProduto.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					quartoProduto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			quartoProduto.setLayout(null);
			
			JPanel promocaoProduto = new JPanel();
			promocaoProduto.setLayout(null);
			promocaoProduto.setBackground(new Color(0, 131, 176));
			promocaoProduto.setBounds(596, 690, 594, 180);
			panelDestaques.add(promocaoProduto);
			promocaoProduto.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					promocaoProduto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			});
			promocaoProduto.setLayout(null);
			
			JLabel fotoProduto5 = new JLabel("");
			fotoProduto5.setBounds(147, -60, 300, 300);
			promocaoProduto.add(fotoProduto5);
			fotoProduto5.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/splitscreen-product-img-6-300x300.png")));
			
			try{  
				banco.abrirConexao();
				banco.stmt= banco.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
				String query;
				query = "SELECT * FROM produtos";
				banco.resultset = banco.stmt.executeQuery(query);
				
				int stop = 0;
				
	            while(banco.resultset.next() && stop == 0 ) {
	            	if(produtoDestaque.getName().equals(banco.resultset.getString("classificacaoProduto"))) {
	            		stop = 1;
	            		

	        			
	        			JLabel fotoProdutoDestaque = new JLabel("");
	        			fotoProdutoDestaque.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/splitscreen-product-img-9-550x550.png")));
	        			fotoProdutoDestaque.setBounds(46, 200, 550, 550);
	        			produtoDestaque.add(fotoProdutoDestaque);
	        			
	        			JLabel precoProdutoDestaque = new JLabel();
	            	 	precoProdutoDestaque.setForeground(Color.BLACK);
	            	 	precoProdutoDestaque.setHorizontalAlignment(SwingConstants.CENTER);
	            	 	precoProdutoDestaque.setText(banco.resultset.getString("precoProduto") + "R$");
	        			precoProdutoDestaque.setFont(new Font("Gotham Medium", Font.PLAIN, 32));
	        			precoProdutoDestaque.setBounds(75, 95, 100, 35);
	        			produtoDestaque.add(precoProdutoDestaque);
	        			
	        			JLabel nomeProdutoDestaque = new JLabel();
	        			nomeProdutoDestaque.setText(banco.resultset.getString("nomeProduto"));
	        			nomeProdutoDestaque.setHorizontalAlignment(SwingConstants.RIGHT);
	        			nomeProdutoDestaque.setFont(new Font("Gotham Book", Font.PLAIN, 15));
	        			nomeProdutoDestaque.setBounds(451, 825, 103, 16);
	        			produtoDestaque.add(nomeProdutoDestaque);
	        			
	        			JLabel categoriaProdutoDestaque = new JLabel();
	        			categoriaProdutoDestaque.setText(banco.resultset.getString("nomeCategoria"));
	        			categoriaProdutoDestaque.setHorizontalAlignment(SwingConstants.RIGHT);
	        			categoriaProdutoDestaque.setForeground(new Color(0,0,0,150));
	        			categoriaProdutoDestaque.setFont(new Font("Gotham Book", Font.PLAIN, 13));
	        			categoriaProdutoDestaque.setBounds(424, 810, 130, 16);
	        			produtoDestaque.add(categoriaProdutoDestaque);
	        			
	                }if(primeiroProduto.getName().equals(banco.resultset.getString("codProduto"))){
	            	 	stop = 1;
	        			
	            		JLabel precoPrimeiroProduto = new JLabel();
	        			precoPrimeiroProduto.setText(banco.resultset.getString("precoProduto") + "R$");
	        			precoPrimeiroProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        			precoPrimeiroProduto.setForeground(Color.WHITE);
	        			precoPrimeiroProduto.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
	        			precoPrimeiroProduto.setBounds(10, -20, 90, 20);
	        			primeiroProduto.add(precoPrimeiroProduto);
	        			
	        			JLabel nomePrimeiroProduto = new JLabel();
	        			nomePrimeiroProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        			nomePrimeiroProduto.setBounds(48, 364, 200, 16);
	        			primeiroProduto.add(nomePrimeiroProduto);
	        			nomePrimeiroProduto.setText(banco.resultset.getString("nomeProduto"));
	        			nomePrimeiroProduto.setForeground(Color.WHITE);
	        			nomePrimeiroProduto.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
	        			
	        			JLabel categoriaPrimeiroProduto = new JLabel();
	        			categoriaPrimeiroProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        			categoriaPrimeiroProduto.setBounds(48, 379, 200, 16);
	        			primeiroProduto.add(categoriaPrimeiroProduto);
	        			categoriaPrimeiroProduto.setText(banco.resultset.getString("nomeCategoria"));
	        			categoriaPrimeiroProduto.setFont(new Font("Gotham Book", Font.PLAIN, 12));
	        			categoriaPrimeiroProduto.setForeground(Color.WHITE);
	        			
	        			JLabel fotoPrimeiroProduto = new JLabel("");
	        			fotoPrimeiroProduto.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/splitscreen-product-img-8-240x300.png")));
	        			fotoPrimeiroProduto.setBounds(28, 60, 240, 224);
	        			primeiroProduto.add(fotoPrimeiroProduto);
	        			
	        			/**
	        			 * ENTERED
	        			 */
	        			
	        			//PREÇO
	        			primeiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		precoPrimeiroProduto.setLocation(precoPrimeiroProduto.getX(), precoPrimeiroProduto.getY() + 5);
	        		            		if (precoPrimeiroProduto.getY() + precoPrimeiroProduto.getHeight() == 40) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			//NOME
	        			primeiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		nomePrimeiroProduto.setLocation(nomePrimeiroProduto.getX(), nomePrimeiroProduto.getY() - 5);
	        		            		if (nomePrimeiroProduto.getY() + nomePrimeiroProduto.getHeight() == 300) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});

	        			//CATEGORIA
	        			primeiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		categoriaPrimeiroProduto.setLocation(categoriaPrimeiroProduto.getX(), categoriaPrimeiroProduto.getY() - 5);
	        		            		if (categoriaPrimeiroProduto.getY() + categoriaPrimeiroProduto.getHeight() == 315) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			/**
	        			 * EXITED
	        			 */
	        			
	        			//PREÇO
	        			primeiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		precoPrimeiroProduto.setLocation(precoPrimeiroProduto.getX(), precoPrimeiroProduto.getY() - 5);
	        		            		if (precoPrimeiroProduto.getY() + precoPrimeiroProduto.getHeight() == 0) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			//NOME
	        			primeiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		nomePrimeiroProduto.setLocation(nomePrimeiroProduto.getX(), nomePrimeiroProduto.getY() + 5);
	        		            		if (nomePrimeiroProduto.getY() + nomePrimeiroProduto.getHeight() == 380) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			
	        			//CATEGORIA
	        			primeiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		categoriaPrimeiroProduto.setLocation(categoriaPrimeiroProduto.getX(), categoriaPrimeiroProduto.getY() + 5);
	        		            		if (categoriaPrimeiroProduto.getY() + categoriaPrimeiroProduto.getHeight() == 395) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	            	}if(segundoProduto.getName().equals(banco.resultset.getString("codProduto"))){
	            	 	stop = 1;
	        			
	            		JLabel precoSegundoProduto = new JLabel();
	            		precoSegundoProduto.setText(banco.resultset.getString("precoProduto") + "R$");
	            		precoSegundoProduto.setHorizontalAlignment(SwingConstants.CENTER);
	            		precoSegundoProduto.setForeground(Color.BLACK);
	            		precoSegundoProduto.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
	            		precoSegundoProduto.setBounds(10, -20, 90, 20);
	        			segundoProduto.add(precoSegundoProduto);
	        			
	        			JLabel nomeSegundoProduto = new JLabel();
	        			nomeSegundoProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        			nomeSegundoProduto.setBounds(48, 364, 200, 16);
	        			segundoProduto.add(nomeSegundoProduto);
	        			nomeSegundoProduto.setText(banco.resultset.getString("nomeProduto"));
	        			nomeSegundoProduto.setForeground(Color.BLACK);
	        			nomeSegundoProduto.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
	        			
	        			JLabel categoriaSegundoProduto = new JLabel();
	        			categoriaSegundoProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        			categoriaSegundoProduto.setBounds(48, 379, 200, 16);
	        			segundoProduto.add(categoriaSegundoProduto);
	        			categoriaSegundoProduto.setText(banco.resultset.getString("nomeCategoria"));
	        			categoriaSegundoProduto.setFont(new Font("Gotham Book", Font.PLAIN, 12));
	        			categoriaSegundoProduto.setForeground(Color.BLACK);
	        			
	        			JLabel fotoSegundoProduto = new JLabel("");
	        			fotoSegundoProduto.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/splitscreen-product-img-10-240x300.png")));
	        			fotoSegundoProduto.setBounds(28, 60, 240, 224);
	        			segundoProduto.add(fotoSegundoProduto);
	        			
	        			/**
	        			 * ENTERED
	        			 */
	        			
	        			//PREÇO
	        			segundoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		precoSegundoProduto.setLocation(precoSegundoProduto.getX(), precoSegundoProduto.getY() + 5);
	        		            		if (precoSegundoProduto.getY() + precoSegundoProduto.getHeight() == 40) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			//NOME
	        			segundoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		nomeSegundoProduto.setLocation(nomeSegundoProduto.getX(), nomeSegundoProduto.getY() - 5);
	        		            		if (nomeSegundoProduto.getY() + nomeSegundoProduto.getHeight() == 300) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});

	        			//CATEGORIA
	        			segundoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		categoriaSegundoProduto.setLocation(categoriaSegundoProduto.getX(), categoriaSegundoProduto.getY() - 5);
	        		            		if (categoriaSegundoProduto.getY() + categoriaSegundoProduto.getHeight() == 315) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			/**
	        			 * EXITED
	        			 */
	        			
	        			//PREÇO
	        			segundoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		precoSegundoProduto.setLocation(precoSegundoProduto.getX(), precoSegundoProduto.getY() - 5);
	        		            		if (precoSegundoProduto.getY() + precoSegundoProduto.getHeight() == 0) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			//NOME
	        			segundoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		nomeSegundoProduto.setLocation(nomeSegundoProduto.getX(), nomeSegundoProduto.getY() + 5);
	        		            		if (nomeSegundoProduto.getY() + nomeSegundoProduto.getHeight() == 380) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			
	        			//CATEGORIA
	        			segundoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		categoriaSegundoProduto.setLocation(categoriaSegundoProduto.getX(), categoriaSegundoProduto.getY() + 5);
	        		            		if (categoriaSegundoProduto.getY() + categoriaSegundoProduto.getHeight() == 395) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	            	}if(terceiroProduto.getName().equals(banco.resultset.getString("codProduto"))){
	            	 	stop = 1;
	        			
	            		JLabel precoTerceiroProduto = new JLabel();
	            		precoTerceiroProduto.setText(banco.resultset.getString("precoProduto") + "R$");
	            		precoTerceiroProduto.setHorizontalAlignment(SwingConstants.CENTER);
	            		precoTerceiroProduto.setForeground(Color.BLACK);
	            		precoTerceiroProduto.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
	            		precoTerceiroProduto.setBounds(10, -20, 90, 20);
	        			terceiroProduto.add(precoTerceiroProduto);
	        			
	        			JLabel nomeTerceiroProduto = new JLabel();
	        			nomeTerceiroProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        			nomeTerceiroProduto.setBounds(48, 364, 200, 16);
	        			terceiroProduto.add(nomeTerceiroProduto);
	        			nomeTerceiroProduto.setText(banco.resultset.getString("nomeProduto"));
	        			nomeTerceiroProduto.setForeground(Color.BLACK);
	        			nomeTerceiroProduto.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
	        			
	        			JLabel categoriaTerceiroProduto = new JLabel();
	        			categoriaTerceiroProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        			categoriaTerceiroProduto.setBounds(48, 379, 200, 16);
	        			terceiroProduto.add(categoriaTerceiroProduto);
	        			categoriaTerceiroProduto.setText(banco.resultset.getString("nomeCategoria"));
	        			categoriaTerceiroProduto.setFont(new Font("Gotham Book", Font.PLAIN, 12));
	        			categoriaTerceiroProduto.setForeground(Color.BLACK);
	        			
	        			JLabel fotoTerceiroProduto = new JLabel("");
	        			fotoTerceiroProduto.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/splitscreen-product-img-5-240x300.png")));
	        			fotoTerceiroProduto.setBounds(28, 60, 240, 224);
	        			terceiroProduto.add(fotoTerceiroProduto);
	        			
	        			/**
	        			 * ENTERED
	        			 */
	        			
	        			//PREÇO
	        			terceiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		precoTerceiroProduto.setLocation(precoTerceiroProduto.getX(), precoTerceiroProduto.getY() + 5);
	        		            		if (precoTerceiroProduto.getY() + precoTerceiroProduto.getHeight() == 40) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			//NOME
	        			terceiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		nomeTerceiroProduto.setLocation(nomeTerceiroProduto.getX(), nomeTerceiroProduto.getY() - 5);
	        		            		if (nomeTerceiroProduto.getY() + nomeTerceiroProduto.getHeight() == 300) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});

	        			//CATEGORIA
	        			terceiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		categoriaTerceiroProduto.setLocation(categoriaTerceiroProduto.getX(), categoriaTerceiroProduto.getY() - 5);
	        		            		if (categoriaTerceiroProduto.getY() + categoriaTerceiroProduto.getHeight() == 315) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			/**
	        			 * EXITED
	        			 */
	        			
	        			//PREÇO
	        			terceiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		precoTerceiroProduto.setLocation(precoTerceiroProduto.getX(), precoTerceiroProduto.getY() - 5);
	        		            		if (precoTerceiroProduto.getY() + precoTerceiroProduto.getHeight() == 0) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			//NOME
	        			terceiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		nomeTerceiroProduto.setLocation(nomeTerceiroProduto.getX(), nomeTerceiroProduto.getY() + 5);
	        		            		if (nomeTerceiroProduto.getY() + nomeTerceiroProduto.getHeight() == 380) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			
	        			//CATEGORIA
	        			terceiroProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		categoriaTerceiroProduto.setLocation(categoriaTerceiroProduto.getX(), categoriaTerceiroProduto.getY() + 5);
	        		            		if (categoriaTerceiroProduto.getY() + categoriaTerceiroProduto.getHeight() == 395) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	            	}if(quartoProduto.getName().equals(banco.resultset.getString("codProduto"))){
	            	 	stop = 1;
	        			
	            		JLabel precoQuartoProduto = new JLabel();
	            		precoQuartoProduto.setText(banco.resultset.getString("precoProduto") + "R$");
	            		precoQuartoProduto.setHorizontalAlignment(SwingConstants.CENTER);
	            		precoQuartoProduto.setForeground(Color.BLACK);
	            		precoQuartoProduto.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
	            		precoQuartoProduto.setBounds(10, -20, 90, 20);
	        			quartoProduto.add(precoQuartoProduto);
	        			
	        			JLabel nomeQuartoProduto = new JLabel();
	        			nomeQuartoProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        			nomeQuartoProduto.setBounds(48, 364, 200, 16);
	        			quartoProduto.add(nomeQuartoProduto);
	        			nomeQuartoProduto.setText(banco.resultset.getString("nomeProduto"));
	        			nomeQuartoProduto.setForeground(Color.BLACK);
	        			nomeQuartoProduto.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
	        			
	        			JLabel categoriaQuartoProduto = new JLabel();
	        			categoriaQuartoProduto.setHorizontalAlignment(SwingConstants.CENTER);
	        			categoriaQuartoProduto.setBounds(48, 379, 200, 16);
	        			quartoProduto.add(categoriaQuartoProduto);
	        			categoriaQuartoProduto.setText(banco.resultset.getString("nomeCategoria"));
	        			categoriaQuartoProduto.setFont(new Font("Gotham Book", Font.PLAIN, 12));
	        			categoriaQuartoProduto.setForeground(Color.BLACK);
	        			
	        			JLabel fotoQuartoProduto = new JLabel("");
	        			fotoQuartoProduto.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/splitscreen-product-img-3-240x300.png")));
	        			fotoQuartoProduto.setBounds(28, 60, 240, 224);
	        			quartoProduto.add(fotoQuartoProduto);
	        			
	        			/**
	        			 * ENTERED
	        			 */
	        			
	        			//PREÇO
	        			quartoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		precoQuartoProduto.setLocation(precoQuartoProduto.getX(), precoQuartoProduto.getY() + 5);
	        		            		if (precoQuartoProduto.getY() + precoQuartoProduto.getHeight() == 40) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			//NOME
	        			quartoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		nomeQuartoProduto.setLocation(nomeQuartoProduto.getX(), nomeQuartoProduto.getY() - 5);
	        		            		if (nomeQuartoProduto.getY() + nomeQuartoProduto.getHeight() == 300) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});

	        			//CATEGORIA
	        			quartoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseEntered(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		categoriaQuartoProduto.setLocation(categoriaQuartoProduto.getX(), categoriaQuartoProduto.getY() - 5);
	        		            		if (categoriaQuartoProduto.getY() + categoriaQuartoProduto.getHeight() == 315) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			/**
	        			 * EXITED
	        			 */
	        			
	        			//PREÇO
	        			quartoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		precoQuartoProduto.setLocation(precoQuartoProduto.getX(), precoQuartoProduto.getY() - 5);
	        		            		if (precoQuartoProduto.getY() + precoQuartoProduto.getHeight() == 0) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			//NOME
	        			quartoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		nomeQuartoProduto.setLocation(nomeQuartoProduto.getX(), nomeQuartoProduto.getY() + 5);
	        		            		if (nomeQuartoProduto.getY() + nomeQuartoProduto.getHeight() == 380) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	        			
	        			
	        			//CATEGORIA
	        			quartoProduto.addMouseListener(new MouseAdapter() {
	        				public void mouseExited(MouseEvent arg0) {
	        					new Timer(1, new ActionListener() {
	        		            	public void actionPerformed(ActionEvent e) {
	        		            		categoriaQuartoProduto.setLocation(categoriaQuartoProduto.getX(), categoriaQuartoProduto.getY() + 5);
	        		            		if (categoriaQuartoProduto.getY() + categoriaQuartoProduto.getHeight() == 395) {
	        		            			((Timer) e.getSource()).stop();
	        		            		}
	        		            	}
	        		            }).start();
	        					}
	        				});
	            	}else{
	                	stop = 0;
	                }
	            }
			}catch(Exception ec){}
    	 	
			primeiroProduto.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					try{  
						banco.abrirConexao();
						banco.stmt= banco.con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
						String query;
						query = "SELECT * FROM produtos";
						banco.resultset = banco.stmt.executeQuery(query);
						
						int stop = 0;
						
			            while(banco.resultset.next() && stop == 0 ) {
			            	if(primeiroProduto.getName().equals(banco.resultset.getString("codProduto"))){
			            	 	stop = 1;
			            	 	
			            	 	JPanel panelVerMais = new JPanel();
			            	 	panelVerMais.setBackground(new Color(243, 243, 245, 255));
			        			panelCardLayout.add(panelVerMais, "verMais");
			        			panelVerMais.setLayout(null);
			        			
			        			JLabel voltarDestaques = new JLabel("VOLTAR PARA DESTAQUES");
			        			voltarDestaques.setFont(new Font("Gotham Book", Font.PLAIN, 12));
			        			voltarDestaques.setBounds(76, 45, 180, 14);
			        			voltarDestaques.addPropertyChangeListener(new PropertyChangeListener() {
			        				public void propertyChange(PropertyChangeEvent evt) {
			        					voltarDestaques.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			        				}
			        			});
			        			voltarDestaques.addMouseListener(new MouseAdapter() {
			        				@Override
			        				public void mouseClicked(MouseEvent arg0) {
			        					c1.show(panelCardLayout, "1");
			        				}
			        			});
			        			panelVerMais.add(voltarDestaques);
			        			
			        			JLabel lblNomeproduto = new JLabel(banco.resultset.getString("nomeProduto"));
			        			lblNomeproduto.setHorizontalAlignment(SwingConstants.LEFT);
			        			lblNomeproduto.setFont(new Font("Gotham Book", Font.PLAIN, 32));
			        			lblNomeproduto.setForeground(Color.BLACK);
			        			lblNomeproduto.setBounds(650, 145, 385, 35);
			        			panelVerMais.add(lblNomeproduto);
			        			
			        			JLabel lblr = new JLabel(banco.resultset.getString("precoProduto") + "R$");
			        			lblr.setHorizontalAlignment(SwingConstants.LEFT);
			        			lblr.setForeground(Color.BLACK);
			        			lblr.setFont(new Font("Gotham Book", Font.PLAIN, 20));
			        			lblr.setBounds(650, 185, 85, 20);
			        			panelVerMais.add(lblr);
			        			
			        			JLabel label = new JLabel("");
			        			label.setIcon(new ImageIcon(IUHome.class.getResource("/View/imagesRoupas/testeVerMais.png")));
			        			label.setBounds(75, 110, 535, 465);
			        			panelVerMais.add(label);
			        			
			        			JTextArea lblDescproduto = new JTextArea(banco.resultset.getString("descProduto"));
			        			lblDescproduto.setLineWrap(true);
			        			lblDescproduto.setWrapStyleWord(true);
			        			lblDescproduto.setEditable(false);
			        			lblDescproduto.setBackground(new Color(255,255,255,0));
			        			lblDescproduto.setForeground(Color.BLACK);
			        			lblDescproduto.setFont(new Font("Gotham Book", Font.PLAIN, 14));
			        			lblDescproduto.setBounds(650, 216, 450, 194);
			        			panelVerMais.add(lblDescproduto);
			        			
			        			JLabel lblEstoque = new JLabel("Estoque: " + banco.resultset.getString("estoqueProduto") + " unidades.");
			        			lblEstoque.setHorizontalAlignment(SwingConstants.LEFT);
			        			lblEstoque.setForeground(Color.BLACK);
			        			lblEstoque.setFont(new Font("Gotham Book", Font.PLAIN, 14));
			        			lblEstoque.setBounds(650, 482, 250, 20);
			        			panelVerMais.add(lblEstoque);
			        			
			        			JButton adicionarCarrinho = new JButton("ADICIONAR AO CARRINHO");
			        			adicionarCarrinho.setFont(new Font("Gotham Book", Font.PLAIN, 15));
			        			adicionarCarrinho.setBounds(650, 421, 300, 50);
			        			adicionarCarrinho.setBorder(null);
			        			adicionarCarrinho.setIcon(new ColorIcon(Color.DARK_GRAY, adicionarCarrinho.getWidth(), adicionarCarrinho.getHeight()));
			        			adicionarCarrinho.setHorizontalTextPosition(JButton.CENTER);
			        			adicionarCarrinho.setVerticalTextPosition(JButton.CENTER);
			        			adicionarCarrinho.setMargin(new Insets(0, 0, 0, 0));
			        			adicionarCarrinho.setBackground(Color.DARK_GRAY);
			        			adicionarCarrinho.setFont(new Font("Gotham Book", Font.PLAIN, 16));
			        			adicionarCarrinho.setFocusPainted(false);
			        			adicionarCarrinho.setForeground(Color.WHITE);
			        			panelVerMais.add(adicionarCarrinho);
			            	 	
			        			
			                }else{
			                	stop = 0;
			                }
			            }
					}catch(Exception ec){}
					c1.show(panelCardLayout, "verMais");
				}
			});
 			
			lblDestaques.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
	            new Timer(1, new ActionListener() {
	            	public void actionPerformed(ActionEvent e) {
	            		panelMenuAberto.setLocation(panelMenuAberto.getX() - 5, 30);
	            		if (panelMenuAberto.getX() + panelMenuAberto.getWidth() == 0) {
	            			((Timer) e.getSource()).stop();
	            		}
	            	}
	            }).start();
				}
			});
			lblDestaques.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent arg0) {
	            new Timer(1, new ActionListener() {
	            	public void actionPerformed(ActionEvent e) {
	            		panelMenuFechado.setLocation(panelMenuFechado.getX() + 5, 30);
	            		if (panelMenuFechado.getX() + panelMenuFechado.getWidth() == 125) {
	            			((Timer) e.getSource()).stop();
	            		}
	            	}
	            }).start();
				}
			});
			lblDestaques.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					c1.show(panelCardLayout, "1");
				}
			});
			
			lblLogo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					c1.show(panelCardLayout, "0");
				}
			});
		}
}