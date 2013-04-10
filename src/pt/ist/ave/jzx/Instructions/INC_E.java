package pt.ist.ave.jzx.Instructions;

public class INC_E extends Instruction {

	public INC_E(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_e8(_cpu.inc8(_cpu.getM_e8()));
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
