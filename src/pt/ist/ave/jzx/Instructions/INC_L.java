package pt.ist.ave.jzx.Instructions;

public class INC_L extends Instruction {

	public INC_L(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_l8(_cpu.inc8(_cpu.getM_l8()));
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
