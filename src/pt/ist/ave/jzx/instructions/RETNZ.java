package pt.ist.ave.jzx.instructions;

public class RETNZ extends Instruction {

	private int _tstates;
	
	public RETNZ(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_tstates = 5;
		if (!_cpu.getM_zeroF()) {
			_tstates = 11;
			_cpu.setM_pc16(_cpu.pop16());
		}
	}

	@Override
	public int incTstates() {
		return _tstates;
	}

}
