package pt.ist.ave.jzx.Instructions;

public class RET_Z extends Instruction {

	private int _tstates;
	
	public RET_Z(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_tstates = 5;
		if (_cpu.getM_zeroF()) {
			_tstates += 6;
		_cpu.setM_pc16(_cpu.pop16());
		}
	}

	@Override
	public int incTstates() {
		return _tstates;
	}

}
