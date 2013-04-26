package pt.ist.ave.jzx.operations;

public class OUT_I extends Operation {

	private int _b8;

	public void out_i() {
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
		notImplementedError("getM_parityoverflowF");
		return false;
	}

	@Override
	public boolean getM_halfcarryF() {
		notImplementedError("getM_halfcarryF");
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		return _b8 == 0;
	}

	@Override
	public boolean getM_signF() {
		notImplementedError("getM_signF");
		return false;
	}

	@Override
	public boolean getM_5F() {
		notImplementedError("getM_5F");
		//TODO
		return false;
	}

	@Override
	public boolean getM_3F() {
		notImplementedError("getM_3F");
		//TODO
		return false;
	}
}
