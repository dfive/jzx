package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class CPD extends Operation {

	private int _bc16;
	private int _work8;

	public int cpd() {
		_bc16 = _cpu.bc16();
		_cpu.setM_parityoverflowF(getM_parityoverflowF());
		_work8 = _cpu.getM_a8() - _work8 - (_cpu.getM_halfcarryF() ? 1 : 0);
		_cpu.setM_3F(getM_3F());
		_cpu.setM_5F(getM_5F());
		return _work8;
	}
	
	@Override
	public boolean getM_carryF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		notImplementedError("getM_addsubtractF");
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		return _bc16 != 0;
	}

	@Override
	public boolean getM_halfcarryF() {
		notImplementedError("getM_halfcarryF");
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		notImplementedError("getM_zeroF");
		return false;
	}

	@Override
	public boolean getM_signF() {
		notImplementedError("getM_signF");
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
