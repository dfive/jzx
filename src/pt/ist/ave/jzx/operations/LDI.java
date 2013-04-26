package pt.ist.ave.jzx.operations;

import pt.ist.ave.jzx.Z80;

public class LDI extends Operation {

	private int _work8;

	public int ldi() {
		_cpu.setM_tstates(_cpu.getM_tstates() + 16);

		_work8 = _cpu.getM_memory().read8(_cpu.hl16());

		_cpu.getM_memory().write8(_cpu.de16(), _work8);
		_cpu.inc16de();
		_cpu.inc16hl();
		_cpu.dec16bc();

		_cpu.setM_halfcarryF(getM_halfcarryF());
		_cpu.setM_parityoverflowF(getM_parityoverflowF());
		_cpu.setM_addsubtractF(getM_addsubtractF());

		_work8 += _cpu.getM_a8();

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
		return false;
	}

	@Override
	public boolean getM_parityoverflowF() {
		return _cpu.bc16() != 0;
	}

	@Override
	public boolean getM_halfcarryF() {
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
