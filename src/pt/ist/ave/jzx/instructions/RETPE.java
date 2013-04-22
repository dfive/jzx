package pt.ist.ave.jzx.instructions;

public class RETPE extends Instruction {

	private int _tstates;

	public RETPE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_tstates = 5;
		if (_cpu.getM_parityoverflowF()) {
			_tstates = 11;
			_cpu.setM_pc16(_cpu.pop16());
		}
	}

	@Override
	public int incTstates() {
		return _tstates;
	}

}
