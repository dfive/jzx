package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.ILogger;
import pt.ist.ave.jzx.operations.OperationsFactory;
import pt.ist.ave.jzx.operations.CPD;
import pt.ist.ave.jzx.operations.ED_3_5;
import pt.ist.ave.jzx.operations.INI;
import pt.ist.ave.jzx.operations.LDD;
import pt.ist.ave.jzx.operations.LDDR;
import pt.ist.ave.jzx.operations.LDI;
import pt.ist.ave.jzx.operations.LDIR;
import pt.ist.ave.jzx.operations.RLD;
import pt.ist.ave.jzx.operations.RRD;

public class DECODEED extends Instruction {

	public DECODEED(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {

		/**
		 * Decode the instructions whose first opcode is 0xED.
		 */
		int work8 = 0;
		int op8 = _cpu.mone8();
		ED_3_5 ed_3_5;

		switch (op8) {
		/* in b,(c) */
		case 0x40:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.setM_b8(_cpu.in8(_cpu.bc16()));
			break;

			/* out (c),b */
		case 0x41:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_b8());
			break;

			/* sbc hl,bc */
		case 0x42:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.sbc_hl(_cpu.bc16());
			break;

			/* ld (NN),bc */
		case 0x43:
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.getM_memory().write16(_cpu.getM_memory().read16(_cpu.getM_pc16()), _cpu.bc16());
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* neg */
		case 0x44:
		case 0x4c: /* unofficial */
		case 0x54: /* unofficial */
		case 0x5c: /* unofficial */
		case 0x64: /* unofficial */
		case 0x6c: /* unofficial */
		case 0x74: /* unofficial */
		case 0x7c: /* unofficial */
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			work8 = _cpu.getM_a8();
			_cpu.setM_a8(0);
			_cpu.sub_a(work8);
			break;

			/* retn */
		case 0x45:
		case 0x55: /* unofficial */
		case 0x5d: /* unofficial */
		case 0x65: /* unofficial */
		case 0x6d: /* unofficial */
		case 0x75: /* unofficial */
		case 0x7d: /* unofficial */
			_cpu.setM_tstates(_cpu.getM_tstates() + 14);
			_cpu.setM_pc16(_cpu.pop16());
			_cpu.setM_iff1a(_cpu.getM_iff1b());
			break;

			/**
			 * The IM 0/1 instruction puts the processor in either IM 0 or 1
			 * (undetermined at this time). The SGS booklet says that there are two
			 * flip-flops which determine the interrupt mode and are set by the IM
			 * instructions. The possible values are:
			 * 
			 * 00 IM 0 01 not used 10 IM 1 11 IM 2
			 * 
			 * so it is entirely possible that the IM 0/1 instruction sets these
			 * registers to 01. What the Z80 does when this happens is anyone's
			 * guess.
			 */

			/* im 0 */
		case 0x46:
		case 0x4e: /* unofficial */
		case 0x66: /* unofficial */
		case 0x6e: /* unofficial */
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_im2(0);
			break;

			/* ld i,a */
		case 0x47:
			_cpu.setM_tstates(_cpu.getM_tstates() + 9);
			_cpu.setM_i8(_cpu.getM_a8());
			break;

			/* in c,(c) */
		case 0x48:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.setM_c8(_cpu.in8(_cpu.bc16()));
			break;

			/* out (c),c */
		case 0x49:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_c8());
			break;

			/* adc hl,bc */
		case 0x4A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.adc_hl(_cpu.bc16());
			break;

			/* ld bc,(NN) */
		case 0x4B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.bc16(_cpu.getM_memory().read16(_cpu.getM_memory().read16(_cpu.getM_pc16())));
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* reti */
		case 0x4D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 14);
			_cpu.setM_pc16(_cpu.pop16());
			// m_iff1a(m_iff1b;
			break;

			/* ld r,a */
		case 0x4F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 9);
			_cpu.setM_r8(_cpu.getM_a8());
			break;

			/* in d,(c) */
		case 0x50:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.setM_d8(_cpu.in8(_cpu.bc16()));
			break;

			/* out (c),d */
		case 0x51:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_d8());
			break;

			/* sbc hl,de */
		case 0x52:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.sbc_hl(_cpu.de16());
			break;

			/* ld (NN),de */
		case 0x53:
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.getM_memory().write16(_cpu.getM_memory().read16(_cpu.getM_pc16()), _cpu.de16());
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* im 1 */
		case 0x56:
		case 0x76: /* unofficial */
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_im2(1);
			break;

			/* ld a,i */
		case 0x57:
			_cpu.setM_tstates(_cpu.getM_tstates() + 9);
			_cpu.ld_a_special(_cpu.getM_i8());
			break;

			/* in e,(c) */
		case 0x58:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.setM_e8(_cpu.in8(_cpu.bc16()));
			break;

			/* out (c),e */
		case 0x59:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_e8());
			break;

			/* adc hl,de */
		case 0x5A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.adc_hl(_cpu.de16());
			break;

			/* ld de,(NN) */
		case 0x5B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.de16(_cpu.getM_memory().read16(_cpu.getM_memory().read16(_cpu.getM_pc16())));
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* im 2 */
		case 0x5E:
		case 0x7e: /* unofficial */
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_im2(2);
			break;

			/* ld a,r */
		case 0x5F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 9);
			_cpu.ld_a_special(_cpu.getM_r8());
			break;

			/* in h,(c) */
		case 0x60:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.setM_h8(_cpu.in8(_cpu.bc16()));
			break;

			/* out (c),h */
		case 0x61:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_h8());
			break;

			/* sbc hl,hl */
		case 0x62:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.sbc_hl(_cpu.hl16());
			break;

			/* ld (nn),hl */
		case 0x63: /* unofficial */
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.getM_memory().write16(_cpu.getM_memory().read16(_cpu.getM_pc16()), _cpu.hl16());
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* rrd */
			/* actualizar flags numa classe propria */
		case 0x67:
			//						_cpu.setM_tstates(_cpu.getM_tstates() + 18);
			//						work8 = _cpu.getM_memory().read8(_cpu.hl16());
			//						_cpu.getM_memory().write8(_cpu.hl16(), ((work8 >> 4) | (_cpu.getM_a8() << 4)));
			//						_cpu.setM_a8((_cpu.getM_a8() & 0xf0) | (work8 & 0x0f));
			//						_cpu.setM_signF(((_cpu.getM_a8() & 0x80) != 0));
			//						_cpu.setM_zeroF((_cpu.getM_a8() == 0));
			//						_cpu.setM_halfcarryF(false);
			//						_cpu.setM_parityoverflowF(Z80.m_parityTable[_cpu.getM_a8()]);
			//						_cpu.setM_addsubtractF(false);
			//						_cpu.setM_3F(((_cpu.getM_a8() & Z80.THREE_MASK) != 0));
			//						_cpu.setM_5F(((_cpu.getM_a8() & Z80.FIVE_MASK) != 0));
			RRD op = OperationsFactory.rrd;
			work8 = op.rrd();
			break;

			/* in l,(c) */
		case 0x68:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.setM_l8(_cpu.in8(_cpu.bc16()));
			break;

			/* out (c),l */
		case 0x69:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.m_io.out(_cpu.bc16(), _cpu.getM_l8());
			break;

			/* adc hl,hl */
		case 0x6A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.adc_hl(_cpu.hl16());
			break;

			/* ld hl,(NN) */
		case 0x6B: /* unofficial */
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.hl16(_cpu.getM_memory().read16(_cpu.getM_memory().read16(_cpu.getM_pc16())));
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* rld */
		case 0x6F:						
			//			_cpu.setM_tstates(_cpu.getM_tstates() + 18);
			//			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			//			_cpu.getM_memory().write8(_cpu.hl16(), (((work8 << 4) | (_cpu.getM_a8() & 0x0f))) & 0xff);
			//			_cpu.setM_a8((_cpu.getM_a8() & 0xf0) | (work8 >> 4));
			//			_cpu.setM_signF(((_cpu.getM_a8() & 0x80) != 0));
			//			_cpu.setM_zeroF((_cpu.getM_a8() == 0));
			//			_cpu.setM_halfcarryF(false);
			//			_cpu.setM_parityoverflowF(Z80.m_parityTable[_cpu.getM_a8()]);
			//			_cpu.setM_addsubtractF(false);
			//			_cpu.setM_3F(((_cpu.getM_a8() & Z80.THREE_MASK) != 0));
			//			_cpu.setM_5F(((_cpu.getM_a8() & Z80.FIVE_MASK) != 0));
			RLD rldOp = OperationsFactory.rld;
			work8 = rldOp.rld();
			break;

			/**
			 * The ED70 instruction reads from port (C), just like the other
			 * instructions, but throws away the result. It does change the flags in
			 * the same way as the other IN instructions, however.
			 */

			/* in f,(c) */
		case 0x70:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.in8(_cpu.bc16());
			break;

			/* out (c),0 */
		case 0x71: /* unofficial */
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.getM_io().out(_cpu.bc16(), 0);
			break;

			/* sbc hl,sp */
		case 0x72:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.sbc_hl(_cpu.getM_sp16());
			break;

			/* ld (NN),sp */
		case 0x73:
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.getM_memory().write16(_cpu.getM_memory().read16(_cpu.getM_pc16()), _cpu.getM_sp16());
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* nop */
		case 0x77: /* unofficial */
		case 0x7f: /* unofficial */
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			break;

			/* in a,(c) */
		case 0x78:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.setM_a8(_cpu.in8(_cpu.bc16()));
			break;

			/* out (c),a */
		case 0x79:
			_cpu.setM_tstates(_cpu.getM_tstates() + 12);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_a8());
			break;

			/* adc hl,sp */
		case 0x7A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.adc_hl(_cpu.getM_sp16());
			break;

			/* ld sp,(NN) */
		case 0x7B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.setM_sp16(_cpu.getM_memory().read16(_cpu.getM_memory().read16(_cpu.getM_pc16())));
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* ldi */
		case 0xA0:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			_cpu.getM_memory().write8(_cpu.de16(), work8);
			_cpu.inc16de();
			_cpu.inc16hl();
			_cpu.dec16bc();
			//						_cpu.setM_halfcarryF(false);
			//						_cpu.setM_parityoverflowF((_cpu.bc16() != 0));
			//						_cpu.setM_addsubtractF(false);
			LDI ldiOp = OperationsFactory.ldi;
			work8 += _cpu.getM_a8();
			//						_cpu.setM_3F(((work8 & Z80.THREE_MASK) != 0));
			//						_cpu.setM_5F(((work8 & Z80.ONE_MASK) != 0));
			ed_3_5 = OperationsFactory.ed_3_5;
			ed_3_5.update5_3(work8);

			break;

			/* cpi */
		case 0xA1:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			_cpu.cmp_a_special(work8);
			_cpu.inc16hl();
			_cpu.dec16bc();
			//			_cpu.setM_parityoverflowF((_cpu.bc16() != 0));
			CPD cpdOp = OperationsFactory.cpd;
			cpdOp.cpd();
			work8 = _cpu.getM_a8() - work8 - (_cpu.getM_halfcarryF() ? 1 : 0);
			//			_cpu.setM_3F(((work8 & Z80.THREE_MASK) != 0));
			//			_cpu.setM_5F(((work8 & Z80.ONE_MASK) != 0));
			ed_3_5 = OperationsFactory.ed_3_5;
			ed_3_5.update5_3(work8);

			break;

			/* ini */
		case 0xA2:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			_cpu.getM_memory().write8(_cpu.hl16(), _cpu.getM_io().in8(_cpu.bc16()));
			_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
			_cpu.inc16hl();
			//			_cpu.setM_zeroF(_cpu.getM_b8() == 0);
			//			_cpu.setM_addsubtractF(true);
			// TODO: handle 3F, 5F
			INI iniOp = OperationsFactory.ini;
			iniOp.ini();
			break;

			/* outi */
		case 0xA3:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_memory().read8(_cpu.hl16()));
			//			_cpu.setM_zeroF(_cpu.getM_b8() == 0);
			//			_cpu.setM_addsubtractF(true);
			iniOp = OperationsFactory.ini;
			iniOp.ini();

			// TODO: handle 3F, 5F
			break;

			/* ldd */
		case 0xA8:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			_cpu.getM_memory().write8(_cpu.de16(), work8);
			_cpu.dec16de();
			_cpu.dec16hl();
			_cpu.dec16bc();
			//						_cpu.setM_parityoverflowF(_cpu.bc16() != 0);
			//						_cpu.setM_halfcarryF(false);
			//						_cpu.setM_addsubtractF(false);
			LDD lddOp = OperationsFactory.ldd;
			lddOp.ldd(work8);
			work8 += _cpu.getM_a8();
			//						_cpu.setM_3F((work8 & Z80.THREE_MASK) != 0);
			//						_cpu.setM_5F((work8 & Z80.ONE_MASK) != 0);
			ed_3_5 = OperationsFactory.ed_3_5;
			ed_3_5.update5_3(work8);

			break;

			/* cpd */
		case 0xA9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			_cpu.cmp_a_special(work8);
			_cpu.dec16hl();
			_cpu.dec16bc();
			//			_cpu.setM_parityoverflowF(_cpu.bc16() != 0);
			cpdOp = OperationsFactory.cpd;
			cpdOp.cpd();
			work8 = _cpu.getM_a8() - work8 - (_cpu.getM_halfcarryF() ? 1 : 0);
			//			_cpu.setM_3F((work8 & Z80.THREE_MASK) != 0);
			//			_cpu.setM_5F((work8 & Z80.ONE_MASK) != 0);


			ed_3_5 = OperationsFactory.ed_3_5;
			ed_3_5.update5_3(work8);
			break;

			/* ind */
		case 0xAA:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			_cpu.getM_memory().write8(_cpu.hl16(), _cpu.getM_io().in8(_cpu.bc16()));
			_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
			_cpu.dec16hl();
			//			_cpu.setM_zeroF(_cpu.getM_b8() == 0);
			//			_cpu.setM_addsubtractF(true);
			iniOp = OperationsFactory.ini;
			iniOp.ini();
			// TODO: handle 3F, 5F
			break;

			/* outd */
		case 0xAB:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_memory().read8(_cpu.hl16()));
			_cpu.dec16hl();
			//			_cpu.setM_zeroF(_cpu.getM_b8() == 0);
			//			_cpu.setM_addsubtractF(true);
			iniOp = OperationsFactory.ini;
			iniOp.ini();
			// TODO: handle 3F, 5F
			break;

			/* ldir */
		case 0xB0:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			_cpu.getM_memory().write8(_cpu.de16(), work8);
			_cpu.inc16hl();
			_cpu.inc16de();
			_cpu.dec16bc();
			//			_cpu.setM_parityoverflowF(_cpu.bc16() != 0);
			//			_cpu.setM_halfcarryF(false);
			//			_cpu.setM_addsubtractF(false);
			LDIR ldirOp = OperationsFactory.ldir;
			ldirOp.ldir(work8);
			if (_cpu.getM_parityoverflowF()) {
				_cpu.setM_tstates(_cpu.getM_tstates() + 5);
				_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
			}
			work8 += _cpu.getM_a8();
			//			_cpu.setM_3F((work8 & Z80.THREE_MASK) != 0);
			//			_cpu.setM_5F((work8 & Z80.ONE_MASK) != 0);
			ed_3_5 = OperationsFactory.ed_3_5;
			ed_3_5.update5_3(work8);

			break;

			/* cpir */
		case 0xB1:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			_cpu.cmp_a_special(work8);
			_cpu.inc16hl();
			_cpu.dec16bc();
			//			_cpu.setM_parityoverflowF(_cpu.bc16() != 0);
			CPD cpdrOp = OperationsFactory.cpd;
			cpdrOp.cpd();
			if (_cpu.getM_parityoverflowF() && !_cpu.getM_zeroF()) {
				_cpu.setM_tstates(_cpu.getM_tstates() + 5);
				_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
			}
			work8 = _cpu.getM_a8() - work8 - (_cpu.getM_halfcarryF() ? 1 : 0);
			//			_cpu.setM_3F((work8 & Z80.THREE_MASK) != 0);
			//			_cpu.setM_5F((work8 & Z80.ONE_MASK) != 0);
			ed_3_5 = OperationsFactory.ed_3_5;
			ed_3_5.update5_3(work8);
			break;

			/* inir */
		case 0xB2:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			_cpu.getM_memory().write8(_cpu.hl16(), _cpu.getM_io().in8(_cpu.bc16()));
			_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
			_cpu.inc16hl();
			//			_cpu.setM_zeroF(_cpu.getM_b8() == 0);
			//			_cpu.setM_addsubtractF(true);
			iniOp = OperationsFactory.ini;
			iniOp.ini();
			if (!_cpu.getM_zeroF()) {
				_cpu.setM_tstates(_cpu.getM_tstates() + 5);
				_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
			}
			break;

			/* otir */
		case 0xB3:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_memory().read8(_cpu.hl16()));
			_cpu.inc16hl();
			//			_cpu.setM_zeroF(_cpu.getM_b8() == 0);
			//			_cpu.setM_addsubtractF(true);
			iniOp = OperationsFactory.ini;
			iniOp.ini();
			if (!_cpu.getM_zeroF()) {
				_cpu.setM_tstates(_cpu.getM_tstates() + 5);
				_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
			}

			break;

			/* lddr */
		case 0xB8:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			_cpu.getM_memory().write8(_cpu.de16(), work8);
			_cpu.dec16hl();
			_cpu.dec16de();
			_cpu.dec16bc();
			//			_cpu.setM_parityoverflowF(_cpu.bc16() != 0);
			//			_cpu.setM_halfcarryF(false);
			//			_cpu.setM_addsubtractF(false);
			LDDR lddrOp = OperationsFactory.lddr;
			lddrOp.ldd(work8);
			if (_cpu.getM_parityoverflowF()) {
				_cpu.setM_tstates(_cpu.getM_tstates() + 5);
				_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
			}
			work8 += _cpu.getM_a8();
			//						_cpu.setM_3F((work8 & Z80.THREE_MASK) != 0);
			//						_cpu.setM_5F((work8 & Z80.ONE_MASK) != 0);
			ed_3_5 = OperationsFactory.ed_3_5;
			ed_3_5.update5_3(work8);

			break;

			/* cpdr */
		case 0xB9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			work8 = _cpu.getM_memory().read8(_cpu.hl16());
			_cpu.cmp_a_special(work8);
			_cpu.dec16hl();
			_cpu.dec16bc();
			//			_cpu.setM_parityoverflowF(_cpu.bc16() != 0);
			cpdrOp = OperationsFactory.cpd;
			cpdrOp.cpd();
			if (_cpu.getM_parityoverflowF() && !_cpu.getM_zeroF()) {
				_cpu.setM_tstates(_cpu.getM_tstates() + 5);
				_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
			}
			work8 = _cpu.getM_a8() - work8 - (_cpu.getM_halfcarryF() ? 1 : 0);
			//						_cpu.setM_3F ((work8 & Z80.THREE_MASK) != 0);
			//						_cpu.setM_5F ((work8 & Z80.ONE_MASK) != 0);
			ed_3_5 = OperationsFactory.ed_3_5;
			ed_3_5.update5_3(work8);


			break;

			/* indr */
		case 0xBA:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			_cpu.getM_memory().write8(_cpu.hl16(), _cpu.getM_io().in8(_cpu.bc16()));
			_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
			_cpu.dec16hl();
			//			_cpu.setM_zeroF(_cpu.getM_b8() == 0);
			//			_cpu.setM_addsubtractF(true);
			iniOp = OperationsFactory.ini;
			iniOp.ini();

			if (!_cpu.getM_zeroF()) {
				_cpu.setM_tstates(_cpu.getM_tstates() + 5);
				_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
			}

			//			// TODO: handle 3F, 5F
			break;

			/* otdr */
		case 0xBB:
			_cpu.setM_tstates(_cpu.getM_tstates() + 16);
			_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
			_cpu.getM_io().out(_cpu.bc16(), _cpu.getM_memory().read8(_cpu.hl16()));
			_cpu.dec16hl();
			//			_cpu.setM_zeroF(_cpu.getM_b8() == 0);
			//			_cpu.setM_addsubtractF(true);

			iniOp = OperationsFactory.ini;
			iniOp.ini();

			if (!_cpu.getM_zeroF()) {
				_cpu.setM_tstates(_cpu.getM_tstates() + 5);
				_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
			}
			// TODO: handle 3F, 5F
			break;

		case 0xFB:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			// TODO: write levelLoaderTrap()
			break;

		case 0xFC:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			// TODO: write load()
			break;

		case 0xFD:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			// TODO: write save()
			break;

			/**
			 * The ED opcodes in the range 00-3F and 80-FF (except for the block
			 * instructions of course) do nothing at all but taking up 8 T states
			 * and incrementing the R register by 2.
			 */

		default:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);

			_cpu.getM_logger().log(
					ILogger.C_ERROR,
					"Unimplemented instruction: "
							+ _cpu.getM_memory().read8(_cpu.decdec16( _cpu.getM_pc16())) + " "
							+ _cpu.getM_memory().read8(_cpu.dec16( _cpu.getM_pc16())) + " at "
							+ _cpu.decdec16( _cpu.getM_pc16()));

			break;
		}
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 0;
	}

}
