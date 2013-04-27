package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class BIT_XX extends Operation {

	private int _bit3;
	private int _val8;
	private boolean _m_zeroF;
	private int _xx16high8;
	{	
		_updatedFlags = new int[]{
				Z80.FLAG_ZERO,
				Z80.FLAG_SIGN,
				Z80.FLAG_HALF_CARRY,
				Z80.FLAG_PARITY_OVERFLOW,
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_3,
				Z80.FLAG_5
		};
	}
	public void bit_xx(int bit3, int val8) {
		_bit3 = bit3;
		_val8 = val8;
		_xx16high8 = _cpu.xx16high8();
		_m_zeroF = _cpu.getM_zeroF();
		
		_cpu.setM_zeroF(getM_zeroF());
		_cpu.setM_halfcarryF(getM_halfcarryF());
		_cpu.setM_parityoverflowF(getM_parityoverflowF());
		_cpu.setM_addsubtractF(getM_addsubtractF());
		_cpu.setM_signF(getM_signF());
		_cpu.setM_3F(getM_3F());
		_cpu.setM_5F(getM_5F());
//		updateFlags();
	}

	@Override
	public boolean getM_carryF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		return _m_zeroF;
	}

	@Override
	public boolean getM_halfcarryF() {
		return true;
	}

	@Override
	public boolean getM_zeroF() {
		int auxBit3 = _bit3;
		return (_val8 & (0x01 << auxBit3)) == 0;
	}

	@Override
	public boolean getM_signF() {
		int auxBit3 = _bit3;
		return (_val8 & (0x01 << auxBit3)) == 0x80;
	}

	@Override
	public boolean getM_5F() {
		return (_xx16high8 & Z80.FIVE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return (_xx16high8 & Z80.THREE_MASK) != 0;
	}
}
