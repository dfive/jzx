package pt.ist.ave.jzx;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

import pt.ist.ave.jzx.instructions.Instruction;
import pt.ist.ave.jzx.instructions.InstructionFactory;
import pt.ist.ave.jzx.operations.ADC_A;
import pt.ist.ave.jzx.operations.ADC_HL;
import pt.ist.ave.jzx.operations.ADD_A;
import pt.ist.ave.jzx.operations.ADD_HL;
import pt.ist.ave.jzx.operations.ADD_XX;
import pt.ist.ave.jzx.operations.AND_A_OPERATION;
import pt.ist.ave.jzx.operations.BIT_HL;
import pt.ist.ave.jzx.operations.BIT_Operation;
import pt.ist.ave.jzx.operations.BIT_XX;
import pt.ist.ave.jzx.operations.CMP_A;
import pt.ist.ave.jzx.operations.CMP_A_SPECIAL;
import pt.ist.ave.jzx.operations.DEC8;
import pt.ist.ave.jzx.operations.DefaultOperation;
import pt.ist.ave.jzx.operations.IN8;
import pt.ist.ave.jzx.operations.INC8;
import pt.ist.ave.jzx.operations.LD_A_SPECIAL;
import pt.ist.ave.jzx.operations.OR_A_Operation;
import pt.ist.ave.jzx.operations.Operation;
import pt.ist.ave.jzx.operations.RL8;
import pt.ist.ave.jzx.operations.RLC8;
import pt.ist.ave.jzx.operations.RR8;
import pt.ist.ave.jzx.operations.RRC8;
import pt.ist.ave.jzx.operations.RetrieveFlagsOperation;
import pt.ist.ave.jzx.operations.SBC_A;
import pt.ist.ave.jzx.operations.SBC_HL;
import pt.ist.ave.jzx.operations.SLA8;
import pt.ist.ave.jzx.operations.SLI8;
import pt.ist.ave.jzx.operations.SRA8;
import pt.ist.ave.jzx.operations.SRL8;
import pt.ist.ave.jzx.operations.SUB_A;
import pt.ist.ave.jzx.operations.XOR_A_Operation;

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
			lastFlagOperation[i] = new DefaultOperation();
		}
	}

	/**
	 * Explicit flags, which represent the bits in the F register as distinct
	 * variables.
	 * 
	 * @see #storeFlags
	 * @see #retrieveFlags
	 */

	/**
	 * Last operation performed that refreshed flag values. Access this variable
	 * to get the value of the pretended flag.
	 *   
	 * @see #storeFlags
	 * @see #retrieveFlags
	 */


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

	/****************************************************************************
	 *																			*
	 * 								FLAGS										*
	 * 																			*
	 ****************************************************************************/

	public void setFlagOperation(int flagId, Operation operation) {
		//		System.out.println("setFlag [flag: " + flag + ", operation: " + operation + "]");

		lastFlagOperation[flagId] = operation;
	}

	public Operation getLastFlagOperation(int flagId) {
		return lastFlagOperation[flagId];
	}

	/**
	 * @return the m_carryF
	 */
	public boolean getM_carryF() {
		return lastFlagOperation[FLAG_CARRY].getM_carryF();
	}

	/**
	 * @return the m_addsubtractF
	 */
	public boolean getM_addsubtractF() {
		return lastFlagOperation[FLAG_ADD_SUBTRACT].getM_addsubtractF();
	}

	/**
	 * @return the m_parityoverflowF
	 */
	public boolean getM_parityoverflowF() {
		return lastFlagOperation[FLAG_PARITY_OVERFLOW].getM_parityoverflowF();
	}

	/**
	 * @return the m_halfcarryF
	 */
	public boolean getM_halfcarryF() {
		return lastFlagOperation[FLAG_HALF_CARRY].getM_halfcarryF();
	}

	/**
	 * @return the m_zeroF
	 */
	public boolean getM_zeroF() {
		return lastFlagOperation[FLAG_ZERO].getM_zeroF();
	}

	/**
	 * @return the m_signF
	 */
	public boolean getM_signF() {
		return lastFlagOperation[FLAG_SIGN].getM_signF();
	}

	/**
	 * @return the m_5F
	 */
	public boolean getM_5F() {
		return lastFlagOperation[FLAG_5].getM_5F();
	}

	/**
	 * @return the m_3F
	 */
	public boolean getM_3F() {
		return lastFlagOperation[FLAG_3].getM_3F();
	}

	/****************************************************************************
	 *																			*
	 * 								FLAGS - end									*
	 * 																			*
	 ****************************************************************************/

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

//	private static final int[] instructionCounter = new int[255];

//	private static long instrs = 0;

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
		RetrieveFlagsOperation op = new RetrieveFlagsOperation();
		op.retrieveFlags();
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
		ADD_XX operation = new ADD_XX();
		operation.add_xx(val16);
	}

	/**
	 * Add a 16-bit value to the HL register and set the appropriate flags.
	 */
	public void add_hl(int val16) {
		ADD_HL op = new ADD_HL();
		op.add_hl(val16);
	}

	/**
	 * Add a 16-bit value, with carry, to the HL register and set the
	 * appropriate flags.
	 */
	public void adc_hl(int val16) {
		ADC_HL op = new ADC_HL();
		op.adc_hl(val16);
	}

	/**
	 * Subtract a 16-bit value, with carry, from the HL register and set the
	 * appropriate flags.
	 */
	public void sbc_hl(int val16) {
		SBC_HL op = new SBC_HL();
		op.sbc_hl(val16);
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



	/**
	 * Increment a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int inc8(int reg8) {
		INC8 op = new INC8();
		return op.inc8(reg8);
	}


	/**
	 * Decrement a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int dec8(int reg8) {
		DEC8 op = new DEC8();
		return op.dec8(reg8);
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
		RL8 op = new RL8();
		return op.rl8(reg8);
	}

	/**
	 * Rotate right a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int rr8(int reg8) {
		RR8 op = new RR8();
		return op.rr8(reg8);
	}

	/**
	 * Shift left a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int sla8(int reg8) {
		SLA8 op = new SLA8();
		return op.sla8(reg8);
	}

	/**
	 * Shift right a 8-bit value and return the 8-bit result, setting the
	 * appropriate flags.
	 */
	public int sra8(int reg8) {
		SRA8 op = new SRA8();
		return op.sra8(reg8);
	}

	/**
	 * Shift left with 1-pad a 8-bit value and return the 8-bit result, setting
	 * the appropriate flags.
	 */
	public int sli8(int reg8) {
		SLI8 op = new SLI8();
		return op.sli8(reg8);
	}

	/**
	 * Shift right with 1-pad a 8-bit value and return the 8-bit result, setting
	 * the appropriate flags.
	 */
	public int srl8(int reg8) {
		SRL8 op = new SRL8();
		return op.srl8(reg8);
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
		LD_A_SPECIAL op = new LD_A_SPECIAL();
		op.ld_a_special(reg8);
	}

	/**
	 * Add a 8-bit value to the A register and set the appropriate flags.
	 */
	public void add_a(int val8) {
		ADD_A op = new ADD_A();
		op.add_a(val8);
	}

	/**
	 * Add with carry a 8-bit value to the A register and set the appropriate
	 * flags.
	 */
	public void adc_a(int val8) {
		ADC_A op = new ADC_A();
		op.adc_a(val8);
	}

	/**
	 * Subtract a 8-bit value from the A register and set the appropriate flags.
	 */
	public void sub_a(int val8) {
		SUB_A op = new SUB_A();
		op.sub_a(val8);
	}

	/**
	 * Subtract with carry a 8-bit value from the A register and set the
	 * appropriate flags.
	 */
	public void sbc_a(int val8) {
		SBC_A op = new SBC_A();
		op.sbc_a(val8);
	}

	/**
	 * And a 8-bit value to the A register and set the appropriate flags.
	 */
	public void and_a(int val8) {
		AND_A_OPERATION op = new AND_A_OPERATION();
		op.and_a(val8);
	}

	/**
	 * Xor a 8-bit value to the A register and set the appropriate flags.
	 */
	public void xor_a(int val8) {
		XOR_A_Operation op = new XOR_A_Operation();
		op.xor_a(val8);
	}

	/**
	 * Or a 8-bit value to the A register and set the appropriate flags.
	 */
	public void or_a(int val8) {
		OR_A_Operation op = new OR_A_Operation();
		op.or_a(val8);
	}

	/**
	 * Compare a 8-bit value to the A register and set the appropriate flags.
	 */
	public void cmp_a(int val8) {
		CMP_A op = new CMP_A();
		op.cmp_a(val8);
	}

	/**
	 * Compare a 8-bit value to the A register and set the appropriate flags,
	 * except PARITY and CARRY.
	 */
	public void cmp_a_special(int val8) {
		CMP_A_SPECIAL op = new CMP_A_SPECIAL();
		op.cmp_a_special(val8);
	}

	/**
	 * Test the given bit in a byte and set the appropriate flags.
	 */
	public void bit(int bit3, int reg8) {
		BIT_Operation op = new BIT_Operation();
		op.bit(bit3, reg8);
	}

	public void bit_hl(int bit3, int val8) {
		BIT_HL op = new BIT_HL();
		op.bit_hl(bit3, val8);
	}

	public void bit_xx(int bit3, int val8) {
		BIT_XX op = new BIT_XX();
		op.bit_xx(bit3, val8);
	}

	/**
	 * Read an 8-bit value from a 16-bit I/O port and return it.
	 */
	public int in8(int port16) {
		IN8 op = new IN8();
		return op.in8(port16);
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
			//			PerformanceCounter.start("emulate");
			emulateOne();

			if (m_stop) {
				break;
			}

			while (m_pause) {
				pauseMode();
			}

			//WARNING: ACTIVE WAIT FOR THE UPDATE TO FINISH
			//			PerformanceCounter.start("emulate - WAIT");

			while(!isUpdateDone.getAndSet(false));

			//			PerformanceCounter.end("emulate - WAIT");
			//			PerformanceCounter.end("emulate");

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
