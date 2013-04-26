package pt.ist.ave.jzx.operations;

public class OUT_D extends Operation {
	private int _b8;

	public void out_d() {
		_b8 = _cpu.getM_b8();
		
		_cpu.setM_zeroF(getM_zeroF());
		_cpu.setM_addsubtractF(getM_addsubtractF());
	}

	@Override
	public boolean getM_carryF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean getM_parityoverflowF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_halfcarryF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_zeroF() {
		// TODO Auto-generated method stub
		return _b8 == 0;
	}

	@Override
	public boolean getM_signF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_5F() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_3F() {
		// TODO Auto-generated method stub
		return false;
	}
}
