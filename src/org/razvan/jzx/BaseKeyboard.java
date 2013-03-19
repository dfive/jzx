package org.razvan.jzx;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Base class extended by all Keyboard components that comprise the
 * emulator.
 * <P>
 * There are currently no children of this class, so all keyboard
 * functionality is included in this class.
 *
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan Surdulescu</A> (c) 2001 - 2006
 * @author <A HREF="mailto:webmaster@zx-spectrum.net">Erik Kunze</A> (c) 1995, 1996, 1997
 * @author <A HREF="mailto:des@ops.netcom.net.uk">Des Herriott</A> (c) 1993, 1994
 * <BR>
 * You may use and distribute this software for free provided you include
 * this copyright notice. You may not sell this software, use my name for
 * publicity reasons or modify the code without permission from me.
 */
public class BaseKeyboard extends BaseComponent implements KeyListener {
	// TODO: configure the joystick keyboard mappings
	
	/** Joystick UP event = Keypad 8 */
	protected static final int JOY_UP = KeyEvent.VK_NUMPAD8;
	
	/** Joystick DOWN event = Keypad 2 */
	protected static final int JOY_DOWN = KeyEvent.VK_NUMPAD2;
	
	/** Joystick LEFT event = Keypad 4 */
	protected static final int JOY_LEFT = KeyEvent.VK_NUMPAD4;
	
	/** Joystick RIGHT event = Keypad 5 */
	protected static final int JOY_RIGHT = KeyEvent.VK_NUMPAD6;
	
	/** Joystick FIRE event = Keypad 0 */
	protected static final int JOY_FIRE = KeyEvent.VK_NUMPAD0;
	
	/**
	 * Maps ASCII keys to a keyboard row and bit to set/reset.
	 * <P>
	 * e.g. m_keyTable['a'] means set/reset bit 0 (0xfe) of keyboard row 1.
	 *
	 * Keyboard row 8 (in the table below) means "unused".
	 *
	 * TODO: finish implementing remaining keys
	 */
	protected static final int[][] m_keyTable = {
		{8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		{8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		{8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		{8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		/* SP       PGUP       PGDN       END        HOME       LEFT       UP         RIGHT  */
		{7, 0xfe}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		/* DOWN                                      COMMA      MINUS      PERIOD     SLASH  */
		{8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		/* 0        1          2          3          4          5          6          7      */
		{4, 0xfe}, {3, 0xfe}, {3, 0xfd}, {3, 0xfb}, {3, 0xf7}, {3, 0xef}, {4, 0xef}, {4, 0xf7},
		/* 8        9	                  SEMICOL               EQUALS                       */
		{4, 0xfb}, {4, 0xfd}, {8, 0x00}, {5, 0xfd}, {8, 0x00}, {6, 0xfd}, {8, 0x00}, {8, 0x00},
		/*          A          B          C          D          E          F          G      */
		{8, 0x00}, {1, 0xfe}, {7, 0xef}, {0, 0xf7}, {1, 0xfb}, {2, 0xfb}, {1, 0xf7}, {1, 0xef},
		/* H        I          J          K          L          M          N          O      */
		{6, 0xef}, {5, 0xfb}, {6, 0xf7}, {6, 0xfb}, {6, 0xfd}, {7, 0xfb}, {7, 0xf7}, {5, 0xfd},
		/* P        Q          R          S          T          U          V          W      */
		{5, 0xfe}, {2, 0xfe}, {2, 0xf7}, {1, 0xfd}, {2, 0xef}, {5, 0xf7}, {0, 0xef}, {2, 0xfd},
		/* X        Y          Z          OPENBR     BACKSLA    CLOSEBR                      */
		{0, 0xfb}, {5, 0xef}, {0, 0xfd}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		/*          NUMPAD0    NUMPAD1    NUMPAD2    NUMPAD3    NUMPAD4    NUMPAD5    NUMPAD6*/
		{8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		/*NUMPAD7   NUMPAD8    NUMPAD9    NUMPAD*    NUMPAD+               NUMPAD-    NUMPAD.*/
		{8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		/*NUMPAD/                                                                            */
		{8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00},
		/*                                                                            NUMPADD*/
		{8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}, {8, 0x00}
	};
	
	/**
	 * Is the cursor currently in SHIFT mode?
	 */
	protected boolean m_unshiftCursor;
	
	/**
	 * Cached reference to the I/O component.
	 * <P>
	 * This is used to alter the keyboard port array.
	 */
	protected BaseIO m_io;
	
	protected BaseScreen m_screen;
	
	/**
	 * Cache the reference to the I/O component.
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);
		
		m_io = m_spectrum.getIO();
		m_screen = m_spectrum.getScreen();
	}
	
	/**
	 * Does nothing.
	 */
	public void reset() {
		// Empty
	}
	
	/**
	 * Releases the cached I/O component reference.
	 */
	public void terminate() {
		m_io = null;
		
		super.terminate();
	}
	
	/**
	 * Does nothing.
	 * <P>
	 * Must exist in order for this class to be able to
	 * implement the KeyListener interface.
	 */
	public void keyTyped(KeyEvent kevent) {
		// Empty
	}
	
	/**
	 * Modify the I/O ports in accordance with the
	 * key that was pressed.
	 */
	public void keyPressed(KeyEvent kevent) {
		int key = kevent.getKeyCode();
		
		// Joystick takes precedence.
		if (key == JOY_UP) {
			m_io.orIn(BaseIO.P_KEMPSTON, BaseIO.B_UP);
		} else if (key == JOY_DOWN) {
			m_io.orIn(BaseIO.P_KEMPSTON, BaseIO.B_DOWN);
		} else if (key == JOY_LEFT) {
			m_io.orIn(BaseIO.P_KEMPSTON, BaseIO.B_LEFT);
		} else if (key == JOY_RIGHT) {
			m_io.orIn(BaseIO.P_KEMPSTON, BaseIO.B_RIGHT);
		} else if (key == JOY_FIRE) {
			m_io.orIn(BaseIO.P_KEMPSTON, BaseIO.B_FIRE);
		}
		
		switch (key) {
		case KeyEvent.VK_ENTER:
			m_io.andKey(6, 0xfe);
			break;
		case KeyEvent.VK_BACK_SPACE:
		case KeyEvent.VK_DELETE:
			m_io.andKey(0, 0xfe);
			m_io.andKey(4, 0xfe);
			break;
		case KeyEvent.VK_ESCAPE:
			m_io.andKey(0, 0xfe);
			m_io.andKey(3, 0xfe);
			break;
		case KeyEvent.VK_UP:
			if (!m_unshiftCursor) {
				m_io.andKey(0, 0xfe);
			}
			m_io.andKey(4, 0xf7);
			break;
		case KeyEvent.VK_DOWN:
			if (!m_unshiftCursor) {
				m_io.andKey(0, 0xfe);
			}
			m_io.andKey(4, 0xef);
			break;
		case KeyEvent.VK_LEFT:
			if (!m_unshiftCursor) {
				m_io.andKey(0, 0xfe);
			}
			m_io.andKey(3, 0xef);
			break;
		case KeyEvent.VK_RIGHT:
			if (!m_unshiftCursor) {
				m_io.andKey(0, 0xfe);
			}
			m_io.andKey(4, 0xfb);
			break;
		case KeyEvent.VK_COMMA:
			m_io.andKey(7, 0xfd);
			m_io.andKey(7, 0xf7);
			break;
		case KeyEvent.VK_MINUS:
			m_io.andKey(7, 0xfd);
			m_io.andKey(6, 0xf7);
			break;
		case KeyEvent.VK_PERIOD:
			m_io.andKey(7, 0xfd);
			m_io.andKey(7, 0xfb);
			break;
		case KeyEvent.VK_SLASH:
			m_io.andKey(7, 0xfd);
			m_io.andKey(0, 0xef);
			break;
		case KeyEvent.VK_SEMICOLON:
			// TODO: fix
			/*
			 m_io.andKey(7, 0xfd);
			 m_io.andKey(5, 0xfd);
			 */
			break;
		case KeyEvent.VK_EQUALS:
			// TODO: fix
			/*
			 m_io.andKey(7, 0xfd);
			 m_io.andKey(6, 0xfd);
			 */
			break;
		case KeyEvent.VK_SHIFT:
			m_io.andKey(0, 0xfe);
			break;
		case KeyEvent.VK_CONTROL:
			m_io.andKey(7, 0xfd);
			break;
			
		default:
			if (key < 128) {
				m_io.andKey(m_keyTable[key][0], m_keyTable[key][1]);
			}
		break;
		}
	}
	
	/**
	 * Modify the I/O ports in accordance with the
	 * key that was released.
	 */
	public void keyReleased(KeyEvent kevent) {
		int key = kevent.getKeyCode();
		
		// Joystick takes precedence.
		if (key == JOY_UP) {
			m_io.andIn(BaseIO.P_KEMPSTON, ~BaseIO.B_UP);
		} else if (key == JOY_DOWN) {
			m_io.andIn(BaseIO.P_KEMPSTON, ~BaseIO.B_DOWN);
		} else if (key == JOY_LEFT) {
			m_io.andIn(BaseIO.P_KEMPSTON, ~BaseIO.B_LEFT);
		} else if (key == JOY_RIGHT) {
			m_io.andIn(BaseIO.P_KEMPSTON, ~BaseIO.B_RIGHT);
		} else if (key == JOY_FIRE) {
			m_io.andIn(BaseIO.P_KEMPSTON, ~BaseIO.B_FIRE);
		}
		
		switch (key) {
		case KeyEvent.VK_TAB:
			m_unshiftCursor = !m_unshiftCursor;
			break;
			
		case KeyEvent.VK_ENTER:
			m_io.orKey(6, 0x01);
			break;
		case KeyEvent.VK_BACK_SPACE:
		case KeyEvent.VK_DELETE:
			m_io.orKey(0, 0x01);
			m_io.orKey(4, 0x01);
			break;
		case KeyEvent.VK_ESCAPE:
			m_io.orKey(0, 0x01);
			m_io.orKey(3, 0x01);
			break;
		case KeyEvent.VK_UP:
			if (!m_unshiftCursor) {
				m_io.orKey(0, 0x01);
			}
			m_io.orKey(4, 0x08);
			break;
		case KeyEvent.VK_DOWN:
			if (!m_unshiftCursor) {
				m_io.orKey(0, 0x01);
			}
			m_io.orKey(4, 0x10);
			break;
		case KeyEvent.VK_LEFT:
			if (!m_unshiftCursor) {
				m_io.orKey(0, 0x01);
			}
			m_io.orKey(3, 0x10);
			break;
		case KeyEvent.VK_RIGHT:
			if (!m_unshiftCursor) {
				m_io.orKey(0, 0x01);
			}
			m_io.orKey(4, 0x04);
			break;
		case KeyEvent.VK_COMMA:
			m_io.orKey(7, ~0xf7);
			m_io.orKey(7, 0x02);
			break;
		case KeyEvent.VK_MINUS:
			m_io.orKey(6, ~0xf7);
			m_io.orKey(7, 0x02);
			break;
		case KeyEvent.VK_PERIOD:
			m_io.orKey(7, ~0xfb);
			m_io.orKey(7, 0x02);
			break;
		case KeyEvent.VK_SLASH:
			m_io.orKey(0, ~0xef);
			m_io.orKey(7, 0x02);
			break;
		case KeyEvent.VK_SEMICOLON:
			// TODO: fix
			/*
			 m_io.orKey(5, ~0xfd);
			 m_io.orKey(7, 0xfd);
			 */
			break;
		case KeyEvent.VK_EQUALS:
			// TODO: fix
			/*
			 m_io.orKey(6, ~0xfd);
			 m_io.orKey(7, 0xfd);
			 */
			break;
		case KeyEvent.VK_SHIFT:
			m_io.orKey(0, 0x01);
			break;
		case KeyEvent.VK_CONTROL:
			m_io.orKey(7, 0x02);
			break;
			
		default:
			if (key < 128) {
				m_io.orKey(m_keyTable[key][0], ~m_keyTable[key][1]);
			}
		
		break;
		}
	}
	
	/**
	 * Does nothing.
	 * <P>
	 * No keyboard state is saved, so there is nothing
	 * to load.
	 */
	public void load(BaseLoader loader) {
		// Empty
	}
}
