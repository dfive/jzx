package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class CPDR extends Operation {

	private int _b16;

	public int cpdr(int work8){
		_cpu.dec16hl();
		_cpu.dec16bc();
		_b16 = _cpu.bc16();
		_cpu.setM_parityoverflowF(getM_parityoverflowF());
		if (_cpu.getM_parityoverflowF() && !_cpu.getM_zeroF()) {
			_cpu.setM_tstates(_cpu.getM_tstates() + 5);
			_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
		}
		
		work8 = _cpu.getM_a8() - work8 - (_cpu.getM_halfcarryF() ? 1 : 0);
		_work8 = work8;
		
		_cpu.setM_3F (getM_3F());
		_cpu.setM_5F (getM_5F());
		return work8;
	}

	@Override
	public boolean getM_carryF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		return _b16 != 0;
	}

	@Override
	public boolean getM_halfcarryF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_signF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_5F() {
		return (_work8 & Z80.ONE_MASK) != 0;
	}

	@Override
	public boolean getM_3F() {
		return (_work8 & Z80.THREE_MASK) != 0;
	}
}
