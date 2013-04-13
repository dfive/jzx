package pt.ist.ave.jzx.Instructions;

public class RETNC extends Instruction {

	private int _tstates;
	
	public RETNC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_tstates = 5;
		if (!_cpu.getM_carryF()) {
			_tstates += 6;
			_cpu.setM_pc16(_cpu.pop16());
		}
	}

	@Override
	public int incTstates() {
		return _tstates;
	}

}
