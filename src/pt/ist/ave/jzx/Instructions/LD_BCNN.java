package pt.ist.ave.jzx.Instructions;

public class LD_BCNN extends Instruction {

	public LD_BCNN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.bc16(_cpu.getM_memory().read16(_cpu.getM_pc16()));
		_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
	}

	@Override
	public int incTstates() {
		return 10;
	}

}
