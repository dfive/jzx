package pt.ist.ave.jzx.operations;

public class INIR extends Operation {
	private int _b8;

	public void inir() {
		_b8  = _cpu.getM_b8() ;
		
		_cpu.setM_zeroF(getM_zeroF());
		_cpu.setM_addsubtractF(getM_addsubtractF());
		if (!_cpu.getM_zeroF()) {
			_cpu.setM_tstates(_cpu.getM_tstates() + 5);
			_cpu.setM_pc16(_cpu.decdec16(_cpu.getM_pc16()));
		}
	}

	@Override
	public boolean getM_carryF() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getM_addsubtractF() {
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
