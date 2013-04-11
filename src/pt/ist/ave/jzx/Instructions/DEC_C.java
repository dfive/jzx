package pt.ist.ave.jzx.Instructions;

public class DEC_C extends Instruction {

	public DEC_C(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_c8(_cpu.dec8(_cpu.getM_c8()));

	}

	@Override
	public int incTstates() {
		return 4;
	}

}
