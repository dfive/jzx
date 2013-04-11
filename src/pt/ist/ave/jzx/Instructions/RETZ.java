package pt.ist.ave.jzx.Instructions;

public class RETZ extends Instruction {

	private int _tstates;
	
	public RETZ(short opCode) {
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
