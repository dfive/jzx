package pt.ist.ave.jzx.instructions;

import pt.ist.ave.jzx.ILogger;

/**
 * IY register operations
 */
//special instruction
public class DECODEXX extends Instruction {

	public DECODEXX(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int op8 = _cpu.mone8();

		if(_opCode == 0xdd) {
			_cpu.setM_xx16(_cpu.getM_ix16());		
		}else if (_opCode == 0xfd) {
			_cpu.setM_xx16(_cpu.getM_iy16());
		}

		//		public void decodeXX(int op8) {
		int work16 = 0;
		int work8 = 0;

		switch (op8) {
		/* add xx,bc */
		case 0x09:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.add_xx(_cpu.bc16());
			break;

			/* add xx,de */
		case 0x19:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.add_xx(_cpu.de16());
			break;

			/* ld xx,NN */
		case 0x21:
			_cpu.setM_tstates(_cpu.getM_tstates() + 14);
			_cpu.setM_xx16(_cpu.getM_memory().read16(_cpu.getM_pc16()));
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* ld (NN),xx */
		case 0x22:
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.getM_memory().write16(_cpu.getM_memory().read16(_cpu.getM_pc16()), _cpu.getM_xx16());
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* inc xx */
		case 0x23:
			_cpu.setM_tstates(_cpu.getM_tstates() + 10);
			_cpu.inc16xx();
			break;

			/* inc hx */
		case 0x24:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16high8(_cpu.inc8(_cpu.xx16high8()));
			break;

			/* dec hx */
		case 0x25:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16high8(_cpu.dec8(_cpu.xx16high8()));
			break;

			/* ld hx,N */
		case 0x26:
			_cpu.setM_tstates(_cpu.getM_tstates() + 11);
			_cpu.xx16high8(_cpu.getM_memory().read8(_cpu.inc16pc()));
			break;

			/* add xx,xx */
		case 0x29:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.add_xx(_cpu.getM_xx16());
			break;

			/* ld xx,(NN) */
		case 0x2A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 20);
			_cpu.setM_xx16(_cpu.getM_memory().read16(_cpu.getM_memory().read16(_cpu.getM_pc16())));
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* dec xx */
		case 0x2B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 10);
			_cpu.dec16xx();
			break;

			/* inc lx */
		case 0x2C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16low8(_cpu.inc8(_cpu.xx16low8()));
			break;

			/* dec lx */
		case 0x2D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16low8(_cpu.dec8(_cpu.xx16low8()));
			break;

			/* ld lx,N */
		case 0x2E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 11);
			_cpu.xx16low8(_cpu.getM_memory().read8(_cpu.inc16pc()));
			break;

			/* inc (xx+d) */
		case 0x34:
			_cpu.setM_tstates(_cpu.getM_tstates() + 23);
			work16 = _cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc()));
			work8 = _cpu.getM_memory().read8(work16);
			work8 = _cpu.inc8(work8);
			_cpu.getM_memory().write8(work16, work8);
			break;

			/* dec (xx+d) */
		case 0x35:
			_cpu.setM_tstates(_cpu.getM_tstates() + 23);
			work16 = _cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc()));
			work8 = _cpu.getM_memory().read8(work16);
			work8 = _cpu.dec8(work8);
			_cpu.getM_memory().write8(work16, work8);
			break;

			/* ld (xx+d),N */
		case 0x36:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			_cpu.getM_memory().write8(_cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.getM_pc16())),
					_cpu.getM_memory().read8(_cpu.inc16(_cpu.getM_pc16())));
			_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
			break;

			/* add xx,sp */
		case 0x39:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.add_xx(_cpu.getM_sp16());
			break;

			/* ld b,hx */
		case 0x44:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.xx16high8());
			break;

			/* ld b,lx */
		case 0x45:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_b8(_cpu.xx16low8());
			break;

			/* ld b,(xx+d) */
		case 0x46:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work16 = _cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc()));
			_cpu.setM_x8(work16 >> 8);
			_cpu.setM_b8(_cpu.getM_memory().read8(work16));
			break;

			/* ld c,hx */
		case 0x4C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.xx16high8());
			break;

			/* ld c,lx */
		case 0x4D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_c8(_cpu.xx16low8());
			break;

			/* ld c,(xx+d) */
		case 0x4E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			work16 = _cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc()));
			_cpu.setM_x8(work16 >> 8);
			_cpu.setM_c8(_cpu.getM_memory().read8(work16));
			break;

			/* ld d,hx */
		case 0x54:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.xx16high8());
			break;

			/* ld d,lx */
		case 0x55:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_d8(_cpu.xx16low8());
			break;

			/* ld d,(xx+d) */
		case 0x56:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work16 = _cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc()));
			_cpu.setM_x8(work16 >> 8);
			_cpu.setM_d8(_cpu.getM_memory().read8(work16));
			break;

			/* ld e,hx */
		case 0x5C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.xx16high8());
			break;

			/* ld e,lx */
		case 0x5D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_e8(_cpu.xx16low8());
			break;

			/* ld e,(xx+d) */
		case 0x5E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work16 = _cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc()));
			_cpu.setM_x8(work16 >> 8);
			_cpu.setM_e8(_cpu.getM_memory().read8(work16));
			break;

			/* ld hx,b */
		case 0x60:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16high8(_cpu.getM_b8());
			break;

			/* ld hx,c */
		case 0x61:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16high8(_cpu.getM_c8());
			break;

			/* ld hx,d */
		case 0x62:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16high8(_cpu.getM_d8());
			break;

			/* ld hx,e */
		case 0x63:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16high8(_cpu.getM_e8());
			break;

			/* ld hx,hx */
		case 0x64:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			break;

			/* ld hx,lx */
		case 0x65:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16high8(_cpu.xx16low8());
			break;

			/* ld h,(xx+d) */
		case 0x66:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work16 = _cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc()));
			_cpu.setM_x8(work16 >> 8);
			_cpu.setM_h8(_cpu.getM_memory().read8(work16));
			break;

			/* ld hx,a */
		case 0x67:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16high8(_cpu.getM_a8());
			break;

			/* ld lx,b */
		case 0x68:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16low8(_cpu.getM_b8());
			break;

			/* ld lx,c */
		case 0x69:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16low8(_cpu.getM_c8());
			break;

			/* ld lx,d */
		case 0x6A:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16low8(_cpu.getM_d8());
			break;

			/* ld lx,e */
		case 0x6B:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16low8(_cpu.getM_e8());
			break;

			/* ld lx,hx */
		case 0x6C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16low8(_cpu.xx16high8());
			break;

			/* ld lx,lx */
		case 0x6D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			break;

			/* ld l,(xx+d) */
		case 0x6E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work16 = _cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc()));
			_cpu.setM_x8(work16 >> 8);
			_cpu.setM_l8(_cpu.getM_memory().read8(work16));
			break;

			/* ld lx,a */
		case 0x6F:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xx16low8(_cpu.getM_a8());
			break;

			/* ld (xx+d),b */
		case 0x70:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			_cpu.getM_memory().write8(_cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc())),
					_cpu.getM_b8());
			break;

			/* ld (xx+d),c */
		case 0x71:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			_cpu.getM_memory().write8(_cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc())),
					_cpu.getM_c8());
			break;

			/* ld (xx+d),d */
		case 0x72:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			_cpu.getM_memory().write8(_cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc())),
					_cpu.getM_d8());
			break;

			/* ld (xx+d),e */
		case 0x73:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			_cpu.getM_memory().write8(_cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc())),
					_cpu.getM_e8());
			break;

			/* ld (xx+d),h */
		case 0x74:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			_cpu.getM_memory().write8(_cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc())),
					_cpu.getM_h8());
			break;

			/* ld (xx+d),l */
		case 0x75:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			_cpu.getM_memory().write8(_cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc())),
					_cpu.getM_l8());
			break;

			/* ld (xx+d),a */
		case 0x77:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			_cpu.getM_memory().write8(_cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc())),
					_cpu.getM_a8());
			break;

			/* ld a,hx */
		case 0x7C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.xx16high8());
			break;

			/* ld a,lx */
		case	0x7D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_a8(_cpu.xx16low8());
			break;

			/* ld a,(xx+d) */
		case 0x7E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work16 = _cpu.add16(_cpu.getM_xx16(), (byte) _cpu.getM_memory().read8(_cpu.inc16pc()));
			_cpu.setM_x8(work16 >> 8);
			_cpu.setM_a8(_cpu.getM_memory().read8(work16));
			break;

			/* add a,hx */
		case 0x84:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.add_a(_cpu.xx16high8());
			break;

			/* add a,lx */
		case 0x85:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.add_a(_cpu.xx16low8());
			break;

			/* add a,(xx+d) */
		case 0x86:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work8 = _cpu.getM_memory().read8(_cpu.add16(_cpu.getM_xx16(),
					(byte) _cpu.getM_memory().read8(_cpu.inc16pc())));
			_cpu.add_a(work8);
			break;

			/* adc a,hx */
		case 0x8C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.adc_a(_cpu.xx16high8());
			break;

			/* adc a,lx */
		case 0x8D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.adc_a(_cpu.xx16low8());
			break;

			/* adc a,(xx+d) */
		case 0x8E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work8 = _cpu.getM_memory().read8(_cpu.add16(_cpu.getM_xx16(),
					(byte) _cpu.getM_memory().read8(_cpu.inc16pc())));
			_cpu.adc_a(work8);
			break;

			/* sub hx */
		case 0x94:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.sub_a(_cpu.xx16high8());
			break;

			/* sub lx */
		case 0x95:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.sub_a(_cpu.xx16low8());
			break;

			/* sub (xx+d) */
		case 0x96:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work8 = _cpu.getM_memory().read8(_cpu.add16(_cpu.getM_xx16(),
					(byte) _cpu.getM_memory().read8(_cpu.inc16pc())));
			_cpu.sub_a(work8);
			break;

			/* sbc a,hx */
		case 0x9C:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.sbc_a(_cpu.xx16high8());
			break;

			/* sbc a,l */
		case 0x9D:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.sbc_a(_cpu.xx16low8());
			break;

			/* sbc a,(xx+d) */
		case 0x9E:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work8 = _cpu.getM_memory().read8(_cpu.add16(_cpu.getM_xx16(),
					(byte) _cpu.getM_memory().read8(_cpu.inc16pc())));
			_cpu.sbc_a(work8);
			break;

			/* and hx */
		case 0xA4:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.and_a(_cpu.xx16high8());
			break;

			/* and lx */
		case 0xA5:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.and_a(_cpu.xx16low8());
			break;

			/* and (xx+d) */
		case 0xA6:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work8 = _cpu.getM_memory().read8(_cpu.add16(_cpu.getM_xx16(),
					(byte) _cpu.getM_memory().read8(_cpu.inc16pc())));
			_cpu.and_a(work8);
			break;

			/* xor hx */
		case 0xAC:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xor_a(_cpu.xx16high8());
			break;

			/* xor lx */
		case 0xAD:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.xor_a(_cpu.xx16low8());
			break;

			/* xor (xx+d) */
		case 0xAE:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work8 = _cpu.getM_memory().read8(_cpu.add16(_cpu.getM_xx16(),
					(byte) _cpu.getM_memory().read8(_cpu.inc16pc())));
			_cpu.xor_a(work8);
			break;

			/* or hx */
		case 0xB4:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.or_a(_cpu.xx16high8());
			break;

			/* or lx */
		case 0xB5:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.or_a(_cpu.xx16low8());
			break;

			/* or (xx+d) */
		case 0xB6:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work8 = _cpu.getM_memory().read8(_cpu.add16(_cpu.getM_xx16(),
					(byte) _cpu.getM_memory().read8(_cpu.inc16pc())));
			_cpu.or_a(work8);
			break;

			/* cp hx */
		case 0xBC:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.cmp_a(_cpu.xx16high8());
			break;

			/* cp lx */
		case 0xBD:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.cmp_a(_cpu.xx16low8());
			break;

			/* cp (xx+d) */
		case 0xBE:
			_cpu.setM_tstates(_cpu.getM_tstates() + 19);
			work8 = _cpu.getM_memory().read8(_cpu.add16(_cpu.getM_xx16(),
					(byte) _cpu.getM_memory().read8(_cpu.inc16pc())));
			_cpu.cmp_a(work8);
			break;

			/* Index register with 0xCB handling */
		case 0xCB: {
			byte disp8 = (byte) _cpu.getM_memory().read8(_cpu.inc16pc());
			_cpu.setM_xx16(_cpu.add16(_cpu.getM_xx16(), disp8));

			switch (_cpu.getM_memory().read8(_cpu.inc16pc())) {
			//			 xxcbops

			/* rlc (xx+d),b */
			case 0x00:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_b8(_cpu.rlc8(_cpu.getM_b8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* rlc (xx+d),c */
			case 0x01:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_c8(_cpu.rlc8(_cpu.getM_c8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* rlc (xx+d),d */
			case 0x02:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_d8(_cpu.rlc8(_cpu.getM_d8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* rlc (xx+d),e */
			case 0x03:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_e8(_cpu.rlc8(_cpu.getM_e8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* rlc (xx+d),h */
			case 0x04:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_h8(_cpu.rlc8(_cpu.getM_h8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* rlc (xx+d),l */
			case 0x05:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_l8(_cpu.rlc8(_cpu.getM_l8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* rlc (xx+d) */
			case 0x06:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				work8 = _cpu.getM_memory().read8(_cpu.getM_xx16());
				work8 = _cpu.rlc8(work8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), work8);
				break;

				/* rlc (xx+d),a */
			case 0x07:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_a8(_cpu.rlc8(_cpu.getM_a8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* rrc (xx+d),b */
			case 0x08:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_b8(_cpu.rrc8(_cpu.getM_b8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* rrc (xx+d),c */
			case 0x09:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_c8(_cpu.rrc8(_cpu.getM_c8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* rrc (xx+d),d */
			case 0x0A:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_d8(_cpu.rrc8(_cpu.getM_d8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* rrc (xx+d),e */
			case 0x0B:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_e8(_cpu.rrc8(_cpu.getM_e8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* rrc (xx+d),h */
			case 0x0C:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_h8(_cpu.rrc8(_cpu.getM_h8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* rrc (xx+d),l */
			case 0x0D:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_l8(_cpu.rrc8(_cpu.getM_l8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* rrc (xx+d) */
			case 0x0E:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				work8 = _cpu.getM_memory().read8(_cpu.getM_xx16());
				work8 = _cpu.rrc8(work8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), work8);
				break;

				/* rrc a */
			case 0x0F:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_a8(_cpu.rrc8(_cpu.getM_a8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* rl (xx+d),b */
			case 0x10:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_b8(_cpu.rl8(_cpu.getM_b8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* rl (xx+d),c */
			case 0x11:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_c8(_cpu.rl8(_cpu.getM_c8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* rl (xx+d),d */
			case 0x12:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_d8(_cpu.rl8(_cpu.getM_d8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* rl (xx+d),e */
			case 0x13:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_e8(_cpu.rl8(_cpu.getM_e8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* rl (xx+d),h */
			case 0x14:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_h8(_cpu.rl8(_cpu.getM_h8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* rl (xx+d),l */
			case 0x15:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_l8(_cpu.rl8(_cpu.getM_l8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* rl (xx+d) */
			case 0x16:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				work8 = _cpu.getM_memory().read8(_cpu.getM_xx16());
				work8 = _cpu.rl8(work8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), work8);
				break;

				/* rl (xx+d),a */
			case 0x17:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_a8(_cpu.rl8(_cpu.getM_a8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* rr (xx+d),b */
			case 0x18:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_b8(_cpu.rr8(_cpu.getM_b8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* rr (xx+d),c */
			case 0x19:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_c8(_cpu.rr8(_cpu.getM_c8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* rr (xx+d),d */
			case 0x1A:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_d8(_cpu.rr8(_cpu.getM_d8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* rr (xx+d),e */
			case 0x1B:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_e8(_cpu.rr8(_cpu.getM_e8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* rr (xx+d),h */
			case 0x1C:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_h8(_cpu.rr8(_cpu.getM_h8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* rr (xx+d),l */
			case 0x1D:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_l8(_cpu.rr8(_cpu.getM_l8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* rr (xx+d) */
			case 0x1E:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				work8 = _cpu.getM_memory().read8(_cpu.getM_xx16());
				work8 = _cpu.rr8(work8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), work8);
				break;

				/* rr (xx+d),a */
			case 0x1F:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_a8(_cpu.rr8(_cpu.getM_a8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* sla (xx+d),b */
			case 0x20:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_b8(_cpu.sra8(_cpu.getM_b8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* sla (xx+d),c */
			case 0x21:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_c8(_cpu.sla8(_cpu.getM_c8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* sla (xx+d),d */
			case 0x22:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_d8(_cpu.sla8(_cpu.getM_d8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* sla (xx+d),e */
			case 0x23:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_e8(_cpu.sla8(_cpu.getM_e8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* sla (xx+d),h */
			case 0x24:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_h8(_cpu.sla8(_cpu.getM_h8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* sla (xx+d),l */
			case 0x25:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_l8(_cpu.sla8(_cpu.getM_l8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* sla (xx+d) */
			case 0x26:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				work8 = _cpu.getM_memory().read8(_cpu.getM_xx16());
				work8 = _cpu.sla8(work8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), work8);
				break;

				/* sla (xx+d),a */
			case 0x27:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_a8(_cpu.sla8(_cpu.getM_a8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* sra (xx+d),b */
			case 0x28:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_b8(_cpu.sra8(_cpu.getM_b8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* sra (xx+d),c */
			case 0x29:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_c8(_cpu.sra8(_cpu.getM_c8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* sra (xx+d),d */
			case 0x2A:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_d8(_cpu.sra8(_cpu.getM_d8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* sra (xx+d),e */
			case 0x2B:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_e8(_cpu.sra8(_cpu.getM_e8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* sra (xx+d),h */
			case 0x2C:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_h8(_cpu.sra8(_cpu.getM_h8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* sra (xx+d),l */
			case 0x2D:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_l8(_cpu.sra8(_cpu.getM_l8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* sra (xx+d) */
			case 0x2E:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				work8 = _cpu.getM_memory().read8(_cpu.getM_xx16());
				work8 = _cpu.sra8(work8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), work8);
				break;

				/* sra (xx+d),a */
			case 0x2F:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_a8(_cpu.sra8(_cpu.getM_a8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/**
				 * The next 8 opcodes are undocumented.
				 */

				/* sli (xx+d),b */
			case 0x30:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_b8(_cpu.sli8(_cpu.getM_b8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* sli (xx+d),c */
			case 0x31:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_c8(_cpu.sli8(_cpu.getM_c8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* sli (xx+d),d */
			case 0x32:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_d8(_cpu.sli8(_cpu.getM_d8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* sli (xx+d),e */
			case 0x33:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_e8(_cpu.sli8(_cpu.getM_e8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* sli (xx+d),h */
			case 0x34:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_h8(_cpu.sli8(_cpu.getM_h8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* sli (xx+d),l */
			case 0x35:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_l8(_cpu.sli8(_cpu.getM_l8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* sli (xx+d) */
			case 0x36:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				work8 = _cpu.getM_memory().read8(_cpu.getM_xx16());
				work8 = _cpu.sli8(work8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), work8);
				break;

				/* sli (xx+d),a */
			case 0x37:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_a8(_cpu.sli8(_cpu.getM_a8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* srl (xx+d),b */
			case 0x38:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_b8(_cpu.srl8(_cpu.getM_b8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* srl (xx+d),c */
			case 0x39:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_c8(_cpu.srl8(_cpu.getM_c8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* srl (xx+d),d */
			case 0x3A:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_d8(_cpu.srl8(_cpu.getM_d8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* srl (xx+d),e */
			case 0x3B:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_e8(_cpu.srl8(_cpu.getM_e8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* srl (xx+d),h */
			case 0x3C:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_h8(_cpu.srl8(_cpu.getM_h8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* srl (xx+d),l */
			case 0x3D:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_l8(_cpu.srl8(_cpu.getM_l8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* srl (xx+d) */
			case 0x3E:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				work8 = _cpu.getM_memory().read8(_cpu.getM_xx16());
				work8 = _cpu.srl8(work8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), work8);
				break;

				/* srl (xx+d),a */
			case 0x3F:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()));
				_cpu.setM_a8(_cpu.srl8(_cpu.getM_a8()));
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* bit 0,(ix+d) */
			case 0x40: /* unofficial */
			case 0x41: /* unofficial */
			case 0x42: /* unofficial */
			case 0x43: /* unofficial */
			case 0x44: /* unofficial */
			case 0x45: /* unofficial */
			case 0x46:
			case 0x47: /* unofficial */
				_cpu.setM_tstates(_cpu.getM_tstates() + 20);
				_cpu.bit_xx(0, _cpu.getM_memory().read8(_cpu.getM_xx16()));
				break;

				/* bit 1,(xx+d) */
			case 0x48: /* unofficial */
			case 0x49: /* unofficial */
			case 0x4A: /* unofficial */
			case 0x4B: /* unofficial */
			case 0x4C: /* unofficial */
			case 0x4D: /* unofficial */
			case 0x4E:
			case 0x4F: /* unofficial */
				_cpu.setM_tstates(_cpu.getM_tstates() + 20);
				_cpu.bit_xx(1, _cpu.getM_memory().read8(_cpu.getM_xx16()));
				break;

				/* bit 2,(xx+d) */
			case 0x50: /* unofficial */
			case 0x51: /* unofficial */
			case 0x52: /* unofficial */
			case 0x53: /* unofficial */
			case 0x54: /* unofficial */
			case 0x55: /* unofficial */
			case 0x56:
			case 0x57: /* unofficial */
				_cpu.setM_tstates(_cpu.getM_tstates() + 20);
				_cpu.bit_xx(2, _cpu.getM_memory().read8(_cpu.getM_xx16()));
				break;

				/* bit 3,(xx+d) */
			case 0x58: /* unofficial */
			case 0x59: /* unofficial */
			case 0x5A: /* unofficial */
			case 0x5B: /* unofficial */
			case 0x5C: /* unofficial */
			case 0x5D: /* unofficial */
			case 0x5E:
			case 0x5F: /* unofficial */
				_cpu.setM_tstates(_cpu.getM_tstates() + 20);
				_cpu.bit_xx(3, _cpu.getM_memory().read8(_cpu.getM_xx16()));
				break;

				/* bit 4,(xx+d) */
			case 0x60: /* unofficial */
			case 0x61: /* unofficial */
			case 0x62: /* unofficial */
			case 0x63: /* unofficial */
			case 0x64: /* unofficial */
			case 0x65: /* unofficial */
			case 0x66:
			case 0x67: /* unofficial */
				_cpu.setM_tstates(_cpu.getM_tstates() + 20);
				_cpu.bit_xx(4, _cpu.getM_memory().read8(_cpu.getM_xx16()));
				break;

				/* bit 5,(xx+d) */
			case 0x68: /* unofficial */
			case 0x69: /* unofficial */
			case 0x6A: /* unofficial */
			case 0x6B: /* unofficial */
			case 0x6C: /* unofficial */
			case 0x6D: /* unofficial */
			case 0x6E:
			case 0x6F: /* unofficial */
				_cpu.setM_tstates(_cpu.getM_tstates() + 20);
				_cpu.bit_xx(5, _cpu.getM_memory().read8(_cpu.getM_xx16()));
				break;

				/* bit 6,(xx+d) */
			case 0x70: /* unofficial */
			case 0x71: /* unofficial */
			case 0x72: /* unofficial */
			case 0x73: /* unofficial */
			case 0x74: /* unofficial */
			case 0x75: /* unofficial */
			case 0x76:
			case 0x77: /* unofficial */
				_cpu.setM_tstates(_cpu.getM_tstates() + 20);
				_cpu.bit_xx(6, _cpu.getM_memory().read8(_cpu.getM_xx16()));
				break;

				/* bit 7,(xx+d) */
			case 0x78: /* unofficial */
			case 0x79: /* unofficial */
			case 0x7A: /* unofficial */
			case 0x7B: /* unofficial */
			case 0x7C: /* unofficial */
			case 0x7D: /* unofficial */
			case 0x7E:
			case 0x7F: /* unofficial */
				_cpu.setM_tstates(_cpu.getM_tstates() + 20);
				_cpu.bit_xx(7, _cpu.getM_memory().read8(_cpu.getM_xx16()));
				break;

				/* res 0,(xx+d),b */
			case 0x80:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfe);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* res 0,(xx+d),c */
			case 0x81:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfe);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* res 0,(xx+d),d */
			case 0x82:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfe);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* res 0,(xx+d),e */
			case 0x83:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfe);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* res 0,(xx+d),h */
			case 0x84:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfe);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* res 0,(xx+d),l */
			case 0x85:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfe);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* res 0,(xx+d) */
			case 0x86:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfe));
				break;

				/* res 0,(xx+d),a */
			case 0x87:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfe);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* res 1,(xx+d),b */
			case 0x88:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfd);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* res 1,(xx+d),c */
			case 0x89:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfd);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* res 1,(xx+d),d */
			case 0x8A:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfd);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* res 1,(xx+d),e */
			case 0x8B:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfd);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* res 1,(xx+d),h */
			case 0x8C:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfd);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* res 1,(xx+d),l */
			case 0x8D:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfd);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* res 1,(xx+d) */
			case 0x8E:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfd));
				break;

				/* res 1,(xx+d),a */
			case 0x8F:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfd);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* res 2,(xx+d),b */
			case 0x90:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfb);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* res 2,(xx+d),c */
			case 0x91:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfb);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* res 2,(xx+d),d */
			case 0x92:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfb);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* res 2,(xx+d),e */
			case 0x93:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfb);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* res 2,(xx+d),h */
			case 0x94:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfb);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* res 2,(xx+d),l */
			case 0x95:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfb);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* res 2,(xx+d) */
			case 0x96:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfb));
				break;

				/* res 2,(xx+d),a */
			case 0x97:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xfb);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* res 3,(xx+d),b */
			case 0x98:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xf7);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* res 3,(xx+d),c */
			case 0x99:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xf7);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* res 3,(xx+d),d */
			case 0x9A:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xf7);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* res 3,(xx+d),e */
			case 0x9B:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xf7);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* res 3,(xx+d),h */
			case 0x9C:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xf7);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* res 3,(xx+d),l */
			case 0x9D:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xf7);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* res 3,(xx+d) */
			case 0x9E:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xf7));
				break;

				/* res 3,(xx+d),a */
			case 0x9F:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xf7);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* res 4,(xx+d),b */
			case 0xA0:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xef);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* res 4,(xx+d),c */
			case 0xA1:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xef);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* res 4,(xx+d),d */
			case 0xA2:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xef);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* res 4,(xx+d),e */
			case 0xA3:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xef);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* res 4,(xx+d),h */
			case 0xA4:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xef);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* res 4,(xx+d),l */
			case 0xA5:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xef);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* res 4,(xx+d) */
			case 0xA6:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xef));
				break;

				/* res 4,(xx+d),a */
			case 0xA7:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xef);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* res 5,(xx+d),b */
			case 0xA8:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xdf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* res 5,(xx+d),c */
			case 0xA9:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xdf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* res 5,(xx+d),d */
			case 0xAA:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xdf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* res 5,(xx+d),e */
			case 0xAB:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xdf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* res 5,(xx+d),h */
			case 0xAC:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xdf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* res 5,(xx+d),l */
			case 0xAD:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xdf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* res 5,(xx+d) */
			case 0xAE:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xdf));
				break;

				/* res 5,(xx+d),a */
			case 0xAF:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xdf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* res 6,(xx+d),b */
			case 0xB0:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xbf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* res 6,(xx+d),c */
			case 0xB1:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xbf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* res 6,(xx+d),d */
			case 0xB2:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xbf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* res 6,(xx+d),e */
			case 0xB3:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xbf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* res 6,(xx+d),h */
			case 0xB4:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xbf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* res 6,(xx+d),l */
			case 0xB5:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xbf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* res 6,(xx+d) */
			case 0xB6:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xbf));
				break;

				/* res 6,(xx+d),a */
			case 0xB7:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0xbf);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* res 7,(xx+d),b */
			case 0xB8:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0x7f);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* res 7,(xx+d),c */
			case 0xB9:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0x7f);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* res 7,(xx+d),d */
			case 0xBA:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0x7f);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* res 7,(xx+d),e */
			case 0xBB:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0x7f);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* res 7,(xx+d),h */
			case 0xBC:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0x7f);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* res 7,(xx+d),l */
			case 0xBD:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0x7f);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* res 7,(xx+d) */
			case 0xBE:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0x7f));
				break;

				/* res 7,(xx+d),a */
			case 0xBF:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) & 0x7f);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* set 0,(xx+d),b */
			case 0xC0:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x1);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* set 0,(xx+d),c */
			case 0xC1:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x1);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* set 0,(xx+d),d */
			case 0xC2:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x1);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* set 0,(xx+d),e */
			case 0xC3:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x1);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* set 0,(xx+d),h */
			case 0xC4:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x1);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* set 0,(xx+d),l */
			case 0xC5:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x1);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* set 0,(xx+d) */
			case 0xC6:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x1));
				break;

				/* set 0,(xx+d),a */
			case 0xC7:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x1);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* set 1,(xx+d),b */
			case 0xC8:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x2);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* set 1,(xx+d),c */
			case 0xC9:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x2);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* set 1,(xx+d),d */
			case 0xCA:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x2);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* set 1,(xx+d),e */
			case 0xCB:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x2);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* set 1,(xx+d),h */
			case 0xCC:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x2);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* set 1,(xx+d),l */
			case 0xCD:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x2);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* set 1,(xx+d) */
			case 0xCE:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x2));
				break;

				/* set 1,(xx+d),a */
			case 0xCF:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x2);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* set 2,(xx+d),b */
			case 0xD0:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x4);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* set 2,(xx+d),c */
			case 0xD1:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x4);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* set 2,(xx+d),d */
			case 0xD2:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x4);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* set 2,(xx+d),e */
			case 0xD3:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x4);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* set 2,(xx+d),h */
			case 0xD4:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x4);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* set 2,(xx+d),l */
			case 0xD5:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x4);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* set 2,(xx+d) */
			case 0xD6:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x4));
				break;

				/* set 2,(xx+d),a */
			case 0xD7:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x4);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* set 3,(xx+d),b */
			case 0xD8:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* set 3,(xx+d),c */
			case 0xD9:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* set 3,(xx+d),d */
			case 0xDA:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* set 3,(xx+d),e */
			case 0xDB:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* set 3,(xx+d),h */
			case 0xDC:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* set 3,(xx+d),l */
			case 0xDD:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* set 3,(xx+d) */
			case 0xDE:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x8));
				break;

				/* set 3,(xx+d),a */
			case 0xDF:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x8);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* set 4,(xx+d),b */
			case 0xE0:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x10);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* set 4,(xx+d),c */
			case 0xE1:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x10);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* set 4,(xx+d),d */
			case 0xE2:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x10);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* set 4,(xx+d),e */
			case 0xE3:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x10);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* set 4,(xx+d),h */
			case 0xE4:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x10);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* set 4,(xx+d),l */
			case 0xE5:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x10);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* set 4,(xx+d) */
			case 0xE6:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x10));
				break;

				/* set 4,(xx+d),a */
			case 0xE7:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x10);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* set 5,(xx+d),b */
			case 0xE8:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x20);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* set 5,(xx+d),c */
			case 0xE9:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x20);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* set 5,(xx+d),d */
			case 0xEA:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x20);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* set 5,(xx+d),e */
			case 0xEB:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x20);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* set 5,(xx+d),h */
			case 0xEC:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x20);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* set 5,(xx+d),l */
			case 0xED:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x20);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* set 5,(xx+d) */
			case 0xEE:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x20));
				break;

				/* set 5(xx+d),,a */
			case 0xEF:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x20);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* set 6,(xx+d),b */
			case 0xF0:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x40);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* set 6,(xx+d),c */
			case 0xF1:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x40);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* set 6,(xx+d),d */
			case 0xF2:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x40);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* set 6,(xx+d),e */
			case 0xF3:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x40);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* set 6,(xx+d),h */
			case 0xF4:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x40);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* set 6,(xx+d),l */
			case 0xF5:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x40);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* set 6,(xx+d) */
			case 0xF6:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x40));
				break;

				/* set 6,(xx+d),a */
			case 0xF7:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x40);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;

				/* set 7,(xx+d),b */
			case 0xF8:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x80);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_b8());
				break;

				/* set 7,(xx+d),c */
			case 0xF9:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_c8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x80);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_c8());
				break;

				/* set 7,(xx+d),d */
			case 0xFA:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_d8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x80);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_d8());
				break;

				/* set 7,(xx+d),e */
			case 0xFB:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_e8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x80);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_e8());
				break;

				/* set 7,(xx+d),h */
			case 0xFC:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_h8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x80);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_h8());
				break;

				/* set 7,(xx+d),l */
			case 0xFD:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_l8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x80);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_l8());
				break;

				/* set 7,(xx+d) */
			case 0xFE:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), (_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x80));
				break;

				/* set 7,(xx+d),a */
			case 0xFF:
				_cpu.setM_tstates(_cpu.getM_tstates() + 23);
				_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_xx16()) | 0x80);
				_cpu.getM_memory().write8(_cpu.getM_xx16(), _cpu.getM_a8());
				break;
			}

			_cpu.setM_xx16(_cpu.sub16(_cpu.getM_xx16(), disp8));
		}
		break;

		/* pop xx */
		case 0xE1:
			_cpu.setM_tstates(_cpu.getM_tstates() + 14);
			_cpu.setM_xx16(_cpu.pop16());
			break;

			/* ex (sp),xx */
		case 0xE3:
			_cpu.setM_tstates(_cpu.getM_tstates() + 23);
			work16 = _cpu.getM_memory().read16(_cpu.getM_sp16());
			_cpu.getM_memory().write16(_cpu.getM_sp16(), _cpu.getM_xx16());
			_cpu.setM_xx16(work16);
			break;

			/* push XX */
		case 0xE5:
			_cpu.setM_tstates(_cpu.getM_tstates() + 15);
			_cpu.push(_cpu.getM_xx16());
			break;

			/* jp (xx) */
		case 0xE9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			_cpu.setM_pc16(_cpu.getM_xx16());
			break;

			/* ld sp,xx */
		case 0xF9:
			_cpu.setM_tstates(_cpu.getM_tstates() + 10);
			_cpu.setM_sp16(_cpu.getM_xx16());
			break;

			/**
			 * Many DD or FD opcodes after each other will effectively be NOPs,
			 * doing nothing except repeatedly setting the flag "treat HL as IX" (or
			 * IY) and taking up 4 T states
			 */

		case 0xDD:
		case 0xFD:
			_cpu.setM_tstates(_cpu.getM_tstates() + 4);
			_cpu.dec16pc(); /* Back to the second DD/FD */
			break;

			/**
			 * DD and FD have no effect on ED instructions, or on EX DE,HL.
			 */

		case 0xEB:
		case 0xED:
			_cpu.setM_tstates(_cpu.getM_tstates() + 8);
			break;

			/**
			 * Each unimplemented opcode does the same as the nonprefixed opcode.
			 */

		default:
			_cpu.setM_tstates(_cpu.getM_tstates() + 4);
			_cpu.dec16pc(); /* Back to the nonprefixed opcode */

			_cpu.getM_logger().log(
					ILogger.C_ERROR,
					"Unimplemented instruction: "
							+ _cpu.getM_memory().read8(_cpu.dec16(_cpu.getM_pc16())) + " "
							+ _cpu.getM_memory().read8(_cpu.getM_pc16()) + " at " + _cpu.dec16(_cpu.getM_pc16()));
			break;


		}

		if(_opCode == 0xdd) {
			_cpu.setM_ix16(_cpu.getM_xx16());		
		}else if (_opCode == 0xfd) {
			_cpu.setM_iy16(_cpu.getM_xx16());
		}
	}

	@Override
	public int incTstates() {
		return 0;
	}

}
