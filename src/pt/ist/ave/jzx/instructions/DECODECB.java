package pt.ist.ave.jzx.instructions;

//special instruction
/**
 * Decode the instructions whose first opcode is 0xCB.
 */
public class DECODECB extends Instruction {

	public DECODECB(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int op8 = _cpu.mone8();
		int work8 = 0;
		
		switch (op8) {
		
		/* rlc b */
		case 0x00:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.rlc8(_cpu.getM_b8()));
			break;

			/* rlc c */
		case 0x01:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.rlc8(_cpu.getM_c8()));
			break;

			/* rlc d */
		case 0x02:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.rlc8(_cpu.getM_d8()));
			break;

			/* rlc e */
		case 0x03:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.rlc8(_cpu.getM_e8()));
			break;

			/* rlc h */
		case 0x04:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.rlc8(_cpu.getM_h8()));
			break;

			/* rlc l */
		case 0x05:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.rlc8(_cpu.getM_l8()));
			break;

			/* rlc (hl) */
		case 0x06:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			work8 = _cpu.rlc8(work8);
			_cpu.getM_memory().write8(_cpu.hl16(), work8);
			break;

			/* rlc a */
		case 0x07:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.rlc8(_cpu.getM_a8()));
			break;

			/* rrc b */
		case 0x08:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.rrc8(_cpu.getM_b8()));
			break;

			/* rrc c */
		case 0x09:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.rrc8(_cpu.getM_c8()));
			break;

			/* rrc d */
		case 0x0A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.rrc8(_cpu.getM_d8()));
			break;

			/* rrc e */
		case 0x0B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.rrc8(_cpu.getM_e8()));
			break;

			/* rrc h */
		case 0x0C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.rrc8(_cpu.getM_h8()));
			break;

			/* rrc l */
		case 0x0D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.rrc8(_cpu.getM_l8()));
			break;

			/* rrc (hl) */
		case 0x0E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			work8 = _cpu.rrc8(work8);
			_cpu.getM_memory().write8(_cpu.hl16(), work8);
			break;

			/* rrc a */
		case 0x0F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.rrc8(_cpu.getM_a8()));
			break;

			/* rl b */
		case 0x10:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.rl8(_cpu.getM_b8()));
			break;

			/* rl c */
		case 0x11:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.rl8(_cpu.getM_c8()));
			break;

			/* rl d */
		case 0x12:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.rl8(_cpu.getM_d8()));
			break;

			/* rl e */
		case 0x13:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.rl8(_cpu.getM_e8()));
			break;

			/* rl h */
		case 0x14:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.rl8(_cpu.getM_h8()));
			break;

			/* rl l */
		case 0x15:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.rl8(_cpu.getM_l8()));
			break;

			/* rl (hl) */
		case 0x16:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			work8 = _cpu.rl8(work8);
			_cpu.getM_memory().write8(_cpu.hl16(), work8);
			break;

			/* rl a */
		case 0x17:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.rl8(_cpu.getM_a8()));
			break;

			/* rr b */
		case 0x18:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.rr8(_cpu.getM_b8()));
			break;

			/* rr c */
		case 0x19:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.rr8(_cpu.getM_c8()));
			break;

			/* rr d */
		case 0x1A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.rr8(_cpu.getM_d8()));
			break;

			/* rr e */
		case 0x1B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.rr8(_cpu.getM_e8()));
			break;

			/* rr h */
		case 0x1C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.rr8(_cpu.getM_h8()));
			break;

			/* rr l */
		case 0x1D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.rr8(_cpu.getM_l8()));
			break;

			/* rr (hl) */
		case 0x1E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			work8 = _cpu.rr8(work8);
			_cpu.getM_memory().write8(_cpu.hl16(), work8);
			break;

			/* rr a */
		case 0x1F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.rr8(_cpu.getM_a8()));
			break;

			/* sla b */
		case 0x20:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.sla8(_cpu.getM_b8()));
			break;

			/* sla c */
		case 0x21:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.sla8(_cpu.getM_c8()));
			break;

			/* sla d */
		case 0x22:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.sla8(_cpu.getM_d8()));
			break;

			/* sla e */
		case 0x23:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.sla8(_cpu.getM_e8()));
			break;

			/* sla h */
		case 0x24:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.sla8(_cpu.getM_h8()));
			break;

			/* sla l */
		case 0x25:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.sla8(_cpu.getM_l8()));
			break;

			/* sla (hl) */
		case 0x26:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			work8 = _cpu.sla8(work8);
			_cpu.getM_memory().write8(_cpu.hl16(), work8);
			break;

			/* sla a */
		case 0x27:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.sla8(_cpu.getM_a8()));
			break;

			/* sra b */
		case 0x28:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.sra8(_cpu.getM_b8()));
			break;

			/* sra c */
		case 0x29:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.sra8(_cpu.getM_c8()));
			break;

			/* sra d */
		case 0x2A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.sra8(_cpu.getM_d8()));
			break;

			/* sra e */
		case 0x2B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.sra8(_cpu.getM_e8()));
			break;

			/* sra h */
		case 0x2C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.sra8(_cpu.getM_h8()));
			break;

			/* sra l */
		case 0x2D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.sra8(_cpu.getM_l8()));
			break;

			/* sra (hl) */
		case 0x2E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			work8 = _cpu.sra8(work8);
			_cpu.getM_memory().write8(_cpu.hl16(), work8);
			break;

			/* sra a */
		case 0x2F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.sra8(_cpu.getM_a8()));
			break;

			/**
			 * Undocumented instructions.
			 * 
			 * These instructions shift left the operand and make bit 0 always one.
			 */

			/* sli b */
		case 0x30:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.sli8(_cpu.getM_b8()));
			break;

			/* sli c */
		case 0x31:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.sli8(_cpu.getM_c8()));
			break;

			/* sli d */
		case 0x32:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.sli8(_cpu.getM_d8()));
			break;

			/* sli e */
		case 0x33:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.sli8(_cpu.getM_e8()));
			break;

			/* sli h */
		case 0x34:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.sli8(_cpu.getM_h8()));
			break;

			/* sli l */
		case 0x35:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.sli8(_cpu.getM_l8()));
			break;

			/* sli (hl) */
		case 0x36:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			work8 = _cpu.sli8(work8);
			_cpu.getM_memory().write8(_cpu.hl16(), work8);
			break;

			/* sli a */
		case 0x37:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.sli8(_cpu.getM_a8()));
			break;

			/* srl b */
		case 0x38:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.srl8(_cpu.getM_b8()));
			break;

			/* srl c */
		case 0x39:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.srl8(_cpu.getM_c8()));
			break;

			/* srl d */
		case 0x3A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.srl8(_cpu.getM_d8()));
			break;

			/* srl e */
		case 0x3B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.srl8(_cpu.getM_e8()));
			break;

			/* srl h */
		case 0x3C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.srl8(_cpu.getM_h8()));
			break;

			/* srl l */
		case 0x3D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.srl8(_cpu.getM_l8()));
			break;

			/* srl (hl) */
		case 0x3E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			work8 = _cpu.srl8(work8);
			_cpu.getM_memory().write8(_cpu.hl16(), work8);
			break;

			/* srl a */
		case 0x3F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.srl8(_cpu.getM_a8()));
			break;

			/* bit 0,b */
		case 0x40:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(0, _cpu.getM_b8());
			break;

			/* bit 0,c */
		case 0x41:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(0, _cpu.getM_c8());
			break;

			/* bit 0,d */
		case 0x42:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(0, _cpu.getM_d8());
			break;

			/* bit 0,e */
		case 0x43:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(0, _cpu.getM_e8());
			break;

			/* bit 0,h */
		case 0x44:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(0, _cpu.getM_h8());
			break;

			/* bit 0,l */
		case 0x45:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(0, _cpu.getM_l8());
			break;

			/* bit 0,(hl) */
		case 0x46:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.bit_hl(0, _cpu.getM_memory().read8(_cpu.hl16()));
			break;

			/* bit 0,a */
		case 0x47:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(0, _cpu.getM_a8());
			break;

			/* bit 1,b */
		case 0x48:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(1, _cpu.getM_b8());
			break;

			/* bit 1,c */
		case 0x49:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(1, _cpu.getM_c8());
			break;

			/* bit 1,d */
		case 0x4A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(1, _cpu.getM_d8());
			break;

			/* bit 1,e */
		case 0x4B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(1, _cpu.getM_e8());
			break;

			/* bit 1,h */
		case 0x4C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(1, _cpu.getM_h8());
			break;

			/* bit 1,l */
		case 0x4D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(1, _cpu.getM_l8());
			break;

			/* bit 1,(hl) */
		case 0x4E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.bit_hl(1, _cpu.getM_memory().read8(_cpu.hl16()));
			break;

			/* bit 1,a */
		case 0x4F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(1, _cpu.getM_a8());
			break;

			/* bit 2,b */
		case 0x50:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(2, _cpu.getM_b8());
			break;

			/* bit 2,c */
		case 0x51:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(2, _cpu.getM_c8());
			break;

			/* bit 2,d */
		case 0x52:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(2, _cpu.getM_d8());
			break;

			/* bit 2,e */
		case 0x53:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(2, _cpu.getM_e8());
			break;

			/* bit 2,h */
		case 0x54:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(2, _cpu.getM_h8());
			break;

			/* bit 2,l */
		case 0x55:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(2, _cpu.getM_l8());
			break;

			/* bit 2,(hl) */
		case 0x56:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.bit_hl(2, _cpu.getM_memory().read8(_cpu.hl16()));
			break;

			/* bit 2,a */
		case 0x57:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(2, _cpu.getM_a8());
			break;

			/* bit 3,b */
		case 0x58:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(3, _cpu.getM_b8());
			break;

			/* bit 3,c */
		case 0x59:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(3, _cpu.getM_c8());
			break;

			/* bit 3,d */
		case 0x5A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(3, _cpu.getM_d8());
			break;

			/* bit 3,e */
		case 0x5B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(3, _cpu.getM_e8());
			break;

			/* bit 3,h */
		case 0x5C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(3, _cpu.getM_h8());
			break;

			/* bit 3,l */
		case 0x5D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(3, _cpu.getM_l8());
			break;

			/* bit 3,(hl) */
		case 0x5E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.bit_hl(3, _cpu.getM_memory().read8(_cpu.hl16()));
			break;

			/* bit 3,a */
		case 0x5F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(3, _cpu.getM_a8());
			break;

			/* bit 4,b */
		case 0x60:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(4, _cpu.getM_b8());
			break;

			/* bit 4,c */
		case 0x61:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(4, _cpu.getM_c8());
			break;

			/* bit 4,d */
		case 0x62:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(4, _cpu.getM_d8());
			break;

			/* bit 4,e */
		case 0x63:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(4, _cpu.getM_e8());
			break;

			/* bit 4,h */
		case 0x64:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(4, _cpu.getM_h8());
			break;

			/* bit 4,l */
		case 0x65:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(4, _cpu.getM_l8());
			break;

			/* bit 4,(hl) */
		case 0x66:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.bit_hl(4, _cpu.getM_memory().read8(_cpu.hl16()));
			break;

			/* bit 4,a */
		case 0x67:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(4, _cpu.getM_a8());
			break;

			/* bit 5,b */
		case 0x68:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(5, _cpu.getM_b8());
			break;

			/* bit 5,c */
		case 0x69:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(5, _cpu.getM_c8());
			break;

			/* bit 5,d */
		case 0x6A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(5, _cpu.getM_d8());
			break;

			/* bit 5,e */
		case 0x6B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(5, _cpu.getM_e8());
			break;

			/* bit 5,h */
		case 0x6C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(5, _cpu.getM_h8());
			break;

			/* bit 5,l */
		case 0x6D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(5, _cpu.getM_l8());
			break;

			/* bit 5,(hl) */
		case 0x6E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.bit_hl(5, _cpu.getM_memory().read8(_cpu.hl16()));
			break;

			/* bit 5,a */
		case 0x6F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(5, _cpu.getM_a8());
			break;

			/* bit 6,b */
		case 0x70:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(6, _cpu.getM_b8());
			break;

			/* bit 6,c */
		case 0x71:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(6, _cpu.getM_c8());
			break;

			/* bit 6,d */
		case 0x72:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(6, _cpu.getM_d8());
			break;

			/* bit 6,e */
		case 0x73:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(6, _cpu.getM_e8());
			break;

			/* bit 6,h */
		case 0x74:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(6, _cpu.getM_h8());
			break;

			/* bit 6,l */
		case 0x75:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(6, _cpu.getM_l8());
			break;

			/* bit 6,(hl) */
		case 0x76:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.bit_hl(6, _cpu.getM_memory().read8(_cpu.hl16()));
			break;

			/* bit 6,a */
		case 0x77:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(6, _cpu.getM_a8());
			break;

			/* bit 7,b */
		case 0x78:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(7, _cpu.getM_b8());
			break;

			/* bit 7,c */
		case 0x79:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(7, _cpu.getM_c8());
			break;

			/* bit 7,d */
		case 0x7A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(7, _cpu.getM_d8());
			break;

			/* bit 7,e */
		case 0x7B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(7, _cpu.getM_e8());
			break;

			/* bit 7,h */
		case 0x7C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(7, _cpu.getM_h8());
			break;

			/* bit 7,l */
		case 0x7D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(7, _cpu.getM_l8());
			break;

			/* bit 7,(hl) */
		case 0x7E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.bit_hl(7, _cpu.getM_memory().read8(_cpu.hl16()));
			break;

			/* bit 7,a */
		case 0x7F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.bit(7, _cpu.getM_a8());
			break;

			/* res 0,b */
		case 0x80:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() & 0xfe);
			break;

			/* res 0,c */
		case 0x81:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() & 0xfe);
			break;

			/* res 0,d */
		case 0x82:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() & 0xfe);
			break;

			/* res 0,e */
		case 0x83:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() & 0xfe);
			break;

			/* res 0,h */
		case 0x84:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() & 0xfe);
			break;

			/* res 0,l */
		case 0x85:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() & 0xfe);
			break;

			/* res 0,(hl) */
		case 0x86:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) & 0xfe));
			break;

			/* res 0,a */
		case 0x87:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() & 0xfe);
			break;

			/* res 1,b */
		case 0x88:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() & 0xfd);
			break;

			/* res 1,c */
		case 0x89:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() & 0xfd);
			break;

			/* res 1,d */
		case 0x8A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() & 0xfd);
			break;

			/* res 1,e */
		case 0x8B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() & 0xfd);
			break;

			/* res 1,h */
		case 0x8C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() & 0xfd);
			break;

			/* res 1,l */
		case 0x8D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() & 0xfd);
			break;

			/* res 1,(hl) */
		case 0x8E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) & 0xfd));
			break;

			/* res 1,a */
		case 0x8F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() & 0xfd);
			break;

			/* res 2,b */
		case 0x90:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() & 0xfb);
			break;

			/* res 2,c */
		case 0x91:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() & 0xfb);
			break;

			/* res 2,d */
		case 0x92:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() & 0xfb);
			break;

			/* res 2,e */
		case 0x93:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() & 0xfb);
			break;

			/* res 2,h */
		case 0x94:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() & 0xfb);
			break;

			/* res 2,l */
		case 0x95:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() & 0xfb);
			break;

			/* res 2,(hl) */
		case 0x96:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) & 0xfb));
			break;

			/* res 2,a */
		case 0x97:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() & 0xfb);
			break;

			/* res 3,b */
		case 0x98:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() & 0xf7);
			break;

			/* res 3,c */
		case 0x99:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() & 0xf7);
			break;

			/* res 3,d */
		case 0x9A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() & 0xf7);
			break;

			/* res 3,e */
		case 0x9B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() & 0xf7);
			break;

			/* res 3,h */
		case 0x9C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() & 0xf7);
			break;

			/* res 3,l */
		case 0x9D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() & 0xf7);
			break;

			/* res 3,(hl) */
		case 0x9E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) & 0xf7));
			break;

			/* res 3,a */
		case 0x9F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() & 0xf7);
			break;

			/* res 4,b */
		case 0xA0:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() & 0xef);
			break;

			/* res 4,c */
		case 0xA1:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() & 0xef);
			break;

			/* res 4,d */
		case 0xA2:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() & 0xef);
			break;

			/* res 4,e */
		case 0xA3:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() & 0xef);
			break;

			/* res 4,h */
		case 0xA4:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() & 0xef);
			break;

			/* res 4,l */
		case 0xA5:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() & 0xef);
			break;

			/* res 4,(hl) */
		case 0xA6:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) & 0xef));
			break;

			/* res 4,a */
		case 0xA7:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() & 0xef);
			break;

			/* res 5,b */
		case 0xA8:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() & 0xdf);
			break;

			/* res 5,c */
		case 0xA9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() & 0xdf);
			break;

			/* res 5,d */
		case 0xAA:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() & 0xdf);
			break;

			/* res 5,e */
		case 0xAB:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() & 0xdf);
			break;

			/* res 5,h */
		case 0xAC:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() & 0xdf);
			break;

			/* res 5,l */
		case 0xAD:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() & 0xdf);
			break;

			/* res 5,(hl) */
		case 0xAE:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) & 0xdf));
			break;

			/* res 5,a */
		case 0xAF:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() & 0xdf);
			break;

			/* res 6,b */
		case 0xB0:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() & 0xbf);
			break;

			/* res 6,c */
		case 0xB1:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() & 0xbf);
			break;

			/* res 6,d */
		case 0xB2:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() & 0xbf);
			break;

			/* res 6,e */
		case 0xB3:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() & 0xbf);
			break;

			/* res 6,h */
		case 0xB4:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() & 0xbf);
			break;

			/* res 6,l */
		case 0xB5:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() & 0xbf);
			break;

			/* res 6,(hl) */
		case 0xB6:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) & 0xbf));
			break;

			/* res 6,a */
		case 0xB7:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() & 0xbf);
			break;

			/* res 7,b */
		case 0xB8:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() & 0x7f);
			break;

			/* res 7,c */
		case 0xB9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() & 0x7f);
			break;

			/* res 7,d */
		case 0xBA:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() & 0x7f);
			break;

			/* res 7,e */
		case 0xBB:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() & 0x7f);
			break;

			/* res 7,h */
		case 0xBC:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() & 0x7f);
			break;

			/* res 7,l */
		case 0xBD:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() & 0x7f);
			break;

			/* res 7,(hl) */
		case 0xBE:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) & 0x7f));
			break;

			/* res 7,a */
		case 0xBF:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() & 0x7f);
			break;

			/* set 0,b */
		case 0xC0:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() | 0x1);
			break;

			/* set 0,c */
		case 0xC1:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() | 0x1);
			break;

			/* set 0,d */
		case 0xC2:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() | 0x1);
			break;

			/* set 0,e */
		case 0xC3:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() | 0x1);
			break;

			/* set 0,h */
		case 0xC4:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() | 0x1);
			break;

			/* set 0,l */
		case 0xC5:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() | 0x1);
			break;

			/* set 0,(hl) */
		case 0xC6:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) | 0x1));
			break;

			/* set 0,a */
		case 0xC7:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() | 0x1);
			break;

			/* set 1,b */
		case 0xC8:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() | 0x2);
			break;

			/* set 1,c */
		case 0xC9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() | 0x2);
			break;

			/* set 1,d */
		case 0xCA:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() | 0x2);
			break;

			/* set 1,e */
		case 0xCB:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() | 0x2);
			break;

			/* set 1,h */
		case 0xCC:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() | 0x2);
			break;

			/* set 1,l */
		case 0xCD:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() | 0x2);
			break;

			/* set 1,(hl) */
		case 0xCE:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) | 0x2));
			break;

			/* set 1,a */
		case 0xCF:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() | 0x2);
			break;

			/* set 2,b */
		case 0xD0:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() | 0x4);
			break;

			/* set 2,c */
		case 0xD1:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() | 0x4);
			break;

			/* set 2,d */
		case 0xD2:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() | 0x4);
			break;

			/* set 2,e */
		case 0xD3:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() | 0x4);
			break;

			/* set 2,h */
		case 0xD4:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() | 0x4);
			break;

			/* set 2,l */
		case 0xD5:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() | 0x4);
			break;

			/* set 2,(hl) */
		case 0xD6:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) | 0x4));
			break;

			/* set 2,a */
		case 0xD7:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() | 0x4);
			break;

			/* set 3,b */
		case 0xD8:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() | 0x8);
			break;

			/* set 3,c */
		case 0xD9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() | 0x8);
			break;

			/* set 3,d */
		case 0xDA:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() | 0x8);
			break;

			/* set 3,e */
		case 0xDB:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() | 0x8);
			break;

			/* set 3,h */
		case 0xDC:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() | 0x8);
			break;

			/* set 3,l */
		case 0xDD:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() | 0x8);
			break;

			/* set 3,(hl) */
		case 0xDE:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) | 0x8));
			break;

			/* set 3,a */
		case 0xDF:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() | 0x8);
			break;

			/* set 4,b */
		case 0xE0:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() | 0x10);
			break;

			/* set 4,c */
		case 0xE1:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() | 0x10);
			break;

			/* set 4,d */
		case 0xE2:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() | 0x10);
			break;

			/* set 4,e */
		case 0xE3:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() | 0x10);
			break;

			/* set 4,h */
		case 0xE4:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() | 0x10);
			break;

			/* set 4,l */
		case 0xE5:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() | 0x10);
			break;

			/* set 4,(hl) */
		case 0xE6:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) | 0x10));
			break;

			/* set 4,a */
		case 0xE7:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() | 0x10);
			break;

			/* set 5,b */
		case 0xE8:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() | 0x20);
			break;

			/* set 5,c */
		case 0xE9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() | 0x20);
			break;

			/* set 5,d */
		case 0xEA:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() | 0x20);
			break;

			/* set 5,e */
		case 0xEB:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() | 0x20);
			break;

			/* set 5,h */
		case 0xEC:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() | 0x20);
			break;

			/* set 5,l */
		case 0xED:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() | 0x20);
			break;

			/* set 5,(hl) */
		case 0xEE:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) | 0x20));
			break;

			/* set 5,a */
		case 0xEF:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() | 0x20);
			break;

			/* set 6,b */
		case 0xF0:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() | 0x40);
			break;

			/* set 6,c */
		case 0xF1:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() | 0x40);
			break;

			/* set 6,d */
		case 0xF2:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() | 0x40);
			break;

			/* set 6,e */
		case 0xF3:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() | 0x40);
			break;

			/* set 6,h */
		case 0xF4:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() | 0x40);
			break;

			/* set 6,l */
		case 0xF5:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() | 0x40);
			break;

			/* set 6,(hl) */
		case 0xF6:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) | 0x40));
			break;

			/* set 6,a */
		case 0xF7:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() | 0x40);
			break;

			/* set 7,b */
		case 0xF8:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.getM_b8() | 0x80);
			break;

			/* set 7,c */
		case 0xF9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.getM_c8() | 0x80);
			break;

			/* set 7,d */
		case 0xFA:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.getM_d8() | 0x80);
			break;

			/* set 7,e */
		case 0xFB:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.getM_e8() | 0x80);
			break;

			/* set 7,h */
		case 0xFC:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_h8(_cpu.getM_h8() | 0x80);
			break;

			/* set 7,l */
		case 0xFD:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_l8(_cpu.getM_l8() | 0x80);
			break;

			/* set 7,(hl) */
		case 0xFE:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.getM_memory().write8(_cpu.hl16(), (_cpu.getM_memory().read8(_cpu.hl16()) | 0x80));
			break;

			/* set 7,a */
		case 0xFF:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.getM_a8() | 0x80);
			break;
		}
	}

	@Override
	public int incTstates() {
		return 0;
	}

}
