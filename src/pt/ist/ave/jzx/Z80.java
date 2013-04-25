package pt.ist.ave.jzx;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

import pt.ist.ave.jzx.instructions.Instruction;
import pt.ist.ave.jzx.instructions.InstructionFactory;
import pt.ist.ave.jzx.operations.DEC8;
import pt.ist.ave.jzx.operations.INC8;
import pt.ist.ave.jzx.operations.Operation;
import pt.ist.ave.jzx.operations.RLC8;
import pt.ist.ave.jzx.operations.RRC8;
import pt.ist.ave.jzx.operations.ShiftTest;

/**
 * The Z80 CPU component of the Spectrum emulator.
 * 
 * @author <A HREF="mailto:razvan.surdulescu@post.harvard.edu">Razvan
 *         Surdulescu</A> (c) 2001 - 2006 <BR>
 *         You may use and distribute this software for free provided you
 *         include this copyright notice. You may not sell this software, use my
 *         name for publicity reasons or modify the code without permission from
 *         me.
 * 
 * @see BaseSpectrum
 */
public class Z80 extends BaseComponent {
	/**
	 * Indicates that the <TT>emulate()</TT> method should end.
	 */
	protected volatile boolean m_stop;

	/**
	 * Indicates that the <TT>emulate()</TT> method should pause.
	 */
	protected volatile boolean m_pause;

	/**
	 * The current count of T-States for the CPU.
	 * <P>
	 * This count is incremented by the appropriate amount for every instruction
	 * that is executed.
	 * <P>
	 * The T-States are used for synchonizing the rendering of screen lines that
	 * comprise the screen frame.
	 */
	public int m_tstates;

	/**
	 * Cached reference to the memory object.
	 * <P>
	 * This is used to read and write data as CPU instructions access the
	 * memory.
	 */
	public BaseMemory m_memory;

	/**
	 * Cached reference to the I/O object.
	 * <P>
	 * This is used to read and write I/O ports CPU instructions are decoded.
	 */
	public BaseIO m_io;

	/** The bit mask used to extract the CARRY flag from the F register. */
	public static final int CARRY_MASK = 0x01;

	/** The bit mask used to set the 5 flag in some instructions. */
	public static final int ONE_MASK = 0x01;

	/** The bit mask used to extract the ADDSUBTRACT flag from the F register. */
	public static final int ADDSUBTRACT_MASK = 0x02;

	/** The bit mask used to extract the PARITY flag from the F register. */
	public static final int PARITY_MASK = 0x04;

	/** The bit mask used to extract the OVERFLOW flag from the F register. */
	public static final int OVERFLOW_MASK = PARITY_MASK;

	/** The bit mask used to extract the THREE flag from the F register. */
	public static final int THREE_MASK = 0x08;

	/** The bit mask used to extract the HALFCARRY flag from the F register. */
	public static final int HALFCARRY_MASK = 0x10;

	/** The bit mask used to extract the FIVE flag from the F register. */
	public static final int FIVE_MASK = 0x20;

	/** The bit mask used to extract the ZERO flag from the F register. */
	public static final int ZERO_MASK = 0x40;

	/** The bit mask used to extract the SIGN flag from the F register. */
	public static final int SIGN_MASK = 0x80;

	/**
	 * Flags
	 * this data structures are needed in order for the emulator to compute
	 * the flags in a lazy way
	 */
	public static final int FLAG_CARRY = 0;
	public static final int FLAG_ADD_SUBTRACT = 1;
	public static final int FLAG_PARITY_OVERFLOW = 2;
	public static final int FLAG_HALF_CARRY = 3;
	public static final int FLAG_ZERO = 4;
	public static final int FLAG_SIGN = 5;
	public static final int FLAG_5 = 6;
	public static final int FLAG_3 = 7;

	public static final int NUMBER_FLAGS = 8;

	private Operation[] lastFlagOperation;

	{
		lastFlagOperation = new Operation[NUMBER_FLAGS];
		for(int i=0; i < NUMBER_FLAGS; ++i){
			//			lastFlagOperation[i] = new DefaultOperation();
			lastFlagOperation[i] = null;
		}
	}

	/**
	 * Explicit flags, which represent the bits in the F register as distinct
	 * variables.
	 * 
	 * @see #storeFlags
	 * @see #retrieveFlags
	 */
	private boolean m_carryF, m_addsubtractF, m_parityoverflowF, m_halfcarryF,
	m_zeroF, m_signF, m_5F, m_3F;

	/**
	 * Last operation performed that refreshed flag values. Access this variable
	 * to get the value of the pretended flag.
	 *   
	 * @see #storeFlags
	 * @see #retrieveFlags
	 */


	//	public static short[] registers8bit = new short[8];	

	/** The basic registers (least significant 8 bits.) */
	public int m_a8, m_f8, m_b8, m_c8, m_d8, m_e8, m_h8, m_l8;

	/** The alternate registers (least significant 16 bits.) */
	private int m_af16alt, m_bc16alt, m_de16alt, m_hl16alt;
	/** The index registers (least significant 16 bits.) */
	private int m_ix16, m_iy16, m_xx16;
	/** The core registers (least significant 16 bits.) */
	private int m_sp16, m_pc16;
	/** The refresh and interrupt registers (least significant 8 bits.) */
	private int m_r8, m_i8;
	/** The interrupt mode (least significant 2 bits.) */
	private int m_im2;
	/** The flip-flops (least significant 1 bit.) */
	private int m_iff1a, m_iff1b;

	/**
	 * Secret, internal Z80 register that is set as follows:
	 * 
	 * ADD HL,xx = high byte of HL, i.e. H, before the addition LD r,(IX/IY+d) =
	 * high byte of the resulting address IX/IY+d JR d = high byte target
	 * address of the jump
	 */
	private int m_x8;

	/**
	 * Parity table for 256 bytes (true = even parity, false = odd parity).
	 */
	public static final boolean m_parityTable[] = { true, false, false, true,
		false, true, true, false, false, true, true, false, true, false,
		false, true, false, true, true, false, true, false, false, true,
		true, false, false, true, false, true, true, false, false, true,
		true, false, true, false, false, true, true, false, false, true,
		false, true, true, false, true, false, false, true, false, true,
		true, false, false, true, true, false, true, false, false, true,
		false, true, true, false, true, false, false, true, true, false,
		false, true, false, true, true, false, true, false, false, true,
		false, true, true, false, false, true, true, false, true, false,
		false, true, true, false, false, true, false, true, true, false,
		false, true, true, false, true, false, false, true, false, true,
		true, false, true, false, false, true, true, false, false, true,
		false, true, true, false, false, true, true, false, true, false,
		false, true, true, false, false, true, false, true, true, false,
		true, false, false, true, false, true, true, false, false, true,
		true, false, true, false, false, true, true, false, false, true,
		false, true, true, false, false, true, true, false, true, false,
		false, true, false, true, true, false, true, false, false, true,
		true, false, false, true, false, true, true, false, true, false,
		false, true, false, true, true, false, false, true, true, false,
		true, false, false, true, false, true, true, false, true, false,
		false, true, true, false, false, true, false, true, true, false,
		false, true, true, false, true, false, false, true, true, false,
		false, true, false, true, true, false, true, false, false, true,
		false, true, true, false, false, true, true, false, true, false,
		false, true, };

	/** Half carry table (addition). */
	public static boolean m_halfcarryTable[] = { false, false, true, false,
		true, false, true, true };
	/** Half carry table (subtraction). */
	public static boolean m_subhalfcarryTable[] = { false, true, true, true,
		false, false, false, true };

	/** Overflow table (addition). */
	public static boolean m_overflowTable[] = { false, true, false, false,
		false, false, true, false };
	/** Overflow table (subtraction). */
	public static boolean m_suboverflowTable[] = { false, false, false, true,
		true, false, false, false };

	/**
	 * @return the m_tstates
	 */
	public int getM_tstates() {
		return m_tstates;
	}

	/**
	 * @return the m_memory
	 */
	public BaseMemory getM_memory() {
		return m_memory;
	}

	/**
	 * @return the m_io
	 */
	public BaseIO getM_io() {
		return m_io;
	}

	/**
	 * @return the m_carryF
	 */
	public boolean getM_carryF() {
		if(lastFlagOperation[FLAG_CARRY]!=null) {
			return lastFlagOperation[FLAG_CARRY].getM_carryF();
		}
		return m_carryF;
	}

	/**
	 * @return the m_addsubtractF
	 */
	public boolean getM_addsubtractF() {
//		if(lastFlagOperation[FLAG_ADD_SUBTRACT]!=null) {
//			return lastFlagOperation[FLAG_ADD_SUBTRACT].getM_addsubtractF();
//		}
		return m_addsubtractF;
	}

	/**
	 * @return the m_parityoverflowF
	 */
	public boolean getM_parityoverflowF() {
//		if(lastFlagOperation[FLAG_PARITY_OVERFLOW]!=null) {
//			return lastFlagOperation[FLAG_PARITY_OVERFLOW].getM_parityoverflowF();
//		}
		return m_parityoverflowF;
	}

	/**
	 * @return the m_halfcarryF
	 */
	public boolean getM_halfcarryF() {
//		if(lastFlagOperation[FLAG_HALF_CARRY]!=null) {
//			return lastFlagOperation[FLAG_HALF_CARRY].getM_halfcarryF();
//		}
		return m_halfcarryF;
	}

	/**
	 * @return the m_zeroF
	 */
	public boolean getM_zeroF() {
//		if(lastFlagOperation[FLAG_ZERO]!=null) {
//			return lastFlagOperation[FLAG_ZERO].getM_zeroF();
//		}
		return m_zeroF;
	}

	/**
	 * @return the m_signF
	 */
	public boolean getM_signF() {
//		if(lastFlagOperation[FLAG_SIGN]!=null) {
//			return lastFlagOperation[FLAG_SIGN].getM_signF();
//		}
		return m_signF;
	}

	/**
	 * @return the m_5F
	 */
	public boolean getM_5F() {
//		if(lastFlagOperation[FLAG_5]!=null) {
//			return lastFlagOperation[FLAG_5].getM_5F();
//		}
		return m_5F;
	}

	/**
	 * @return the m_3F
	 */
	public boolean getM_3F() {
//		if(lastFlagOperation[FLAG_3]!=null) {
//			return lastFlagOperation[FLAG_3].getM_3F();
//		}
		return m_3F;
	}

	private void dumpFlagOperations() {
		System.out.println("FLAG3 " + lastFlagOperation[FLAG_3]);

		System.out.println("FLAG_5 " + lastFlagOperation[FLAG_5]);

		System.out.println("FLAG_ADD_SUBTRACT " + lastFlagOperation[FLAG_ADD_SUBTRACT]);

		System.out.println("FLAG_CARRY " + lastFlagOperation[FLAG_CARRY]);

		System.out.println("FLAG_HALF_CARRY " + lastFlagOperation[FLAG_HALF_CARRY]);

		System.out.println("FLAG_PARITY_OVERFLOW " + lastFlagOperation[FLAG_PARITY_OVERFLOW]);

		System.out.println("FLAG_SIGN " + lastFlagOperation[FLAG_SIGN]);

		System.out.println("FLAG_ZERO " + lastFlagOperation[FLAG_ZERO]);
	}

	private void resetFlagOperations() {

		if(lastFlagOperation[FLAG_3]!=null) {
			setM_3F(getLastFlagOperation(FLAG_3).getM_3F()) ;
		}

		if(lastFlagOperation[FLAG_5]!=null) {
			setM_5F(getLastFlagOperation(FLAG_5).getM_5F()) ;
		}

		if(lastFlagOperation[FLAG_ADD_SUBTRACT]!=null) {
			setM_addsubtractF(getLastFlagOperation(FLAG_ADD_SUBTRACT).getM_addsubtractF()) ;
		}

		if(lastFlagOperation[FLAG_CARRY]!=null) {
			setM_carryF(getLastFlagOperation(FLAG_CARRY).getM_carryF()) ;
		}

		if(lastFlagOperation[FLAG_HALF_CARRY]!=null) {
			setM_halfcarryF(getLastFlagOperation(FLAG_HALF_CARRY).getM_halfcarryF()) ;
		}

		if(lastFlagOperation[FLAG_PARITY_OVERFLOW]!=null) {
			setM_parityoverflowF(getLastFlagOperation(FLAG_PARITY_OVERFLOW).getM_parityoverflowF()) ;
		}

		if(lastFlagOperation[FLAG_SIGN]!=null) {
			setM_zeroF(getLastFlagOperation(FLAG_SIGN).getM_zeroF()) ;
		}

		if(lastFlagOperation[FLAG_ZERO]!=null) {
			setM_zeroF(getLastFlagOperation(FLAG_ZERO).getM_zeroF()) ;
		}

	}

	/**
	 * @return the m_a8
	 */
	public int getM_a8() {
		return m_a8;
	}

	/**
	 * @return the m_f8
	 */
	public int getM_f8() {
		return m_f8;
	}

	/**
	 * @return the m_b8
	 */
	public int getM_b8() {
		return m_b8;
	}

	/**
	 * @return the m_c8
	 */
	public int getM_c8() {
		return m_c8;
	}

	/**
	 * @return the m_d8
	 */
	public int getM_d8() {
		return m_d8;
	}

	/**
	 * @return the m_e8
	 */
	public int getM_e8() {
		return m_e8;
	}

	/**
	 * @return the m_h8
	 */
	public int getM_h8() {
		return m_h8;
	}

	/**
	 * @return the m_l8
	 */
	public int getM_l8() {
		return m_l8;
	}

	/**
	 * @return the m_af16alt
	 */
	public int getM_af16alt() {
		return m_af16alt;
	}

	/**
	 * @return the m_bc16alt
	 */
	public int getM_bc16alt() {
		return m_bc16alt;
	}

	/**
	 * @return the m_de16alt
	 */
	public int getM_de16alt() {
		return m_de16alt;
	}

	/**
	 * @return the m_hl16alt
	 */
	public int getM_hl16alt() {
		return m_hl16alt;
	}

	/**
	 * @return the m_ix16
	 */
	public int getM_ix16() {
		return m_ix16;
	}

	/**
	 * @return the m_iy16
	 */
	public int getM_iy16() {
		return m_iy16;
	}

	/**
	 * @return the m_xx16
	 */
	public int getM_xx16() {
		return m_xx16;
	}

	/**
	 * @return the m_sp16
	 */
	public int getM_sp16() {
		return m_sp16;
	}

	/**
	 * @return the m_pc16
	 */
	public int getM_pc16() {
		return m_pc16;
	}

	/**
	 * @return the m_r8
	 */
	public int getM_r8() {
		return m_r8;
	}

	/**
	 * @return the m_i8
	 */
	public int getM_i8() {
		return m_i8;
	}

	/**
	 * @return the m_im2
	 */
	public int getM_im2() {
		return m_im2;
	}

	/**
	 * @return the m_iff1a
	 */
	public int getM_iff1a() {
		return m_iff1a;
	}

	/**
	 * @return the m_iff1b
	 */
	public int getM_iff1b() {
		return m_iff1b;
	}

	/**
	 * @return the m_x8
	 */
	public int getM_x8() {
		return m_x8;
	}

	/**
	 * @param m_tstates the m_tstates to set
	 */
	public void setM_tstates(int m_tstates) {
		this.m_tstates = m_tstates;
	}

	/**
	 * @param m_memory the m_memory to set
	 */
	public void setM_memory(BaseMemory m_memory) {
		this.m_memory = m_memory;
	}

	/**
	 * @param m_io the m_io to set
	 */
	public void setM_io(BaseIO m_io) {
		this.m_io = m_io;
	}

	/**
	 * @param m_carryF the m_carryF to set
	 */
	public void setM_carryF(boolean m_carryF) {
		this.m_carryF = m_carryF;
//		setFlagOperation(FLAG_CARRY, null);
	}

	/**
	 * @param m_addsubtractF the m_addsubtractF to set
	 */
	public void setM_addsubtractF(boolean m_addsubtractF) {
		this.m_addsubtractF = m_addsubtractF;
//		setFlagOperation(FLAG_ADD_SUBTRACT, null);
	}

	/**
	 * @param m_parityoverflowF the m_parityoverflowF to set
	 */
	public void setM_parityoverflowF(boolean m_parityoverflowF) {
		this.m_parityoverflowF = m_parityoverflowF;
//		setFlagOperation(FLAG_PARITY_OVERFLOW, null);
	}

	/**
	 * @param m_halfcarryF the m_halfcarryF to set
	 */
	public void setM_halfcarryF(boolean m_halfcarryF) {
		this.m_halfcarryF = m_halfcarryF;
//		setFlagOperation(FLAG_HALF_CARRY, null);
	}

	/**
	 * @param m_zeroF the m_zeroF to set
	 */
	public void setM_zeroF(boolean m_zeroF) {
		this.m_zeroF = m_zeroF;
//		setFlagOperation(FLAG_ZERO, null);
	}

	/**
	 * @param m_signF the m_signF to set
	 */
	public void setM_signF(boolean m_signF) {
		this.m_signF = m_signF;
//		setFlagOperation(FLAG_SIGN, null);
	}

	/**
	 * @param m_5f the m_5F to set
	 */
	public void setM_5F(boolean m_5f) {
		this.m_5F = m_5f;
//		setFlagOperation(FLAG_5, null);
	}

	/**
	 * @param m_3f the m_3F to set
	 */
	public void setM_3F(boolean m_3f) {
		this.m_3F = m_3f;
//		setFlagOperation(FLAG_3, null);
	}

	/**
	 * @param m_a8 the m_a8 to set
	 */
	public void setM_a8(int m_a8) {
		this.m_a8 = m_a8;
	}

	/**
	 * @param m_f8 the m_f8 to set
	 */
	public void setM_f8(int m_f8) {
		this.m_f8 = m_f8;
	}

	/**
	 * @param m_b8 the m_b8 to set
	 */
	public void setM_b8(int m_b8) {
		this.m_b8 = m_b8;
	}

	/**
	 * @param m_c8 the m_c8 to set
	 */
	public void setM_c8(int m_c8) {
		this.m_c8 = m_c8;
	}

	/**
	 * @param m_d8 the m_d8 to set
	 */
	public void setM_d8(int m_d8) {
		this.m_d8 = m_d8;
	}

	/**
	 * @param m_e8 the m_e8 to set
	 */
	public void setM_e8(int m_e8) {
		this.m_e8 = m_e8;
	}

	/**
	 * @param m_h8 the m_h8 to set
	 */
	public void setM_h8(int m_h8) {
		this.m_h8 = m_h8;
	}

	/**
	 * @param m_l8 the m_l8 to set
	 */
	public void setM_l8(int m_l8) {
		this.m_l8 = m_l8;
	}

	/**
	 * @param m_af16alt the m_af16alt to set
	 */
	public void setM_af16alt(int m_af16alt) {
		this.m_af16alt = m_af16alt;
	}

	/**
	 * @param m_bc16alt the m_bc16alt to set
	 */
	public void setM_bc16alt(int m_bc16alt) {
		this.m_bc16alt = m_bc16alt;
	}

	/**
	 * @param m_de16alt the m_de16alt to set
	 */
	public void setM_de16alt(int m_de16alt) {
		this.m_de16alt = m_de16alt;
	}

	/**
	 * @param m_hl16alt the m_hl16alt to set
	 */
	public void setM_hl16alt(int m_hl16alt) {
		this.m_hl16alt = m_hl16alt;
	}

	/**
	 * @param m_ix16 the m_ix16 to set
	 */
	public void setM_ix16(int m_ix16) {
		this.m_ix16 = m_ix16;
	}

	/**
	 * @param m_iy16 the m_iy16 to set
	 */
	public void setM_iy16(int m_iy16) {
		this.m_iy16 = m_iy16;
	}

	/**
	 * @param m_xx16 the m_xx16 to set
	 */
	public void setM_xx16(int m_xx16) {
		this.m_xx16 = m_xx16;
	}

	/**
	 * @param m_sp16 the m_sp16 to set
	 */
	public void setM_sp16(int m_sp16) {
		this.m_sp16 = m_sp16;
	}

	/**
	 * @param m_pc16 the m_pc16 to set
	 */
	public void setM_pc16(int m_pc16) {
		this.m_pc16 = m_pc16;
	}

	/**
	 * @param m_r8 the m_r8 to set
	 */
	public void setM_r8(int m_r8) {
		this.m_r8 = m_r8;
	}

	/**
	 * @param m_i8 the m_i8 to set
	 */
	public void setM_i8(int m_i8) {
		this.m_i8 = m_i8;
	}

	/**
	 * @param m_im2 the m_im2 to set
	 */
	public void setM_im2(int m_im2) {
		this.m_im2 = m_im2;
	}

	/**
	 * @param m_iff1a the m_iff1a to set
	 */
	public void setM_iff1a(int m_iff1a) {
		this.m_iff1a = m_iff1a;
	}

	/**
	 * @param m_iff1b the m_iff1b to set
	 */
	public void setM_iff1b(int m_iff1b) {
		this.m_iff1b = m_iff1b;
	}

	/**
	 * @param m_x8 the m_x8 to set
	 */
	public void setM_x8(int m_x8) {
		this.m_x8 = m_x8;
	}


	/** Tabela com as instancias das instrucoes do cpu */
	private static final Instruction[] instructionTable = new Instruction[256];

	private static final int[] instructionCounter = new int[255];

	private static long instrs = 0;

	/** Inicializacaoo da tabela */
	static {
		for(short i = 0; i < 256; i++){
			instructionTable[i] = InstructionFactory.getInstruction(i);
		}
	}

	/**
	 * Cache references to the memory and I/O objects.
	 */
	public void init(BaseSpectrum spectrum, ILogger logger) {
		super.init(spectrum, logger);
		m_memory = m_spectrum.getMemory();
		m_io = m_spectrum.getIO();
	}

	/**
	 * Set the PC, I, R, IM and IFF* registers to 0.
	 */
	public void reset() {
		m_pc16 = 0;
		m_i8 = 0;
		m_r8 = 0;
		m_im2 = 0;
		m_iff1a = 0;
		m_iff1b = 0;
	}

	/**
	 * Does nothing.
	 */
	public void terminate() {
		// Empty
	}

	/**
	 * Interrupt the CPU.
	 * <P>
	 * If IFF1a is not zero, set both IFF flags to 0 and push the value of PC on
	 * the stack. If the IM is 2, jump to the address (I, 0xFF), otherwise jump
	 * to 0x38.
	 */
	public void interrupt() {
		if (m_iff1a != 0) {
			// Make sure we don't return to a halt instruction.
			if (m_memory.read8(m_pc16) == 0x76) {
				inc16pc();
			}

			m_iff1a = 0;
			m_iff1b = 0;
			push(m_pc16);

			if (m_im2 == 2) {
				m_tstates += 19;
				m_pc16 = m_memory.read16((m_i8 << 8) | 0xff);
			} else {
				m_tstates += 13;
				m_pc16 = 0x38;
			}
		}
	}

	/**
	 * Generates a non-maskable interrupt (NMI.)
	 * <P>
	 * Set IFF1b = IFF1a, and set IFF1a to 0 (disable regular interrupts.) Push
	 * the PC on the stack, then jump to address 0x66.
	 */
	public void nmi() {
		m_tstates += 15;

		m_iff1b = m_iff1a;
		m_iff1a = 0;

		// Make sure we don't return to a halt instruction.
		if (m_memory.read8(m_pc16) == 0x76) {
			inc16pc();
		}

		push(m_pc16);
		m_pc16 = 0x66;
	}

	/**
	 * Trivial accessor for the T-States value.
	 */
	public int getTStates() {
		return m_tstates;
	}

	/**
	 * Trivial mutator for the T-States value.
	 */
	public void setTStates(int tstates) {
		m_tstates = tstates;
	}

	/**
	 * Add a number to the current value of T-States.
	 */
	public void addTStates(int val) {
		m_tstates += val;
	}

	/**
	 * Accessor for the 16-bit AF register (it combines the A and F registers.)
	 */
	public int af16() {
		return ((m_a8 << 8) | m_f8);
	}

	/**
	 * Accessor for the 16-bit BC register (it combines the A and F registers.)
	 */
	public int bc16() {
		return ((m_b8 << 8) | m_c8);
	}

	/**
	 * Accessor for the 16-bit DE register (it combines the A and F registers.)
	 */
	public int de16() {
		return ((m_d8 << 8) | m_e8);
	}

	/**
	 * Accessor for the 16-bit HL register (it combines the A and F registers.)
	 */
	public int hl16() {
		return ((m_h8 << 8) | m_l8);
	}

	/**
	 * Mutator for the 16-bit AF register (it sets the A and F registers.)
	 */
	public void af16(int val16) {
		m_a8 = (val16 >> 8);
		m_f8 = (val16 & 0xff);
	}

	/**
	 * Mutator for the 16-bit BC register (it sets the A and F registers.)
	 */
	public void bc16(int val16) {
		m_b8 = (val16 >> 8);
		m_c8 = (val16 & 0xff);
	}

	/**
	 * Mutator for the 16-bit DE register (it sets the A and F registers.)
	 */
	public void de16(int val16) {
		m_d8 = (val16 >> 8);
		m_e8 = (val16 & 0xff);
	}

	/**
	 * Mutator for the 16-bit HL register (it sets the A and F registers.)
	 */
	public void hl16(int val16) {
		m_h8 = (val16 >> 8);
		m_l8 = (val16 & 0xff);
	}

	/**
	 * Accessor for the low 8 bits of the current index register.
	 * <P>
	 * The current index register is stored in m_xx16, and could either be
	 * m_ix16 or m_iy16.
	 */
	public int xx16low8() {
		return (m_xx16 & 0xff);
	}

	/**
	 * Accessor for the high 8 bits of the current index register.
	 * <P>
	 * The current index register is stored in m_xx16, and could either be
	 * m_ix16 or m_iy16.
	 */
	public int xx16high8() {
		return (m_xx16 >> 8);
	}

	/**
	 * Mutator for the low 8 bits of the current index register.
	 * <P>
	 * The current index register is stored in m_xx16, and could either be
	 * m_ix16 or m_iy16.
	 */
	public void xx16low8(int val8) {
		m_xx16 = ((m_xx16 & 0xff00) | val8);
	}

	/**
	 * Mutator for the high 8 bits of the current index register.
	 * <P>
	 * The current index register is stored in m_xx16, and could either be
	 * m_ix16 or m_iy16.
	 */
	public void xx16high8(int val8) {
		m_xx16 = ((val8 << 8) | (m_xx16 & 0xff));
	}

	/**
	 * Store the values from m_*F variables into the m_f8 register, according to
	 * the *_MASK variables.
	 */
	public void storeFlags() {
		if (getM_signF())
			m_f8 |= SIGN_MASK;
		else
			m_f8 &= ~SIGN_MASK;
		if (getM_zeroF())
			m_f8 |= ZERO_MASK;
		else
			m_f8 &= ~ZERO_MASK;
		if (getM_halfcarryF())
			m_f8 |= HALFCARRY_MASK;
		else
			m_f8 &= ~HALFCARRY_MASK;
		if (getM_parityoverflowF())
			m_f8 |= OVERFLOW_MASK;
		else
			m_f8 &= ~OVERFLOW_MASK;
		if (getM_addsubtractF())
			m_f8 |= ADDSUBTRACT_MASK;
		else
			m_f8 &= ~ADDSUBTRACT_MASK;
		if (getM_carryF())
			m_f8 |= CARRY_MASK;
		else
			m_f8 &= ~CARRY_MASK;
		if (getM_3F())
			m_f8 |= THREE_MASK;
		else
			m_f8 &= ~THREE_MASK;
		if (getM_5F())
			m_f8 |= FIVE_MASK;
		else
			m_f8 &= ~FIVE_MASK;
	}

	/**
	 * Retrieve the flags from the m_f8 register into the the m_*F variables,
	 * according to the *_MASK variables.
	 */
	public void retrieveFlags() {
		setM_signF((m_f8 & SIGN_MASK) != 0);
		setM_zeroF((m_f8 & ZERO_MASK) != 0);
		setM_halfcarryF((m_f8 & HALFCARRY_MASK) != 0);
		setM_parityoverflowF((m_f8 & OVERFLOW_MASK) != 0);
		setM_addsubtractF((m_f8 & ADDSUBTRACT_MASK) != 0);
		setM_carryF((m_f8 & CARRY_MASK) != 0);
		setM_3F((m_f8 & THREE_MASK) != 0);
		setM_5F((m_f8 & FIVE_MASK) != 0);
	}

	/**
	 * Increment the 16-bit PC register.
	 */
	public int inc16pc() {
		int work16 = m_pc16;
		m_pc16 = ((m_pc16 + 1) & 0xffff);
		return work16;
	}

	/**
	 * Increment the 16-bit SP register.
	 */
	public int inc16sp() {
		int work16 = m_sp16;
		m_sp16 = ((m_sp16 + 1) & 0xffff);
		return work16;
	}

	/**
	 * Increment the 16-bit BC register.
	 */
	public int inc16bc() {
		int work16 = bc16();
		bc16((work16 + 1) & 0xffff);
		return work16;
	}

	/**
	 * Increment the 16-bit DE register.
	 */
	public int inc16de() {
		int work16 = de16();
		de16((work16 + 1) & 0xffff);
		return work16;
	}

	/**
	 * Increment the 16-bit HL register.
	 */
	public int inc16hl() {
		int work16 = hl16();
		hl16((work16 + 1) & 0xffff);
		return work16;
	}

	/**
	 * Increment the 16-bit (current) index register (m_xx16).
	 */
	public int inc16xx() {
		int work16 = m_xx16;
		m_xx16 = ((m_xx16 + 1) & 0xffff);
		return work16;
	}

	/**
	 * Decrement the 16-bit PC register.
	 */
	public int dec16pc() {
		int work16 = m_pc16;
		m_pc16 = ((m_pc16 - 1) & 0xffff);
		return work16;
	}

	/**
	 * Decrement the 16-bit SP register.
	 */
	public int dec16sp() {
		int work16 = m_sp16;
		m_sp16 = ((m_sp16 - 1) & 0xffff);
		return work16;
	}

	/**
	 * Decrement the 16-bit BC register.
	 */
	public int dec16bc() {
		int work16 = bc16();
		bc16((work16 - 1) & 0xffff);
		return work16;
	}

	/**
	 * Decrement the 16-bit DE register.
	 */
	public int dec16de() {
		int work16 = de16();
		de16((work16 - 1) & 0xffff);
		return work16;
	}

	/**
	 * Decrement the 16-bit HL register.
	 */
	public int dec16hl() {
		int work16 = hl16();
		hl16((work16 - 1) & 0xffff);
		return work16;
	}

	/**
	 * Decrement the 16-bit (current) index register (m_xx16).
	 */
	public int dec16xx() {
		int work16 = m_xx16;
		m_xx16 = ((m_xx16 - 1) & 0xffff);
		return work16;
	}

	/**
	 * Add a 16-bit value to the (current) index register (m_xx16) and set the
	 * appropriate flags.
	 */
	public void add_xx(int val16) {
		//		ADD_XX operation = new ADD_XX(val16);
		//		operation.execute();

		int work32 = m_xx16 + val16;
		int idx = ((m_xx16 & 0x800) >> 9) | ((val16 & 0x800) >> 10)
				| ((work32 & 0x800) >> 11);
		m_xx16 = work32 & 0xffff;
		setM_halfcarryF(m_halfcarryTable[idx]);
		setM_addsubtractF(false);
		setM_carryF((work32 & 0x10000) != 0);

		int work8 = m_xx16 >> 8;
		setM_3F((work8 & THREE_MASK) != 0);
		setM_5F((work8 & FIVE_MASK) != 0);

	}

	/**
	 * Add a 16-bit value to the HL register and set the appropriate flags.
	 */
	public void add_hl(int val16) {
		m_x8 = m_h8;

		int hl16 = hl16();
		int work32 = hl16 + val16;
		int idx = ((hl16 & 0x800) >> 9) | ((val16 & 0x800) >> 10)
				| ((work32 & 0x800) >> 11);
		hl16(work32 & 0xffff);
		setM_halfcarryF(m_halfcarryTable[idx]);
		setM_addsubtractF( false);
		setM_carryF((work32 & 0x10000) != 0);
		setM_3F((m_h8 & THREE_MASK) != 0);
		setM_5F((m_h8 & FIVE_MASK) != 0);

	}

	/**
	 * Add a 16-bit value, with carry, to the HL register and set the
	 * appropriate flags.
	 */
	public void adc_hl(int val16) {
		int hl16 = hl16();
		int work32 = hl16 + val16 + (m_carryF ? 1 : 0);
		int idx = ((hl16 & 0x8800) >> 9) | ((val16 & 0x8800) >> 10)
				| ((work32 & 0x8800) >> 11);
		hl16 = (work32 & 0xffff);
		hl16(hl16);
		setM_signF((hl16 & 0x8000) != 0);
		setM_zeroF(hl16 == 0);
		setM_halfcarryF(m_halfcarryTable[idx & 0x7]);
		setM_parityoverflowF(m_overflowTable[idx >> 4]);
		setM_addsubtractF( false);
		setM_carryF((work32 & 0x10000) != 0);
		setM_3F((m_h8 & THREE_MASK) != 0);
		setM_5F((m_h8 & FIVE_MASK) != 0);

	}

	/**
	 * Subtract a 16-bit value, with carry, from the HL register and set the
	 * appropriate flags.
	 */
	public void sbc_hl(int val16) {
		int hl16 = hl16();
		int work32 = hl16 - val16 - (m_carryF ? 1 : 0);
		int idx = ((hl16 & 0x8800) >> 9) | ((val16 & 0x8800) >> 10)
				| ((work32 & 0x8800) >> 11);
		hl16 = (work32 & 0xffff);
		hl16(hl16);
		setM_signF((hl16 & 0x8000) != 0);
		setM_zeroF(hl16 == 0);
		setM_halfcarryF(m_subhalfcarryTable[idx & 0x7]);
		setM_parityoverflowF(m_suboverflowTable[idx >> 4]);
		setM_addsubtractF( true);
		setM_carryF((work32 & 0x10000) != 0);
		setM_3F((m_h8 & THREE_MASK) != 0);
		setM_5F((m_h8 & FIVE_MASK) != 0);

	}

	/**
	 * Add two 16-bit values and return the 16-bit result, without setting any
	 * flags.
	 */
	public int add16(int reg16, int val16) {
		return (reg16 + val16) & 0xffff;
	}

	/**
	 * Subtract two 16-bit values and return the 16-bit result, without setting
	 * any flags.
	 */
	public int sub16(int reg16, int val16) {
		return (reg16 - val16) & 0xffff;
	}

	/**
	 * Increment a 16-bit values and return the 16-bit result, without setting
	 * any flags.
	 */
	public int inc16(int reg16) {
		return (reg16 + 1) & 0xffff;
	}

	/**
	 * Increment twice a 16-bit values and return the 16-bit result, without
	 * setting any flags.
	 */
	public int incinc16(int reg16) {
		return (reg16 + 2) & 0xffff;
	}

	/**
	 * Decrement a 16-bit values and return the 16-bit result, without setting
	 * any flags.
	 */
	public int dec16(int reg16) {
		return (reg16 - 1) & 0xffff;
	}

	/**
	 * Decrement twice a 16-bit values and return the 16-bit result, without
	 * setting any flags.
	 */
	public int decdec16(int reg16) {
		return (reg16 - 2) & 0xffff;
	}

	public void setFlagOperation(int flagId, Operation operation) {
		lastFlagOperation[flagId] = operation;
	}

	public Operation getLastFlagOperation(int flagId) {
		return lastFlagOperation[flagId];
	}

	/**
	 * Increment a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int inc8(int reg8) {
		INC8 op = new INC8();
		int work8 = op.inc8(reg8);
		return work8;
	}


	/**
	 * Decrement a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int dec8(int reg8) {
		DEC8 op = new DEC8();
		int work8 = op.dec8(reg8);

		return work8;
	}

	/**
	 * Rotate left with carry a 8-bit value and return the 8-bit result, setting
	 * the appropriate flags.
	 */
	public int rlc8(int reg8) {
		RLC8 rlc8 = new RLC8();
		return rlc8.rlc8(reg8);
	}

	/**
	 * Rotate right with carry a 8-bit value and return the 8-bit result,
	 * setting the appropriate flags.
	 */
	public int rrc8(int reg8) {
		RRC8 op = new RRC8();
		return op.rcc8(reg8);
	}

	/**
	 * Rotate left a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int rl8(int reg8) {
		boolean carry = ((reg8 & 0x80) != 0);
		int work8 = ((reg8 << 1) | (m_carryF ? 1 : 0)) & 0xff;
		setM_carryF(carry);
		shift_test(work8);

		return work8;
	}

	/**
	 * Rotate right a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int rr8(int reg8) {
		boolean carry = ((reg8 & 0x01) != 0);
		int work8 = ((reg8 >> 1) | ((m_carryF ? 1 : 0) << 7));
		setM_carryF(carry);
		shift_test(work8);

		return work8;
	}

	/**
	 * Shift left a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int sla8(int reg8) {
		setM_carryF((reg8 & 0x80) != 0);
		int work8 = (reg8 << 1) & 0xff;
		shift_test(work8);

		return work8;
	}

	/**
	 * Shift right a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int sra8(int reg8) {
		setM_carryF((reg8 & 0x01) != 0);
		int work8 = (reg8 & 0x80);
		work8 = ((reg8 >> 1) | work8);
		shift_test(work8);

		return work8;
	}

	/**
	 * Shift left with 1-pad a 8-bit value and return the 8-bit result, setting
	 * the appropriate flags.
	 */
	public int sli8(int reg8) {
		setM_carryF((reg8 & 0x80) != 0);
		int work8 = ((reg8 << 1) | 0x01) & 0xff;
		shift_test(work8);

		return work8;
	}

	/**
	 * Shift right with 1-pad a 8-bit value and return the 8-bit result, setting
	 * the appropriate flags.
	 */
	public int srl8(int reg8) {
		setM_carryF((reg8 & 0x01) != 0);
		int work8 = (reg8 >> 1);
		shift_test(work8);

		return work8;
	}

	/**
	 * Set the appropriate flags as a result of a shift operation.
	 */
	public void shift_test(int reg8) {
		ShiftTest shiftTest = new ShiftTest();
		shiftTest.shiftTest(reg8);
	}

	/**
	 * Pop the current 16-bit value at the top of the stack and return it.
	 */
	public int pop16() {
		int work16 = m_memory.read16(m_sp16);
		m_sp16 = incinc16(m_sp16);

		return work16;
	}

	/**
	 * Push the given 16-bit value on the top of the stack.
	 */
	public void push(int reg16) {
		m_sp16 = decdec16(m_sp16);
		m_memory.write16(m_sp16, reg16);
	}

	public void ld_a_special(int reg8) {
		m_a8 = reg8;
		setM_signF((m_a8 & 0x80) != 0);
		setM_zeroF(m_a8 == 0);
		setM_halfcarryF(false);
		setM_parityoverflowF(m_iff1b != 0);
		setM_addsubtractF( false);
		setM_3F((m_a8 & THREE_MASK) != 0);
		setM_5F((m_a8 & FIVE_MASK) != 0);

	}

	/**
	 * Add a 8-bit value to the A register and set the appropriate flags.
	 */
	public void add_a(int val8) {
		int work16 = m_a8 + val8;
		int idx = ((m_a8 & 0x88) >> 1) | ((val8 & 0x88) >> 2)
				| ((work16 & 0x88) >> 3);
		m_a8 = work16 & 0xff;
		setM_signF((m_a8 & 0x80) != 0);
		setM_zeroF(m_a8 == 0);
		setM_halfcarryF(m_halfcarryTable[idx & 0x7]);
		setM_parityoverflowF(m_overflowTable[idx >> 4]);
		setM_addsubtractF( false);
		setM_carryF((work16 & 0x100) != 0);
		setM_3F((m_a8 & THREE_MASK) != 0);
		setM_5F((m_a8 & FIVE_MASK) != 0);
	}

	/**
	 * Add with carry a 8-bit value to the A register and set the appropriate
	 * flags.
	 */
	public void adc_a(int val8) {
		int work16 = m_a8 + val8 + (m_carryF ? 1 : 0);
		int idx = ((m_a8 & 0x88) >> 1) | ((val8 & 0x88) >> 2)
				| ((work16 & 0x88) >> 3);
		m_a8 = work16 & 0xff;
		setM_signF((m_a8 & 0x80) != 0);
		setM_zeroF(m_a8 == 0);
		setM_halfcarryF(m_halfcarryTable[idx & 0x7]);
		setM_parityoverflowF(m_overflowTable[idx >> 4]);
		setM_addsubtractF( false);
		setM_carryF((work16 & 0x100) != 0);
		setM_3F((m_a8 & THREE_MASK) != 0);
		setM_5F((m_a8 & FIVE_MASK) != 0);
	}

	/**
	 * Subtract a 8-bit value from the A register and set the appropriate flags.
	 */
	public void sub_a(int val8) {
		int work16 = m_a8 - val8;
		int idx = ((m_a8 & 0x88) >> 1) | ((val8 & 0x88) >> 2)
				| ((work16 & 0x88) >> 3);
		m_a8 = work16 & 0xff;
		setM_signF((m_a8 & 0x80) != 0);
		setM_zeroF(m_a8 == 0);
		setM_halfcarryF(m_subhalfcarryTable[idx & 0x7]);
		setM_parityoverflowF(m_suboverflowTable[idx >> 4]);
		setM_addsubtractF( true);
		setM_carryF((work16 & 0x100) != 0);
		setM_3F((m_a8 & THREE_MASK) != 0);
		setM_5F((m_a8 & FIVE_MASK) != 0);
	}

	/**
	 * Subtract with carry a 8-bit value from the A register and set the
	 * appropriate flags.
	 */
	public void sbc_a(int val8) {
		int work16 = m_a8 - val8 - (m_carryF ? 1 : 0);
		int idx = ((m_a8 & 0x88) >> 1) | ((val8 & 0x88) >> 2)
				| ((work16 & 0x88) >> 3);
		m_a8 = work16 & 0xff;
		setM_signF((m_a8 & 0x80) != 0);
		setM_zeroF(m_a8 == 0);
		setM_halfcarryF(m_subhalfcarryTable[idx & 0x7]);
		setM_parityoverflowF(m_suboverflowTable[idx >> 4]);
		setM_addsubtractF( true);
		setM_carryF((work16 & 0x100) != 0);
		setM_3F((m_a8 & THREE_MASK) != 0);
		setM_5F((m_a8 & FIVE_MASK) != 0);
	}

	/**
	 * And a 8-bit value to the A register and set the appropriate flags.
	 */
	public void and_a(int val8) {
		m_a8 &= val8;
		setM_signF((m_a8 & 0x80) != 0);
		setM_zeroF(m_a8 == 0);
		setM_halfcarryF(true);
		setM_parityoverflowF(m_parityTable[m_a8]);
		setM_addsubtractF( false);
		setM_carryF(false);
		setM_3F((m_a8 & THREE_MASK) != 0);
		setM_5F((m_a8 & FIVE_MASK) != 0);
	}

	/**
	 * Xor a 8-bit value to the A register and set the appropriate flags.
	 */
	public void xor_a(int val8) {
		m_a8 ^= val8;
		setM_signF((m_a8 & 0x80) != 0);
		setM_zeroF(m_a8 == 0);
		setM_halfcarryF(false);
		setM_parityoverflowF(m_parityTable[m_a8]);
		setM_addsubtractF( false);
		setM_carryF(false);
		setM_3F((m_a8 & THREE_MASK) != 0);
		setM_5F((m_a8 & FIVE_MASK) != 0);
	}

	/**
	 * Or a 8-bit value to the A register and set the appropriate flags.
	 */
	public void or_a(int val8) {
		m_a8 |= val8;
		setM_signF((m_a8 & 0x80) != 0);
		setM_zeroF(m_a8 == 0);
		setM_halfcarryF(false);
		setM_parityoverflowF(m_parityTable[m_a8]);
		setM_addsubtractF( false);
		setM_carryF(false);
		setM_3F((m_a8 & THREE_MASK) != 0);
		setM_5F((m_a8 & FIVE_MASK) != 0);
	}

	/**
	 * Compare a 8-bit value to the A register and set the appropriate flags.
	 */
	public void cmp_a(int val8) {
		int work16 = m_a8 - val8;
		int idx = ((m_a8 & 0x88) >> 1) | ((val8 & 0x88) >> 2)
				| ((work16 & 0x88) >> 3);
		setM_signF((work16 & 0x80) != 0);
		setM_zeroF((work16 & 0xff) == 0);
		setM_halfcarryF(m_subhalfcarryTable[idx & 0x7]);
		setM_parityoverflowF(m_suboverflowTable[idx >> 4]);
		setM_addsubtractF( true);
		setM_carryF((work16 & 0x0100) != 0);
		setM_3F((val8 & THREE_MASK) != 0);
		setM_5F((val8 & FIVE_MASK) != 0);
	}

	/**
	 * Compare a 8-bit value to the A register and set the appropriate flags,
	 * except PARITY and CARRY.
	 */
	public void cmp_a_special(int val8) {
		int work16 = m_a8 - val8;
		int idx = ((m_a8 & 0x88) >> 1) | ((val8 & 0x88) >> 2)
				| ((work16 & 0x88) >> 3);
		setM_signF((work16 & 0x80) != 0);
		setM_zeroF((work16 & 0xff) == 0);
		setM_halfcarryF(m_subhalfcarryTable[idx & 0x7]);
		setM_addsubtractF( true);
	}

	/**
	 * Test the given bit in a byte and set the appropriate flags.
	 */
	public void bit(int bit3, int reg8) {
		setM_zeroF((reg8 & (0x01 << bit3)) == 0);
		setM_halfcarryF(true);
		setM_halfcarryF(m_zeroF);
		setM_addsubtractF( false);
		setM_signF((reg8 & (0x01 << bit3)) == 0x80);
		setM_3F(bit3 == 3 && !m_zeroF);
		setM_5F(bit3 == 5 && !m_zeroF);
	}

	public void bit_hl(int bit3, int val8) {
		setM_zeroF((val8 & (0x01 << bit3)) == 0);
		setM_halfcarryF(true);
		setM_parityoverflowF(m_zeroF);
		setM_addsubtractF( false);
		setM_signF((val8 & (0x01 << bit3)) == 0x80);
		setM_3F((m_x8 & THREE_MASK) != 0);
		setM_5F((m_x8 & FIVE_MASK) != 0);
	}

	public void bit_xx(int bit3, int val8) {
		setM_zeroF((val8 & (0x01 << bit3)) == 0);
		setM_halfcarryF(true);
		setM_parityoverflowF(m_zeroF);
		setM_addsubtractF( false);
		setM_signF((val8 & (0x01 << bit3)) == 0x80);
		setM_3F((xx16high8() & THREE_MASK) != 0);
		setM_5F((xx16high8() & FIVE_MASK) != 0);
	}

	/**
	 * Read an 8-bit value from a 16-bit I/O port and return it.
	 */
	public int in8(int port16) {
		int work8 = m_io.in8(port16);
		setM_signF((work8 & 0x80) != 0);
		setM_zeroF(work8 == 0);
		setM_halfcarryF(false);
		setM_parityoverflowF(m_parityTable[work8]);
		setM_addsubtractF( false);
		setM_3F((work8 & THREE_MASK) != 0);
		setM_5F((work8 & FIVE_MASK) != 0);

		return work8;
	}

	/**
	 * M1 cycle: fetch an instruction and increment the PC register.
	 * <P>
	 * The R register is a counter that is updated every instruction, where DD,
	 * FD, ED and CB are to be regarded as separate instructions. So shifted
	 * instruction will increase R by two. There's an interesting exception:
	 * doubly-shifted opcodes, the DDCB and FDCB ones, increase R by two too.
	 * LDI increases R by two, LDIR increases it by 2 times BC, as does LDDR
	 * etcetera. The sequence LD R,A/LD A,R increases A by two, except for the
	 * highest bit: this bit of the R register is never changed.
	 */
	//was private
	public int mone8() {
		m_r8 = (m_r8 & 0x80) | ((m_r8 + 1) & 0x7f);

		return m_memory.read8(inc16pc());
	}

	/**
	 * Stop the emulation (asynchronous.)
	 */
	public void stop() {
		m_stop = true;
	}

	/**
	 * Pause the emulation (asynchronous).
	 */
	public void pause() {
		synchronized (this) {
			m_pause = true;
			notifyAll();
		}
	}

	/**
	 * Unpause the emulation (asynchronous).
	 */
	public void unpause() {
		synchronized (this) {
			m_pause = false;
			notifyAll();
		}
	}

	{
		Instruction.setCPU(this);
		Operation.setCpu(this);
	}


	private void updateRefreshRegister(){
		m_r8 = (m_r8 & 0x80) | ((m_r8 + 1) & 0x7f);
	}


	public void emulate() {
		final CyclicBarrier barrier = new CyclicBarrier(1);
		final Object lock = new Object();
		asyncUpdate(barrier, lock);
		asyncEmulate(barrier, lock);
	}

	private AtomicBoolean isUpdateDone = new AtomicBoolean(false);

	private void asyncUpdate(final CyclicBarrier barrier, final Object lock) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {

					//WARNING: ACTIVE WAIT FOR THE EMULATE TO FINISH 
					//[this should not happen but we never know..]
					// while(isUpdateDone.get()==true);
					//I THINK THAT ACTUALLY THIS SYNC IS NOT NEEDED BECAUSE IN THE WORST CASE WE UPDATE
					//MORE TIMES THAN WHAT WE NEED AND THE RISK PAYS OFF.

					m_spectrum.update();

					isUpdateDone.set(true);

				}
			}
		}).start();
	}

	private void asyncEmulate(final CyclicBarrier barrier, final Object lock) {
		while (true) {
			PerformanceCounter.start("emulate");
			emulateOne();

			if (m_stop) {
				break;
			}

			while (m_pause) {
				pauseMode();
			}

			//WARNING: ACTIVE WAIT FOR THE UPDATE TO FINISH
			PerformanceCounter.start("emulate - WAIT");

			while(!isUpdateDone.getAndSet(false));

			PerformanceCounter.end("emulate - WAIT");
			PerformanceCounter.end("emulate");

		}
	}

	private void emulateOne(){
		updateRefreshRegister();
		Instruction instruction;
		int opcode = m_memory.read8(m_pc16);
		instruction = instructionTable[opcode];
		inc16pc();
		instruction.execute();
		m_tstates += instruction.incTstates();
	}

	/**
	 * Return a string representation of the CPU state, which is useful for
	 * debugging purposes.
	 */
	public String toString() {
		storeFlags();

		return "A=" + m_a8 + ",F=" + m_f8 + ",B=" + m_b8 + ",C=" + m_c8 + ",D="
		+ m_d8 + ",E=" + m_e8 + ",H=" + m_h8 + ",L=" + m_l8 + ",AF1="
		+ m_af16alt + ",BC1=" + m_bc16alt + ",DE1=" + m_de16alt
		+ ",HL1=" + m_hl16alt + ",IX=" + m_ix16 + ",IY=" + m_iy16
		+ ",SP=" + m_sp16 + ",PC=" + m_pc16 + ",R=" + m_r8 + ",I="
		+ m_i8 + ",IM=" + m_im2 + ",IFF1=" + m_iff1a + ",IFF2="
		+ m_iff1b + ",OP=" + m_memory.read8(m_pc16);
	}

	/**
	 * Load the CPU contents from the loader object.
	 */
	public void load(BaseLoader loader) {
		af16(loader.getAF16());
		bc16(loader.getBC16());
		de16(loader.getDE16());
		hl16(loader.getHL16());
		m_af16alt = loader.getAF16ALT();
		m_bc16alt = loader.getBC16ALT();
		m_de16alt = loader.getDE16ALT();
		m_hl16alt = loader.getHL16ALT();
		m_ix16 = loader.getIX16();
		m_iy16 = loader.getIY16();
		m_sp16 = loader.getSP16();
		m_pc16 = loader.getPC16();
		m_r8 = loader.getR8();
		m_i8 = loader.getI8();
		m_im2 = loader.getIM2();
		m_iff1a = loader.getIFF1a();
		m_iff1b = loader.getIFF1b();

		retrieveFlags();
	}

	private void pauseMode() {
		try {
			wait();
		} catch (InterruptedException ie) {
			m_logger.log(ILogger.C_ERROR, ie);
		}
	}
}
