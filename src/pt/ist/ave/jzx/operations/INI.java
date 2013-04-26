package pt.ist.ave.jzx.operations;

public class INI extends Operation {

	private int _b8;

	public void ini() { 
		_cpu.setM_tstates(_cpu.getM_tstates() + 16);
		_cpu.getM_memory().write8(_cpu.hl16(), _cpu.getM_io().in8(_cpu.bc16()));
		_cpu.setM_b8((_cpu.getM_b8() - 1) & 0xff);
		_cpu.inc16hl();
		
		_b8 = _cpu.getM_b8();
		
		_cpu.setM_zeroF(getM_zeroF());
		_cpu.setM_addsubtractF(getM_addsubtractF());
		
		// TODO: handle 3F, 5F
	}
	
	@Override
	public boolean getM_carryF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		return true;
	}

	@Override
	public boolean getM_parityoverflowF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_halfcarryF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		return _b8 == 0;
	}

	@Override
	public boolean getM_signF() {
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_5F() {
		//TODO
		notImplementedError("getM_carryF");
		return false;
	}

	@Override
	public boolean getM_3F() {
		//TODO
		notImplementedError("getM_carryF");
		return false;
	}

}
