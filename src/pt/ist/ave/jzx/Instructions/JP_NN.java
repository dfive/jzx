package pt.ist.ave.jzx.Instructions;

public class JP_NN extends Instruction {

	public JP_NN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_pc16(_cpu.getM_memory().read16(_cpu.getM_pc16()));
	}

	@Override
	public int incTstates() {
		return 10;
	}

}
