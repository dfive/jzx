package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class ADC_HL extends Operation {

	private int _m_h8;
	private int _hl16;
	private int _work32;
	private int _idx;
	
	{	
		_updatedFlags = new int[]{
				Z80.FLAG_ZERO,
				Z80.FLAG_SIGN,
				Z80.FLAG_HALF_CARRY,
				Z80.FLAG_PARITY_OVERFLOW,
				Z80.FLAG_ADD_SUBTRACT,
				Z80.FLAG_CARRY,
				Z80.FLAG_3,
				Z80.FLAG_5
		};
	}
	
	public void adc_hl(int val16) {
		_hl16 = _cpu.hl16();
		_work32 = _hl16 + val16 + (_cpu.getM_carryF() ? 1 : 0);
		_idx = ((_hl16 & 0x8800) >> 9) | ((val16 & 0x8800) >> 10)
				| ((_work32 & 0x8800) >> 11);
		_hl16 = (_work32 & 0xffff);
		
		_cpu.hl16(_hl16);
		
		updateFlags();
//		_cpu.setM_signF(getM_signF());
//		_cpu.setM_zeroF(getM_zeroF());
//		_cpu.setM_halfcarryF(getM_halfcarryF());
//		_cpu.setM_parityoverflowF(getM_parityoverflowF());
//		_cpu.setM_addsubtractF(getM_addsubtractF());
//		_cpu.setM_carryF(getM_carryF());
//		_cpu.setM_3F(getM_3F());
//		_cpu.setM_5F(getM_5F());

	}

	@Override
	public boolean getM_carryF() {
		return (_work32 & 0x10000) != 0;
	}

	@Override
	public boolean getM_addsubtractF() {
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		int auxIdx = _idx;
		return Z80.m_overflowTable[auxIdx >> 4];
	}

	@Override
	public boolean getM_halfcarryF() {
		int auxIdx = _idx;
		return Z80.m_halfcarryTable[auxIdx & 0x7];
	}

	@Override
	public boolean getM_zeroF() {
		return _hl16 == 0;
	}

	@Override
	public boolean getM_signF() {
		return (_hl16 & 0x8000) != 0;
	}

	@Override
	public boolean getM_5F() {
		return (_m_h8 & Z80.FIVE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return (_m_h8 & Z80.THREE_MASK) != 0;
	}

}
