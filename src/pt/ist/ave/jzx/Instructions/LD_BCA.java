package pt.ist.ave.jzx.Instructions;

public class LD_BCA extends Instruction {

	public LD_BCA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.getM_memory().write8(_cpu.bc16(), _cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
